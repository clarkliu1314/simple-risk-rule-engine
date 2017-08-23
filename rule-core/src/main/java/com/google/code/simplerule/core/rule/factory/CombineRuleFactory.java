package com.google.code.simplerule.core.rule.factory;

import java.util.ArrayList;
import java.util.List;

import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RiskLogger;
import com.google.code.simplerule.core.rule.RiskRule;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.core.rule.RuleFactory;

/**
 * 混合的引擎构造类
 * @author drizzt
 *
 */
public class CombineRuleFactory implements RuleFactory {
	protected RuleFactory remoteRuleFactory = null;
	protected RuleFactory localRuleFactory = null;
	protected boolean canSkip = true;
	
	public CombineRuleFactory(RuleFactory factory, RuleFactory localRuleFactory, boolean canSkip) {
		remoteRuleFactory = factory;
		this.localRuleFactory = localRuleFactory;
		this.canSkip = canSkip;
	}

	@Override
	public List<RiskInterface> loadRiskBusiness() {
		List<RiskInterface> lis = localRuleFactory.loadRiskBusiness();
		List<RiskInterface> ris = remoteRuleFactory.loadRiskBusiness();
		return mergeInterface(lis, ris);
	}

	private List<RiskInterface> mergeInterface(List<RiskInterface> lis,
			List<RiskInterface> ris) {
		if (ris == null)
			return lis;
		if (lis == null)
			return ris;
		List<RiskInterface> is = new ArrayList<RiskInterface>();
		for (RiskInterface ri : ris) {
			is.add(ri);
		}
		for (RiskInterface ri : lis) {
			boolean found = false;
			for (RiskInterface rin : is) {
				if (ri.getInterfaceName().equals(rin.getInterfaceName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				is.add(ri);
			}
		}
		return is;
	}

	@Override
	public RiskInterface loadByName(String interfaceName) {
		return remoteRuleFactory.loadByName(interfaceName);
	}

	@Override
	public RiskLogger loadRiskLoggerByName(String interfaceName,
			RiskLogger logger) {
		return remoteRuleFactory.loadRiskLoggerByName(interfaceName, logger);
	}
	
}
