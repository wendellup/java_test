package test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MavsFixedThreadPoolExecutor extends MavsThreadPoolExecutor {

    public MavsFixedThreadPoolExecutor(int nThreads) {
        super(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public MavsFixedThreadPoolExecutor(int nThreads, ThreadFactory threadFactory) {
        super(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), threadFactory);
    }

    public MavsFixedThreadPoolExecutor(int nThreads,
            ThreadFactory threadFactory, RejectedExecutionHandler rejectHandler) {
        super(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), threadFactory,
                rejectHandler);
    }
}