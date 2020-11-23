package fr.manchez.snoopy.application.models.menu;

import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Menus;
import javafx.scene.input.KeyCode;

public class GameMenu extends Menu {

    public GameMenu(){
        super();
    }

    @Override
    public void moveArrow(KeyCode keyCode){

        if(keyCode.equals(KeyCode.UP) && isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34* SnoopyWindow.SCALE);
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
        }else if (keyCode.equals(KeyCode.ENTER) && isOption1){/** lance le 1er level*/
            // Main.menu = new MenuLoader(Main.window, Menus.PasswordMenu).load();
            // Main.menu.drawMenu(Main.window);
        }else if(keyCode.equals(KeyCode.ENTER) && !isOption1){

            Main.window.clearAllMotherFucker();
            Main.menu = new MenuLoader(Main.window, Menus.PasswordMenu).load();
            Main.menu.drawMenu(Main.window);

        }

    }
}
