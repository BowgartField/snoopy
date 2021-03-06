package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.AI.Moves;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Sounds;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.levels.LevelDisplay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.naming.Binding;
import java.util.List;
import java.util.Map;

public class Personnage extends Structure {

    /**
     * Time d'animation
     *
     * L'animation de perte de vie et de défaite est la même
     * */
    Timeline timelineLooseLifeAndDefeat;

    /**
     * Timeline d'animation disparition
     */
    Timeline timelineTpDisapeare;

    /**
     * Timeline animation tp apparition
     */
    Timeline timelineTpApeare;

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

    //retrecissement de la hitbox
    final private int retrec = 5;

    /**
     * Créer un Personnage dans la fenêtre aux position x et y
     * @param point2D {Point2D} Coordonées d'apparition du personnage
     * @param structure Structure du personnage
     * @param window SnoopyWindow
     */
    public Personnage(Point2D point2D, Structures structure, SnoopyWindow window) {

        super(point2D, structure);

        vie = window.getSauvegarde().getPlayer().getVie();

        //On réécrit
        hitbox = new Rectangle(
                (structure.getWidth()-retrec)* SnoopyWindow.SCALE,
                (structure.getHeight()-retrec)*SnoopyWindow.SCALE);

        hitbox.setX(imageView.getX()+retrec);
        hitbox.setY(imageView.getY()+retrec);

        this.window = window;

        //Initialise les animations
        initLooseLifeAndDefeatAnimations();
        initTpDisapeareAnimation();
        initTpApeareAnimation();

    }

    @Override
    protected void bind() {

        xProperty.addListener(((observable, oldValue, newValue) -> {

            hitbox.setX(newValue.doubleValue()+retrec);

        }));

        yProperty.addListener(((observable, oldValue, newValue) -> {

            hitbox.setY(newValue.doubleValue()+retrec);

        }));

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

        if(moveToX > 0){
            Structure nextStructureBeforeMove = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX-1);
            actionBeforeMove(nextStructureBeforeMove, Moves.LEFT);
        }

        if(moveToX > 0 && !isColided(nextPlayerPosition)){

            moveToX--;
            Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);

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

        if(moveToX < 8){
            Structure nextStructureBeforeMove = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX+1);
            actionBeforeMove(nextStructureBeforeMove, Moves.RIGHT);
        }

        if(moveToX < 8 && !isColided(nextPlayerPosition)){

            moveToX++;
            Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);


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

        if(moveToY > 0){
            Structure nextStructureBeforeMove = window.getLevelDisplay().getLevelStruture().get(moveToY-1).get(moveToX);
            actionBeforeMove(nextStructureBeforeMove, Moves.UP);
        }

        if(moveToY > 0 && !isColided(nextPlayerPosition)){

            moveToY--;
            Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);
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

        if(moveToY < 8){
            Structure nextStructureBeforeMove = window.getLevelDisplay().getLevelStruture().get(moveToY+1).get(moveToX);
            actionBeforeMove(nextStructureBeforeMove, Moves.DOWN);
        }

        if(moveToY < 8 && !isColided(nextPlayerPosition)){

            moveToY++;
            Structure nextStructure = window.getLevelDisplay().getLevelStruture().get(moveToY).get(moveToX);

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

        //tapis roulant vers la droite
        if(nextStructure.getStructure().getSymbol().equals(Structures.TAPIS_DROIT.getSymbol())){

            moveRight();

        }

        //tapis roulant vers la gauche
        if(nextStructure.getStructure().getSymbol().equals(Structures.TAPIS_GAUCHE.getSymbol())){

            moveLeft();

        }

        //tapis roulant vers le haut
        if(nextStructure.getStructure().getSymbol().equals(Structures.TAPIS_HAUT.getSymbol())){

            moveUp();

        }

        //tapis roulant vers le bas
        if(nextStructure.getStructure().getSymbol().equals(Structures.TAPIS_BAS.getSymbol())){

            moveDown();

        }

        //tp1 (symbole Z)
        if(nextStructure.getStructure().getSymbol().equals(Structures.TP1.getSymbol())){


            //premier animation de disparition
            window.playSound(Sounds.TELEPORT);
            timelineTpDisapeare.playFromStart();

            //
            timelineTpDisapeare.setOnFinished(event -> {

                //tp le personnage sur l'autre téléporteur
                teleport(nextStructure);

                //deuxième animation d'apparition
                timelineTpApeare.playFromStart();

                //timelineTpDisapeare.stop();
                //timelineTpApeare.stop();

            });
        }

        //bloc piégé
        if(nextStructure.getStructure().getSymbol().equals(Structures.PIEGE.getSymbol())){

            animateDefeate();

        }

    }

    /**
     * Action à effectuer avant le déplacement du personnage
     * @param nextStructure Structure vers laquelle le personnage se dirige
     * @param direction Direction vers laquelle le personnage se dirige
     */
    public void actionBeforeMove(Structure nextStructure, Moves direction) {


        if(nextStructure.getStructure().getSymbol().equals(Structures.POUSSABLE.getSymbol())){
            push(direction, nextStructure);
        }

    }
    /**
     *
     * @param direction Direction vers laquelle le joueur se dirige
     * @param nextStructure Structure vers laquelle le joueur se dirige
     */
    private void push(Moves direction, Structure nextStructure){

        //Coordonées matricielles de l'endroit où déplacer le bloc
        System.out.println(nextStructure.getStructure().getSymbol());
        int x = getMoveToX();
        int y = getMoveToY();

        //Stock les coordonnées matricielles du bloc à déplacer
        int Y = getMoveToY();
        int X = getMoveToX();


        //Vérifie la direction du joueur
        switch (direction){
            case UP:
                Y = Y-1;
                y = y-2;
                moveStructure(nextStructure.getImageView().getY()-32*SnoopyWindow.SCALE, nextStructure.getImageView().getX(), nextStructure, y, x, Y, X);
                break;
            case DOWN:
                Y = Y+1;
                y = y+2;
                moveStructure(nextStructure.getImageView().getY()+32*SnoopyWindow.SCALE, nextStructure.getImageView().getX(), nextStructure, y, x, Y, X);
                break;
            case LEFT:
                X = X-1;
                x = x-2;
                moveStructure(nextStructure.getImageView().getY(), nextStructure.getImageView().getX()-32*SnoopyWindow.SCALE, nextStructure, y, x, Y, X);
                break;
            case RIGHT:
                X = X+1;
                x = x+2;
                moveStructure(nextStructure.getImageView().getY(), nextStructure.getImageView().getX()+32*SnoopyWindow.SCALE, nextStructure, y, x, Y, X);
                break;
        }

    }

    /**
     * Gère le déplacement du bloc poussable
     * @param y Coordonnées de l'endroit ou déplacer la structure
     * @param x Coordonnées de l'endroit ou déplacer la structure
     * @param nextStructure La structure à déplacer
     * @param newY Coordonnées matricielles de l'endroit ou déplacer la structure
     * @param newX Coordonnées matricielles de l'endroit ou déplacer la structure
     * @param originalX Coordonnées matricielles de la structure à déplacer
     * @param originalY Coordonnées matricielles de la structure à déplacer
     */
    private void moveStructure(double y, double x, Structure nextStructure, int newY, int newX, int originalY, int originalX){

        if (!nextStructure.isHasBeenPushed()){

            //Vérifie qu'on ne sorte pas le bloc de la grille
            if((newX >= 0 && newX <= 8)&&(newY >= 0 && newY <= 8)){

                //Vérifie que le bloc soit bien poussé vers le vide
                if(window.getLevelDisplay().getLevelStruture().get(newY).get(newX).getStructure().equals(Structures.EMPTY)){

                    Structure bloc2 = window.getLevelDisplay().getLevelStruture().get(newY).get(newX);

                    //Déplace la hitbox
                    for(Map.Entry<Rectangle, Structures> entry: window.getLevelDisplay().getColisionRectangle().entrySet()){
                        /*
                        entry.getKey().setFill(Color.BLACK);
                        window.addAllNode(entry.getKey());

                         */
                        if (entry.getKey().getX() == nextStructure.getImageView().getX() && entry.getKey().getY() == nextStructure.getImageView().getY()){
                            entry.getKey().setX(x);
                            entry.getKey().setY(y);
                        }

                    }

                    //Déplace le bloc visuellement
                    nextStructure.getImageView().setY(y);
                    nextStructure.getImageView().setX(x);

                    //Déplace le bloc matriciellement
                    window.getLevelDisplay().getLevelStruture().get(newY).remove(newX);
                    window.getLevelDisplay().getLevelStruture().get(newY).add(newX, nextStructure);
                    window.getLevelDisplay().getLevelStruture().get(originalY).remove(originalX);
                    window.getLevelDisplay().getLevelStruture().get(originalY).add(originalX, bloc2);
                    nextStructure.setHasBeenPushed(true);

                }/*
                else{
                    System.out.println("Vous ne poussez pas le bloc vers du vide");
                }
                */

            }/*
            else{
                System.out.println("Vous tentez de pousser le bloc hors de la grille");
            }
            */

        }/*
        else {
            System.out.println("Vous avez déjà poussé ce bloc");
        }
        */

    }

    /**
     * Teleporte le personnage
     *
     * Récupére le symbole du teleporteur actuel, chercher les coordonées du deuxième téléporteur de même symbole
     * et téléporte le personnage dessus
     */
    private void teleport(Structure teleporter){

        int x = 0;
        int y = 0;


        List<List<Structure>> structuresLevelList = window.getLevelDisplay().getLevelStruture();

        for(List<Structure> structureList: structuresLevelList){

            for(Structure struc: structureList){

                if(struc.getStructure().getSymbol().equals(teleporter.getStructure().getSymbol())
                && struc.getImageView().getY() != teleporter.getImageView().getY()
                && struc.getImageView().getX() != teleporter.getImageView().getX()){

                    xProperty.set(struc.getImageView().getX());
                    yProperty.set(struc.getImageView().getY());

                    moveToX = x;
                    moveToY = y;

                }

                x++;

            }

            y++;
            x=0;

        }

    }

    /**
     * Vérifie si le personnage est déjà en cours de déplacement
     * @return boolean Le personnage bouge
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
    private void initLooseLifeAndDefeatAnimations(){

        //Tourne sur lui même puis est désamparé puis éclate
        final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_RIGHT_1));

                isMoving = true;

            }
        });
        final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_UP_1));

            }
        });
        final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_LEFT_1));

            }
        });
        final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));


            }
        });
        final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(800), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_DESAMP));

            }
        });
        final KeyFrame keyFrame6 = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_MORT));

                isMoving = false;

            }
        });

        timelineLooseLifeAndDefeat = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6);

    }

    /**
     * Initialise animation disparition snoopy
     */
    private void initTpDisapeareAnimation(){

        //snoopy qui dispparaite
        final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));
                isMoving = true;

            }
        });
        final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(null);

            }
        });
        final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));

            }
        });
        final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(450), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(null);

            }
        });
        final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));

            }
        });
        final KeyFrame keyFrame6 = new KeyFrame(Duration.millis(750), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(null);
                isMoving = false;

            }
        });

        timelineTpDisapeare = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6);
        timelineTpApeare = new Timeline(keyFrame6,keyFrame5,keyFrame4,keyFrame3,keyFrame2,keyFrame1);

    }

    /**
     * Initialise animation apararition snoopy
     */
    private void initTpApeareAnimation(){


        //snoopy qui saute
        final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(null);
                isMoving = true;

            }
        });
        final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));

            }
        });
        final KeyFrame keyFrame3 = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(null);


            }
        });
        final KeyFrame keyFrame4 = new KeyFrame(Duration.millis(450), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));


            }
        });
        final KeyFrame keyFrame5 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(null);


            }
        });
        final KeyFrame keyFrame6 = new KeyFrame(Duration.millis(750), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getImageView().setImage(getImage(Structures.SNOOPY_IMMOBILE));
                isMoving = false;

            }
        });

        timelineTpApeare = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6);

    }

    /**
     * Animation à la perte de vie
     */
    public void animateLooseLife(){

        window.getLevelDisplay().setLooseLife(true);

        //On sauvegarde la vie
        window.getSauvegarde().getPlayer().setVie(vie);

        isMoving = true;
        timelineLooseLifeAndDefeat.playFromStart();

        timelineLooseLifeAndDefeat.setOnFinished(even -> window.getLevelDisplay().looseLife());

    }

    /**
     * Animation de la défaite
     */
    public void animateDefeate(){

        window.getLevelDisplay().setLoose(true);

        isMoving = true;

        timelineLooseLifeAndDefeat.playFromStart();
        timelineLooseLifeAndDefeat.setOnFinished(even -> window.getLevelDisplay().defeat());

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
     * @param playerRectangle Hitbox du personnage
     * @return Une colision est apparue
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
    public void setMoving(boolean moving) {
        isMoving = moving;
    }


    public int getMoveToX() {
        return moveToX;
    }
    public int getMoveToY() {
        return moveToY;
    }

}
