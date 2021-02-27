package de.juli.jobfxclient.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.converter.CurrencyConverter;
import de.juli.jobfxclient.dialog.AlertDialog;
import de.juli.jobfxclient.dialog.JobAdSourceDialog;
import de.juli.jobfxclient.viewmodel.ViewDocument;
import de.juli.jobfxclient.viewmodel.ViewJobAdSource;
import de.juli.jobfxclient.viewmodel.ViewState;
import de.juli.jobfxclient.viewmodel.ViewTitle;
import de.juli.jobmodel.model.ApplicationState;
import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.Document;
import de.juli.jobmodel.model.History;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Letter;
import de.juli.jobmodel.model.Source;
import de.juli.jobmodel.model.Title;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateController extends Controller implements Initializable {
	private static final Logger LOG = LoggerFactory.getLogger(CreateController.class);
	private ModelController mc = ModelController.getInstance();
	@FXML
	private Label lbl_error;
	@FXML
	private Label lbl_info;
	@FXML
	private Label lbl_count;
	@FXML
	private Label lbl_sum;
	@FXML
	private Label lbl_state;
	@FXML
	private TabPane tp_create;
	@FXML
	private ComboBox<ViewState> cb_state;
	@FXML
	private ComboBox<ViewDocument> cb_doc;
	@FXML
	private ComboBox<ViewTitle> cb_title;
	@FXML
	private ComboBox<ViewJobAdSource> cb_jobAd;
	@FXML
	private TextField txt_name;
	@FXML
	private TextField txt_zip;
	@FXML
	private TextField txt_street;
	@FXML
	private TextField txt_city;
	@FXML
	private TextField txt_web;
	@FXML
	private TextField txt_firstName;
	@FXML
	private TextField txt_LastName;
	@FXML
	private TextField txt_phone;
	@FXML
	private TextField txt_mobile;
	@FXML
	private TextField txt_email;
	@FXML
	private TextField txt_title;
	@FXML
	private TextField txt_salary;
	@FXML
	private TextField txt_webLink;
	@FXML
	private TextField txt_patern;
	@FXML
	private TextArea ta_note;
	@FXML
	private DatePicker dp_jobAdDate;
	@FXML
	private JobAdSourceDialog dialog;

	private List<ViewState> states;
	private List<ViewDocument> docs;
	private List<ViewTitle> titles;
	private List<ViewJobAdSource> sources;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setCreateController(this);
		init();
	}

	@FXML
	private void stateChange(ActionEvent event) {
		LOG.debug("not implemented");
	}

	@FXML
	private void docChange(ActionEvent event) {
		LOG.debug("not implemented");
	}

	@FXML
	private void jobAdChange(ActionEvent event) {
		LOG.debug("not implemented");
	}

	@FXML
	private void jobAdDateChange(ActionEvent event) {
		LOG.debug("not implemented");
	}

	@FXML
	private void newJobSource(ActionEvent event) {
		JobAdSourceDialog.init();
		sources = jobAdController.findAll().stream().map(c -> new ViewJobAdSource(c)).collect(Collectors.toList());
		cb_jobAd.getItems().setAll(FXCollections.observableArrayList(sources));
		cb_jobAd.getSelectionModel().select(sources.size()-1);
	}

	@FXML
	private void cancel(ActionEvent event) {
		super.getMenuController().switchShow(event);
	}

	@FXML
	private void save(ActionEvent event) {
		if(mc.getModelId() != null) {
			update();
		} else {
			create();
		}
		super.getMenuController().switchShow(event);
	}

	public void init() {
		states = Arrays.asList(ApplicationState.values()).stream().map(s -> new ViewState(s)).collect(Collectors.toList());
		cb_state.getItems().setAll(FXCollections.observableArrayList(states));
		cb_state.getSelectionModel().selectFirst();

//		docs = docController.findAll().stream().map(d -> new ViewDocument(d)).collect(Collectors.toList());
//		cb_doc.getItems().setAll(FXCollections.observableArrayList(docs));
//		cb_doc.getSelectionModel().selectFirst();

		titles = Arrays.asList(Title.values()).stream().map(s -> new ViewTitle(s)).collect(Collectors.toList());
		cb_title.getItems().setAll(FXCollections.observableArrayList(titles));
		cb_title.getSelectionModel().selectFirst();

		sources = jobAdController.findAll().stream().map(c -> new ViewJobAdSource(c)).collect(Collectors.toList());
		cb_jobAd.getItems().setAll(FXCollections.observableArrayList(sources));
		cb_jobAd.getSelectionModel().selectFirst();

		dp_jobAdDate.setValue(LocalDate.now());
	}

	public void newModel() {
		mc.newModel();
		clear();
	}

	public void update() {
		fillContactModel(mc.findCompany().getContact());
		fillCompanyModel(mc.findCompany());
		Job model = fillJobModel(mc.getModel());
		if(model != null) {
			mc.persist(model);
		}
	}

	public void create() {
		Contact contact = fillContactModel(new Contact());
		Company company = fillCompanyModel(new Company());
		Job model = fillJobModel(mc.getModel());
		contact = contactController.create(contact);
		company.setContact(contact);
		company.addJob(model);
		company = companyController.create(company);
		mc.persist(model);
	}

	public TabPane getTp_create() {
		return tp_create;
	}

	public void edit(ActionEvent event) {
		fillJob();
	}

	private void clear() {
		txt_title.setText(null);
		txt_email.setText(null);
		txt_firstName.setText(null);
		txt_LastName.setText(null);
		txt_mobile.setText(null);
		txt_phone.setText(null);
		txt_city.setText(null);
		txt_name.setText(null);
		txt_street.setText(null);
		txt_web.setText(null);
		txt_zip.setText(null);
		txt_salary.setText(null);
		cb_state.getSelectionModel().selectFirst();
		cb_doc.getSelectionModel().selectFirst();
	}

	private Contact fillContactModel(Contact contact) {
		contact.setEmail(txt_email.getText());
		contact.setFirstName(txt_firstName.getText());
		contact.setLastName(txt_LastName.getText());
		contact.setMobile(txt_mobile.getText());
		contact.setPhone(txt_phone.getText());
		Title title = cb_title.getSelectionModel().getSelectedItem().getTitle();
		contact.setTitle(title);
		return contact;
	}

	private Company fillCompanyModel(Company company) {
		company.setCity(txt_city.getText());
		company.setName(txt_name.getText());
		company.setStreet(txt_street.getText());
		company.setWeb(txt_web.getText());
		company.setZip(txt_zip.getText());
		return company;
	}

	private Job fillJobModel(Job model) {
		model.setTitle(txt_title.getText());
		model.setNote(ta_note.getText());
		Integer salary = null;
		try {
			salary = (Integer) CurrencyConverter.getNewInstance().convert(txt_salary.getText());
		} catch (NumberFormatException | ClassCastException e) {
			System.err.print(txt_salary.getText() + " konnte nicht in Dezimalwert gewandelt werden");
		}
		model.setSalary(salary);
		Source source = cb_jobAd.getSelectionModel().getSelectedItem().getSource();
		model.setSource(source);
		LocalDate ld = dp_jobAdDate.getValue();
		LOG.error("{}",ld.isAfter(LocalDate.now()));
		if(ld.isAfter(LocalDate.now())) {
			super.getInfoController().displayError("Datum liegt in der Zukunft");
			AlertDialog.init("Datum liegt in der Zukunft", "Datumsfehler");
			return null;
		}
		Date date = Date.valueOf(ld); // Ma
		model.setJobAdDate(date);
		Document doc = cb_doc.getSelectionModel().getSelectedItem().getDocument();
		model.setLetter((Letter) doc);
		ApplicationState state = cb_state.getSelectionModel().getSelectedItem().getState();
		History history = new History(state, ta_note.getText());
		model.addHistory(history);
		return model;
	}

	private void fillJob() {
		fillCompany(mc.findCompany());
		Map<String, String> data = mc.findJobData();
		txt_title.setText(data.get("title"));
		txt_salary.setText(data.get("salary"));
		txt_webLink.setText(data.get("weblink"));
		ta_note.setText(data.get("note"));
		cb_doc.getSelectionModel().select(new ViewDocument(mc.findDocument()));
		cb_jobAd.getSelectionModel().select(new ViewJobAdSource(mc.findJobAdSource()));
		dp_jobAdDate.setValue(mc.findJobAdDate().toLocalDate());
		List<History> historys = mc.findHistory();
		StringBuilder sbValue = new StringBuilder();
		if (historys != null && !historys.isEmpty()) {
			for (History history : historys) {
				String state = history.getApplicationState().getName();
				String date = history.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
				sbValue.append(String.format("%s\t%s\n", date, state));
			}
			lbl_state.setText(sbValue.toString());
			super.getInfoController().displayMsg(historys.get(0).getApplicationState().getName());
		}
		super.getInfoController().displayMsg(historys.get(0).getApplicationState().getName());
	}

	private void fillCompany(Company company) {
		if(company == null) {
			return;
		}
		fillContact(company.getContact());
		txt_name.setText(company.getName());
		txt_zip.setText(company.getZip());
		txt_street.setText(company.getStreet());
		txt_city.setText(company.getCity());
		txt_web.setText(company.getWeb());
	}

	private void fillContact(Contact contact) {
		if(contact == null) {
			return;
		}
		init();
		Title title = contact.getTitle();
		cb_title.getSelectionModel().select(new ViewTitle(title));
		txt_firstName.setText(contact.getFirstName());
		txt_LastName.setText(contact.getLastName());
		txt_phone.setText(contact.getPhone());
		txt_mobile.setText(contact.getMobile());
		txt_email.setText(contact.getEmail());
	}
}
