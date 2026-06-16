package AlgorithmBasic.class33;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class Hash {

	private MessageDigest hash;

	public Hash(String algorithm) {
		try {
			hash = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String hashCode(String input) {
		// 替换掉 DatatypeConverter，纯JDK原生实现
		byte[] digest = hash.digest(input.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println("支持的算法 : ");
		for (String str : Security.getAlgorithms("MessageDigest")) {
			System.out.println(str);
		}
		System.out.println("=======");

		String algorithm = "SHA";
		Hash hash = new Hash(algorithm);

		String input1 = "zuochengyunzuochengyun1";
		String input2 = "zuochengyunzuochengyun2";
		String input3 = "zuochengyunzuochengyun3";
		String input4 = "zuochengyunzuochengyun4";
		String input5 = "zuochengyunzuochengyun5";
		System.out.println(hash.hashCode(input1));
		System.out.println(hash.hashCode(input2));
		System.out.println(hash.hashCode(input3));
		System.out.println(hash.hashCode(input4));
		System.out.println(hash.hashCode(input5));

	}

}