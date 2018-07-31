package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import ninja.dragonheart.TwitchHotKeys.Welcome.Welcome;


public class Main extends Application{
	
	public static UserSettings loadedSettings;
	public static boolean saveSettings=false;
	
	public static void main(String [] args){
		
		if (!FileHandleing.checkDir()){ //Info screen first time you launch the program
			Welcome.startWelcome();
		} else {
			//TODO check if application is already running and only allow one instance.
			//Start JavaFX application
			Application.launch(args);
				
		}

	}
	
	
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
		scene.getStylesheets().add("default.css"); //Set this dynamically based on user preference
		scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
		//stage.getIcons().add(new Image(getClass().getResourceAsStream("tempiconwhilstmakingit.png")));
		
		 //TODO add close
		 stage.setOnHidden(event -> {
			try {
				//TODO Ensure that everything closes properly
				Platform.exit();
			} catch (Exception e){
				System.out.println("There could be an error as the program did not close properly!");
				Platform.exit();
			}
		});
		stage.show();
    }
	
	public static void setSaveSettings(boolean save){
		saveSettings=save;
	}
    
    public static void setSettings(UserSettings settings){
    	loadedSettings=settings;
    }
    
    public static UserSettings getSettings(){
    	return loadedSettings;
    }
    
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

}
