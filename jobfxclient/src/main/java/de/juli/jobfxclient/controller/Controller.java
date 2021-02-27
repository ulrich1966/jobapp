package de.juli.jobfxclient.controller;

import de.juli.jobmodel.controller.CompanyController;
import de.juli.jobmodel.controller.ContactController;
import de.juli.jobmodel.controller.DocumentController;
import de.juli.jobmodel.controller.JobController;
import de.juli.jobmodel.controller.SourceController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public abstract class Controller {
	protected DocumentController docController = new DocumentController();
	protected CompanyController companyController = new CompanyController();
	protected ContactController contactController = new ContactController();
	protected JobController jobController = new JobController();
	protected SourceController jobAdController = new SourceController();
	private static MenuController menuController;
	private static CreateController createController;
	private static ShowController showController;
	private static InfoController infoController;
	private static PropController propController;

	public void errorAlert(String header, String msg){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("FEHLER!");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public boolean confirmAlert(String header, String msg, String title){
		boolean submit = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.OK){
			submit = true;
		}
		return submit;
	}

	public MenuController getMenuController() {
		return Controller.menuController;
	}

	public void setMenuController(MenuController menuController) {
		Controller.menuController = menuController;
	}

	public static CreateController getCreateController() {
		return createController;
	}

	public static void setCreateController(CreateController createController) {
		Controller.createController = createController;
	}

	public static ShowController getShowController() {
		return showController;
	}

	public static void setShowController(ShowController showController) {
		Controller.showController = showController;
	}

	public static InfoController getInfoController() {
		return infoController;
	}

	public static void setInfoController(InfoController infoController) {
		Controller.infoController = infoController;
	}

	public SourceController getJobAdController() {
		return jobAdController;
	}

	public void setJobAdController(SourceController jobAdController) {
		this.jobAdController = jobAdController;
	}

	public static PropController getPropController() {
		return propController;
	}

	public static void setPropController(PropController propController) {
		Controller.propController = propController;
	}
}
