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

public class JsonServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(JsonServiceTest.class);
	private static final List<Integer> RUN_CONT = new ArrayList<>();

	@Test
	public void test() throws Exception {
		//testWrite();
		//testWrite_List();
		//testRead_Instance();
		//testRead_Type();
		testRead_List();
		RUN_CONT.forEach(e -> LOG.debug("done: {}", e));
	}

	@Ignore
	@Test
	public void testWrite() throws NotFoundException, NullPointerException {
//		if (!RUN_CONT.isEmpty()) {
//			return;
//		}
		String name = "uli";
		Account account = AccountHelper.getInstance().fillAccoundByProperties(name);
		Assert.assertNotNull(account);
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		String result = service.write(account, name);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		LOG.debug(result);

		RUN_CONT.add(1);
		LOG.debug("done " + 1 + "\n");
	}

	@Ignore
	@Test
	public void testWrite_List() throws Exception {
//		if (!RUN_CONT.contains(1)) {
//			testWrite();
//		}
		String user_uli = "uli";
		String user_admin = "uli";
		Account uli = AccountHelper.getInstance().fillAccoundByProperties(user_uli);
		Assert.assertNotNull(uli);
		Account admin = AccountHelper.getInstance().fillAccoundByProperties(user_admin);
		Assert.assertNotNull(admin);

		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);

		List<Account> list = new ArrayList<>();
		list.add(uli);
		list.add(admin);

		String result = service.write(list, "userList");
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		LOG.debug(result);

		RUN_CONT.add(2);
		LOG.debug("done " + 2 + "\n");
	}

	@Ignore
	@Test
	public void testRead_Instance() throws Exception {
//		if (!RUN_CONT.contains(2)) {
//			testWrite_List();
//		}

		String name = "uli";
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		Account account = (Account) service.read(new Account(), name);
		Assert.assertNotNull(account);
		LOG.debug(account.toString());

		RUN_CONT.add(3);
		LOG.debug("done " + 3 + "\n");
	}

	@Ignore
	@Test
	public void testRead_Type() throws Exception {
//		if (!RUN_CONT.contains(3)) {
//			testRead_Instance();
//		}

		String name = "uli";
		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		Account account = (Account) service.read(Account.class, name);
		Assert.assertNotNull(account);
		LOG.debug(account.toString());

		RUN_CONT.add(4);
		LOG.debug("done " + 4 + "\n");
	}

	@Ignore
	@Test
	public void testRead_List() throws Exception {
//		if (!RUN_CONT.contains(4)) {
//			testRead_Type();
//		}

		JsonService<Account> service = new JsonService<>();
		Assert.assertNotNull(service);
		List<Account> list = service.readList(Account.class, "userList");
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
		list.forEach(e -> LOG.debug(e.toString()));

		RUN_CONT.add(5);
		LOG.debug("done " + 5 + "\n");
	}

}
