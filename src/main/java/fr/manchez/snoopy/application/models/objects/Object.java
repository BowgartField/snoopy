package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.SnoopyWindow;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;

public abstract class Object {

    /** Coordonnées X de l'objet dans la fenêtre **/
    protected DoubleProperty xProperty;

    /** Coordonnées Y de l'objet dans la fenêtre **/
    protected DoubleProperty yProperty;

    /**
     * Créer un object dans la fenêtre aux position x et y
     * @param position Position de l'objet dans la fênetre
     */
    public Object(Point2D position){

        xProperty = new SimpleDoubleProperty(position.getX());
        yProperty = new SimpleDoubleProperty(position.getY());
    }

    public DoubleProperty yPropertyProperty() {
        return yProperty;
    }

    public DoubleProperty xPropertyProperty() {
        return xProperty;
    }
}
