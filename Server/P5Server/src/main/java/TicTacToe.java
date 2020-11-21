import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RPLS extends Application {
	
	Scene server_main;
	TextField server_port;
	Button server_button;
	EventHandler<ActionEvent> handler1;
	HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
    Server serverconnection;
	Scene main_server_scene;
	Scene second_main_server_scene;
	ListView<GameInfo> listItems = new ListView<GameInfo>();
	Label num_of_clients = new Label();
	ListView<String> top3 = new ListView<String>();
	HashMap<String, Image> imageMap = new HashMap<String, Image>();
	ListView<String> scoreBoardList = new ListView<String>();
	
    HashMap<Integer, Integer> scoreBoard = new HashMap<Integer, Integer>(); 

	
	/*
	 * Checks if the given string is an integer or not, used this to check if valid response was given in the input port.
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
	
	
	
public void updateListView() {
		

	scoreBoardList.getItems().clear();
		
        Iterator iter =  scoreBoard.entrySet().iterator(); 

        while (iter.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)iter.next(); 
            scoreBoardList.getItems().add("Client: " + mapElement.getKey() + " score: " + mapElement.getValue());
        }

	}
	



	
	/*
	 * Initializable of the hashmap and added all the pictures so I can find them later on. This run's first thing when server starts.
	 */
	public void addImages() {
		
		 Image X = new Image("X.png");
		 Image O = new Image("O.jpg");
		 
		 imageMap.put("O", O);
		 imageMap.put("X", X);
		 
	}
	
	
	public synchronized void orderMap() {
		
		
		Object[] a = scoreBoard.entrySet().toArray();
		Arrays.sort(a, new Comparator() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<Integer, Integer>) o2).getValue()
		                   .compareTo(((Map.Entry<Integer, Integer>) o1).getValue());
		    }
		});

	}
	

	boolean checkForDraw(String[] state)
	{
		for(int x = 0; x < state.length; x++)
		{
			if(state[x].equals("b"))
			{
				return false;
			}
		}
		
		return true;
	}
	
	int checkForWinner(String[] state)
	{
		
		if(checkForDraw(state))
			{
				return 0;
			}
		
		if(state[0].equals("O") && state[1].equals("O") && state[2].equals("O")) //horizontal top
		{
			return 1;
		}
		
		if(state[3].equals("O") && state[4].equals("O") && state[5].equals("O"))//horizontal middle
		{
			return 1;
		}

		if(state[6].equals("O") && state[7].equals("O") && state[8].equals("O"))//horizontal bottom
		{
			return 1;
		}

		if(state[0].equals("O") && state[3].equals("O") && state[6].equals("O"))//vert right
		{
			return 1;
		}

		if(state[1].equals("O") && state[4].equals("O") && state[7].equals("O"))//vert middle
		{
			return 1;
		}

		if(state[2].equals("O") && state[5].equals("O") && state[8].equals("O"))//vert left
		{
			return 1;
		}

		if(state[0].equals("O") && state[4].equals("O") && state[8].equals("O"))// diag from top left
		{
			return 1;
		}

		if(state[2].equals("O") && state[4].equals("O") && state[6].equals("O"))// diag from top right
		{
			return 1;
		}
		
		
		if(state[0].equals("X") && state[1].equals("X") && state[2].equals("X")) //horizontal top
		{
			return 2;
		}
		
		if(state[3].equals("X") && state[4].equals("X") && state[5].equals("X"))//horizontal middle
		{
			return 2;
		}

		if(state[6].equals("X") && state[7].equals("X") && state[8].equals("X"))//horizontal bottom
		{
			return 2;
		}

		if(state[0].equals("X") && state[3].equals("X") && state[6].equals("X"))//vert right
		{
			return 2;
		}

		if(state[1].equals("X") && state[4].equals("X") && state[7].equals("X"))//vert middle
		{
			return 2;
		}

		if(state[2].equals("X") && state[5].equals("X") && state[8].equals("X"))//vert left
		{
			return 2;
		}

		if(state[0].equals("X") && state[4].equals("X") && state[8].equals("X"))// diag from top left
		{
			return 2;
		}

		if(state[2].equals("X") && state[4].equals("X") && state[6].equals("X"))// diag from top right
		{
			return 2;
		}
		
		
		else {
			return 999;
		}

	}
	

	
	
	
	String getState(int tictacBoard[][]) {
		
		String state = "";
		
		for (int j = 0; j < tictacBoard.length; j++) {
			
			
			for (int k = 0; k < tictacBoard[j].length; k++) {
				
				if (tictacBoard[j][k] == 0) {
					
					state = state + "b ";
					
					
				}
				
				else if (tictacBoard[j][k] == 1) {
					
					state = state + "O ";
					
					
				}
				
				else if (tictacBoard[j][k] == 2) {
					
					state = state + "X ";
					
				}
				
			}
		}
		
		return state;

	}

/*
 *  Main server scene, using stack_pane. Includes background, and server_button which has it's own handler.
 */
public Scene createMainServerScene() {
	
	Image pic1 = new Image("server_background.PNG"); //add image from resource folder.
	ImageView background_image = new ImageView(pic1);
	background_image.setFitHeight(800);
	background_image.setFitWidth(800);
	background_image.setPreserveRatio(true);
	
	server_button = new Button("Turn on server");
	server_port = new TextField("Enter Port");
	
	StackPane stack_pane = new StackPane();
	stack_pane.getChildren().addAll(background_image, server_button, server_port);
	
	server_button.setMaxWidth(375);
	server_button.setMaxHeight(35);
	
	server_button.setTranslateX(0);
	server_button.setTranslateY(115);
	
	server_port.setMaxWidth(375);
	server_port.setMaxHeight(40);
	
	server_port.setTranslateX(-3);
	server_port.setTranslateY(-10);
	
	server_button.setOnAction(handler1);

	
	HBox root = new HBox();
	root.getChildren().add(stack_pane);
	
	return new Scene(root, 800,800);
	
}


/*
 * Main second server scene, first open's when the first client connects to the server. Includes some logic about what images to use when client
 * has not sent anything vs. when client sends something. 
 */
public Scene createSecondServerScene() {
		
	
	Image pic1 = new Image("server_background_2.png"); //add image from resource folder.
	
	ImageView background_image = new ImageView(pic1);
	background_image.setFitHeight(800);
	background_image.setFitWidth(800);
	background_image.setPreserveRatio(true);


	StackPane stack_pane = new StackPane();
	stack_pane.getChildren().addAll(background_image, scoreBoardList, num_of_clients);
	
	/*
	 *  setting all widget's positions. 
	 */
	
	num_of_clients.setTranslateX(-125);
	num_of_clients.setTranslateY(-200);
	num_of_clients.setStyle("-fx-background-color:   pink;");
		
	scoreBoardList.setMaxHeight(250);
	scoreBoardList.setMaxWidth(300);
	scoreBoardList.setTranslateX(0);
	scoreBoardList.setTranslateY(75);
	
	
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
		// TODO Auto-generated method stub
		addImages();
		
		/*
		 * EventHandler for server_button, allow's for server to start.
		 */
		handler1 = new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent action) {
				
				server_port.setDisable(true); //disabling server_port/button (optional)
				server_button.setDisable(true);
				
				if (!integer_checker(server_port.getText())) { //checking to see if it is a valid response, if it's not then we need a valid response.
				
				server_port.setText("Please enter a real value");
				
				
				main_server_scene = createMainServerScene();
				sceneMap.replace("main_server_scene", main_server_scene);
				primaryStage.setScene(sceneMap.get("main_server_scene"));	
				primaryStage.show();
					
				}
				
				else {
					serverconnection = new Server(data -> { //creating a new instance of the Server class and passing in the port. 
						
						Platform.runLater(() -> { //this method runs, everything client.accept() is called in the TheServer thread, and also ClientThread.
																		
							synchronized(this) {
							
							num_of_clients.setText("Total Clients Connected: " + serverconnection.clients.size());

								
								if (!integer_checker(data.toString())) {
									
									second_main_server_scene = createSecondServerScene();
									sceneMap.replace("second_main_server_scene", second_main_server_scene);
									primaryStage.setScene(sceneMap.get("second_main_server_scene"));	
									
									return;
								}
								
								int i = (int) data - 1;
	
									if (serverconnection.clients.get(i).current.p1rowcol != null) {
									
									String state = getState(serverconnection.clients.get(i).current.tictac);

									System.out.println(state);
									AI_MinMax startThis = new AI_MinMax(state); //need string of the board
									int serverMove = -1;
									
									String names = state;
							        String[] namesArray = null;
							         
							        //Split string with comma
							        namesArray = names.split(" ");
							         
									int winner = checkForWinner(namesArray);
									
									if (winner == 1 || winner == 2 || winner == 0) {
									

										switch(winner) {
											case 1:
												serverconnection.clients.get(i).current.winner = 1;
												serverconnection.clients.get(i).current.score = serverconnection.clients.get(i).current.score + 1;

												break;
											case 2:
												serverconnection.clients.get(i).current.winner = 2;
												break;
											case 0:
												serverconnection.clients.get(i).current.winner = 0;
												break;
									}
										
										
									if (scoreBoard.get(serverconnection.clients.get(i).current.clientid) == null) {
											scoreBoard.put(serverconnection.clients.get(i).current.clientid, serverconnection.clients.get(i).current.score);
									}
										
									else {
											
										scoreBoard.replace(serverconnection.clients.get(i).current.clientid, serverconnection.clients.get(i).current.score);
									}
									
									orderMap();
											
									serverconnection.clients.get(i).current.scoreBoard = scoreBoard;
									
									serverconnection.clients.get(i).current.gameFinished = true;
									updateListView();
									serverconnection.updateClient(i);
									return;
									}
									
									updateListView();
									serverconnection.clients.get(i).current.scoreBoard = scoreBoard;

									
									if (serverconnection.clients.get(i).current.difficulty.contains("Easy")) {
										
										serverMove = startThis.getEasyMove();
										
									}
									
									else if (serverconnection.clients.get(i).current.difficulty.contains("Medium")) {
										
										serverMove = startThis.getNextMove();
										
									}
									
									else if (serverconnection.clients.get(i).current.difficulty.contains("Hard")) {
									
										serverMove = startThis.getRandomNumber();
										
									}
																		
									System.out.println("CHOSEN " + serverMove);
									
									switch (serverMove) {
									
									case 1:
										serverconnection.clients.get(i).current.p2rowcol = "l1";
										break;
									case 2:
										serverconnection.clients.get(i).current.p2rowcol = "m1";
										break;
									case 3:
										serverconnection.clients.get(i).current.p2rowcol = "r1";
										break;
									case 4:
										serverconnection.clients.get(i).current.p2rowcol = "l2";
										break;
									case 5:
										serverconnection.clients.get(i).current.p2rowcol = "m2";
										break;
									case 6:
										serverconnection.clients.get(i).current.p2rowcol = "r2";
										break;
									case 7:
										serverconnection.clients.get(i).current.p2rowcol = "l3";
										break;
									case 8:
										serverconnection.clients.get(i).current.p2rowcol = "m3";
										break;
									case 9:
										serverconnection.clients.get(i).current.p2rowcol = "r3";
										break;
									default:
										serverconnection.clients.get(i).current.p2rowcol = null;
										break;
									
									}
									
									serverconnection.updateClient(i);
									
									
									}
									
								}
	
								
							//}
							
														
							second_main_server_scene = createSecondServerScene();
							sceneMap.replace("second_main_server_scene", second_main_server_scene);
							primaryStage.setScene(sceneMap.get("second_main_server_scene"));						});
							
						}, Integer.parseInt(server_port.getText()));
					
					}
					
				}
				
			};
		
		/* 
		 * The following code runs beginning of the program.
		 */
		main_server_scene = createMainServerScene();
		sceneMap.put("main_server_scene", main_server_scene);
		sceneMap.put("second_main_server_scene", second_main_server_scene);
		
		primaryStage.setTitle("Server");
		primaryStage.setScene(sceneMap.get("main_server_scene"));
		primaryStage.show();
		
	}

}
