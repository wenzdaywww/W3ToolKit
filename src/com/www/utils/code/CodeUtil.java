package com.www.utils.code;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
/**
 * 编码加密解密
 * @author www
 *
 */
public class CodeUtil {

	public static void main(String[] args) {
		System.out.println(CodeUtil.produce16MD5("www"));
		System.out.println(CodeUtil.produce32MD5("www"));
		System.out.println("字符串加密后="+CodeUtil.encrypt("www123", "123456"));
		System.out.println("字符串解密后="+CodeUtil.decrypt("www123", CodeUtil.encrypt("www123", "123456")));
	}
	/**
	 * 32位MD5加密
	 * @param code
	 * @return
	 */
	public static String produce32MD5(String code){
		try {  
			MessageDigest md = MessageDigest.getInstance("MD5");  
			md.update(code.getBytes());  
			byte b[] = md.digest();  
			int i;  
			StringBuffer buf = new StringBuffer("");  
			for (int offset = 0; offset < b.length; offset++) {  
				i = b[offset];  
				if (i < 0)  
					i += 256;  
				if (i < 16)  
					buf.append("0");  
				buf.append(Integer.toHexString(i));  
			}  
			//32位加密  
			return buf.toString();  
		} catch (Exception e) {  
			e.printStackTrace();  
			return null;  
		}
	}
	/**
	 * 16位MD5加密
	 * @param code
	 * @return
	 */
	public static String produce16MD5(String code){
		try {  
			MessageDigest md = MessageDigest.getInstance("MD5");  
			md.update(code.getBytes());  
			byte b[] = md.digest();  
			int i;  
			StringBuffer buf = new StringBuffer("");  
			for (int offset = 0; offset < b.length; offset++) {  
				i = b[offset];  
				if (i < 0)  
					i += 256;  
				if (i < 16)  
					buf.append("0");  
				buf.append(Integer.toHexString(i));  
			}   
			//16位的加密  
			return buf.toString().substring(8, 24);  
		} catch (Exception e) {  
			e.printStackTrace();  
			return null;  
		}
	}
	/** 
	 * 加密字符串 
	 * @param strIn 需加密的字符串 
	 * @return 加密后的字符串 
	 * @throws Exception 
	 */  
	public static String encrypt(String strKey,String strIn) {  
		try {
			return byteArr2HexStr(encrypt(strKey,strIn.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;  
		}
	}  
	/** 
	 * 解密字符串 
	 * @param strIn 需解密的字符串 
	 * @return 解密后的字符串 
	 * @throws Exception 
	 */  
	public static String decrypt(String strKey,String strIn) {  
		try {
			return new String(decrypt(strKey,hexStr2ByteArr(strIn)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}  
	} 
	/** 
	 * 加密字节数组 
	 * @param arrB 需加密的字节数组 
	 * @return 加密后的字节数组 
	 * @throws Exception 
	 */  
	private static byte[] encrypt(String strKey,byte[] arrB){ 
		try {
			Key key = getKey(strKey.getBytes());
			Cipher encryptCipher = Cipher.getInstance("DES");  
			encryptCipher.init(Cipher.ENCRYPT_MODE, key); 
			return encryptCipher.doFinal(arrB);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}  
	/** 
	 * 解密字节数组 
	 * @param arrB 需解密的字节数组 
	 * @return 解密后的字节数组 
	 * @throws Exception 
	 */  
	private static byte[] decrypt(String strKey,byte[] arrB){  
		try {
			Key key = getKey(strKey.getBytes());
			Cipher decryptCipher = Cipher.getInstance("DES");  
			decryptCipher.init(Cipher.DECRYPT_MODE, key); 
			return decryptCipher.doFinal(arrB);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}  
	/** 
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 
	 * 不足8位时后面补0，超出8位只取前8位 
	 * @param arrBTmp 构成该字符串的字节数组 
	 * @return 生成的密钥 
	 * @throws java.lang.Exception 
	 */  
	private static Key getKey(byte[] arrBTmp) {  
		byte[] arrB = new byte[8];  
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {  
			arrB[i] = arrBTmp[i];  
		}  
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");  
		return key;  
	}  
	/** 
	 * 将byte数组转换为表示16进制值的字符串， 
	 * 如：byte[]{8,18}转换为：0813， 
	 * 和public static byte[] hexStr2ByteArr(String strIn) 
	 * 互为可逆的转换过程 
	 * @param arrB 需要转换的byte数组 
	 * @return 转换后的字符串 
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出 
	 */  
	private static String byteArr2HexStr(byte[] arrB){  
		int iLen = arrB.length;  
		StringBuffer sb = new StringBuffer(iLen * 2);  
		for (int i = 0; i < iLen; i++) {  
			int intTmp = arrB[i];  
			while (intTmp < 0) {  
				intTmp = intTmp + 256;  
			}  
			if (intTmp < 16) {  
				sb.append("0");  
			}  
			sb.append(Integer.toString(intTmp, 16));  
		}  
		return sb.toString();  
	}  
	/** 
	 * 将表示16进制值的字符串转换为byte数组， 
	 * 和public static String byteArr2HexStr(byte[] arrB) 
	 * 互为可逆的转换过程 
	 * @param strIn 需要转换的字符串 
	 * @return 转换后的byte数组 
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出 
	 */  
	private static byte[] hexStr2ByteArr(String strIn){  
		byte[] arrB = strIn.getBytes();  
		int iLen = arrB.length;  
		byte[] arrOut = new byte[iLen / 2];  
		for (int i = 0; i < iLen; i = i + 2) {  
			String strTmp = new String(arrB, i, 2);  
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);  
		}  
		return arrOut;  
	}  
}
