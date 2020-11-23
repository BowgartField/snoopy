package fr.manchez.snoopy.application.models.display.displays;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.display.Display;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.geometry.Point2D;

public abstract class MenuDisplay extends Display{

    protected Structure curseur;

    /** Premiere ligne de sélection = true*/
    protected boolean isOption1 = true;

    public MenuDisplay(SnoopyWindow snoopyWindow) {
        super(snoopyWindow);
    }

    @Override
    public void draw(){

        // On affiche le curseur
        curseur = new Structure(
                new Point2D(73* SnoopyWindow.SCALE, 216*SnoopyWindow.SCALE),
                Structures.CURSEUR
        );

        window.getPane().getChildren().add(curseur.getImageView());

        drawOther();
    }

    protected void drawOther(){};
}
