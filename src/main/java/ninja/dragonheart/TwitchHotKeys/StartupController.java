package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ninja.dragonheart.TwitchHotKeys.ErrorHandling;

public class StartupController {
	
	@FXML
	private TextField twitchName;
	@FXML
	private PasswordField oauth;
	@FXML
	private CheckBox saveSettingsCheckBox;
	@FXML
	private Button loadSavedButton;
	
	@FXML
	private AnchorPane setupPane;
	
	/////More Options/////
	@FXML
	public Button moreOptionsButton;
	@FXML
	public TextField channelTextBox;
	@FXML
	public Text channelText;
	@FXML
	public Button channelHelpButton;
	@FXML
	public CheckBox joinMessageToggle;
	@FXML
	public Button previousChannelsButton1;
	@FXML
	public Button previousChannelsButton2;
	@FXML
	public Button previousChannelsButton3;
	@FXML
	public Button previousChannelsButton4;
	@FXML
	public Button previousChannelsButton5;
	
	public UserSettings loadedSettings;
	
	public boolean isMoreOptionsShown;
	
	public ArrayList<String> previousChannels;
	
	/////////////////////////////////////Setup///////////////////////////////////////
	
	public void initialize() {
		channelTextBox.setVisible(false);
		channelText.setVisible(false);
		channelHelpButton.setVisible(false);
		joinMessageToggle.setVisible(false);
		
		previousChannelsButton1.setVisible(false);
		previousChannelsButton2.setVisible(false);
		previousChannelsButton3.setVisible(false);
		previousChannelsButton4.setVisible(false);
		previousChannelsButton5.setVisible(false);
		
		loadSavedButton.setVisible(false); //Make button hidden
		
		if(FileHandleing.exists("C://TwitchChatHotKeys/savedSettings.bin")){ //if there are settings saved
			FadeTransition fadeIn = new FadeTransition(Duration.millis(5000), loadSavedButton); //Start fading button in
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
			loadSavedButton.setVisible(true); //Make button visible
		}
		
		previousChannels=Main.getPreviousChannels();
		
		if(previousChannels != null){
			if(previousChannels.size() >= 1){
				previousChannelsButton1.setText(previousChannels.get(0));
			}
						
			if(previousChannels.size() >= 2){
				previousChannelsButton2.setText(previousChannels.get(1));
			}
						
			if(previousChannels.size() >= 3){
				previousChannelsButton3.setText(previousChannels.get(2));
			}
						
			if(previousChannels.size() >= 4){
				previousChannelsButton4.setText(previousChannels.get(3));
			}
						
			if(previousChannels.size() >= 5){
				previousChannelsButton5.setText(previousChannels.get(4));
			}
		}
	}
	
	/////////////////////////////////////Login///////////////////////////////////////
	
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
			
			System.out.println(channelTextBox.getText().toString());
			
			Main.setDoConnectMessage(!joinMessageToggle.isSelected()); //Saves state of checkbox in static main class
			
			if(channelTextBox.getText().toString().equals("")){
				Main.setChannel("#" + twitchName.getText().toLowerCase());
				MakeBot.makeNewBot(twitchName.getText(), oauth.getText(), "#" + twitchName.getText().toLowerCase());
			} else {
				if (channelTextBox.getText().toString().substring(0,1).equals("#")){ //Check if user already put the #
					String channel=channelTextBox.getText().toString().toLowerCase(); //memoization my man!
					
					if(previousChannels != null && !previousChannels.contains(channel)){ //Only add it if it has not been visited recently
						if(previousChannels.size() >= 5){
							previousChannels.remove(0); //Remove oldest element 
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						} else {
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						}
					}
					
					Main.setChannel(channel);
					MakeBot.makeNewBot(twitchName.getText(), oauth.getText(), channelTextBox.getText().toString().toLowerCase());
				} else {
					String channel="#" + channelTextBox.getText().toString().toLowerCase();
					
					if(previousChannels != null && !previousChannels.contains(channel)){
						if(previousChannels.size() >= 5){ //Save new channel and make sure only 5 are ever stored
							previousChannels.remove(0);
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						} else {
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						}
					}
					
					Main.setChannel(channel);
					MakeBot.makeNewBot(twitchName.getText(), oauth.getText(), "#" + channelTextBox.getText().toString().toLowerCase());
				}
			}
			
			
			
			////////Prepare key Listener////////
			try {
				GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
				System.err.println("There was a problem registering the native hook.");
				System.err.println(ex.getMessage());

				System.exit(1);
			}
			
			// Get the logger for "org.jnativehook" and set the level to warning.
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.WARNING);
			// Don't forget to disable the parent handlers.
			logger.setUseParentHandlers(false);
			
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
			
			Main.setDoConnectMessage(!joinMessageToggle.isSelected()); //Saves state of checkbox in static main class
			
			if(channelTextBox.getText().toString().equals("")){
				
				Main.setChannel("#" + loadedSettings.getUserName().toLowerCase());
				MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth(), "#" + loadedSettings.getUserName().toLowerCase());
			} else {
				if (channelTextBox.getText().toString().substring(0,1).equals("#")){ //Check if user already put the #
					String channel=channelTextBox.getText().toString().toLowerCase();
					
					if(previousChannels != null && !previousChannels.contains(channel)){
						if(previousChannels.size() >= 5){
							previousChannels.remove(0);
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						} else {
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						}
					}
					
					Main.setChannel(channel);
					MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth(), channelTextBox.getText().toString().toLowerCase());
				} else {
					String channel="#" + channelTextBox.getText().toString().toLowerCase();
					
					if(previousChannels != null && !previousChannels.contains(channel)){
						if(previousChannels.size() >= 5){
							previousChannels.remove(0);
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						} else {
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						}
					}
					
					Main.setChannel(channel);
					MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth(), "#" + channelTextBox.getText().toString().toLowerCase());
				}
			}
			
			////////Prepare key Listener////////
			
			try {
				GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
				System.err.println("There was a problem registering the native hook.");
				System.err.println(ex.getMessage());
				
				System.exit(1);
			}
			
			// Get the logger for "org.jnativehook" and set the level to warning.
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.WARNING);
			// Don't forget to disable the parent handlers.
			logger.setUseParentHandlers(false);
						
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
	
	public void changeStyle(){
		try {
			setupPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Styling/styler.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from start()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
			e.printStackTrace();
		}
	}

	/////////////////////////////////////More options///////////////////////////////////////
	
	public void openMoreOptions(){
		//Disable the button for 3 seconds to prevent spam. Must use new thread to not stall the thread that manages UI
		Thread buttonCooldown=new Thread(){
			@Override
			public void run(){
				moreOptionsButton.setDisable(true);
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					ErrorHandling.error(e);
					e.printStackTrace();
				}
				
				moreOptionsButton.setDisable(false);
			}
		};
		
		buttonCooldown.start();
		
		//Set visibility to true because the first time this is ran it is false
		if(!isMoreOptionsShown){
			channelTextBox.setVisible(true);
			channelText.setVisible(true);
			channelHelpButton.setVisible(true);
			joinMessageToggle.setVisible(true);
		}
		
		//Fade in the more options if they are out
		if (!isMoreOptionsShown){
			FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), channelTextBox);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
			
			fadeIn = new FadeTransition(Duration.millis(1000), channelText);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
			
			fadeIn = new FadeTransition(Duration.millis(1000), channelHelpButton);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
			/////
			
			if(previousChannels != null){
				if(previousChannels.size() >= 1){ //This does not work right with a switch
					previousChannelsButton1.setVisible(true);
					fadeIn = new FadeTransition(Duration.millis(1000), previousChannelsButton1);
					fadeIn.setFromValue(0.0);
					fadeIn.setToValue(1.0);
					fadeIn.play();
				}
					
				if(previousChannels.size() >= 2){
					previousChannelsButton2.setVisible(true);
					fadeIn = new FadeTransition(Duration.millis(1000), previousChannelsButton2);
					fadeIn.setFromValue(0.0);
					fadeIn.setToValue(1.0);
					fadeIn.play();
				}
				
				if(previousChannels.size() >= 3){
					previousChannelsButton3.setVisible(true);
					fadeIn = new FadeTransition(Duration.millis(1000), previousChannelsButton3);
					fadeIn.setFromValue(0.0);
					fadeIn.setToValue(1.0);
					fadeIn.play();
				}
					
				if(previousChannels.size() >= 4){
					previousChannelsButton4.setVisible(true);
					fadeIn = new FadeTransition(Duration.millis(1000), previousChannelsButton4);
					fadeIn.setFromValue(0.0);
					fadeIn.setToValue(1.0);
					fadeIn.play();
				}
					
				if(previousChannels.size() >= 5){
					previousChannelsButton5.setVisible(true);
					fadeIn = new FadeTransition(Duration.millis(1000), previousChannelsButton5);
					fadeIn.setFromValue(0.0);
					fadeIn.setToValue(1.0);
					fadeIn.play();
				}
			}
			
			/////
			fadeIn = new FadeTransition(Duration.millis(3000), joinMessageToggle); //Longer time to make it slowly spread out for neat effect
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
			
			isMoreOptionsShown=true; //Variable to keep track of if it was just faded in or out
		} else { //Fade out the more options if they are in
			FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), channelTextBox);
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.play();
			
			fadeOut = new FadeTransition(Duration.millis(2000), channelText);
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.play();
			
			fadeOut = new FadeTransition(Duration.millis(2000), channelHelpButton);
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.play();
			
		/////
			
			if(previousChannels != null){
				if(previousChannels.size() >= 1){
					fadeOut = new FadeTransition(Duration.millis(2000), previousChannelsButton1);
					fadeOut.setFromValue(1.0);
					fadeOut.setToValue(0.0);
					fadeOut.play();
				}
							
				if(previousChannels.size() >= 2){
					fadeOut = new FadeTransition(Duration.millis(2000), previousChannelsButton2);
					fadeOut.setFromValue(1.0);
					fadeOut.setToValue(0.0);
					fadeOut.play();
				}
							
				if(previousChannels.size() >= 3){
					fadeOut = new FadeTransition(Duration.millis(2000), previousChannelsButton3);
					fadeOut.setFromValue(1.0);
					fadeOut.setToValue(0.0);
					fadeOut.play();
				}
							
				if(previousChannels.size() >= 4){
					fadeOut = new FadeTransition(Duration.millis(2000), previousChannelsButton4);
					fadeOut.setFromValue(1.0);
					fadeOut.setToValue(0.0);
					fadeOut.play();
				}
							
				if(previousChannels.size() >= 5){
					fadeOut = new FadeTransition(Duration.millis(2000), previousChannelsButton5);
					fadeOut.setFromValue(1.0);
					fadeOut.setToValue(0.0);
					fadeOut.play();
				}
			}
					
			/////
			
			fadeOut = new FadeTransition(Duration.millis(1000), joinMessageToggle); //Lower time to make it slowly spread out for neat effect
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.play();
			
			isMoreOptionsShown=false;
		}	
		
	}
	
	public void channelHelp(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText("Channel help");
		alert.setContentText("This option allows you to join someone elses channel rather than your own. Enter their twitch.tv username in here and you will connect to theirs instead."
				+ " to connect to your own you may just leave this blank.");
		alert.showAndWait();
	}
	
	public void setToPreviousChannel(ActionEvent event){
		switch (event.getSource().toString().substring(32, 33)){
			case "1" :
				channelTextBox.setText(previousChannelsButton1.getText());
				break;
				
			case "2" :
				channelTextBox.setText(previousChannelsButton2.getText());
				break;
				
			case "3" :
				channelTextBox.setText(previousChannelsButton3.getText());
				break;
				
			case "4" :
				channelTextBox.setText(previousChannelsButton4.getText());
				break;
				
			case "5" :
				channelTextBox.setText(previousChannelsButton5.getText());
				break;
		}
		System.out.println(event.getSource().toString().substring(32, 33));
	}
	
}
