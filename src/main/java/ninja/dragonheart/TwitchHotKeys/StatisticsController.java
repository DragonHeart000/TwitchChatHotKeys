package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class StatisticsController {
	//TODO add back button
	
	//FXML vars
	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private ListView<?> mostTalketiveUsersListView;
	
	
	/*
	 * For the sake of keeping things organized I created another controller for each page
	 * the issue with this is the menu bar is almost 100% the same throughout (other than loadSaved
	 * change to loadSavedAfterStart since the program will need to stop the running bot before
	 * starting a new one). Because of this I copy pasted the code over to here. It should be possible
	 * to have the menu on it's own controller so this does not need to be in every controller but that
	 * is not top priority. But I will add it to the todos.
	 * TODO make a controller for the menu bar separate to clean up code.
	 */
	
	/////////////////////////////////////Menu Bar///////////////////////////////////////
	
	//////File Menu//////
	
	public void newFromMenu(){
		//End current instances of bot and key listener
		try {
			System.out.println("Calling thread kill");
			if (MakeBot.checkThread()){
				MakeBot.killThread();
				KeyListener.endKeyListener();
			}
		} catch (Exception e){
			ErrorHandling.error(e, "There could be an error as the program did not close properly!");
			System.out.println("There could be an error as the program did not close properly!");
		}
		System.out.println("Switching scenes");
		//Switch scene
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("LoginScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from start()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
			e.printStackTrace();
		}
		
	}
	
	public void loadSavedAfterStart(){
		//TODO this will be done a bit differently from here.
	}
	
	public void quit(){
		Platform.exit();
	}
	
	//////View Menu//////
	
	public void goToStats(){
		//There already
		/*
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("StatisticsScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from goToStats()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from goToStats()");
			e.printStackTrace();
		}
		*/
	}
	
	public void goToAutoResponses(){
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("AutoResponseSetting.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from goToStats()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from goToStats()");
			e.printStackTrace();
		}
	}
	
	public void goToBindScreen(){
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from goToStats()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from goToStats()");
			e.printStackTrace();
		}
	}
	
	//////Help Menu//////
	
	public void aboutLink(){
		Alert alert = new Alert(AlertType.CONFIRMATION); //Create alert
		alert.setTitle("Confirmation");
		alert.setHeaderText("This will open up the developers offical website in your web browser");
		alert.setContentText("Do you wish to proceed");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){ //If user says ok then the website will open otherwise nothing happens
			String url_open ="https://dragonheart.ninja"; //Set URL to open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening webpage on dev page!");
				ErrorHandling.error(e, "Error opening webpage on dev page!");
				e.printStackTrace();
			}  //opens the URL in the default browser
		} 
	}
	
	public void gitHubLink(){
		Alert alert = new Alert(AlertType.CONFIRMATION); //Create alert
		alert.setTitle("Confirmation");
		alert.setHeaderText("This will open up the GitHub page for Twitch Chat Hot Keys in your web browser");
		alert.setContentText("Do you wish to proceed");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){ //If user says ok then the website will open otherwise nothing happens
			String url_open ="https://github.com/DragonHeart000/TwitchChatHotKeys"; //Set URL to open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening webpage on github page!");
				ErrorHandling.error(e, "Error opening webpage on github page!");
				e.printStackTrace();
			}  //opens the URL in the default browser
		} 
	}
	
	/////////////////////////////////////Setup Data///////////////////////////////////////
	
	public void initialize() { //Initialize method is called the stats fxml file is loaded
		final Long TIMEOFPROGRAMSTART=Main.getProgramStartTime();
		refreshData();
	}
	
	public void refreshData(){
		///////////////////////////////////////Most talkative users data///////////////////////////////////////
		//Data on chatters will be presented in the listview with the fx:id mostTalketiveUsersListView
		
		ArrayList<Chatter> chatters=Main.getChatters(); //Data with all chatters.
		
		 //Use Chatter.getAmountOfMessagesSent() to get how many messages that user has sent.
		//Organize arraylist into a list with most messages sent first.
		
		///////////////////////////////////////Command usage///////////////////////////////////////
		//Very simular to above but with different data.
		ArrayList<Macro> macrosForCommands=Main.getSettings().getMacros(); //Data with all macros
		
		//COMMAND:: macros for this one
		
		/*Macros that are for commands will have the Macro.getCondition() method return a string that starts
		* like this: "COMMAND::" and then is followed with the command that it responds to when said in chat
		* 
		* Example: "COMMAND::!YouTube" will mean that whenever !YouTube is said in chat it will say whatever
		* Macro.getOutPut(); says in chat.
		* 
		* You must check all macros and only use ones that have COMMAND:: at the start of the string returned from
		* Macro.getCondition(); and then sort the similarly to how most talkative users was sorted.
		*/
		
		///////////////////////////////////////Keyboard heatmap///////////////////////////////////////
		//You may need to talk to me again before doing this one.
		
		ArrayList<Macro> macrosForKeyBoard=Main.getSettings().getMacros(); //Data with all macros
		
		//EVERYTHING BUT COMMAND:: macros for this one
		
		/* Use Macro.getUsage() to get how many times that macro was used.
		 * Use Macro.getInput() to get an int that represents what button that macro is tied to.
		 * View this table to see what ints are tied to what keys:
		 * https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
		 * 
		 * Set each button on the keyboard shown in the StatisticsScreen.fxml file that is represented by the given
		 * number from Macro.getInput() to a colour dynamically picked by scaling a colour spectrum to the spectrum
		 * of usage of macros.
		 */
		
		///////////////////////////////////////Chat activity Graph///////////////////////////////////////
		//Graph should show the chat activity over time.
		//Tell me if you need any more data.
		//Here is how to set the data on a JavaFX graph: https://www.youtube.com/watch?v=CMmeFZyWWSI&ab_channel=GenuineCoder
		
		//The var TIMEOFPROGRAMSTART in the initialize method has the time that the program started. You can use that here
		//It is a final since that time should never change.
		//Unix time in mills. Returns the amount of milliseconds since Jan 01 1970 at the start of the program.
		
		//Chatter.getAllMessagesSent() will return a 2D ArrayList with the 0th element being the message and the 1st element being the timestamp
		//Use Long.parseLong(String) to convert the timestamp from a string to a long that you can use.
		
		
		
		
	}
}
