package fr.manchez.snoopy.application;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SnoopyWindow{

    //Stage de la fenêtre
    protected Stage stage;

    //Element root de la fenêtre
    protected Pane pane = new Pane();

    //Scene
    protected Scene scene;

    public static int SCALE = 2;
    public static int width = 580;

    //Sprite total sur l'écran
    protected final int totalSpriteInWidth = 20;

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
    protected void initialize(){

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
