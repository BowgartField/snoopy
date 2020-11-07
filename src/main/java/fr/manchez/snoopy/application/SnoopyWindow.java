package fr.manchez.snoopy.application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SnoopyWindow {

    //Stage de la fenêtre
    Stage stage;

    //Element root de la fenêtre
    Pane pane = new Pane();

    //Scene
    Scene scene;

    public static int SCALE = 2;
    public static int width = 580;

    //Sprite total sur l'écran
    private final int totalSpriteInWidth = 20;

    public SnoopyWindow(Stage stage){
        this.stage = stage;

        scene = new Scene(pane);
        stage.setScene(scene);

        initialize();
        events();

    }

    /**
     * Initialize le menu d'accueil
     */
    private void initialize(){


        pane.setPrefWidth(640);
        pane.setPrefHeight(640);
        stage.setResizable(false);

    }

    /**
     * Affiche la fenêtre
     */
    public void show(){
        stage.show();
    }

    /**
     *
     */
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

    /*
        GETTERS
     */

    public int getTotalSpriteInWidth() {
        return totalSpriteInWidth;
    }

    public Scene getScene(){
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public int getScale() {
        return SCALE;
    }
}
