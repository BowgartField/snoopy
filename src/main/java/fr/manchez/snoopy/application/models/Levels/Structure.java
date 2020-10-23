package fr.manchez.snoopy.application.models.Levels;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Structure {

    /** Coordonnées X de la structure dans l'espace 2D **/
    private DoubleProperty xProperty;

    /** Coordonnées Y de la structure dans l'espace 2D **/
    private DoubleProperty yProperty;

    /** Image de la structure **/
    private ImageView imageView;

    /** Structure **/
    private Structures structure;

    /**
     * Constructeur
     * @param point2D Point 2D, correspondant à la position de la structure dans l'espace
     * @param structure Structure représenté par l'objet
     */
    public Structure(Point2D point2D, Structures structure){

        this.structure = structure;

        xProperty = new SimpleDoubleProperty(point2D.getX());
        yProperty = new SimpleDoubleProperty(point2D.getY());

        //On récupére l'image correspondant à la structure
        InputStream inputStream = getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/" + structure.getFileURL());
        Image image = new Image(
                inputStream,
                structure.getWidth()*SnoopyWindow.SCALE,
                structure.getHeight()*SnoopyWindow.SCALE,
                false,
                true);
        imageView = new javafx.scene.image.ImageView(image);

        //On bind les propriétés de notre structure
        imageView.xProperty().bindBidirectional(xProperty);
        imageView.yProperty().bindBidirectional(yProperty);

    }

}
