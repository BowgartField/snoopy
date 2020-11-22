package fr.manchez.snoopy.application;

import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.enums.Menus;
import fr.manchez.snoopy.application.models.levels.Level;
import fr.manchez.snoopy.application.models.levels.LevelLoader;
import fr.manchez.snoopy.application.models.menu.Menu;
import fr.manchez.snoopy.application.models.menu.StartMenu;
import fr.manchez.snoopy.application.models.menu.MenuLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Fenêtre principale
     */
    public static SnoopyWindow window;
    public static Stage stage;

    public static Level level;
    public static Menu menu;

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        Main.stage = stage;

        //On créé la fenêtre snoopy
        window = new MenuWindow(stage);

        menu = new MenuLoader(window, Menus.StartMenu).load();
        menu.drawMenu(window);

        //level = new LevelLoader(Levels.MAIN, window).load();
        //level.drawStructure(window);

        //On affiche la fenêtre
        window.show();

    }

}
