package fr.manchez.snoopy.application.models.display.displays;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class FinishDisplay extends MenuDisplay{

    public FinishDisplay(SnoopyWindow snoopyWindow) {
        super(snoopyWindow);

        window.getPane().setBackground(
            new Background(
                new BackgroundImage(
                        new Image(
                                getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.FinishDisplay.getBackgroundURL()),
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

    @Override
    public void draw(){}

    /**
     *
     */
    @Override
    public void traductEvent(KeyEvent event){

        KeyCode keyCode = event.getCode();

        if(keyCode.equals(KeyCode.ENTER)){

            window.getSauvegarde().reset();

            window.loadNewDisplay(Displays.StartDisplay);

        }

    }

}
