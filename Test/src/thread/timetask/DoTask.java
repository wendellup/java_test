package thread.timetask;

/**
 * @author troy(J2EE)
 * @version 1.0
 */
import java.util.*;
import java.text.*;
public class DoTask {   
    private final static Timer timer = new Timer();
	private static Date firstTime;//任务首次执行时间
	private final static long PERIO_TIME=2 * 1000;//任务运行周期: 单位毫秒
	public final static Timer getTimer(){
		return timer;//获取timer对象的实例
	}
    public void start() throws Exception{
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       firstTime=sdf.parse("2010-07-26 16:06:10");
       timer.scheduleAtFixedRate(new SendMsgTask(),firstTime,PERIO_TIME);//设置定时任务
    } 
	public static void main(String[] args) throws Exception{ 
       new DoTask().start(); //开启任务
    }
}
class SendMsgTask extends TimerTask{
	private static int i=0;
	public void run() { 
		sendMsg();
	} 
	private void sendMsg() {
		i++;
		System.out.println("开始执行-- 开始 (第"+i+"次)");
		if(i==5){
			DoTask.getTimer().cancel();//当执行任务5次后停止--计时器
		}
	} 
}
