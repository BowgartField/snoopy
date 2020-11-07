package fr.manchez.snoopy.application.enums;

public enum Levels {

    MAIN("test.txt"),
    LEVEL_1("");

    private String Url;

    Levels(String Url){
        this.Url = Url;
    }

    @Override
    public String toString() {
        return this.Url;
    }
}
