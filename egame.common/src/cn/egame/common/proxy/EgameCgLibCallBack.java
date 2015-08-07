/**
 * 
 */
package cn.egame.common.proxy;

import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ExceptionCommonBase;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Description TODO
 * 
 * @ClassName EgameCgLibCallBack
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author zhangjb
 * 
 * @Create Date Jan 16, 2015
 * 
 * @Modified by none
 * 
 * @Modified Date
 */

public class EgameCgLibCallBack implements MethodInterceptor {

    private Logger logger = Logger.getLogger(EgameCgLibCallBack.class);
    //private String logInfoStr = "%1$d %2$d %3$s appid:%4$s uid:%5$s req_params:%6$s";
    private static IProxyHandle checkMethodHandle = ProxyHandleManager.getInstance().getProxyHandle(ProxyHandleType.CHECK_METHOD);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        int errorCode = 200;
        Object result = null;
        String appid = "-1";
        String uid = "-1";
        long exeTime = 0L;
        StringBuilder param = new StringBuilder();
        long beginTime = Calendar.getInstance().getTimeInMillis();
        if (checkMethodHandle != null) {
            boolean flag = checkMethodHandle.checkMethod(args, method.getName());
            if (!flag) {
                throw new ExceptionCommonBase(-1, "The Method forbid for a time for your app");
            }
        }
        try {
            result = proxy.invokeSuper(obj, args);
            long endTime = Calendar.getInstance().getTimeInMillis();
            exeTime = endTime - beginTime;
            if (args != null) {
                if (args.length > 1) {
                    appid = args[0].toString();
                    uid = args[1].toString();
                }
                for (Object arg : args) {
                    param.append(arg == null ? null : arg.toString()).append(" | ");
                }
            }
        } catch (Exception e) {
            if (e.getCause() instanceof ExceptionCommonBase) {
                ExceptionCommonBase ex = (ExceptionCommonBase) e.getCause();
                errorCode = ex.getErrorCode();
            }
            logger.error(e.getMessage());
        }
        StringBuilder logInfo = new StringBuilder();
        logInfo.append(exeTime).append(" ").append(errorCode).append(" ").append(method.getName()).append(" ");
        logInfo.append("appId:").append(appid).append(" ").append("uid:").append(uid).append(" param:").append(param);
        //logger.info(String.format(logInfoStr, exeTime, errorCode, method.getName(), appid, uid, param));
        logger.info(logInfo);
        return result;
    }
}
