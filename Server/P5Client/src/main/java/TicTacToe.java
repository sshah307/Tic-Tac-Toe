import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RPLS extends Application {
	
	Scene main_client_scene;
	Scene main_second_client_scene;
	Scene main_third_client_scene;
	TextField client_port = new TextField("Enter port");
	TextField client_address = new TextField("Enter server Address");
	Button client_button;
	EventHandler<ActionEvent> handler1;
	EventHandler<ActionEvent> handler2;
	EventHandler<ActionEvent> handler3;
	EventHandler<ActionEvent> handler4;
	HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
	Client clientconnection;
	Label opponent_label = new Label("Opponent Played");
	ImageView opponent_played;
	HashMap<String, Image> imageMap = new HashMap<String, Image>();
	Button play_again = new Button("Play Again");
	
	ListView<String> client_messages = new ListView<String>();
	ListView<String> scoreBoard = new ListView<String>();
	
	
	MenuBar menu;
	Menu difficulty;
	MenuItem easy;
	MenuItem medium;
	MenuItem hard;
		
	ImageView l1img;
	ImageView l2img;
	ImageView l3img;
	ImageView r1img;
	ImageView r2img;
	ImageView r3img;
	ImageView m1img;
	ImageView m2img;
	ImageView m3img;
		
	
	int tictac[][]={
		    {0,0,0},
		    {0,0,0},
		    {0,0,0},
		};
	
	
	
	int score; 
	
	String current_choice = new String(); //for choosing the row/column
	
	Button l1 = new Button();
	Button l2 = new Button();
	Button l3 = new Button();

	Button m1 = new Button();
	Button m2 = new Button();
	Button m3 = new Button();

	
	Button r1 = new Button();
	Button r2 = new Button();
	Button r3 = new Button();
	
	Button quit_button = new Button("Quit");
	
	PauseTransition pause1 = new PauseTransition(Duration.seconds(3));
	PauseTransition pause2 = new PauseTransition(Duration.seconds(3));

	
	/*
	 * Will run in the beginning of the program, to access images easier later on.
	 */
	public void addImages() {
		
		 Image X = new Image("X.png");
		 Image O = new Image("O.jpg");
		 Image frame = new Image("frame.jpg");
		 Image XO = new Image("XO.png");
		 
		 imageMap.put("O", O);
		 imageMap.put("X", X);
		 imageMap.put("frame", frame);
		 imageMap.put("XO", XO);
		 
	}
	
	public void updateListView() {
		

		scoreBoard.getItems().clear();
		
        Iterator iter =  clientconnection.current.scoreBoard.entrySet().iterator(); 

        while (iter.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)iter.next(); 
            scoreBoard.getItems().add("Client: " + mapElement.getKey() + " score: " + mapElement.getValue());
        }

	}
	
	

	
	/*
	 * Checks if the user entered a valid response, will be used to redirect user if wrong response is entered. 
	 */
	public boolean integer_checker(String mystring) {
	    if(mystring == null || mystring.trim().isEmpty()) {
	        return false;
	    }
	    for (int i = 0; i < mystring.length(); i++) {
	    	
	    	if (mystring.charAt(i) == '.') {
	    		continue;
	    	}
	        if(!Character.isDigit(mystring.charAt(i))) {
	            return false;
	        } 
	    }
	    return true;
	}

	
	void updateBoard(GameInfo current) {
		
		ImageView X = new ImageView(imageMap.get("X"));
		X.setFitHeight(50);
		X.setFitWidth(69);
		
		if (current.p2rowcol.contains("l1")) {
			
			System.out.println("RUNS1");
			
			l1.setDisable(true);
			l1.setGraphic(X);

			tictac[0][0] = 2; //2 = server
			
		}
		
		else if (current.p2rowcol.contains("l2")) {
			
			System.out.println("RUNS2");

			
			l2.setDisable(true);
			l2.setGraphic(X);
			
			tictac[1][0] = 2;
			
		}
		
		else if (current.p2rowcol.contains("l3")) {
			
			System.out.println("RUNS3");

			l3.setDisable(true);
			l3.setGraphic(X);
			
			tictac[2][0] = 2;
			
		}
		
		else if (current.p2rowcol.contains("m1")) {
			
			System.out.println("RUNS4");

			
			m1.setDisable(true);
			m1.setGraphic(X);
			
			tictac[0][1] = 2;
			
		}
		
		else if (current.p2rowcol.contains("m2")) {
			
			System.out.println("RUNS5");

			
			m2.setDisable(true);
			m2.setGraphic(X);
			
			tictac[1][1] = 2;
			
		}
		
		else if (current.p2rowcol.contains("m3")) {
			
			System.out.println("RUNS6");

			
			m3.setDisable(true);
			m3.setGraphic(X);
			
			tictac[2][1] = 2;
			
		}
		
		else if (current.p2rowcol.contains("r1")) {
			
			System.out.println("RUNS7");

			
			r1.setDisable(true);
			r1.setGraphic(X);
			
			tictac[0][2] = 2;
			
		}
		
		else if (current.p2rowcol.contains("r2")) {
			
			System.out.println("RUNS8");

			
			r2.setDisable(true);
			r2.setGraphic(X);
			
			tictac[1][2] = 2;
			
		}
		
		else if (current.p2rowcol.contains("r3")) {
			
			System.out.println("RUNS9");

			
			r3.setDisable(true);
			r3.setGraphic(X);
			
			tictac[2][2] = 2;			
		}
		
		current_choice = null;
		
	}
	
	
	void sendornot() {
		
	if (!current_choice.isEmpty()  && !difficulty.getText().contains("Select Difficulty")) {
		
		for (int i = 0; i < tictac.length; i++) 
			  
            // Loop through all elements of current row 
            for (int j = 0; j < tictac[i].length; j++) 
                System.out.print(tictac[i][j] + " "); 
		
		
		GameInfo choice = new GameInfo();
		
		choice.score = score;
		choice.tictac = new int[tictac.length][];
		for(int i = 0; i < tictac.length; i++)
		    choice.tictac[i] = tictac[i].clone();
			
		choice.p1rowcol = current_choice;
		choice.difficulty = difficulty.getText();
		
		clientconnection.send(choice);
		current_choice = null;
				
	}
	
	
	else {
		
	client_messages.getItems().clear();
	
		l1.setDisable(false);
		l2.setDisable(false);
		l3.setDisable(false);
		m1.setDisable(false);
		m2.setDisable(false);
		m3.setDisable(false);
		r1.setDisable(false);
		r2.setDisable(false);
		r3.setDisable(false);
		
		

		l1.setGraphic(l1img);
		l2.setGraphic(l2img);
		l3.setGraphic(l3img);

		 m1.setGraphic(m1img);
		 m2.setGraphic(m2img);
		 m3.setGraphic(m3img);

		 r1.setGraphic(r1img);
		 r2.setGraphic(r2img);
		 r3.setGraphic(r3img);
		 
		
		 client_messages.getItems().add("Please select a difficulty first");
		
	
		
	}
	
	
	
	
		
	}
	
	
	
	
	/*
	 *  First client scene, includes basic textfields and button's to have user enter the text and join the server, includes client_button to finally connect to the server.
	 *  
	 */
	public Scene createMainClientScene() {
		
		Image pic1 = new Image("client_background.PNG"); //add image from resource folder.
		ImageView background_image = new ImageView(pic1);
		background_image.setFitHeight(800);
		background_image.setFitWidth(800);
		background_image.setPreserveRatio(true);
		
		client_button = new Button("Join");
		
		StackPane stack_pane = new StackPane();
		stack_pane.getChildren().addAll(background_image, client_button, client_port, client_address);
		
		client_button.setMaxWidth(395);
		client_button.setMaxHeight(35);
		
		client_button.setTranslateX(0);
		client_button.setTranslateY(120);
		
		client_port.setMaxWidth(405);
		client_port.setMaxHeight(40);
		
		client_port.setTranslateX(-3);
		client_port.setTranslateY(-17);
		
		client_address.setMaxWidth(405);
		client_address.setMaxHeight(40);
		
		client_address.setTranslateX(-3);
		client_address.setTranslateY(-97);
		
		client_button.setOnAction(handler1);
		
		HBox root = new HBox();
		root.getChildren().add(stack_pane);
		
		return new Scene(root, 800,800);
		
	}
	
	/*
	 * Second main client scene, includes logic about which images to display and check if it is a new game or current round. 
	 */
	public Scene createSecondMainClientScene() {
		
		Image pic1 = new Image("client_background_2.png"); //add image from resource folder.
		
		ImageView background_image = new ImageView(pic1);
		background_image.setFitHeight(800);
		background_image.setFitWidth(800);
		background_image.setPreserveRatio(true);
		
		ImageView frame_top = new ImageView(imageMap.get("frame"));
		
		
		frame_top.maxHeight(150);
		frame_top.maxWidth(200);
		frame_top.setTranslateX(-190);
		frame_top.setTranslateY(0);
		
		
		difficulty = new Menu("Select Difficulty");
		easy = new MenuItem("Easy");
		medium = new MenuItem("Medium");
		hard = new MenuItem("Hard");
		
		difficulty.getItems().addAll(easy, medium, hard);
		
		menu = new MenuBar();
		menu.getMenus().addAll(difficulty);
		
		
		menu.setTranslateX(300);
		menu.setTranslateY(-200);
		menu.setMaxWidth(100);
		

		client_messages.setMaxHeight(100);
		client_messages.setMaxWidth(150);
		client_messages.setTranslateX(100);
		client_messages.setTranslateY(-50);
		
		
		scoreBoard.setMaxHeight(100);
		scoreBoard.setMaxWidth(150);
		scoreBoard.setTranslateX(300);
		scoreBoard.setTranslateY(-50);
		
		
		easy.setOnAction((ActionEvent e) -> { //if user chooses scissors. 
			
			difficulty.setText("Easy");
			
        });
		
		medium.setOnAction((ActionEvent e) -> { //if user chooses scissors. 
			
			difficulty.setText("Medium");
			
        });

		hard.setOnAction((ActionEvent e) -> { //if user chooses scissors. 
	
			difficulty.setText("Hard");
	
		});
		
		
		l1img = new ImageView(imageMap.get("XO"));
		 l2img = new ImageView(imageMap.get("XO"));
		 l3img = new ImageView(imageMap.get("XO"));
		 r1img = new ImageView(imageMap.get("XO"));
		 r2img = new ImageView(imageMap.get("XO"));
		 r3img = new ImageView(imageMap.get("XO"));
		 m1img = new ImageView(imageMap.get("XO"));
		 m2img = new ImageView(imageMap.get("XO"));
	     m3img = new ImageView(imageMap.get("XO"));
		
						
		/*
		 * The following are positions of the widgets on the screen. 
		 */
		
		
		
		l1img.setFitHeight(50);
		l1img.setFitWidth(69);
		l2img.setFitHeight(50);
		l2img.setFitWidth(69);
		l3img.setFitHeight(50);
		l3img.setFitWidth(69);
		r1img.setFitHeight(50);
		r1img.setFitWidth(69);
		r2img.setFitHeight(50);
		r2img.setFitWidth(69);
		r3img.setFitHeight(50);
		r3img.setFitWidth(69);
		m1img.setFitHeight(50);
		m1img.setFitWidth(69);
		m2img.setFitHeight(50);
		m2img.setFitWidth(69);
		m3img.setFitHeight(50);
		m3img.setFitWidth(69);
		
		
		 
		 l1.setGraphic(l1img);
		 l2.setGraphic(l2img);
		 l3.setGraphic(l3img);

		 m1.setGraphic(m1img);
		 m2.setGraphic(m2img);
		 m3.setGraphic(m3img);

		 r1.setGraphic(r1img);
		 r2.setGraphic(r2img);
		 r3.setGraphic(r3img);

		 
		 l1.setPadding(Insets.EMPTY);
		 l2.setPadding(Insets.EMPTY);
		 l3.setPadding(Insets.EMPTY);
		 m1.setPadding(Insets.EMPTY);
		 m2.setPadding(Insets.EMPTY);
		 m3.setPadding(Insets.EMPTY);
		 r1.setPadding(Insets.EMPTY);
		 r2.setPadding(Insets.EMPTY);
		 r3.setPadding(Insets.EMPTY);

		 
			
		
		StackPane stack_pane = new StackPane();
		stack_pane.getChildren().addAll(background_image, frame_top, l1, l2, l3, m1, m2, m3, r1, r2, r3, menu, client_messages, scoreBoard);
		
		
		/*
		client_messages.setMaxHeight(150);
		client_messages.setMaxWidth(200);
		client_messages.setTranslateX(0);
		client_messages.setTranslateY(-25);
		
		
		opponent_played.setFitHeight(125);
		opponent_played.setFitWidth(150);
		opponent_played.setPreserveRatio(true);
		opponent_played.setTranslateX(260);
		opponent_played.setTranslateY(-25);
		
		opponent_label.setStyle("-fx-background-color: pink;");
		opponent_label.setTranslateX(263);
		opponent_label.setTranslateY(-100);
		
		*/
		
		
		
		/*
		 * END OF THE POSITIONS OF THE WIDGETS.
		 */
		
		
						
		l1.maxHeight(50);
		l1.setMaxWidth(69);
		l1.setTranslateX(-321);
		l1.setTranslateY(-125);
		
		l1.setOnMouseClicked((MouseEvent e) -> { 
			
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
			
			l1.setDisable(true);
			l1.setGraphic(O);
			
			// 0 = blank, 1 = client, 2 = server.
			
			tictac[0][0] = 1;
			current_choice = "l1";
			
			sendornot();
			
        });
		
		
		l2.maxHeight(50);
		l2.setMaxWidth(69);
		l2.setTranslateX(-321);
		l2.setTranslateY(0);
		
		l2.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
			
			l2.setDisable(true);

			
			l2.setGraphic(O);

			tictac[1][0] = 1;
			
			current_choice = "l2";
			sendornot();
			
        });
		
		l3.maxHeight(50);
		l3.setMaxWidth(69);
		l3.setTranslateX(-321);
		l3.setTranslateY(125);
		
		l3.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
	
			l3.setDisable(true);
	
			l3.setGraphic(O);

			tictac[2][0] = 1;
			
			current_choice = "l3";
			sendornot();
			
        });
		
		m1.maxHeight(50);
		m1.setMaxWidth(69);
		m1.setTranslateX(-200);
		m1.setTranslateY(-125);
		
		m1.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
		
			m1.setDisable(true);
	
			
			m1.setGraphic(O);

			tictac[0][1] = 1;
			
			current_choice = "m1";
			sendornot();
			
        });
		
		m2.maxHeight(50);
		m2.setMaxWidth(69);
		m2.setTranslateX(-200);
		m2.setTranslateY(0);
		
		m2.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
	
			m2.setDisable(true);
	
			
			m2.setGraphic(O);

			tictac[1][1] = 1;
			
			
			current_choice = "m2";
			sendornot();
			
        });
		
		m3.maxHeight(50);
		m3.setMaxWidth(69);
		m3.setTranslateX(-200);
		m3.setTranslateY(125);
		
		m3.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
	
			m3.setDisable(true);
			
			m3.setGraphic(O);

			tictac[2][1] = 1;
			
			current_choice = "m3";
			sendornot();
			
        });
		
		r1.maxHeight(50);
		r1.setMaxWidth(69);
		r1.setTranslateX(-79);
		r1.setTranslateY(-125);
		
		r1.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
	
			r1.setDisable(true);

			
			r1.setGraphic(O);

			tictac[0][2] = 1;
			
			current_choice = "r1";
			sendornot();
			
        });
		
		r2.maxHeight(50);
		r2.setMaxWidth(69);
		r2.setTranslateX(-79);
		r2.setTranslateY(0);
		
		r2.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
			
			r2.setGraphic(O);
			
		
	
			r2.setDisable(true);
	
			
			r2.setGraphic(O);

			tictac[1][2] = 1;
			
			current_choice = "r2";
			sendornot();
			
        });
		
		r3.maxHeight(50);
		r3.setMaxWidth(69);
		r3.setTranslateX(-79);
		r3.setTranslateY(125);
		
		r3.setOnMouseClicked((MouseEvent e) -> { 
            
			ImageView O = new ImageView(imageMap.get("O"));
			O.setFitHeight(50);
			O.setFitWidth(69);
			
			r3.setGraphic(O);

			r3.setDisable(true);
	
			
			tictac[2][2] = 1;
			
			r3.setGraphic(O);

			current_choice = "r3";
			sendornot();
			
        });
		
		
		HBox root = new HBox();
		root.getChildren().add(stack_pane);
		
		return new Scene(root, 800,800);
		
	}
	
	
	/*
	 * This is the third main scene for client. It includes two listView of what each client played, at each round. 
	 */
	public Scene createThirdMainClientScene() {
		
		
		Image pic1 = new Image("client_background_2.png"); //add image from resource folder.
		
		ImageView background_image = new ImageView(pic1);
		background_image.setFitHeight(800);
		background_image.setFitWidth(800);
		background_image.setPreserveRatio(true);

		
		play_again.setMaxHeight(50);
		play_again.setMaxWidth(200);
		play_again.setTranslateX(0);
		play_again.setTranslateY(-125);
		
		quit_button.setMaxHeight(50);
		quit_button.setMaxWidth(200);
		quit_button.setTranslateX(0);
		quit_button.setTranslateY(-75);
		
		StackPane stack_pane = new StackPane();
		stack_pane.getChildren().addAll(background_image, play_again, quit_button);
		
		play_again.setOnAction(handler2); //handler for playing game again. 
		quit_button.setOnAction(handler3);
		
		HBox root = new HBox();
		root.getChildren().add(stack_pane);
		
		return new Scene(root, 800,800);
			
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		addImages();
		
		//main handler for client_button, connects to a server on the port selected, and includes logic. 
		handler1 = new EventHandler<ActionEvent>() {
			
			
			public void handle(ActionEvent action) {
				
				client_button.setDisable(true);
				client_port.setDisable(true);
				client_address.setDisable(true);
				
				/*
				 * Checks if user entered a valid response, if not then re prompt the user to make a valid response. 
				 */
				if (!integer_checker(client_port.getText())) {
				
				client_port.setText("Please enter a real value");
				
				client_button.setDisable(false);
				client_port.setDisable(false);
				client_address.setDisable(false);
				
				
				primaryStage.setScene(sceneMap.get("main_client_scene"));
				primaryStage.show();
					
				}
				
				
				else { //user entered a valid response, and it is time to connect to the server. 
					
					// creating a new instance of the client class and passing in the port and the address of the server. 
					clientconnection = new Client(data -> {
						
						Platform.runLater(() -> {
							
							GameInfo current = clientconnection.current;
							score = current.score;
							updateListView();
							
							if (current.error == true) {
								
								
								client_messages.getItems().add("There was a error connecting to server");
								client_messages.getItems().add("Please re-connect, terminating in 5 seconds");
								
								client_address.setText("That is not a valid server/port, please enter a valid server address/port");
								client_port.setText("That is not a valid server/port, please enter a valid server address/port");
								
								
								main_second_client_scene = createSecondMainClientScene();
								sceneMap.replace("main_second_client_scene", main_second_client_scene);
								primaryStage.setScene(sceneMap.get("main_second_client_scene"));
								
								client_address.setDisable(false);
								client_port.setDisable(false);
								
								current.error = false;
								
								pause1.play();
								pause1.setOnFinished(handler4);
								
								
								
							}
							
							else {
							
							
								
							if ( (current.message != null)) {
								System.out.println("THIS RAN 3");
								
								client_messages.getItems().add(current.message);
							}
							
							else {
																						
							if (current.gameFinished == true) { //if the server determines that game is finished, then go to the third scene. 
								
								System.out.println("THIS RAN2");
								
								l1.setDisable(true);
								l2.setDisable(true);
								l3.setDisable(true);
								m1.setDisable(true);
								m2.setDisable(true);
								m3.setDisable(true);
								r1.setDisable(true);
								r2.setDisable(true);
								r3.setDisable(true);


								client_messages.getItems().clear();
								
								main_third_client_scene = createThirdMainClientScene();
								sceneMap.put("main_third_client_scene", main_third_client_scene);
								
								pause1.play();
								pause1.setOnFinished(e->primaryStage.setScene(sceneMap.get("main_third_client_scene")));
								
							}
							
							else {
							
						    // received move from server.
							
							if (current.p2rowcol != null) {
								
								updateBoard(current);
								

							}
				

								
							}
							
							
							}
							
						
							}
							
						});
							
						}, Integer.parseInt(client_port.getText()), client_address.getText());
					
					
					//Start the connection for the client.
					clientconnection.start();
					
					main_second_client_scene = createSecondMainClientScene();
					sceneMap.replace("main_second_client_scene", main_second_client_scene);
					primaryStage.setScene(sceneMap.get("main_second_client_scene"));									
					primaryStage.show();
															
					}
					
				}
				
			};
		
		/*
		 * The following handles playingAgain. 
		 */
		handler2 = new EventHandler<ActionEvent>() {
				
				
				public void handle(ActionEvent action) {
					
				
					for (int j = 0; j < tictac.length; j++) {
						
						
						for (int k = 0; k < tictac[j].length; k++) {
							
						tictac[j][k] = 0;
							
						}
					}
					
					l1.setDisable(false);
					l2.setDisable(false);
					l3.setDisable(false);
					m1.setDisable(false);
					m2.setDisable(false);
					m3.setDisable(false);
					r1.setDisable(false);
					r2.setDisable(false);
					r3.setDisable(false);
					

					main_second_client_scene = createSecondMainClientScene();
					sceneMap.replace("main_second_client_scene", main_second_client_scene);
					primaryStage.setScene(sceneMap.get("main_second_client_scene"));									
					primaryStage.show();
					
				}
				
		};
		
		handler3 = new EventHandler<ActionEvent>() {
			
			
			public void handle(ActionEvent action) {
				
			
				
				GameInfo playing_again = new GameInfo();
				playing_again.p1PlayingAgain = false;
				playing_again.message = "QUIT";
				
				clientconnection.send(playing_again);
				
				try {
					clientconnection.socketClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Platform.exit();
				System.exit(0);
				
				
			}
			
	};
	
	handler4 = new EventHandler<ActionEvent>() {
		
		
		public void handle(ActionEvent action) {
			
			try {
				clientconnection.socketClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Platform.exit();
			System.exit(0);
			
			
		}
		
};
		
		
		
		main_client_scene = createMainClientScene(); 
		main_second_client_scene = createSecondMainClientScene();
		
		sceneMap.put("main_client_scene", main_client_scene);
		sceneMap.put("main_second_client_scene", main_second_client_scene);
		
		primaryStage.setTitle("Client");
		primaryStage.setScene(sceneMap.get("main_client_scene"));
		primaryStage.show();
	}

}
