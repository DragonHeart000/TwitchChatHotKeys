package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class MakeBot {
	
	public static Thread bot=null;
	public static PircBotX twitchBot=null; //Initialize twitchBot
	
	public static void makeNewBot(String name, String oauth){
		
		//Configure Twitch bot with given info
		Configuration twitchConfiguration = new Configuration.Builder()
				.setAutoNickChange(false) //Twitch doesn't support multiple users
				.setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command
				.setCapEnabled(true)
				.addServer("irc.twitch.tv")
				.setName(name) //Your twitch.tv username
				.setServerPassword(oauth) //Your oauth password from http://twitchapps.com/tmi
				.addAutoJoinChannel("#"+name.toLowerCase()) //Some twitch channel
				.addListener(new Listener())
				.buildConfiguration();
			
		//Create bot
		twitchBot = new PircBotX(twitchConfiguration);
		
		bot=new Thread(){
			@Override
			public void run(){
				//Start with try catch
				try {
					twitchBot.startBot();
				} catch (IOException e) {
					System.out.println("ERROR: IOException in twitchBot");
					e.printStackTrace();
				} catch (IrcException e) {
					System.out.println("ERROR: IrcException in twitchBot");
					e.printStackTrace();
				}
			}
		};
		
		bot.start();
	}
	
	public static void killThread(){
		System.out.println("Attempting to kill threads");
		try {
			if (bot.isAlive()){
				twitchBot.stopBotReconnect(); //Stop the bot from auto reconnecting
				twitchBot.sendIRC().quitServer(); //Disconnect from server
				twitchBot.close(); //Force close to ensure it quitsl
			} else {
				System.out.println("BOT IS NOT ALIVE");
			}
		} catch (Exception e){
			System.out.println("FAILED");
			ErrorHandling.error(e, "Failed to close threads!");
			//Try catch in order to prevent errors in event that the program is terminated via this method before start() is called
		}
	}

}
