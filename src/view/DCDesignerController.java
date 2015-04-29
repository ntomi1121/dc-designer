package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.sun.javafx.event.EventHandlerManager;

import model.Elemek;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import dc.designer.MainApp;

public class DCDesignerController {
	@FXML
	private TreeView<String> tvElements;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnProps;
	
	@FXML
	private TextArea taDescripton;
	
	@FXML
	private ImageView ivPicture;
	
	@FXML
	private MenuBar mbMenu;
	
	@FXML
	private MenuItem miClose;
	
	@FXML
	private MenuItem miSave;
	
	@FXML
	private MenuItem miSaveAs;
	
	@FXML
	private MenuItem miOpen;
	
	@FXML
	private AnchorPane apList;

	@FXML
	private AnchorPane apDesc;
	
	@FXML
	private Hyperlink hlElement;
	
	private MainApp mainApp;
	FileChooser fileChooser = new FileChooser();
	
	 List<Elemek> elemek = Arrays.<Elemek>asList(
	            new Elemek("f1", "Floor"),
	            new Elemek("f2", "Floor"),
	            new Elemek("c1", "Cabinet"),
	            new Elemek("c2", "Cabinet"),
	            new Elemek("r1", "Rack"),
	            new Elemek("ps1", "Poweer supply"),
	            new Elemek("cr1", "Computer room"));
	            
	public DCDesignerController(){
	}
	
	
	public MainApp getMainApp() {
		return mainApp;
	}
	
	@FXML
	private void initialize(){
	treeViewBuild();
	menuItemConfiguration();
	

	}
	private void treeViewBuild() {
		TreeItem<String> root = new TreeItem<>("Data Center");
		tvElements.setRoot(root);
		
		TreeItem<String> itemCroom = new TreeItem<>("Computer room");
		itemCroom.setExpanded(true);
		root.getChildren().add(itemCroom);
		/*
		TreeItem<String> itemFloor = new TreeItem<>("Floor");
		itemFloor.setExpanded(true);
		itemCroom.getChildren().add(itemFloor);
		
		TreeItem<String> itemCabinet = new TreeItem<>("Cabinet");
		itemCabinet.setExpanded(true);
		itemCroom.getChildren().add(itemCabinet);
		
		TreeItem<String> itemRack = new TreeItem<>("Rack");
		itemRack.setExpanded(true);
		itemCroom.getChildren().add(itemRack);
		
		TreeItem<String> itemPs = new TreeItem<>("Power supply");
		itemPs.setExpanded(true);
		itemCroom.getChildren().add(itemPs);
		*/
		System.out.println();
		for(Elemek elem: elemek){
			TreeItem<String> elemleaf = new TreeItem<String>(elem.getName());
			boolean found = false;
			for (TreeItem<String> typeNode : root.getChildren()) {
                if (typeNode.getValue().contentEquals(elem.getType())){
                	typeNode.getChildren().add(elemleaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> typeNode = new TreeItem<String>(
                    elem.getType()
          
                );
                root.getChildren().add(typeNode);
                typeNode.getChildren().add(elemleaf);
            }
		}
		tvElements.setEditable(true);
		tvElements.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	                return new TextFieldTreeCellImpl();
	            }
	        });
	}


	private void menuItemConfiguration() {
		//Bezárja az alkalmazást
		miClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		}); 
		//Megnyitja a kiválasztott fájlt
		miOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				fileChooserConfiguration();
				File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
				if(file != null){
				//ivPicture.setImage(file);
				}	
			}
		});
		miSaveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Save Image");
	            
	            File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			}
		});
		
	}


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void fileChooserConfiguration() {
		fileChooser.setTitle("Open Resource File");
	}
	
	
	private final class TextFieldTreeCellImpl extends TreeCell<String> {
		 
        private TextField textField;
        private ContextMenu modifyMenu = new ContextMenu();
        
        public TextFieldTreeCellImpl() {
        	MenuItem addMenuItem = new MenuItem("Add Element");
            modifyMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					TreeItem newElem = 
	                        new TreeItem<String>("Új elem");
	                            getTreeItem().getChildren().add(newElem);
	                    
	                    stageAdd();
					
				}
			

				private void stageAdd() {
					try {
						Stage newStage = new Stage();
						newStage.setTitle("Új elem hozzáadása");
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(MainApp.class.getResource("/view/NewElement.fxml"));
						AnchorPane newLayout;
						
						newLayout = (AnchorPane) loader.load();
						Scene scene = new Scene(newLayout);
						
						NewElementController controller = loader.getController();
				        List<String> tipusok = new ArrayList<String>();

						for(Elemek elem: elemek){
							for(TreeItem<String> typeNode : tvElements.getRoot().getChildren())
							if(typeNode.getValue().contentEquals(elem.getType())){
										tipusok.add(elem.getType().toString());
								}
							}
					
						System.out.println(tipusok);
						controller.setTipusok(tipusok);
						
						newStage.setScene(scene);
						newStage.setResizable(false);
						newStage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}


				
            });
            
            MenuItem deleteMenuItem = new MenuItem("Delete Element");
            modifyMenu.getItems().add(deleteMenuItem);
            deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent event) {
					  System.out.println(getTreeItem().toString());	
				}
            });
        }
 
        @Override
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
                if (
                        !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                    ){
                        setContextMenu(modifyMenu);
                        
                    }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }


	public TreeView<String> getTvElements() {
		return tvElements;
	}


	public void setTvElements(TreeView<String> tvElements) {
		this.tvElements = tvElements;
	}
	
	public AnchorPane getApList() {
		return apList;
	}


	public void setApList(AnchorPane apList) {
		this.apList = apList;
	}


	public AnchorPane getApDesc() {
		return apDesc;
	}


	public void setApDesc(AnchorPane apDesc) {
		this.apDesc = apDesc;
	}
	
}
