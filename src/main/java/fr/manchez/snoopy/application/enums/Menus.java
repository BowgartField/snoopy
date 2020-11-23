package fr.manchez.snoopy.application.enums;

public enum Menus {

    StartMenu("startMenu.png"),
    GameMenu("gameMenu.png"),
    PasswordMenu("passwordMenu.png");

    String url;

    Menus(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
