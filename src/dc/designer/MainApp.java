package dc.designer;

import java.io.IOException;
import java.net.URL;

import view.DCDesignerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
    private AnchorPane rootLayout;
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Data center designer");
        
        initrootLayout();

	}

	public void initrootLayout() {
		// TODO Auto-generated method stub
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/DcDesignerGUI2.fxml"));
		
		rootLayout = (AnchorPane) loader.load();
		Scene scene = new Scene(rootLayout);
		
		DCDesignerController controller = loader.getController();
		controller.setMainApp(this);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);	
		primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
