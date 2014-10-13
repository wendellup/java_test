package test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class MavsThreadPoolExecutor extends ThreadPoolExecutor {
    private static final Logger LOGGER = Logger.getLogger(MavsThreadPoolExecutor.class);

    public MavsThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MavsThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                handler);
    }

    public MavsThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory);
    }

    public MavsThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        LOGGER.info("Thread[" + t.getName() + "]#beforeExecute:{}",
                new RuntimeException(MavsThreadPoolStateMonitor.monitor(this)));
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        LOGGER.info("Thread[" + Thread.currentThread().getName()
                + "]EfterExecute:{}", new RuntimeException(MavsThreadPoolStateMonitor.monitor(this)));
        if (t != null) {
            LOGGER.warn("Worker.runs.task.err", t);
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        LOGGER.info("terminated:{}", new RuntimeException(MavsThreadPoolStateMonitor.monitor(this)));
    }
}
