package fr.manchez.snoopy.application.models.levels;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.objects.Personnage;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class permettant de chargé un niveau depuis un fichier .txt dans un objet Level
 */
public class LevelDisplayLoader {

    /** Niveau Chargé **/
    LevelDisplay level;

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
     * @param window SnoopyWindow
     */
    public LevelDisplayLoader(Levels levels, SnoopyWindow window){
        this.levels = levels;
        this.window = window;

       level = new LevelDisplay(window, levels);
    }

    /**
     * Dessine le niveau dans la fenêtre
     * @return LevelDisplay
     */
    public LevelDisplay load(){

        loadFileIntoArray();
        createStructuresPoint2DList();
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

        InputStream in = getClass().getResourceAsStream("/fr/manchez/snoopy/levels/" + levels.getUrl());

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
     * Dessines les structres
     */
    private void createStructures(){
        int index = 0;

        //on boucle sur les colonnes
        for(List<Character> charactersList: levelCharacters){

            List<Structure> structureList = new ArrayList<>();

            //on boucle sur les lignes
            for (Character character: charactersList){

                //On scale les coordonnées
                Point2D newPoint2D = new Point2D(
                        structuresPosition.get(index).getX()*window.getScale()+window.getScale(),
                        structuresPosition.get(index).getY()*window.getScale()+window.getScale()
                );

                Structures structure = Structures.getStructuresFromSymbol(character);

                //La structure n'est pas le player
                if(!String.valueOf(character).equals(Structures.SNOOPY_IMMOBILE.getSymbol())){

                    Structure structureToAdd = new Structure(newPoint2D,structure);

                    structureList.add(structureToAdd);

                    //La structure est un obstacle ou un destructible ou un bloc disparition
                    if(structure.getSymbol().equals(Structures.OBSTACLE.getSymbol())
                            || structure.getSymbol().equals(Structures.DESTRUCTIBLE.getSymbol())
                            || structure.getSymbol().equals(Structures.DISPARITION_ENTIER.getSymbol())){

                        Rectangle rectangleColision = new Rectangle(
                                newPoint2D.getX(),
                                newPoint2D.getY(),
                                structure.getWidth()*window.getScale(),
                                structure.getHeight()*window.getScale()
                        );

                        level.addColisionStructure(structure,rectangleColision);

                    }

                    //La structure est un oiseau
                    if(structure.getSymbol().equals(Structures.BIRD.getSymbol())){

                        level.addBirds(structureToAdd);

                    }

                }else{

                    level.addSnoopy(new Personnage(newPoint2D,structure,window));
                    structureList.add(new Structure(newPoint2D,Structures.SNOOPY_SPAWN_POINT));

                }

                index++;

            }

            level.addStructure(structureList);

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
