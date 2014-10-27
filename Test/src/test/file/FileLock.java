package test.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileLock {
//    private static final Logger logger = LoggerFactory.getLogger(FileLock.class);
    public static final long LOCK_OBTAIN_WAIT_FOREVER = -1;
    public static long LOCK_POLL_INTERVAL = 1000;
    protected Throwable failureReason;
    int threshold = 5 * 60;// 五分钟
    String markStr;// 每个锁对应写入的文件的内容
    String lockFileName;// 文件锁路径及名称

    public FileLock(String lockFileName, String markStr) {
        this.lockFileName = lockFileName;
        this.markStr = markStr;
    }

    // 判断当前路径下的文件是否已经加锁
    public  boolean isLocked() {
        File tf = new File(lockFileName);
        if (!tf.exists()) { // 对应的文件不存在,则没有加锁
            return false;
        }
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        // 文件存在，检查对应的时间戳
        try {
            br = new BufferedReader(new FileReader(tf));
            String tempString = null;
            while ((tempString = br.readLine()) != null) {
                sb.append(tempString);
            }
            br.close();
            br = null;
            // 不存在内容，则删除对应的文件
            if (sb.length() == 0) {
                deleteFile();
                return false;// 表示当前没有加锁
            }
            // 获取时间戳
            String[] arr = sb.toString().split(",");

            if (arr == null || arr.length != 2) {// 找不到对应的时间戳，认为当前加锁有问题
                //检查文件的修改时间
                Long modifiedTime = tf.lastModified()/1000;
                //修改时间大于5分钟
                if (System.currentTimeMillis() / 1000 - modifiedTime > threshold){
                    deleteFile();
                    return false;// 表示当前没有加锁
                }else{
                    return true;//再次等待几分钟
                }
                
            }
            String timestampStr = sb.toString().split(",")[0];// 时间戳
            Long timestamp = Long.parseLong(timestampStr);
            if (System.currentTimeMillis() / 1000 - timestamp < threshold) {// 在指定的范围内，则认为当前的文件上锁的，否则，则认为超时
                System.out.println(System.currentTimeMillis()+"加锁标志： "+sb.toString());
                return true;
            } else {
                System.out.println(markStr+":超过时间范围");
                
                deleteFile();
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println(System.currentTimeMillis()+"读取文件出错");
            return false;
        } catch (IOException e) {
            System.out.println(System.currentTimeMillis()+"读取文件出错");
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(System.currentTimeMillis()+"读取文件出错");
                }
            }
        }
    }

    // 在使用之前，先调用isLock方法对是否加锁进行判断
    public boolean obtain() {
        try {
            createFile(); // 创建文件
        } catch (Exception e) {
            System.out.println(System.currentTimeMillis()+markStr+" 加锁时，创建文件失败");
            return false;// 加锁失败
        }
        // 创建文件成功
        File tf = new File(lockFileName);
        BufferedOutputStream bos = null;
        BufferedReader br = null;
        //先读，进行判断
        try {
            br = new BufferedReader(new FileReader(tf));
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            while ((tempString = br.readLine()) != null) {
                sb.append(tempString);
            }
            br.close();
            br = null;
            // 存在内容，则加锁失败
            if (sb.length() != 0) {
                System.out.println(markStr+"已经有其他进程"+sb.toString()+"占用");
                return false;// 加锁失败
            }
          
        } catch (FileNotFoundException e) {// 读取文件流出错
            System.out.println(System.currentTimeMillis()+markStr+"读入文件流出错");
            return false;
        } catch (IOException e) {
            System.out.println(System.currentTimeMillis()+markStr+"读入文件流出错");
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(System.currentTimeMillis()+"获取锁时关闭读取文件出错");
                }
            }
        }
     
        try {
            // 写入加锁时信息
            bos = new BufferedOutputStream(new FileOutputStream(tf));
            bos.write(markStr.getBytes());
            bos.flush();
            bos.close();
            bos = null;
            System.out.println(System.currentTimeMillis()+markStr+"：写入信息成功");
        } catch (FileNotFoundException e) {// 写入文件流出错
            System.out.println(System.currentTimeMillis()+markStr+"写入文件流出错");
            return false;
        } catch (IOException e) {
            System.out.println(System.currentTimeMillis()+markStr+"写入文件流出错");
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    System.out.println(System.currentTimeMillis()+"获取锁是写入文件出错");
                }
            }
        }
        try {
            // 再次读入，进行检测是否加锁成功
            br = new BufferedReader(new FileReader(tf));
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            while ((tempString = br.readLine()) != null) {
                sb.append(tempString);
            }
            br.close();
            br = null;
            // 不存在内容，则加锁失败
            if (sb.length() == 0) {
                return false;// 加锁失败
            }
            if (markStr.equals(sb.toString())) {// 文件的为内容是当前加锁的内容
                System.out.println(System.currentTimeMillis()+markStr+"：加锁成功");
                return true;// 加锁成功
            } else{
                System.out.println(System.currentTimeMillis()+markStr+"写入的内容和读取的内容不一致");
                return false;// 加锁失败
            }
        } catch (FileNotFoundException e) {// 读取文件流出错
            System.out.println(System.currentTimeMillis()+markStr+"读入文件流出错");
            return false;
        } catch (IOException e) {
            System.out.println(System.currentTimeMillis()+markStr+"读入文件流出错");
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(System.currentTimeMillis()+"获取锁时关闭读取文件出错");
                }
            }
        }
    }
    
    public boolean obtain(long lockWaitTimeout) throws IOException {
        failureReason = null;
        boolean locked = obtain();
        if (lockWaitTimeout < 0 && lockWaitTimeout != LOCK_OBTAIN_WAIT_FOREVER)
          throw new IllegalArgumentException("lockWaitTimeout should be LOCK_OBTAIN_WAIT_FOREVER or a non-negative number (got " + lockWaitTimeout + ")");

        long maxSleepCount = lockWaitTimeout / LOCK_POLL_INTERVAL;
        long sleepCount = 0;
        while (!locked) {
          if (lockWaitTimeout != LOCK_OBTAIN_WAIT_FOREVER && sleepCount++ >= maxSleepCount) {
            String reason = "Lock obtain timed out: " + this.toString();
            if (failureReason != null) {
              reason += ": " + failureReason;
            }
//            LockObtainFailedException e = new LockObtainFailedException(reason);
            if (failureReason != null) {
//              e.initCause(failureReason);
            }
//            throw e;
          }
          try {
            Thread.sleep(LOCK_POLL_INTERVAL);
          } catch (InterruptedException ie) {
//            throw new ThreadInterruptedException(ie);
          }
          locked = obtain();
        }
        return locked;
      }


    public void unlock() {
        System.out.println(System.currentTimeMillis()+"释放锁:"+markStr);
        deleteFile();
    }

    private void createFile() throws IOException {
        try {
            File tf = new File(lockFileName);
            if (!tf.exists()) {
                tf.createNewFile();
                System.out.println(System.currentTimeMillis()+markStr+"创建文件成功");
            } else {
                throw new IOException("文件已经存在,不能覆盖");
            }
            tf = null;
        } catch (IOException e) {
            throw e;
        }
    }

    private void deleteFile() {
        File tf = new File(lockFileName);
        if (tf.exists()) {
            tf.delete();
        }
        tf = null;
    }
}
