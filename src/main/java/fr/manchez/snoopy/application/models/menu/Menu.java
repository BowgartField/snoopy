package fr.manchez.snoopy.application.models.menu;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public abstract class Menu {

    protected Structure curseur;

    /** Premiere ligne de sélection = true*/
    protected boolean isOption1 = true;

    public void drawMenu(SnoopyWindow window){

        window.getPane().getChildren().clear();

        // On affiche le curseur
        curseur = new Structure(
                new Point2D(73* SnoopyWindow.SCALE, 216*SnoopyWindow.SCALE),
                Structures.CURSEUR
        );

        window.getPane().getChildren().add(curseur.getImageView());
    }

    public void moveArrow(KeyCode keyCode){}
}
