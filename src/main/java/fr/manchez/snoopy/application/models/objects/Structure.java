package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Object;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Structure extends Object {

    /** Image de la structure **/

    protected ImageView imageView;

    /** Structure **/

    protected Structures structure;

    /**
     * Constructeur
     * @param point2D Point 2D, correspondant à la position de la structure dans l'espace
     * @param structure Structure représenté par l'objet
     */
    public Structure(Point2D point2D, Structures structure){

        super(point2D);

        this.structure = structure;

        imageView = new javafx.scene.image.ImageView(getImage(structure));

        //On bind les propriétés de notre structure
        imageView.xProperty().bindBidirectional(xProperty);
        imageView.yProperty().bindBidirectional(yProperty);

    }

    public ImageView getImageView(){
        return imageView;
    }

    /**
     *
     * @param structure
     * @return
     */
    protected Image getImage(Structures structure){

        //On récupére l'image correspondant à la structure
        InputStream inputStream = getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/" + structure.getFileURL());
        return new Image(
                inputStream,
                structure.getWidth()*SnoopyWindow.SCALE,
                structure.getHeight()*SnoopyWindow.SCALE,
                false,
                true);


    }

}
