package cn.egame.common.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SEGameExecutorService {
	public static int NTHREADS = 5;
	private static SEGameExecutorService instance = null;
	public static synchronized SEGameExecutorService getInstance() {
		if (instance == null)
			instance = new SEGameExecutorService();
		return instance;
	}

	private ExecutorService pools = null;

	public SEGameExecutorService() {
		pools = Executors.newFixedThreadPool(NTHREADS);
	}

	public void close() {
		if (pools != null)
			pools.shutdownNow();
	}

	public void execute(Runnable run) {
		pools.execute(run);
	}

	@Override
	protected void finalize() {
		close();
	}
}
