package com.google.code.simplerule.core.rule;

import java.util.*;
import com.google.code.simplerule.core.exception.RiskException;
import com.google.code.simplerule.core.processor.RiskCode;
import com.google.code.simplerule.core.result.ConditionResult;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.condition.FactorCondition;

/**
 * 规则规则处理器
 * @author drizzt
 *
 */
public class RiskRule {
    /**
     * 所有条件
     */
    private List<RuleCondition> conditionChain;

    /**
     * 所有处理器
     */
    private List<RiskHandler> handlerChain;

    /**
     * Rule有效起始时间
     */
    private Date startTime;

    /**
     * Rule有效结束时间
     */
    private Date endTime;

    /**
     * 级别，9高级
     */
    private int level = 9;

    /**
     * 规则名
     */
    private String name;

    /**
     * 规则编号
     */
    private String number;

    /**
     * 时间段开始
     */
    private String startInterval;

    /**
     * 时间段结束
     */
    private  String endInterval;

    /**
     * 执行规则
     * @param context 上下文
     * @param map 参数
     * @return 返回结果
     * @throws RiskException
     */
    public RiskResult processRule(RuleContext context, Map map) throws RiskException {
        boolean cresult = true;
        List<ConditionResult> list = new ArrayList<ConditionResult>();
        for (RuleCondition rc : conditionChain) {
            cresult =  rc.execute(context, map);
            Object connector =  rc.getConnector();
            ConditionResult conditionResult = new ConditionResult(connector,cresult);
            list.add(conditionResult);
        }
        
        //合并结果
        cresult =  handleOrAnd(list);
        context.setHit(cresult);

        RiskResult result = RiskCode.simpleResult(RiskCode.Success, "成功");
        if (cresult && handlerChain != null) {
            for (RiskHandler rh : handlerChain) {
                RiskResult rr = rh.execute(context, result);
                if (rr != null) {
                    result = rr;
                }
            }
        }
        return result;
    }

    /**
     * 设置条件
     * @param conditionChain
     */
    public void setConditionChain(List<RuleCondition> conditionChain) {
        this.conditionChain = conditionChain;
    }

    /**
     * 设置处理器
     * @param handlerChain
     */
    public void setHandlerChain(List<RiskHandler> handlerChain) {
        this.handlerChain = handlerChain;
    }

    public List<RuleCondition> getConditionChain() {
        return this.conditionChain;
    }

    public List<RiskHandler> getHandlerChain() {
        return this.handlerChain;
    }

    /**
     * 规则规则是否正确
     * @return
     */
    public boolean isAvailable() {
        if (conditionChain == null || conditionChain.size() < 1)
            return false;
        return true;
    }

    /**
     * 规则规则是否在有效期内
     * @param dt
     * @return
     */
    public boolean isEnabled(Date dt) {
        boolean  flag = true;
        if (conditionChain == null || conditionChain.size() < 1){
            flag = false;
        }

        //有为空的时间直接过
        if (dt==null || startTime==null || endTime ==null){
            flag =  true;
        }

        //判断是否在有效期内
        if (dt!=null && ((startTime != null && dt.before(startTime)) || (endTime != null && dt.after(endTime))) ){
            flag =  false;
        }

        //判断是否在一天中的时间段内
        if ( (startInterval != null &&  startInterval.length() > 0) && (endInterval !=null  && endInterval.length() > 0)){
            Date start = processStartTime(startInterval);
            Date end =  processEndTime(endInterval);
            if (dt!=null && ((start !=null && dt.before(start)) || (end!=null && dt.after(end)) )){
                flag =  false;
            }
        }
        return flag;
    }

    /**
     * 处理结束时间段
     * @param interval
     * @return
     */
    private Date  processStartTime(String interval){
        Calendar calendar = new GregorianCalendar();
        int hour = Integer.valueOf(interval.split(":")[0]);
        int minute = Integer.valueOf(interval.split(":")[1]);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 处理开始时间段
     * @param interval
     * @return
     */
    private Date  processEndTime(String interval){
        Calendar calendar = new GregorianCalendar();
        int hour = Integer.valueOf(interval.split(":")[0]);
        int minute = Integer.valueOf(interval.split(":")[1]);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        //结束时间都认为是一分钟的最后，即59秒999毫秒，约为1分钟
        calendar.set(Calendar.MINUTE,minute + 1);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 得到所有风险因素
     * @return
     */
    public List<RiskFactor> getFactors() {
        if (conditionChain == null || conditionChain.size() < 1)
            return null;
        List<RiskFactor> rfs = new ArrayList();
        for (RuleCondition rc : conditionChain) {
            if (rc instanceof FactorCondition) {
                FactorCondition fc = (FactorCondition)rc;
                rfs.add(fc.getFactor());
            }
        }
        return rfs;
    }

    /**
     * 按顺序执行
     * @param list
     * @param index
     * @return
     */
    public boolean handle(List<ConditionResult> list,int index){

        ConditionResult conditionResult = list.get(index);
        Object connector =  conditionResult.getConnector();
        Boolean result = conditionResult.getResult();

        if (connector == null || connector.equals("") || index > list.size() - 1){

        }
        if (connector.equals("or")){
            result = result || handle(list, index + 1);
        }else if (connector.equals("and")){
            result = result && handle(list, index + 1);
        }

        return result;
    }

    /**
     * 先执行或，再执行与
     * @param list
     * @return
     */
    public boolean handleOrAnd(List<ConditionResult> list){
        //处理所有or
        for (int index = 0;index<list.size(); index ++){
            ConditionResult conditionResult = list.get(index);
            Object connector =  conditionResult.getConnector();
            Boolean result = conditionResult.getResult();
            if (connector != null && connector.equals("or")){
                result = result || list.get(index + 1).getResult();
                conditionResult.setResult(result);
                conditionResult.setConnector(list.get(index + 1).getConnector());
                list.remove(index + 1);
                //减一，移除list一个后要重新判断当前的index的值
                index = index - 1;
            }
        }

        //处理and
        boolean   result  =  handleAnd(list,0);
        return result;
    }

    /**
     * 处理全部是and
     * @param list
     * @param index
     * @return
     */
    public boolean handleAnd(List<ConditionResult> list,int index){
        ConditionResult conditionResult = list.get(index);
        Boolean result = conditionResult.getResult();
        if (index < list.size() - 1){
            result = result && handleAnd(list, index + 1);
        }
        return  result;
    }

    protected String id;

    /**
     * 得到规则规则id
     * @return
     */
    public String getId() {
        if (id != null)
            return id;
        return String.valueOf(this.hashCode());
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartInterval() {
        return startInterval;
    }

    public void setStartInterval(String startInterval) {
        this.startInterval = startInterval;
    }

    public String getEndInterval() {
        return endInterval;
    }

    public void setEndInterval(String endInterval) {
        this.endInterval = endInterval;
    }
}
