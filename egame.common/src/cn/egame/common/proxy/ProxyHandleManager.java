/**
 * 
 */
package cn.egame.common.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Description TODO
 * 
 * @ClassName ProxyHandleManager
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author zhangjb
 * 
 * @Create Date Mar 4, 2015
 * 
 * @Modified by none
 * 
 * @Modified Date
 */

public class ProxyHandleManager {

    private Logger logger = Logger.getLogger(ProxyHandleManager.class);
    private static Map<String, IProxyHandle> proxyHandleMap = new HashMap<String, IProxyHandle>();
    private static List<IProxyHandle> proxyHandles = new ArrayList<IProxyHandle>();
    private volatile static ProxyHandleManager instance = null;

    static public ProxyHandleManager getInstance() {
        if (instance == null) {
                if (instance == null) {
                    instance = new ProxyHandleManager();
                }
        }
        return instance;
    }

    private ProxyHandleManager() {

    }

    /**
     * @Description: 注册事件
     * @param handleType
     *            事件类型
     * @param proxyHandle
     *            事件接口
     * @return void
     */
    public void registerEvent(String handleType, IProxyHandle proxyHandle) {
        try {
            proxyHandleMap.put(handleType, proxyHandle);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @Description: 列出所有事件执行接口
     * @return
     * @return List<IProxyHandle>
     */
    public List<IProxyHandle> returnAllHandle() {
        try {
            for (String handleType : proxyHandleMap.keySet()) {
                proxyHandles.add(proxyHandleMap.get(handleType));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return proxyHandles;
    }

    /**
     * @Description: 移除某一事件 
     * @param handleType   
     * @return void
     */
    public void removeEvent(String handleType) {
        try {
            proxyHandleMap.remove(handleType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @Description: 获取某一事件执行接口 
     * @param handleType
     * @return   
     * @return IProxyHandle
     */
    public IProxyHandle getProxyHandle(String handleType) {
        IProxyHandle proxy = null;
        try {
            proxy = proxyHandleMap.get(handleType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return proxy;
    }
}
