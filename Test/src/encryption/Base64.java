package encryption;

import java.io.IOException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import cn.egame.common.util.Utils;

public class Base64 {
	public static void main(String[] args) throws IOException, Base64DecodingException {
		String str = "啊啊啊啊";
		String encodeStr = Utils.toBase64Encode(str);
		System.out.println(encodeStr);
		
		String decodeStr = Utils.toBase64DecodeString(encodeStr);
		System.out.println(decodeStr);
		
	}
}
