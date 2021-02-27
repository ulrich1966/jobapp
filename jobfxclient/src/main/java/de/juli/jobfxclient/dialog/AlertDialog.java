package de.juli.jobfxclient.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertDialog {

	public AlertDialog(){
	}
	
	public static void init(String msg, String title) {
		Alert alert = new Alert(AlertType.ERROR);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(AlertDialog.class.getResource("/image/error.png").toString()));
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
