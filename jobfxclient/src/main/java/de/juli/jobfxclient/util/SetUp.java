package de.juli.jobfxclient.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.h2.tools.Server;

import de.juli.jobfxclient.ProjectSettings;
import de.juli.jobmodel.controller.CompanyController;
import de.juli.jobmodel.controller.ContactController;
import de.juli.jobmodel.controller.DocumentController;
import de.juli.jobmodel.controller.JobController;
import de.juli.jobmodel.controller.SourceController;
import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.Document;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Source;
import de.juli.jobmodel.model.Title;

public class SetUp {
	private static Server server;
	private static List<Document> documents;
	private static List<Source> sources;

	public static void main(String[] args) {
		try {
			server = Server.createTcpServer(args).start();
			System.out.println("Server runs ...");
			documents = createDocuments();
			documents.forEach(System.out::println);
			sources = createSources();			
			sources.forEach(System.out::println);
			List<Company> companys = createCompanys();
			companys.forEach(company -> print(company));
			whatch(server);
		} catch (Exception e) {
			e.printStackTrace();
			server.stop();
			System.exit(0);
		} finally {
		}
	}

	private static void whatch(Server server) {
		Scanner scan = new Scanner(System.in);
		System.out.println("... stop Server press q");
		while (scan.hasNext()) {
			String line = scan.nextLine();
			if (line != null && !line.isEmpty() && line.contains("q")) {
				System.out.println("Stop ");
				server.stop();
				scan.close();
				System.exit(0);
			}
		}
	}

	private static List<Source> createSources() {
		SourceController controller = new SourceController();
		List<Source> sources = new ArrayList<>();
		sources.add(new Source("bei", "Xing.de"));
		sources.add(new Source("bei der", "Agentur für Arbeit"));
		sources.add(new Source("bei", "Step Stone"));
		sources.add(new Source("im", "Weser Kurier"));
		sources.forEach(m -> controller.persist(m));
		return sources;
	}

	private static List<Company> createCompanys() {
		CompanyController controller = new CompanyController();
		for (int i = 1; i < 11; i++) {
			Company model = new Company();
			model.setCity("city_" + i);
			model.setName("name_" + i);
			model.setStreet("street_" + i);
			model.setWeb("web_" + i);
			model.setZip("zip_" + i);
			model = controller.create(model);
			createContacts(model);
			createJobs(model);
			model = controller.update(model);
		}
		return controller.findAll();
	}

	private static List<Job> createJobs(Company company) {
		JobController controller = new JobController();
		for (int i = 1; i < 11; i++) {
			String name = company.getName()+"_title_" + i;
			String note = company.getName()+"_note_" + i;
			String webLink = "www."+company.getName()+"_note_"+i+".de";
			Document doc = documents.get(ToolService.randomInt(documents.size()));
			Source source = sources.get(ToolService.randomInt(sources.size()));
			Integer salary = new Integer(ToolService.randomInt(10000, 100000));
			java.util.Date time = new java.util.Date();
			//Job model = new Job(name, salary, doc, note, company);
			Job model = new Job();
			model.setWebLink(webLink);
			model.setSource(source);
			model.setJobAdDate(new Date(time.getTime()));
			model = controller.create(model);
			//company.addJob(model);
		}
		return controller.findAll();
	}

	private static List<Contact> createContacts(Company company) {
		ContactController controller = new ContactController();
		Contact model = null;
		for (int i = 1; i < 11; i++) {
			model = new Contact();
			model.setTitle(Title.values()[ToolService.randomInt(Title.values().length-1)]);
			//model.setEmail("Email_" + i + "@job.de");
			model.setEmail("uli.test@fantasymail.de");
			model.setFirstName(company.getName()+"_firstName_" + i);
			model.setLastName(company.getName()+"_lastName_" + 1);
			model.setMobile("123 000" + i);
			model.setPhone("123 000" + i);
			model = controller.create(model);
			company.setContact(model);
		}
		return controller.findAll();
	}

	private static List<Document> createDocuments() {
		DocumentController controller = new DocumentController();
		try {
			Path path = Paths.get(SetUp.class.getResource("/docs").toURI());
			List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
			for (Path source : list) {
				String fileName = source.getFileName().toString();
				Path target = Paths.get(ProjectSettings.getDocRoot() + "/" + source.getFileName());
				Path copy = Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				System.out.println(String.format("File to %s copyed", copy));
				String reltaivPath = String.format("/%s/%s", ProjectSettings.getDocRoot().getFileName(), fileName);
				//controller.create(new Document(reltaivPath, fileName, FilenameUtils.getExtension(fileName)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return controller.findAll();
		return null;
	}

	private static Object print(Company company) {
		System.out.println(company);
		company.getJobs().forEach(job -> System.out.println("\t"+job));
		return null;
	}

}
