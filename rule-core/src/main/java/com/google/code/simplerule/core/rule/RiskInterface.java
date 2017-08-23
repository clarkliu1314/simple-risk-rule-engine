package com.google.code.simplerule.core.rule;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.google.code.simplerule.async.AsyncContext;
//import com.google.code.simplerule.async.AsyncFactory;
//import com.google.code.simplerule.async.AsyncFuture;
import com.google.code.simplerule.core.annotation.Rule;
import com.google.code.simplerule.core.exception.RiskException;
import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.processor.DefaultRuleProcessor;
import com.google.code.simplerule.core.processor.RiskCode;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.condition.FactorCondition;

/**
 * 规则接口，用于处理业务接入
 * @author drizzt
 *
 */
public class RiskInterface {
	protected Logger logger = LoggerFactory.getLogger(RiskInterface.class);
	protected List<RiskRule> rules = null;
	protected String description;
	protected String interfaceName;
	protected Date beginTime, endTime;
//	protected AsyncFactory asyncFactory;
	protected int maxTimeOut = 3000;
	protected final Method ruleMethod;
	protected RiskLogger riskLogger;
	protected RuleMonitor ruleStat;
	protected boolean logAll = true;
	protected Map<String, RuleCollector> collectors = null;
	
	public static final int RuleMediumLevel = 5; 
	
	/**
	 * 构造规则接口
	 */
	public RiskInterface() {
		ruleMethod = getRuleMethod();
		ruleStat = new RuleMonitor();
	}
	
	/**
	 * 得到规则的处理方法，供异步调用
	 * @return
	 */
	private Method getRuleMethod() {
		Class c = RiskRule.class;
		Method[] ms = c.getMethods();
		for (Method m : ms) {
			if (m.getName().equals("processRule"))
				return m;
		}
		return null;
	}

	/**
	 * 得到所有规则
	 * @return
	 */
	public List<RiskRule> getRules() {
		return rules;
	}
	
	/**
	 * 设置所有规则
	 * @param rules
	 */
	public void setRules(List<RiskRule> rules) {
		this.rules = rules;
		
		initCollectors(rules);
	}
	
	private void initCollectors(List<RiskRule> rules2) {
		collectors = new HashMap();
		
		if (rules != null && rules.size() > 0) {
			for (RiskRule r : rules) {
				if (r.getConditionChain() == null || r.getConditionChain().size() < 1)
					continue;
				
				for (RuleCondition c : r.getConditionChain()) {
					if (!c.getClass().equals(FactorCondition.class))
						continue;
					FactorCondition fc = (FactorCondition)c;
					if (fc.getFactor() == null || !(fc.getFactor() instanceof RuleCollector))
						continue;
					
					RuleCollector rc = (RuleCollector)fc.getFactor();
					if (collectors.containsKey(rc.getCollectorKey()))
						continue;
					
					collectors.put(rc.getCollectorKey(),  rc);
				}
			}
		}
	}

	/**
	 * 得到描述信息
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述信息
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 得到接口名
	 * @return
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * 得到开始时间
	 * @return
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 得到结束时间
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 得到规则日志记录器
	 * @return
	 */
	public RiskLogger getLogger() {
		return riskLogger;
	}

	public void setLogger(RiskLogger rl) {
		this.riskLogger = rl;
	}

	/**
	 * 设置异步处理工厂
	 * @param asyncFactory
	 */
//	public void setAsyncFactory(AsyncFactory asyncFactory) {
//		this.asyncFactory = asyncFactory;
//	}

	/**
	 * 得到接口参数
	 * @return
	 */
	public List<FactorField> getArguments() {
		if (rules == null || rules.size() < 1)
			return null;
		
		List<FactorField> result = new ArrayList();
		for (RiskRule r : rules) {
			List<RiskFactor> fs = r.getFactors();
			if (fs == null || fs.size() < 1)
				continue;
			for (RiskFactor f : fs) {
				FactorField[] fields = f.getExternalArguments();
				if (fields == null || fields.length < 1)
					continue;
				for (FactorField field : fields) {
					if (!foundField(result, field)) {
						result.add(field);
					}
				}
			}
		}
		if (riskLogger != null) {
			FactorField[] fields = riskLogger.getArguments();
			if (fields != null && fields.length > 0) {
				for (FactorField field : fields) {
					if (!foundField(result, field)) {
						result.add(field);
					}
				}
			}
		}
		return result;
	}
	
	private boolean foundField(List<FactorField> fs, FactorField field) {
		for (FactorField f : fs) {
			if (f.getName().equals(field.getName()))
				return true;
		}
		return false;
	}

	/**
	 * 接口是否正确
	 * @return
	 */
	public boolean isAvailable() {
		if (rules == null || rules.size() < 1)
			return false;
		return true;
	}
	
	/**
	 * 当前接口是否可用
	 * @return
	 */
	public boolean isEnabled() {
		if (rules == null || rules.size() < 1)
			return false;
		Date dtNow = new Date();
		if (beginTime != null && dtNow.before(beginTime))
			return false;
		if (endTime != null && dtNow.after(endTime))
			return false;
		for (RiskRule r : rules) {
			if (r.isEnabled(dtNow))
				return true;
		}
		return false;
	}
	
	/**
	 * 执行接口规则
	 * @param map 参数
	 * @return 结果
	 * @throws Exception
	 */
	public RiskResult processRule(Map map) throws Exception {
		long tick = System.currentTimeMillis();
		RiskResult result = null;
		
//		if (asyncFactory != null) {
//			//如多线程工厂不为空，则多线程执行
//			result = runMuliThread(map);
//		}
//		else 
		{
			//否则单线程执行
			result = runSingleThread(map);
		}

		tick = System.currentTimeMillis() - tick;
		
		//如为空，表示没有规则，此时返回成功
		if (result == null) {
			result = RiskCode.simpleResult(RiskCode.Success, "");
		}
		
		tick = System.currentTimeMillis();
		//规则日志记录器不为空。原因不为空表示触发规则，要记录。logAll为true表示所有情况都要记录。
		
		if (collectors != null && collectors.size() > 0) {
			for (RuleCollector rc : collectors.values()) {
				processCollector(rc, map, result);
			}
		}
		if (riskLogger != null 
				&& (logAll || (result.getReason() != null && !result.getReason().equals("")))) {
			riskLogger.writeLog(this.getInterfaceName(), map, result, tick, result.getReason());
		}
		tick = System.currentTimeMillis() - tick;
		
		logger.info("Logger time:" + tick);
		
		//返回结果
		return result;
	}

	private void processCollector(RuleCollector rc, Map map, RiskResult result) throws RiskException, RiskValidationException {
		rc.collect(this.getInterfaceName(), getArgumentObjects(rc, map), result);
	}
	
	private Object[] getArgumentObjects(RuleCollector rc, Map map) throws RiskException, RiskValidationException {
		FactorField[] fs = rc.getExternalArguments();
		if (fs == null)
			return null;
		
		int count = fs.length;
		Object[] objs = new Object[count];
		for (int i=0; i<count; i++) {
			FactorField ff = fs[i];
			if (!map.containsKey(ff.getName()))
				throw new RiskException(RiskCode.ValidateError, ff.getName() + " not found.");
			objs[i] = ff.convert(map.get(ff.getName()));
		}
		return objs;
	}

	private RiskResult runSingleThread(Map map) {
		RiskResult result = null;
		Date dt = new Date();
		List<RuleContext> contexts = new ArrayList(20);
		RuleContext context = null;
		long tick = 0;
		//异步执行所有规则
		for (RiskRule r : rules) {
			tick = System.currentTimeMillis();
			
			if (!r.isEnabled(dt))
				continue;
			//map是请求的参数map
			context = createRuleContext(map, r);
			try {
				//执行一个rule中所有的条件处理结果，并合并结果输出。
				context.setLastResult(r.processRule(context, map));
				
				tick = System.currentTimeMillis() - tick;
				context.getLastResult().setExecuteTime(String.valueOf(tick));
			} catch (RiskException re) { 
				logger.warn("Interface process error." + interfaceName, re);
				result = RiskCode.simpleResult(re.getCode(), re.getMessage());
			} catch (Exception e) {
				logger.error("Interface process error." + interfaceName, e);
				result = RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
				break;
			}
			
			contexts.add(context);
		}
		
		tick = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		String ruleInfo = "";
		if (result == null) {
			int size = contexts.size();
			for (int i=0; i<size; i++) {
				context = contexts.get(i);
				RiskResult obj = context.getLastResult();
				
				//如为空，未知异常
				if (obj == null) {
					logger.error("Processing " + interfaceName + " error.Rule:" + context.getCurrentRule().getId() + ".Context:" + context.toString());
					result = RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
					break;
				}
				sb.append(context.getCurrentRule().getName() + ":" + obj.getExecuteTime() + ";");
				//表示验证异常
				ruleInfo += context.getRuleTrack();
				
				//执行规则正常
				if (obj instanceof RiskResult) {
					result = (RiskResult)obj;
					if (!result.getCode().equals(RiskCode.Success)) {
						//规则为高级，并且执行不成功，则表示命中规则，并返回结果
						if (context.getCurrentRule().getLevel() > RuleMediumLevel)
							break;
						else {
							result = RiskCode.simpleResult(RiskCode.Success, "成功");
						}
					}
				}
			}
		}
		tick = System.currentTimeMillis() - tick;
		logger.info(sb.toString() + ".common:" + tick);
		
		result.setReason(ruleInfo);
		return result;
	}

//	private RiskResult runMuliThread(Map map) throws Exception {
//		RiskResult result = null;
//		Date dt = new Date();
//		AsyncContext asyncContext = new AsyncContext();
//		List<AsyncFuture> futures = new ArrayList(20);
//		List<RuleContext> contexts = new ArrayList(20);
//		RuleContext context = null;
//		AsyncFuture future = null;
//		//异步执行所有规则
//		for (RiskRule r : rules) {
//			if (!r.isEnabled(dt))
//				continue;
//			
//			future = asyncFactory.createFuture(asyncContext);
//			future.setId(r.getId());
//			asyncContext.incr();
//			
//			context = createRuleContext(map, r);
//			future.execute(r, ruleMethod, ruleMethod, new Object[]{context, map});
//			
//			futures.add(future);
//			contexts.add(context);
//		}
//		
//		//等待执行结束，超时则返回
//		asyncContext.waitProcessFinish(maxTimeOut);
//		if (asyncContext.isProcessing()) {
//			result = RiskCode.simpleResult(RiskCode.ProcessTimeOut, "执行超时。");
//		}
//		
//		String ruleInfo = "";
//		//得到结果，如不成功则返回
//		if (result == null) {	//result为null表示没有超时
//			int size = futures.size();
//			for (int i=0; i<size; i++) {
//				future = futures.get(i);
//				context = contexts.get(i);
//				Object obj = asyncContext.getResult(future.getId());
//				//如为空，未知异常
//				if (obj == null) {
//					logger.error("Processing " + interfaceName + " error.Rule:" + context.getCurrentRule().getId() + ".Context:" + context.toString());
//					result = RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
//					break;
//				}
//				//表示验证异常
//				if (obj instanceof RiskException) {
//					RiskException re = (RiskException)obj;
//					if (re.getCode().equals(RiskCode.ValidateError)) {
//						return RiskCode.simpleResult(RiskCode.ValidateError, re.getMessage());
//					}
//				}
//				ruleInfo += context.getRuleTrack();
//				
//				//执行规则正常
//				if (obj instanceof RiskResult) {
//					result = (RiskResult)obj;
//					if (!result.getCode().equals(RiskCode.Success)) {
//						//规则为高级，并且执行不成功，则表示命中规则，并返回结果
//						if (context.getCurrentRule().getLevel() > RuleMediumLevel)
//							break;
//						else {
//							result = RiskCode.simpleResult(RiskCode.Success, "成功");
//						}
//					}
//				}
//				else if (obj instanceof Exception) {
//					//发生异常，并捕获到了
//					logger.error("Processing " + interfaceName + " error.Rule:" + context.getCurrentRule().getId(), (Exception)obj);
//					result = RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
//					break;
//				}
//				else {
//					//发生异常
//					logger.error("Processing " + interfaceName + " error.Rule:" + context.getCurrentRule().getId() + ".Context:" + context.toString());
//					result = RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
//					break;
//				}
//			}
//		}
//		
//		result.setReason(ruleInfo);
//		return result;
//	}

	private RuleContext createRuleContext(Map map, RiskRule r) {
		RuleContext context = new RuleContext();
		context.setRiskInterface(this);
		context.setParam(map);
		context.setCurrentRule(r);
		return context;
	}

	public RuleMonitor getRuleStat() {
		return ruleStat;
	}

	public void setLogAll(boolean logAll) {
		this.logAll = logAll;
	}
}
