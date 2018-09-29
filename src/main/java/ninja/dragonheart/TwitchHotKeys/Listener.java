package ninja.dragonheart.TwitchHotKeys;

import java.util.ArrayList;

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
		
		//TODO add option to turn logging of since this can be taxing for streams with many chat messages
		boolean newUser=true;
		ArrayList<Chatter> tempChattersList = Main.getChatters();
		for(Chatter person : tempChattersList){
			if (person.getUserName().equals(lastUser)){
				newUser=false;
				person.update(event.getMessage(), event.getTimestamp());
				Main.updateChatters(tempChattersList);
				break;
			}
		}
		if (newUser){
			tempChattersList.add(new Chatter(event.getUser().getNick(), event.getMessage(), event.getTimestamp()));
			Main.updateChatters(tempChattersList);
		}
	}
	
	public void onConnect(final ConnectEvent event){
		if (Main.doConnectMessage){
			event.getBot().sendIRC().message(Main.getChannel(), "Twitch Chat Hot Keys loaded and ready for use! Version 0.3.7 Alpha. https://www.thk.chat");
		}
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
