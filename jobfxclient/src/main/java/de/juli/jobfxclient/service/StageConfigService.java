package de.juli.jobfxclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.AppStart;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageConfigService {
	private static final Logger LOG = LoggerFactory.getLogger(StageConfigService.class);
	private Scene scene;

	public StageConfigService(Scene scene) {
		this.scene = scene;
	}

	public Stage config(Stage stage) {
		stage.setScene(scene);
		stage.setTitle("Verwaltung Jobsuche");
		stage.show();

		stage.setOnCloseRequest(event -> {
			System.out.println();
			LOG.info("CLOSING");
			if(AppStart.getServer() != null){
				AppStart.getServer().stop();
				LOG.info("Running: {}", AppStart.getServer().isRunning(true));
				LOG.info("Server: {}",AppStart.getServer().getStatus());
			}
			stage.close();
			Platform.exit();
			LOG.info("DONE");
			System.out.println();
			System.exit(0);
		});

		stage.setOnHiding(event -> {
			System.out.println("hiding");
		});

		stage.setOnShowing(event -> {
			System.out.println("showing");
		});

		return stage;
	}

}
