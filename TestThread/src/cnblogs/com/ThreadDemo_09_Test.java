package cnblogs.com;

/* ===================================================================================
 * 文件：ThreadDemo09.java
 * 描述：线程的中断
 * ===================================================================================
 */
class ThreadA extends Thread {

	private Thread thdOther;

	ThreadA(Thread thdOther) {
		this.thdOther = thdOther;
	}

	public void run() {

		System.out.println(getName() + " 运行...");

		int sleepTime = (int) (Math.random() * 10000);
		System.out.println(getName() + " 睡眠 " + sleepTime + " 毫秒。");

		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}

		System.out.println(getName() + " 觉醒，即将中断线程 B。");
		// 中断线程B,线程Ｂ暂停运行
		thdOther.interrupt();
	}
}

class ThreadB extends Thread {
	int count = 0;

	public void run() {

		System.out.println(getName() + " 运行...");

		while (!this.isInterrupted()) {
			System.out.println(getName() + " 运行中 " + count++);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				int sleepTime = (int) (Math.random() * 10000);
				System.out.println(getName() + " 睡眠" + sleepTime
						+ " 毫秒。觉醒后立即运行直到终止。");

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException m) {
				}

				System.out.println(getName() + " 已经觉醒，运行终止...");
				// 重新设置标记，继续运行
				this.interrupt();
			}
		}

		System.out.println(getName() + " 终止。");
	}
}

class ThreadDemo_09_Test {
	public static void main(String argv[]) {
		ThreadB thdb = new ThreadB();
		thdb.setName("ThreadB");

		ThreadA thda = new ThreadA(thdb);
		thda.setName("ThreadA");

		thdb.start();
		thda.start();
	}
}
/*
 * 　　运行以上程序，你可以清楚地看到中断的过程。首先线程Ｂ开始运行，接着运行线程Ａ，在线程Ａ睡眠一段时间觉醒后，调用interrupt()方法中断线程Ｂ，
 * 此是可能Ｂ正在睡眠，觉醒后掏出一个InterruptedException异常，执行其中的语句，为了更清楚地看到线程的中断恢复，
 * 我在InterruptedException异常后增加了一次睡眠
 * ，当睡眠结束后，线程Ｂ调用自身的interrupt()方法恢复中断，这时测试isInterrupt()返回true,线程退出。 线程和进程(Threads
 * and Processes) 第一个关键的系统级概念，究竟什么是线程或者说究竟什么是进程？她们其实就是操作系统内部的一种数据结构。
 * 进程数据结构掌握着所有与内存相关的东西
 * ：全局地址空间、文件句柄等等诸如此类的东西。当一个进程放弃执行（准确的说是放弃占有CPU），而被操作系统交换到硬盘上，
 * 使别的进程有机会运行的时候，在那个进程里的所有数据也将被写到硬盘上，甚至包括整个系统的核心（core
 * memory）。可以这么说，当你想到进程（process），就应该想到内存（memory） （进程 ==
 * 内存）。如上所述，切换进程的代价非常大，总有那么一大堆的内存要移来移去
 * 。你必须用秒这个单位来计量进程切换（上下文切换），对于用户来说秒意味着明显的等待和硬盘灯的狂闪
 * （对于作者的我，就意味着IBM龙腾3代的烂掉，5555555）
 * 。言归正传，对于Java而言，JVM就几乎相当于一个进程（process），因为只有进程才能拥有堆内存
 * （heap，也就是我们平时用new操作符，分出来的内存空间）。 那么线程是什么呢？你可以把它看成“一段代码的执行”----
 * 也就是一系列由JVM执行的二进制指令
 * 。这里面没有对象（Object）甚至没有方法(Method)的概念。指令执行的序列可以重叠，并且并行的执行。后面，我会更加详细的论述这个问题
 * 。但是请记住，线程是有序的指令，而不是方法（method）。
 * 线程的数据结构，与进程相反，仅仅只包括执行这些指令的信息。它包含当前的运行上下文（context
 * ）：如寄存器（register）的内容、当前指令的在运行引擎的指令流中的位置
 * 、保存方法（methods）本地参数和变量的运行时堆栈。如果发生线程切换，OS只需把寄存器的值压进栈
 * ，然后把线程包含的数据结构放到某个类是列表(LIST)的地方
 * ；把另一个线程的数据从列表中取出，并且用栈里的值重新设置寄存器。切换线程更加有效率，时间单位是毫秒。对于Java而言，一个线程可以看作是JVM的一个状态。
 * 运行时堆栈
 * （也就是前面说的存储本地变量和参数的地方）是线程数据结构一部分。这是因为多个线程，每一个都有自己的运行时堆栈，也就是说存储在这里面的数据是绝对线程安全
 * （后面将会详细解释这个概念
 * ）的。因为可以肯定一个线程是无法修改另一个线程的系统级的数据结构的。也可以这么说一个不访问堆内存的（只读写堆栈内存）方法，是线程安全的（Thread
 * Safe）。 线程安全和同步
 * 线程安全，是指一个方法（method）可以在多线程的环境下安全的有效的访问进程级的数据（这些数据是与其他线程共享的）。事实上，线程安全是个很难达到的目标。
 * 线程安全的核心概念就是同步，它保证多个线程： 同时开始执行，并行运行 不同时访问相同的对象实例 不同时执行同一段代码
 * 我将会在后面的章节，一一细诉这些问题。但现在还是让我们来看看同步的一种经典的
 * 实现方法——信号量。信号量是任何可以让两个线程为了同步它们的操作而相互通信的对象。Java也是通过信号量来实现线程间通信的。
 * 不要被微软的文档所暗示的信号量仅仅是Dijksta提出的计数型信号量所迷惑。信号量其实包含任何可以用来同步的对象。
 * 如果没有synchronized关键字，就无法用JAVA实现信号量，但是仅仅只依靠它也不足够。我将会在后面为大家演示一种用Java实现的信号量。
 * 同步的代价很高哟！ 同步（或者说信号量，随你喜欢啦）的一个很让人头痛的问题就是代价。考虑一下，下面的代码：
 * 
 * Listing 1.2: import java.util.*; import java.text.NumberFormat; class Synch {
 * private static long[ ] locking_time = new long[100]; private static long[ ]
 * not_locking_time = new long[100]; private static final long ITERATIONS =
 * 10000000; synchronized long locking (long a, long b){return a + b;} long
 * not_locking (long a, long b){return a + b;}
 * 
 * private void test( int id ) { long start = System.currentTimeMillis();
 * for(long i = ITERATIONS; --i >= 0 ;) { locking(i,i); } locking_time[id] =
 * System.currentTimeMillis() - start; start = System.currentTimeMillis();
 * for(long i = ITERATIONS; --i >= 0 ;) { not_locking(i,i); }
 * not_locking_time[id] = System.currentTimeMillis() - start; } static void
 * print_results( int id ) { NumberFormat compositor =
 * NumberFormat.getInstance(); compositor.setMaximumFractionDigits( 2 ); double
 * time_in_synchronization = locking_time[id] - not_locking_time[id];
 * System.out.println( "Pass " + id + ": Time lost: " + compositor.format(
 * time_in_synchronization ) + " ms. " + compositor.format(
 * ((double)locking_time[id]/not_locking_time[id])*100.0 ) + "% increase" ); }
 * static public void main(String[ ] args) throws InterruptedException { final
 * Synch tester = new Synch(); tester.test(0); print_results(0); tester.test(1);
 * print_results(1); tester.test(2); print_results(2); tester.test(3);
 * print_results(3); tester.test(4); print_results(4); tester.test(5);
 * print_results(5); tester.test(6); print_results(6); final Object start_gate =
 * new Object(); Thread t1 = new Thread() { public void run() { try{
 * synchronized(start_gate) { start_gate.wait(); } } catch( InterruptedException
 * e ){} tester.test(7); } }; Thread t2 = new Thread() { public void run() {
 * try{ synchronized(start_gate) { start_gate.wait(); } } catch(
 * InterruptedException e ){} tester.test(8); } };
 * Thread.currentThread().setPriority( Thread.MIN_PRIORITY ); t1.start();
 * t2.start(); synchronized(start_gate){ start_gate.notifyAll(); } t1.join();
 * t2.join(); print_results( 7 ); print_results( 8 ); } }
 */