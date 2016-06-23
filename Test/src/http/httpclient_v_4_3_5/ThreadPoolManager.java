package http.httpclient_v_4_3_5;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    
    //处理回调及记录日志的线程池
    private static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(100000);
    public static ThreadPoolExecutor threadPool =  new ThreadPoolExecutor(
            5,
            5,
            500, TimeUnit.SECONDS, queue,
            new ThreadPoolExecutor.CallerRunsPolicy());
    
}
