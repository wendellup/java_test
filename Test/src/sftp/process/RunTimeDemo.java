package sftp.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Buffer;

public class RunTimeDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Runtime r = Runtime.getRuntime();
        //应用程序所在的路径
//        String str_path = "ipconfig";
        String str_path = "/test/mobile_8103_restart.sh";
        
        List<String> strList = new ArrayList<String>();
        Process process = null;
        try {
            //该方法开启一个新的进程
//            process = r.exec(new String[]{"/bin/bash", "-c", str_path});
        	process = r.exec(new String[]{"/bin/bash", "-c", str_path});
            InputStreamReader ir = new InputStreamReader(process
                    .getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(ir);
            String line;
//            process.waitFor();
            while ((line = br.readLine()) != null){
                strList.add(line);
            }
            System.out.println(strList);
            int exitValue = process.waitFor();
            if (0 != exitValue) {
                System.out.println("call shell failed. error code is :" + exitValue);
            }else{
                System.out.println("call shell success.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //利用该方法结束开启的进程
        process.destroy();
        
        
//        while(true){
//        	
//        }

    }

}