package fr.manchez.snoopy.application.models.levels;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Personnage;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class permettant de chargé un niveau depuis un fichier .txt dans un objet Level
 */
public class LevelLoader {

    /** Niveau Chargé **/
    Level level = new Level();

    /** Liste des positions possibles pour les structures **/
    List<Point2D> structuresPosition = new ArrayList<>();

    /** Charactéres contenus dans le fichier .txt **/
    List<List<Character>> levelCharacters = new ArrayList<>();

    /** Url du fichier correspondant au niveau */
    private final Levels levels;

    /** Fenêtre dans laquelle chargé le niveau */
    SnoopyWindow window;

    /**
     * Constructeur par défaut
     * @param levels Niveau a chargé
     */
    public LevelLoader(Levels levels, SnoopyWindow window){
        this.levels = levels;
        this.window = window;
    }

    /**
     * Dessine le niveau dans la fenêtre
     */
    public Level load(){

        loadFileIntoArray();
        createStructuresPoint2DList();
        drawTimer();
        setBackground();
        createStructures();

        return level;

    }

    /**
     * Affiche le fond d'écran
     */
    private void setBackground() {

        window.getPane().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(
                                        getClass().getResourceAsStream("/fr/manchez/snoopy/sprites/Fond/fond.png"),
                                        580,580,true,true
                                ),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        )
                )
        );

    }

    /**
     * Charge le niveau depuis un .txt dans un tableau
     */
    private void loadFileIntoArray(){

        InputStream in = getClass().getResourceAsStream("/fr/manchez/snoopy/levels/" + levels);

        try(Reader fr = new InputStreamReader(in, StandardCharsets.UTF_8)){

            //Représente une line (Il y en a logiquement 20)
            List<Character> charactersLine = new ArrayList<>();

            int content = 0;

            while((content = fr.read()) != -1){

                if(content != 10 && content != 13){
                    charactersLine.add((char) content);

                }else if (content == 13){

                    // 13 (ASCII) = carrage return (retour à la ligne). On change donc de ligne
                    levelCharacters.add(charactersLine);
                    charactersLine = new ArrayList<>();

                }

            }

            //Permet d'ajouter la dernière ligne puisque lorsque le pointeur arrive sur le derniere élément
            //Ce n'est pas 13 donc la derniere ligne n'est pas enregistré dans la tableau
            levelCharacters.add(charactersLine);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Dessine le compteur dans la fenêtre
     */
    private void drawTimer(){

        Structures timerHoriOn = Structures.TIMER_HORI_ON;
        Structures timerVerOn = Structures.TIMER_VER_ON;

        //Liste contenant la position de l'élement et sa structure
        HashMap<Point2D,Structures> timer = new HashMap<Point2D, Structures>(){{

            put(new Point2D(0,0 ),Structures.COIN_HAUT_GAUCHE);

            put(new Point2D(16,0),timerHoriOn);
            put(new Point2D(32,0), timerHoriOn);
            put(new Point2D(48,0), timerHoriOn);
            put(new Point2D(64,0), timerHoriOn);
            put(new Point2D(80,0), timerHoriOn);
            put(new Point2D(96,0), timerHoriOn);
            put(new Point2D(112,0), timerHoriOn);
            put(new Point2D(128, 0), Structures.TIMER_NOIR);
            put(new Point2D(160, 0), Structures.TIMER_GRIS);
            put(new Point2D(192,0), timerHoriOn);
            put(new Point2D(208,0), timerHoriOn);
            put(new Point2D(224,0), timerHoriOn);
            put(new Point2D(240,0), timerHoriOn);
            put(new Point2D(256,0), timerHoriOn);
            put(new Point2D(272,0 ), timerHoriOn);
            put(new Point2D(288,0), timerHoriOn);

            put(new Point2D(304,0), Structures.COIN_HAUT_DROITE);

            put(new Point2D(304,16),timerVerOn);
            put(new Point2D(304,32), timerVerOn);
            put(new Point2D(304,48), timerVerOn);
            put(new Point2D(304,64), timerVerOn);
            put(new Point2D(304,80), timerVerOn);
            put(new Point2D(304,96), timerVerOn);
            put(new Point2D(304,112), timerVerOn);
            put(new Point2D(304, 128), timerVerOn);
            put(new Point2D(304, 144), timerVerOn);
            put(new Point2D(304, 160), timerVerOn);
            put(new Point2D(304, 176), timerVerOn);
            put(new Point2D(304,192), timerVerOn);
            put(new Point2D(304,208), timerVerOn);
            put(new Point2D(304,224), timerVerOn);
            put(new Point2D(304,240), timerVerOn);
            put(new Point2D(304,256), timerVerOn);
            put(new Point2D(304,272), timerVerOn);
            put(new Point2D(304,288), timerVerOn);

            put(new Point2D(304,304), Structures.COIN_BAS_DROITE);

            put(new Point2D(16,304),timerHoriOn);
            put(new Point2D(32,304), timerHoriOn);
            put(new Point2D(48,304), timerHoriOn);
            put(new Point2D(64,304), timerHoriOn);
            put(new Point2D(80,304), timerHoriOn);
            put(new Point2D(96,304), timerHoriOn);
            put(new Point2D(112,304), timerHoriOn);
            put(new Point2D(128, 304), timerHoriOn);
            put(new Point2D(144, 304), timerHoriOn);
            put(new Point2D(160, 304), timerHoriOn);
            put(new Point2D(176, 304), timerHoriOn);
            put(new Point2D(192,304), timerHoriOn);
            put(new Point2D(208,304), timerHoriOn);
            put(new Point2D(224,304), timerHoriOn);
            put(new Point2D(240,304), timerHoriOn);
            put(new Point2D(256,304), timerHoriOn);
            put(new Point2D(272,304), timerHoriOn);
            put(new Point2D(288,304), timerHoriOn);

            put(new Point2D(0,304), Structures.COIN_BAS_GAUCHE);

            put(new Point2D(0,16),timerVerOn);
            put(new Point2D(0,32), timerVerOn);
            put(new Point2D(0,48), timerVerOn);
            put(new Point2D(0,64), timerVerOn);
            put(new Point2D(0,80), timerVerOn);
            put(new Point2D(0,96), timerVerOn);
            put(new Point2D(0,112), timerVerOn);
            put(new Point2D(0, 128), timerVerOn);
            put(new Point2D(0, 144), timerVerOn);
            put(new Point2D(0, 160), timerVerOn);
            put(new Point2D(0, 176), timerVerOn);
            put(new Point2D(0,192), timerVerOn);
            put(new Point2D(0,208), timerVerOn);
            put(new Point2D(0,224), timerVerOn);
            put(new Point2D(0,240), timerVerOn);
            put(new Point2D(0,256), timerVerOn);
            put(new Point2D(0,272), timerVerOn);
            put(new Point2D(0,288), timerVerOn);


        }};

        //Conteneur
        ObservableList<Node> root = window.getPane().getChildren();

        //Affiche le timer
        for (Map.Entry<Point2D,Structures> structure: timer.entrySet()){

            Image image = new Image(getClass().getResourceAsStream(
                    "/fr/manchez/snoopy/sprites/" + structure.getValue().getFileURL()),
                    structure.getValue().getWidth()*window.getScale(),
                    structure.getValue().getHeight()*window.getScale(),
                    false,
                    true
            );

            ImageView imageView = new ImageView(image);
            imageView.setX(structure.getKey().getX()*window.getScale());
            imageView.setY(structure.getKey().getY()*window.getScale());

            root.add(imageView);



        }

    }

    /**
     * Dessines les structres
     */
    private void createStructures(){

        int index = 0;

        //on boucle sur les colonnes
        for(List<Character> charactersList: levelCharacters){

            //on boucle sur les colonnes
            for (Character character: charactersList){

                //On scale les coordonnées
                Point2D newPoint2D = new Point2D(
                        structuresPosition.get(index).getX()*window.getScale()+window.getScale(),
                        structuresPosition.get(index).getY()*window.getScale()+window.getScale()
                );

                Structures structure = Structures.getStructuresFromSymbol(character);

                if(!String.valueOf(character).equals(Structures.SNOOPY_IMMOBILE.getSymbol())){

                    level.addStructure(new Structure(newPoint2D,structure));

                }else{

                    level.addSnoopy(new Personnage(newPoint2D,structure));

                }

                index++;

            }

        }

    }

    /**
     *
     */
    private void createStructuresPoint2DList(){

        int[] positions = {16,48,80,112,144,176,208,240,272};

        for (int y : positions) {

            for (int x : positions) {

                structuresPosition.add(new Point2D(x, y));

            }

        }

    }

}
