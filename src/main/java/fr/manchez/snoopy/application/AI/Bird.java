package fr.manchez.snoopy.application.AI;

import javafx.geometry.Point2D;

/** Représente un oiseau **/
public class Bird {

    /** Coordonées dans l'espace **/
    Point2D point2D;

    public Bird(Point2D point2D){

        this.point2D = point2D;

    }

    /**
     * Récupére les coordonées de l'oiseau
     * @return renvoie les coordonées de l'oiseau
     */
    public Point2D getCoords() {
        return point2D;
    }
}
