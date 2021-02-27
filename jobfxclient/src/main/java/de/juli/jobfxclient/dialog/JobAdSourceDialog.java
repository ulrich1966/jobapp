package de.juli.jobfxclient.dialog;

import de.juli.jobmodel.controller.SourceController;
import de.juli.jobmodel.model.Source;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class JobAdSourceDialog {
	private static Dialog<Source> dialog;
	private static TextField txt_name;
	private static TextField txt_pronomen;

	public JobAdSourceDialog(){
		init();
	}
	
	public static void init() {
		dialog = new Dialog<>();
		dialog.setTitle("Jobangebotsquelle");
		dialog.setHeaderText("Neue Jobangebotsquelle hinzfügen");
		GridPane grid = getGrid();
		dialog.getDialogPane().setContent(grid);
		ButtonType loginButtonType = new ButtonType("Speichern", ButtonData.APPLY);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		dialog.showAndWait();
		System.out.println(txt_name.getText()+" "+txt_pronomen.getText());
		if(!txt_name.getText().isEmpty() && !txt_pronomen.getText().isEmpty()) {
			SourceController controller = new SourceController();
			controller.persist(new Source(txt_pronomen.getText(), txt_name.getText()));			
		}
	}

	private static GridPane getGrid() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPrefWidth(300.0);;
		grid.setPrefHeight(100.0);;
		txt_name = new TextField();
		txt_name.setPrefWidth(200);
		txt_pronomen = new TextField();
		txt_pronomen.setPrefWidth(200);
		grid.add(new Label("Quelle:"), 0, 0);
		grid.add(txt_name, 1, 0);
		grid.add(new Label("Prefix:"), 0, 1);
		grid.add(txt_pronomen, 1, 1);
		return grid;
	}
}
