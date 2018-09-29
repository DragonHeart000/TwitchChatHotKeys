package ninja.dragonheart.TwitchHotKeys;

import java.util.ArrayList;

public class Chatter {
	
	String userName;
	ArrayList<String> allMessagesSent=new ArrayList<String>();
	long lastMessageSentTimeStamp;
	int amountOfMessagesSent;
	boolean isMod;
	
	//Constructor
	public Chatter(String userName, String lastMessageSent, long lastMessageSentTimeStamp){
		this.userName = userName;
		allMessagesSent.add(lastMessageSent);
		this.lastMessageSentTimeStamp = lastMessageSentTimeStamp;
		amountOfMessagesSent = 1;
		isMod=false; //TODO actually set this var when twitch api is implemented.
	}
	
	//Mutators
	public void update(String lastMessageSent, long lastMessageSentTimeStamp){
		amountOfMessagesSent++;
		allMessagesSent.add(lastMessageSent);
		this.lastMessageSentTimeStamp = lastMessageSentTimeStamp;
	}
	
	//Accessors
	public String getUserName(){
		return userName;
	}
	
	public String getLastMessageSent(){
		return allMessagesSent.get(allMessagesSent.size()-1);
	}
	
	public long getLastMessageSentTimeStamp(){
		return lastMessageSentTimeStamp;
	}
	
	public int getAmountOfMessagesSent(){
		return amountOfMessagesSent;
	}
	
	public boolean getIfMod(){
		return isMod;
	}
}
