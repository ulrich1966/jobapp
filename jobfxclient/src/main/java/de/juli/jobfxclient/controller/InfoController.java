package de.juli.jobfxclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class InfoController extends Controller implements Initializable {
	//private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);
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
	private Label lbl_id;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setInfoController(this);
	}

	public void displayId(String id) {
		lbl_id.setText(id);
	}

	public void displayCount(String count, String sum) {
		lbl_count.setText(count);
		lbl_sum.setText(sum);
	}

	public void displayMsg(String msg) {
		if(msg != null && !msg.isEmpty()){
			lbl_error.setText(null);
			lbl_info.setText(msg);
		}
		lbl_error.setVisible(false);
		lbl_info.setVisible(true);
	}

	public void displayError(String error) {
		if(error != null && !error.isEmpty()){
			lbl_info.setText(null);
			lbl_error.setText(error);
		}
		lbl_info.setVisible(false);
		lbl_error.setVisible(true);
	}
}
