package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class KeyBoardBindScreenController {
	
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
	private TextField newChannel;

	@FXML
	private AnchorPane mainPane;
	
	//keyboard buttons
	@FXML
	private Button KEY112;
	@FXML
	private Button KEY113;
	@FXML
	private Button KEY114;
	@FXML
	private Button KEY115;
	@FXML
	private Button KEY116;
	@FXML
	private Button KEY117;
	@FXML
	private Button KEY118;
	@FXML
	private Button KEY119;
	@FXML
	private Button KEY120;
	@FXML
	private Button KEY121;
	@FXML
	private Button KEY122;
	@FXML
	private Button KEY123;
	@FXML
	private Button KEY27;
	@FXML
	private Button KEY192;
	@FXML
	private Button KEY49;
	@FXML
	private Button KEY50;
	@FXML
	private Button KEY51;
	@FXML
	private Button KEY52;
	@FXML
	private Button KEY53;
	@FXML
	private Button KEY54;
	@FXML
	private Button KEY55;
	@FXML
	private Button KEY56;
	@FXML
	private Button KEY57;
	@FXML
	private Button KEY48;
	@FXML
	private Button KEY189;
	@FXML
	private Button KEY187;
	@FXML
	private Button KEY8;
	
	
	
	@FXML
	private Button KEY9;
	@FXML
	private Button KEY81;
	@FXML
	private Button KEY87;
	@FXML
	private Button KEY69;
	@FXML
	private Button KEY82;
	@FXML
	private Button KEY84;
	@FXML
	private Button KEY89;
	@FXML
	private Button KEY85;
	@FXML
	private Button KEY73;
	@FXML
	private Button KEY79;
	@FXML
	private Button KEY80;
	@FXML
	private Button KEY219;
	@FXML
	private Button KEY221;
	@FXML
	private Button KEY220;
	@FXML
	private Button KEY20;
	@FXML
	private Button KEY65;
	@FXML
	private Button KEY83;
	@FXML
	private Button KEY68;
	@FXML
	private Button KEY70;
	@FXML
	private Button KEY71;
	@FXML
	private Button KEY72;
	@FXML
	private Button KEY74;
	@FXML
	private Button KEY75;
	@FXML
	private Button KEY76;
	@FXML
	private Button KEY186;
	@FXML
	private Button KEY222;
	@FXML
	private Button KEY13;
	@FXML
	private Button KEY16;
	@FXML
	private Button KEY90;
	@FXML
	private Button KEY88;
	@FXML
	private Button KEY67;
	@FXML
	private Button KEY86;
	@FXML
	private Button KEY66;
	@FXML
	private Button KEY78;
	@FXML
	private Button KEY77;
	@FXML
	private Button KEY188;
	@FXML
	private Button KEY190;
	@FXML
	private Button KEY191;
	@FXML
	private Button KEY17;
	@FXML
	private Button KEY91;
	@FXML
	private Button KEY18;
	@FXML
	private Button KEY32;
	@FXML
	private Button KEY92;
	@FXML
	private Button KEY93;
	
	@FXML
	private Button KEY44;
	@FXML
	private Button KEY145;
	@FXML
	private Button KEY19;
	@FXML
	private Button KEY45;
	@FXML
	private Button KEY36;
	@FXML
	private Button KEY33;
	@FXML
	private Button KEY46;
	@FXML
	private Button KEY35;
	@FXML
	private Button KEY34;
	
	@FXML
	private Button KEY38;
	@FXML
	private Button KEY40;
	@FXML
	private Button KEY37;
	@FXML
	private Button KEY39;
	
	@FXML
	private Button KEY144;
	@FXML
	private Button KEY111;
	@FXML
	private Button KEY106;
	@FXML
	private Button KEY109;
	@FXML
	private Button KEY103;
	@FXML
	private Button KEY104;
	@FXML
	private Button KEY105;
	@FXML
	private Button KEY107;
	@FXML
	private Button KEY100;
	@FXML
	private Button KEY101;
	@FXML
	private Button KEY102;
	@FXML
	private Button KEY97;
	@FXML
	private Button KEY98;
	@FXML
	private Button KEY99;
	@FXML
	private Button KEY96;
	@FXML
	private Button KEY110;

	
	//Vars

	public ArrayList<String> previousChannels = Main.getPreviousChannels();
	
	private int lastKeyPressed;
	
	private UserSettings loadedSettings;
	
	
	 public void initialize() {
		 checkBindOutPut.setDisable(true);
		 
		 newBindInPut.setText("BLANK");
		 checkBindInPut.setText("BLANK");
		 deleteBindInPut.setText("BLANK");
		 
		 loadedSettings=Main.getSettings();
		 
		 if (loadedSettings.getMacros() != null){
		    for (Macro temp : loadedSettings.getMacros()){
		    	changeKeyColour(Integer.toString(temp.getInput()), true);
		    }
	    }
	 }
	 
	 public void changeKeyColour(String key, boolean add){
		 for (Node node : mainPane.getChildren()){
 			try {
		    	if (node.getId().equals("KEY" + key)){
		    		if(add){
		    			node.getStyleClass().clear();
		    			node.getStyleClass().add("button");
		    			node.getStyleClass().add("keyBound");
		    		} else {
		    			node.getStyleClass().clear();
		    			node.getStyleClass().add("button");
		    			node.getStyleClass().add("keyNotBound");
		    		}
		    	}
 			} catch (NullPointerException e){
 				//Some of the children do not have an fx:id so it will throw a null pointer and crash unless you handle it
 			}
	    }
	 }
	

	/*
	 * For the sake of keeping things organized I created another controller for
	 * each page the issue with this is the menu bar is almost 100% the same
	 * throughout (other than loadSaved change to loadSavedAfterStart since the
	 * program will need to stop the running bot before starting a new one).
	 * Because of this I copy pasted the code over to here. It should be
	 * possible to have the menu on it's own controller so this does not need to
	 * be in every controller but that is not top priority. But I will add it to
	 * the todos. TODO make a controller for the menu bar separate to clean up
	 * code.
	 */

	/////////////////////////////////////Menu Bar///////////////////////////////////////

	////// File Menu//////

	public void newFromMenu() {
		// End current instances of bot and key listener
		try {
			System.out.println("Calling thread kill");
			if (MakeBot.checkThread()) {
				MakeBot.killThread();
				KeyListener.endKeyListener();
			}
		} catch (Exception e) {
			ErrorHandling.error(e, "There could be an error as the program did not close properly!");
			System.out.println("There could be an error as the program did not close properly!");
		}
		System.out.println("Switching scenes");
		// Switch scene
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("LoginScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from start()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
			e.printStackTrace();
		}

	}

	public void loadSavedAfterStart() {
		// End current instances of bot and key listener
		try {
			System.out.println("Calling thread kill");
			if (MakeBot.checkThread()) {
				MakeBot.killThread();
			}
		} catch (Exception e) {
			ErrorHandling.error(e, "There could be an error as the program did not close properly!");
			System.out.println("There could be an error as the program did not close properly!");
		}

		/////

		if (FileHandleing.exists("C://TwitchChatHotKeys/savedSettings.bin")) {
			UserSettings loadedSettings = FileHandleing.readInUserSettings("C://TwitchChatHotKeys/savedSettings.bin");
			Main.setSettings(loadedSettings);
			Main.setSaveSettings(true);

			if (newChannel.getText().toString().equals("")) {

				Main.setChannel("#" + loadedSettings.getUserName().toLowerCase());
				MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth(),
						"#" + loadedSettings.getUserName().toLowerCase());
			} else {
				if (newChannel.getText().toString().substring(0, 1).equals("#")) { // Check if user already put the #
					String channel = newChannel.getText().toString().toLowerCase();

					if (previousChannels != null && !previousChannels.contains(channel)) {
						if (previousChannels.size() >= 5) {
							previousChannels.remove(0);
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						} else {
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						}
					}

					Main.setChannel(channel);
					MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth(),
							newChannel.getText().toString().toLowerCase());
				} else {
					String channel = "#" + newChannel.getText().toString().toLowerCase();

					if (previousChannels != null && !previousChannels.contains(channel)) {
						if (previousChannels.size() >= 5) {
							previousChannels.remove(0);
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						} else {
							previousChannels.add(channel);
							Main.setPreviousChannels(previousChannels);
						}
					}

					Main.setChannel(channel);
					MakeBot.makeNewBot(loadedSettings.getUserName(), loadedSettings.getOauth(),
							"#" + newChannel.getText().toString().toLowerCase());
				}
			}
		}
	}

	public void quit() {
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

	////// Help Menu//////

	public void aboutLink() {
		Alert alert = new Alert(AlertType.CONFIRMATION); // Create alert
		alert.setTitle("Confirmation");
		alert.setHeaderText("This will open up the developers offical website in your web browser");
		alert.setContentText("Do you wish to proceed");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) { // If user says ok then the website will open otherwise nothing happens
			String url_open = "https://dragonheart.ninja"; // Set URL to open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening webpage on dev page!");
				ErrorHandling.error(e, "Error opening webpage on dev page!");
				e.printStackTrace();
			} // opens the URL in the default browser
		}
	}

	public void gitHubLink() {
		Alert alert = new Alert(AlertType.CONFIRMATION); // Create alert
		alert.setTitle("Confirmation");
		alert.setHeaderText("This will open up the GitHub page for Twitch Chat Hot Keys in your web browser");
		alert.setContentText("Do you wish to proceed");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) { // If user says ok then the website will open otherwise nothing happens
			String url_open = "https://github.com/DragonHeart000/TwitchChatHotKeys"; // Set URL to open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening webpage on github page!");
				ErrorHandling.error(e, "Error opening webpage on github page!");
				e.printStackTrace();
			} // opens the URL in the default browser
		}
	}

	/////////////////////////////////////Bind commands///////////////////////////////////////
	
	/////////Setting key//////////
	
	public void setKey(Event e){
		/*Some keys show up multiple times on a keyboard but have the same ID
		 * you are only allowed to have one object per fx:id in an FXML file.
		 * To get around this for the duplicate ones I have added an $ on the end.
		 * Then the following code will remove the $ if it is there to effectively
		 * make them the same.
		 */
		String temp=e.getSource().toString().substring(13, 
				e.getSource().toString().indexOf(","));
		if(!temp.contains("$")){
			lastKeyPressed=Integer.parseInt(e.getSource().toString().substring(13, 
					e.getSource().toString().indexOf(",")));
		} else {
			lastKeyPressed=Integer.parseInt(e.getSource().toString().substring(13, 
					e.getSource().toString().indexOf("$")));
		}
		
		newBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
		checkBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
		deleteBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
		
		//For check bind
		checkBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
		if (Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")) {
			checkBindOutPut.setText("THAT KEY IS NOT BOUND");
		} else {
			checkBindOutPut.setText(Main.checkMacro(lastKeyPressed));
		}
	}

	public void setKeyViaKeyBoard(){
		if (!newBindInPut.equals("...")) {
			newBindInPut.setText("...");
			checkBindInPut.setText("...");
			deleteBindInPut.setText("...");
			Thread bindListener = new Thread() {
				@Override
				public void run() {
					//Set new key
					lastKeyPressed = KeyListener.getNextKey().getRawCode();
				
					//For new bind
					newBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
				
					//For check bind
					checkBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
					if (Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")) {
						checkBindOutPut.setText("THAT KEY IS NOT BOUND");
					} else {
						checkBindOutPut.setText(Main.checkMacro(lastKeyPressed));
					}
				
					//For delete bind
					deleteBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
				
					//For ending thread
					Thread.currentThread().interrupt();
					return;
				}
			};
			bindListener.start();
		}
	}
	
	/////////Bind functions/////////

	public void saveNewBind() {
		if (newBindInPut.getText().equals("...")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind input feild is blank");
			alert.setHeaderText(null);
			alert.setContentText(
					"Please click the \"Click to set bind\" button and then a key on your keyboard to set this.");
			alert.showAndWait();
		} else if (!Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("That key is already bound");
			alert.setHeaderText(null);
			alert.setContentText("If you would like to see what it is bound to use the check macro feature.");
			alert.showAndWait();
		} else if (newBindInPut.getText().equals("BLANK")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind input feild is blank");
			alert.setHeaderText(null);
			alert.setContentText(
					"Please click the \"Click to set bind\" button and then a key on your keyboard to set this.");
			alert.showAndWait();
		} else if (newBindOutPut.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind output feild is blank");
			alert.setHeaderText(null);
			alert.setContentText("Please type something in the output box.");
			alert.showAndWait();
		} else {
			Main.addSettingsMacro(new Macro(lastKeyPressed, newBindOutPut.getText(), null));
			newBindInPut.setText("BLANK");
			checkBindInPut.setText("BLANK");
			deleteBindInPut.setText("BLANK");
			newBindOutPut.setText("");
			changeKeyColour(Integer.toString(lastKeyPressed), true);
		}
	}

	////// Delete Bind//////

	public void deleteBind() {
		if (!deleteBindInPut.getText().equals("...")) {
			Main.delMacro(lastKeyPressed);
			newBindInPut.setText("BLANK");
			checkBindInPut.setText("BLANK");
			deleteBindInPut.setText("BLANK");
			changeKeyColour(Integer.toString(lastKeyPressed), false);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Too early of a request");
			alert.setHeaderText(null);
			alert.setContentText("Please hit a key before attempting to delete it.");
			alert.showAndWait();
		}
	}

	////// Bind help//////

	public void bindHelp() {
		Alert alert = new Alert(AlertType.CONFIRMATION); // Create alert
		alert.setTitle("Help");
		alert.setHeaderText("For help you can visit the GitHub wiki page.");
		alert.setContentText("Would you like to open it now?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) { // If user says ok then the website will open otherwise nothing happens
			String url_open = "https://github.com/DragonHeart000/TwitchChatHotKeys/wiki"; // Set URL to open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening webpage on dev page!");
				ErrorHandling.error(e, "Error opening webpage on dev page!");
				e.printStackTrace();
			} // opens the URL in the default browser
		}
	}

	/////////////////////////////////////Channel Switching///////////////////////////////////////

	public void joinNewChannel() {
		if (newChannel.getText().toString().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Channel to join is blank");
			alert.setContentText("Please enter a channel name to join in the box.");
			alert.showAndWait();
		} else if (newChannel.getText().toString().substring(0, 1).equals("#")) { // Channels must have a # before them and be lower case
			String channel = newChannel.getText().toString().toLowerCase();

			MakeBot.joinNewChannel(channel);

			if (previousChannels != null && !previousChannels.contains(channel)) {
				if (previousChannels.size() >= 5) {
					previousChannels.remove(0);
					previousChannels.add(channel);
					Main.setPreviousChannels(previousChannels);
				} else {
					previousChannels.add(channel);
					Main.setPreviousChannels(previousChannels);
				}
			}

			newChannel.setText(""); // Set text back to blank to show user it worked
		} else {
			String channel = "#" + newChannel.getText().toString().toLowerCase();

			MakeBot.joinNewChannel(channel);

			if (previousChannels != null && !previousChannels.contains(channel)) {
				if (previousChannels.size() >= 5) {
					previousChannels.remove(0);
					previousChannels.add(channel);
					Main.setPreviousChannels(previousChannels);
				} else {
					previousChannels.add(channel);
					Main.setPreviousChannels(previousChannels);
				}
			}

			newChannel.setText("");
		}

	}
	
	/////////////////////////////////////MISC/////////////////////////////////////
	
	public void swapView(){
		try {
			mainPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml")));
		} catch (IOException e) {
			System.out.println("ERROR: IOException when loading AnchorPane from start()");
			ErrorHandling.error(e, "ERROR: IOException when loading AnchorPane from start()");
			e.printStackTrace();
		}
	}
	
}
