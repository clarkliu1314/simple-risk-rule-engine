package com.google.code.simplerule.core.processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.simplerule.core.annotation.Field;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.help.FieldInfo;
import com.google.code.simplerule.core.help.HelpInfo;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RiskLogger;
import com.google.code.simplerule.core.rule.RuleFactory;
import com.google.code.simplerule.core.rule.RuleMonitor;
import com.google.code.simplerule.core.runner.ProcessRunner;

/**
 * 默认的规则执行器
 * @author drizzt
 *
 */
public class DefaultRuleProcessor implements RuleProcessor {
	protected Logger logger = LoggerFactory.getLogger(DefaultRuleProcessor.class);
	protected Map<String, RiskInterface> mappers;
	protected RuleFactory ruleFactory;
//	protected AsyncFactory asyncFactory;
	protected boolean useActor;
	protected ProcessRunner runner = null;
	protected boolean logAll = true;

//	protected ServerStatus status = null;
	
	protected List<RiskFactor> factors = null;
	protected List<RiskHandler> handlers = null;
	protected RiskLogger riskLogger = null;
	
	protected Map<String, String> interfaces = null;
	
	/**
	 * 设置系统中的所有风险因素
	 * @param rs
	 */
//	@Autowired
	public void setFactors(List<RiskFactor> rs) {
		factors = rs;
	}
	
	/**
	 * 设置系统中的所有处理器
	 * @param rhs
	 */
//	@Autowired
	public void setHandlers(List<RiskHandler> rhs) {
		handlers = rhs;
	}
	
	/**
	 * 设置规则接口
	 * @param interfaces
	 */
	public void setRiskInterfaces(Map<String, String> interfaces) {
		this.interfaces = interfaces;
	}
	
	/**
	 * 构造
	 * @param factory 规则工厂
	 * @param runner 执行者
	 * @param logAll 审核日志是否记录所有规则结果
	 */
//	public DefaultRuleProcessor(RuleFactory factory, ProcessRunner runner, RiskLogger r, boolean logAll) {
//		this(factory, runner, r, logAll
//				//, null
//				);
//	}
	
	/**
	 * 构造
	 * @param factory 规则工厂
	 * @param runner 执行者
	 * @param logAll 审核日志是否记录所有规则结果
	 * @param asyncFactory 异步处理工厂
	 */
	public DefaultRuleProcessor(RuleFactory factory, ProcessRunner runner, RiskLogger r, boolean logAll
//			, AsyncFactory asyncFactory
			) {
		mappers = new HashMap<String, RiskInterface>();
		ruleFactory = factory;
//		this.asyncFactory = asyncFactory;
		this.logAll = logAll;
		
		riskLogger = r;
		this.runner = runner;
//		status = new ServerStatus();
//		status.setStart(new Date());
	}
	
	/**
	 * 初始化方法
	 */
	@Override
	public void initialize() {
		System.out.println("aaaaaaaaaaaaa==========");
		List<RiskInterface> list = ruleFactory.loadRiskBusiness();
		if (list == null || list.size() < 1)
			return;
		
		for (RiskInterface rb : list) {
//			rb.setAsyncFactory(asyncFactory);
			rb.setLogger(getOperatorByInterfaceName(rb.getInterfaceName()));
			rb.setLogAll(logAll);
			mappers.put(rb.getInterfaceName(), rb);
		}
	}
	
	/**
	 * 得到规则接口的日志处理器
	 * @param interfaceName
	 * @return
	 */
	private RiskLogger getOperatorByInterfaceName(String interfaceName) {
		return ruleFactory.loadRiskLoggerByName(interfaceName, riskLogger);
	}

	/**
	 * 执行规则接口
	 * @param interfaceName 接口名
	 * @param map 执行参数
	 * @return 执行结果
	 */
	@Override
	public RiskResult process(String interfaceName, Map map) {
		if (!mappers.containsKey(interfaceName)) {
			return RiskCode.simpleResult(RiskCode.NotFoundRule, "Not found Rule by " + interfaceName);
		}
		
		RiskInterface inter = mappers.get(interfaceName);
		
		long tick = (new Date()).getTime();
		RiskResult result = null;
		try {
			//使用运行器执行
			result = runner.run(inter, map);
			
			tick = (new Date()).getTime() - tick;
			
			//统计状态信息
			addStat(inter, result, tick);
		}
		catch (Exception e) {
			logger.error("Processing " + interfaceName + " error.", e);
			result = RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
		}
		return result;
	}

	private void addStat(RiskInterface inter, RiskResult result, long tick) {
		RuleMonitor rm = inter.getRuleStat();
		rm.setLastVisit(new Date());
		if (result.getCode().equals(RiskCode.Exception)) {
			rm.setFailure(rm.getFailure() + 1);
//			status.setFailure(status.getFailure() + 1);
		}
		else {
			rm.setSuccess(rm.getSuccess() + 1);
//			status.setSuccess(status.getSuccess() + 1);
		}
		if (tick > rm.getMaxTime()) {
			rm.setMaxTime(tick);
		}
		int num = rm.getFailure() + rm.getSuccess();
		rm.setAverageTime((rm.getAverageTime() * (num - 1) + tick) / num);
		
		rm.setHealth(getStatusHealth(rm.getSuccess(), rm.getFailure()));
//		status.setHealth(getStatusHealth(status.getSuccess(), status.getFailure()));
	}

	private int getStatusHealth(int s, int f) {
		if (s == 0 && f > 0)
			return 1;
		if (f < 1)
			return 10;
		
		return s * 10 / (f + s);
	}

	/**
	 * 得到接口帮助信息
	 */
	@Override
	public HelpInfo getHelpInfo(String interfaceName) {
		if (!mappers.containsKey(interfaceName)) {
			return null;
		}
		
		return getHelpInfo(mappers.get(interfaceName));
	}

	private HelpInfo getHelpInfo(RiskInterface inter) {
		Class r = RiskResult.class;
		
		HelpInfo hi = new HelpInfo();
		hi.setInterfaceName(inter.getInterfaceName());
		hi.setDescription(inter.getDescription());
		hi.setParamInfos(getInterfaceParam(inter));
		hi.setResultInfos(createFieldInfo(r));
		return hi;
	}

	private List<FieldInfo> getInterfaceParam(RiskInterface inter) {
		List<FactorField> list = inter.getArguments();
		if (list == null || list.size() < 1)
			return null;
		
		List<FieldInfo> fis = new ArrayList();
		for (FactorField ff : list) {
			FieldInfo fi = new FieldInfo(ff.getName(), ff.getType().getName(), ff.getDescription(), true);
			fis.add(fi);
		}
		return fis;
	}

	private List<FieldInfo> createFieldInfo(Class p) {
		Method[] ms = p.getMethods();
		if (ms == null || ms.length < 1)
			return null;
		
		List<FieldInfo> list = new ArrayList();
		for (Method m : ms) {
			String name = m.getName();
			if (!name.startsWith("get") || m.getParameterTypes().length > 0)
				continue;
			
			Field f = m.getAnnotation(Field.class);
			if (f == null)
				continue;
			
			name = name.substring(3, 4).toLowerCase() + name.substring(4);
			list.add(new FieldInfo(name, m.getReturnType().getName(), f.description(), f.require()));
		}
		return list;
	}

	/**
	 * 更新接口
	 */
	@Override
	public int updateRuleInfo(String interfaceName) {
		try {
			logger.warn(interfaceName + " changed.");
			
			RiskInterface inter = ruleFactory.loadByName(interfaceName);
			if (inter == null || !inter.isAvailable()) {
				logger.error("Load rule error " + interfaceName + ".Maybe rule not validated.");
				return 2;
			}
			
//			inter.setAsyncFactory(asyncFactory);
			inter.setLogAll(logAll);
			inter.setLogger(getOperatorByInterfaceName(inter.getInterfaceName()));
			mappers.put(interfaceName, inter);
		}
		catch (Exception e) {
			logger.error("Update rule info error." + interfaceName + " content check error.", e);
			return 3;
		}
		return 0;
	}

	/**
	 * 得到规则接口列表
	 */
	@Override
	public Map<String, RiskInterface> getRuleList() {
		return mappers;
	}

//	@Override
//	public ServerStatus getServerStatus() {
//		return status;
//	}
	
	@Override
	public List<RiskFactor> findFactors(String business) {
		return factors;
	}

	@Override
	public List<RiskHandler> findHandlers(String business) {
		return handlers;
	}

	@Override
	public List<RiskInterface> findInterfaces() {
		List<RiskInterface> list = new ArrayList<RiskInterface>();
		Iterator<Entry<String, RiskInterface>> i = mappers.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, RiskInterface> e = i.next();
			list.add(e.getValue());
		}
		return list;
	}

	@Override
	public RuleMonitor getInterfaceMonitor(String interfaceName) {
		RiskInterface inter = mappers.get(interfaceName);
		if (inter == null)
			return null;
		return inter.getRuleStat();
	}
}
