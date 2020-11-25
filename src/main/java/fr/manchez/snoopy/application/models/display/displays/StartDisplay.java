package fr.manchez.snoopy.application.models.display.displays;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class StartDisplay extends MenuDisplay {

    /** Media player **/
    MediaPlayer mediaPlayer;

    public StartDisplay(SnoopyWindow window){
        super(window);

        window.getPane().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(
                                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.StartDisplay.getBackgroundURL()),
                                        640,640,true,true
                                ),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        )
                )
        );

        try{

            Media sound = new Media(getClass().getResource("/fr/manchez/snoopy/sounds/titleTheme.mp3").toURI().toString());

            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /** Gère les déplacements du curseur*/
    @Override
    public void traductEvent(KeyEvent event) {

        KeyCode keyCode = event.getCode();

        /** Vérifie la touche sur laquelle l'utilisateur appuie ainsi que la ligne sur laquelle le curseur est placée */
        if(keyCode.equals(KeyCode.UP) && isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34* SnoopyWindow.SCALE);
            isOption1 = false;
        }else if(keyCode.equals(KeyCode.UP)){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
            isOption1 = true;
        }else if(keyCode.equals(KeyCode.DOWN) && isOption1){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34*SnoopyWindow.SCALE);
            isOption1 = false;
        }else if(keyCode.equals(KeyCode.DOWN)){
            curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
            isOption1 = true;
        }else if (keyCode.equals(KeyCode.ENTER)){

            window.loadNewDisplay(Displays.GameDisplay);

        }

    }

}
