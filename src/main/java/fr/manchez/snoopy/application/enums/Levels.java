package fr.manchez.snoopy.application.enums;

public enum Levels{

    LEVEL_1("test.txt", "0001");

    private String Url;
    private String password;

    Levels(String Url, String password){
        this.Url = Url;
        this.password = password;
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

    public String getUrl() {
        return Url;
    }
}
