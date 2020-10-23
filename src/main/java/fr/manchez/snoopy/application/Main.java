package fr.manchez.snoopy.application;

import fr.manchez.snoopy.application.enums.Level;
import fr.manchez.snoopy.application.models.Levels.LevelLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Fenêtre principale
     */
    public static SnoopyWindow window;

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        //On créé la fenêtre snoopy
        window = new SnoopyWindow(stage);

        LevelLoader levelLoader = new LevelLoader(Level.MAIN, window);
        levelLoader.draw();

        //On affiche la fenêtre
        window.show();

    }

}
