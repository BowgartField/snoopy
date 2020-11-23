package fr.manchez.snoopy.application.enums;

public enum Levels{

    MAIN("test.txt", "0001"),
    LEVEL_1("", "");

    private String Url;
    private String password;

    Levels(String Url, String password){
        this.Url = Url;
        this.password = password;
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
}
