package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class AutoResponseSettingController {
	
	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private ListView<?> autoResponseInputListView;
	@FXML
	private ListView<?> autoResponseOutputListView;
	
	@FXML
	private TextField newAutoResponseInPut;
	@FXML
	private TextField newAutoResponseOutPut;
	@FXML
	private TextField deleteAutoResponseInPut;
	
	
	public void initialize() {
		
	}
	
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
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("StatisticsScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from goToStats()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from goToStats()");
			e.printStackTrace();
		}
	}
	
	public void goToAutoResponses(){
		//You're there already
		/*
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("AutoResponseSetting.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from goToStats()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from goToStats()");
			e.printStackTrace();
		}
		*/
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
	
	/////////////////////////////////////Auto Response Handling///////////////////////////////////////
	
	public void makeAutoResponse(){
		if (!newAutoResponseOutPut.getText().equals("") || !newAutoResponseInPut.getText().equals("")){
			Macro temp=new Macro(9999, newAutoResponseOutPut.getText(), "COMMAND::" + newAutoResponseInPut.getText());
			Main.addSettingsMacro(temp);
			
			newAutoResponseOutPut.setText("");
			newAutoResponseInPut.setText("");
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Missing Parameter");
			alert.setHeaderText(null);
			alert.setContentText("Please make sure you have entered something for both the input and output.");
			alert.showAndWait();
		}
		
		
		/*
		 * autoResponseInputListView
		 * autoResponseOutputListView
		 */
	}
	
	public void deleteAutoResponse(){
		
	}
}
