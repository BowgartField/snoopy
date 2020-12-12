package fr.manchez.snoopy.application.enums;

public enum Levels{

    LEVEL_1("level1.txt", "6523", 001),
    LEVEL_2("level2.txt", "0002", 002),
    LEVEL_3("level3.txt", "0003", 003),
    LEVEL_4("level4.txt", "0004", 004),
    LEVEL_5("level5.txt", "0005", 005);

    private String Url;
    private String password;

    private int levelNumber;

    Levels(String Url, String password, int levelNumber){
        this.Url = Url;
        this.password = password;
        this.levelNumber = levelNumber;
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
}
