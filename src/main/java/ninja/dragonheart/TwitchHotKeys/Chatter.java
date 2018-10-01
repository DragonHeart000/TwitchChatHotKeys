package ninja.dragonheart.TwitchHotKeys;

import java.util.ArrayList;

public class Chatter {
	
	String userName;
	ArrayList<ArrayList<String>> allMessagesSent=new ArrayList<ArrayList<String>>(); //2d array with message then timestamp
	int amountOfMessagesSent;
	boolean isMod;
	
	//Constructor
	public Chatter(String userName, String lastMessageSent, long lastMessageSentTimeStamp){
		this.userName = userName;
		
		ArrayList<String> temp=new ArrayList<String>();
		temp.add(lastMessageSent);
		temp.add(Long.toString(lastMessageSentTimeStamp));
		allMessagesSent.add(temp);
		
		amountOfMessagesSent = 1;
		isMod=false; //TODO actually set this var when twitch api is implemented.
	}
	
	//Mutators
	public void update(String lastMessageSent, long lastMessageSentTimeStamp){
		amountOfMessagesSent++;
		
		ArrayList<String> temp=new ArrayList<String>();
		temp.add(lastMessageSent);
		temp.add(Long.toString(lastMessageSentTimeStamp));
		allMessagesSent.add(temp);
	}
	
	//Accessors
	public String getUserName(){
		return userName;
	}
	
	public String getLastMessageSent(){
		return allMessagesSent.get(allMessagesSent.size()-1).get(0);
	}
	
	public ArrayList<ArrayList<String>> getAllMessagesSent(){
		return allMessagesSent;
	}
	
	public long getLastMessageSentTimeStamp(){
		return Long.parseLong(allMessagesSent.get(allMessagesSent.size()-1).get(1));
	}
	
	public int getAmountOfMessagesSent(){
		return amountOfMessagesSent;
	}
	
	public boolean getIfMod(){
		return isMod;
	}
}
