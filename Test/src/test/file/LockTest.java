package test.file;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        for(int i = 10 ;i>0;i--){
            new Thread(new ReadThread()).start();
        }   
//        for(int i = 10 ;i>0;i--){
//            new Thread(new WriteThread()).start();
//        }   
    }

    
    
    public static void read(){
        try {
            String  markStr = System.currentTimeMillis()/1000 + "," +InetAddress.getLocalHost().getHostAddress()+" "+ Thread.currentThread().getId();
            System.out.println(markStr);
            FileLock lock = new FileLock("D://1.txt",markStr);
            
            if(!lock.isLocked()){
             try{
                if(lock.obtain(10000)){
                    Long sleepTime = new Random().nextInt(10)*1000L;
                    Thread.sleep(sleepTime);
                    lock.unlock();
                    System.out.println("运行完成并释放对应的文件："+markStr);
                }
             }catch(Exception e){
                 System.out.println(e.getMessage());
             }    
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
     
    }
    
    public static void write(){
        try {
            String  markStr = System.currentTimeMillis()/1000 + "," +InetAddress.getLocalHost().getHostAddress()+" "+ Thread.currentThread().getId();
            System.out.println(markStr);
            FileLock lock = new FileLock("D://1.txt",markStr);
            
            if(!lock.isLocked()){
             try{
                if(lock.obtain(1000)){
                    Long sleepTime = new Random().nextInt(10)*1000*60L;
                    Thread.sleep(sleepTime);
                    lock.unlock();
                }
             }catch(Exception e){
                 System.out.println(e.getMessage());
             }    
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }
    
  static  class ReadThread implements Runnable{

        @Override
        public void run() {
            read();
        }
    }
    
 static   class WriteThread implements Runnable{

        @Override
        public void run() {
            write();
        }
    }
}
