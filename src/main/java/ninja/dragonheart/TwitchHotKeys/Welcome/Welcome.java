package ninja.dragonheart.TwitchHotKeys.Welcome;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ninja.dragonheart.TwitchHotKeys.ErrorHandling;
import ninja.dragonheart.TwitchHotKeys.Main;

public class Welcome extends Application{
	
	public static void startWelcome(){
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene=new Scene(create(), javafx.scene.paint.Color.TRANSPARENT);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.setOnHidden(event -> { //reopen on close
			
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
			  
			  
			Platform.exit();
		});
		stage.show();
	}
	
	private Parent create(){
		Pane root=new Pane();
		root.setPrefSize(800, 600);
		root.setBackground(null);
		
		Image image=new Image(getClass().getResourceAsStream("Welcome-Screen.png"));
		
		Button exitBtn=new Button("X");
		exitBtn.setTranslateX(600);
		exitBtn.setOnAction(event -> {
		Platform.exit();
		});
		
		Button continueBtn=new Button("Continue");
		continueBtn.setTranslateX(325);
		continueBtn.setTranslateY(350);
		continueBtn.setOnAction(event -> {
		Platform.exit();
		});
		
		root.getChildren().addAll(new ImageView(image), exitBtn, continueBtn);
		return root;
	}
}
