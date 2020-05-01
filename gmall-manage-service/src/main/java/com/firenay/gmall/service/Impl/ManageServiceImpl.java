package com.firenay.gmall.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.firenay.gmall.config.RedisUtils;
import com.firenay.gmall.constant.ManageConst;
import com.firenay.gmall.entity.*;
import com.firenay.gmall.mapper.*;
import com.firenay.gmall.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
	private SpuInfoMapper spuInfoMapper;

	@Autowired
	private BaseAttrValueMapper baseAttrValueMapper;

	@Autowired
	private BaseSaleAttrMapper baseSaleAttrMapper;

	@Autowired
	private SpuSaleAttrMapper spuSaleAttrMapper;

	@Autowired
	private SpuImageMapper spuImageMapper;

	@Autowired
	private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

	@Autowired
	private SkuInfoMapper skuInfoMapper;

	@Autowired
	private SkuAttrValueMapper skuAttrValueMapper;

	@Autowired
	private SkuImageMapper skuImageMapper;

	@Autowired
	private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

	@Autowired
	private RedisUtils redisUtils;

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;


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

	/**
	 * http://localhost:520/attrInfoList?catalog3Id=36
	 * [{"id":"113","attrName":"最初的梦想","catalog3Id":"36","attrValueList":null}]
	 * 自定义联合查询
	 */
	@Override
	public List<BaseAttrInfo> getAttrList(String catalog3Id) {
//		return baseAttrInfoMapper.select(new BaseAttrInfo(catalog3Id));
		return baseAttrInfoMapper.getBaseAttrInfoListByCatalog3Id(catalog3Id);
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

	@Override
	public List<SpuInfo> getSpuList(SpuInfo spuInfo) {
		return spuInfoMapper.select(spuInfo);
	}

	@Override
	public List<BaseSaleAttr> getBaseSaleAttrList() {
		return baseSaleAttrMapper.selectAll();
	}

	/**
	 * 保存商品的各个属性
	 */
	@Override
	@Transactional
	public int saveSpuInfo(SpuInfo spuInfo) {
		// 保存数据的具体实现
		spuInfoMapper.insertSelective(spuInfo);

		List<SpuImage> spuImageList = spuInfo.getSpuImageList();
		if(spuImageList != null && spuImageList.size() > 0){
			// 保存图片
			for (SpuImage spuImage : spuImageList) {
				spuImage.setSpuId(spuInfo.getId());
				spuImageMapper.insertSelective(spuImage);
			}
		}

		List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
		if (spuSaleAttrList != null && spuSaleAttrList.size() > 0){
			for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
				spuSaleAttr.setSpuId(spuInfo.getId());
				spuSaleAttrMapper.insertSelective(spuSaleAttr);

				// 保存商品的子属性
				List<SpuSaleAttrValue> attrValueList = spuSaleAttr.getSpuSaleAttrValueList();
				if(attrValueList != null && attrValueList.size() > 0){
					for (SpuSaleAttrValue attrValue : attrValueList) {
						attrValue.setSpuId(spuInfo.getId());
						spuSaleAttrValueMapper.insertSelective(attrValue);
					}
				}
			}
		}
		return 1;
	}

	@Override
	public List<SpuImage> getSpuImageList(SpuImage spuImage) {
		return spuImageMapper.select(spuImage);
	}

	/**
	 *
	 * @SQL内连接： SELECT * FROM spu_sale_attr sav INNER JOIN spu_sale_attr_value ssav
	 * 		ON sav.sale_attr_id = ssav.sale_attr_id  AND  sav.spu_id = ssav.spu_id WHERE sav.spu_id = 68;
	 */
	@Override
	public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {
		return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
	}

	@Override
	@Transactional
	public int saveSkuInfo(SkuInfo skuInfo) {
			// skuInfo 中不为 null 的值全部保存
		skuInfoMapper.insertSelective(skuInfo);
		List<SkuImage> skuImageList = skuInfo.getSkuImageList();
		for (SkuImage image : skuImageList) {
			image.setSkuId(skuInfo.getId());
			skuImageMapper.insertSelective(image);
		}

		// 保存销售属性
		List<SkuAttrValue> attrValueList = skuInfo.getSkuAttrValueList();
		if (attrValueList != null && attrValueList.size() > 0){
			for (SkuAttrValue attrValue : attrValueList) {
				attrValue.setSkuId(skuInfo.getId());
				skuAttrValueMapper.insertSelective(attrValue);
			}
		}

		List<SkuSaleAttrValue> saleAttrValueList = skuInfo.getSkuSaleAttrValueList();
		for (SkuSaleAttrValue saleAttrValue : saleAttrValueList) {
			saleAttrValue.setSkuId(skuInfo.getId());
			skuSaleAttrValueMapper.insertSelective(saleAttrValue);
		}
		return 1;
	}

	/**
	 * 根据SkuId查询所有东西 [搭建了redis + redis分布式锁]
	 */
	@Override
	public SkuInfo getSkuInfo(String skuId) {
		return getSkuInfoRedis(skuId);		//用自己的锁的方式

//		return getSkuInfoRedisson(skuId);
	}

	/**
	 * 利用 redisson 分布式锁
	 */
	private SkuInfo getSkuInfoRedisson(String skuId) {
		SkuInfo skuInfo =null;
		Jedis redis = null;
		RLock nayLock = null;
		try {
			Config config = new Config();
			config.useSingleServer().setAddress("redis://" + host + ":" + port);
			RedissonClient redissonClient = Redisson.create(config);
			// 创建锁
			nayLock = redissonClient.getLock("fireNayLock");
			nayLock.lock(5, TimeUnit.SECONDS);
			// 开始写业务逻辑
			redis = redisUtils.getRedis();
			String skuKey = ManageConst.SKUKEY_PREFIX + skuId + ManageConst.SKUKEY_SUFFIX;
			// 跟自己写的那个一样 判断是否存在 skuKey
			if(redis.exists(skuKey)){
				String skuJson = redis.get(skuKey);
				skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
			}else{
				skuInfo = getSkuInfoDB(skuId);
				redis.setex(skuKey, ManageConst.SKUKEY_TIMEOUT, JSON.toJSONString(skuInfo));
				log.info(skuId + "号缓存添加完毕");
			}
		} catch (Exception e) {
			skuInfo = getSkuInfoDB(skuId);
			System.out.println("======================================================redis挂了======================================================");
		} finally {
			if (redis != null){
				redis.close();
			}
			if (nayLock != null){
				nayLock.unlock();
			}
			return skuInfo;
		}
	}

	/**
	 * 自己的 redis 分布式锁
	 */
	private SkuInfo getSkuInfoRedis(String skuId) {
		SkuInfo skuInfo = null;
		Jedis redis = null;
		try {
			redis = redisUtils.getRedis();
			//		redis.set("lsl","加油！！！");
			String skuKey = ManageConst.SKUKEY_PREFIX + skuId + ManageConst.SKUKEY_SUBFIX;
			String skuJson = redis.get(skuKey);
			if(skuJson == null){
				log.warn("正在添加缓存");
				// sku:skuId:lock
				String skuLockKey = ManageConst.SKUKEY_PREFIX + skuId + ManageConst.SKUKEY_SUFFIX;
				// 对不存在的外键进行设置会返回 OK										nx防止键存在发生冲突	px 为属性设置存活时间
				String lockKey = redis.set(skuLockKey, "加油！！！", SetParams.setParams().nx().px(ManageConst.SKULOCK_EXPIRE_PX));
				if("OK".equals(lockKey)){
					// 此时加锁成功  从DB查询数据转成String设置进去
					skuInfo = getSkuInfoDB(skuId);
					String skuRedis = JSON.toJSONString(skuInfo);
					redis.setex(skuKey, ManageConst.SKUKEY_TIMEOUT, skuRedis);
					// 删除锁 不删除外面的都进不来
					redis.del(skuLockKey);
					log.info(skuId + "号缓存添加完毕");
				} else {
					// 其他的人等一会继续调用
					Thread.sleep(200);
					return getSkuInfo(skuId);
				}
			} else {
				// 有缓存直接转换走 finally
				skuInfo = JSON.parseObject(skuJson, SkuInfo.class);
			}
		} catch (Exception e) {
			skuInfo = getSkuInfoDB(skuId);
			System.out.println("======================================================redis挂了======================================================");
		} finally {
			if(redis != null){
				redis.close();
			}
			return skuInfo;
		}
	}

	/**
	 * 从数据库查询Sku所有信息[包括图片]
	 */
	private SkuInfo getSkuInfoDB(String skuId) {
		// 直接将skuImageList 放入skuInfo 中！
		SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
		skuInfo.setSkuImageList(getSkuImageBySkuId(skuId));
		return skuInfo;
	}

	/**
	 * 根据skuId查询图片
	 */
	@Override
	public List<SkuImage> getSkuImageBySkuId(String skuId) {
		return skuImageMapper.select(new SkuImage(skuId));
	}

	/**
	 * 根据skuInfo查询销售属性集合
	 */
	@Override
	public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {
		return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuInfo.getId(),skuInfo.getSpuId());
	}

	/**
	 * 根据sluId查询销售属性值集合
	 */
	@Override
	public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId) {
		return skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
	}
}
