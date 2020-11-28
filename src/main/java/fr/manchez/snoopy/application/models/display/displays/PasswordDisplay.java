package fr.manchez.snoopy.application.models.display.displays;

import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Displays;
import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class PasswordDisplay extends MenuDisplay {

    protected Structure curseurUp;
    protected Structure integer1;
    protected Structure integer2;
    protected Structure integer3;
    protected Structure integer4;

    List<Integer> password = new ArrayList<>(); //Stock le mot de passe
    boolean isChoosingPass = false;
    int position = 0; //Stock la position

    public PasswordDisplay(SnoopyWindow snoopyWindow){
        super(snoopyWindow);

        window.getPane().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(
                                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/"+ Displays.PasswordDisplay.getBackgroundURL()),
                                        640,640,true,true
                                ),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        )
                )
        );

        password.add(0);
        password.add(0);
        password.add(0);
        password.add(0);

    }

    @Override
    public void drawOther(){

        /** Affichage du curseur et des numéros permettant de composer le mot de passe */
        curseurUp = new Structure(
                new Point2D(96* SnoopyWindow.SCALE, 232*SnoopyWindow.SCALE),
                Structures.CURSEURUP
        );
        integer1 = new Structure(
                new Point2D(97* SnoopyWindow.SCALE, 216*SnoopyWindow.SCALE),
                Structures.ZERO
        );
        integer2 = new Structure(
                new Point2D(113* SnoopyWindow.SCALE, 216*SnoopyWindow.SCALE),
                Structures.ZERO
        );
        integer3 = new Structure(
                new Point2D(129* SnoopyWindow.SCALE, 216*SnoopyWindow.SCALE),
                Structures.ZERO
        );
        integer4 = new Structure(
                new Point2D(145* SnoopyWindow.SCALE, 216*SnoopyWindow.SCALE),
                Structures.ZERO
        );

        window.addAllNode(
                integer1.getImageView(),
                integer2.getImageView(),
                integer3.getImageView(),
                integer4.getImageView()
        );

    }

    private void increment(boolean aug){
        int toAdd = 0;

        if(aug){
            if(password.get(position) < 9){
                toAdd = password.get(position)+1;
            }
        }else{
            if(password.get(position) > 0){
                toAdd = password.get(position)-1;
            }else{
                toAdd = 9;
            }
        }

        password.remove(position);
        password.add(position,toAdd);

        if(position == 0){
            integer1.setImage(Structures.getStructuresFromSymbol(String.valueOf(toAdd).charAt(0)));
        }else if(position == 1){
            integer2.setImage(Structures.getStructuresFromSymbol(String.valueOf(toAdd).charAt(0)));
        }else if (position == 2){
            integer3.setImage(Structures.getStructuresFromSymbol(String.valueOf(toAdd).charAt(0)));
        }else if(position == 3) {
            integer4.setImage(Structures.getStructuresFromSymbol(String.valueOf(toAdd).charAt(0)));
        }

    }

    /** Gère les déplacements du curseur*/
    @Override
    public void traductEvent(KeyEvent event) {

        KeyCode keyCode = event.getCode();

        if(keyCode.equals(KeyCode.ESCAPE)){

            window.loadNewDisplay(Displays.GameDisplay);

        }else{

            /** Vérifie sur quelle touche on appuie et sur quel ligne/colonne du menu on se trouve et effectue l'action correspondante*/
            if (!isChoosingPass) {

                if(keyCode.equals(KeyCode.UP) && isOption1){
                    curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34* SnoopyWindow.SCALE);
                    isOption1 = false;
                }else if(keyCode.equals(KeyCode.UP)){
                    curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
                    isOption1 = true;
                }else if(keyCode.equals(KeyCode.DOWN) && isOption1){
                    curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + 34*SnoopyWindow.SCALE);
                    isOption1 = false;
                }else if(keyCode.equals(KeyCode.DOWN)){
                    curseur.yPropertyProperty().set(curseur.yPropertyProperty().get() + -34*SnoopyWindow.SCALE);
                    isOption1 = true;
                }else if (keyCode.equals(KeyCode.RIGHT) && isOption1) {
                    window.removeAllNode(curseur.getImageView()); // Cache le curseur de base
                    window.addAllNode(curseurUp.getImageView()); // Affiche le curseur qui pointe vers le haut
                    isChoosingPass = true; // Spécifie qu'on est entrain de sélectionner le mot de passe
                }else if (keyCode.equals(KeyCode.ENTER) && !isOption1){

                    window.loadNewDisplay(Displays.GameDisplay);

                }else if (keyCode.equals(KeyCode.ENTER)) {
                    /** Vérification du code de la game*/
                    String pass = String.valueOf(password.get(0)) + password.get(1) + password.get(2) + password.get(3);
                    Levels level = Levels.findLevelsFromPassword(pass);

                    if (level != null) {
                        CheckPassword();
                    }
                }

            }else{

                if (keyCode.equals(KeyCode.RIGHT) && position < 3){
                    curseurUp.xPropertyProperty().set(curseurUp.xPropertyProperty().get() + 16* SnoopyWindow.SCALE);
                    position++;
                }else if (keyCode.equals(KeyCode.LEFT) && position > 0) {
                    curseurUp.xPropertyProperty().set(curseurUp.xPropertyProperty().get() + -16 * SnoopyWindow.SCALE);
                    position--;
                }else if (keyCode.equals(KeyCode.LEFT) && position == 0) {
                    window.removeAllNode(curseurUp.getImageView()); // Cache le curseur qui pointe vers le haut
                    window.addAllNode(curseur.getImageView()); // Affiche le curseur de base
                    isChoosingPass = false; // Spécifie qu'on n'est plus entrain de sélectionner le mot de passe
                }else if (keyCode.equals(KeyCode.UP)) {
                    increment(true);
                }else if(keyCode.equals(KeyCode.DOWN)){
                    increment(false);
                }else if(keyCode.equals(KeyCode.ENTER)){
                    CheckPassword();
                }
            }

        }

    }

    /** Vérification du code donné et lancement du level correspondant*/
    private void CheckPassword(){

        String pass = String.valueOf(password.get(0)) + password.get(1) + password.get(2) + password.get(3);
        Levels level  =  Levels.findLevelsFromPassword(pass);

        if(level != null){

            window.loadNewLevelDisplay(level);

        }

    }
}
