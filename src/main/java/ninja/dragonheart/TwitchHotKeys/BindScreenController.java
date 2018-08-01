package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public class BindScreenController {
	
	//fxml vars
	@FXML
	private Text newBindInPut;
	@FXML
	private TextField newBindOutPut;
	
	@FXML
	private Text checkBindInPut;
	@FXML
	private TextField checkBindOutPut;
	
	@FXML
	private Text deleteBindInPut;
	@FXML
	private TextField deleteBindOutPut;
	
	private int lastKeyPressed;
	
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
		
	}
	
	public void loadSavedAfterStart(){
		//TODO end all current running tasks
		//loadSaved();
	}
	
	public void quit(){
		Platform.exit();
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
	
	/////////////////////////////////////Bind commands///////////////////////////////////////
	
	public void makeNewBind(){
		newBindInPut.setText("..."); //This does not change, it is supposed to change to prompt the user to hit a key
		lastKeyPressed=KeyListener.getNextKey().getRawCode();
		newBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
	}
	
	public void saveNewBind(){
		if (!Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("That key is already bound");
			alert.setHeaderText(null);
			alert.setContentText("If you would like to see what it is bound to use the check macro feature.");
			alert.showAndWait();
		} else if (newBindInPut.equals("BLANK")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind input feild is blank");
			alert.setHeaderText(null);
			alert.setContentText("Please click the \"Click to set bind\" button and then a key on your keyboard to set this.");
			alert.showAndWait();
		} else if (newBindOutPut.equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind output feild is blank");
			alert.setHeaderText(null);
			alert.setContentText("Please type something in the output box.");
			alert.showAndWait();
		} else {
			Main.addSettingsMacro(new Macro(lastKeyPressed, newBindOutPut.getText(), null));
			newBindInPut.setText("BLANK");
			newBindOutPut.setText("");
		}
	}
	
	public void checkNewBind(){
		checkBindOutPut.setText("");
		lastKeyPressed=KeyListener.getNextKey().getRawCode();
		if (Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Not Bound");
			alert.setHeaderText(null);
			alert.setContentText("That key is not bound");
			alert.showAndWait();
		} else {
			checkBindOutPut.setText(Main.checkMacro(lastKeyPressed));
		}
	}
	
	public void deleteNewBind(){
		deleteBindOutPut.setText("");
		lastKeyPressed=KeyListener.getNextKey().getRawCode();
		deleteBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
		deleteBindOutPut.setText(Main.checkMacro(lastKeyPressed));
	}
	
	public void deleteBind(){
		Main.delMacro(lastKeyPressed);
		deleteBindInPut.setText("BLANK");
		deleteBindOutPut.setText("");
	}
	
	public void bindHelp(){
		//TODO
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("This is an Alpha, don't expect every button to work");
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	

}
