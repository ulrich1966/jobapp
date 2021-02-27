package de.juli.jobfxclient.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WaitDialog {
	private static Alert dialog;
	private static Stage stage;

	public static boolean enableClose(String msg) {
		System.out.println("enable");
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
		GridPane pane = new GridPane();
		pane.setMaxWidth(Double.MAX_VALUE);
		Label lbl = new Label(msg);
		pane.add(lbl, 0, 1);
		dialog.getDialogPane().setExpandableContent(pane);
		return false;
	}

	public static void init(String msg, String title) {
		dialog = new Alert(AlertType.INFORMATION);
		stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(new Image(AlertDialog.class.getResource("/image/time.png").toString()));
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.setTitle("Warten ...");
		dialog.setHeaderText(msg);
		dialog.setContentText("");
		dialog.show();
	}

	public static void close() {
		stage.close();
	}
}
