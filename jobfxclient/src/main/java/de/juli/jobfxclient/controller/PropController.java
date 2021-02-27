package de.juli.jobfxclient.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.service.PropertyService;
import de.juli.jobfxclient.viewmodel.Conf;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PropController extends Controller implements Initializable {
	private static final Logger LOG = LoggerFactory.getLogger(PropController.class);
	private Properties prop = PropertyService.findAppProperties();
	
	@FXML
	private Pane pane_email_conf;
	@FXML
	private Pane pane_path_conf;
	@FXML
	private Pane pane_db_conf;
	@FXML
	private Pane pane_docs_conf;
	@FXML
	private ListView<Conf> lv_conf;
	@FXML
	private TextField txt_sender;
	@FXML
	private TextField txt_smtp;
	@FXML
	private TextField txt_port;
	@FXML
	private TextField txt_user;
	@FXML
	private PasswordField txt_pass;
	@FXML
	private TextField txt_profil_link;

	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setPropController(this);
		if(prop != null && !prop.isEmpty()) {
			fillFields();			
		} else {
			LOG.error("Properties not loaded");
			return;
		}
		ObservableList<Conf> items = FXCollections.observableArrayList(Arrays.asList(Conf.values()));
		lv_conf.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Conf>() {
			@Override
			public void changed(ObservableValue<? extends Conf> ov, Conf old_value, Conf new_value) {
				switch (new_value) {
				case E_MAIL:
					pane_docs_conf.setVisible(false);
					pane_email_conf.setVisible(true);
					pane_path_conf.setVisible(false);
					pane_db_conf.setVisible(false);
					break;
				case PATHS:
					pane_docs_conf.setVisible(false);
					pane_email_conf.setVisible(false);
					pane_path_conf.setVisible(true);
					pane_db_conf.setVisible(false);
					break;
				case DATABASE:
					pane_docs_conf.setVisible(false);
					pane_email_conf.setVisible(false);
					pane_path_conf.setVisible(false);
					pane_db_conf.setVisible(true);
					break;
				case DOCUMENTS:
					pane_docs_conf.setVisible(true);
					pane_email_conf.setVisible(false);
					pane_path_conf.setVisible(false);
					pane_db_conf.setVisible(false);
					break;
				default:
					break;
				}
			}
		});
		lv_conf.setItems(items);
		pane_path_conf.setVisible(true);
	}

	private void fillFields() {
		txt_sender.setText((String) prop.get("sender"));
		txt_smtp.setText((String) prop.get("smtp"));
		txt_port.setText((String) prop.get("port"));
		txt_user.setText((String) prop.get("user"));
		txt_pass.setText((String) prop.get("pass"));
		txt_profil_link.setText((String) prop.get("profillink"));
	}

	@FXML
	private void cancel(ActionEvent event) {
		LOG.info("Canceled by user");
		stage.close();
	}
	
	@FXML
	private void save(ActionEvent event) {
		try {
			prop.put("sender", txt_sender.getText());
			prop.put("smtp", txt_smtp.getText());
			prop.put("port", txt_port.getText());
			prop.put("user", txt_user.getText());
			prop.put("pass", txt_pass.getText());
			prop.put("profillink", txt_profil_link.getText());
			PropertyService.saveAppProperties(prop);
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
