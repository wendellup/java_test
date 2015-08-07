package cn.egame.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ExceptionCommonBase;

public class EgameRmiProxy implements InvocationHandler {

	private Object target;
    private Logger logger = Logger.getLogger(EgameRmiProxy.class);
    private String logInfoStr = "%1$d %2$d %3$s appid:%4$s uid:%5$s req_params:%6$s ";

    public EgameRmiProxy(Object target) throws RemoteException {
        this.target = target;
    }

    public String getFormatTime(long timeMillis) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date = new Date(timeMillis);

        return sdf.format(date);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long start = System.currentTimeMillis();
        int errorCode = 200;
        // 执行业务方法
        Object result;
        try {
            result = method.invoke(this.target, args);
        } catch (Exception e) {
            if (e.getCause() instanceof ExceptionCommonBase) {
                ExceptionCommonBase ex = (ExceptionCommonBase) e.getCause();
                errorCode = ex.getErrorCode();
                throw ex;
            } else {
                throw e;
            }
        } finally {
            long end = System.currentTimeMillis();
            // 记录日志
            long respTime = end - start;
            String appid;
            String uid;
            String reqparams = "";
            String flag = "";
            int argsLen = args == null ? 0 : args.length;
            if (argsLen == 0) {

                appid = "-1";
                uid = "-1";
                reqparams = "null";
            }  else {
                if (argsLen > 1) {
                    appid = args[0].toString();
                    uid = args[1].toString();
                } else {
                    appid = args[0].toString();
                    uid = "-1";
                }
                for (Object arg : args) {
                    reqparams += flag;
                    reqparams += (arg == null ? "null" : arg.toString());
                    flag = "|";
                }
            }

            logger.info(String.format(logInfoStr, respTime, errorCode, method.getName(), appid, uid, reqparams));
        }

        return result;
    }

    public static <T> T getProxyInstance(T target) throws RemoteException {
        Class targetClass = target.getClass();

        ClassLoader loader = targetClass.getClassLoader();
        Class[] interfaces = targetClass.getInterfaces();
        EgameRmiProxy handler = new EgameRmiProxy(target);

        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }

}
