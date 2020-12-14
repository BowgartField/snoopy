package fr.manchez.snoopy.application.enums;

import javafx.geometry.Point2D;

public enum Levels{

    LEVEL_1("level1.txt", "6523", 1,new Point2D(50.0,150.0)),
    LEVEL_2("level2.txt", "0002", 2,new Point2D(50.0,150.0)),
    LEVEL_3("level3.txt", "0003", 3,new Point2D(50.0,190.0)),
    LEVEL_4("level4.txt", "0004", 4,new Point2D(50.0,150.0)),
    LEVEL_5("level5.txt", "0005", 5,new Point2D(200.0,320.0)),
    LEVEL_6("level6.txt", "0006", 6,new Point2D(50.0,150.0));

    private String Url;
    private String password;

    private int levelNumber;
    private Point2D ballePosition;

    Levels(String Url, String password, int levelNumber, Point2D ballePosition){
        this.Url = Url;
        this.password = password;
        this.levelNumber = levelNumber;
        this.ballePosition = ballePosition;
    }

    public static Levels findLevelsFromPassword(String password){
        Levels level = null;

        for (Levels levels: Levels.values()){
            if (levels.password.equals(password)){
                level = levels;
                break;
            }
        }
        return level;
    }
    public static Levels findLevelsFromLevelNumber(int levelNumber){
        Levels level = null;

        for (Levels levels: Levels.values()){
            if (levels.levelNumber == levelNumber){
                level = levels;
                break;
            }
        }
        return level;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return Url;
    }

    public Point2D getBallePosition() {
        return ballePosition;
    }
}
