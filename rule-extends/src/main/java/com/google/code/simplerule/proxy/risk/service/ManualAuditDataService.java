package com.google.code.simplerule.proxy.risk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

//import com.google.code.simplerule.proxy.risk.dao.ManualAuditDataDao;
import com.google.code.simplerule.proxy.risk.dto.CommonResult;
import com.google.code.simplerule.proxy.risk.dto.ManualAuditDataDto;
import com.google.code.simplerule.proxy.risk.entity.ManualAuditDataEntity;
import com.google.code.simplerule.proxy.risk.enums.RiskAuditStatusEnum;

/**
 * @author sunny
 * @since 2013-5-14
 * @version 1.0.0
 * @comment 人工审核数据服务，存储外部传入的数据
 */
@Service
public class ManualAuditDataService {

//	@Resource
//	private ManualAuditDataDao manualAuditDataDao;

	/**
	 * 分页查询
	 * 
	 * @return
	 */
//	public CommonResult<ManualAuditDataEntity> selectPaging(
//			ManualAuditDataEntity entity) {
//		CommonResult<ManualAuditDataEntity> p = new CommonResult<ManualAuditDataEntity>();
//		long start = System.currentTimeMillis();
//		List<ManualAuditDataEntity> list = manualAuditDataDao.selectPaging(entity);
//		long end = System.currentTimeMillis();
//		System.out.println("查询数据时间:" + (end - start));
//		
//		//查询总数设为Long最大数
//		long total = 0L;
//		total = Long.MAX_VALUE;
//
//		
//		p.setRows(list);
//		p.setTotal(total);
//		return p;
//	};
//
//	/**
//	 * 审核操作
//	 * 
//	 * @return
//	 */
//	public Integer manualAudit(ManualAuditDataEntity entity) {
//		// check param
//		if (entity == null || entity.getRowKey() == null
//				|| entity.getRowKey().length() <= 0) {
//			throw new RuntimeException("失败：参数异常(RowKey不能为空)");
//		}
//		if (entity == null || entity.getAuditStatus() == null
//				|| entity.getAuditStatus().length() <= 0) {
//			throw new RuntimeException("失败：参数异常(审核状态不能为空)");
//		}
//		if (entity == null || entity.getAuditBackup() == null
//				|| entity.getAuditBackup().length() <= 0) {
//			throw new RuntimeException("失败：参数异常(审核备注不能为空)");
//		}
//		
//		//update data
//		return updateData(entity);
//	}
//
//	/**
//	 * 插入人工审核数据
//	 * 
//	 * @param entity
//	 * @return
//	 */
//	public Integer insertData(ManualAuditDataEntity entity) {
//		// check param
//		if (entity == null || entity.getInterfaceName() == null
//				|| entity.getInterfaceName().length() <= 0) {
//			throw new RuntimeException("失败：参数异常(接口名不能为空)");
//		}
//		if (entity == null || entity.getRuleNumber() == null
//				|| entity.getRuleNumber().length() <= 0) {
//			throw new RuntimeException("失败：参数异常(规则编号不能为空)");
//		}
//		if (entity == null || entity.getRuleTrack() == null
//				|| entity.getRuleTrack().length() <= 0) {
//			throw new RuntimeException("失败：参数异常(规则执行信息不能为空)");
//		}
//		if (entity == null || entity.getAuditStatus() == null
//				|| entity.getAuditStatus().length() <= 0) {
//			entity.setAuditStatus(String
//					.valueOf(RiskAuditStatusEnum.WaitForAudit.toCode()));
//		}
//
//		return manualAuditDataDao.insert(entity);
//	}
//
//	/**
//	 * 修改人工审核数据
//	 * 
//	 * @param entity
//	 * @return
//	 */
//	public Integer updateData(ManualAuditDataEntity entity) {
//		return manualAuditDataDao.update(entity);
//	}
//
//	/**
//	 * 设置可选属性
//	 * 
//	 * @param entity
//	 * @param dto
//	 */
//	public void setValues(ManualAuditDataEntity entity, ManualAuditDataDto dto) {
//		dto.setRowKey(entity.getRowKey());
//		dto.setInterfaceName(entity.getInterfaceName());
//		dto.setRuleNumber(entity.getRuleNumber());
//		dto.setRuleTrack(entity.getRuleTrack());
//		dto.setAuditStatus(entity.getAuditStatus());
//		dto.setAuditStatusDisplayValue(RiskAuditStatusEnum.enumValueOf(
//				Integer.valueOf(entity.getAuditStatus())).toDescription());
//		dto.setAuditBackup(entity.getAuditBackup());
//		dto.setOperator(entity.getOperator());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		try {
//			dto.setManualAuditDate(sdf.parse(entity.getManualAuditDate()));
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
//
//		Map map = entity.getParam();
//		if (map != null) {
//			Map param = entity.getParam();
//			Set<Entry> set = param.entrySet();
//			Iterator<Entry> iterator = set.iterator();
//			while (iterator.hasNext()) {
//				Entry entry = iterator.next();
//				String key = (String) entry.getKey();
//				String value = (String) entry.getValue();
//				if (key.equalsIgnoreCase("username")) {
//					dto.setUsername(value);
//				} else if (key.equalsIgnoreCase("tel")) {
//					dto.setTel(value);
//				}
//			}
//		}
//	}
}
