package ninja.dragonheart.TwitchHotKeys;

import java.io.Serializable;

public class Macro implements Serializable{
	
	private static final long serialVersionUID = 906470716726307209L;
	int input;
	String output;
	String condition;
	int amountOfTimesUsed;
	
	public Macro(int input, String output, String condition){
		/*
		 * input is for the key that is hit to trigger the macro
		 * output is for the message outputted when the key is hit
		 * condition is to allow for easier implementation of future more advanced macros
		 * (such as ones that use the twitch api to see what game is being played and change the macro accordingly or different profiles)
		 */
		
		this.input=input;
		this.output=output;
		this.condition=condition;
		amountOfTimesUsed=0;
	}
	
	public int getInput(){
		return input;
	}
	
	public String getOutput(){
		amountOfTimesUsed++;
		return output;
	}
	
	//GAME::GAMENAME will be for using a macro only when GAMENAME is being played (need to implement twitch api for this to work).
	//COMMAND::COMMANDNAME will be for auto responding to when COMMANDNAME is said in chat.
	public String getCondition(){
		return condition;
	}
	
	public int getUsage(){
		return amountOfTimesUsed;
	}

}
