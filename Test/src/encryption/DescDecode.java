package encryption;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import ch.ethz.ssh2.crypto.cipher.AES;
import cn.egame.common.util.DES;
import cn.egame.common.util.Utils;

public class DescDecode {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		String str = "U2FsdGVkX1+GwIYSkb6ewlmShIAAR+k1oKV87HFVoZlaCLjKUa3RsXxMIzs88xv2gvX9wXRao4SLaiOyB8I13w== ";
		System.out.println(DES.decryptForDES(str, "46152837"));
	}
}
