package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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

		//Listeners for buttons
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
						new Person("Jacob", "Smith", "405-495-4343")
						);

		final HBox hb = new HBox();

		Scene scene = new Scene(new Group());
		Stage stage2 = new Stage();

		stage2.setTitle("User Information Table");
		stage2.setWidth(450);
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

		table.setItems(data);
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		final TextField addFirstName = new TextField();
		addFirstName.setPromptText("First Name");
		addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
		final TextField addLastName = new TextField();
		addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		addLastName.setPromptText("Last Name");
		final TextField addEmail = new TextField();
		addEmail.setMaxWidth(emailCol.getPrefWidth());
		addEmail.setPromptText("Phone Number");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.add(new Person(
						addFirstName.getText(),
						addLastName.getText(),
						addEmail.getText()));
				addFirstName.clear();
				addLastName.clear();
				addEmail.clear();
			}
		});

		hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
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
						new ClinicalTrials("Name", "Trial", "Sponsor")
						);

		final HBox hb = new HBox();

		Scene scene = new Scene(new Group());
		Stage stage2 = new Stage();

		stage2.setTitle("Clinical Trials");
		stage2.setWidth(450);
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

		TableColumn nameCol = new TableColumn("Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<ClinicalTrials, String>("name"));
		nameCol.setCellFactory(cellFactory);
		nameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<ClinicalTrials, String>>() {
					@Override
					public void handle(CellEditEvent<ClinicalTrials, String> t) {
						((ClinicalTrials) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setName(t.getNewValue());
					}
				}
				);


		TableColumn trialCol = new TableColumn("Trial ");
		trialCol.setMinWidth(100);
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
		table.getColumns().addAll(nameCol, trialCol, sponsorCol);

		final TextField addName = new TextField();
		addName.setPromptText("Name");
		addName.setMaxWidth(nameCol.getPrefWidth());
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
						addName.getText(),
						addTrial.getText(),
						addSponsor.getText()));
				addName.clear();
				addTrial.clear();
				addSponsor.clear();
			}
		});

		hb.getChildren().addAll(addName, addTrial, addSponsor, addButton);
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
	            new Tasks("Hannah", "Draw Blood", "8/12/2017")
	            );
	           
	    final HBox hb = new HBox();
	    Scene scene = new Scene(new Group());
        stage.setTitle("Tasks");
        stage.setWidth(450);
        stage.setHeight(550);
 
        final Label label = new Label("Task Form");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                       return new EditingCell();
                    }
                };
 
        TableColumn firstPatientCol = new TableColumn("Patient Name");
        firstPatientCol.setMinWidth(100);
        firstPatientCol.setCellValueFactory(
            new PropertyValueFactory<Tasks, String>("PatientName"));
        firstPatientCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstPatientCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Tasks, String>>() {
                @Override
                public void handle(CellEditEvent<Tasks, String> t) {
                    ((Tasks) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPatientName(t.getNewValue());
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
        table.getColumns().addAll(firstPatientCol, taskNameCol, dateCol);
 
        final TextField addPatientName = new TextField();
        addPatientName.setPromptText("Patient Name");
        addPatientName.setMaxWidth(firstPatientCol.getPrefWidth());
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
                        addPatientName.getText(),
                        addTaskName.getText(),
                        addDate.getText()));
                addPatientName.clear();
                addTaskName.clear();
                addDate.clear();
            }
        });
 
        hb.getChildren().addAll(addPatientName, addTaskName, addDate, addButton);
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
		private final SimpleStringProperty patientName;
		private final SimpleStringProperty taskName;
		private final SimpleStringProperty taskDueDate;


		private Tasks(String patient, String name, String dueDate) {
			this.patientName = new SimpleStringProperty(patient);
			this.taskName = new SimpleStringProperty(name);
			this.taskDueDate = new SimpleStringProperty(dueDate);
		}

		public String getPatientName() {
			return patientName.get();
		}

		public void setPatientName(String Name) {
			patientName.set(Name);
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
		//private final SimpleStringProperty clinicalTrial;

		private Person(String fName, String lName, String phoneNumber) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.phoneNumber = new SimpleStringProperty(phoneNumber);
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

		private final SimpleStringProperty name;
		private final SimpleStringProperty trial;
		private final SimpleStringProperty sponsor;


		private ClinicalTrials(String name, String trial, String sponsor) {
			this.name = new SimpleStringProperty(name);
			this.trial = new SimpleStringProperty(trial);
			this.sponsor = new SimpleStringProperty(trial);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
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

	 private final ObservableList<Patient> data =
		        FXCollections.observableArrayList(
		            new Patient("Jacob", "Smith", "Hoo", "M", "surgery", "Ha"),
		            new Patient("Isabella", "Johnson", "123456", "M", "surgery", "123"),
		            new Patient("Ethan", "Williams", "123456", "M", "surgery", "123"),
		            new Patient("Emma", "Jones", "123456", "M", "surgery", "123"),
		            new Patient("Michael", "Brown", "123456", "M", "surgery", "123")
		        );
	
	
	Scene sceneData;

	public void patientPage() {
		Stage newStage = new Stage();
		newStage.setTitle("Patient");
        BorderPane root = new BorderPane();
		sceneData = new Scene(root,1400,400);
		final Label label = new Label("Patient");
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
		textField1.setPromptText("Social security number");
		TextField textField2 = new TextField ();
		textField2.setPromptText("Gender");
		TextField textField3 = new TextField ();
		textField3.setPromptText("Protocol");
		TextField textField4 = new TextField ();
		textField4.setPromptText("Protocol number");
		
		
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
			e.printStackTrace();
		}
	    
	    
	    // **************************************************
	    table.setItems(ptList);
	    //boxTable.setSpacing(5);
	    //boxTable.setPadding(new Insets(10, 0, 0, 10));
	    boxTable.getChildren().addAll(label, table);
	    
	    
		
		Button sub = new Button( "Submit" );
		sub.setOnAction( (e) -> {
			data.add(new Patient(textField.getText(), textField5.getText(), textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText()));
			textField.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField4.clear();
			textField5.clear();
		} );
		
		Button clear = new Button( "Clear" );
		sub.setOnAction( (e) -> {
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

	//Patient class
	public class Patient {
		private String firstName;
		private String lastName;
		private String sponsor;
		private String caseNumber;
		private String protocol;
		private String notes;

		private Patient(String fName, String lName, String sponsor, String caseNumber, String protocol, String notes) {
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

	public static void main(String[] args) {
		launch(args);
	}
}