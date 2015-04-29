package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class NewElementController {
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnLocation;
	
	@FXML
	private ComboBox<String> cbTypes;
	
	@FXML
	private TextField tfQuantities;
	
	private DCDesignerController dController;
	private  List<String> tipusok = new ArrayList<String>();
	
	
	public NewElementController(){
		
	}
	@FXML
    private void initialize() {
		
		System.out.println(tipusok);
	    System.out.println(cbTypes.getItems().setAll(tipusok));
		buttonsConfiguration();
		
       
    }
	private void buttonsConfiguration() {
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			
			}
		});
		btnLocation.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				dController.getApList().setDisable(true);
				dController.getApDesc().setDisable(true);
			}
		});
		
	}
	
	
	public List<String> getTipusok() {
		return tipusok;
	}
	
	public void setTipusok(List<String> tipusok) {
		this.tipusok = tipusok;
	}
	

  
    
}
