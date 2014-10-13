package test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class ThreadPoolExecutorExample {
	private static final Logger LOGGER = Logger
			.getLogger(ThreadPoolExecutorExample.class);

	public static void main(String[] args) throws Exception {
		// 固定2个线程的线程池
		MavsFixedThreadPoolExecutor fixedThreadPoolExecutor1 = new MavsFixedThreadPoolExecutor(
				2, new MavsThreadFactory("Example", "FixedThreadPool-1"),
				new MavsRejectedExecutionPolicy());
		// 从线程池的状态监视器来看:此时poolSize=1/workQueueSize=0,即启动了一个线程,工作队列没有任务
		fixedThreadPoolExecutor1.execute(new ThreadPoolTask());
		// 从线程池的状态监视器来看:此时poolSize=2/workQueueSize=0,即又启动了一个线程
		fixedThreadPoolExecutor1.execute(new ThreadPoolTask());

		// 由提交了3个任务,从输出来看:poolSize一直为2.而workQueueSize最多为3->随着任务的执行,workQueueSize变为0
		// 所以MavsFixedThreadPoolExecutor这个线程池会保持固定线程数量
		fixedThreadPoolExecutor1.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor1.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor1.execute(new ThreadPoolTask());

		// 执行shutdown
		// 另外从输出看:发现线程池终止的时候调用了terminate方法
		fixedThreadPoolExecutor1.shutdown();

		Thread.sleep(1 * 1000);

		// 测试shutdown后,还可以执行任务吗?
		// 答案当然是NO.因为新建worker线程的条件包括插入队列都必须是在RUNNING状态下.
		// 而执行了shutdown后则更改了运行状态为SHUTDOWN
		fixedThreadPoolExecutor1.execute(new ThreadPoolTask());

		Thread.sleep(1 * 1000);
		LOGGER.debug("");

		// cached线程池
		MavsCachedThreadPoolExecutor cachedThreadPoolExecutor1 = new MavsCachedThreadPoolExecutor(
				new MavsThreadFactory("Example", "CachedThreadPool-1"),
				new MavsRejectedExecutionPolicy());

		// 从输出可以看到,线程池最多启动了5个线程,workQueueSize一直为0
		cachedThreadPoolExecutor1.execute(new ThreadPoolTask());
		cachedThreadPoolExecutor1.execute(new ThreadPoolTask());
		cachedThreadPoolExecutor1.execute(new ThreadPoolTask());
		cachedThreadPoolExecutor1.execute(new ThreadPoolTask());
		cachedThreadPoolExecutor1.execute(new ThreadPoolTask());

		// 暂停2分钟,使得默认空闲1分钟的worker线程退出
		Thread.sleep(2 * 60 * 1000);

		LOGGER.debug("");

		// 从输出可以看到:poolSize=0,即空闲的worker线程被回收了.
		// 另外所有的worker线程被回收了,线程池就结束了.
		// 因为:ThreadPoolExecutor#void workerDone(Worker w)->
		// if (--poolSize ==0)tryTerminate()
		// 但是这种线程自然结束的话,并没有调用覆写的terminate方法.因为tryTerminate的实现中是判断当前线程池状态是STOP/SHUTDOWN的时候才执行terminated方法的
		LOGGER.debug("cachedThreadPoolExecutor1.state:{}"+
				MavsThreadPoolStateMonitor.monitor(cachedThreadPoolExecutor1));

		// 单线程线程池,注意这个和{@link
		// Executors#newSingleThreadExecutor的区别},后者仅是返回的暴露的ExecutorService接口
		MavsFixedThreadPoolExecutor singleExecutor = new MavsFixedThreadPoolExecutor(
				1, new MavsThreadFactory("Example", "SingleThreadPool-1"),
				new MavsRejectedExecutionPolicy());
		// 提交一个可抛出异常的任务
		// 从输出看出
		// 1:执行了afterExecute方法且其中的Throwable t为不null.此执行任务的时候抛出了异常.
		// 2.线程因为异常终止,因指定了线程默认的UncaughtExceptionHandler,所以执行了uncaughtException方法.
		singleExecutor.execute(new ThreadPoolExceptionTask());
		Thread.sleep(1 * 1000);
		// 从输出可以看到:poolSize=0变为了0.即线程终止了.
		// 因为Worker线程的run方法只是try/finally,即并没有捕获异常.而runTask向上抛出异常至run,直接到finally.->workerDone->poolSize--
		// ->tryTerminate
		LOGGER.debug("singleExecutor.state:{}"+
				MavsThreadPoolStateMonitor.monitor(singleExecutor));

		Thread.sleep(1 * 1000);
		// 测试线程池异常终止后,还可以执行任务吗?
		// 答案是YES.因为此时的线程池状态依然是RUNNING.
		singleExecutor.execute(new ThreadPoolTask());
		Thread.sleep(1 * 1000);
		// 从输出发现:poolSize=1,即新增了一个worker线程.另外从线程的名字Mavs-Example-SingleThreadPool-1-2也可看得出.
		LOGGER.debug("singleExecutor.state:{}"+
				MavsThreadPoolStateMonitor.monitor(singleExecutor));

		// 这里是提交了一个任务,内部会被封装成->RunnableFuture->FutureTask
		// 而其内部run->Sync#innerRun->其内部会被try/catch的->所以理论上结果应该线程应该不会异常终止.
		// 从输出看:1.afterExecute方法中的异常参数为null.
		// 2.没用调用默认的UncaughtExceptionHandler.也就是说线程正常运行.
		singleExecutor.submit(new ThreadPoolExceptionTask());

		singleExecutor.shutdown();

		// 测试setCoreSize以及setMaximumSize

		// 3个固定线程数目的线程池
		MavsFixedThreadPoolExecutor fixedThreadPoolExecutor2 = new MavsFixedThreadPoolExecutor(
				3, new MavsThreadFactory("Example", "FixedThreadPool-2"),
				new MavsRejectedExecutionPolicy());

		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());

		// 设置核心线程大小为6.
		fixedThreadPoolExecutor2.setCorePoolSize(6);

		Thread.sleep(1 * 1000);
		// 从输出看:poolSize=6
		LOGGER.debug("fixedThreadPoolExecutor2.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor2));

		// 设置核心线程大小为2
		fixedThreadPoolExecutor2.setCorePoolSize(2);
		Thread.sleep(1 * 1000);
		// 从输出看.poolSize=6
		// 因为 workQueue.remainingCapacity()此时不为0,即不会中断多余的空闲线程.
		// 另外此时所有的worker线程正在处在等待状态.
		LOGGER.debug("fixedThreadPoolExecutor2.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor2));

		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		Thread.sleep(1 * 1000);
		// 从输出可以看到:此时poolSize=5.因为某个等待线程获得执行机会后再次getTask后->会执行pool(keepAliveTime),则直接回收退出.
		LOGGER.debug("fixedThreadPoolExecutor2.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor2));

		// 继续执行3个任务
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor2.execute(new ThreadPoolTask());
		Thread.sleep(1 * 1000);
		// 从输出可以发现:此时poolSize=2,因为多余的线程在执行完任务下次getTask判断的时候直接就被回收了.
		// 另外:此时maximumSize是3.coreSize为2.也就是说此时的线程池已经不再是固定数量线程的线程池了.
		LOGGER.debug("fixedThreadPoolExecutor2.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor2));

		fixedThreadPoolExecutor2.shutdown();

		// 测试setMaximumPoolSize
		// 2个固定线程数目的线程池
		MavsFixedThreadPoolExecutor fixedThreadPoolExecutor3 = new MavsFixedThreadPoolExecutor(
				2, new MavsThreadFactory("Example", "FixedThreadPool-3"),
				new MavsRejectedExecutionPolicy());

		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());

		Thread.sleep(1 * 1000);
		// 设置最大线程池大小为4
		fixedThreadPoolExecutor3.setMaximumPoolSize(4);

		// 提交一系列任务
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());
		fixedThreadPoolExecutor3.execute(new ThreadPoolTask());

		Thread.sleep(1 * 1000);
		// 从输出看:maximumPoolSize=4/poolSize=2
		// 即只是修改了maximumPoolSize的值/poolSize仍然为2.因为用的是无限阻塞队列,所以多余的任务都被放到了队列.
		LOGGER.debug("fixedThreadPoolExecutor3.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor3));
		try {
			// 这里抛出了一个异常,因为1比coreSize 2还要小
			fixedThreadPoolExecutor3.setMaximumPoolSize(1);
		} catch (Exception e) {
			LOGGER.warn("fixedThreadPoolExecutor3.setMaximumPoolSize.err.", e);
		}

		fixedThreadPoolExecutor3.shutdown();

		// 自定义线程池1
		// 工作队列为容量3的阻塞队列
		// 等待空闲时间为60s
		ThreadPoolExecutor userDefinedExecutor1 = new ThreadPoolExecutor(2, 4,
				10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3),
				new MavsThreadFactory("Example", "User-Define-Executor-1"),
				new MavsRejectedExecutionPolicy());

		// 直接提交很多任务
		// 这个测试的目的在于测试拒绝策略.从输出可以看到:
		// poolSize=4/workQueueSize=3这个时候，即已经达到了最大线程数目和队列上限,则执行了拒绝策略.
		for (int i = 0; i < 20; i++) {
			userDefinedExecutor1.execute(new ThreadPoolTask());
		}

		Thread.sleep(5 * 1000);
		LOGGER.debug("userDefinedExecutor1.state:{}"+
				MavsThreadPoolStateMonitor.monitor(userDefinedExecutor1));
		// 将线程池最大池数目调整为3.此时的poolSize为4.
		userDefinedExecutor1.setMaximumPoolSize(3);
		Thread.sleep(1 * 1000);
		// 从输出看:poolSize还是为4.因为此时所有的worker线程都在poll(timeout)->然后setMaximumPoolSize->会中断一个空闲线程->但是getTask这里
		// 被try/catch了.
		// 不过多余的线程在空闲的时候都会被回收.
		LOGGER.debug("userDefinedExecutor1.state:{}"+
				MavsThreadPoolStateMonitor.monitor(userDefinedExecutor1));
		Thread.sleep(5 * 1000);
		LOGGER.debug("userDefinedExecutor1.state:{}"+
				MavsThreadPoolStateMonitor.monitor(userDefinedExecutor1));

		userDefinedExecutor1.shutdown();

		// 测试prestartCoreThread()/prestartAllCoreThreads
		MavsFixedThreadPoolExecutor fixedThreadPoolExecutor4 = new MavsFixedThreadPoolExecutor(
				3, new MavsThreadFactory("Example", "FixedThreadPool-4"),
				new MavsRejectedExecutionPolicy());

		LOGGER.debug("fixedThreadPoolExecutor4.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor4));
		// 启动一个核心线程
		fixedThreadPoolExecutor4.prestartCoreThread();
		// 从输出可以看出:poolSize为1，即启动了一个worker.
		LOGGER.debug("fixedThreadPoolExecutor4.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor4));
		// 启动所有核心线程
		// 从输出可以看出:poolSize为3,即现在启动了所有的核心线程
		fixedThreadPoolExecutor4.prestartAllCoreThreads();
		LOGGER.debug("fixedThreadPoolExecutor4.state:{}"+
				MavsThreadPoolStateMonitor.monitor(fixedThreadPoolExecutor4));
	}

	/**
	 * 
	 * 用于测试的线程池任务
	 * 
	 * @author landon
	 * 
	 */
	private static class ThreadPoolTask implements Runnable {
		private static final AtomicInteger COUNTER = new AtomicInteger(1);

		private int id;

		public ThreadPoolTask() {
			id = COUNTER.getAndIncrement();
		}

		@Override
		public void run() {
			LOGGER.debug(this + " begin");

			try {
				TimeUnit.MICROSECONDS.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.warn(this + " was interrupted", e);
			}

			LOGGER.debug(this + " end");
		}

		@Override
		public String toString() {
			return "ThreadPoolTask [id=" + id + "]" + "["
					+ Thread.currentThread().getName() + "]";
		}
	}

	/**
	 * 
	 * 用于测试的线程池异常任务
	 * 
	 * @author landon
	 * 
	 */
	private static class ThreadPoolExceptionTask implements Runnable {
		private static final AtomicInteger COUNTER = new AtomicInteger(1);

		private int id;

		public ThreadPoolExceptionTask() {
			id = COUNTER.getAndIncrement();
		}

		@Override
		public void run() {
			LOGGER.debug(this + " begin");

			throw new RuntimeException("ThreadPoolExceptionTask.Exception.");
		}

		@Override
		public String toString() {
			return "ThreadPoolExceptionTask [id=" + id + "]" + "["
					+ Thread.currentThread().getName() + "]";
		}
	}
}