package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private Stage primaryStage;
	//private Composition composition = new Composition();
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		mainWindow();
	}
	
	public void mainWindow(){
		
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindowView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			MainWindowController mainWindowController  = loader.getController();
			mainWindowController.setMain(this);
			//mainWindowController.setCompositon(composition);
			
			Scene scene = new Scene(pane);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			mainWindowController.run();
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
