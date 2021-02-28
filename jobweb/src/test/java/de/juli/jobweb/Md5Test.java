package de.juli.jobweb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Md5Test {
	private static final Logger LOG = LoggerFactory.getLogger(Md5Test.class);

	@Test
	public void test() throws NoSuchAlgorithmException {
		String uli = "DD55CEC2CE59ACA4E6647DCFBC90DC27";
		String uliPass = "uli";
		String admin = "21232F297A57A5A743894A0E4A801FC3";
		String adminPass = "admin";

		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(uliPass.getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		LOG.debug(myHash);
		Assert.assertEquals(true, myHash.equals(uli));
		
		md.update(adminPass.getBytes());
		digest = md.digest();
		myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		LOG.debug(myHash);
		Assert.assertEquals(true, myHash.equals(admin));


		
	}

}
