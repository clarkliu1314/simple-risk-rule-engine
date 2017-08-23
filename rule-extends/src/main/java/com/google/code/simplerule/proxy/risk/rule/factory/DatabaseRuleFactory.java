package com.google.code.simplerule.proxy.risk.rule.factory;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.esotericsoftware.kryo.Kryo;
import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskConvertor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RiskLogger;
import com.google.code.simplerule.core.rule.RiskRule;
import com.google.code.simplerule.core.rule.RuleCondition;
import com.google.code.simplerule.core.rule.RuleFactory;
import com.google.code.simplerule.core.rule.condition.FactorCondition;
import com.google.code.simplerule.core.rule.factory.PackageRuleFactory;
import com.google.code.simplerule.proxy.risk.entity.InterfaceEntity;
import com.google.code.simplerule.proxy.risk.entity.InterfaceLogFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleConditionEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleHandlerEntity;
import com.google.code.simplerule.proxy.risk.entity.RulesEntity;
import com.google.code.simplerule.proxy.risk.service.InterfaceLogFieldService;
import com.google.code.simplerule.proxy.risk.service.InterfaceService;
import com.google.code.simplerule.proxy.risk.service.RuleFieldService;
import com.google.code.simplerule.proxy.risk.service.RulesService;

public class DatabaseRuleFactory implements RuleFactory, ApplicationContextAware {

    protected Logger logger = LoggerFactory.getLogger(PackageRuleFactory.class);
    @Autowired
    private RulesService rulesService;
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private RuleFieldService fieldService;
    @Autowired
    private InterfaceLogFieldService interfaceLogFieldService;
    
    protected final Kryo kryo = new Kryo();

    public DatabaseRuleFactory() {
    }

    @Override
    public List<RiskInterface> loadRiskBusiness() {
        List<InterfaceEntity> list = interfaceService.findList();
        if (list == null || list.size() < 1)
            return null;
        List<RiskInterface> ris = new ArrayList();
        for (InterfaceEntity ie : list) {
            RiskInterface ri = createByEntity(ie); 
            
            if (ri.isAvailable()) {
                ris.add(ri);
            }
        }
        return ris;
    }

    private RiskInterface createByEntity(InterfaceEntity ie) {
        RiskInterface ri = new RiskInterface();
        ri.setInterfaceName(ie.getInterfaceName());
        ri.setDescription(ri.getDescription());
        ri.setRules(createRules(ri));

        return ri;
    }

    private List<RiskRule> createRules(RiskInterface ri) {
        List<RulesEntity> res = rulesService.findByInterfaceName(ri.getInterfaceName());

        //remove the rule which is not avalibale
        Iterator iterator =  res.iterator();
        while (iterator.hasNext()){
            RulesEntity rulesEntity = (RulesEntity)iterator.next();
            if (rulesEntity == null || rulesEntity.getStatus() == 0){
                iterator.remove();
            }
        }

        for (int i = 0;i < res.size();i++){
            for (int j = i;j<res.size();j++){
                RulesEntity rei =  res.get(i);
                RulesEntity rej =  res.get(j);
                if (rei.getPriority() < rej.getPriority()){
                    res.set(i,rej);
                    res.set(j,rei);
                }
            }
        }
        if (res == null || res.size() < 1)
            return null;

        List<RiskRule> list = new ArrayList();
        for (RulesEntity re : res) {
        	if (re.getStatus() != null && re.getStatus().intValue() == 0)
        		continue;
        	
        	try {
	            List<RuleConditionEntity> conditions = rulesService.findRuleConditionByRuleId(re.getId());
	            List<RuleHandlerEntity> handlers = rulesService.findRuleHandlerByRuleId(re.getId());
	            if (conditions == null || conditions.size() < 1)
	                continue;
	            if (handlers == null || handlers.size() < 1)
	                continue;
	
	            RiskRule rr = new RiskRule();
	            rr.setId(re.getId().toString());
	            rr.setNumber(re.getNo());
	            rr.setName(re.getName());
	            rr.setLevel(re.getLevel());
				rr.setConditionChain(createConditions(conditions));
	            rr.setHandlerChain(createHandlers(handlers));
	            rr.setStartTime(re.getStartTime());
	            rr.setEndTime(re.getEndTime());
	            rr.setStartInterval(re.getStartInterval());
	            rr.setEndInterval(re.getEndInterval());
	            if (rr.isAvailable()) {
	                list.add(rr);
	            }
        	} catch (RiskValidationException e) {
				logger.error("create rule error." + re.getNo(), e);
			}
        }
        return list;
    }

    private List<RiskHandler> createHandlers(List<RuleHandlerEntity> handlers) throws RiskValidationException {
        List<RiskHandler> rhs = new ArrayList();
        RiskHandler handler = null;
        Object[] objects = null;
        for (RuleHandlerEntity rhe : handlers) {
            handler = (RiskHandler)loadFromSpring(rhe.getCommand());
            objects = createHandlerArguments(handler, rhe.getCommandValue());
            handler.setArguments(objects);
            rhs.add(handler);
        }
        return rhs;
    }

    private Object[] createHandlerArguments(RiskHandler handler, String commandValue) throws RiskValidationException {
        FactorField[] ffs = handler.getArgumentFields();
        String[] ss = commandValue.split(",");
        if (ffs.length > ss.length)
            throw new IllegalArgumentException("Parse handler " + commandValue + " error." + handler.getClass().getName());

        int size = ffs.length;
        Object[] objs = new Object[size];
        for (int i=0; i<size; i++) {
            objs[i] = ffs[i].convert(ss[i].replace("/`", ","));
        }
        return objs;
    }

    private List<RuleCondition> createConditions(
            List<RuleConditionEntity> conditions) throws RiskValidationException {
        List<RuleCondition> rcs = new ArrayList();
        RiskFactor f = null;
        ConditionalOperator operator = null;
        Object value = null;
        Object[] params = null;
        String connector = null;
        FactorField[] externalParams = null;
        for (RuleConditionEntity rce : conditions) {
        	try {
	            f = (RiskFactor)loadFromSpring(rce.getRiskFactor());
	            operator = (ConditionalOperator)loadClass(rce.getCheckCondition());
	            value = createOperateValue(f, rce.getCheckValue());
	            params = createRiskParams(f, rce.getRiskFactorParam());
	            f.setInternalArguments(params);
	            externalParams = createExternalParams(f, rce.getExternalParam(), rce.getId());
	            f.setExternalArguments(externalParams);
	            if(rce.getRiskConvertor() != null)
	            	f.setRiskConvertor((RiskConvertor)loadFromSpring(rce.getRiskConvertor()));
	            connector = rce.getConnector();
	            rcs.add(new FactorCondition(f, operator, value,connector));
        	}
        	catch (Exception e) {
        		logger.error("load rule condition error.id is " + rce.getId(), e);
        	}
        }
        return rcs;
    }

    private Object[] createRiskParams(RiskFactor f, String riskFactorParam) throws RiskValidationException {
    	FactorField[] ffs = f.getInternalArguments();
    	if (ffs == null || ffs.length < 1)
    		return null;
    	
    	if (ffs.length > 0 && riskFactorParam == null)
    		throw new IllegalArgumentException("Parse factor " + f.getName() + " error.Params is null.");
    	
    	String[] ss = riskFactorParam.split(",");
    	if (ffs.length > ss.length)
            throw new IllegalArgumentException("Parse factor " + f.getName() + " error." + riskFactorParam);

    	int size = ffs.length;
        Object[] objs = new Object[size];
        for (int i=0; i<size; i++) {
            objs[i] = ffs[i].convert(ss[i].replace("/`", ","));
        }
        return objs;
	}
    
    private FactorField[] createExternalParams(RiskFactor f, String externalParam, Long cid) throws RiskValidationException {
    	FactorField[] result = null;
    	FactorField[] ffs = f.getExternalArguments();
    	if (ffs == null || ffs.length < 1)
    		return null;
    	
    	int size = ffs.length;
    	result = new FactorField[size];
    	for (int i=0; i<size; i++) {
    		result[i] = kryo.copy(ffs[i]);
    	}
    	
    	if (externalParam == null || externalParam.equals(""))
    		return result;
    	
    	String[] ss = externalParam.split(":")[0].split(",");

        for (int i=0; i<size; i++) {
        	if (ss.length-1 < i)
        		break;
        	
        	RuleFieldEntity fe = getRuleFieldById(cid, ss[i]);
        	if (fe == null)
        		continue;
        	
        	FactorField ff = result[i];
        	ff.setName(fe.getName());
        	ff.setDescription(fe.getDescription());
        	ff.setValueEnum(getValueEnum(fe));
        }
        return result;
	}

	private Map getValueEnum(RuleFieldEntity fe) {
		if (fe.getCodes() == null || fe.getCodes().equals(""))
			return null;
		
		String[] sc = fe.getCodes().split(",");
		String[] sci = fe.getCodeinfos().split(",");
		if (sc == null || sci == null)
			return null;
		if (sc.length != sci.length)
			return null;
		
		Map m = new HashMap();
		int size = sc.length;
		for (int i=0; i<size; i++) {
			m.put(sc[i], sci[i]);
		}
		return m;
	}

	private RuleFieldEntity getRuleFieldById(Long cid, String sid) {
		try {
			long id = Long.valueOf(sid);
			return fieldService.getById(id);
		}
		catch (Exception e) {
			logger.warn("get field error" + sid + ",condition id is " + cid, e);
			return null;
		}
	}

	private Object createOperateValue(RiskFactor f, String checkValue) throws RiskValidationException {
        return f.getResult().convert(checkValue);
    }

    private Object loadClass(String name) {
        try {
            Class c = Class.forName(name);
            return c.newInstance();
        } catch (Exception e) {
            logger.error("create object error." + name, e);
            return null;
        }
    }
    private Object loadFromSpring(String name) {
        try {
            return kryo.copy(context.getBean(Class.forName(name)));
        } catch (Exception e) {
            logger.error("create object error." + name, e);
            return null;
        }
    }

    private ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    @Override
    public RiskInterface loadByName(String interfaceName) {
        InterfaceEntity ie = interfaceService.getByName(interfaceName);
        if (ie == null)
            return null;

        return createByEntity(ie); 
    }

	@Override
	public RiskLogger loadRiskLoggerByName(String interfaceName, RiskLogger l) {
		try {
			RiskLogger rl = kryo.copy(l);
			rl.setArguments(findLoggerFieldsByName(interfaceName));
			return rl;
        } catch (Exception e) {
            logger.error("create logger error." + interfaceName, e);
            return null;
        }
	}

	private FactorField[] findLoggerFieldsByName(String interfaceName) {
		List<InterfaceLogFieldEntity> list = interfaceLogFieldService.findByInterfaceName(interfaceName);
		if (list == null || list.size() < 1)
			return null;
		
		int size = list.size();
		FactorField[] ffs = new FactorField[size];
		for (int i=0; i<size; i++) {
			InterfaceLogFieldEntity fe = list.get(i);
			ffs[i] = new StringFactorField(fe.getFieldName(), fe.getFieldDescription());
		}
		return ffs;
	}
}
