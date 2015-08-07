package cn.egame.common.event;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import sun.misc.Queue;
import cn.egame.common.cache.factory.MemCacheClient;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

public class EGameEventBase implements Runnable {
	protected Logger logger = Logger.getLogger(EGameEventBase.class
			.getSimpleName());

	private Queue queue = new Queue();
	private Thread thread = null;
	private Object SyncRoot = new Object();

	public class EventStack {
		public EventStack(EGameEventBase instance, String method, Object[] args)
				throws ExceptionCommonBase {
			this.instance = instance;
			this.method = method;
			if (args == null || args.length < 1)
				throw new ExceptionCommonBase(ErrorCodeBase.ParameterError);
			this.args = args;
			this.args[this.args.length - 1] = true;
		}

		public String getMethod() {
			return method;
		}

		public Object[] getArgs() {
			return args;
		}

		private String method = null;
		private Object[] args = null;
		private EGameEventBase instance = null;

		public EGameEventBase getInstance() {
			return instance;
		}

		public void setInstance(EGameEventBase instance) {
			this.instance = instance;
		}
	}

	private Method getMethod(EGameEventBase instance, String method) {
		Class clazz = instance.getClass();
		Method[] ms = clazz.getDeclaredMethods();
		for (Method m : ms) {
			if (Utils.stringCompare(m.getName(), method))
				return m;
		}
		return null;
	}

	public void enqueue(String method, Object[] args)
			throws ExceptionCommonBase {
		if (args == null)
			return;

		EventStack stack = new EventStack(this, method, args);
		queue.enqueue(stack);
		if (thread == null)
			synchronized (SyncRoot) {
				if (thread == null) {
					thread = new Thread(this);
					thread.start();
				}
			}
	}

	@Override
	public void run() {
		try {
			while (!queue.isEmpty()) {
				try {
					EventStack item = (EventStack) queue.dequeue();
					Method method = this.getMethod(item.getInstance(),
							item.getMethod());
					method.invoke(item.getInstance(), item.getArgs());
				} catch (Throwable e) {
					logger.info(null, e);
				}
			}
		} catch (Throwable e) {

		} finally {
			thread = null;
		}
	}

	public void test(int a, int b, boolean sync) throws ExceptionCommonBase {
		if (!sync) {
			Object[] args = new Object[] { a, b, sync };
			this.enqueue("test", args);
			return;
		}

		System.out.println(a + ":" + b);
	}

	public static void main(String[] args) {

		try {

			EGameEventBase b = new EGameEventBase();
			b.test(1, 2, false);
			while (true) {
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
