package ninja.dragonheart.TwitchHotKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandleing {
	
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
		return true;
	}
	
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
	
	public static UserSettings readInUserSettings(String fileName){
		checkDir();
		UserSettings settingsToReturn=null;
		try {
			ObjectInputStream is=new ObjectInputStream(new FileInputStream(fileName));
			try {
				settingsToReturn=(UserSettings) is.readObject(); //Reads the object
			} catch (ClassNotFoundException e) {
				System.out.println("ERROR: ClassNotFoundException when reading in channel settings!");
				e.printStackTrace();
			}
			is.close();
		} catch (IOException e) {
			System.out.println("ERROR: IOException when reading in channel settings!");
			e.printStackTrace();
		}
		
		return new UserSettings(settingsToReturn.getUserName(), settingsToReturn.getOauth(), settingsToReturn.getMacros());
	}

}
