package de.juli.jobfxclient;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import de.juli.jobfxclient.service.OdtPdfConverterService;

public class PdfConverterTest {
	private static File source = new File("G:/temp/anschreiben.odt");
	private static File target = new File("G:/temp/anschreiben.pdf");
	
	@Test
	public void xdocreport() throws Exception {
		File convert = OdtPdfConverterService.getInstance().convert(source, target);
		Assert.assertNotNull("convert is missing", convert);
    }


}
