package de.juli.jobfxclient.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobmodel.model.ApplicationState;
import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.Document;
import de.juli.jobmodel.model.History;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Source;
import de.juli.jobmodel.model.Title;

public class ModelController extends Controller {
	private static final Logger LOG = LoggerFactory.getLogger(ModelController.class);
	private static ModelController controller;
	private List<Job> models;
	private Job model;
	private String msg;
	private String id;
	public static int currentIndex = 0;

	private ModelController() {
		updateModel(jobController.findAll());
	}

	public static ModelController getInstance() {
		if (ModelController.controller == null) {
			controller = new ModelController();
		}
		return ModelController.controller;
	}

	public void persist(Job model) {
		this.model = jobController.persist(model);
		updateModel(jobController.findAll());
		msg = "Job gespeichert";
	}

	public void newModel() {
		this.model = new Job();
	}

	public boolean delete() {
		boolean success = false;
		if (this.model != null) {
			success = super.jobController.delete(model);
		} else {
			LOG.error("Kein Model!");
			msg = "Kein Job-Model";
		}
		if (success) {
			updateModel(jobController.findAll());
		}
		return success;
	}

	public Long findNext() {
		currentIndex++;
		Long id = this.model.getId();
		try {
			this.model = models.get(currentIndex);
			id = this.model.getId();
		} catch (IndexOutOfBoundsException e) {
			LOG.error("End of dataset");
		}
		return id;
	}

	public Long findPrevious() {
		currentIndex--;
		Long id = this.model.getId();
		try {
			this.model = models.get(currentIndex);
			id = this.model.getId();
		} catch (IndexOutOfBoundsException e) {
			LOG.error("End of dataset");
		}
		return id;
	}

	public void findFirst() {
		currentIndex = 0;
		this.model = models.get(currentIndex);
	}

	public void findLast() {
		currentIndex = models.size() - 1;
		this.model = models.get(currentIndex);
	}

	public Document findDocument() {
		Document doc = null;
		if (model != null) {
			//doc = model.getLetter();
		}
		return doc;
	}

	public Company findCompany() {
		Company comp = null;
		if (model != null) {
			comp = model.getCompany();
		}
		return comp;
	}

	public Map<String, String> findJobData() {
		Map<String, String> data = new HashMap<>();
		if (this.model != null) {
			Document doc = findDocument();
			if (doc != null) {
				data.put("document", "" + doc.getName());
			} else {
				data.put("document", "");
			}
			data.put("note", "" + this.model.getNote());
			data.put("salary", "" + String.valueOf(this.model.getSalary()));
			data.put("title", "" + this.model.getTitle());
			data.put("date", "" + this.model.getJobAdDate());
			data.put("source", "" + this.model.getSource().getName());
			data.put("pronomen", "" + this.model.getSource().getPronomen());
			data.put("weblink", "" + this.model.getWebLink());
		} else {
			LOG.error("kein model");
		}
		return data;
	}

	public Map<String, String> findApplyData() {
		Map<String, String> data = findJobData();
		data.putAll(findCompanyData());
		data.putAll(findContactData(0));
		return data;
	}

	public Map<String, String> findCompanyData() {
		Map<String, String> data = new HashMap<>();
		if (this.model.getCompany() != null) {
			Company company = this.model.getCompany();
			data.put("name", "" + company.getName());
			data.put("zip", "" + company.getZip());
			data.put("city", "" + company.getCity());
			data.put("street", "" + company.getStreet());
		} else {
			LOG.error("kein model");
		}
		return data;
	}

	public Map<String, String> findContactData(int number) {
		Map<String, String> data = new HashMap<>();
		Contact contact = this.model.getCompany().getContact();
		if (contact != null) {
			contact = this.model.getCompany().getContact();
			data.put("first_name", "" + contact.getFirstName());
			data.put("last_name", "" + contact.getLastName());
			data.put("sex", "" + Title.appellation(contact.getTitle()));
			data.put("appell", "" + contact.getTitle().getName());
		} else {
			LOG.error("kein model");
		}
		return data;
	}

	public List<History> findHistory() {
		List<History> historys = null;
		if (model != null) {
			historys = model.getHistorys();
			if (historys != null && !historys.isEmpty()) {
				Collections.reverse(historys);
			}
		}
		return historys;
	}

	public History findLastHistory() {
		History entry = null;
		if (model != null) {
			entry = model.getHistoryLastEntry();
		}
		return entry;
	}

	public Source findJobAdSource() {
		return this.model.getSource();
	}

	public Date findJobAdDate() {
		return this.model.getJobAdDate();
	}

	public void findByCompany(String value) {
		if (value != null && !value.isEmpty()) {
			Company company = companyController.findByName(value);
			List<Job> jobs = company.getJobs();
			updateModel(jobs);
		} else {
			findAll();
		}
	}

	public void findAll() {
		updateModel(jobController.findAll());
	}

	public Long getModelId() {
		Long id = 0l;
		if (model != null) {
			id = model.getId();
		}
		return id;
	}

	public String getMsg() {
		return msg;
	}

	public int getSize() {
		int size = 0;
		if (models != null && !models.isEmpty()) {
			size = models.size();
		}
		return size;
	}

	public String getId() {
		return id;
	}

	public String getDatasetNum() {
		return String.valueOf(currentIndex + 1);
	}

	public Job getModel() {
		return model;
	}

	public Path getLocalDocDir() {
		Path path = null;
		if (model != null) {
			try {
				path = Paths.get(model.getLocalDocDir());
			} catch (NullPointerException e) {
				path = null;
			}
		}
		return path;
	}

	public ApplicationState setLocalDocDir(Path path) {
		model.setLocalDocDir(path.toString());
		model = super.jobController.persist(model);
		return ApplicationState.SUCCESS;
	}

	private void updateModel(List<Job> models) {
		Collections.reverse(models);
		if (models == null || models.isEmpty()) {
			LOG.error("\nNo Jobs found ... Check your Db and persistient.xml for create/update\n");
			models = new ArrayList<>();
			return; 
		}
		LOG.info(String.format("\t%s jobs found", models.size()));
		this.models = models;
		currentIndex = 0;
		this.model = models.get(currentIndex);
		this.id = String.valueOf(this.model.getId());
	}

}
