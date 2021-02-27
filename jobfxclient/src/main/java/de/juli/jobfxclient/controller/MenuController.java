package de.juli.jobfxclient.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.ProjectSettings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController extends Controller implements Initializable {
	private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);
	@FXML
	private MenuBar menuBar;
	@FXML
	private MenuItem menuItem_create;
	@FXML
	private MenuItem menuItem_show;
	@FXML
	private MenuItem menuItem_document;
	@FXML
	private MenuItem menuItem_document_del;
	@FXML
	private MenuItem menuItem_prop;
	@FXML
	private Pane pane_show;
	@FXML
	private Pane pane_create;
	@FXML
	private TextField txt_search;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.setMenuController(this);
		show(null);
	}

	@FXML
	private void exitClicked(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	private void create(ActionEvent event) {
		menuItem_show.setDisable(false);
		menuItem_create.setDisable(true);
		pane_show.setVisible(false);
		pane_create.setVisible(true);
	}

	@FXML
	private void show(ActionEvent event) {
		menuItem_show.setDisable(true);
		menuItem_create.setDisable(false);
		pane_create.setVisible(false);
		pane_show.setVisible(true);
	}

	@FXML
	private void search(ActionEvent event) {
		String text = txt_search.getText();
		super.getShowController().findByCompany(text);
	}

	@FXML
	private void clear(ActionEvent event) {
		txt_search.setText("");
	}

	@FXML
	private void document(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.initialDirectoryProperty().set(ProjectSettings.getDocRoot().toFile());
		List<File> files = chooser.showOpenMultipleDialog(null);
		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			try {
				Path source = Paths.get(file.toURI());
				Path target = Paths.get(ProjectSettings.getDocRoot() + "/" + fileName);
				Path copy = Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				LOG.info("File copied to: {}", copy);
				String reltaivPath = String.format("/%s/%s", ProjectSettings.getDocRoot().getFileName(), fileName);
//				Document doc = new Document(reltaivPath, fileName);
//				doc = docController.create(doc);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}

	@FXML
	private void delDocument(ActionEvent event) {
		File root = ProjectSettings.getDocRoot().toFile();
		FileChooser chooser = new FileChooser();
		chooser.initialDirectoryProperty().set(root);
		List<File> files = chooser.showOpenMultipleDialog(null);
		if (files == null) {
			return;
		}
		for (File file : files) {
			try {
				Files.delete(Paths.get(file.toURI()));
//				Document doc = docController.findByName(file.getName());
//				docController.remove(doc);
				pane_create.autosize();
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}

	@FXML
	private void openProperties() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/props.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Programmeigenschaften");
			stage.setScene(new Scene(root, 650, 450));
			stage.show();
			super.getPropController().setStage(stage);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			search(null);
		}
	}

	/**
	 * Kapselt create(event)
	 */
	public void switchEdit(ActionEvent event) {
		create(event);
		super.getCreateController().edit(event);
	}

	/**
	 * Kapselt show(event)
	 */
	public void switchShow(ActionEvent event) {
		show(event);
		super.getShowController().update();
	}

	public void switchNew(ActionEvent event) {
		create(event);
		super.getCreateController().newModel();
	}
}
