package fr.manchez.snoopy.application.models.sauvegarde;

import fr.manchez.snoopy.application.enums.Levels;
import fr.manchez.snoopy.application.enums.PlayersType;
import fr.manchez.snoopy.application.models.objects.Personnage;

public class Player {

    /** Player **/
    PlayersType player;

    /** HighScore **/
    int highscore;

    /** Score actuel **/
    int score;

    /** Level */
    Levels level;

    /** Nombre de vie **/
    int vie;

    /**

     /* *
     *
     * @param player Type de joueur (PLAYER1 ou PLAYER2)
     * @param highscore Score le plus haut
     * @param score Score actuel
     */
    public Player(PlayersType player, int highscore, int score, Levels level, int vie){

        this.player = player;
        this.score = score;
        this.vie = vie;
        this.level = level;
        this.highscore = highscore;

    }

    /**
     * Constructeur
     * @param playersType Type de joueur (PLAYER1 ou PLAYER2)
     */
    public Player(PlayersType playersType){

        this.player = playersType;
        this.score = 0;
        this.highscore = 0;
        this.vie = Personnage.DEFAULT_VIE;
        this.level = Levels.LEVEL_1;

    }

    /** Important pour Jackson */
    public Player(){}

    /**
     * Réinitialise le joueur
     */
    public void reset(){

        this.score = 0;
        this.vie = Personnage.DEFAULT_VIE;
        this.level = Levels.LEVEL_1;

    }

    /**
     * Reset tout
     */
    public void resetAll(){

        this.score = 0;
        this.highscore = 0;
        this.vie = Personnage.DEFAULT_VIE;
        this.level = Levels.LEVEL_1;

    }


    /*
        GETTERS AND SETTERS
     */

    public PlayersType getPlayer() {
        return player;
    }
    public void setPlayer(PlayersType player) {
        this.player = player;
    }
    public int getHighscore() {
        return highscore;
    }
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Levels getLevel() {
        return level;
    }
    public void setLevel(Levels level) {
        this.level = level;
    }
    public int getVie() {
        return vie;
    }
    public void setVie(int vie) {
        this.vie = vie;
    }

    /*
        UTILS
     */

    @Override
    public String toString() {
        return "Player{" +
                "player=" + player +
                ", highscore=" + highscore +
                ", score=" + score +
                ", level=" + level +
                ", vie=" + vie +
                '}';
    }
}
