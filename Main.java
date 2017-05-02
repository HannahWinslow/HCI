package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.scene.text.Text;


public class Main extends Application {
	Stage myStage;
	Scene loginScene, mainScene, taskScene, userScene; 
	Image icon;
	@Override
	public void start(Stage primaryStage) {
		try {

			//Set up pages 
			loginPage();
			mainPage();
			
			//IGNORE THIS, I WILL REMOVE IT LATER, IT'S MY TESTING CLASS FOR THE DB - Josiah
			//System.out.println("Testing DB connection");
			//DBConn testDB = new DBConn();
			//testDB.accessDB();
			//System.out.println("DB connection passed");



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
			
			String enteredCode = passwordTextField.getText();
			String userName = userNameTextField.getText();
			//Eventually, this will need to switch to a different
			//scene based upon the level of the user, but for now,
			//we will just switch to a scene that has all access
			
			//THIS WILL RUN A QUERY TO SEE IF USER IS IN DATABASE, IF SO, WILL LOG THEM IN
			//make sure to keep track of their user permissions and only make buttons enabled if they have appropriate permissions
			
			//*************************************************************************
			//USER QUERY HERE
			
			
		    
		    
		    Connection conn;
		    
			try {
				
				conn=DriverManager.getConnection("jdbc:ucanaccess://C:/dbTest/teamDB.accdb");
				
				
				Statement st = conn.createStatement();
				String newQuery = "SELECT * FROM tblUsr WHERE usrLogin=\"" + userName + "\";";
				
				ResultSet results = st.executeQuery(newQuery);
				ResultSetMetaData resultsInfo = results.getMetaData();
				
				int numCols = resultsInfo.getColumnCount();
				boolean loggedIn = false;
				//System.out.println("Testing for user: " + userName + " and password: " + enteredCode);
				
				while (results.next()) {
					
				
					if (userName.equals(results.getString(7))  && enteredCode.equals(results.getString(5))) {
						//System.out.println(results.getString(7) + " = " + userName + " and " + results.getString(5) + " = " + enteredCode);
						
						//loggedIn = true;
						myStage.setScene(mainScene);
						loggedIn = true;
						//loginScene = new Scene(root,400,400);
						
					} else {
						//System.out.println("Results: " + results.getString(1) + ", " + results.getString(2) + ", " + results.getString(3)  + ", " + results.getString(4)  + ", " + results.getString(5)  + ", " + results.getString(6) + ", " + results.getString(7));
						
					}
				
				}
				
				conn.close();
				
				if (!loggedIn) {
					throw new SQLException();
				}
			} catch (SQLException sqlException) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Incorrect username and password!", "InfoBox: " + "Bad Data", JOptionPane.INFORMATION_MESSAGE);
			}
			
			//myStage.setScene(mainScene);

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

		//Image
		String path = "http://i.imgur.com/oOSjtuY.png";
		icon = new Image(path);

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
		Button userForm = new Button("Add Users");
		Button patientForm = new Button("Add Patients");
		Button trialForm = new Button("Add Clinical Trials");
		Button taskForm = new Button("Add Tasks");

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

		//Listeners for buttons
		patientDataButton.setOnAction(e -> {
			searchPatient();

		});
		
		taskForm.setOnAction(e -> {
			taskPage();

		});

		userForm.setOnAction(e -> {
			userPage();
		});

		patientForm.setOnAction(e -> {
			patientPage();
		});

		trialForm.setOnAction(e -> {
			trialPage();
		});



		//Create scene
		mainScene = new Scene(grid,400,400);


	}

	/*
	 * Author: Betsie Koshy 
	 * 
	 */
	public void userPage() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(12);

		HBox hbButtons = new HBox();
		hbButtons.setSpacing(10.0);

		grid.add(hbButtons, 8, 15, 15, 1);

		userScene = new Scene(grid,400,400);

		TableView<Person> table = new TableView<Person>();
		final ObservableList<Person> data =
				FXCollections.observableArrayList(
						new Person("Jacob", "Smith", "405-495-4343", "jSmith")
						);

		final HBox hb = new HBox();

		Scene scene = new Scene(new Group());
		Stage stage2 = new Stage();

		stage2.setTitle("User Information Table");
		stage2.setWidth(640);
		stage2.setHeight(550);

		final Label label = new Label("Users");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);
		Callback<TableColumn, TableCell> cellFactory =
				new Callback<TableColumn, TableCell>() {
			public TableCell call(TableColumn p) {
				return new EditingCell();
			}
		};

		TableColumn firstNameCol = new TableColumn("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(
				new PropertyValueFactory<Person, String>("firstName"));
		firstNameCol.setCellFactory(cellFactory);
		firstNameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						((Person) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setFirstName(t.getNewValue());
					}
				}
				);


		TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(
				new PropertyValueFactory<Person, String>("lastName"));
		lastNameCol.setCellFactory(cellFactory);
		lastNameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						((Person) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setLastName(t.getNewValue());
					}
				}
				);

		TableColumn emailCol = new TableColumn("Phone Number");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(
				new PropertyValueFactory<Person, String>("phoneNumber"));
		emailCol.setCellFactory(cellFactory);
		emailCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						((Person) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setPhoneNumber(t.getNewValue());
					}
				}
				);
		
		TableColumn userNameCol = new TableColumn("Username");
		userNameCol.setMinWidth(200);
		userNameCol.setCellValueFactory(
				new PropertyValueFactory<Person, String>("UserName"));
		userNameCol.setCellFactory(cellFactory);
		userNameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						((Person) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setUserName(t.getNewValue());
					}
				}
				);

		table.setItems(data);
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, userNameCol);

		final TextField addFirstName = new TextField();
		addFirstName.setPromptText("First Name");
		addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
		final TextField addLastName = new TextField();
		addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		addLastName.setPromptText("Last Name");
		final TextField addEmail = new TextField();
		addEmail.setMaxWidth(emailCol.getPrefWidth());
		addEmail.setPromptText("Phone Number");
		final TextField addUserName = new TextField();
		addUserName.setMaxWidth(userNameCol.getPrefWidth());
		addUserName.setPromptText("Username");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.add(new Person(
						addFirstName.getText(),
						addLastName.getText(),
						addEmail.getText(),
						addUserName.getText()));
				addFirstName.clear();
				addLastName.clear();
				addEmail.clear();
				addUserName.clear();
			} 
		});

		hb.getChildren().addAll(addFirstName, addLastName, addEmail, addUserName,addButton);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage2.setScene(scene);
		stage2.show();

	}

	public void trialPage() {

		TableView<ClinicalTrials> table = new TableView<ClinicalTrials>();
		final ObservableList<ClinicalTrials> data =
				FXCollections.observableArrayList(
						new ClinicalTrials("Trial", "Sponsor")
						);

		final HBox hb = new HBox();

		Scene scene = new Scene(new Group());
		Stage stage2 = new Stage();

		stage2.setTitle("Clinical Trials");
		stage2.setWidth(450);
		stage2.setHeight(550);

		final Label label = new Label("Clinical Trials");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);
		Callback<TableColumn, TableCell> cellFactory =
				new Callback<TableColumn, TableCell>() {
			public TableCell call(TableColumn p) {
				return new EditingCell();
			}
		};



		TableColumn trialCol = new TableColumn("Trial ");
		trialCol.setMinWidth(200);
		trialCol.setCellValueFactory(
				new PropertyValueFactory<ClinicalTrials, String>("trial"));
		trialCol.setCellFactory(cellFactory);
		trialCol.setOnEditCommit(
				new EventHandler<CellEditEvent<ClinicalTrials, String>>() {
					@Override
					public void handle(CellEditEvent<ClinicalTrials, String> t) {
						((ClinicalTrials) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setTrial(t.getNewValue());
					}
				}
				);

		TableColumn sponsorCol = new TableColumn("Sponsor");
		sponsorCol.setMinWidth(200);
		sponsorCol.setCellValueFactory(
				new PropertyValueFactory<ClinicalTrials, String>("sponsor"));
		sponsorCol.setCellFactory(cellFactory);
		sponsorCol.setOnEditCommit(
				new EventHandler<CellEditEvent<ClinicalTrials, String>>() {
					@Override
					public void handle(CellEditEvent<ClinicalTrials, String> t) {
						((ClinicalTrials) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setSponsor(t.getNewValue());
					}
				}
				);

		table.setItems(data);
		table.getColumns().addAll(trialCol, sponsorCol);

		
		final TextField addTrial = new TextField();
		addTrial.setMaxWidth(trialCol.getPrefWidth());
		addTrial.setPromptText("Trial");
		final TextField addSponsor = new TextField();
		addSponsor.setMaxWidth(sponsorCol.getPrefWidth());
		addSponsor.setPromptText("Sponsor");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.add(new ClinicalTrials(
						addTrial.getText(),
						addSponsor.getText()));
				addTrial.clear();
				addSponsor.clear();
			}
		});

		hb.getChildren().addAll(addTrial, addSponsor, addButton);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage2.setScene(scene);
		stage2.show();

	}



	public void taskPage() {
		Stage stage = new Stage();
		TableView<Tasks> table = new TableView<Tasks>();
	    final ObservableList<Tasks> data =
	            FXCollections.observableArrayList(
	            new Tasks("Hannah","Winslow", "Draw Blood", "8/12/2017")
	            );
	           
	    final HBox hb = new HBox();
	    Scene scene = new Scene(new Group());
        stage.setTitle("Tasks");
        stage.setWidth(550);
        stage.setHeight(550);
 
        final Label label = new Label("Tasks");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                       return new EditingCell();
                    }
                };
 
        TableColumn firstPatientCol = new TableColumn("First Name");
        firstPatientCol.setMinWidth(100);
        firstPatientCol.setCellValueFactory(
            new PropertyValueFactory<Tasks, String>("PatientFName"));
        firstPatientCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstPatientCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Tasks, String>>() {
                @Override
                public void handle(CellEditEvent<Tasks, String> t) {
                    ((Tasks) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPatientFName(t.getNewValue());
                }
            }
        );
        
        TableColumn lastPatientCol = new TableColumn("Last Name");
        lastPatientCol.setMinWidth(100);
        lastPatientCol.setCellValueFactory(
            new PropertyValueFactory<Tasks, String>("PatientLName"));
        lastPatientCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastPatientCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Tasks, String>>() {
                @Override
                public void handle(CellEditEvent<Tasks, String> t) {
                    ((Tasks) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPatientLName(t.getNewValue());
                }
            }
        );
 
 
        TableColumn taskNameCol = new TableColumn("Task Name");
        taskNameCol.setMinWidth(100);
        taskNameCol.setCellValueFactory(
            new PropertyValueFactory<Person, String>("TaskName"));
        taskNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Tasks, String>>() {
                @Override
                public void handle(CellEditEvent<Tasks, String> t) {
                    ((Tasks) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTaskName(t.getNewValue());
                }
            }
        );
 
        TableColumn dateCol = new TableColumn("Due Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(
            new PropertyValueFactory<Tasks, String>("TaskDueDate"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Tasks, String>>() {
                @Override
                public void handle(CellEditEvent<Tasks, String> t) {
                    ((Tasks) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTaskDueDate(t.getNewValue());
                }
            }
        );
 
        table.setItems(data);
        table.getColumns().addAll(firstPatientCol, lastPatientCol,taskNameCol, dateCol);
 
        final TextField addPatientFName = new TextField();
        addPatientFName.setPromptText("First Name");
        addPatientFName.setMaxWidth(firstPatientCol.getPrefWidth());
        final TextField addPatientLName = new TextField();
        addPatientLName.setPromptText("Last Name");
        addPatientLName.setMaxWidth(lastPatientCol.getPrefWidth());
        final TextField addTaskName = new TextField();
        addTaskName.setMaxWidth(taskNameCol.getPrefWidth());
        addTaskName.setPromptText("Task Name");
        final TextField addDate = new TextField();
        addDate.setMaxWidth(dateCol.getPrefWidth());
        addDate.setPromptText("Due Date");
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Tasks(
                        addPatientFName.getText(),
                        addPatientLName.getText(),
                        addTaskName.getText(),
                        addDate.getText()));
                addPatientFName.clear();
                addPatientLName.clear();
                addTaskName.clear();
                addDate.clear();
            }
        });
 
        hb.getChildren().addAll(addPatientFName, addPatientLName,addTaskName, addDate, addButton);
        hb.setSpacing(3);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();

	}

	//Class for tasks
	public static class Tasks {		
		private final SimpleStringProperty patientFName;
		private final SimpleStringProperty patientLName;
		private final SimpleStringProperty taskName;
		private final SimpleStringProperty taskDueDate;


		private Tasks(String patientFName,String patientLName, String name, String dueDate) {
			this.patientFName = new SimpleStringProperty(patientFName);
			this.patientLName = new SimpleStringProperty(patientLName);
			this.taskName = new SimpleStringProperty(name);
			this.taskDueDate = new SimpleStringProperty(dueDate);
		}

		public String getPatientFName() {
			return patientFName.get();
		}

		public void setPatientFName(String Name) {
			patientFName.set(Name);
		}
		
		public String getPatientLName() {
			return patientLName.get();
		}

		public void setPatientLName(String Name) {
			patientLName.set(Name);
		}


		public String getTaskName() {
			return taskName.get();
		}

		public void setTaskName(String name) {
			taskName.set(name);
		}
		public String getTaskDueDate() {
			return taskDueDate.get();
		}

		public void setTaskDueDate(String date) {
			taskDueDate.set(date);
		}


	}

	/*
	 * Class for users
	 * Author: Betsie Koshy 
	 * 
	 */
	public static class Person {

		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty phoneNumber;
		private final SimpleStringProperty userName;
		//private final SimpleStringProperty clinicalTrial;

		private Person(String fName, String lName, String phoneNumber, String userName) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.phoneNumber = new SimpleStringProperty(phoneNumber);
			this.userName = new SimpleStringProperty(userName);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String fName) {
			lastName.set(fName);
		}

		public String getPhoneNumber() {
			return phoneNumber.get();
		}

		public void setPhoneNumber(String fName) {
			phoneNumber.set(fName);
		}
		
		public String getUserName() {
			return userName.get();
		}
		
		public void setUserName(String aName) {
			userName.set(aName);
		}
	}

	class EditingCell extends TableCell<Person, String> {

		private TextField textField;

		public EditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createTextField();
				setText(null);
				setGraphic(textField);
				textField.selectAll();
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText((String) getItem());
			setGraphic(null);
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
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
			textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, 
						Boolean arg1, Boolean arg2) {
					if (!arg2) {
						commitEdit(textField.getText());
					}
				}
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}




	public static class ClinicalTrials {

		
		private final SimpleStringProperty trial;
		private final SimpleStringProperty sponsor;


		private ClinicalTrials(String trial, String sponsor) {
			
			this.trial = new SimpleStringProperty(trial);
			this.sponsor = new SimpleStringProperty(sponsor);
		}


		public String getTrial() {
			return trial.get();
		}

		public void setTrial(String ptrial) {
			trial.set(ptrial);
		}

		public String getSponsor() {
			return sponsor.get();
		}

		public void setSponsor(String sp) {
			sponsor.set(sp);
		}
	}
	/*
	    class EditingCell2 extends TableCell<ClinicalTrials, String> {
	        private TextField textField;
	        public EditingCell2() {
	        }
	        @Override
	        public void startEdit() {
	            if (!isEmpty()) {
	                super.startEdit();
	                createTextField();
	                setText(null);
	                setGraphic(textField);
	                textField.selectAll();
	            }
	        }
	        @Override
	        public void cancelEdit() {
	            super.cancelEdit();
	            setText((String) getItem());
	            setGraphic(null);
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
	                    setGraphic(null);
	                }
	            }
	        }
	        private void createTextField() {
	            textField = new TextField(getString());
	            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
	            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
	                @Override
	                public void changed(ObservableValue<? extends Boolean> arg0, 
	                    Boolean arg1, Boolean arg2) {
	                        if (!arg2) {
	                            commitEdit(textField.getText());
	                        }
	                }
	            });
	        }
	        private String getString() {
	            return getItem() == null ? "" : getItem().toString();
	        }
	    }
	 */

	 private final ObservableList<Patient> dataPatient =
			FXCollections.observableArrayList(
					new Patient("Jacob", "Smith", "123", "M", "surgery", "123"),
					new Patient("Isabella", "Johnson", "123", "M", "surgery", "123"),
					new Patient("Ethan", "Williams", "123", "M", "surgery", "123"),
					new Patient("Emma", "Jones", "123", "M", "surgery", "123"),
					new Patient("Michael", "Brown", "123", "M", "surgery", "123")
					);
	
	
	Scene sceneData;
	
	

	public void patientPage() {
		Stage newStage = new Stage();
		newStage.setTitle("Patients");
        BorderPane root = new BorderPane();
		sceneData = new Scene(root,1400,400);
		final Label label = new Label("Patients");
		label.setFont(new Font("Arial", 20));
		
		//A series of box to create the form
		HBox box = new HBox();
		VBox box3 = new VBox();
		HBox box1 = new HBox();
		
		//text field to add patient
		TextField textField = new TextField ();
		textField.setPromptText("First Name");
		TextField textField5 = new TextField();
		textField5.setPromptText("Last Name");
		TextField textField1 = new TextField ();
		textField1.setPromptText("Sponsor");
		TextField textField2 = new TextField ();
		textField2.setPromptText("Protocol");
		TextField textField3 = new TextField ();
		textField3.setPromptText("Case #");
		TextField textField4 = new TextField ();
		textField4.setPromptText("Notes");
		
		
		//create a table to see data
		TableView table = new TableView();
		table.setEditable(true);
		
		TableColumn nameCol = new TableColumn("First Name");
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("FirstName"));
		
		TableColumn lNameCol = new TableColumn("Last Name");
		lNameCol.setMinWidth(200);
		lNameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("LastName"));
		
	    TableColumn ssnCol = new TableColumn("Sponsor");
	    ssnCol.setMinWidth(200);
	    ssnCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Sponsor"));
		
	    TableColumn genderCol = new TableColumn("Protocol");
	    genderCol.setMinWidth(200);
	    genderCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Protocol"));
		
	    TableColumn protocolCol = new TableColumn("Case Number");
	    protocolCol.setMinWidth(200);
	    protocolCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("CaseNumber"));
	    
	    TableColumn protocolNumberCol = new TableColumn("Notes");
	    protocolNumberCol.setMinWidth(400);
	    protocolNumberCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Notes"));
		
	    table.getColumns().addAll(nameCol, lNameCol, ssnCol, genderCol, protocolCol, protocolNumberCol);
	    VBox boxTable = new VBox();
	    
	    //HERE IS WHERE THE DATA IS SET
	    
	    // **************************************************
//	    String ptFName = "";
//	    String ptLName = "";
//	    String ptSponsor = "";
//	    String ptNumber = "";
//	    String ptCaseNo = "";
//	    String ptNotes = "";
//	    ObservableList<Patient> ptList = FXCollections.observableArrayList();
//	    
//	    
//	    Connection conn;
//		try {
//			conn=DriverManager.getConnection("jdbc:ucanaccess://C:/dbTest/teamDB.accdb");
//			
//			Statement st = conn.createStatement();
//			String newQuery = "SELECT TblPatients.ptFName, TblPatients.ptLName, tblProtocol.protSponsor, tblProtocol.protNumber, TblPatients.ptCaseNo, TblPatients.ptNotes FROM tblProtocol INNER JOIN TblPatients ON tblProtocol.protProtocol = TblPatients.protProtocol;";
//			ResultSet results = st.executeQuery(newQuery);
//			ResultSetMetaData resultsInfo = results.getMetaData();
//			int numCols = resultsInfo.getColumnCount();
//			while (results.next()) {
//			
//				ptFName = results.getString(1);
//				ptLName = results.getString(2);
//				ptSponsor = results.getString(3);
//				ptNumber = results.getString(4);
//				ptCaseNo = results.getString(5);
//				ptNotes = results.getString(6);
//				Patient tempPatient = new Patient(ptFName, ptLName, ptSponsor, ptNumber, ptCaseNo, ptNotes);
//				ptList.add(tempPatient);
//			
//			}
//			
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
	    ObservableList<Patient> ptList = getPtList();
	    // **************************************************
	    table.setItems(ptList);
	    //boxTable.setSpacing(5);
	    //boxTable.setPadding(new Insets(10, 0, 0, 10));
	    boxTable.getChildren().addAll(label, table);
	    
	    
		
		Button sub = new Button( "Submit" );
		sub.setOnAction( (e) -> {
			
			//data.add(new Patient(textField.getText(), textField5.getText(), textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText()));
			String ptFName = textField.getText();
		    String ptLName = textField5.getText();
		    String ptSponsor = textField1.getText();
		    String ptNumber = textField2.getText();
		    String ptCaseNo = textField3.getText();
		    String ptNotes = textField4.getText();
		    //System.out.println("Adding new patient");
		    addNewPt(ptFName, ptLName, ptSponsor, ptNumber, ptCaseNo, ptNotes);
		    //System.out.println("New patient added");
		    
		    dataPatient.clear();
		    dataPatient.addAll(getPtList());
		    table.setItems(dataPatient);
			
			//addNewPt();
			textField.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField4.clear();
			textField5.clear();
			newStage.toFront();
		} );
		
		Button clear = new Button( "Clear" );
		clear.setOnAction( (e) -> {
			//System.out.println("Clear pressed!");
			textField.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField4.clear();
			textField5.clear();
		} );
		
		box1.getChildren().addAll(textField, textField5 ,textField1 ,textField2 ,textField3 ,textField4, sub, clear);
		box1.setSpacing(3);
		
		box3.getChildren().addAll(label, table, box1);
		root.setCenter(box3);
		newStage.setScene(sceneData);
		newStage.show();
	}
	
	public void addNewPt(String fName, String lName, String sponsor, String protocol, String caseNumber, String ptNotes) {
		String protKey = "";
		int protKeyInt = 1;
		String updateQry = "";
		String protKeyQuery = "SELECT protProtocol FROM tblProtocol WHERE protSponsor=\"";
		protKeyQuery += sponsor + "\" AND protNumber=\"" + protocol + "\";";
		
		//System.out.println(protKeyQuery);
		
		//create DB connection
		Connection conn;
		try {
			conn=DriverManager.getConnection("jdbc:ucanaccess://C:/dbTest/teamDB.accdb");
			
			Statement st = conn.createStatement();
			
			ResultSet results = st.executeQuery(protKeyQuery);
			ResultSetMetaData resultsInfo = results.getMetaData();
			int numCols = resultsInfo.getColumnCount();
			while (results.next()) {
				protKey = results.getString(1);
			}
			//protKeyInt = Integer.parseInt(protKey);
			//System.out.println("Using prot key of: " + protKey);
			
			updateQry = "INSERT INTO tblPatients (ptLName, ptFName, protProtocol, ptCaseNo, ptNotes) VALUES (\"" + lName + "\",\"" + fName + "\"," + protKey + ",\"" + caseNumber + "\",\"" + ptNotes  + "\");";
			//System.out.println(updateQry);
			st.executeUpdate(updateQry);
			//st.executeQuery(updateQry);
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The values you have entered are invalid! Likely there is no such protocol!", "InfoBox: " + "Bad Data", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		
	}
	
	public ObservableList<Patient> getPtList() {
		 String ptFName = "";
		    String ptLName = "";
		    String ptSponsor = "";
		    String ptNumber = "";
		    String ptCaseNo = "";
		    String ptNotes = "";
		    ObservableList<Patient> ptList = FXCollections.observableArrayList();
		    
		    
		    Connection conn;
			try {
				conn=DriverManager.getConnection("jdbc:ucanaccess://C:/dbTest/teamDB.accdb");
				
				Statement st = conn.createStatement();
				String newQuery = "SELECT TblPatients.ptFName, TblPatients.ptLName, tblProtocol.protSponsor, tblProtocol.protNumber, TblPatients.ptCaseNo, TblPatients.ptNotes FROM tblProtocol INNER JOIN TblPatients ON tblProtocol.protProtocol = TblPatients.protProtocol;";
				ResultSet results = st.executeQuery(newQuery);
				ResultSetMetaData resultsInfo = results.getMetaData();
				int numCols = resultsInfo.getColumnCount();
				while (results.next()) {
				
					ptFName = results.getString(1);
					ptLName = results.getString(2);
					ptSponsor = results.getString(3);
					ptNumber = results.getString(4);
					ptCaseNo = results.getString(5);
					ptNotes = results.getString(6);
					Patient tempPatient = new Patient(ptFName, ptLName, ptSponsor, ptNumber, ptCaseNo, ptNotes);
					ptList.add(tempPatient);
				
				}
				
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, "An unknown error occurred!", "InfoBox: " + "Bad Data", JOptionPane.INFORMATION_MESSAGE);
			}
			
			return ptList;
	}
	

	//Patient class
	public class Patient {
		private String firstName;
		private String lastName;
		private String sponsor;
		private String caseNumber;
		private String protocol;
		private String notes;

		private Patient(String fName, String lName, String sponsor, String protocol, String caseNumber, String notes) {
			this.firstName = fName;
			this.lastName = lName;
			this.sponsor = sponsor;
			this.caseNumber = caseNumber;
			this.protocol = protocol;
			this.notes = notes;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String fNameNew) {
			firstName = fNameNew;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lNameNew) {
			lastName = lNameNew;
		}

		public String getSponsor() {
			return sponsor;
		}

		public void setSponsor(String sponsorNew) {
			sponsor = sponsorNew;
		}

		public String getCaseNumber() {
			return caseNumber;
		}

		public void setCaseNumber(String newCaseNumber) {
			caseNumber = newCaseNumber;
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocolNew) {
			protocol = protocolNew;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String newNotes) {
			notes = newNotes;
		}
	}
	


	public void searchPatient() {
//		ObservableList<Patient> data1 =
//			FXCollections.observableArrayList(
//					new Patient("Jacob", "Smith", "123", "M", "surgery", "123"),
//					new Patient("Isabella", "Johnson", "123", "M", "surgery", "123"),
//					new Patient("Ethan", "Williams", "123", "M", "surgery", "123"),
//					new Patient("Emma", "Jones", "123", "M", "surgery", "123"),
//					new Patient("Michael", "Brown", "123", "M", "surgery", "123")
//					);
		ObservableList<Patient> data1 =FXCollections.observableArrayList();
		ObservableList<Patient> searchPatient =FXCollections.observableArrayList();
		
		Stage stage2 = new Stage();
		stage2.setTitle("Search Patient");
		BorderPane root = new BorderPane();
		sceneData = new Scene(root,800,400);
		final Label label = new Label("Patient");
		final Label label1 = new Label("Press the button for the field you want to search for or go back to main menu: ");
		label.setFont(new Font("Arial", 20));
		label1.setFont(new Font("Arial", 12));
		stage2.setScene(sceneData);
		//A series of box to create the form
		HBox box = new HBox();
		VBox box3 = new VBox();
		HBox box1 = new HBox();

		//text field to add patient
		TextField textField = new TextField ();
		textField.setPromptText("First Name");
		TextField textField5 = new TextField();
		textField5.setPromptText("Last Name");
		TextField textField1 = new TextField ();
		textField1.setPromptText("Sponsor");
		TextField textField2 = new TextField ();
		textField2.setPromptText("Case Number");
		TextField textField3 = new TextField ();
		textField3.setPromptText("Protocol");
		TextField textField4 = new TextField ();
		textField4.setPromptText("Note");


		//create a table to see data
		TableView table = new TableView();
		table.setEditable(true);

		Callback<TableColumn, TableCell> cellFactory =
				new Callback<TableColumn, TableCell>() {
			public TableCell call(TableColumn p) {
				return new EditingCell();
			}
		};

		TableColumn nameCol = new TableColumn("First Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("FirstName"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Patient, String>>() {
					@Override
					public void handle(CellEditEvent<Patient, String> t) {
						((Patient) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setFirstName(t.getNewValue());
					}
				}
				);

		TableColumn lastCol = new TableColumn("Last Name");
		lastCol.setMinWidth(100);
		lastCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("LastName"));
		lastCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Patient, String>>() {
					@Override
					public void handle(CellEditEvent<Patient, String> t) {
						((Patient) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setLastName(t.getNewValue());
					}
				}
				);

		TableColumn ssnCol = new TableColumn("Sponsor");
		ssnCol.setMinWidth(100);
		ssnCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Sponsor"));
		ssnCol.setCellFactory(TextFieldTableCell.forTableColumn());
		ssnCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Patient, String>>() {
					@Override
					public void handle(CellEditEvent<Patient, String> t) {
						((Patient) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setSponsor(t.getNewValue());
					}
				}
				);

		TableColumn genderCol = new TableColumn("Case Number");
		genderCol.setMinWidth(100);
		genderCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("CaseNumber"));
		genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
		genderCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Patient, String>>() {
					@Override
					public void handle(CellEditEvent<Patient, String> t) {
						((Patient) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setCaseNumber(t.getNewValue());
					}
				}
				);

		TableColumn protocolNumberCol = new TableColumn("Notes");
		protocolNumberCol.setMinWidth(300);
		protocolNumberCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Notes"));
		protocolNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
		protocolNumberCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Patient, String>>() {
					@Override
					public void handle(CellEditEvent<Patient, String> t) {
						((Patient) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setNotes(t.getNewValue());
					}
				}
				);

		TableColumn protocolCol = new TableColumn("protocol");
		protocolCol.setMinWidth(100);
		protocolCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Protocol"));
		protocolCol.setCellFactory(TextFieldTableCell.forTableColumn());
		protocolCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Patient, String>>() {
					@Override
					public void handle(CellEditEvent<Patient, String> t) {
						((Patient) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setProtocol(t.getNewValue());
					}
				}
				);

		table.getColumns().addAll(nameCol, lastCol, ssnCol, genderCol, protocolCol, protocolNumberCol);
		VBox boxTable = new VBox();
		//table.setItems(searchPatient);
		data1.addAll(getPtList());
		table.setItems(data1);
		boxTable.setSpacing(5);
		boxTable.setPadding(new Insets(10, 0, 0, 10));
		boxTable.getChildren().addAll(label, table);
		
		Button search = new Button( "Search" );
		search.setOnAction( (e) -> {
//			String newFName = "";
//			String newLName = "";
//			String newSponsor = "";
//			String NewCase = "";
//			String newProtocol = "";
//			String newNotes = "";
			
			table.setItems(getPtList());
			searchPatient.clear();
			for (int i = 0; i < data1.size(); i++)
			{
				String newFName = "";
				String newLName = "";
				String newSponsor = "";
				String newCase = "";
				String newProtocol = "";
				String newNotes = "";
				try {
					newFName = data1.get(i).getFirstName().toLowerCase();
					newLName = data1.get(i).getLastName().toLowerCase();
					newSponsor = data1.get(i).getSponsor().toLowerCase();
					newCase = data1.get(i).getCaseNumber().toLowerCase();
					newProtocol = data1.get(i).getProtocol().toLowerCase();
					newNotes = data1.get(i).getNotes().toLowerCase();
				} catch (Exception newNPE) {
					
				}
				
				
				if (newFName.equals(textField.getText().toLowerCase()))
				{
					searchPatient.add(new Patient(data1.get(i).getFirstName(), data1.get(i).getLastName(), data1.get(i).getSponsor(), data1.get(i).getCaseNumber(), data1.get(i).getProtocol(), data1.get(i).getNotes()));
				}
				if (newLName.equals(textField5.getText().toLowerCase()))
				{
					searchPatient.add(new Patient(data1.get(i).getFirstName(), data1.get(i).getLastName(), data1.get(i).getSponsor(), data1.get(i).getCaseNumber(), data1.get(i).getProtocol(), data1.get(i).getNotes()));
				}
				if (newSponsor.equals(textField1.getText().toLowerCase()))
				{
					searchPatient.add(new Patient(data1.get(i).getFirstName(), data1.get(i).getLastName(), data1.get(i).getSponsor(), data1.get(i).getCaseNumber(), data1.get(i).getProtocol(), data1.get(i).getNotes()));
				}
				if (newCase.equals(textField2.getText().toLowerCase()))
				{
					searchPatient.add(new Patient(data1.get(i).getFirstName(), data1.get(i).getLastName(), data1.get(i).getSponsor(), data1.get(i).getCaseNumber(), data1.get(i).getProtocol(), data1.get(i).getNotes()));
				}
				if (newProtocol.equals(textField3.getText().toLowerCase()))
				{
					searchPatient.add(new Patient(data1.get(i).getFirstName(), data1.get(i).getLastName(), data1.get(i).getSponsor(), data1.get(i).getCaseNumber(), data1.get(i).getProtocol(), data1.get(i).getNotes()));
				}
				if (newNotes.equals(textField4.getText().toLowerCase()))
				{
					searchPatient.add(new Patient(data1.get(i).getFirstName(), data1.get(i).getLastName(), data1.get(i).getSponsor(), data1.get(i).getCaseNumber(), data1.get(i).getProtocol(), data1.get(i).getNotes()));
				}
			}
			table.setItems(searchPatient);
			textField.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField4.clear();
			textField5.clear();
		} );
		
		Button clear = new Button( "Clear" );
		clear.setOnAction( (e) -> {
			textField.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField4.clear();
			textField5.clear();
		} );
		
		HBox searchBox = new HBox();
		
		Button searchName = new Button("Search First Name");
		searchName.setOnAction( (e) -> {
			searchBox.getChildren().clear();
			box3.getChildren().remove(searchBox);
			searchBox.getChildren().addAll(textField, search, clear);
			box3.getChildren().addAll(searchBox);
		});
		
		Button searchLName = new Button("Search Last Name");
		searchLName.setOnAction( (e) -> {
			searchBox.getChildren().clear();
			box3.getChildren().remove(searchBox);
			searchBox.getChildren().addAll(textField5, search, clear);
			box3.getChildren().addAll(searchBox);
		});
		
		Button searchSponsor = new Button("Search Sponsor");
		searchSponsor.setOnAction( (e) -> {
			searchBox.getChildren().clear();
			box3.getChildren().remove(searchBox);
			searchBox.getChildren().addAll(textField1, search, clear);
			box3.getChildren().addAll(searchBox);
		});
		
		Button searchCaseNumber = new Button("Search Case Number");
		searchCaseNumber.setOnAction( (e) -> {
			searchBox.getChildren().clear();
			box3.getChildren().remove(searchBox);
			searchBox.getChildren().addAll(textField2, search, clear);
			box3.getChildren().addAll(searchBox);
		});
		
		Button searchProtocol = new Button("Search Protocol");
		searchProtocol.setOnAction( (e) -> {
			searchBox.getChildren().clear();
			box3.getChildren().remove(searchBox);
			searchBox.getChildren().addAll(textField3, search, clear);
			box3.getChildren().addAll(searchBox);
		});

		Button back = new Button( "Back to Main Menu" );
		back.setOnAction( (e) -> {
			stage2.close();
			mainPage();
		} );

		box1.getChildren().addAll(searchName, searchLName, searchSponsor, searchCaseNumber, searchProtocol, back);
		box1.setSpacing(3);

		box3.getChildren().addAll(label, table, label1, box1);
		box3.setSpacing(10);
		root.setCenter(box3);
		stage2.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
