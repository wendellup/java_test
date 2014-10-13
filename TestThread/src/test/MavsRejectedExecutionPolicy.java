package test;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * 
 * Mavs线程池拒绝执行策略
 * 
 * @author landon
 * 
 */
public class MavsRejectedExecutionPolicy implements RejectedExecutionHandler {
    private static final Logger LOGGER = Logger
            .getLogger(MavsRejectedExecutionPolicy.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        LOGGER.debug("task rejectedExecution.ThreadPool.state:{}"+
                MavsThreadPoolStateMonitor.monitor(executor));
    }
}
