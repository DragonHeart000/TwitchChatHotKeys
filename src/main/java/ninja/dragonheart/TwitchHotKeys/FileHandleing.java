package ninja.dragonheart.TwitchHotKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHandleing {
	
	/////////////////////////////////////Checking///////////////////////////////////////
	
	public static boolean exists(String name){
		checkDir();
		File temp = new File(name);
		System.out.println(name);
		System.out.println(temp.getAbsoluteFile().exists());
		return temp.getAbsoluteFile().exists();
	}
	
	public static boolean checkDir(){
		if(!new File("C://TwitchChatHotKeys").exists()){
			try {
				Files.createDirectories(Paths.get("C://TwitchChatHotKeys"));
			} catch (IOException e) {
				System.out.println("ERROR: IOException when creating path C://TwitchChatHotKeys");
				ErrorHandling.error(e, "ERROR: IOException when creating path C://TwitchChatHotKeys");
				e.printStackTrace();
			}
			return false;
		}
		
		if (!new File("C://TwitchChatHotKeys/styles").exists()){
			try {
				Files.createDirectories(Paths.get("C://TwitchChatHotKeys/styles"));
			} catch (IOException e) {
				System.out.println("ERROR: IOException when creating path C://TwitchChatHotKeys");
				ErrorHandling.error(e, "ERROR: IOException when creating path C://TwitchChatHotKeys");
				e.printStackTrace();
			}
			return false;
		}
		
		return true;
	}
	
	/////////////////////////////////////Write out///////////////////////////////////////
	
	public static void writeOutUserSettings(UserSettings toWrite, String filePath){
		checkDir();
		try {
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(filePath)); //File path should look as such C://TwitchHotKeys/NAMEOFFILE
			os.writeObject(toWrite); //Writes the given object to file
			os.close();
		} catch (IOException e) {
			System.out.println("ERROR: IOException when writing out user settings!");
			ErrorHandling.error(e, "ERROR: IOException when writing out user settings!");
			e.printStackTrace();
		} 
	}
	
	public static void writeOutString(String toWrite, String filePath){ //Used for writing out skin preference but has generic name for possible future uses
		checkDir();
		try {
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(filePath)); //File path should look as such C://TwitchChatHotKeys/styles/set style.bin
			os.writeObject(toWrite); //Writes the given object to file
			os.close();
		} catch (IOException e) {
			System.out.println("ERROR: IOException when writing out string!");
			ErrorHandling.error(e, "ERROR: IOException when writing out string!");
			e.printStackTrace();
		} 
	}
	
	public static <E> void writeOutArray(ArrayList<E> toWrite, String filePath){ //Used for writing out skin preference but has generic name for possible future uses
		checkDir();
		try {
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(filePath)); //File path should look as such C://TwitchChatHotKeys/styles/set style.bin
			os.writeObject(toWrite); //Writes the given object to file
			os.close();
		} catch (IOException e) {
			System.out.println("ERROR: IOException when writing out ArrayList!");
			ErrorHandling.error(e, "ERROR: IOException when writing out ArrayList!");
			e.printStackTrace();
		} 
	}
	
	/////////////////////////////////////Read in///////////////////////////////////////
	
	public static UserSettings readInUserSettings(String fileName){
		checkDir();
		UserSettings settingsToReturn=null;
		try {
			ObjectInputStream is=new ObjectInputStream(new FileInputStream(fileName));
			try {
				settingsToReturn=(UserSettings) is.readObject(); //Reads the object
			} catch (ClassNotFoundException e) {
				ErrorHandling.error(e, "ERROR: ClassNotFoundException when reading in channel settings!");
				System.out.println("ERROR: ClassNotFoundException when reading in channel settings!");
				e.printStackTrace();
			}
			is.close();
		} catch (IOException e) {
			ErrorHandling.error(e, "ERROR: IOException when reading in channel settings!");
			System.out.println("ERROR: IOException when reading in channel settings!");
			e.printStackTrace();
		}
		
		return new UserSettings(settingsToReturn.getUserName(), settingsToReturn.getOauth(), settingsToReturn.getMacros());
	}
	
	public static String readInString(String fileName){
		checkDir();
		String stringToReturn="";
		try {
			ObjectInputStream is=new ObjectInputStream(new FileInputStream(fileName));
			stringToReturn=(String) is.readObject(); //Reads the object
			is.close();
		} catch (ClassNotFoundException e) {
			ErrorHandling.error(e, "ERROR: IOException when reading in!");
			e.printStackTrace();
		} catch (IOException e) {
			ErrorHandling.error(e, "ERROR: IOException when reading in String!");
			System.out.println("ERROR: IOException when reading in String!");
			e.printStackTrace();
		}
		
		return stringToReturn;
	}
	
	@SuppressWarnings("unchecked")
	public static <E> ArrayList<E> readInArray(String fileName){
		checkDir();
		ArrayList<E> stringToReturn = null;
		try {
			ObjectInputStream is=new ObjectInputStream(new FileInputStream(fileName));
			stringToReturn=(ArrayList<E>) is.readObject(); //Reads the object
			is.close();
		} catch (ClassNotFoundException e) {
			ErrorHandling.error(e, "ERROR: IOException when reading in!");
			e.printStackTrace();
		} catch (IOException e) {
			ErrorHandling.error(e, "ERROR: IOException when reading in ArrayList!");
			System.out.println("ERROR: IOException when reading in ArrayList!");
			e.printStackTrace();
		}
		
		return stringToReturn;
	}

}
