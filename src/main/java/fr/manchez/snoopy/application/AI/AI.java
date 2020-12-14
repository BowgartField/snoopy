package fr.manchez.snoopy.application.AI;

import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.Structures;
import fr.manchez.snoopy.application.models.levels.LevelDisplay;
import fr.manchez.snoopy.application.models.objects.Structure;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Intelligence artificielle permettenant de résoudre les niveaux seul (enfin je crois)
 */
public class AI {

    /* DEPEDENCIES **/
    LevelDisplay levelDisplay;

    /** SnoopyWidow **/
    SnoopyWindow window;

    /** Coords de la prochaine position **/
    private Structure birdToGo;

    /** List des déplacement **/
    private List<Direction> pathToNereastBird = new ArrayList<>();

    /**
     *
     */
    private Timeline animateSnoopyTimeline;

    int x;
    int y;

    /**
     *Constructeur par défaut de l'IA
     * @param levelDisplay L'affichage du niveau
     * @param window  Fenetre du niveau
     */
    public AI(LevelDisplay levelDisplay, SnoopyWindow window){

        this.levelDisplay = levelDisplay;
        this.window = window;

        this.x = 4;
        this.y = 4;

    }

    /**
     * Démmare le mode automatique
     */
    public void start(){

        //Cherche le chemin le plus court

        selectShortestPathToNereastBird();

    }

    /**
     * Récupére l'oiseau le plus proche
     */
    private void getNereastBird(){

        List<Structure> birdList = levelDisplay.getBirdList();

        //System.out.println("taille oiseau: " + birdList.size());
        
        double actualDistance = -1;

        ImageView bloc = levelDisplay.getLevelStruture().get(y).get(x).getImageView();

        for(Structure birds: birdList){

            if(birds.getStructure().equals(Structures.BIRD)){

                if(actualDistance != -1){

                    double distance = Math.hypot(
                            Math.abs(bloc.getY() - birds.getImageView().getY()),
                            Math.abs(bloc.getX() - birds.getImageView().getX())
                    );

                    if(distance <= actualDistance){
                        actualDistance = distance;
                        birdToGo = birds;
                    }

                }else{

                    actualDistance = Math.hypot(
                            Math.abs(bloc.getY() - birds.getImageView().getY()),
                            Math.abs(bloc.getX() - birds.getImageView().getX())
                    );

                    birdToGo = birds;

                }

            }

        }

        //System.out.println(actualDistance);
        //System.out.println(birdToGo);

    }

    /**
     * Scanne l'environnement autour du personnage
     */
    private List<Direction> scanNearbyEnvironement(){

        List<Direction> nearbyStructures = new ArrayList<>();

        //On récupére les 4 structures

        //le haut
        if(y != 0){
            nearbyStructures.add(new Direction(Moves.UP,
                levelDisplay.getLevelStruture().get(y-1).get(x)
            ));
        }

        //pour le bas
        if(y != 8){
            nearbyStructures.add(new Direction(Moves.DOWN,
                levelDisplay.getLevelStruture().get(y+1).get(x)
            ));
        }

        //pour la gauche
        if(x != 0){
            nearbyStructures.add(new Direction(Moves.LEFT,
                levelDisplay.getLevelStruture().get(y).get(x-1)
            ));
        }

        //pour la droite
        if(x != 8){
            nearbyStructures.add(new Direction(Moves.RIGHT,
                levelDisplay.getLevelStruture().get(y).get(x+1)
            ));
        }

        return nearbyStructures;

    }


    /**
     * Recherche le chemin le plus court vers les oiseaux
     */
    private void selectShortestPathToNereastBird(){

        List<List<Direction>> pathAlreadyCalculatePath = new ArrayList<>();

        List<Direction> actualPath = new ArrayList<>();

        //on détecte l'environement
        List<Direction> nearbyStructure;

        /*
         * BOUCLE
         */

        do{

            getNereastBird();

            //System.out.println("---------------------------------------------------------------------------------------");

            //printObject(pathAlreadyCalculatePath);
            //System.out.println("actualPath: " + actualPath);

            //Récupére les quatres structures
            nearbyStructure = scanNearbyEnvironement();

            //System.out.println("Nearby Directions: " + nearbyStructure);

            double actualDistanceChoosed = -1;
            Direction directionChoosed = null;

            //choisi la direction suivante en fonction de la distance par rapport a l'oiseau
            //choisi la plus petite distance parmis les distances disponible

            //Boucle sur les 4 strcutures autour de la position actuelle (x et y)
            for(Direction nearbyDirection: nearbyStructure){

                //if: la structure n'est pas un obstacle = c'est un chemin possible
                //else: la straucture est un obstacle on skip
                if(!nearbyDirection.getStructure().getStructure().equals(Structures.OBSTACLE)){

                    //On vérifie qu'il ne repasse pas par une même case
                    boolean finded = false;
                    for(Direction direction: actualPath){

                        if(direction.getStructure().equals(nearbyDirection.getStructure())){
                            finded = true;
                            break;
                        }
                    }

                    //on vérifie que l'on est pas
                    if(finded
                        || (actualPath.size() > 0 && getInverse(nearbyDirection).equals(actualPath.get(actualPath.size()-1).getMoves()))
                        || (actualPath.size() > 1 && getInverse(nearbyDirection).equals(actualPath.get(actualPath.size()-2).getMoves()))
                    ){
                        continue;
                    }

                    //On récupére les chemin déjà calculé qui ressemble a celui ou l'on est actuellement
                    List<List<Direction>> potentialAlreadyCalculatedPaths = getPotentialAlreadyCalculatedPaths(actualPath,pathAlreadyCalculatePath);

                    //if: il existe des chemins déjà calculés qui ressemblent au chemin actuel
                    //else: il s'agit d'un nouveau chemin => on a regarde si cette distance est plus petite que l'ancienne
                    if(potentialAlreadyCalculatedPaths.size() > 0){

                        //On boucle sur nos chemin potentiel
                        for(List<Direction> directionsList: potentialAlreadyCalculatedPaths){

                            //if: On arrive sur l'avant dernière case d'un chemin déjà calculé
                            //    C'est pour éviter de repasser sur le même chemin, qui est enfaite un cul de sac.
                            //else: on est pas sur un chemin déjà calculé => on calcul normal
                            if(actualPath.size() == directionsList.size()-1){

                                //if: On est jmais passer par ce chemin avant => on teste si il s'agit de la direction la plus proche
                                //else: On est déjà passé par le chemin ce qui veut dire nearbydirection = directionList.size()-1 =>
                                //    On fait rien puisqu'on s'apprete a repasser sur un chemin déjà calculer
                                if(!nearbyDirection.getMoves().equals(directionsList.get(directionsList.size()-1).getMoves())) {

                                    Pair<Direction,Double> result = getDistance(actualDistanceChoosed,
                                            nearbyDirection,directionChoosed);

                                    actualDistanceChoosed = result.getValue();
                                    directionChoosed = result.getKey();

                                }

                            }else{

                                Pair<Direction,Double> result = getDistance(actualDistanceChoosed,
                                        nearbyDirection,directionChoosed);

                                actualDistanceChoosed = result.getValue();
                                directionChoosed = result.getKey();

                            }

                        }

                    }else{

                        Pair<Direction,Double> result = getDistance(actualDistanceChoosed,nearbyDirection,
                                directionChoosed);

                        actualDistanceChoosed = result.getValue();
                        directionChoosed = result.getKey();

                    }


                }

            }

            //on est pas arrivé dans un cul de sac
            if(directionChoosed != null){

                //System.out.println("ENTRE distance: " + actualDistanceChoosed);
                //System.out.println("ENTRE direction: " + directionChoosed);

                increment(directionChoosed);
                actualPath.add(directionChoosed);

            }else{

                //si directionChoosed = null ca veut dire qu'on est arrivé à un cul de sac
                pathAlreadyCalculatePath.add(actualPath);
                actualPath = new ArrayList<>();

                x = window.getLevelDisplay().getPersonnage().getMoveToX();
                y = window.getLevelDisplay().getPersonnage().getMoveToY();

            }

            //System.out.println("--------------------------------------------------------------------------------------");


        }while (!containBird(actualPath));

        pathToNereastBird = actualPath;

        goToNearestBird();

    }

    /**
     * Récupére les chemins possibles déjà calculés en fonction de celui entrain d'être calculer
     * @param actualPath Chemin entrain d'être calculer
     * @param alreadyCalculatedPaths Chemins déjà calculer
     * @return Chemins ressemblant au chemin entrain d'être calculer
     */
    private List<List<Direction>> getPotentialAlreadyCalculatedPaths(List<Direction> actualPath, List<List<Direction>> alreadyCalculatedPaths){

        List<List<Direction>> potentialPath = new ArrayList<>();

        for(List<Direction> calculatedPath: alreadyCalculatedPaths){

            if(calculatedPath.size() == actualPath.size()+1){

                boolean isSamePath = true;

                for(int i = 0; i < actualPath.size(); i++){

                    if (!calculatedPath.get(i).getMoves().equals(actualPath.get(i).getMoves())) {
                        isSamePath = false;
                        break;
                    }

                }

                if(isSamePath){
                    potentialPath.add(calculatedPath);
                }

            }

        }

        return potentialPath;

    }

    /**
     * Incrémente la position fictive de snoopy
     * @param direction direction pris par snoopy
     */
    private void increment(Direction direction){

        switch (direction.getMoves()) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
        }

    }

    /**
     * Retourne l'inverse de la direction
     * @param direction Direction
     * @return Inverse de la direction
     */
    private Moves getInverse(Direction direction){

        Moves moves = null;

        switch (direction.getMoves()) {
            case UP:
                moves = Moves.DOWN;
                break;
            case DOWN:
                moves = Moves.UP;
                break;
            case RIGHT:
                moves = Moves.LEFT;
                break;
            case LEFT:
                moves = Moves.RIGHT;
                break;
        }

        return moves;

    }

    /**
     * Affiche un objet
     * @param directions Objet à afficher
      */
    private void printObject(List<List<Direction>> directions){

        directions.forEach(item -> System.out.println("calculated Paths:" + directions));

    }

    /**
     * Check la distance la plus petite
     * @param actualDistanceChoosed Distance la plus petite trouvé actuellement
     * @param directionToCheck Direction à check
     */
    private Pair<Direction,Double> getDistance(double actualDistanceChoosed, Direction directionToCheck, Direction actualDirection){

        Pair<Direction,Double> result = null;

        double distance = Math.hypot(
                Math.abs(directionToCheck.getStructure().getImageView().getY() - birdToGo.getImageView().getY()),
                Math.abs(directionToCheck.getStructure().getImageView().getX() - birdToGo.getImageView().getX())
        );

        if(actualDistanceChoosed == -1 || distance <= actualDistanceChoosed){

            //on a trouvé une distance plus petite OU c'est la première
            result = new Pair<>(directionToCheck,distance);

        }else{

            //elle n'est pas plus petite que la plus petite déjà trouvé
            result = new Pair<>(actualDirection,actualDistanceChoosed);

        }

        /*
        System.out.println("distance la plus petite trouvée:" + actualDistanceChoosed);
        System.out.println("distance calculé:" + distance);
        System.out.println("directionToCheck test:" + directionToCheck);

         */

        return result;

    }

    /**
     *
     * @return
     */
    private boolean containBird(List<Direction> actualPath){

        boolean result = false;

        for(Direction direction: actualPath){

            if(direction.getStructure().equals(birdToGo)){
                result = true;
                break;
            }

        }

        return result;
    }

    /**
     * Anime snoopy jusqu'a l'oiseau le plus proche
     */
    private void goToNearestBird(){

        System.out.println("animation start");

        AtomicInteger index = new AtomicInteger();

        //On a récupérer l'oiseau le plus proche
        animateSnoopyTimeline = new Timeline(new KeyFrame(Duration.millis(375), (ActionEvent event) -> {

                if(index.get() < pathToNereastBird.size()){


                    switch (pathToNereastBird.get(index.get()).getMoves()) {
                        case UP:
                            levelDisplay.getPersonnage().moveUp();
                            break;
                        case DOWN:
                            levelDisplay.getPersonnage().moveDown();
                            break;
                        case RIGHT:
                            levelDisplay.getPersonnage().moveRight();
                            break;
                        case LEFT:
                            levelDisplay.getPersonnage().moveLeft();
                            break;
                    }

                    index.getAndIncrement();

                }else{
                    animateSnoopyTimeline.stop();
                    restartAnimation();
                }

            }
        ));
        animateSnoopyTimeline.setCycleCount(Animation.INDEFINITE);
        animateSnoopyTimeline.playFromStart();

    }

    /**
     *
     */
    private void restartAnimation(){

        pathToNereastBird.clear();
        levelDisplay.getBirdList().remove(birdToGo);

        if(levelDisplay.getBirdsRemaining() != 0){
            selectShortestPathToNereastBird();
        }

    }

    /**
     *
     */
    public void stop(){

        if(animateSnoopyTimeline != null){
            animateSnoopyTimeline.stop();
        }

    }

}
