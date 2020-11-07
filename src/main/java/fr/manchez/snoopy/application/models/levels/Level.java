package fr.manchez.snoopy.application.models.levels;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.models.objects.Personnage;
import fr.manchez.snoopy.application.models.objects.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet de gére un niveau au niveau:
 * - des colisions
 * - des emplacements des structures
 */
public class Level {

    /**
     * Contient la structure du Niveau
     */
    private List<Structure> levelStruture = new ArrayList<>();

    /**
     * Snoopy
     */
    private Personnage snoopy;

    /**
     * Ajoute une structure au niveau
     * @param structure structure
     */
    public void addStructure(Structure structure){
        levelStruture.add(structure);
    }

    /**
     * Ajoute snoopy
     */
    public void addSnoopy(Personnage snoopy){

        this.snoopy = snoopy;

    }

    /**
     *
     */
    public void drawStructure(SnoopyWindow window){

        //On affiche le décor dans la fenêtre
        for (Structure structure: levelStruture){

            window.getPane().getChildren().add(structure.getImageView());

        }

        // On affiche le personnage
        window.getPane().getChildren().add(snoopy.getImageView());

    }

    /*
        GETTERS
     */

    /**
     *
     */
    public Personnage getPersonnage(){
        return snoopy;
    }

}
