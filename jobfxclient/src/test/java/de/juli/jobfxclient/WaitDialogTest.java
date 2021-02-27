package de.juli.jobfxclient;

import org.junit.Test;

import de.juli.jobfxclient.dialog.WaitDialog;
import javafx.application.Application;
import javafx.stage.Stage;

public class WaitDialogTest extends Application {

	@Test
	public void test() throws InterruptedException {
		launch(new String[] {""});
		//WaitDialog.init("bla", "blub");
	}

	@Override
	public void start(Stage stage) {
			try {
				WaitDialog.init("Bitte Warten bis die Daten gespeichert sind.", "");
				Thread.sleep(2000);
				boolean close = WaitDialog.enableClose("test");
				System.out.println(close);
			} catch (Exception e) {
				e.printStackTrace();
			}


//		StackPane root = new StackPane();
//
//	    BorderPane borderPane = new BorderPane();
//	    HBox headerBox = new HBox();
//	    headerBox.getStyleClass().add( "header-component" );
//	    headerBox.getChildren().addAll( GlyphsDude.createIcon(FontAwesomeIcon.BARS, "40px") );
//
//	    borderPane.setTop( headerBox );
//	    Label centerComponent = new Label( "CENTER COMPONENT" );
//	    centerComponent.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
//	    centerComponent.getStyleClass().add( "center-component" );
//	    borderPane.setCenter( centerComponent );
//	    root.getChildren().add( borderPane );
//
//	    Scene scene = new Scene( root );
//	    scene.getStylesheets().add( "style.css" );
//
//	    stage.setScene( scene );
//	    stage.setWidth( 300 );
//	    stage.setHeight( 400 );
//	    stage.setTitle( "JavaFX 8 app" );
//	    stage.show();


	}

}
