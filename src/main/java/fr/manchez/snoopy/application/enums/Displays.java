package fr.manchez.snoopy.application.enums;

public enum Displays{

    StartDisplay("startMenu.png"),
    GameDisplay("gameMenu.png"),
    PasswordDisplay("passwordMenu.png"),
    LevelDisplay("");

    String backgroundURL;

    Displays(String backgroundURL){
        this.backgroundURL = backgroundURL;
    }

    public String getBackgroundURL() {
        return backgroundURL;
    }
}
