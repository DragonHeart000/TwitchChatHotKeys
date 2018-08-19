package ninja.dragonheart.TwitchHotKeys;

import java.io.IOException;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class MakeBot {
	
	public static void makeNewBot(String name, String oauth){
		Thread bot=new Thread(){
			@SuppressWarnings("resource")
			@Override
			public void run(){
	
				PircBotX twitchBot=null; //Initialize twitchBot so we can access it outside of the while loop
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

}
