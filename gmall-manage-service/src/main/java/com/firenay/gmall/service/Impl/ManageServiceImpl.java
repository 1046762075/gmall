package com.firenay.gmall.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.firenay.gmall.entity.BaseAttrInfo;
import com.firenay.gmall.entity.BaseAttrValue;
import com.firenay.gmall.entity.BaseCatalog1;
import com.firenay.gmall.entity.BaseCatalog2;
import com.firenay.gmall.entity.BaseCatalog3;
import com.firenay.gmall.mapper.BaseAttrInfoMapper;
import com.firenay.gmall.mapper.BaseAttrValueMapper;
import com.firenay.gmall.mapper.BaseCatalog1Mapper;
import com.firenay.gmall.mapper.BaseCatalog2Mapper;
import com.firenay.gmall.mapper.BaseCatalog3Mapper;
import com.firenay.gmall.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: ManageServiceImpl</p>
 * Description：一二三级分类的实现类
 * date：2020/4/27 16:44
 */
@Service
@Slf4j
public class ManageServiceImpl implements ManageService {

	@Autowired
	private BaseCatalog1Mapper baseCatalog1Mapper;

	@Autowired
	private BaseCatalog2Mapper baseCatalog2Mapper;

	@Autowired
	private BaseCatalog3Mapper baseCatalog3Mapper;

	@Autowired
	private BaseAttrInfoMapper baseAttrInfoMapper;

	@Autowired
	private BaseAttrValueMapper baseAttrValueMapper;


	@Override
	public List<BaseCatalog1> getBaseCatalog1() {
		return baseCatalog1Mapper.selectAll();
	}

	@Override
	public List<BaseCatalog2> getBaseCatalog2(String catalog1Id) {
		return baseCatalog2Mapper.select(new BaseCatalog2(catalog1Id));
	}

	@Override
	public List<BaseCatalog3> getBaseCatalog3(String catalog2Id) {
		return baseCatalog3Mapper.select(new BaseCatalog3(catalog2Id));
	}

	@Override
	public List<BaseAttrInfo> getAttrList(String catalog3Id) {
		return baseAttrInfoMapper.select(new BaseAttrInfo(catalog3Id));
	}

	/**
	 * 这里需要保存多张表
	 */
	@Override
	@Transactional
	public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {

		// 里面有数据的情况下
		if (baseAttrInfo.getId() != null){
			log.info("修改数据：" + baseAttrInfo);
			baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
		}else {
			log.info("插入数据：" + baseAttrInfo);
			// 保存数据 baseAttrInfo
			baseAttrInfoMapper.insertSelective(baseAttrInfo);
		}

		// 先清空数据，在插入到数据即可！
		// 清空数据的条件 根据 attrId 把所有相关的数据删了 后面在插入
		BaseAttrValue baseAttrValueDel = new BaseAttrValue();
		baseAttrValueDel.setAttrId(baseAttrInfo.getId());
		baseAttrValueMapper.delete(baseAttrValueDel);

		List<BaseAttrValue> attrValues = baseAttrInfo.getAttrValueList();
		if(attrValues != null && attrValues.size() > 0){
			// 循环保存前台所有的数据
			for (BaseAttrValue attrValue : attrValues) {
				// 获取管理员当前选择的 BaseCatalog 的id
				attrValue.setAttrId(baseAttrInfo.getId());
				baseAttrValueMapper.insertSelective(attrValue);
			}
		}
	}

	/**
	 * 数据的回显
	 */
	@Override
	public List<BaseAttrValue> getAttrValueList(String attrId) {
		// 显示这个 id 下面的所有数据供管理员修改
		List<BaseAttrValue> BaseAttrValueList = baseAttrValueMapper.select(new BaseAttrValue(attrId));
		return BaseAttrValueList;
	}

	@Override
	public BaseAttrInfo getAttrInfo(String attrId) {
		BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

		// 将这个id在 平台中所有商品的集合放入平台属性
		List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.select(new BaseAttrValue(attrId));
		baseAttrInfo.setAttrValueList(baseAttrValueList);
		return baseAttrInfo;
	}
}
