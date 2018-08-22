package ninja.dragonheart.TwitchHotKeys.Welcome;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ninja.dragonheart.TwitchHotKeys.ErrorHandling;
import ninja.dragonheart.TwitchHotKeys.FileHandleing;
import ninja.dragonheart.TwitchHotKeys.Main;

public class NewUpdate extends Application{
	
	private static boolean doReopen=false;
	
	public static void startUpdate(){
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene=new Scene(create(), javafx.scene.paint.Color.TRANSPARENT);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.setOnHidden(event -> {
			if(doReopen){
				//reopen on close if asked to
				
				try {
				final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
				final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	
				// is it a jar file?
				if(!currentJar.getName().endsWith(".jar")){
					return;
				}
	
				// Build command: java -jar application.jar
				final ArrayList<String> command = new ArrayList<String>();
				command.add(javaBin);
				command.add("-jar");
				command.add(currentJar.getPath());
	
				final ProcessBuilder builder = new ProcessBuilder(command);
				builder.start();
				} catch (Exception e){
					System.out.println("Error rebooting program!");
					ErrorHandling.error(e, "Error rebooting program!");
					e.printStackTrace();
				}
			}
			  
			Platform.exit();
			
		});
		stage.show();
	}
	
	private Parent create(){
		Pane root=new Pane();
		root.setPrefSize(800, 600);
		root.setBackground(null);
		
		Image image=new Image(getClass().getResourceAsStream("New Update Screen Background.png"));
		
		Button exitBtn=new Button("X");
		exitBtn.setTranslateX(600);
		exitBtn.setOnAction(event -> {
			Platform.exit();
		});
		
		Button continueBtn=new Button("Never update");
		continueBtn.setTranslateX(323);
		continueBtn.setTranslateY(350);
		continueBtn.setOnAction(event -> {
			doReopen=true;
			FileHandleing.writeOutString("skip", "C://TwitchChatHotKeys/update.bin");
			Platform.exit();
		});
		
		Button goToUpdateBtn=new Button("Get update");
		goToUpdateBtn.setTranslateX(325);
		goToUpdateBtn.setTranslateY(320);
		goToUpdateBtn.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION); //Create alert
			alert.setTitle("Confirmation");
			alert.setHeaderText("This will open up the GitHub releases page for Twitch Chat Hot Keys in your web browser");
			alert.setContentText("Do you wish to proceed");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){ //If user says ok then the website will open otherwise nothing happens
				String url_open ="https://github.com/DragonHeart000/TwitchChatHotKeys/releases"; //Set URL to open
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
				} catch (IOException e) {
					System.out.println("Error opening webpage on github page!");
					ErrorHandling.error(e, "Error opening webpage on github page!");
					e.printStackTrace();
				}  //opens the URL in the default browser
			} 
			Platform.exit();
		});
		
		root.getChildren().addAll(new ImageView(image), exitBtn, continueBtn, goToUpdateBtn);
		return root;
	}

}
