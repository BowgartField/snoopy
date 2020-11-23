package fr.manchez.snoopy.application.models.menu;

import fr.manchez.snoopy.application.MenuWindow;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Menus;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import sun.security.util.Password;

public class MenuLoader {

    /** Fenêtre dans laquelle le menu sera chargé */
    SnoopyWindow window;

    /** Background à charger */
    Menus menuToLoad;

    Menu menu;

    public MenuLoader(SnoopyWindow window, Menus menuToLoad){
        this.window = window;
        this.menuToLoad = menuToLoad; //emplacement du background chargé
    }

    public Menu load(){

        window.getPane().getChildren().removeAll();
        setBackground();

        if(menuToLoad.equals(Menus.StartMenu)){
            menu = new StartMenu();
        }else if (menuToLoad.equals(Menus.GameMenu)){
            menu = new GameMenu();
        }else if(menuToLoad.equals(Menus.PasswordMenu)){
            menu = new PasswordMenu();
        }
        return menu;
    }

    private void setBackground() {

        window.getPane().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(
                                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+menuToLoad.getUrl()),
                                        640,640,true,true
                                ),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        )
                )
        );

    }

}
