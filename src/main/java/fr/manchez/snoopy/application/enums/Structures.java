package fr.manchez.snoopy.application.enums;

public enum Structures {

    COIN_HAUT_GAUCHE("","Contours/coin_haut_gauche.png",16,16),
    COIN_HAUT_DROITE("","Contours/coin_haut_droite.png", 16,16),
    COIN_BAS_GAUCHE("","Contours/coin_bas_gauche.png",16,16),
    COIN_BAS_DROITE("","Contours/coin_bas_droite.png",16,16),

    TIMER_HORI_ON("","Contours/timer_horizontal_ON.png",16,16),
    TIMER_VER_ON("","Contours/timer_vertical_ON.png",16,16),

    TIMER_NOIR("","Contours/trait_haut_noir.png",32,16),
    TIMER_GRIS("","Contours/trait_haut_gris.png",32,16),

    EMPTY("X", "", 30,30),

    BIRD("P","Personnages/Oiseau/oiseau.png",32,32),
    SNOOPY("S","Personnages/Snoopy/face_immobile.png",32,32),

    OBSTACLE("O","Blocs/obstacle.png",30,30),
    DESTRUCTIBLE("D","Blocs/destructible2.png",30,30);




    /** Url vers le fichier de la structures **/
    private final String fileURL;

    /** Symbole représentant la structure dans le fichier **/
    private final String symbol;

    /** Longeur de base de la structure  **/
    private final int width;

    /** Largeur de base de la structure  **/
    private final int height;

    /** Lie un symbole à un fichier **/
    Structures(String symbol, String fileURL, int width, int height){
        this.symbol = symbol;
        this.fileURL = fileURL;
        this.width = width;
        this.height = height;
    }

    /**
     * Récupére l'url de la structure en fonction de son symbol
     * @param symbol Symbole de la structure
     * @return Url du fichier de la structure
     */
    public static Structures getStructuresFromSymbol(char symbol){

        Structures structure = null;

        for (Structures structures: values()){
            if(structures.getSymbol().equals(String.valueOf(symbol))){
                structure = structures;
                break;
            }
        }

        return structure;
    }

    public String getSymbol() {
        return symbol;
    }
    public String getFileURL() {
        return fileURL;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight() {
        return height;
    }
}
