package fr.manchez.snoopy.application.enums;

public enum Level {

    MAIN("test.txt"),
    LEVEL_1("");

    private String Url;

    Level(String Url){
        this.Url = Url;
    }

    @Override
    public String toString() {
        return this.Url;
    }
}
