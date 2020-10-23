package fr.manchez.snoopy.application.models;

public class Object {

    /** Position x de l'objet dans la fenêtre **/
    private int x;

    /** Position y de l'objet dans la fenêtre **/
    private int y;

    /**
     * Créer un object dans la fenêtre aux position x et y
     * @param x Position x
     * @param y Position y
     */
    public Object(int x, int y){
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

}
