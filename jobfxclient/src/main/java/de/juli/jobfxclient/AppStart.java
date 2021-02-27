package de.juli.jobfxclient;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.service.StageConfigService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class AppStart extends Application {
	private static final Logger LOG = LoggerFactory.getLogger(AppStart.class);
	private static Server server;
	private static Scene scene;
	private static Stage appStage;
	private static StageConfigService config;

	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/app.fxml"));
			scene = new Scene(root);
			scene.getStylesheets().add("/style.css");
			config = new StageConfigService(scene);
			appStage = config.config(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void disable() {
		getWindow().fireEvent(new WindowEvent(appStage, WindowEvent.WINDOW_HIDING));
	}

	public static void enable() {
		getWindow().fireEvent(new WindowEvent(appStage, WindowEvent.WINDOW_SHOWING));
	}

	public static void main(String[] args) {
		try {
			server = Server.createTcpServer(args).start();
			System.out.println();
			LOG.info("Running: {}", server.isRunning(true));
			LOG.info("Server: {}",server.getStatus());
		} catch (SQLException e) {
			LOG.error("{}" ,e);
		}
		launch(args);
		LOG.info("DONE");
	}

	public static Window getWindow() {
		return scene.getWindow();
	}

	public static Server getServer() {
		return server;
	}
}
