package cache.redis;

import java.io.Serializable;

public class EGamePushCacheKey implements Serializable {

	private static final long serialVersionUID = -2594042228196817005L;
	
	private static final String root = "PUSH-";
    
    // 通用黑城市
    public static String listPushCommonBlackCitys() {
        return root + "listCommonBlackCitys";
    }
    
}
