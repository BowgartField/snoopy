package fr.manchez.snoopy.application.models.sauvegarde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.manchez.snoopy.application.Main;
import fr.manchez.snoopy.application.SnoopyWindow;
import fr.manchez.snoopy.application.enums.PlayersType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sauvegarde {

    /** window **/
    SnoopyWindow window;

    /** Nom du fichier de sauvegarde **/
    String fileName = "save.json";

    /** Liste des joueurs **/
    List<Player> players = new ArrayList<>();

    /**
     * Constructeur
     */
    public Sauvegarde(SnoopyWindow window){

        this.window = window;
        load();

    }

    /**
     * Charge les joueurs
     */
    private void load(){

        File file = new File(fileName);

        try{

            //le fichier de sauvegarde existe
            if(!file.createNewFile()){

                ObjectMapper objectMapper = new ObjectMapper();

                players = objectMapper.readValue(file, new TypeReference<List<Player>>(){});

            }else{

                //le fichier de sauvegarde n'existe pas
                //On charge un nouveau joueur vierge
                players.add(new Player(PlayersType.PLAYER1));
                players.add(new Player(PlayersType.PLAYER2));

            }

        }catch (IOException e){

            e.printStackTrace();

        }

    }

    /**
     * Sauvegarde les joueur dans un fichier
     */
    public void save(){

        List<Player> playerList = new ArrayList<>();

        playerList.add(new Player(PlayersType.PLAYER1));
        playerList.add(new Player(PlayersType.PLAYER2));

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(fileName);

        try{

            objectMapper.writeValue(file, playerList);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Retourne le joueur choisi
     */
    public Player getPlayer(){

        Player result = null;

        for (Player player: players){

            if(player.getPlayer() == window.getPlayersType()){
                result = player;
            }

        }

        return result;

    }

    /**
     * Retourne la Liste des joueurs
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }
}
