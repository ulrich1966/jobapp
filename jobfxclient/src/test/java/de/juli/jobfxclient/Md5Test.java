package de.juli.jobfxclient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Test;

public class Md5Test {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws NoSuchAlgorithmException {
		String password = "123456";

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		System.out.println("Digest(in hex format):: " + hexString.toString());
	}
}
