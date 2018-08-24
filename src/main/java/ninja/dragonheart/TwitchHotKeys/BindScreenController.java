package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class BindScreenController implements Initializable{
	
	//fxml vars
	@FXML
	private Text newBindInPut;
	@FXML
	private TextField newBindOutPut;
	@FXML
	private ComboBox<?> newBindIOutPutnserter;
	
	@FXML
	private Text checkBindInPut;
	@FXML
	private TextField checkBindOutPut;
	
	@FXML
	private Text deleteBindInPut;
	@FXML
	private TextField deleteBindOutPut;
	
	@FXML
	private TextField newChannel;
	
	@FXML
	private AnchorPane mainPane;
	
	private int lastKeyPressed;
	
	@Override // and this
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
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
	
	//////Make Bind//////
	
	public void makeNewBind(){
		if (!newBindInPut.equals("...")){
			newBindInPut.setText("..."); //This does not change, it is supposed to change to prompt the user to hit a key
			Thread bindListener=new Thread(){
				@Override
				public void run(){
					lastKeyPressed=KeyListener.getNextKey().getRawCode();
					newBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
					Thread.currentThread().interrupt();
					return;
				}
			};
			bindListener.start();
		}
		
	}
	
	public void saveNewBind(){
		if (newBindInPut.getText().equals("...")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind input feild is blank");
			alert.setHeaderText(null);
			alert.setContentText("Please click the \"Click to set bind\" button and then a key on your keyboard to set this.");
			alert.showAndWait();
		} else if (!Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("That key is already bound");
			alert.setHeaderText(null);
			alert.setContentText("If you would like to see what it is bound to use the check macro feature.");
			alert.showAndWait();
		} else if (newBindInPut.getText().equals("BLANK")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keybind input feild is blank");
			alert.setHeaderText(null);
			alert.setContentText("Please click the \"Click to set bind\" button and then a key on your keyboard to set this.");
			alert.showAndWait();
		} else if (newBindOutPut.getText().equals("")){
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
	
	public void newBindOutPutInsert(){
		switch (newBindIOutPutnserter.getValue().toString()){
			case "Last User" : 
				newBindOutPut.setText(newBindOutPut.getText() + "$$USER "); //Adds the user var to the output box
				newBindOutPut.requestFocus(); //Brings focus back to the output box
				newBindOutPut.end(); //Puts cursor at end of the text
				break; //you better know what this is
			case "Last Message" : 
				newBindOutPut.setText(newBindOutPut.getText() + "$$USERMESSAGE ");
				newBindOutPut.requestFocus();
				newBindOutPut.end();
				break;
			case "Time" : 
				newBindOutPut.setText(newBindOutPut.getText() + "$$TIME ");
				newBindOutPut.requestFocus();
				newBindOutPut.end();
				break;
			case "Date" : 
				newBindOutPut.setText(newBindOutPut.getText() + "$$DATE ");
				newBindOutPut.requestFocus();
				newBindOutPut.end();
				break;
			default:
				//This should never happen but if it does we got a long night of debugging ahead...
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("This should never be shown, if it comes up please submit a bug report to the github page.");
				alert.showAndWait();
		}	
	}
	
	//////Check Bind//////
	
	public void checkNewBind(){
		if (!checkBindInPut.getText().equals("...")){
		checkBindOutPut.setText("");
		checkBindInPut.setText("...");
		Thread bindListener=new Thread(){
			@Override
			public void run(){
				lastKeyPressed=KeyListener.getNextKey().getRawCode();
				 	if (Main.checkMacro(lastKeyPressed).equals("NO OUTPUT")){
				 		checkBindOutPut.setText("THAT KEY IS NOT BOUND");
				 		checkBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
				 	/*	
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Not Bound");
					alert.setHeaderText(null);
					alert.setContentText("That key is not bound");
					alert.showAndWait();
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
	
	//////Delete Bind//////
	
	public void deleteNewBind(){
		if (!deleteBindInPut.getText().equals("...")){
			deleteBindInPut.setText("...");
			Thread bindListener=new Thread(){
				@Override
				public void run(){
					lastKeyPressed=KeyListener.getNextKey().getRawCode();
					deleteBindInPut.setText(KeyListener.keyCodeToKeyString(lastKeyPressed));
					deleteBindOutPut.setText(Main.checkMacro(lastKeyPressed));
					Thread.currentThread().interrupt();
					return;
				}
			};
			bindListener.start();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Too many requests");
			alert.setHeaderText(null);
			alert.setContentText("Please hit a key before attempting to delete another.");
			alert.showAndWait();
		}
		
	}
	
	public void deleteBind(){
		if (!deleteBindInPut.getText().equals("...")){
			Main.delMacro(lastKeyPressed);
			deleteBindInPut.setText("BLANK");
			deleteBindOutPut.setText("");
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Too early of a request");
			alert.setHeaderText(null);
			alert.setContentText("Please hit a key before attempting to delete it.");
			alert.showAndWait();
		}
	}
	
	//////Bind help//////
	
	public void bindHelp(){
		//TODO Add more explanation for what you can do and how to do it.
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText("Help");
		alert.setContentText("To refference the last user type $$USER or @$$USER to @ them!");
		alert.showAndWait();
	}
	
	/////////////////////////////////////Channel Switching///////////////////////////////////////
	
	public void joinNewChannel(){
		if (newChannel.getText().toString().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Channel to join is blank");
			alert.setContentText("Please enter a channel name to join in the box.");
			alert.showAndWait();
		} else if (newChannel.getText().toString().substring(0,1).equals("#")){ //Channels must have a # before them and be lower case
			MakeBot.joinNewChannel(newChannel.getText().toString().toLowerCase());
			newChannel.setText(""); //Set text back to blank to show user it worked
		} else {
			MakeBot.joinNewChannel("#" + newChannel.getText().toString().toLowerCase());
			newChannel.setText("");
		}
		
	}

}
