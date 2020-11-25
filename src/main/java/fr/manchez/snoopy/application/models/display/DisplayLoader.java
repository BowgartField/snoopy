package fr.manchez.snoopy.application.models.display;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import fr.manchez.snoopy.application.models.display.displays.GameDisplay;
import fr.manchez.snoopy.application.models.display.displays.PasswordDisplay;
import fr.manchez.snoopy.application.models.display.displays.StartDisplay;

public abstract class DisplayLoader {

    /** Gère le background a afficher*/
    public static Display load(Displays displays, SnoopyWindow window){

        Display display = null;

        if(displays.equals(Displays.StartDisplay)){
            display = new StartDisplay(window);
        }

        if(displays.equals(Displays.GameDisplay)){
            display = new GameDisplay(window);
        }

        if(displays.equals(Displays.PasswordDisplay)){
            display = new PasswordDisplay(window);
        }

        return display;

    }

}
