package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.Optional;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ninja.dragonheart.TwitchHotKeys.ErrorHandling;
import ninja.dragonheart.TwitchHotKeys.FileHandleing;
import ninja.dragonheart.TwitchHotKeys.KeyListener;
import ninja.dragonheart.TwitchHotKeys.Main;
import ninja.dragonheart.TwitchHotKeys.MakeBot;
import ninja.dragonheart.TwitchHotKeys.UserSettings;

public class StyleController {
	
	//fxml vars
	@FXML
	private Text currentSkinText;
	@FXML
	private ComboBox<?> pickSkinDropDown;
	@FXML
	private TextField customFromFileBox;
	
	@FXML
	private AnchorPane stylerPane;
	
	public UserSettings loadedSettings;
	
	
	public void initialize(){ //Initialize method is called on load so this is used to make sure the right skin is displayed as whats loaded.
		String loadedSkin=FileHandleing.readInString("C://TwitchChatHotKeys/styles/set style.bin");
		currentSkinText.setText(loadedSkin.substring(loadedSkin.lastIndexOf("/")+1, loadedSkin.lastIndexOf("."))); //Should get just name of .css file
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
		try {
			stylerPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("LoginScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from start()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
			e.printStackTrace();
		}
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
				stylerPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml")));
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
	
	/////////////////////////////////////Buttons///////////////////////////////////////
	
	public void previewCSS(){
		stylerPane.getScene().getStylesheets().clear(); //Clear css because for some reason if you don't the css gets all scuffed
		stylerPane.getScene().getStylesheets().add(getClass().getResource("Styling/PreMades/" + pickSkinDropDown.getValue() + ".css").toExternalForm());
		System.out.println("Styling/PreMades/" + pickSkinDropDown.getValue() + ".css");
	}
	
	public void setNewDefaultSkin(){
		//Write out file path of wanted style to the "set style.bin" file.
		FileHandleing.writeOutString("Styling/PreMades/" + pickSkinDropDown.getValue() + ".css", "C://TwitchChatHotKeys/styles/set style.bin");
		//Have to add Styling here because it will only be loaded from outside this package.
		
		//Change current skin text to the new skin
		currentSkinText.setText(pickSkinDropDown.getValue().toString());
		
		//Tell user it has worked
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Saved!");
		alert.setHeaderText(null);
		alert.setContentText("Your prefference has been saved!");
		alert.showAndWait();
	}
	
	public void loadFromFile(){
		if (customFromFileBox.getText().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No file to load");
			alert.setHeaderText(null);
			alert.setContentText("Please enter the file path of your .css file you wish to load into the text feild");
			alert.showAndWait();
		} else {
			try {
				//Set style to file listed
				stylerPane.getScene().getStylesheets().add(getClass().getResource(customFromFileBox.getText()).toExternalForm());
				
				//Write out file path of wanted style to the "set style.bin" file.
				FileHandleing.writeOutString(customFromFileBox.getText(), "C://TwitchChatHotKeys/styles/set style.bin");
				
				//Change current skin text to the new skin
				currentSkinText.setText(customFromFileBox.toString().substring(customFromFileBox.toString().lastIndexOf("/") + 1
						, customFromFileBox.toString().lastIndexOf(".")));
				
				//Tell user it has worked
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Saved!");
				alert.setHeaderText(null);
				alert.setContentText("Your prefference has been saved!");
				alert.showAndWait();
			} catch (Exception e){
				ErrorHandling.error(e, "Error loading requested .css file");
			}
		}
	}
	
	public void goToCreateNew(){
		//TODO go to fxml scene where you can pick colours of each part and an image
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Not supported yet");
		alert.setHeaderText(null);
		alert.setContentText("The creration of custom skins is planned to make it easy for anyone to do. As of right now this is not implimented"
				+ "but you can still look at the source code and use the defualt skins as an example and create your own from them. Skins are made"
				+ "in CSS.");
		alert.showAndWait();
	}
	
	//try this out later: Application.STYLESHEET_MODENA
	
}
