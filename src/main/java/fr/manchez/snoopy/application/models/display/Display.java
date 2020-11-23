package fr.manchez.snoopy.application.models.display;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import javafx.scene.input.KeyEvent;


public abstract class Display{

    protected Displays displays;
    protected SnoopyWindow window;

    public Display(SnoopyWindow snoopyWindow){

        window = snoopyWindow;

    }

    public void traductEvent(KeyEvent event){}
    public void draw(){}

}
