package ninja.dragonheart.TwitchHotKeys;

public class CustomKey {
	
	private String letterValue;
	private int numberValue;
	
	public CustomKey(String letterValue, int numberValue){
		this.letterValue = letterValue;
		this.numberValue = numberValue;
	}
	
	public String getLetterValue(){
		return letterValue;
	}
	
	public int getNumberValue(){
		return numberValue;
	}
}
