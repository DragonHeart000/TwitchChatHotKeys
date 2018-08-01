package ninja.dragonheart.TwitchHotKeys;

import java.io.Serializable;
import java.util.ArrayList;

public class UserSettings implements Serializable{
	
	
	private static final long serialVersionUID = 8764355452085670072L;
	String userName;
	String oauth;
	String channel;
	ArrayList<Macro> macros;
	
	public UserSettings(String userName, String oauth, ArrayList<Macro> macros){
		this.userName=userName;
		this.oauth=oauth;
		channel="#"+userName.toLowerCase();
		this.macros=macros;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getOauth(){
		return oauth;
	}
	
	public String getChannel(){
		return channel;
	}
	
	public ArrayList<Macro> getMacros(){
		return macros;
	}

}
