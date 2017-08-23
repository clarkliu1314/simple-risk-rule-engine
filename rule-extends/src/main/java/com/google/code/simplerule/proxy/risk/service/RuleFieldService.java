package com.google.code.simplerule.proxy.risk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.dao.RuleFieldDao;
import com.google.code.simplerule.proxy.risk.entity.RuleFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.common.ComboTreeEntity;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;

@Service
public class RuleFieldService {
	@Autowired
	private RuleFieldDao ruleFieldDao;

	public void saveRuleFieldEntity(RuleFieldEntity entity) {
		ruleFieldDao.insert(entity);
	}
	
	public void modifyRuleFieldEntity(RuleFieldEntity entity) {
		ruleFieldDao.update(entity);
	}
	
	public void deleteById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		ruleFieldDao.delete(map);
	}
	
	public PageTagResultDTO<RuleFieldEntity> queryLimit(RuleFieldEntity entity) {
		HashMap<String, Object> paramMap = ObjectUtils.objectToMap(entity);
		
		PageTagResultDTO<RuleFieldEntity> page = new PageTagResultDTO<RuleFieldEntity>();
		
		int count = ruleFieldDao.queryCount(paramMap);
		if(count > 0) {
			page.setList(ruleFieldDao.queryLimit(paramMap));
		} else {
			page.setList(new ArrayList<RuleFieldEntity>());
		}
		
		page.setTotalSize(count);
		
		return page;
	}
	
	public RuleFieldEntity getById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return ruleFieldDao.queryOne(map);
	}

	public List<RuleFieldEntity> listOrderByCategory() {
		return ruleFieldDao.listOrderByCategory();
	}
	
	public List<ComboTreeEntity>  queryAllOnCategory(){
		List<ComboTreeEntity> list = new ArrayList<ComboTreeEntity>();
		List<RuleFieldEntity> categories = ruleFieldDao.queryCategories();
		for (RuleFieldEntity ruleFieldEntity : categories) {
		  ComboTreeEntity ce = new ComboTreeEntity();
		  ce.setText(ruleFieldEntity.getCategory());
		  List<ComboTreeEntity> cl = new ArrayList<ComboTreeEntity>();
		  Map<String,Object> paramMap = new HashMap<String,Object>();
		  paramMap.put("category", ruleFieldEntity.getCategory());
		  List<RuleFieldEntity> relist = ruleFieldDao.queryByCategory(paramMap);
		  for (RuleFieldEntity re : relist) {
			  ComboTreeEntity entity = new ComboTreeEntity();
			  entity.setId(re.getId());
			  entity.setText(re.getDescription());
			  cl.add(entity);
		}
		  ce.setChildren(cl);
		  list.add(ce);
		}
		return list;
	}
	
	
}
