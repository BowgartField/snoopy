package fr.manchez.snoopy.application.enums;

public enum PlayersType {

    PLAYER1("player1"),
    PLAYER2("player2");

    String url;

    PlayersType(String url){

        this.url = url;

    }

    public String getUrl() {
        return url;
    }
}
