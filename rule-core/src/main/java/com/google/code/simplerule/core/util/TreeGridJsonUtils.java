package com.google.code.simplerule.core.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.util.CollectionUtils;

/**
 * JQuery树表Json串构造工具
 * @author 韩彦伟
 * @since 2013-5-15
 */
public class TreeGridJsonUtils {

	public final static String ROOTNODENAME = "root_node";
	
	@SuppressWarnings("unchecked")
	public static String parseToTreeJson(List<?> beanList, String idField, String pidField){
		if(CollectionUtils.isEmpty(beanList)) return "";
		
		DefaultMutableTreeNode rootNode = buildTree(beanList, idField, pidField);
		
		Enumeration<DefaultMutableTreeNode> treeNodeEnum = rootNode.postorderEnumeration();
		
		HashMap<String, String> id_topLevelJson_map = new HashMap<String, String>();
		
		HashMap<String, String> id_json_map = new HashMap<String, String>();
		while(treeNodeEnum.hasMoreElements()){
			DefaultMutableTreeNode treeNode = treeNodeEnum.nextElement();
			Object userObj = treeNode.getUserObject();
			
			if(userObj instanceof String && userObj.equals(ROOTNODENAME))
				continue;
			
			String jsonOfSelfWithoutBrace = getJsonOfSelfWithoutBrace(userObj);
			
			int childrenCount = treeNode.getChildCount();
			if(childrenCount > 0){
				StringBuilder childrenJsonBuilder = new StringBuilder();
				for(int index=0;index < childrenCount; index++){
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeNode.getChildAt(index);
					Object childObj = child.getUserObject();
					String childIid = CommonBeanUtils.getAttributeValue(childObj, idField).toString();
					String childJson = id_json_map.get(childIid);
					childrenJsonBuilder.append(childJson+",");
				}
				childrenJsonBuilder.deleteCharAt(childrenJsonBuilder.lastIndexOf(","));
				childrenJsonBuilder.insert(0, "[");
				childrenJsonBuilder.append("]");
				
				jsonOfSelfWithoutBrace = jsonOfSelfWithoutBrace+" , \"children\" :"+childrenJsonBuilder.toString();
			}
			
			String jsonOfSelf = "{"+jsonOfSelfWithoutBrace+"}";
			
			id_json_map.put(CommonBeanUtils.getAttributeValue(userObj, idField).toString(), jsonOfSelf);
			
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeNode.getParent();
			Object parentObj = parentNode.getUserObject();
			if(parentObj instanceof String && parentObj.equals(ROOTNODENAME))
				id_topLevelJson_map.put(CommonBeanUtils.getAttributeValue(userObj, idField).toString(), jsonOfSelf);
		}
		
		Set<String> topIdSet = id_topLevelJson_map.keySet();
		List<String> topIdList = new ArrayList<String>(topIdSet);		
		Collections.sort(topIdList);
		
		StringBuilder resultJsonBuilder = new StringBuilder();
		for(String topId : topIdList){
			String topJson = id_topLevelJson_map.get(topId);
			resultJsonBuilder.append(topJson+",");
		}
		
		resultJsonBuilder.deleteCharAt(resultJsonBuilder.lastIndexOf(","));
		
		return "["+resultJsonBuilder+"]";
	}
	
	private static String getJsonOfSelfWithoutBrace(Object obj){
		Field[] allFields = obj.getClass().getDeclaredFields();
		StringBuilder builder = new StringBuilder();
		for(Field field : allFields){
			String fieldName = field.getName();
			
			Object fieldValue = null;
			try{
				fieldValue = CommonBeanUtils.getAttributeValue(obj, fieldName);
			}catch(Exception ex){//对出错的字段进行容错，忽略掉
				continue;
			}
						
			builder.append("\""+fieldName+"\":\""+getFormatString(fieldValue)+"\", ");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		
		return builder.toString();
	}
	
	private static String getFormatString(Object obj){
		if(obj == null) return "";
		
		if(obj instanceof Date){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.format(obj);
		}
		
		return obj.toString();
	}

	private static DefaultMutableTreeNode buildTree(List<?> beanList, String idField, String pidField) {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(ROOTNODENAME);
		HashMap<String, DefaultMutableTreeNode> id_treeNode_map = new HashMap<String, DefaultMutableTreeNode>();
		
		for(Object obj : beanList){
			DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(obj);
			id_treeNode_map.put(CommonBeanUtils.getAttributeValue(obj, idField).toString(), treeNode);
		}
		
		for(Object obj : beanList){
			String id = CommonBeanUtils.getAttributeValue(obj, idField).toString();
			String pid = CommonBeanUtils.getAttributeValue(obj, pidField).toString();
			
			DefaultMutableTreeNode selfNode = id_treeNode_map.get(id);
			DefaultMutableTreeNode parentNode = id_treeNode_map.get(pid);
			if(parentNode == null)
				rootNode.add(selfNode);
			else
				parentNode.add(selfNode);
		}
		return rootNode;
	}
}
