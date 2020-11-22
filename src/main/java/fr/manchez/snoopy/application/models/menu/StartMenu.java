package fr.manchez.snoopy.application.models.menu;

import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Menus;
import javafx.scene.input.KeyCode;


public class StartMenu extends Menu{

    public StartMenu(){
        super();
    }

    /** Gère les déplacements du curseur*/
    @Override
    public void moveArrow(KeyCode keyCode){

        if(keyCode.equals(KeyCode.UP) && isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34*SnoopyWindow.SCALE);
            isOption1 = false;
        }else if(keyCode.equals(KeyCode.UP) && !isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
            isOption1 = true;
        }else if(keyCode.equals(KeyCode.DOWN) && isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34*SnoopyWindow.SCALE);
            isOption1 = false;
        }else if(keyCode.equals(KeyCode.DOWN) && !isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
            isOption1 = true;
        }else if (keyCode.equals(KeyCode.ENTER)){

            /** Démarre le 1er level */
            Main.window.clearAllMotherFucker();
            Main.menu = new MenuLoader(Main.window, Menus.GameMenu).load();
            Main.menu.drawMenu(Main.window);

        }

    }

}
