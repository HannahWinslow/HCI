package application;
	




import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import javafx.scene.text.Text;


public class Main extends Application {
	Stage myStage;
	Scene loginScene, mainScene;
	Image icon;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Set up login page and main page
			loginPage();
			mainPage();
			
			
			
			//So the stage can be used by any method
			myStage = primaryStage;
		
			
			
			
			//Set details of primary stage
			primaryStage.setTitle("Patient Data Tool");
			primaryStage.setScene(loginScene);			//Initial scene
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loginPage() {
		
		//Create new layout for login page
		GridPane root = new GridPane();		
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));
		
		//Sign in title
		Text scenetitle = new Text("Sign In");
		scenetitle.setFont(Font.font("Calibri", 25));
		root.add(scenetitle, 0, 0, 2, 1);
		
		//Labels and Text Fields for User Name and Password
		Label userNameLabel = new Label("User Name:");
		root.add(userNameLabel, 0, 1);

		TextField userNameTextField = new TextField();
		root.add(userNameTextField, 1, 1);

		Label passwordLabel = new Label("Password:");
		root.add(passwordLabel, 0, 2);

		PasswordField passwordTextField = new PasswordField();
		root.add(passwordTextField, 1, 2);
		
		
		//Create Sign in Button
		Button signInButton = new Button("Sign in");
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_RIGHT);
		hBox.getChildren().add(signInButton);
		root.add(hBox, 1, 4);
		
		//Event handler for Sign in button
		signInButton.setOnAction(e -> {
			//Eventually, this will need to switch to a different
			//scene based upon the level of the user, but for now,
			//we will just switch to a scene that has all access
			myStage.setScene(mainScene);
			
        });
		
		//Create scene
		loginScene = new Scene(root,400,400);
	}
	
	public void mainPage() {
		
		//Create layout for main page
		GridPane grid = new GridPane();		//Layout for main menu
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//Label for image
		icon = new Image(getClass().getResourceAsStream("medical icon.png"));
		
		Label i = new Label();
		
		i.setGraphic(new ImageView(icon));
		grid.add(i,0,0);
		i.setPadding(new Insets(0,0,0,40));

		
		//Create buttons for all forms
		Button patientDataButton = new Button("Search Patient Data");
		patientDataButton.setMinHeight(40);
		patientDataButton.setFont(Font.font("Calibri", 15));
		patientDataButton.setMinWidth(200);
		grid.add(patientDataButton, 0, 2, 2, 1);
		Button userForm = new Button("User Form");
		Button patientForm = new Button("Patient Form");
		Button trialForm = new Button("Clinical Trial Form");
		Button taskForm = new Button("Task Form");
		
		//Make all buttons same width
		userForm.setMaxWidth(Double.MAX_VALUE);
		patientForm.setMaxWidth(Double.MAX_VALUE);
		trialForm.setMaxWidth(Double.MAX_VALUE);
		taskForm.setMaxWidth(Double.MAX_VALUE);
		
		//Add all buttons to grid
		grid.add(patientForm, 0, 4, 2, 1);
		grid.add(userForm, 0, 5, 2, 1);
		grid.add(trialForm, 0, 6, 2, 1);
		grid.add(taskForm, 0, 7, 2, 1);
		
		
		
		//Create scene
		mainScene = new Scene(grid,400,400);
		
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
