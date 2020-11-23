package fr.manchez.snoopy.application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MenuWindows extends SnoopyWindow{

    public MenuWindows(Stage stage){
        super(stage);
    }

    @Override
    public void events(){

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                KeyCode keyCode = event.getCode();

                //ICI FAIRE BOUGER L'ARROW DE selection

            }
        });

    }

}
