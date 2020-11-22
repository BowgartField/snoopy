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
    protected Stage stage;

    //Element root de la fenêtre
    protected Pane pane = new Pane();

    //Scene
    protected Scene scene;

    public static int SCALE = 2;
    public static int width = 580;

    //Sprite total sur l'écran
    private final int totalSpriteInWidth = 20;

    public SnoopyWindow(Stage stage){
        this.stage = stage;

        scene = new Scene(pane);
        stage.setScene(scene);

        System.out.println("Scene: " + scene);

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
    public void events(){}

    public void clearAllMotherFucker(){

        System.out.println(stage);

        getPane().getChildren().clear();
        getPane().setBackground(Background.EMPTY);

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
