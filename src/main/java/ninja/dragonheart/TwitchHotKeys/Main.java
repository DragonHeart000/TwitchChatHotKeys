package ninja.dragonheart.TwitchHotKeys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ninja.dragonheart.TwitchHotKeys.Welcome.NewUpdate;
import ninja.dragonheart.TwitchHotKeys.Welcome.Welcome;


public class Main extends Application{
	
	private static final String VERSION="0.4.0Alpha";
	private static String newestVersion="";
	
	public static UserSettings loadedSettings;
	public static boolean saveSettings=false;
	public static String channel;
	public static boolean doConnectMessage=true;
	
	public static void main(String [] args){
		if (!FileHandleing.checkDir()){ //Info screen first time you launch the program
			Welcome.startWelcome();
		} else {
			//TODO check if application is already running and only allow one instance.
			//Start JavaFX application
			//Application.launch(args);
			
			JSONObject gitHubJson;
			try {
				gitHubJson = readJsonFromUrl("https://api.github.com/repos/DragonHeart000/TwitchChatHotKeys/releases/latest");
				newestVersion=gitHubJson.get("tag_name").toString();
			    
			    if (!FileHandleing.exists("C://TwitchChatHotKeys/update.bin")){ //Make sure the update settings are there
					FileHandleing.writeOutString("updated", "C://TwitchChatHotKeys/update.bin");
				}
			    
			    if(!VERSION.equals(gitHubJson.get("tag_name")) && !FileHandleing.readInString("C://TwitchChatHotKeys/update.bin").equals(gitHubJson.get("tag_name"))){
			    	//Not running most recent version && not wanting to skip it
			    	NewUpdate.startUpdate();
			    } else {
			    	Application.launch(args); //If running latest version run program
			    }
			    
			} catch (JSONException e) {
				ErrorHandling.error(e, "Failed to get most recent version from gitHub because of JSONException. Starting without checking."); //Warn user
				Application.launch(args); //Start anyway
			} catch (IOException e) {
				ErrorHandling.error(e, "Failed to get most recent version from gitHub because of IOException. starting without checking.");
				Application.launch(args);
			}

		}

	}
	
	/////////////////////////////////////JSON reading///////////////////////////////////////

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
	    	sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
	    }
	}
	
	/////////////////////////////////////JavaFX start///////////////////////////////////////
	  
	@Override
    public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		Parent twitchChatHotKeysSetup=null;
		try {
			twitchChatHotKeysSetup = loader.load();
		} catch (IOException e1) {
			System.out.println("ERROR: IOException on loader.load() in start method in main class");
			e1.printStackTrace();
		}
		//Control localControllerVar = loader.getController();
		stage.setTitle("Twitch Chat Hot Keys");
		Scene scene = new Scene(twitchChatHotKeysSetup, 800, 600);
		stage.setScene(scene);
		stage.setResizable(false);
		
		if (FileHandleing.exists("C://TwitchChatHotKeys/styles/set style.bin")){
			try {
				scene.getStylesheets().add(getClass().getResource(FileHandleing.readInString("C://TwitchChatHotKeys/styles/set style.bin")).toExternalForm());
			} catch (Exception e){
				ErrorHandling.error(e, "Something has gone wrong with your skin! Likely this was caused by the skin being deleted.");
				scene.getStylesheets().add(getClass().getResource("Styling/PreMades/Slip Stream.css").toExternalForm()); //Slip Stream is dark
			}
		} else {
			FileHandleing.writeOutString("Styling/PreMades/dark.css", "C://TwitchChatHotKeys/styles/set style.bin"); //Make sure that set style.bin has been writen out and will be able to be read by writing out the default skin to it.
			scene.getStylesheets().add(getClass().getResource("Styling/PreMades/Slip Stream.css").toExternalForm()); //Default is dark
		}
		
		//scene.getStylesheets().add("default.css");
		
		//TODO make icon
		//stage.getIcons().add(new Image(getClass().getResourceAsStream("tempiconwhilstmakingit.png")));

		 stage.setOnHidden(event -> {
			try {
				System.out.println("Calling thread kill");
				if (MakeBot.checkThread()){
					MakeBot.killThread();
					KeyListener.endKeyListener();
				}
				Platform.exit();
			} catch (Exception e){
				ErrorHandling.error(e, "There could be an error as the program did not close properly!");
				System.out.println("There could be an error as the program did not close properly!");
				Platform.exit();
			}
		});
		stage.show();
    }
	
	/////////////////////////////////////Data///////////////////////////////////////
	
	/////Settings/////
	public static void setSaveSettings(boolean save){
		saveSettings=save;
	}
    
    public static void setSettings(UserSettings settings){
    	loadedSettings=settings;
    }
    
    public static UserSettings getSettings(){
    	return loadedSettings;
    }
    
    //Macros//
    public static void addSettingsMacro(Macro macroToAdd){
    	loadedSettings.getMacros().add(macroToAdd);
    	if (saveSettings){
    		FileHandleing.writeOutUserSettings(loadedSettings, "C://TwitchChatHotKeys/SavedSettings.bin");
    	}
    }
    
    public static String checkMacro(int key){
    	if (loadedSettings.getMacros() != null){
	    	for (Macro temp : loadedSettings.getMacros()){
	    		if (key == temp.getInput()){
	    			return temp.getOutput();
	    		}
	    	}
    	}
    	return "NO OUTPUT";
    }
    
    public static void delMacro(int key){
    	for (Macro temp : loadedSettings.getMacros()){
    		if (temp.getInput() == key){
    			loadedSettings.getMacros().remove(temp);
    			if (saveSettings){
    	    		FileHandleing.writeOutUserSettings(loadedSettings, "C://TwitchChatHotKeys/SavedSettings.bin");
    	    	}
    			return;
    		}
    	}
    }	
    
    /////Channel/////
    public static void setChannel(String toSet){
    	channel=toSet;
    }
    
    public static String getChannel(){
		return channel;
    }
    
    /////Connection message/////
    public static boolean getDoConnectMessage(){
    	return doConnectMessage;
    }
    
    public static void setDoConnectMessage(boolean doIt){
    	doConnectMessage=doIt;
    }
    
    /////Version/////
    public static String getNewestVersionNumber(){
    	return newestVersion;
    }

}
