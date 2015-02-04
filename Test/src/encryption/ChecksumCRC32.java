package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class ChecksumCRC32 {
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

	public static void main(String[] args) throws Exception {

		File fileDir = new File("F:\\安装包");
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				long beginMillis = System.currentTimeMillis();
				long encryCode = doChecksum(file);
				long endMillis = System.currentTimeMillis();
				System.out.println(encryCode + ",fileSize:" + file.length()
						/ 1024 / 1024 + "MB,cost:" + (endMillis - beginMillis)
						/ 1000 + "seconds." + ",fileName:" + file.getName());

			}
		}
	}

}