package de.juli.jobfxclient.util;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;

public class AppProperties {
	private final static String APP_NAME = "Bewerbungsapp";
	private final ReadOnlyStringWrapper appNameValue;
	private final static String ROOT_NAME = AppProperties.class.getResource("/").toString();
	private ReadOnlyStringWrapper docRoot;
	private final static String APPDOC_ROOT_NAME = AppProperties.class.getResource("/appdocs").toString();
	private ReadOnlyStringWrapper appDocRoot;
	private final static SimpleBooleanProperty ODT_MAIL_FLAG = new SimpleBooleanProperty(false);
	private final static SimpleBooleanProperty TXT_MAIL_FLAG = new SimpleBooleanProperty(true);
	private final static SimpleBooleanProperty OPEN_EXP_AFTER_SAVE = new SimpleBooleanProperty(false);

	public AppProperties(){
		appNameValue = new ReadOnlyStringWrapper(this, APP_NAME, "");
		docRoot = new ReadOnlyStringWrapper(this, ROOT_NAME, "");
		appDocRoot = new ReadOnlyStringWrapper(this, APPDOC_ROOT_NAME, "");
	}

	public static String getAppName() {
		return APP_NAME;
	}

	public ReadOnlyStringWrapper getAppNameValue() {
		return appNameValue;
	}

	public static String getRootName() {
		return ROOT_NAME;
	}

	public static String getAppdocRootName() {
		return APPDOC_ROOT_NAME;
	}

	public ReadOnlyStringWrapper getDocRoot() {
		return docRoot;
	}

	public ReadOnlyStringWrapper getAppDocRoot() {
		return appDocRoot;
	}

	public static SimpleBooleanProperty getOdtMailFlag() {
		return ODT_MAIL_FLAG;
	}

	public static SimpleBooleanProperty getTxtMailFlag() {
		return TXT_MAIL_FLAG;
	}

	public static SimpleBooleanProperty getOpenExpAfterSave() {
		return OPEN_EXP_AFTER_SAVE;
	}
}
