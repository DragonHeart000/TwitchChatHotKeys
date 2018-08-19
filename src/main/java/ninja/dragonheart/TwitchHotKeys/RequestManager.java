package ninja.dragonheart.TwitchHotKeys;

import java.time.LocalDateTime;

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
		if (request.contains("$$DATE")){ //The current date in m/d/y format
			requestMessageSent(request.substring(0, request.indexOf("$$DATE")) + //Start of request
					LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear() + //Var
					request.substring(request.indexOf("$$DATE")+6, request.length())); //End of request
		} else if (request.contains("$$TIME")){ //The current time in hh:ss format
			requestMessageSent(request.substring(0, request.indexOf("$$TIME")) + //Start of request
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + //Var  (Should we make vars for different time formats??)
					request.substring(request.indexOf("$$TIME")+6, request.length())); //End of request
		} else if (request.contains("$$USERMESSAGE")){
			requestMessageSent(request.substring(0, request.indexOf("$$USERMESSAGE")) + //Start of request
					Listener.getLastUserMessage() + //Var  (Should we make vars for different time formats??)
					request.substring(request.indexOf("$$USERMESSAGE")+13, request.length())); //End of request
		} else if (request.contains("$$USER")){ //The most recent user to talk in chat
			requestMessageSent(request.substring(0, request.indexOf("$$USER")) +
					Listener.getLastUser() +
					request.substring(request.indexOf("$$USER")+6, request.length()));
					//Use recursion to make sure we get every instance of a var and other vars
		} else {
			event.getBot().sendIRC().message(loadedSettings.getChannel(), request); //If no special commands send standard message as is
		}
	}
	
}