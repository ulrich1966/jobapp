package de.juli.jobfxclient.dialog;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PropertyDialog {
	private static Alert dialog;
	private static Stage stage;

	public static boolean enableClose(String msg) {
		System.out.println("enable");
		return false;
	}

	public static void init(String msg, String title) {
	}

	public static void close() {
		stage.close();
	}
}
