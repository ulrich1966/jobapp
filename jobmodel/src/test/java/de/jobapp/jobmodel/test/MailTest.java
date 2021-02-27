package de.jobapp.jobmodel.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Test;

public class MailTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			Map<String, String> data = fillData();
			//String p = "J:\\servers\\apache-tomcat-9.0.12\\wtpwebapps\\jobweb\\WEB-INF\\classes\\docs\\mail.txt";
			Path root = Paths.get(MailTest.class.getResource("/").toURI());
			Path path = root.resolve(Paths.get("mail.txt"));
			if(Files.exists(path)) {
				System.out.println(path);				
			} else {
				System.out.println("No such file: "+path);				
			}
			List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
			StringBuilder mail = new StringBuilder();
			for (String line : lines) {
				for (Entry<String, String> e : data.entrySet()) {
					if(line.contains(e.getKey())) {
						line = line.replace(e.getKey(), e.getValue());
					}
				}
				mail.append(line+"\n");
			}
			System.out.println(mail.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> fillData() {
		Map<String, String> data = new HashMap<>();

		data.put("cmp_name", "test");
		data.put("cmp_zip", "test");
		data.put("cmp_city", "test");
		data.put("cmp_street", "test");

		data.put("cnt_sex", "test");
		data.put("cnt_title", "test");
		data.put("cnt_first_name", "test");
		data.put("cnt_last_name", "test");

		data.put("job_title", "test");
		data.put("job_salary", "test");
		data.put("job_date", "test");

		data.put("src_pronomen", "test");
		data.put("src_source", "test");

		data.put("droplink", "test");
		return data;
	}
}
