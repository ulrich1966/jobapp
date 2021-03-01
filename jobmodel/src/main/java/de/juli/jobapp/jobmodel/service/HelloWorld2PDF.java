package de.juli.jobapp.jobmodel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class HelloWorld2PDF {

	public static void main(String[] args) {
		createPDF();
		createPDF();
	}

	private static void createPDF() {
		File in = new File("G:/temp/name_1.docx");
		String out ="G:/temp/hallo2.pdf";
		try {
			long start = System.currentTimeMillis();
			InputStream is = new FileInputStream(in);
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
			OutputStream os = new java.io.FileOutputStream(out);
			Docx4J.toPDF(wordMLPackage, os);

			os.flush();
			os.close();

			System.err.println("Generate " + out.toString() + " with " + (System.currentTimeMillis() - start) + "ms");

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}