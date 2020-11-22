package fr.manchez.snoopy.application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuWindow extends SnoopyWindow{

    public MenuWindow(Stage stage){
        super(stage);

        System.out.println(stage);
    }

    @Override
    public void events(){

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                Main.menu.moveArrow(event.getCode());

            }
        });


    }

}
