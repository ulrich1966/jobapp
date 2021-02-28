package de.juli.jobweb.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Handler implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(Md5Handler.class);

	/**
	 * Generiert einen MD5 Hash und gibt ihnn zurueck 
	 */
	public String generateMd5(String arg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md = MessageDigest.getInstance("MD5");
		md.update(arg.getBytes());
		byte[] digest = md.digest();
		String tmpHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return tmpHash;
	}

	/**
	 * Vegleicht einen Plaintext mit einem Hashwert und gibt bei Gleichheit true und bei
	 * Ungleichheit false zurueck 
	 */
	public boolean compareMd5(String plain, String hash) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes());
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
			if (myHash.equals(hash)) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			LOG.error("{}", e);
		}
		return false;
	}
}
