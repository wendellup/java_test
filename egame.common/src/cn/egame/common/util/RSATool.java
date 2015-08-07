/**
 * 
 */
package cn.egame.common.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * 
 * @ClassName RSATool
 * @Copyright 炫彩互动
 * @Project egame.charge
 * @Author qintao
 * @Create Date 2013-4-15
 * @Modified by none
 * @Modified Date
 */
public class RSATool {

	// 数字签名，密钥算法
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 数字签名 签名/验证算法
	 * */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


	/**
	 * 签名
	 * 
	 * @param data待签名数据
	 * @param privateKey
	 *            密钥
	 * @return byte[] 数字签名
	 * */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 取得私钥
		// 生成私钥
		PrivateKey priKey = getPrivateKey(privateKey);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initSign(priKey);
		// 更新
		signature.update(data);
		BASE64Encoder base64En = new BASE64Encoder();
		return base64En.encode(signature.sign());
	}

	public static boolean verify(byte[] data, String publicKey, byte[] sign) throws Exception {
		// 初始化公钥
		PublicKey pubKey = getPublicKey(publicKey);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initVerify(pubKey);
		// 更新
		signature.update(data);
		// 验证
		return signature.verify(sign);
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		BASE64Decoder base64De = new BASE64Decoder();
		keyBytes = base64De.decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		BASE64Decoder base64De = new BASE64Decoder();
		keyBytes = base64De.decodeBuffer(key);
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
		return publicKey;
	}
	
}
