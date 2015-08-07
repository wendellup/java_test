package cn.egame.common.operation.factory;

import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.operation.ResultHealth;

public class CheckCacheHealth{
	String cacheName;

	public CheckCacheHealth(String cacheName) {
		this.cacheName = cacheName;
	}

	public ResultHealth checkHealth() {
		ResultHealth result = new ResultHealth();
		result.setCode(1);
		result.setName("cache_" + cacheName);

		ICacheClient cacheClient = SCacheClient.getInstance(cacheName);
		cacheClient.set("check_cache_" + cacheName, "check_cache_" + cacheName);
		String value = cacheClient.getString("check_cache_" + cacheName);
		if (!("check_cache_" + cacheName).equals(value)) {
			result.setCode(0);
		}
		return result;
	}


}
