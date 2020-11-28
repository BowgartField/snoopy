package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Sounds;
import fr.manchez.snoopy.application.enums.Structures;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

public class Personnage extends Structure {

    /**
     * Time d'animation
     *
     * L'animation de perte de vie et de défaite est la même
     * */
    Timeline timeline;

    /** Vie du personnage **/
    private int vie;

    /** Vie par défaut */
    public static int DEFAULT_VIE = 5;

    /** **/
    boolean isDefeating = false;

    /**
     * Window
     */
    SnoopyWindow window;

    /** Le permet est en déplacement */
    private boolean isMoving = false;

    /** Nombre de déplacement sur l'axe X et Y, permet de vérifier que snoopy ne sorte pas des cases */
    private int moveToX = 4;
    private int moveToY = 4;

    /** Nombre de pixel de déplacement par frame, ici 5 frames */
    final double mov = 6.4; //->32/5

    /**
     * Créer un Personnage dans la fenêtre aux position x et y
     */
    public Personnage(Point2D point2D, Structures structure, SnoopyWindow window) {

        super(point2D, structure);

        vie = window.getSauvegarde().getPlayer().getVie();

        this.window = window;

        //Initialise les animations
        initAnimations();

    }

    /**
     * Déplace le personnage vers la gauche
     */
    public void moveLeft(){

        Rectangle nextPlayerPosition = new Rectangle(
                xProperty.get()-mov*5*SnoopyWindow.SCALE,
                yProperty.get(),
                (structure.getWidth()-2)*SnoopyWindow.SCALE,
                (structure.getHeight()-2)*SnoopyWindow.SCALE
        );

        if(moveToX > 0 && !isColided(nextPlayerPosition)){

            moveToX--;

            final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    isMoving = true;

                    getImageView().setImage(getImage(Structures.SNOOPY_LEFT_1));
                    xProperty.set(xProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_LEFT_2));
                    xProperty.set(xProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_LEFT_3));
                    xProperty.set(xProperty.doubleValue()-mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_LEFT_1));
                    xProperty.set(xProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_LEFT_2));
                    xProperty.set(xProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                    Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);
                    actionWhenMove(nextStructure);

                    isMoving = false;

                }
            });

            Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5);
            timeline.playFromStart();

        }

    }

    /**
     * Déplace le personnage vers la droite
     */
    public void moveRight(){

        Rectangle nextPlayerPosition = new Rectangle(
                xProperty.get()+mov*5*SnoopyWindow.SCALE,
                yProperty.get(),
                (structure.getWidth()-2)*SnoopyWindow.SCALE,
                (structure.getHeight()-2)*SnoopyWindow.SCALE
        );

        if(moveToX < 8 && !isColided(nextPlayerPosition)){

            moveToX++;

            final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    isMoving = true;

                    getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_1));
                    xProperty.set(xProperty.doubleValue()+mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_2));
                    xProperty.set(xProperty.doubleValue()+mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_3));
                    xProperty.set(xProperty.doubleValue()+mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_1));
                    xProperty.set(xProperty.doubleValue()+mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_2));
                    xProperty.set(xProperty.doubleValue()+mov*SnoopyWindow.SCALE);

                    Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);
                    actionWhenMove(nextStructure);

                    isMoving = false;
                }
            });

            Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5);
            timeline.playFromStart();

        }
    }

    /**
     * Déplace le personnage vers le haut
     */
    public void moveUp(){

        Rectangle nextPlayerPosition = new Rectangle(
                xProperty.get(),
                yProperty.get()-mov*5*SnoopyWindow.SCALE,
                (structure.getWidth()-2)*SnoopyWindow.SCALE,
                (structure.getHeight()-2)*SnoopyWindow.SCALE
        );

        if(moveToY > 0 && !isColided(nextPlayerPosition)){

            moveToY--;

            final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    isMoving = true;

                    getImageView().setImage(getImage(Structures.SNOOPY_UP_1));
                    yProperty.set(yProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_UP_2));
                    yProperty.set(yProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_UP_1));
                    yProperty.set(yProperty.doubleValue()-mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_UP_2));
                    yProperty.set(yProperty.doubleValue()-mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_UP_1));
                    yProperty.set(yProperty.doubleValue()-mov*SnoopyWindow.SCALE);

                    Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);
                    actionWhenMove(nextStructure);

                    isMoving = false;
                }
            });

            Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5);
            timeline.playFromStart();


        }

    }

    /**
     * Déplace le personnage vers la bas
     */
    public void moveDown(){

        Rectangle nextPlayerPosition = new Rectangle(
                xProperty.get(),
                yProperty.get()+mov*5*SnoopyWindow.SCALE,
                (structure.getWidth()-2)*SnoopyWindow.SCALE,
                (structure.getHeight()-2)*SnoopyWindow.SCALE
        );

        if(moveToY < 8 && !isColided(nextPlayerPosition)){

            moveToY++;

            final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    isMoving = true;

                    getImageView().setImage(getImage(Structures.SNOOPY_DOWN_1));
                    yProperty.set(yProperty.doubleValue()+mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_DOWN_2));
                    yProperty.set(yProperty.doubleValue()+mov*SnoopyWindow.SCALE);

                }
            });
            final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_DOWN_1));
                    yProperty.set(yProperty.doubleValue()+mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_DOWN_2));
                    yProperty.set(yProperty.doubleValue()+mov*SnoopyWindow.SCALE);
                }
            });
            final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    getImageView().setImage(getImage(Structures.SNOOPY_DOWN_1));
                    yProperty.set(yProperty.doubleValue()+mov*SnoopyWindow.SCALE);

                    Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);
                    actionWhenMove(nextStructure);

                    isMoving = false;
                }
            });

            Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5);
            timeline.playFromStart();

        }


    }

    /**
     * Action effectué à chaque déplacement du personnage
     * @param nextStructure Structure devant le personnage
     */
    public void actionWhenMove(Structure nextStructure){

        //si le bloc suivant est une destructible
        if(nextStructure.getStructure().getSymbol().equals(Structures.DESTRUCTIBLE.getSymbol())){

            nextStructure.imageView.setImage(nextStructure.getImage(Structures.DEBRIS));
            window.getLevelDisplay().setColisionToRemove(nextStructure.hitbox);

            window.playSound(Sounds.BLOC_BREAK);

        }

        //si le bloc suivant est un oiseau
        if(nextStructure.getStructure().getSymbol().equals(Structures.BIRD.getSymbol())){

           window.removeAllNode(nextStructure.imageView);
           window.getLevelDisplay().animateGetBird(nextStructure);

           //Remplace l'oiseau par du vide
            List<Structure> structureList = window.getLevelDisplay().getLevelStruture().get(moveToY);

            Structure empty = new Structure(new Point2D(
                    nextStructure.getImageView().getX(),
                    nextStructure.getImageView().getY()
            ),Structures.EMPTY);

            structureList.remove(moveToX);
            structureList.add(moveToX,empty);

        }

    }

    /**
     * Vérifie si le personnage est déjà en cours de déplacement
     * @return
     */
    public boolean isMoving(){
        return isMoving;
    }

    /*
        Animation
     */

    /**
     * Initialise les animations
     */
    private void initAnimations(){

        //Tourne sur lui même puis est désamparé puis éclate
        final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_1));

                isMoving = true;

            }
        });
        final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_UP_1));

            }
        });
        final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_LEFT_1));

            }
        });
        final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(900), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));


            }
        });
        final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(1200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_DESAMP));

            }
        });
        final KeyFrame keyFrame6 = new KeyFrame(Duration.millis(1800), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_MORT));

                isMoving = false;

            }
        });

        timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6);

    }

    /**
     * Animation à la perte de vie
     */
    public void animateLooseLife(){

        //On sauvegarde la vie
        window.getSauvegarde().getPlayer().setVie(vie);

        isMoving = true;
        timeline.playFromStart();

        //TODO: changer la cible
        timeline.setOnFinished(even -> window.getLevelDisplay().looseLife());

    }

    /**
     * Animation de la défaite
     */
    public void animateDefeate(){

        isMoving = true;

        timeline.playFromStart();

        //TODO: changer la cible
        timeline.setOnFinished(even -> window.getLevelDisplay().defeat());

    }

    /**
     * Animation de victoire
     */
    public void animateVictory(){

        //snoopy qui saute
        final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_HEUREUX));
                yProperty.set(yProperty.get()-16*SnoopyWindow.SCALE);
                isMoving = true;

            }
        });
        final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));
                yProperty.set(yProperty.get()+16*SnoopyWindow.SCALE);

            }
        });
        final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_HEUREUX));
                yProperty.set(yProperty.get()-16*SnoopyWindow.SCALE);
                isMoving = true;

            }
        });
        final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(900), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));
                yProperty.set(yProperty.get()+16*SnoopyWindow.SCALE);

            }
        });
        final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(1200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_HEUREUX));
                yProperty.set(yProperty.get()-16*SnoopyWindow.SCALE);
                isMoving = true;

            }
        });
        final KeyFrame keyFrame6 = new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));
                yProperty.set(yProperty.get()+16*SnoopyWindow.SCALE);

            }
        });

        Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6);
        timeline.playFromStart();

        //On lance l'écran de victoire
        timeline.setOnFinished(event -> window.getLevelDisplay().victory());

    }

    /*
        COLISIONS
     */

    /**
     * Contrôle si le personnage est en colision avec un bloc du décor
     */
    public boolean isColided(Rectangle playerRectangle){

        boolean result = false;

        for(Map.Entry<Rectangle,Structures> struc: window.getLevelDisplay().getColisionRectangle().entrySet()){

            if(!struc.getValue().getSymbol().equals(Structures.DESTRUCTIBLE.getSymbol())){

                if(struc.getKey().intersects(playerRectangle.getLayoutBounds())){
                    result = true;
                    break;
                }

            }

        }

        return result;

    }


    /*
        GETTERS AND SETTERS
     */

    public int getVie() {
        return vie;
    }
    public void setVie(int vie) {
        this.vie = vie;
    }

    public boolean isDefeating() {
        return isDefeating;
    }
    public void setDefeating(boolean defeating) {
        isDefeating = defeating;
    }
}
