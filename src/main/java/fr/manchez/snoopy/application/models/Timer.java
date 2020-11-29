package fr.manchez.snoopy.application.models;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Timer {

    Timeline timeline;

    /** Timer structure **/
    List<Structure> timer;

    /** Window **/
    SnoopyWindow window;

    /** Index **/
    int timerIndex = 1;

    /** Temps avant défaite (en secondes) **/
    int maxTimer = 74;

    public Timer(SnoopyWindow snoopyWindow){

        this.window = snoopyWindow;

        initTimer();
        drawTimer();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                //si le niveau n'est pas en pause
                if(!window.getLevelDisplay().isPause()){

                    //Il reste encore du temps
                    if(timerIndex < maxTimer){

                        Structure structureActual = timer.get(timerIndex);

                        if(!structureActual.getStructure().getSymbol().equals(Structures.TIMER_NOIR.getSymbol())
                                || !structureActual.getStructure().getSymbol().equals(Structures.TIMER_GRIS.getSymbol())){


                            if(structureActual.getStructure().getSymbol().equals(Structures.COIN_HAUT_DROITE.getSymbol())
                                    || structureActual.getStructure().getSymbol().equals(Structures.COIN_BAS_DROITE.getSymbol())
                                    || structureActual.getStructure().getSymbol().equals(Structures.COIN_BAS_GAUCHE.getSymbol())) {

                                timerIndex++;
                                structureActual = timer.get(timerIndex);
                            }

                                if(structureActual.getStructure().getSymbol().equals(Structures.TIMER_VER_ON.getSymbol())){
                                    structureActual.setImage(Structures.TIMER_VER_OFF);
                                }

                                if(structureActual.getStructure().getSymbol().equals(Structures.TIMER_HORI_ON.getSymbol())){
                                    structureActual.setImage(Structures.TIMER_HORI_OFF);
                                }

                                timerIndex++;

                        }else{
                            timerIndex=timerIndex+2;
                            System.out.println(timerIndex);
                            timer.get(timerIndex).setImage(Structures.TIMER_HORI_OFF);
                        }

                    }else{

                        window.getLevelDisplay().defeat();
                        timeline.stop();

                    }

                }

            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /**
     *
     */
    private void initTimer(){
        
        
        Structures timerHoriOn = Structures.TIMER_HORI_ON;
        Structures timerVerOn = Structures.TIMER_VER_ON;

        //Liste contenant la position de l'élement et sa structure
        timer = new ArrayList<Structure>(){{

            add(new Structure(new Point2D(0,0 ),Structures.COIN_HAUT_GAUCHE));

            add(new Structure((new Point2D(16,0)),timerHoriOn));
            add(new Structure((new Point2D(32,0)), timerHoriOn));
            add(new Structure((new Point2D(48,0)), timerHoriOn));
            add(new Structure((new Point2D(64,0)), timerHoriOn));
            add(new Structure((new Point2D(80,0)), timerHoriOn));
            add(new Structure((new Point2D(96,0)), timerHoriOn));
            add(new Structure((new Point2D(112,0)), timerHoriOn));
            add(new Structure((new Point2D(128, 0)), Structures.TIMER_NOIR));
            add(new Structure((new Point2D(160, 0)), Structures.TIMER_GRIS));
            add(new Structure((new Point2D(192,0)), timerHoriOn));
            add(new Structure((new Point2D(208,0)), timerHoriOn));
            add(new Structure((new Point2D(224,0)), timerHoriOn));
            add(new Structure((new Point2D(240,0)), timerHoriOn));
            add(new Structure((new Point2D(256,0)), timerHoriOn));
            add(new Structure((new Point2D(272,0)), timerHoriOn));
            add(new Structure((new Point2D(288,0)), timerHoriOn));

            add(new Structure((new Point2D(304,0)), Structures.COIN_HAUT_DROITE));

            add(new Structure((new Point2D(304,16)),timerVerOn));
            add(new Structure((new Point2D(304,32)), timerVerOn));
            add(new Structure((new Point2D(304,48)), timerVerOn));
            add(new Structure((new Point2D(304,64)), timerVerOn));
            add(new Structure((new Point2D(304,80)), timerVerOn));
            add(new Structure((new Point2D(304,96)), timerVerOn));
            add(new Structure((new Point2D(304,112)), timerVerOn));
            add(new Structure((new Point2D(304, 128)), timerVerOn));
            add(new Structure((new Point2D(304, 144)), timerVerOn));
            add(new Structure((new Point2D(304, 160)), timerVerOn));
            add(new Structure((new Point2D(304, 176)), timerVerOn));
            add(new Structure((new Point2D(304,192)), timerVerOn));
            add(new Structure((new Point2D(304,208)), timerVerOn));
            add(new Structure((new Point2D(304,224)), timerVerOn));
            add(new Structure((new Point2D(304,240)), timerVerOn));
            add(new Structure((new Point2D(304,256)), timerVerOn));
            add(new Structure((new Point2D(304,272)), timerVerOn));
            add(new Structure((new Point2D(304,288)), timerVerOn));

            add(new Structure((new Point2D(304,304)), Structures.COIN_BAS_DROITE));

            add(new Structure((new Point2D(288,304)), timerHoriOn));
            add(new Structure((new Point2D(272,304)), timerHoriOn));
            add(new Structure((new Point2D(256,304)), timerHoriOn));
            add(new Structure((new Point2D(240,304)), timerHoriOn));
            add(new Structure((new Point2D(224,304)), timerHoriOn));
            add(new Structure((new Point2D(208,304)), timerHoriOn));
            add(new Structure((new Point2D(192,304)), timerHoriOn));
            add(new Structure((new Point2D(176, 304)), timerHoriOn));
            add(new Structure((new Point2D(160, 304)), timerHoriOn));
            add(new Structure((new Point2D(144, 304)), timerHoriOn));
            add(new Structure((new Point2D(128, 304)), timerHoriOn));
            add(new Structure((new Point2D(112,304)), timerHoriOn));
            add(new Structure((new Point2D(96,304)), timerHoriOn));
            add(new Structure((new Point2D(80,304)), timerHoriOn));
            add(new Structure((new Point2D(64,304)), timerHoriOn));
            add(new Structure((new Point2D(48,304)), timerHoriOn));
            add(new Structure((new Point2D(32,304)), timerHoriOn));
            add(new Structure((new Point2D(16,304)),timerHoriOn));

            add(new Structure((new Point2D(0,304)), Structures.COIN_BAS_GAUCHE));


            add(new Structure((new Point2D(0,288)), timerVerOn));
            add(new Structure((new Point2D(0,272)), timerVerOn));
            add(new Structure((new Point2D(0,256)), timerVerOn));
            add(new Structure((new Point2D(0,240)), timerVerOn));
            add(new Structure((new Point2D(0,224)), timerVerOn));
            add(new Structure((new Point2D(0,208)), timerVerOn));
            add(new Structure((new Point2D(0,192)), timerVerOn));
            add(new Structure((new Point2D(0, 176)), timerVerOn));
            add(new Structure((new Point2D(0, 160)), timerVerOn));
            add(new Structure((new Point2D(0, 144)), timerVerOn));
            add(new Structure((new Point2D(0, 128)), timerVerOn));
            add(new Structure((new Point2D(0,112)), timerVerOn));
            add(new Structure((new Point2D(0,96)), timerVerOn));
            add(new Structure((new Point2D(0,80)), timerVerOn));
            add(new Structure((new Point2D(0,64)), timerVerOn));
            add(new Structure((new Point2D(0,48)), timerVerOn));
            add(new Structure((new Point2D(0,32)), timerVerOn));
            add(new Structure((new Point2D(0,16)),timerVerOn));

        }};

    }

    /**
     * Dessine le compteur dans la fenêtre
     */
    private void drawTimer(){

        //Affiche le timer
        for (Structure structure: timer){

            structure.getImageView().setX(structure.getImageView().getX()*SnoopyWindow.SCALE);
            structure.getImageView().setY(structure.getImageView().getY()*SnoopyWindow.SCALE);
            
            window.addAllNode(structure.getImageView());

        }

    }

    /**
     * Récupére le score
     */
    public int getTimerScore(){

        return (maxTimer-timerIndex)*100;

    }
}
