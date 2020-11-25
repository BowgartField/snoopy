package fr.manchez.snoopy.application.models.levels;

import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Balle;
import fr.manchez.snoopy.application.models.objects.Personnage;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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


    public LevelDisplay(SnoopyWindow window){

        this.window = window;

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

        //On affiche le décor dans la fenêtre
        for(Map.Entry<Rectangle,Structures> test: getColisionRectangle().entrySet()){

            Rectangle rec = test.getKey();
            rec.setFill(Color.BROWN);
            rec.setOpacity(0.3);

            window.addAllNode(rec);

        }

        window.getPane().getChildren().add(snoopy.getHitbox());

        //On affiche le personnage
        window.getPane().getChildren().add(snoopy.getImageView());

        //On affiche la balle
        balle = new Balle(window);
        window.getPane().getChildren().add(balle.getImageView());

    }

    /**
     *
     */
    public void animateGetBird(Structure bird){

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

            //Animation de victoire
            //Affichage du score
            //Passage au niveau suivant

            System.out.println("victoire !");

        }

    }

    /**
     *
     */
    public void getColisionRectangle(Rectangle rectangle){

        //colisionRectangle.s

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
}
