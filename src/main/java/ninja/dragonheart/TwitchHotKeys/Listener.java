package ninja.dragonheart.TwitchHotKeys;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Listener extends ListenerAdapter{
	

	public static String lastUser;
	public static String lastUserMessage;
	final UserSettings LOADEDSETTINGS=Main.getSettings();

	
	@Override
    public void onGenericMessage(GenericMessageEvent event) {
		lastUser=event.getUser().getNick();
		System.out.println(lastUser);
		lastUserMessage=event.getMessage();
	}
	
	public void onConnect(final ConnectEvent event){
		event.getBot().sendIRC().message(LOADEDSETTINGS.getChannel(), "Twitch Chat Hot Keys loaded and ready for use! Version 0.3.0 Alpha. https://www.thk.chat");
		RequestManager.setBotRefference(event);
	}
	
	//Get IRC info
	
	public static String getLastUser(){
		//Useful for banning or timing out users with a macro. This will return the most recent person to talk in chat
		return lastUser;
	}
	
	public static String getLastUserMessage(){
		//Useful for giving a reason for a ban
		return lastUserMessage;
	}
	
}
