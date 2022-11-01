package com.example.nevs.util;

import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * MD5工具类
 */
@Component
public class MD5Util {
	public static String md5(String src){
		return DigestUtils.sha1DigestAsHex(src);
	}
	public static String inputPassToFromPass(String inputPass,String salt){
		String str = "" +salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}
	public static String formPassToDBPass(String formPass,String salt){
		String str = "" +salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}

	public static void main(String[] args) {
		String salt = "134fll";
		String input = inputPassToFromPass("123456", salt);
		System.out.println(formPassToDBPass("123456",salt));
	}

}