package com.firenay.gmall.constant;

/**
 * <p>Title: ManageConst</p>
 * Description：
 * date：2020/5/1 10:23
 */
public class ManageConst {

	public static final String SKUKEY_PREFIX = "sku:";

	public static final String SKUKEY_SUBFIX = ":info";

	/**
	 * 缓存存活时间	默认为 1 天
	 */
	public static final int SKUKEY_TIMEOUT = 24 * 60 * 60;

	/**
	 * 锁的存活时间 10秒
	 */
	public static final int SKULOCK_EXPIRE_PX = 1000;

	public static final String SKUKEY_SUFFIX = ":lock";
}
