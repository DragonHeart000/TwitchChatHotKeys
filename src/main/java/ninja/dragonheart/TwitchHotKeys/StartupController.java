package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;
import ninja.dragonheart.TwitchHotKeys.ErrorHandling;

public class StartupController {
	
	@FXML
	private TextField twitchName;
	@FXML
	private PasswordField oauth;
	@FXML
	private CheckBox saveSettingsCheckBox;
	@FXML
	private AnchorPane setupPane;
	
	public UserSettings loadedSettings;
	
	public void start(){
		if (twitchName.getText().equals("") || oauth.getText().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Missing info!");
			alert.setHeaderText(null);
			alert.setContentText("You seem to have left a required feild blank, please be sure to input this info then"
					+ " try again!");
			alert.showAndWait();
		} else {
			
			////////Start saving settings////////
			//Make sure oauth is done right and if not fix it
			String userOauth=oauth.getText();
			if (userOauth.length()>6 && !userOauth.substring(0,6).toLowerCase().equals("oauth:")){ //Make sure the user added the oauth: part
				userOauth="oauth:"+userOauth;
			}
			
			loadedSettings=new UserSettings(twitchName.getText(), userOauth, new ArrayList<Macro>()); //Creates the user
			if (saveSettingsCheckBox.isSelected()){
				Main.setSaveSettings(true);
				FileHandleing.writeOutUserSettings(loadedSettings, "C://TwitchChatHotKeys/SavedSettings.bin"); //Save the settings if check box is ticked
			} else {
				ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
				ButtonType noSaveButton = new ButtonType("Continue Without Saveing", ButtonBar.ButtonData.CANCEL_CLOSE);
				Alert alert = new Alert(AlertType.CONFIRMATION, "", saveButton, noSaveButton); //Create alert
				alert.setTitle("Alert");
				alert.setHeaderText("Not saveing settings will not save any hot keys you setup for use next time you start the program.");
				alert.setContentText("Would you like to save settings?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){ //If user says ok then the website will open otherwise nothing happens
					Main.setSaveSettings(true);
					FileHandleing.writeOutUserSettings(loadedSettings, "C://TwitchChatHotKeys/SavedSettings.bin"); //Save the settings if they said yes
				} 
			}
			Main.setSettings(loadedSettings);
			MakeBot.makeNewBot(twitchName.getText(), oauth.getText());
			
			////////Prepare key Listener////////
			try {
				GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
				System.err.println("There was a problem registering the native hook.");
				System.err.println(ex.getMessage());

				System.exit(1);
			}
			GlobalScreen.addNativeKeyListener(new KeyListener());
			
			/////////////Swap scene/////////////
			try {
				setupPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml")));
			} catch (IOException e) {
				System.out.println("ERROR: IOException when loading AnchorPane from start()");
				ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
				e.printStackTrace();
			}
		}
	}
	
	public void oauthHelp(){
		Alert alert = new Alert(AlertType.CONFIRMATION); //Create alert
		alert.setTitle("What is oauth?");
		alert.setHeaderText("OAuth (Open Authorization) is an authorization protocol that allows third party applications"
				+ " access to user data without giving out the users password.");
		alert.setContentText("Would you like to open the webpage that will allow you to get your oauth?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){ //If user says ok then the website will open otherwise nothing happens
			String url_open ="https://twitchapps.com/tmi/"; //Set URL to open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening oauth webpage page!");
				ErrorHandling.error(e, "Error opening oauth webpage page!");
				e.printStackTrace();
			}  //opens the URL in the default browser
		}
	}
	
	/////////////////////////////////////Menu Bar///////////////////////////////////////
	
	//////File Menu//////
	
	public void newFromMenu(){
		
	}
	
	public void loadSaved(){
		if (FileHandleing.exists("C://TwitchChatHotKeys/savedSettings.bin")){
			loadedSettings=FileHandleing.readInUserSettings("C://TwitchChatHotKeys/savedSettings.bin");
			Main.setSettings(loadedSettings);
			Main.setSaveSettings(true);
			MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth());
			
			////////Prepare key Listener////////
			try {
				GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
				System.err.println("There was a problem registering the native hook.");
				System.err.println(ex.getMessage());
				
				System.exit(1);
			}
			GlobalScreen.addNativeKeyListener(new KeyListener());
			
			/////////////Swap scene/////////////
			try {
				setupPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml")));
			} catch (IOException e) {
				System.out.println("ERROR: IOException when loading AnchorPane from start()");
				ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No saved info!");
			alert.setHeaderText(null);
			alert.setContentText("Twitch Chat Hot Keys was unable to locate the saved user settings. Please make sure the settings are located in"
					+ " C://TwitchChatHotKeys/ and nammed savedSettings.bin");
			alert.showAndWait();
		}
	}
	
	public void loadSavedAfterStart(){
		//TODO end all current running tasks
		loadSaved();
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
	
}
