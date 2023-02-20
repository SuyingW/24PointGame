package Models;

import Models.Exceptions.DuplicatePlayerException;

import java.util.ArrayList;
import java.util.List;

public class WholeGame {
    private List<Player> players;
    private Game currentGame;

    public WholeGame() {
        this.players = new ArrayList<>();
        this.currentGame = new Game();
        this.currentGame.addtoWholeGame(this);
    }

    //REQUIRES: name
    //MODIFIES: this
    //EFFECTS: create a player with the given name and add the player to the game
    public void addPlayer(Player player) throws DuplicatePlayerException {
        Boolean duplicate = false;
        for (Player p : players) {
            if(p.getName().equals(player.getName())) {
                duplicate = true;
            }
        }
        if (!duplicate) {
            this.getPlayers().add(player);
        } else {
            throw new DuplicatePlayerException();
        }
    }

    //REQUIRES: the given player is in player list
    //MODIFIES: this
    //EFFECTS: remove the player with the given name
    public void removePlayer(Player player) {
        if (players.contains(player)) {
            this.players.remove(player);
        } else {
            System.out.print("User doesn't exist");
        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Game getCurrentGame() {
        return this.currentGame;
    }


    //EFFECTS: set current game and assign the whole game to the current game
    public void setCurrentGame(Game g) {
        if (this.currentGame != g) {
            this.currentGame = g;
            g.addtoWholeGame(this);
        }
    }

}
