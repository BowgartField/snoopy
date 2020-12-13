package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Balle extends Structure{

    /**
     *
     */
    private boolean isBallStop = false;

    /**
     *
     */
    List<Structure> teleportersList = new ArrayList<>();

    /**
     * Time animation de la balle
     */
    Timeline timeline;

    /**
     * Fenetre snoopyWindow
     */
    SnoopyWindow window;

    /**
     * vecteur de déplacement de la balle
     */
    private int stepX = 1;
    private int stepY = 1;

    /**
     * Créer un object dans la fenêtre aux position x et y
     * @param window SnoopyWindow
     */
    public Balle(SnoopyWindow window) {

        super(new Point2D(50,150), Structures.BALLE);

        this.window = window;

        this.getTeleporters();

        /*
            DEBUG
         */

        //hitbox.setFill(Color.BLUE);
        //hitbox.setOpacity(0.5);
        //window.addAllNode(hitbox);

        timeline = new Timeline(new KeyFrame(Duration.millis(7), event -> {

                // si le niveau n'est pas en pause
                if(!window.getLevelDisplay().isPause() || !isBallStop){

                    xProperty.set(xProperty.get()+stepX);
                    yProperty.set(yProperty.get()+stepY);

                    //If the ball reaches the left or right border make the step negative
                    if(xProperty.get() <= 16*SnoopyWindow.SCALE || xProperty.get() >= 580){
                        stepX = -stepX;
                    }

                    //If the ball reaches the bottom or top border make the step negative
                    if(yProperty.get() <= 16*SnoopyWindow.SCALE || yProperty.get() >= 580){
                        stepY = -stepY;
                    }

                    //colision avec blocs
                    isColided();

                    //cloision avec personnage
                    isColidedWithPlayer();

                    //On vérifie si la balle est sur un teleporteur
                    isOnTeleport();

                }

            }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /**
     * Gére les colisions entre le personnage et la balle
     */
    private void isColidedWithPlayer() {

        Personnage personnage = window.getLevelDisplay().getPersonnage();

        if(hitbox.intersects(personnage.hitbox.getLayoutBounds())){

            personnage.setMoving(true);

            if(!personnage.isDefeating){

                //on enlève une vie
                personnage.setVie(personnage.getVie()-1);

                if(personnage.getVie() > 0){

                    //perte de vie
                    personnage.animateLooseLife();

                }else{

                    //defaite
                    personnage.animateDefeate();

                }

                personnage.setDefeating(true);

            }

        }

    }

    /**
     * Gére les colisions avec les blocs
     */
    private void isColided(){

        //TODO: copier le tableau pour eviter ConccurentException

        for(Map.Entry<Rectangle,Structures> rectangle: window.getLevelDisplay().getColisionRectangle().entrySet()){

            if(rectangle.getValue().getSymbol().equals(Structures.OBSTACLE.getSymbol())
                    || rectangle.getValue().getSymbol().equals(Structures.DESTRUCTIBLE.getSymbol())
                    ||rectangle.getValue().getSymbol().equals(Structures.DISPARITION_ENTIER.getSymbol())){

                if(rectangle.getKey().intersects(hitbox.getLayoutBounds())){

                    Bounds blocBounds = rectangle.getKey().getBoundsInLocal();
                    Bounds balleBounds = hitbox.getBoundsInLocal();

                    //orienté vers le haut gauche
                    if(stepY < 0 && stepX < 0){

                        //face droite et bas
                        if(balleBounds.getMinY() == blocBounds.getMaxY() && balleBounds.getMinX() == blocBounds.getMaxX()){
                            stepX = -stepX;
                            stepY = -stepY;
                            break;
                        }else{

                            //tape le bas du bloc
                            if(balleBounds.getMinY() == blocBounds.getMaxY()){
                                stepY = -stepY;
                                break;
                            }

                            //tape la droite du bloc
                            if(balleBounds.getMinX() == blocBounds.getMaxX()){
                                stepX = -stepX;
                                break;
                            }

                        }

                    }

                    //orienté vers le haut droite
                    if(stepY < 0 && stepX > 0){

                        //face gauche ou bas

                        if(balleBounds.getMaxX() == blocBounds.getMinX() && balleBounds.getMinY() == blocBounds.getMaxY()){
                            stepX = -stepX;
                            stepY = -stepY;
                            break;
                        }else{

                            //tape le bas du bloc
                            if(balleBounds.getMinY() == blocBounds.getMaxY()){
                                stepY = -stepY;
                                break;
                            }

                            //tape la gauche du bloc
                            if(balleBounds.getMaxX() == blocBounds.getMinX()){
                                stepX = -stepX;
                                break;
                            }
                        }

                    }

                    //orienté vers bas droit
                    if(stepY > 0 && stepX > 0){

                        //face gauche et haut
                        if(balleBounds.getMaxX() == blocBounds.getMinX() && balleBounds.getMaxY() == blocBounds.getMinY()){

                            stepX = -stepX;
                            stepY = -stepY;
                            break;

                        }else{

                            //tape la gauche du bloc
                            if(balleBounds.getMaxX() == blocBounds.getMinX()){
                                stepX = -stepX;
                                break;
                            }

                            //tape le haut du bloc
                            if(balleBounds.getMaxY() == blocBounds.getMinY()){
                                stepY = -stepY;
                                break;

                            }

                        }


                    }

                    //orienté vers le bas gauche
                    if(stepY > 0 && stepX < 0){

                        //face droite et haut

                        if(balleBounds.getMaxY() == blocBounds.getMinY() && balleBounds.getMinX() == blocBounds.getMaxX()){
                            stepX = -stepX;
                            stepY = -stepY;
                            break;
                        }else{

                            //tape le haut du bloc
                            if(balleBounds.getMaxY() == blocBounds.getMinY()){

                                stepY = -stepY;
                                break;

                            }

                            //tape la droite du bloc
                            if(balleBounds.getMinX() == blocBounds.getMaxX()){

                                stepX = -stepX;
                                break;

                            }

                        }

                    }

                }

            }

        }

        window.getLevelDisplay().removeColision();

    }

    /**
     * Vérifie si la balle est sur un teleporteur
     */
    private void isOnTeleport(){

        for(Structure structure: teleportersList){

            if(structure.getHitbox().intersects(getHitbox().getBoundsInLocal())){

                isBallStop = true;

                getPairTeleporter(structure);

            }
        }

    }

    /**
     * Récupére les téléporters du niveau
     */
    private void getTeleporters(){

        for(List<Structure> structureList: window.getLevelDisplay().getLevelStruture()){

            for(Structure structure: structureList){


                if(structure.getStructure().equals(Structures.TP1) ||structure.getStructure().equals(Structures.TP2)){

                    teleportersList.add(structure);

                }

            }

        }

    }

    /**
     *
     */
    private void getPairTeleporter(Structure teleporter){

        for(Structure structure: teleportersList){

            if(structure.getStructure().equals(teleporter.getStructure()) && !structure.equals(teleporter)){



            }

        }

    }

    /**
     *
     */
    public void stopAnimate(){
        timeline.stop();
    }

}
