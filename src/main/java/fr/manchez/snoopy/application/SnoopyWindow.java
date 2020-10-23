package fr.manchez.snoopy.application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    //Sprite total sur l'écran
    private final int totalSpriteInWidth = 20;

    public SnoopyWindow(Stage stage){
        this.stage = stage;

        scene = new Scene(pane);
        stage.setScene(scene);

        initialize();
    }

    /**
     * Initialize le menu d'accueil
     */
    private void initialize(){

         /*

            1   |  9  |   1
            16     32     16

            -> 1 sprite de 16 px
            -> 9 sprites de 32 px
            -> 1 sprite de 16  px

            On multiplie la taille par 2 sinon c'est trop petit

        */

        pane.setPrefWidth(640);
        pane.setPrefHeight(640);


        pane.setBackground(
            new Background(
                new BackgroundImage(
                        new Image(
                                getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/fond.png"),
                                580,580,true,true
                        ),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
                )
            )
        );

        stage.setResizable(false);

    }

    public void show(){
        stage.show();
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
