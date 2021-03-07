package de.juli.jobapp.jobmodel.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.lang.NullPointerException;

import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.util.AccountHelper;
import javassist.NotFoundException;

public class JsonServiceTestUser {
	private static final Logger LOG = LoggerFactory.getLogger(JsonServiceTestUser.class);

	@Test
	public void test() throws Exception {
		testWrite();
		testWrite_List();
		testRead_Instance();
		testRead_Type();
		testRead_List();
	}

	@Ignore
	@Test
	public void testWrite() throws NotFoundException, NullPointerException {
		String name = "uli";
		Account account = AccountHelper.getInstance().fillAccoundByProperties(name);
		Assert.assertNotNull(account);
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		String result = service.write(account, name);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		LOG.debug(result);

		LOG.debug("done " + 1 + "\n");
	}

	@Ignore
	@Test
	public void testWrite_List() throws Exception {
		String user_uli = "uli";
		String user_admin = "admin";
		Account uli = AccountHelper.getInstance().fillAccoundByProperties(user_uli);
		Assert.assertNotNull(uli);
		Account admin = AccountHelper.getInstance().fillAccoundByProperties(user_admin);
		Assert.assertNotNull(admin);

		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);

		List<Account> list = new ArrayList<>();
		list.add(uli);
		list.add(admin);

		String result = service.write(list, "user-list");
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		LOG.debug(result);

		LOG.debug("done " + 2 + "\n");
	}

	@Ignore
	@Test
	public void testRead_Instance() throws Exception {
		String name = "uli";
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		Account account = (Account) service.read(new Account(), name);
		Assert.assertNotNull(account);
		LOG.debug(account.toString());

		LOG.debug("done " + 3 + "\n");
	}

	@Ignore
	@Test
	public void testRead_Type() throws Exception {
		String name = "uli";
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		Account account = (Account) service.read(Account.class, name);
		Assert.assertNotNull(account);
		LOG.debug(account.toString());

		LOG.debug("done " + 4 + "\n");
	}

	@Ignore
	@Test
	public void testRead_List() throws Exception {
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		List<Account> list = service.readList(Account.class, "user-list");
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
		list.forEach(e -> LOG.debug(e.toString()));

		LOG.debug("done " + 5 + "\n");
	}

}
