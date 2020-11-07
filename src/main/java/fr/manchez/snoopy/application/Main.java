package fr.manchez.snoopy.application;

import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.models.levels.Level;
import fr.manchez.snoopy.application.models.levels.LevelLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Fenêtre principale
     */
    public static SnoopyWindow window;

    public static Level level;

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        //On créé la fenêtre snoopy
        window = new SnoopyWindow(stage);

        level = new LevelLoader(Levels.MAIN, window).load();
        level.drawStructure(window);

        //On affiche la fenêtre
        window.show();

    }

}
