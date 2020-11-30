package fr.manchez.snoopy.application.models.display.displays;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import fr.manchez.snoopy.application.enums.Levels;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class  GameDisplay extends MenuDisplay {

    public GameDisplay(SnoopyWindow snoopyWindow){
        super(snoopyWindow);

        window.getPane().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(
                                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.GameDisplay.getBackgroundURL()),
                                        640,640,true,true
                                ),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        )
                )
        );
    }

    /** Effectue une action en fonction de la touche sur laquelle l'utilisateur appuie */
    @Override
    public void traductEvent(KeyEvent keyEvent){

        KeyCode keyCode = keyEvent.getCode();

        if(keyCode.equals(KeyCode.ESCAPE)){

            window.loadNewDisplay(Displays.StartDisplay);

        }else{

            /** Vérifie la touche sur laquelle l'utilisateur appuie ainsi que la ligne sur laquelle le curseur est placée */
            if(keyCode.equals(KeyCode.UP) && isOption1){
                curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34* SnoopyWindow.SCALE);
                isOption1 = false;
            }else if(keyCode.equals(KeyCode.UP) && !isOption1){
                curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
                isOption1 = true;
            }else if(keyCode.equals(KeyCode.DOWN) && isOption1){
                curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34*SnoopyWindow.SCALE);
                isOption1 = false;
            }else if(keyCode.equals(KeyCode.DOWN) && !isOption1){
                curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
                isOption1 = true;
            }else if (keyCode.equals(KeyCode.ENTER) && isOption1){

                window.loadNewLevelDisplay(window.getSauvegarde().getPlayer().getLevel());
                //window.loadNewLevelDisplay(Levels.LEVEL_4);

            }else if(keyCode.equals(KeyCode.ENTER) && !isOption1){

                window.loadNewDisplay(Displays.PasswordDisplay);

            }

        }

    }
}
