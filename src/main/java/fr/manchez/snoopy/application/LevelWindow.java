package fr.manchez.snoopy.application;

import fr.manchez.snoopy.application.models.levels.Level;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LevelWindow extends SnoopyWindow{

    public LevelWindow(){
        super(Main.stage);

        System.out.println(Main.stage);
    }

    @Override
    public void events(){

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                KeyCode keyCode = event.getCode();

                if(!Main.level.getPersonnage().isMoving()){

                    if(keyCode.equals(KeyCode.LEFT)){
                        Main.level.getPersonnage().moveLeft();
                    }else if(keyCode.equals(KeyCode.RIGHT)){
                        Main.level.getPersonnage().moveRight();
                    }else if(keyCode.equals(KeyCode.UP)){
                        Main.level.getPersonnage().moveUp();
                    }else if(keyCode.equals(KeyCode.DOWN)){
                        Main.level.getPersonnage().moveDown();
                    }

                }

            }
        });

    }

}
