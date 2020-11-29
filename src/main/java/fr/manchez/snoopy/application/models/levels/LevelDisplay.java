package fr.manchez.snoopy.application.models.levels;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.enums.Sounds;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.Timer;
import fr.manchez.snoopy.application.models.objects.Balle;
import fr.manchez.snoopy.application.models.objects.Personnage;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Permet de gére un niveau avec:
 * - des colisions
 * - les emplacements des structures
 */
public class LevelDisplay {

    Levels level;

    /** Image de pause */
    ImageView pauseBackground = new ImageView();

    /** Image "pause" */
    ImageView pauseDisplay = new ImageView();

    /** Image de fin de niveau*/
    ImageView levelEndBackground = new ImageView();

    /** Image "Stage XXX clear" ou "stage XXX"*/
    ImageView stageInfo = new ImageView();

    /** Image du curseur*/
    ImageView cursor = new ImageView();

    /** Structure des vies */
    Structure vieUnite;
    Structure vieDixaine;

    /** Structure du numéro de niveau*/
    Structure levelNbUnite;
    Structure levelNbDixaine;
    Structure levelNbCentaine;

    /**Stock les structures des scores*/
    List<Structure> scoreStructures = new ArrayList<>();
    List<Structure> highscoreStructures = new ArrayList<>();

    /**  Le niveau est en pause*/
    boolean isPause = false;

    /** Une vie est perdue*/
    boolean isLooseLife = false;

    /** La partie est perdue*/
    boolean isLoose = false;

    /** Le niveau est gagné*/
    boolean isWin = false;

    /** La ligne 1 de l'écran de défaite est sélectionnée */
    boolean isOption1 = true;

    /** Timer **/
    Timer timer;

    /** timeline de l'affichage du score*/
    Timeline scoreTimeline;
    /**
     * window
     */
    SnoopyWindow window;

    /**
     * Oiseaux restants
     */
    private int birdsRemaining = 0;

    /**
     *
     */
    private HashMap<Rectangle,Structures> colisionRectangle = new HashMap<>();

    /**
     *
     */
    private List<Rectangle> colisionToRemove = new ArrayList<>();

    /**
     * Contient la structure du Niveau
     */
    private List<List<Structure>> levelStruture = new ArrayList<>();

    /**
     * Snoopy
     */
    private Personnage snoopy;

    /**
     * La balle
     */
    private Balle balle;


    public LevelDisplay(SnoopyWindow window, Levels level){

        this.window = window;
        this.level = level;
        timer = new Timer(window);

        showPause();

    }

    /**
     * Initialise les éléments relatifs au menu pause
     */
    private void showPause() {

        //On récupére l'image correspondant à la structure
        InputStream pauseBgIS = getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/pausebg.png");

        pauseBackground.setImage(
                new Image(
                        pauseBgIS,
                        320*SnoopyWindow.SCALE,
                        320*SnoopyWindow.SCALE,
                        false,
                        true)
        );

        pauseBackground.setOpacity(0.6);

        //On récupére l'image du pause
        InputStream pauseIS = getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Details/pause.png");

        pauseDisplay = new ImageView(
                new Image(
                        pauseIS,
                        Structures.PAUSE.getWidth()*SnoopyWindow.SCALE,
                        Structures.PAUSE.getHeight()*SnoopyWindow.SCALE
                        ,false
                        ,true
                )
        );

        pauseDisplay.setY((window.getPane().getHeight()-Structures.PAUSE.getHeight()*SnoopyWindow.SCALE)/2);
        pauseDisplay.setX((window.getPane().getWidth()-Structures.PAUSE.getWidth()*SnoopyWindow.SCALE)/2);

    }

    /**
     * Initialise les éléments relatifs à l'écran de perte de vie
     */
    private void showLooseLifeScreen(){
        levelEndBackground.setImage(
                new Image(
                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.LevelEndDisplay.getBackgroundURL()),
                        290*SnoopyWindow.SCALE,
                        290*SnoopyWindow.SCALE,
                        false,
                        true)
        );
        levelEndBackground.setY(15*SnoopyWindow.SCALE);
        levelEndBackground.setX(15*SnoopyWindow.SCALE);


        stageInfo = new ImageView(
                new Image(
                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/"+ Structures.STAGE_LOOSE.getFileURL()),
                        Structures.STAGE_LOOSE.getWidth()*SnoopyWindow.SCALE,
                        Structures.STAGE_LOOSE.getHeight()*SnoopyWindow.SCALE,
                        false,
                        true
                )
        );
        stageInfo.setY(32*SnoopyWindow.SCALE);
        stageInfo.setX((window.getPane().getWidth()-Structures.STAGE_LOOSE.getWidth()*SnoopyWindow.SCALE)/2);



        window.addAllNode(
                levelEndBackground,
                stageInfo
        );

        displayLifes();
        displayActualLevel();
        displayAnimatedBalls();
        initScore(window.getSauvegarde().getPlayer().getScore(), window.getSauvegarde().getPlayer().getHighscore());
    }

    /**
     * Initialise les éléments relatifs à l'écran de gain de niveau
     */
    private void showWinScreen(){
        levelEndBackground.setImage(
                new Image(
                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.LevelEndDisplay.getBackgroundURL()),
                        290*SnoopyWindow.SCALE,
                        290*SnoopyWindow.SCALE,
                        false,
                        true)
        );
        levelEndBackground.setY(15*SnoopyWindow.SCALE);
        levelEndBackground.setX(15*SnoopyWindow.SCALE);

        stageInfo = new ImageView(
                new Image(
                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/"+ Structures.STAGE_CLEAR.getFileURL()),
                        Structures.STAGE_CLEAR.getWidth()*SnoopyWindow.SCALE,
                        Structures.STAGE_CLEAR.getHeight()*SnoopyWindow.SCALE,
                        false,
                        true
                )
        );
        stageInfo.setY(32*SnoopyWindow.SCALE);
        stageInfo.setX((window.getPane().getWidth()-Structures.STAGE_CLEAR.getWidth()*SnoopyWindow.SCALE)/2);


        window.addAllNode(
                levelEndBackground,
                stageInfo
        );


        displayLifes();
        displayActualLevel();
        displayAnimatedBalls();
        displayAnimatedScore();
    }

    /** Affiche les éléments relatifs à la défaite*/
    private void showLooseScreen(){

        levelEndBackground.setImage(
                new Image(
                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.LooseDisplay.getBackgroundURL()),
                        290*SnoopyWindow.SCALE,
                        290*SnoopyWindow.SCALE,
                        false,
                        true)
        );
        levelEndBackground.setY(15*SnoopyWindow.SCALE);
        levelEndBackground.setX(15*SnoopyWindow.SCALE);


        cursor = new ImageView(
                new Image(
                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/"+ Structures.CURSEUR.getFileURL()),
                        Structures.CURSEUR.getWidth()*SnoopyWindow.SCALE,
                        Structures.CURSEUR.getHeight()*SnoopyWindow.SCALE,
                        false,
                        true
                )
        );

        cursor.setY(179*SnoopyWindow.SCALE+levelEndBackground.getY());
        cursor.setX(68*SnoopyWindow.SCALE+levelEndBackground.getX());

        window.addAllNode(
                levelEndBackground,
                cursor
        );
        displayActualPassword();
        initScore(window.getSauvegarde().getPlayer().getScore(), window.getSauvegarde().getPlayer().getHighscore());
    }


    /**
     * Déclencher lors de la victoire
     */
    public void victory(){

        //Animation de victoire
        //Affichage du score
        //Passage au niveau suivant

        System.out.println("victoire !!");

        //Il a compléter le level actuel
        window.getSauvegarde().getPlayer().setLevel(Levels.findLevelsFromLevelNumber(level.getLevelNumber()+1));

        isWin = true;
        isPause = true;

        showWinScreen();

    }

    /**
     * Déclencher lors de la défaite
     */
    public void defeat(){

        System.out.println("Défaite !!");

        isLoose = true;
        isPause = true;

        showLooseScreen();

    }

    /**
     * Déclencher lors de la perte de vie
     */
    public void looseLife(){

        System.out.println("perte de vie !!");

        isLooseLife = true;
        isPause = true;

        showLooseLifeScreen();

    }

    /**
     * Gère l'affichage du nombre de vies restantes
     */
    private void displayLifes(){

        int vie = getPersonnage().getVie();

        if(vie < 9){
            vieDixaine = new Structure(
                    new Point2D(162*SnoopyWindow.SCALE + levelEndBackground.getX(), 222*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            vieUnite = new Structure(
                    new Point2D(178* SnoopyWindow.SCALE + levelEndBackground.getX(), 222*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(vie).charAt(0))
            );

        }else{
            vieDixaine = new Structure(
                    new Point2D(162* SnoopyWindow.SCALE + levelEndBackground.getX(), 222*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(vie).charAt(0))
            );
            vieUnite = new Structure(
                    new Point2D(178* SnoopyWindow.SCALE + levelEndBackground.getX(), 222*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(vie).charAt(1))
            );
        }


        window.addAllNode(
                vieDixaine.getImageView(),
                vieUnite.getImageView()
        );

    }

    /**
     * Gère l'affichage du niveau en cours
     */
    private void displayActualLevel(){

        int levelNumber = level.getLevelNumber();

        /**Gère les coordonnées de placement de "levelNumber"*/
        int x = Structures.STAGE_CLEAR.getWidth()/2;
        int y = 18;
        if(isLooseLife){
            x = 170;
        }


        if(levelNumber < 10){
            levelNbCentaine = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            levelNbDixaine = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            levelNbUnite = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(levelNumber).charAt(0))
            );
        }else if(levelNumber < 100){
            levelNbCentaine = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            levelNbDixaine = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(levelNumber).charAt(1))
            );
            levelNbUnite = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(levelNumber).charAt(0))
            );
        }else {
            levelNbCentaine = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(levelNumber).charAt(2))
            );
            levelNbDixaine = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(levelNumber).charAt(1))
            );
            levelNbUnite = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(levelNumber).charAt(0))
            );
        }

        window.addAllNode(
                levelNbUnite.getImageView(),
                levelNbDixaine.getImageView(),
                levelNbCentaine.getImageView()
        );

    }

    /**
     * Gère l'affichage du mot de passe du niveau
     */
    private void displayActualPassword(){
        /**Structures des numéros du mot de passe*/
        Structure intPass1;
        Structure intPass2;
        Structure intPass3;
        Structure intPass4;

        int password = Integer.parseInt(level.getPassword());
        int y = 258;
        int x = 194;

        if(password < 10){
            intPass1 = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            intPass2 = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            intPass3 = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );

            intPass4 = new Structure(
                    new Point2D((x+46)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(0))
            );
        }else if (password < 100){
            intPass1 = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            intPass2 = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            intPass3 = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(0))
            );

            intPass4 = new Structure(
                    new Point2D((x+46)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(1))
            );
        }else if (password < 1000){
            intPass1 = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.ZERO
            );
            intPass2 = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(0))
            );
            intPass3 = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(1))
            );

            intPass4 = new Structure(
                    new Point2D((x+46)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(2))
            );
        }else{
            intPass1 = new Structure(
                    new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(0))
            );
            intPass2 = new Structure(
                    new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(1))
            );
            intPass3 = new Structure(
                    new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(2))
            );

            intPass4 = new Structure(
                    new Point2D((x+46)* SnoopyWindow.SCALE + levelEndBackground.getX(), y*SnoopyWindow.SCALE + levelEndBackground.getY()),
                    Structures.getStructuresFromSymbol(String.valueOf(password).charAt(3))
            );
        }



        window.addAllNode(
                intPass1.getImageView(),
                intPass2.getImageView(),
                intPass3.getImageView(),
                intPass4.getImageView()
        );


    }

    /**
     * Gère l'affichage des boules animées sur les écrans de fin
     */
    private void displayAnimatedBalls(){

        int deplacement = 50*SnoopyWindow.SCALE;
        Duration duration = Duration.millis(700);

        Structure boule1 = new Structure(new Point2D(30*SnoopyWindow.SCALE, 250*SnoopyWindow.SCALE), Structures.BALL_STATE1);
        Structure boule2 = new Structure(new Point2D(260*SnoopyWindow.SCALE, 250*SnoopyWindow.SCALE), Structures.BALL_STATE1);
        Structure ombre1 = new Structure(new Point2D(30*SnoopyWindow.SCALE, 266*SnoopyWindow.SCALE), Structures.SHADOW_STATE1);
        Structure ombre2 = new Structure(new Point2D(260*SnoopyWindow.SCALE, 266*SnoopyWindow.SCALE), Structures.SHADOW_STATE1);


        window.addAllNode(
                boule1.getImageView(),
                boule2.getImageView(),
                ombre1.getImageView(),
                ombre2.getImageView()
        );


        Timeline timeline = new Timeline();

        for(int i = 0; i < deplacement*6; i++){

            if(i <= deplacement){

                final KeyFrame keyFrame = new KeyFrame(Duration.millis(i), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        boule1.getImageView().setY(boule1.getImageView().getY()-1);
                        boule2.getImageView().setY(boule2.getImageView().getY()-1);

                        boule1.setImage(Structures.BALL_STATE1);
                        boule2.setImage(Structures.BALL_STATE1);
                        ombre1.setImage(Structures.SHADOW_STATE1);
                        ombre2.setImage(Structures.SHADOW_STATE1);
                        boule1.getImageView().toFront();
                        boule2.getImageView().toFront();

                    }
                });

                timeline.getKeyFrames().add(keyFrame);

            }else if(i < deplacement*2){

                final KeyFrame keyFrame = new KeyFrame(Duration.millis(i), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        boule1.getImageView().setY(boule1.getImageView().getY()-1);
                        boule2.getImageView().setY(boule2.getImageView().getY()-1);

                        boule1.setImage(Structures.BALL_STATE2);
                        boule2.setImage(Structures.BALL_STATE2);
                        ombre1.setImage(Structures.SHADOW_STATE2);
                        ombre2.setImage(Structures.SHADOW_STATE2);
                        boule1.getImageView().toFront();
                        boule2.getImageView().toFront();

                    }
                });

                timeline.getKeyFrames().add(keyFrame);


            }else if(i < deplacement*3){

                final KeyFrame keyFrame = new KeyFrame(Duration.millis(i), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        boule1.getImageView().setY(boule1.getImageView().getY()-1);
                        boule2.getImageView().setY(boule2.getImageView().getY()-1);

                        boule1.setImage(Structures.BALL_STATE3);
                        boule2.setImage(Structures.BALL_STATE3);
                        ombre1.setImage(Structures.SHADOW_STATE3);
                        ombre2.setImage(Structures.SHADOW_STATE3);
                        boule1.getImageView().toFront();
                        boule2.getImageView().toFront();

                    }
                });

                timeline.getKeyFrames().add(keyFrame);

            }else if(i < deplacement*4){

                final KeyFrame keyFrame = new KeyFrame(Duration.millis(i), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        boule1.getImageView().setY(boule1.getImageView().getY()+1);
                        boule2.getImageView().setY(boule2.getImageView().getY()+1);
                        boule1.getImageView().toFront();
                        boule2.getImageView().toFront();

                    }
                });

                timeline.getKeyFrames().add(keyFrame);

            }else if(i < deplacement*5){

                final KeyFrame keyFrame = new KeyFrame(Duration.millis(i), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        boule1.getImageView().setY(boule1.getImageView().getY()+1);
                        boule2.getImageView().setY(boule2.getImageView().getY()+1);

                        boule1.setImage(Structures.BALL_STATE2);
                        boule2.setImage(Structures.BALL_STATE2);
                        ombre1.setImage(Structures.SHADOW_STATE2);
                        ombre2.setImage(Structures.SHADOW_STATE2);
                        boule1.getImageView().toFront();
                        boule2.getImageView().toFront();

                    }
                });

                timeline.getKeyFrames().add(keyFrame);

            }else{

                final KeyFrame keyFrame = new KeyFrame(Duration.millis(i), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        boule1.getImageView().setY(boule1.getImageView().getY()+1);
                        boule2.getImageView().setY(boule2.getImageView().getY()+1);

                        boule1.setImage(Structures.BALL_STATE1);
                        boule2.setImage(Structures.BALL_STATE1);
                        ombre1.setImage(Structures.SHADOW_STATE1);
                        ombre2.setImage(Structures.SHADOW_STATE1);
                        boule1.getImageView().toFront();
                        boule2.getImageView().toFront();

                    }
                });

                timeline.getKeyFrames().add(keyFrame);

            }

        }

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setRate(0.45);
        timeline.playFromStart();


        /*
        //snoopy qui saute
        final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                final TranslateTransition translateAnimation1 = new TranslateTransition(
                        duration, boule1.getImageView());

                translateAnimation1.setByY(-deplacement);
                translateAnimation1.playFromStart();

                final TranslateTransition translateAnimation2 = new TranslateTransition(
                        duration, boule2.getImageView());

                translateAnimation2.setByY(-deplacement);
                translateAnimation2.playFromStart();
            }
        });
        final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(700), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                boule1.setImage(Structures.BALL_STATE2);
                boule2.setImage(Structures.BALL_STATE2);

                final TranslateTransition translateAnimation1 = new TranslateTransition(
                        duration, boule1.getImageView());

                translateAnimation1.setByY(-deplacement);
                translateAnimation1.playFromStart();

                final TranslateTransition translateAnimation2 = new TranslateTransition(
                        duration, boule2.getImageView());

                translateAnimation2.setByY(-deplacement);
                translateAnimation2.playFromStart();
            }
        });
        final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(1400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                boule1.setImage(Structures.BALL_STATE3);
                boule2.setImage(Structures.BALL_STATE3);

                final TranslateTransition translateAnimation1 = new TranslateTransition(
                        duration, boule1.getImageView());

                translateAnimation1.setByY(-deplacement);
                translateAnimation1.playFromStart();

                final TranslateTransition translateAnimation2 = new TranslateTransition(
                        duration, boule2.getImageView());

                translateAnimation2.setByY(-deplacement);
                translateAnimation2.playFromStart();
            }
        });
        final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(2100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                final TranslateTransition translateAnimation1 = new TranslateTransition(
                        duration, boule1.getImageView());

                translateAnimation1.setByY(deplacement);
                translateAnimation1.playFromStart();

                final TranslateTransition translateAnimation2 = new TranslateTransition(
                        duration, boule2.getImageView());

                translateAnimation2.setByY(deplacement);
                translateAnimation2.playFromStart();
            }
        });
        final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(2800), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boule1.setImage(Structures.BALL_STATE2);
                boule2.setImage(Structures.BALL_STATE2);

                final TranslateTransition translateAnimation1 = new TranslateTransition(
                        duration, boule1.getImageView());

                translateAnimation1.setByY(deplacement);
                translateAnimation1.playFromStart();
                final TranslateTransition translateAnimation2 = new TranslateTransition(
                        duration, boule2.getImageView());

                translateAnimation2.setByY(deplacement);
                translateAnimation2.playFromStart();
            }
        });
        final KeyFrame keyFrame6 = new KeyFrame(Duration.millis(3500), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    boule1.setImage(Structures.BALL_STATE1);
                    boule2.setImage(Structures.BALL_STATE1);

                    final TranslateTransition translateAnimation1 = new TranslateTransition(
                            duration, boule1.getImageView());

                    translateAnimation1.setByY(deplacement);
                    translateAnimation1.playFromStart();

                    final TranslateTransition translateAnimation2 = new TranslateTransition(
                            duration, boule2.getImageView());

                    translateAnimation2.setByY(deplacement);
                    translateAnimation2.playFromStart();
                }
        });
        final KeyFrame keyFrame7 = new KeyFrame(Duration.millis(4200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });

        Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6,keyFrame7);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setRate(2);
        timeline.playFromStart();

         */


    }

    /**
     * Affiche l'augmentation du score
     */
    private void displayAnimatedScore(){

        final int[] highscore = {window.getSauvegarde().getPlayer().getHighscore()};

        final int[] actualscore = {window.getSauvegarde().getPlayer().getScore()};
        int finalscore = actualscore[0] + timer.getTimerScore();

        initScore(actualscore[0], highscore[0]);

        scoreTimeline = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if(actualscore[0] <= finalscore){
                    changeScore(actualscore[0]);


                    if(actualscore[0] == highscore[0]){

                        changeHighcore(highscore[0]);
                        highscore[0] = highscore[0] +100;

                    }
                    actualscore[0] = actualscore[0]+100;
                }else{
                    scoreTimeline.stop();
                    window.getSauvegarde().getPlayer().setScore(actualscore[0]);
                    window.getSauvegarde().getPlayer().setHighscore(highscore[0]);
                    window.getSauvegarde().save();
                }

            }

        }));

        scoreTimeline.setCycleCount(Animation.INDEFINITE);
        scoreTimeline.play();

    }

    /**Affiche le score*/
    private void initScore(int score, int highscore){

        int x = 109;
        int highscoreY= 111;
        int scoreY = 154;

        if(isLoose){
            highscoreY = highscoreY-26;
            scoreY = scoreY-21;
        }

        String convertedScore = convertScore(score);
        String convertedHighscore = convertScore(highscore);

        Structure highscore1 = new Structure(
                new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(0))
        );
        Structure highscore2 = new Structure(
                new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(1))
        );
        Structure highscore3 = new Structure(
                new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(2))
        );
        Structure highscore4 = new Structure(
                new Point2D((x+48)* SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(3))
        );
        Structure highscore5 = new Structure(
                new Point2D((x+64)* SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(4))
        );
        Structure highscore6 = new Structure(
                new Point2D((x+80)* SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(5))
        );
        Structure highscore7 = new Structure(
                new Point2D((x+96)* SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(6))
        );
        Structure highscore8 = new Structure(
                new Point2D((x+112)* SnoopyWindow.SCALE + levelEndBackground.getX(), highscoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedHighscore.charAt(7))
        );

        highscoreStructures.add(highscore1);
        highscoreStructures.add(highscore2);
        highscoreStructures.add(highscore3);
        highscoreStructures.add(highscore4);
        highscoreStructures.add(highscore5);
        highscoreStructures.add(highscore6);
        highscoreStructures.add(highscore7);
        highscoreStructures.add(highscore8);


        Structure score1 = new Structure(
                new Point2D(x*SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(0))
        );
        Structure score2 = new Structure(
                new Point2D((x+16)*SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(1))
        );
        Structure score3 = new Structure(
                new Point2D((x+32)* SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(2))
        );
        Structure score4 = new Structure(
                new Point2D((x+48)* SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(3))
        );
        Structure score5 = new Structure(
                new Point2D((x+64)* SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(4))
        );
        Structure score6 = new Structure(
                new Point2D((x+80)* SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(5))
        );
        Structure score7 = new Structure(
                new Point2D((x+96)* SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(6))
        );
        Structure score8 = new Structure(
                new Point2D((x+112)* SnoopyWindow.SCALE + levelEndBackground.getX(), scoreY*SnoopyWindow.SCALE + levelEndBackground.getY()),
                Structures.getStructuresFromSymbol(convertedScore.charAt(7))
        );

        scoreStructures.add(score1);
        scoreStructures.add(score2);
        scoreStructures.add(score3);
        scoreStructures.add(score4);
        scoreStructures.add(score5);
        scoreStructures.add(score6);
        scoreStructures.add(score7);
        scoreStructures.add(score8);

        highscoreStructures.forEach(item -> window.addAllNode(item.getImageView()));
        scoreStructures.forEach(item -> window.addAllNode(item.getImageView()));

    }

    /** Change le score*/
    public void changeScore(int score){

        //TODO: ajouter bruitage
        String convertedScore = convertScore(score);

        scoreStructures.get(0).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(0)));
        scoreStructures.get(1).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(1)));
        scoreStructures.get(2).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(2)));
        scoreStructures.get(3).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(3)));
        scoreStructures.get(4).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(4)));
        scoreStructures.get(5).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(5)));
        scoreStructures.get(6).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(6)));
        scoreStructures.get(7).setImage(Structures.getStructuresFromSymbol(convertedScore.charAt(7)));

    }

    /** Change le highscore*/
    public void changeHighcore(int highScore){

        String convertedHighscore = convertScore(highScore);

        highscoreStructures.get(0).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(0)));
        highscoreStructures.get(1).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(1)));
        highscoreStructures.get(2).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(2)));
        highscoreStructures.get(3).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(3)));
        highscoreStructures.get(4).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(4)));
        highscoreStructures.get(5).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(5)));
        highscoreStructures.get(6).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(6)));
        highscoreStructures.get(7).setImage(Structures.getStructuresFromSymbol(convertedHighscore.charAt(7)));

    }

    public  String convertScore(int score){

        String convertedScore = "";

        if (score < 10){
            convertedScore = "0000000" + score;
        }else if(score < 100){
            convertedScore = "000000" + score;
        }else if(score < 1000){
            convertedScore = "00000" + score;
        }else if(score < 10000){
            convertedScore = "0000" + score;
        }else if(score < 100000){
            convertedScore = "000" + score;
        }else if(score < 1000000){
            convertedScore = "00" + score;
        }else if(score < 10000000){
            convertedScore = "0" + score;
        }else{
            convertedScore = String.valueOf(score);
        }

        return convertedScore;
    }
    /**
     * Ajoute une structure au niveau
     * @param structure structure
     */
    public void addStructure(List<Structure> structure){
        levelStruture.add(structure);
    }

    /**
     * Ajoute les colisions au niveau
     * @param rectangleColision Colision à ajouter
     */
    public void addColisionStructure(Structures structures, Rectangle rectangleColision){
        colisionRectangle.put(rectangleColision,structures);
    }

    /**
     * Ajoute snoopy
     */
    public void addSnoopy(Personnage snoopy){

        this.snoopy = snoopy;

    }

    /**
     * On affiche les structure du niveau à l'intérieur de la fenêtre
     * -> Les structures du niveau
     * -> Le personnage
     * -> La balle
     */
    public void draw(){

        //On affiche le décor dans la fenêtre
        for (List<Structure> structureList: levelStruture){

            for(Structure structure: structureList){

                if(!structure.getStructure().getSymbol().equals(Structures.SNOOPY_SPAWN_POINT.getSymbol())){

                    window.getPane().getChildren().add(structure.getImageView());

                }

            }

        }

        /* DEBUG
        //On affiche le décor dans la fenêtre
        for(Map.Entry<Rectangle,Structures> test: getColisionRectangle().entrySet()){
            Rectangle rec = test.getKey();
            rec.setFill(Color.BROWN);
            rec.setOpacity(0.3);
            window.addAllNode(rec);
        }
        //DEBUT Hitbox personnage
        //window.getPane().getChildren().add(snoopy.getHitbox());
         */

        //On affiche le personnage
        window.addAllNode(snoopy.getImageView());

        //On affiche la balle
        balle = new Balle(window);
        window.addAllNode(balle.getImageView());

    }

    /**
     * Animation déclenché au passage de Snoopy sur un l'oiseau
     */
    public void animateGetBird(Structure bird){

        window.playSound(Sounds.BIRD_CATCH);

        birdsRemaining--;

        Structure points = new Structure(
                new Point2D(
                        bird.getImageView().getX(),
                        bird.getImageView().getY()
                ),
                Structures.POINTS
        );

        window.addAllNode(points.getImageView());

        final TranslateTransition translateAnimation = new TranslateTransition(
                Duration.millis(700), points.getImageView());

        translateAnimation.setByY(-32*SnoopyWindow.SCALE);
        translateAnimation.playFromStart();

        translateAnimation.setOnFinished(event -> window.removeAllNode(points.getImageView()));

        if(birdsRemaining == 0){ // -> VICTOIRE

            window.getLevelDisplay().getPersonnage().animateVictory();

        }

    }

    /**
     * Ajoute des colisions à supprimer à la liste
     */
    public void setColisionToRemove(Rectangle rectangle){
        colisionToRemove.add(rectangle);
    }

    /**
     * Supprime les colisions
     */
    public void removeColision(){

        for (Rectangle rectangle: colisionToRemove){

            try{

                for (Map.Entry<Rectangle, Structures> colision: colisionRectangle.entrySet()){
                    if(colision.getKey().getX() == rectangle.getX() && colision.getKey().getY() == rectangle.getY()){
                        colisionRectangle.remove(colision.getKey());
                    }
                }

            } catch (Exception e) {
                System.out.println();
            }

        }

        colisionToRemove.clear();

    }

    /**
     * Ajoute un oiseau en plus
     */
    public void addBirds(){

        birdsRemaining++;

    }

    /**
     * Event
     * @param event Event arrivé
     */
    public void traductEvent(KeyEvent event){

        KeyCode keyCode = event.getCode();

        if(!isPause){

            if(!snoopy.isMoving()){

                if(keyCode.equals(KeyCode.LEFT)){
                    snoopy.moveLeft();
                }else if(keyCode.equals(KeyCode.RIGHT)){
                    snoopy.moveRight();
                }else if(keyCode.equals(KeyCode.UP)){
                    snoopy.moveUp();
                }else if(keyCode.equals(KeyCode.DOWN)){
                    snoopy.moveDown();
                }

            }

            //Si on appuye sur le "p" -> pause
            if(keyCode.equals(KeyCode.P)){ showPause(); }

            //Si on appuye sur le "s" -> on sauvegarde
            if(keyCode.equals(KeyCode.S)){

                isPause = true;

                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Etes-vous sûre de vouloir sauvegarder et quitter ?",
                        ButtonType.YES,
                        ButtonType.NO);
                alert.showAndWait();

                if(alert.getResult() == ButtonType.YES){

                    window.getSauvegarde().save();

                }else if(alert.getResult() == ButtonType.NO){

                    alert.close();

                    isPause = false;

                }

            }

        }else{

            //Si on appuye sur le "p" -> enleve la pause
            if(keyCode.equals(KeyCode.P)){ setPause(); }
            if(isLooseLife && keyCode.equals(KeyCode.ENTER)){

                window.loadNewLevelDisplay(level);

                isPause = false;
                isLooseLife = false;
            }
            if (isWin && keyCode.equals(KeyCode.ENTER)){

                //On est sur un écran de victoire + on a presser la touche "entrer"

                window.loadNewLevelDisplay(Levels.findLevelsFromLevelNumber(level.getLevelNumber()+1));

                isPause = false;
                isWin = false;
            }
            if(isLoose){
                if(keyCode.equals(KeyCode.UP) && isOption1){
                    cursor.setY(cursor.getY() + 34* SnoopyWindow.SCALE);
                    isOption1 = false;
                }else if(keyCode.equals(KeyCode.UP)){
                    cursor.setY(cursor.getY() + -34*SnoopyWindow.SCALE);
                    isOption1 = true;
                }else if(keyCode.equals(KeyCode.DOWN) && isOption1){
                    cursor.setY(cursor.getY() + 34* SnoopyWindow.SCALE);
                    isOption1 = false;
                }else if(keyCode.equals(KeyCode.DOWN)){
                    cursor.setY(cursor.getY() + -34*SnoopyWindow.SCALE);
                    isOption1 = true;
                }else if (keyCode.equals(KeyCode.ENTER) && isOption1){

                    window.loadNewDisplay(Displays.StartDisplay);
                    isPause = false;
                    isLoose = false;

                }else if (keyCode.equals(KeyCode.ENTER)){

                    window.loadNewLevelDisplay(Levels.LEVEL_1);
                    isPause = false;
                    isLoose = false;

                }
            }

        }

    }

    /**
     * Met en Pause le jeu
     */
    public void setPause(){

        if(!isPause){

            pauseBackground.toFront();
            pauseDisplay.toFront();
            window.addAllNode(pauseBackground,pauseDisplay);

            isPause = true;

        }else{

            //Enleve l'écran de pause
            window.removeAllNode(pauseBackground,pauseDisplay);
            isPause = false;

        }

    }

    /*
        GETTERS
     */

    /**
     *
     */
    public Personnage getPersonnage(){
        return snoopy;
    }

    /**
     *
     */
    public HashMap<Rectangle,Structures> getColisionRectangle(){
        return colisionRectangle;
    }

    /**
     *
     */
    public List<List<Structure>> getLevelStruture(){
        return levelStruture;
    }

    /**
     *
     * @return
     */
    public boolean isPause() {
        return isPause;
    }
}
