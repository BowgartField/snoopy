package fr.manchez.snoopy.application.enums;

public enum Displays{

    StartDisplay("startMenu.png"),
    GameDisplay("gameMenu.png"),
    PasswordDisplay("passwordMenu.png"),
    LevelDisplay(""),
    LevelEndDisplay("levelEnd.png"),
    LooseDisplay("loose.png");

    String backgroundURL;

    Displays(String backgroundURL){
        this.backgroundURL = backgroundURL;
    }

    public String getBackgroundURL() {
        return backgroundURL;
    }
}
