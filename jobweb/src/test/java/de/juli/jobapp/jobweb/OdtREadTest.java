package de.juli.jobapp.jobweb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OdtREadTest {

	// @Test
	public void test3() throws Exception {
		String file = this.getClass().getResource("/anschreiben_og.odt").getFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = null;

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry("content.xml");
			is = zipFile.getInputStream(zipEntry);

			Document doc = builder.parse(is);
			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();

			Node body = findBody(nodes);
			System.out.println("" + body.getFirstChild());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	//@Test
	public void test4() throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		String file = this.getClass().getResource("/anschreiben_og.odt").getFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry("content.xml");
			InputStream is = zipFile.getInputStream(zipEntry);
			Document document = documentBuilder.parse(new InputSource(is));
		}

		// String usr =
		// document.getElementsByTagName("user").item(0).getTextContent();
		// String pwd =
		// document.getElementsByTagName("password").item(0).getTextContent();
	}

	@Test
	public void test5() throws Exception {

		String file = this.getClass().getResource("/anschreiben_og.odt").getFile();

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry("content.xml");
			InputStream is = zipFile.getInputStream(zipEntry);

			InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);

			br.lines().forEach(line -> System.out.println(line));
		}
	}

	private Node findBody(NodeList nodeList) {
		Node item = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			String name = nodeList.item(i).getNodeName();
			if (name.contains("body")) {
				item = nodeList.item(i);
			}
		}
		return item;
	}
}