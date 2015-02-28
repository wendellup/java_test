package encryption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class ChecksumCRC8 {
	public static long doChecksum(File file) {
		long checksum = 0;
		try {
			CheckedInputStream cis = null;
			long fileSize = 0;
			try {
				// Computer CRC32 checksum
				cis = new CheckedInputStream(new FileInputStream(file),
						new CRC32());
				fileSize = file.length();
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.exit(1);
			}
			byte[] buf = new byte[128];
			while (cis.read(buf) >= 0) {
			}
			checksum = cis.getChecksum().getValue();
			// System.out
			// .println(checksum + " " + fileSize + " " + file.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return checksum;
	}

	/** 
     * 获得指定文件的byte数组 
     */  
    private static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
	
	public static void main(String[] args) throws Exception {

		File fileDir = new File("F:\\安装包");
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				long beginMillis = System.currentTimeMillis();
				byte[] fileBytes = getBytes(file.getAbsolutePath());
				byte encryCode = CRC8.calcCrc8(fileBytes);
				String encryCodeStr = "" + Integer.toHexString(0x00ff & encryCode);
				long endMillis = System.currentTimeMillis();
				System.out.println(encryCodeStr + ",fileSize:" + file.length()
						/ 1024 / 1024 + "MB,cost:" + (endMillis - beginMillis)
						 + "millisSeconds." + ",fileName:" + file.getName());

			}
		}
	}

}