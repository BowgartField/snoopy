package fr.manchez.snoopy.application.models.sauvegarde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
     * @param window Snoopywindow
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

                if(file.canRead() && file.canWrite()){

                    ObjectMapper objectMapper = new ObjectMapper();

                    players = objectMapper.readValue(file, new TypeReference<List<Player>>(){});

                }else{

                    System.out.println("Impossible de lire ou écrire");

                }

            }else{

                //le fichier de sauvegarde n'existe pas
                //On charge un nouveau joueur vierge
                players.add(new Player(PlayersType.PLAYER1));
                players.add(new Player(PlayersType.PLAYER2));

                save();


            }

        }catch (IOException e){

            System.out.println(e.getMessage());

        }

    }

    /**
     * Sauvegarde les joueur dans un fichier
     */
    public void save(){

        ObjectMapper objectMapper = new ObjectMapper();


        File file = new File(fileName);

        try{

            objectMapper.writeValue(file, players);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Réinitialise le joueur
     */
    public void reset(){

        if(window.getPlayersType() == PlayersType.PLAYER1){
            players.get(0).reset();

        }else{
            players.get(1).reset();
        }

        save();

    }

    /**
     * Reset All
     */
    public void resetAll(){

        if(window.getPlayersType() == PlayersType.PLAYER1){
            players.get(0).resetAll();

        }else{
            players.get(1).resetAll();
        }

        save();

    }

    /**
     * Retourne le joueur actuel
     * @return Joueur actuel
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

}
