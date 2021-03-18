package de.juli.jobapp.jobmodel.util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.SenderAddress;
import de.juli.jobapp.jobmodel.service.JsonService;
import javassist.NotFoundException;

public class AccountHelper {
	private static final Logger LOG = LoggerFactory.getLogger(AccountHelper.class);
	private static AccountHelper helper;
	
	private AccountHelper() {
	}
	
	public static AccountHelper getInstance() {
		if(helper == null) {
			AccountHelper.helper = new AccountHelper();
		}		
		return AccountHelper.helper;
	}
	
	public List<Account> fillAccoundByJsonFile(String name) throws NotFoundException {
		JsonService service = new JsonService();
		List<Account> list = service.readList(Account.class, name);
		return list;
	}
	
	public Account fillAccoundByProperties(String name) throws NotFoundException {
		AppProperties props = AppProperties.getInstance(AppProperties.USERS_PROP);
		Account account = new Account();
		account.setName(props.propertyFind(String.format("%s.name", name)));
		account.setPass(makeMd5(props.propertyFind(String.format("%s.pass", name))));
		account.setProfillink(props.propertyFind(String.format("%s.profillink", name)));
		account.setPort(Integer.valueOf(props.propertyFind(String.format("%s.port", name))));
		account.setSender(props.propertyFind(String.format("%s.sender", name)));
		account.setSmtp(props.propertyFind(String.format("%s.smtp", name)));
		account.setSmtpPass(props.propertyFind(String.format("%s.smtp.pass", name)));
		account.setUser(props.propertyFind(String.format("%s.email.user", name)));
		account.setUser(props.propertyFind(String.format("%s.roll", name)));
		account.setStyleTheme(props.propertyFind(String.format("%s.theme", name)));
		
		SenderAddress adress = new SenderAddress("Hein", "Mück", "Sebalsdsbrück. 36", 28203, "Bremen", "01111 22334455", "hein.mück@gmx.de");
		account.setAddress(adress);
		
		return account;
	}
	
	private String makeMd5(String value) {
        Md5Handler md5 = new Md5Handler();        
        try {
        	String hash = md5.generateMd5("uli");
        	return hash;
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage());
		}
        return value;
	}

}
