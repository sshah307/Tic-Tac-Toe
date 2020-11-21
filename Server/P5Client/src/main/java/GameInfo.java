import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.ListView;
import javafx.util.Pair;

/*
 * Includes all the information that is going back and forth between client and server. 
 * 
 */

public class GameInfo implements Serializable {
	
	int p1Points;
	int p2Points;
	
	String p1rowcol = null;
	String p2rowcol = null;
	
	
    HashMap<Integer, Integer> scoreBoard = new HashMap<Integer, Integer>(); 
	
	int score;
	
	boolean error = false;
	
	boolean have2players;
	
	boolean p1PlayingAgain = false;
	boolean p2PlayingAgain = false;
	
	String message;
	String difficulty;
	
	boolean gameFinished = false;
	int winner = 999;
	
	int tictac[][];
	
	int clientid;
	
	
	
}
