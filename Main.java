package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class Main extends Application {
	 private final ObservableList<Person> data =
		        FXCollections.observableArrayList(
		            new Person("Jacob", "Smith", "jacob.smith@example.com"),
		            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
		            new Person("Ethan", "Williams", "ethan.williams@example.com"),
		            new Person("Emma", "Jones", "emma.jones@example.com"),
		            new Person("Michael", "Brown", "michael.brown@example.com")
		        );
	@Override
	public void start(Stage primaryStage) {
		try {
			//create a table to see data
			TableView table = new TableView();
			Label label = new Label("Address Book");
			table.setEditable(true);
			TableColumn nameCol = new TableColumn("First Name");
			nameCol.setMinWidth(100);
			nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		    TableColumn ssnCol = new TableColumn("Social security number");
		    TableColumn genderCol = new TableColumn("Gender");
		    TableColumn protocolNumberCol = new TableColumn("Protocol Number");
		    TableColumn protocolCol = new TableColumn("protocol");
		    table.getColumns().addAll(nameCol, ssnCol, genderCol, protocolCol, protocolNumberCol);
		    VBox boxTable = new VBox();
		    table.setItems(data);
		    boxTable.setSpacing(5);
		    boxTable.setPadding(new Insets(10, 0, 0, 10));
		    boxTable.getChildren().addAll(label, table);
	        
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			
			//A series of box to create the form
			HBox box = new HBox();
			VBox box3 = new VBox();
			HBox box1 = new HBox();
			HBox box2 = new HBox();
			HBox box4 = new HBox();
			HBox box5 = new HBox();
			HBox box6 = new HBox();
			
			//create a test menu bar
			MenuBar bar = new MenuBar();
			Menu file = new Menu( "File" );
			MenuItem addInfo = new MenuItem( "Add Patient" );
			MenuItem seeData = new MenuItem( "Data Check" );
			seeData.setOnAction( (e) -> {
				root.setCenter(boxTable);
			} );
			MenuItem quit = new MenuItem( "Quit" );
			quit.setOnAction( (e) -> {
				primaryStage.close();
			} );
			file.getItems().add( addInfo );
			file.getItems().add( seeData );
			file.getItems().add( quit );
			bar.getMenus().addAll(file);
			
			Button sub = new Button( "Submit" );
			Button clear = new Button( "Clear" );
			box.getChildren().addAll(sub, clear);
			box.setSpacing(10);
			Label label1 = new Label("Name: ");
			Label label2 = new Label("Social security number: ");
			Label label3 = new Label("Gender: ");
			Label label4 = new Label("Protocol: ");
			Label label5 = new Label("Protocol number: ");
			
			TextField textField = new TextField ();
			TextField textField1 = new TextField ();
			TextField textField2 = new TextField ();
			TextField textField3 = new TextField ();
			TextField textField4 = new TextField ();
			box1.getChildren().addAll(label1, textField);
			box1.setSpacing(10);
			box2.getChildren().addAll(label2, textField1);
			box2.setSpacing(10);
			box4.getChildren().addAll(label3, textField2);
			box4.setSpacing(10);
			box5.getChildren().addAll(label4, textField3);
			box5.setSpacing(10);
			box6.getChildren().addAll(label5, textField4);
			box6.setSpacing(10);
			box3.getChildren().addAll(box1, box2, box4, box5, box6);
			
			root.setCenter(box3);
			root.setTop(bar);
			root.setBottom(box);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public class Person {
		 private  String firstName;
	     private  String lastName;
	     private  String email;

	     private Person(String fName, String lName, String email) {
	         this.firstName = new String(fName);
	         this.lastName = new String(lName);
	         this.email = new String(email);
	     }

	     public String getFirstName() {
	         return firstName;
	     }

	     public void setFirstName(String fName) {
	         firstName = fName;
	     }

	     public String getLastName() {
	         return lastName;
	     }

	     public void setLastName(String fName) {
	         lastName = fName;
	     }

	     public String getEmail() {
	         return email;
	     }

	     public void setEmail(String fName) {
	         email = fName;
	     }
	}
}
