package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Map;

public class Balle extends Structure{

    SnoopyWindow window;

    private int stepX = 1;
    private int stepY = 1;

    /**
     * Créer un object dans la fenêtre aux position x et y
     */
    public Balle(SnoopyWindow window) {

        super(new Point2D(50,150), Structures.BALLE);

        this.window = window;


        /*
            DEBUG
         */

        hitbox.setFill(Color.BLUE);
        hitbox.setOpacity(0.5);
        window.addAllNode(hitbox);


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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

                isColided();

            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void isColided(){

        for(Map.Entry<Rectangle,Structures> rectangle: window.getLevelDisplay().getColisionRectangle().entrySet()){

            if(rectangle.getValue().getSymbol().equals(Structures.OBSTACLE.getSymbol()) || rectangle.getValue().getSymbol().equals(Structures.DESTRUCTIBLE.getSymbol())){

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
                                System.out.println("bas \n");
                                stepY = -stepY;
                                break;
                            }

                            //tape la droite du bloc
                            if(balleBounds.getMinX() == blocBounds.getMaxX()){
                                System.out.println("droite \n");
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
                                System.out.println("bas \n");
                                stepY = -stepY;
                                break;
                            }

                            //tape la gauche du bloc
                            if(balleBounds.getMaxX() == blocBounds.getMinX()){
                                System.out.println("gauche \n");
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
                                System.out.println("gauche \n");
                                stepX = -stepX;
                                break;
                            }

                            //tape le haut du bloc
                            if(balleBounds.getMaxY() == blocBounds.getMinY()){

                                System.out.println("haut \n");
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

                                System.out.println("haut \n");
                                stepY = -stepY;
                                break;

                            }

                            //tape la droite du bloc
                            if(balleBounds.getMinX() == blocBounds.getMaxX()){

                                System.out.println("droite \n");
                                stepX = -stepX;
                                break;

                            }

                        }

                    }

                }

            }

        }

    }

    /**
     *
     */
    public int chooseRandom(){

        //int nombreAleatoire = Min + (int)(Math.random() * ((Max - Min) + 1));
        int nombreAleatoire = 1 + (int)(Math.random() * ((2 - 1) + 1));
        return nombreAleatoire;

    }

}
