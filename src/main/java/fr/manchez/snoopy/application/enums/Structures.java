package fr.manchez.snoopy.application.enums;

public enum Structures {

    COIN_HAUT_GAUCHE("CHG","Contours/coin_haut_gauche.png",16,16),
    COIN_HAUT_DROITE("CHD","Contours/coin_haut_droite.png", 16,16),
    COIN_BAS_GAUCHE("CBG","Contours/coin_bas_gauche.png",16,16),
    COIN_BAS_DROITE("CBD","Contours/coin_bas_droite.png",16,16),

    TIMER_HORI_ON("HON","Contours/timer_horizontal_ON.png",16,16),
    TIMER_VER_ON("VON","Contours/timer_vertical_ON.png",16,16),
    TIMER_HORI_OFF("HOF","Contours/timer_horizontal_OFF.png",16,16),
    TIMER_VER_OFF("VOF","Contours/timer_vertical_OFF.png",16,16),

    TIMER_NOIR("","Contours/trait_haut_noir.png",32,16),
    TIMER_GRIS("","Contours/trait_haut_gris.png",32,16),

    EMPTY("X", "", 30,30),

    BIRD("P","Personnages/Oiseau/oiseau.png",32,32),
    POINTS("","Details/1000.png",32,16),

    BALLE("","Details/balle.png",16,16),

    SNOOPY_IMMOBILE("S","Personnages/Snoopy/face_immobile.png",32,32),
    SNOOPY_SPAWN_POINT("S","Personnages/Snoopy/face_immobile.png",32,32),
    SNOOPY_DESAMP("","Personnages/Snoopy/desempare.png",32,32),
    SNOOPY_MORT("","Details/mort.png",32,32),
    SNOOPY_HEUREUX("","Personnages/Snoopy/heureux.png",32,48),

    SNOOPY_LEFT_1("","Personnages/Snoopy/left/left_1.png",32,32),
    SNOOPY_LEFT_2("","Personnages/Snoopy/left/left_2.png",32,32),
    SNOOPY_LEFT_3("","Personnages/Snoopy/left/left_3.png",32,32),
    SNOOPY_RIGHT_1("","Personnages/Snoopy/right/right_1.png",32,32),
    SNOOPY_RIGHT_2("","Personnages/Snoopy/right/right_2.png",32,32),
    SNOOPY_RIGHT_3("","Personnages/Snoopy/right/right_3.png",32,32),
    SNOOPY_UP_1("","Personnages/Snoopy/up/up_1.png",32,32),
    SNOOPY_UP_2("","Personnages/Snoopy/up/up_2.png",32,32),
    SNOOPY_DOWN_1("","Personnages/Snoopy/down/down_1.png",32,32),
    SNOOPY_DOWN_2("","Personnages/Snoopy/down/down_2.png",32,32),

    OBSTACLE("O","Blocs/obstacle.png",30,30),
    DESTRUCTIBLE("D","Blocs/destructible2.png",30,30),
    DEBRIS("","Blocs/cassé.png",30,30),
    TAPIS_HAUT("H","Blocs/tapis_haut.png",30,30),
    TAPIS_BAS("B","Blocs/tapis_bas.png",30,30),
    TAPIS_DROIT("R","Blocs/tapis_droite.png",30,30),
    TAPIS_GAUCHE("L","Blocs/tapis_gauche.png",30,30),
    PIEGE("T","Blocs/piege.png",30,30),
    POUSSABLE("V","Blocs/poussable.png",30,30),
    DISPARITION_ENTIER("K","Blocs/disparition_entier.png",30,30),
    DISPARITION_DEMI("","Blocs/disparition_demi.png",30,30),

    TP1("Z","Blocs/teleporteur.png",30,30),
    TP2("W","Blocs/teleporteur.png",30,30),

    CURSEUR("", "Details/curseur.png", 16, 16),
    CURSEURUP("", "Details/curseurUp.png", 16, 16),
    PAUSE("","Details/pause.png",80,16),

    ZERO("0", "Textes/Chiffres/0.png", 14, 14),
    UN("1", "Textes/Chiffres/1.png", 14, 14),
    DEUX("2", "Textes/Chiffres/2.png", 14, 14),
    TROIS("3", "Textes/Chiffres/3.png", 14, 14),
    QUATRE("4", "Textes/Chiffres/4.png", 14, 14),
    CINQ("5", "Textes/Chiffres/5.png", 14, 14),
    SIX("6", "Textes/Chiffres/6.png", 14, 14),
    SEPT("7", "Textes/Chiffres/7.png", 14, 14),
    HUIT("8", "Textes/Chiffres/8.png", 14, 14),
    NEUF("9", "Textes/Chiffres/9.png", 14, 14),

    STAGE_CLEAR("", "Textes/stageClear.png", 240, 16),
    STAGE_LOOSE("", "Textes/stageNb.png", 141,16),

    BALL_STATE1("", "Details/boule1.png", 32, 32),
    BALL_STATE2("", "Details/boule2.png", 32, 32),
    BALL_STATE3("", "Details/boule3.png", 32, 32),
    SHADOW_STATE1("", "Details/ombre1.png", 32, 16),
    SHADOW_STATE2("", "Details/ombre2.png", 32, 16),
    SHADOW_STATE3("", "Details/ombre3.png", 32, 16);

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
