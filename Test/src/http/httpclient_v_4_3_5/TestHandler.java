package cn.egame.server.open.biz.v2.factory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.egame.client.EGameClientExt;
import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.servlet.WebUtils;
import cn.egame.common.servlet.model.MethodType;
import cn.egame.common.util.Utils;
import cn.egame.server.open.biz.v2.ThreadPoolManager;
import cn.egame.server.open.biz.v2.util.CommonUtils;

public class TestHandler {
    private static final Logger LOGGER = Logger.getLogger(TestHandler.class);

    public static ICacheClient getGameCache() {
        return SCacheClient.getInstance("egame");
    }

    public static ICacheClient getDataSupportCache() {
        return SCacheClient.getInstance("egame_data_support");
    }

    public static Object testIncrBy(HttpServletRequest req, HttpServletResponse resp, MethodType method, String[] path, Object token)
            throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String cacheKey = "currentAdvKey-8104";
                long methodCount = Utils.toLong(getGameCache().incrBy(cacheKey, 1), 1);
            }
        });
        
        ThreadPoolManager.threadPool.execute(thread);
        return "ok";
        
    }
    
    public static Object testGetInfoByIP(HttpServletRequest req, HttpServletResponse resp, MethodType method, String[] path, Object token)
            throws IOException {
        int provinceId = EGameClientExt.getInstance().getProvinceIdByIp(0, 0,
                WebUtils.getString(req, "ip"));
        return provinceId;
    }
    
    public static Object testBlackImsi(HttpServletRequest req, HttpServletResponse resp, MethodType method, String[] path, Object token)
            throws IOException {
        int provinceId = EGameClientExt.getInstance().getProvinceIdByIp(0, 0,
                WebUtils.getString(req, "ip"));
        return provinceId;
    }
    
    public static Object justTest(HttpServletRequest req, HttpServletResponse resp, MethodType method, String[] path, Object token)
            throws IOException {
        return CommonUtils.getNetWork(req);
    }
}
