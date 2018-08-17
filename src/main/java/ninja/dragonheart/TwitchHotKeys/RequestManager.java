package ninja.dragonheart.TwitchHotKeys;

import org.pircbotx.hooks.events.ConnectEvent;

public class RequestManager {
	
	public static ConnectEvent event;
	public static UserSettings loadedSettings=Main.getSettings();
	
	public static void setBotRefference(ConnectEvent newEvent){
		event=newEvent;
	}
	
	public static void updateSettings(UserSettings newSettings){
		loadedSettings=newSettings;		
	}
	
	public static void requestMessageSent(String request){
		if (request.contains("$$USER")){
			event.getBot().sendIRC().message(loadedSettings.getChannel(),
					request.substring(0, request.indexOf("$")) +
					Listener.getLastUser() +
					request.substring(request.indexOf("$")+6, request.length()));
		} else {
			event.getBot().sendIRC().message(loadedSettings.getChannel(), request); //If no special commands send standard message as is
		}
	}
	
}
