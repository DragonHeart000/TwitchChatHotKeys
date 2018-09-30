package ninja.dragonheart.TwitchHotKeys;

import java.util.ArrayList;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Listener extends ListenerAdapter{
	

	public static String lastUser;
	public static String lastUserMessage;
	public UserSettings loadedSettings;
	public ArrayList<Macro> autoResponses;

	
	@Override
    public void onGenericMessage(GenericMessageEvent event) {
		/////////////////Set data/////////////////
		lastUser=event.getUser().getNick();
		System.out.println(lastUser);
		lastUserMessage=event.getMessage();
		
		/////////////////Stats && Logs/////////////////
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
		
		/////////////////Auto Responses/////////////////
		if(loadedSettings.getMacros().size() != Main.getSettings().getMacros().size()){
			//If a new macro has been added we need to check if it is a auto response
			autoResponses.clear();
			for(Macro i : loadedSettings.getMacros()){
				System.out.println(i.getCondition().substring(0, 8));
				if(i.getCondition().length() > 8 && i.getCondition().substring(0, 8).equals("COMMAND::")){
					autoResponses.add(i);
				}
			}
		}
		for(Macro i : autoResponses){
			if(i.getCondition().substring(8, i.getCondition().length()-1).equals(event.getMessage().toString().toLowerCase())){
				event.respondWith(i.getCondition());
			}
		}
	}
	
	public void onConnect(final ConnectEvent event){
		if (Main.doConnectMessage){
			event.getBot().sendIRC().message(Main.getChannel(), "Twitch Chat Hot Keys loaded and ready for use! Version 0.3.7 Alpha. https://www.thk.chat");
		}
		loadedSettings=Main.getSettings();
		System.out.println(loadedSettings.toString());
		for(Macro i : loadedSettings.getMacros()){
			System.out.println(i.getCondition().substring(0, 8));
			if(i.getCondition().length() > 8 && i.getCondition().substring(0, 8).equals("COMMAND::")){
				autoResponses.add(i);
			}
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
