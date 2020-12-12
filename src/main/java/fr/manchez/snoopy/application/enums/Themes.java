package fr.manchez.snoopy.application.enums;

import java.util.Random;

public enum Themes{

    MAIN_THEME("titleTheme.mp3"),
    WIN_THEME("Jingle#01.mp3"),

    LEVEL_THEME1("BGM#02.mp3"),
    LEVEL_THEME2("BGM#03.mp3"),
    LEVEL_THEME3("BGM#04.mp3"),
    LEVEL_THEME4("BGM#05.mp3"),
    LEVEL_THEME5("BGM#06.mp3"),
    LEVEL_THEME6("BGM#07.mp3"),
    LEVEL_THEME7("BGM#08.mp3"),
    LEVEL_THEME8("BGM#09.mp3"),
    LEVEL_THEME9("BGM#10.mp3"),
    LEVEL_THEME10("BGM#11.mp3"),
    LEVEL_THEME11("BGM#12.mp3"),
    LEVEL_THEME12("BGM#13.mp3"),
    LEVEL_THEME13("BGM#14.mp3"),
    LEVEL_THEME14("BGM#15.mp3"),
    LEVEL_THEME15("BGM#16.mp3"),
    LEVEL_THEME16("BGM#17.mp3"),
    LEVEL_THEME17("BGM#18.mp3"),
    LEVEL_THEME18("BGM#19.mp3"),
    LEVEL_THEME19("BGM#20.mp3");


    String url;

    Themes(String url){
        this.url = url;
    }

    public static Themes getRandomTheme() {
        int pick;
        do{
            pick = new Random().nextInt(Themes.values().length);
        }while (Themes.values()[pick].equals(MAIN_THEME) || Themes.values()[pick].equals(WIN_THEME));

        return Themes.values()[pick];
    }

    public String getUrl() {
        return url;
    }
}
