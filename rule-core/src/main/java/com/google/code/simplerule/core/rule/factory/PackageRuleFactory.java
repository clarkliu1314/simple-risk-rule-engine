package com.google.code.simplerule.core.rule.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.esotericsoftware.kryo.Kryo;
import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.rule.RiskConvertor;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RiskLogger;
import com.google.code.simplerule.core.rule.RiskRule;
import com.google.code.simplerule.core.rule.RuleCondition;
import com.google.code.simplerule.core.rule.RuleFactory;
import com.google.code.simplerule.core.rule.condition.FactorCondition;

/**
 * 通过Jar包得到规则
 * @author drizzt
 *
 */
public class PackageRuleFactory implements RuleFactory, ApplicationContextAware {
	protected Logger logger = LoggerFactory.getLogger(PackageRuleFactory.class);
	protected final Kryo kryo = new Kryo();
	protected Resource[] ruleResources;

	public Resource[] getRuleResources() {
		return ruleResources;
	}

	public void setRuleResources(Resource[] ruleResources) {
		this.ruleResources = ruleResources;
	}

	public PackageRuleFactory() {
		System.err.println("PackageRuleFactory :: create...........");
	}

    /**
     * 加载所有规则接口
     * @return
     */
	@Override
	public List<RiskInterface> loadRiskBusiness() {
		if (ruleResources == null || ruleResources.length < 1)
			return null;
		List<RiskInterface> list = new ArrayList();
		for (Resource r : ruleResources) {
			List<RiskInterface> is = loadFromResource(r);
			if (is == null || is.size() < 1)
				continue;
			for (RiskInterface ri : is) {
				if (!list.contains(ri)) {
					list.add(ri);
				}
			}
		}
		return list;
	}

    /**
     * 解析xml文件加载规则接口
     * @param r
     * @return
     */
	private List<RiskInterface> loadFromResource(Resource r) {
		List<RiskInterface> list = new ArrayList();
		SAXReader saxReader = new SAXReader();   
		try {   
			Document document = saxReader.read(r.getInputStream());
			Element interfaces = document.getRootElement();
			for(Iterator i = interfaces.elementIterator(); i.hasNext();){
				RiskInterface ri = new RiskInterface();
				Element inter = (Element)i.next();
				for(Iterator j = inter.elementIterator(); j.hasNext();){
					Element node = (Element)j.next();
					if (node.getName().equals("name")) {
						ri.setInterfaceName(node.getText());
					}
					if (node.getName().equals("description")) {
						ri.setDescription(node.getText());
					}
					if (node.getName().equals("rules")) {
						parseRules(ri, node);
					}
				}
				
				if (ri != null && ri.isAvailable()) {
					list.add(ri);
				}
			}
		} catch (DocumentException e) {
			logger.error("parse rule file error.xml error." + r.getFilename(), e);
		} catch (IOException e) {
			logger.error("parse rule file error." + r.getFilename(), e);
		}
		return list;
	}

    /**
     * 解析规则接口中的规则规则
     * @param ri
     * @param node
     */
	private void parseRules(RiskInterface ri, Element node) {
		List<RiskRule> list = new ArrayList();
		for(Iterator j = node.elementIterator(); j.hasNext();){
			Element rule = (Element)j.next();
			RiskRule r = parseRule(rule);
			if (r != null) {
				list.add(r);
			}
		}
		ri.setRules(list);
	}

	private RiskRule parseRule(Element rule) {
		RiskRule r = new RiskRule();
		for(Iterator j = rule.elementIterator(); j.hasNext();){
			Element node = (Element)j.next();
			if (node.getName().equals("name")) {
				r.setName(node.getText());
			}
			if (node.getName().equals("level")) {
				r.setLevel(Integer.valueOf(node.getText()));
			}
			if (node.getName().equals("conditions")) {
				try {
					parseConditions(r, node);
				} catch (RiskValidationException e) {
					logger.error("parse rule error.", e);
					return null;
				}
			}
			if (node.getName().equals("handlers")) {
				parseHandlers(r, node);
			}
		}
		if (r.isAvailable())
			return r;
		return null;
	}

	private void parseHandlers(RiskRule r, Element node) {
		List<RiskHandler> rcs = new ArrayList();
		for(Iterator i = node.elementIterator(); i.hasNext();){
			Element c = (Element)i.next();
			RiskHandler handler = null;
			Object[] objects = null;
			for(Iterator j = c.elementIterator(); j.hasNext();){
				Element child = (Element)j.next();
				if (child.getName().equals("name")) {
					handler = (RiskHandler)loadFromSpring(child.getText());
				}
				if (child.getName().equals("values")) {
					objects = parseObjectValues(child);
				}
			}
			if (handler != null) {
				handler.setArguments(objects);
				rcs.add(handler);
			}
		}

		r.setHandlerChain(rcs);
	}

	private Object[] parseObjectValues(Element node) {
		List list = new ArrayList();
		for(Iterator i = node.elementIterator(); i.hasNext();) {
			Element c = (Element)i.next();
			list.add(c.getText());
		}
		return list.toArray();
	}

    /**
     * 解析conditions
     * @param r
     * @param node
     * @throws RiskValidationException 
     */
	private void parseConditions(RiskRule r, Element node) throws RiskValidationException {
		List<RuleCondition> rcs = new ArrayList();
		for(Iterator i = node.elementIterator(); i.hasNext();){
			Element c = (Element)i.next();
            FactorCondition factorCondition = null;
			RiskFactor f = null;
			RiskConvertor convertor = null;
			ConditionalOperator operator = null;
			Object value = null;
			Object[] internalArguments = null;
            String connector = null;
			for(Iterator j = c.elementIterator(); j.hasNext();){
				Element child = (Element)j.next();
				if (child.getName().equals("factor")) {
					f = (RiskFactor)loadFromSpring(child.getText());
				}
				if (child.getName().equals("convertor")) {
					convertor = (RiskConvertor)loadFromSpring(child.getText());
					f.setRiskConvertor(convertor);
				}
				if (child.getName().equals("arguments")) {
					internalArguments = parseArguments(child, f);
					if (f != null && internalArguments != null) {
						f.setInternalArguments(internalArguments);
					}
				}
				if (child.getName().equals("operator")) {
					operator = (ConditionalOperator)loadClass(child.getText());
				}
				if (child.getName().equals("value")) {
					value = child.getText();
				}
                if (child.getName().equals("connector")) {
                    connector = child.getText();
                }
			}
			if (f != null && operator != null && value != null) {
                factorCondition = new FactorCondition(f, operator, value,connector);
				rcs.add(factorCondition);
			}
		}
		
		r.setConditionChain(rcs);
	}

	private Object[] parseArguments(Element node, RiskFactor f) throws RiskValidationException {
		if (f == null)
			return null;
		FactorField[] ffs = f.getInternalArguments();
		if (ffs == null || ffs.length < 1)
			return null;
		int size = ffs.length;
		Object[] objs = new Object[size];
		int time = 0;
		for(Iterator i = node.elementIterator(); i.hasNext() && time < size; time ++){
			Element child = (Element)i.next();
			objs[time] = ffs[time].convert(child.getText());
		}
		return objs;
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
		throw new UnsupportedOperationException("PackageRuleFactory not support methed loadByName");
	}

	@Override
	public RiskLogger loadRiskLoggerByName(String interfaceName, RiskLogger logger) {
		return logger;
	}
}
