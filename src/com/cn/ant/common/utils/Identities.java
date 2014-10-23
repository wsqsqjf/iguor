package com.cn.ant.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author calvin
 * @version 2013-01-15
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 生成随机指定位数的随机数字字符串
	 * 
	 * @param size
	 * @return
	 */
	public static String randomStr(int size) {
		long temp = randomLong();
		String result = (temp + "").substring(0, size);
		return result;
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	/**
	 * 获取流水号
	 * 
	 * @return
	 */
	public static String generateMainSn() {
		String dateStr = DateUtils.getDate("yyyyMMddHHmmss");
		String random = randomStr(4);
		String mainSn = dateStr + random;
		return mainSn;
	}

	/**
	 * 生成处方编号
	 * 
	 * @return
	 */
	public static String generatePreMainSn() {
		String dateStr = DateUtils.getDate("yyMMdd");
		String random = randomStr(4);
		String mainSn = dateStr + random;
		return mainSn;
	}

	public static void main(String[] args) {
		System.out.println(generatePreMainSn());
	}
}
