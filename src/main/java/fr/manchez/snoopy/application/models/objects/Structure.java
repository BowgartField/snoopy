package fr.manchez.snoopy.application.models.objects;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

public class Structure extends Object {

    /**
     * Hitbox pour gérer les collisions
     */
    protected final Rectangle hitbox;

    /** Image de la structure **/

    protected ImageView imageView;

    /** Structure **/

    protected Structures structure;



    protected boolean hasBeenPushed = false;

    /**
     * Constructeur
     * @param point2D Point 2D, correspondant à la position de la structure dans l'espace
     * @param structure Structure représenté par l'objet
     */
    public Structure(Point2D point2D, Structures structure){

        super(point2D);

        this.structure = structure;

        imageView = new ImageView(getImage(structure));

        //On bind les propriétés de notre structure
        imageView.xProperty().bindBidirectional(xProperty);
        imageView.yProperty().bindBidirectional(yProperty);

        //On créer la hitbox
        hitbox = new Rectangle(
                structure.getWidth()* SnoopyWindow.SCALE,
                structure.getHeight()*SnoopyWindow.SCALE);
        hitbox.xProperty().bindBidirectional(xProperty);
        hitbox.yProperty().bindBidirectional(yProperty);

    }

    public ImageView getImageView(){
        return imageView;
    }

    /**
     * Récupére l'image dans les dossiers
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
            true
        );

    }

    /**
     * Change l'image de la structure
     * @param structure La structure dont on doit charger l'image
     */
    public void setImage(Structures structure){

        imageView.setImage(getImage(structure));

    }

    /**
     * Renvoie la strcuture associé
     * @return {Structures} la structure
     */
    public Structures getStructure(){
        return structure;
    }

    @Override
    public String toString() {
        return structure.getSymbol();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isHasBeenPushed() {
        return hasBeenPushed;
    }

    public void setHasBeenPushed(boolean hasBeenPushed) {
        this.hasBeenPushed = hasBeenPushed;
    }
}
