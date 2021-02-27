package de.juli.jobfxclient.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.dialog.WaitDialog;
import de.juli.jobfxclient.service.ApplyService;
import de.juli.jobfxclient.util.CurrentAppState;
import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.History;
import de.juli.jobmodel.model.Title;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ShowController extends Controller implements Initializable {
	private static final Logger LOG = LoggerFactory.getLogger(ShowController.class);
	private static ModelController mc;
	@FXML
	private TabPane tp_show;
	@FXML
	private Label lbl_name;
	@FXML
	private Label lbl_zip;
	@FXML
	private Label lbl_street;
	@FXML
	private Label lbl_city;
	@FXML
	private Label lbl_web;
	@FXML
	private Label lbl_contact_title;
	@FXML
	private Label lbl_firstName;
	@FXML
	private Label lbl_LastName;
	@FXML
	private Label lbl_phone;
	@FXML
	private Label lbl_mobile;
	@FXML
	private Label lbl_email;
	@FXML
	private Label lbl_title;
	@FXML
	private Label lbl_salary;
	@FXML
	private Label lbl_jobAdSource;
	@FXML
	private Label lbl_jobAdDate;
	@FXML
	private Label lbl_doc;
	@FXML
	private Label lbl_note;
	@FXML
	private Label lbl_company;
	@FXML
	private Label lbl_state_date;
	@FXML
	private Label lbl_state;
	@FXML
	private Label lbl_webLink;
	@FXML
	private Button cmd_start;
	@FXML
	private Button cmd_back;
	@FXML
	private Button cmd_next;
	@FXML
	private Button cmd_end;
	@FXML
	private Button cmd_edit;
	@FXML
	private Button cmd_send;
	@FXML
	private Button cmd_copy;
	@FXML
	private Button cmd_open;
	@FXML
	private Button cmd_create;
	@FXML
	private Button cmd_new;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.setShowController(this);
		mc = ModelController.getInstance();
		if (mc == null) {
			LOG.error("Kein ModelContoller");
		}
		updateView();
	}

	public TabPane getTp_show() {
		return tp_show;
	}

	public void update() {
		datasetStart(null);
	}

	public void findByCompany(String value) {
		mc.findByCompany(value);
		updateView();
	}

	@FXML
	private void edit(ActionEvent event) {
		super.getMenuController().switchEdit(event);
	}

	@FXML
	private void newModel(ActionEvent event) {
		super.getMenuController().switchNew(event);
	}

	@FXML
	private void datasetBack(ActionEvent event) {
		Long currentId = mc.getModelId();
		if (mc.findPrevious() == currentId) {
			super.getInfoController().displayError("Der Anfag der Daten ist erreicht!");
			cmd_back.setDisable(true);
			cmd_start.setDisable(true);
			cmd_next.setDisable(false);
			cmd_end.setDisable(false);
			return;
		}
		cmd_next.setDisable(false);
		cmd_end.setDisable(false);
		updateView();
	}

	@FXML
	private void datasetNext(ActionEvent event) {
		Long currentId = mc.getModelId();
		if (mc.findNext() == currentId) {
			super.getInfoController().displayError("Das Ende der Daten ist erreicht!");
			cmd_next.setDisable(true);
			cmd_end.setDisable(true);
			cmd_back.setDisable(false);
			cmd_start.setDisable(false);
			return;
		}
		cmd_back.setDisable(false);
		cmd_start.setDisable(false);
		updateView();
	}

	@FXML
	private void datasetStart(ActionEvent event) {
		mc.findFirst();
		cmd_next.setDisable(false);
		cmd_end.setDisable(false);
		cmd_back.setDisable(true);
		cmd_start.setDisable(true);
		updateView();
	}

	@FXML
	private void datasetEnd(ActionEvent event) {
		mc.findLast();
		cmd_next.setDisable(true);
		cmd_end.setDisable(true);
		cmd_back.setDisable(false);
		cmd_start.setDisable(false);
		cmd_back.requestFocus();
		updateView();
	}

	@FXML
	private void send(ActionEvent event) {
		LOG.debug("send");
	}

	@FXML
	private void create(ActionEvent event) {
		try {
			WaitDialog.init("Dokumente werden generiert ...", "Bitte Warten bis die Daten gespeichert sind.");
			ApplyService service = new ApplyService();
			CurrentAppState ops = service.createDocs();
			switch (ops) {
			case SUCCESS:
				super.getInfoController().displayMsg("Dokumente erstellt!");
				enableCmds();
				break;
			case ABORT:
				super.getInfoController().displayMsg("Vorgang abgebrochen");
				break;
			case FAIL:
				super.getInfoController().displayError("Fehler beim Erstellen der Dokumente!");
				break;
			default:
				break;
			}
			boolean isShow = WaitDialog.enableClose(ops.getMsg());
			if(isShow && mc.getLocalDocDir() != null) {
				Runtime.getRuntime().exec("explorer.exe /select," + mc.getLocalDocDir().toString());
			}
		} catch (Exception e) {
			WaitDialog.close();
			super.getInfoController().displayError("Fehler beim Erstellen der Bewerbung");
			errorAlert(e.getMessage(), String.format("Die Datei konnte nicht eingelesen werden!"));
			e.printStackTrace();
		}
	}

	@FXML
	private void delete(ActionEvent event) {
		boolean confirm = super.confirmAlert("Löschen bestätigen", String.format("Soll der Job %s bei %s gelöschtverden", mc.getModel().getTitle(), mc.findCompany().getName()), mc.getModel().getTitle());
		if (!confirm) {
			return;
		}
		boolean success = mc.delete();
		updateView();
		if (success) {
			super.getInfoController().displayMsg(mc.getMsg());
		} else {
			super.getInfoController().displayError(mc.getMsg());
		}
	}

	@FXML
	private void copyLink(ActionEvent event) {
		String value = lbl_webLink.getText();
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(value);
		clipboard.setContent(content);
	}

	@FXML
	private void open(ActionEvent event) {
		try {
			LOG.debug("Open folder: {}", mc.getLocalDocDir());
			Runtime.getRuntime().exec("explorer.exe /open," + mc.getLocalDocDir());
		} catch (IOException e) {
			LOG.error("{}", e.getMessage());
		}
	}

	private void updateView() {
		fillJob();
		super.getInfoController().displayMsg(mc.getMsg());
		super.getInfoController().displayId(mc.getId());
		super.getInfoController().displayCount(mc.getDatasetNum(), String.valueOf(mc.getSize()));
	}

	private void fillJob() {
		cmd_open.setDisable(true);
		cmd_send.setDisable(true);
		fillCompany(mc.findCompany());
		Map<String, String> data = mc.findJobData();
		lbl_title.setText(data.get("title"));
		lbl_salary.setText(data.get("salary"));
		lbl_note.setText(data.get("note"));
		lbl_jobAdSource.setText(data.get("source"));
		lbl_jobAdDate.setText(data.get("date"));
		lbl_doc.setText(data.get("document"));
		lbl_webLink.setText(data.get("weblink"));
		List<History> historys = mc.findHistory();
		StringBuilder sbValue = new StringBuilder();
		if (historys != null && !historys.isEmpty()) {
			for (History history : historys) {
				String state = history.getApplicationState().getName();
				String date = history.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
				String note = history.getNote();
				sbValue.append(String.format("%s\t%s\t%s\n", date, state, note));
			}
			lbl_state.setText(sbValue.toString());
			super.getInfoController().displayMsg(historys.get(0).getApplicationState().getName());
		} else {
			lbl_state_date.setText("no value");
			lbl_state.setText("no value");
			super.getInfoController().displayMsg("");
		}
		enableCmds();
	}

	private void enableCmds() {
		Path path = mc.getLocalDocDir();
		if(path != null && Files.exists(path)){
			cmd_open.setDisable(false);
		}		
	}

	private void fillCompany(Company company) {
		if (company == null) {
			return;
		}
		fillContact(company.getContact());
		lbl_name.setText(company.getName());
		lbl_zip.setText(company.getZip());
		lbl_street.setText(company.getStreet());
		lbl_city.setText(company.getCity());
		lbl_web.setText(company.getWeb());
		lbl_company.setText(company.getName());
	}

	private void fillContact(Contact contact) {
		if (contact == null) {
			return;
		}
		Title title = contact.getTitle();
		if (title != null) {
			lbl_contact_title.setText(title.getName());
		}
		lbl_firstName.setText(contact.getFirstName());
		lbl_LastName.setText(contact.getLastName());
		lbl_phone.setText(contact.getPhone());
		lbl_mobile.setText(contact.getMobile());
		lbl_email.setText(contact.getEmail());
	}
}
