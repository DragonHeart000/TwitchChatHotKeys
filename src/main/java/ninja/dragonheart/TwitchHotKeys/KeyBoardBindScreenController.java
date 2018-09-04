//////////////////////////////////////////////////////////////////////////////////////////////
/*
*  This class is not even close to working. Most of the code is pulled straight from
*  the BindScreenController.java file with only a few edits because for the most part
*  a large portion of the methods will be similar with only slight tweaks and a few
*  new added features.
*  
*  In it's current state all that has been done to this file is copying over the code
*  and a few parts I know I won't need have been deleted.
*  
*  This class will be used for the KeyBoardScreen page. The page is supposed to propose
*  a possible change to how binds will be set and allow users to see what keys on their
*  keyboard have already been bound. I will be finishing up this class soon. If you
*  were planning on submitting something to the git repo that had to do with this file
*  do note that this code here is not even close to it's finished state.
*
*/
/////////////////////////////////////////////////////////////////////////////////////////////





package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

	public ArrayList<String> previousChannels = Main.getPreviousChannels();
	
	private int lastKeyPressed;

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

	///////////////////////////////////// Menu
	///////////////////////////////////// Bar///////////////////////////////////////

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
				if (newChannel.getText().toString().substring(0, 1).equals("#")) { // Check
																					// if
																					// user
																					// already
																					// put
																					// the
																					// #
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

	////// Help Menu//////

	public void aboutLink() {
		Alert alert = new Alert(AlertType.CONFIRMATION); // Create alert
		alert.setTitle("Confirmation");
		alert.setHeaderText("This will open up the developers offical website in your web browser");
		alert.setContentText("Do you wish to proceed");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) { // If user says ok then the website
												// will open otherwise nothing
												// happens
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
		if (result.get() == ButtonType.OK) { // If user says ok then the website
												// will open otherwise nothing
												// happens
			String url_open = "https://github.com/DragonHeart000/TwitchChatHotKeys"; // Set
																						// URL
																						// to
																						// open
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e) {
				System.out.println("Error opening webpage on github page!");
				ErrorHandling.error(e, "Error opening webpage on github page!");
				e.printStackTrace();
			} // opens the URL in the default browser
		}
	}

	///////////////////////////////////// Bind
	///////////////////////////////////// commands///////////////////////////////////////

	////// Make Bind//////

	public void makeNewBind() {
		if (!newBindInPut.equals("...")) {
			newBindInPut.setText("..."); // This does not change, it is supposed
											// to change to prompt the user to
											// hit a key
			Thread bindListener = new Thread() {
				@Override
				public void run() {
					lastKeyPressed = KeyListener.getNextKey().getRawCode();
					newBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
					Thread.currentThread().interrupt();
					return;
				}
			};
			bindListener.start();
		}

	}

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
			newBindOutPut.setText("");
		}
	}

	////// Check Bind//////

	public void checkNewBind() {
		if (!checkBindInPut.getText().equals("...")) {
			checkBindOutPut.setText("");
			checkBindInPut.setText("...");
			Thread bindListener = new Thread() {
				@Override
				public void run() {
					lastKeyPressed = KeyListener.getNextKey().getRawCode();
					if (Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")) {
						checkBindOutPut.setText("THAT KEY IS NOT BOUND");
						checkBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
						/*
						 * Alert alert = new Alert(AlertType.INFORMATION);
						 * alert.setTitle("Not Bound");
						 * alert.setHeaderText(null);
						 * alert.setContentText("That key is not bound");
						 * alert.showAndWait();
						 */
					} else {
						checkBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
						checkBindOutPut.setText(Main.checkMacro(lastKeyPressed));
					}
					Thread.currentThread().interrupt();
					return;
				}
			};
			bindListener.start();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Too many requests");
			alert.setHeaderText(null);
			alert.setContentText("Please hit a key before attempting to check another.");
			alert.showAndWait();
		}
	}

	////// Delete Bind//////

	public void deleteNewBind() {
		if (!deleteBindInPut.getText().equals("...")) {
			deleteBindInPut.setText("...");
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Too many requests");
			alert.setHeaderText(null);
			alert.setContentText("Please hit a key before attempting to delete another.");
			alert.showAndWait();
		}

	}

	public void deleteBind() {
		if (!deleteBindInPut.getText().equals("...")) {
			Main.delMacro(lastKeyPressed);
			deleteBindInPut.setText("BLANK");
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
		if (result.get() == ButtonType.OK) { // If user says ok then the website
												// will open otherwise nothing
												// happens
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
}
