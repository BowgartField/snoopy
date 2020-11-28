package fr.manchez.snoopy.application.models.levels;

import fr.manchez.snoopy.application.SnoopyWindow;
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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.InputStream;
import java.security.Key;
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

    /** Level actuel */
    Levels level;

    /** Image de pause */
    ImageView pauseBackground = new ImageView();

    /** Image "pause" */
    ImageView pauseDisplay = new ImageView();

    /**  Le niveau est en pause */
    boolean isPause = false;

    /** Timer **/
    Timer timer;

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

        initPause();

    }

    /**
     * Initialise les éléments relatifs au menu pause
     */
    private void initPause() {

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
     * Affiche le menu de victoire
     */
    public void victory(){

        //Animation de victoire
        //Affichage du score
        //Passage au niveau suivant

        System.out.println("victoire !!");

    }

    /**
     * Déclencher lors de la défaite
     */
    public void defeate(){

        //mettre le level en pause
        //gerer les touches
        //apeller initEndLevelScreen

        System.out.println("Défaite !!");

    }

    /**
     *
     */
    public void looseLife(){

        //perte de vie

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
                        Alert.AlertType.INFORMATION,
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
            if(keyCode.equals(KeyCode.P)){ showPause(); }

        }

    }

    /**
     * Met en Pause le jeu
     */
    public void showPause(){

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
     * @return
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
