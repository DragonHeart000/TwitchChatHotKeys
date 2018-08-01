package ninja.dragonheart.TwitchHotKeys;

import java.util.ArrayList;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener{
	
	private static volatile boolean waiting=false;
	private static NativeKeyEvent lastRequestedKeyPress;
	private static ArrayList<CustomKey> keyCodes= new ArrayList<CustomKey>() {{
		add(new CustomKey("f1", 112));
		add(new CustomKey("f2", 113));
		add(new CustomKey("f3", 114));
		add(new CustomKey("f4", 115));
		add(new CustomKey("f5", 116));
		add(new CustomKey("f6", 117));
		add(new CustomKey("f7", 118));
		add(new CustomKey("f8", 119));
		add(new CustomKey("f9", 120));
		add(new CustomKey("f10", 121));
		add(new CustomKey("f11", 122));
		add(new CustomKey("f12", 123));

		add(new CustomKey("esc", 27));
		add(new CustomKey("`", 192));
		add(new CustomKey("1", 49));
		add(new CustomKey("2", 50));
		add(new CustomKey("3", 51));
		add(new CustomKey("4", 52));
		add(new CustomKey("5", 53));
		add(new CustomKey("6", 54));
		add(new CustomKey("7", 55));
		add(new CustomKey("8", 56));
		add(new CustomKey("9", 57));
		add(new CustomKey("0", 48));
		add(new CustomKey("-", 189));
		add(new CustomKey("=", 187));
		add(new CustomKey("backspace", 8));

		add(new CustomKey("tab", 9));
		add(new CustomKey("q", 81));
		add(new CustomKey("w", 87));
		add(new CustomKey("e", 69));
		add(new CustomKey("r", 82));
		add(new CustomKey("t", 84));
		add(new CustomKey("y", 89));
		add(new CustomKey("u", 85));
		add(new CustomKey("i", 73));
		add(new CustomKey("o", 79));
		add(new CustomKey("p", 80));
		add(new CustomKey("[", 219));
		add(new CustomKey("]", 221));
		add(new CustomKey("\\", 220));
		add(new CustomKey("caps lock", 20));
		add(new CustomKey("a", 65));
		add(new CustomKey("s", 83));
		add(new CustomKey("d", 68));
		add(new CustomKey("f", 70));
		add(new CustomKey("g", 71));
		add(new CustomKey("h", 72));
		add(new CustomKey("j", 74));
		add(new CustomKey("k", 75));
		add(new CustomKey("l", 76));
		add(new CustomKey(";", 186));
		add(new CustomKey("'", 222));
		add(new CustomKey("enter", 13));
		add(new CustomKey("shift", 16));
		add(new CustomKey("z", 90));
		add(new CustomKey("x", 88));
		add(new CustomKey("c", 67));
		add(new CustomKey("v lock", 86));
		add(new CustomKey("b", 66));
		add(new CustomKey("n", 78));
		add(new CustomKey("m", 77));
		add(new CustomKey(",", 188));
		add(new CustomKey(".", 190));
		add(new CustomKey("/", 191));
		add(new CustomKey("ctrl", 17));
		add(new CustomKey("Lwindow", 91));
		add(new CustomKey("alt", 18));
		add(new CustomKey("space", 32));
		add(new CustomKey("Rwindow", 92));
		add(new CustomKey("menu", 93));
		
		add(new CustomKey("print scrn", 44));
		add(new CustomKey("scroll lock", 145));
		add(new CustomKey("pause", 19));
		add(new CustomKey("insert", 45));
		add(new CustomKey("home", 36));
		add(new CustomKey("pageU", 33));
		add(new CustomKey("del", 46));
		add(new CustomKey("end", 35));
		add(new CustomKey("pageD", 34));
		
		add(new CustomKey("up", 38));
		add(new CustomKey("down", 40));
		add(new CustomKey("left", 37));
		add(new CustomKey("right", 39));
		
		add(new CustomKey("num lock", 144));
		add(new CustomKey("num /", 111));
		add(new CustomKey("num *", 106));
		add(new CustomKey("num -", 109));
		add(new CustomKey("num 7", 103));
		add(new CustomKey("num 8", 104));
		add(new CustomKey("num 9", 105));
		add(new CustomKey("num +", 107));
		add(new CustomKey("num 4", 100));
		add(new CustomKey("num 5", 101));
		add(new CustomKey("num 6", 102));
		add(new CustomKey("num 1", 97));
		add(new CustomKey("num 2", 98));
		add(new CustomKey("num 3", 99));
		add(new CustomKey("num 0", 96));
		add(new CustomKey("num .", 110));
	}};
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		
		//Save next key stroke if the program is waiting for a key to be hit to know what to bind it to
		if (waiting){
			lastRequestedKeyPress=e;
			waiting=false;
		}
		
		
		for (Macro temp : Main.getSettings().getMacros()){
			if (temp.getInput() == e.getRawCode()){
				RequestManager.requestMessageSent(temp.getOutput());
			}
		}
		
		/*
		if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("Pause")){
			RequestManager.requestMessageSent("/followers 0");
		} else if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("Scroll Lock")){
			RequestManager.requestMessageSent("/followersoff");
		}
		*/
		
		System.out.println("e.getRawCode(): " + e.getRawCode());
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}
	
	public static NativeKeyEvent getNextKey(){
		waiting=true;
		while(waiting){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				ErrorHandling.error(e);
			}
		}
		return lastRequestedKeyPress;
	}
	
	public static String keyCodeToKeyString(int key){
		for (CustomKey i : keyCodes){
			if (i.getNumberValue() == key){
				return i.getLetterValue();
			}
		}
		return "ERROR NO VALUE FOUND";
	}
	
}
