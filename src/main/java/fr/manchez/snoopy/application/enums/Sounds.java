package fr.manchez.snoopy.application.enums;

public enum Sounds {

    MAIN_THEME("titleTheme.mp3"),
    BIRD_CATCH("birdCatch.mp3"),
    BLOC_BREAK("destroyBlock.mp3"),
    SCORE("score.mp3");

    String url;

    Sounds(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
