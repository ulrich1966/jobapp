package de.juli.jobapp.jobweb;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import org.junit.Test;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class PdfCreateTest {
	public static final int SPEED_TEST_ITERATIONS = 100;
	public static final String OPENOFFICE_HOST = "dev.emma-nl.hypoport.local";

	private static final String SOURCE_ODF_NAME = "J:/servers/apache-tomcat-9.0.12/uploads/docs/admin/applies/admintest/admintest.odt";
	private static final String TARGET_PDF_NAME = "J:/servers/apache-tomcat-9.0.12/uploads/docs/admin/applies/admintest/admintest.pdf";
	//private static final String CMD = "C:\\Program Files (x86)\\LibreOffice 5\\program\\soffice.exe -accept=socket, host=localhost, port=8100; urp; StarOffice.ServiceManager";
	private static final String CMD = "C:\\Program Files (x86)\\LibreOffice 5\\program\\soffice.exe";

	@Test
	public void test() throws IOException {
		
		//Process process = new ProcessBuilder(CMD).start();
		
		 ProcessBuilder pb = new ProcessBuilder(CMD, "-headless", "-accept=\"socket,host=127.0.0.1,port=8100;urp;\"", "-nofirststartwizard");
		 Process process = pb.start();
	     System.out.println("Started EXE"); 
	     convert();
	     process.destroy();
	     System.out.println("DONE"); 
		
	}
	
	private void convert() throws ConnectException {
		OpenOfficeConnection connection = null;
		File inputFile = new File(SOURCE_ODF_NAME);
		File outputFile = new File(TARGET_PDF_NAME);

		// connect to an OpenOffice.org instance running on port 8100
		// run in shell: soffice -headless
		// -accept="socket,host=127.0.0.1,port=8100;urp;"
		// -nofirststartwizard
		// See: http://www.artofsolving.com/
		connection = new SocketOpenOfficeConnection(8100);
		connection.connect();

		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(inputFile, outputFile);
		// close the connection
		if (connection.isConnected()) {
			connection.disconnect();
		}
	}

}
