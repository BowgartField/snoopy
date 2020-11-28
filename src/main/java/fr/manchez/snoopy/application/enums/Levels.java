package fr.manchez.snoopy.application.enums;

public enum Levels{

    LEVEL_1("test.txt", "6523", 001),
    LEVEL_2("level2.txt", "0002", 002);

    private String Url;
    private String password;

    private int levelNumber;

    Levels(String Url, String password, int levelNumber){
        this.Url = Url;
        this.password = password;
        this.levelNumber = levelNumber;
    }

    @Override
    public String toString() {
        return this.Url;
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

}
