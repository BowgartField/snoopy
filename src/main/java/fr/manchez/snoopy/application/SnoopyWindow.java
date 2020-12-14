package fr.manchez.snoopy.application;

import fr.manchez.snoopy.application.enums.*;
import fr.manchez.snoopy.application.models.display.Display;
import fr.manchez.snoopy.application.models.display.DisplayLoader;
import fr.manchez.snoopy.application.models.levels.LevelDisplay;
import fr.manchez.snoopy.application.models.levels.LevelDisplayLoader;
import fr.manchez.snoopy.application.models.sauvegarde.Sauvegarde;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class SnoopyWindow{

    /** Joueur sélectionné **/
    private PlayersType playersType;

    /** Sauvegarde **/
    private final Sauvegarde sauvegarde;

    /** Media player **/
    AudioClip themeClip;
    AudioClip soundClip;

    /** Stage de la fenêtre **/
    private Stage stage;

    /** Element root de la fenêtre **/
    private Pane pane;

    /** Scene **/
    private Scene scene;

    /** Display */
    private Display display;

    /** Level Display **/
    private LevelDisplay levelDisplay;

    /** Valeur de la mise à l'échelle **/
    public static int SCALE = 2;

    public SnoopyWindow(Stage stage, Displays displays){

        this.stage = stage;

        pane = new Pane();
        scene = new Scene(pane);
        stage.setScene(scene);

        this.sauvegarde = new Sauvegarde(this);

        loadNewDisplay(displays);

        initialize();
        eventsCatcher();

    }

    /**
     * Initialize la fenêtre
     */
    private void initialize(){

        pane.setPrefWidth(640);
        pane.setPrefHeight(640);
        stage.setResizable(false);

    }


    /**
     * Réceptionne et route l'event vers le bon display
     */
    private void eventsCatcher(){
        scene.setOnKeyPressed(event -> {
            if(display != null) {
                 display.traductEvent(event);
            }else {
                levelDisplay.traductEvent(event);
            }
        });
    }

    /**
     * Charge une nouveau display
     * @param windowType Type de windows à charger
     */
    public void loadNewDisplay(Displays windowType){

        if(levelDisplay != null){
            levelDisplay.stopAllAnimate();
        }

        levelDisplay = null;

        //Supprimer tous
        pane.setBackground(Background.EMPTY);
        pane.getChildren().clear();

        //On charge notre display
        display = DisplayLoader.load(windowType, this);

        //On charge les éléments dans la scène
        display.draw();

        System.gc();

    }

    /**
     * Charge un nouveau niveau
     * @param levels Niveau a chargé
     */
    public void loadNewLevelDisplay(Levels levels){


        if(levelDisplay != null){
            levelDisplay.stopAllAnimate();
        }

        display = null;

        //On créer une nouvelle stage et scène
        pane.setBackground(Background.EMPTY);
        pane.getChildren().clear();

        //On charge notre level
        levelDisplay = new LevelDisplayLoader(levels,this).load();

        //On charge les éléments dans la scène
        levelDisplay.draw();

        System.gc();

    }

    /*
        UTILS
     */

    /**
     * Affiche la fenêtre
     */
    public void show(){
        stage.show();
    }

    /**
     * Ajoute les nodes dans la scène
     * @param nodes Nodes à ajouter
     */
    public void addAllNode(Node... nodes){
            pane.getChildren().addAll(nodes);
    }

    /**
     * Permets d'ajouter des noeuds graphiques
     * @param nodes Noeuds graphiques à ajouter
     */
    public void removeAllNode(Node... nodes){
        pane.getChildren().removeAll(nodes);
    }

    /**
     * Joue les themes
     * @param themes Son a jouer
     */
    public void playTheme(Themes themes){

        try{

            if (themeClip != null){
                themeClip.stop();
            }
            themeClip = new AudioClip(getClass().getResource("/fr/manchez/snoopy/sounds/" + themes.getUrl()).toURI().toString());
            themeClip.play();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Joue les sons
     * @param sound Son a jouer
     */
    public void playSound(Sounds sound){

        try{

            soundClip = new AudioClip(getClass().getResource("/fr/manchez/snoopy/sounds/effects/" + sound.getUrl()).toURI().toString());
            soundClip.play();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /*
        GETTERS
     */
    public Scene getScene(){
        return scene;
    }
    public Pane getPane() {
        return pane;
    }
    public int getScale() {
        return SCALE;
    }
    public Display getDisplay() {
        return display;
    }
    public LevelDisplay getLevelDisplay() {
        return levelDisplay;
    }
    public Sauvegarde getSauvegarde() {
        return sauvegarde;
    }
    public PlayersType getPlayersType() {
        return playersType;
    }
    public void setPlayersType(PlayersType playersType) {
        this.playersType = playersType;
    }

}
