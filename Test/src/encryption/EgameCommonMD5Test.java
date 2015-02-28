package encryption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import cn.egame.common.util.Utils;

public class EgameCommonMD5Test {
	
	public static void main(String[] args) {
		System.out.println("Begin.....");
		File fileDir = new File("F:\\安装包");
    	File[] files = fileDir.listFiles();
    	for(File file : files){
    		if(file.isDirectory()){
    			continue;
    		}
    		Utils.encryptFileMD5(file.getAbsolutePath());
    	}
	}
	
	
	
	
//	@Test
	public void test() throws Exception{
		InputStream is = null;
		try {
			is = new FileInputStream("C:\\Users\\yuchao\\Desktop\\a.txt");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[4096];
			int i = 0;
//			while(() != -1){
			i = is.read(b);
			baos.write(b, 0, i);
//			}
//			byte[] b1 = baos.toByteArray();
			System.out.println(byteToHexString(b));;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(is!=null){
				is.close();
			}
		}
	}
	
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
