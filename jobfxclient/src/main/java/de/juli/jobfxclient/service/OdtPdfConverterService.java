package de.juli.jobfxclient.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import org.odftoolkit.odfdom.doc.OdfTextDocument;

import de.juli.jobfxclient.util.AppProperties;
import fr.opensagres.odfdom.converter.pdf.PdfConverter;
import fr.opensagres.odfdom.converter.pdf.PdfOptions;

public class OdtPdfConverterService {
	private static OdtPdfConverterService converter;

	private OdtPdfConverterService() {

	}

	public static OdtPdfConverterService getInstance() {
		if (converter == null) {
			converter = new OdtPdfConverterService();
		}
		return converter;
	}

	public Path convert(Path source, Path target) throws Exception {
		File result = convert(source.toFile(), target.toFile());
		return result.toPath();
	}

	public File convert(File source, File target) throws Exception {
		OdfTextDocument document = OdfTextDocument.loadDocument(source);
		OutputStream out = new FileOutputStream(target);
		PdfOptions options = PdfOptions.create();
		PdfConverter.getInstance().convert(document, out, options);
		if(AppProperties.getOpenExpAfterSave().getValue()){
			Runtime.getRuntime().exec("explorer.exe /select," + target);			
		}
		return target;
	}
}
