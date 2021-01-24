package com.poker.controller;

import com.poker.model.Card;
import com.poker.model.Game;
import com.poker.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path="/")
public class PokerRestController {

    public static final int MAX_GAMES = 100;
    private HashMap<Integer, Game> games = new HashMap<>();

    private int findNextAvailableGameId() {
        int result = -1;
        for (int i=0; i<MAX_GAMES; i++) {
            if (!games.containsKey(i)) {
                return i;
            }
        }
        return result;
    }

    // Create a new game
    @PostMapping(path="/games")
    public ResponseEntity<String> addGame() {
        int id = findNextAvailableGameId();
        if (id != -1) {
            Game game = new Game();
            games.put(id, game);
            return new ResponseEntity<>(String.valueOf(id), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error: Maximum number of games reached", HttpStatus.INSUFFICIENT_STORAGE);
    }

    // Delete a game
    @DeleteMapping(path="/game/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable int id) {
        Game game = games.get(id);
        if (game != null) {
            games.remove(id);
            return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // See all games
    @GetMapping(path="/games")
    public ResponseEntity<Object> getGames() {
        return new ResponseEntity<>(new ArrayList<>(games.keySet()), HttpStatus.OK);
    }

    // Add a deck to a game shoe
    @PostMapping(path="/game/{id}/addDeck")
    public ResponseEntity<String> addDeckToGame(@PathVariable int id) {
        Game game = games.get(id);
        if (game != null) {
            game.addDeckToShoe();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Add player to a game
    @PostMapping(path="/game/{id}/player/{name}")
    public ResponseEntity<String> addPlayerToGame(@PathVariable int id, @PathVariable String name) {
        Game game = games.get(id);
        if (game != null) {
            if(game.addPlayer(name)){
                return new ResponseEntity<>(name, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Error: Player already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Remove player from a game
    @DeleteMapping(path="/game/{id}/player/{name}")
    public ResponseEntity<String> removePlayerFromGame(@PathVariable int id, @PathVariable String name) {
        Game game = games.get(id);
        if (game != null) {
            if(game.removePlayer(name)){
                return new ResponseEntity<>(name, HttpStatus.OK);
            }
            return new ResponseEntity<>("Error: Player does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Deal card to a player
    @PostMapping(path="/game/{id}/player/{name}/dealCard")
    public ResponseEntity<String> dealCardToPlayer(@PathVariable int id, @PathVariable String name) {
        Game game = games.get(id);
        if (game != null) {
            if (game.getSortedListOfPlayers().contains(new Player(name))) {
                if (game.dealCard(name)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>("Error: No more cards to deal", HttpStatus.METHOD_NOT_ALLOWED);
            }
            return new ResponseEntity<>("Error: Player does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Get list of cards for a player
    @GetMapping(path="/game/{id}/player/{name}")
    public ResponseEntity<Object> getPlayerCards(@PathVariable int id, @PathVariable String name) {
        Game game = games.get(id);
        if (game != null) {
            if (game.getSortedListOfPlayers().contains(new Player(name))) {
                ArrayList<String> response =  new ArrayList<String>();
                ArrayList<Card> cards = game.getListOfCardForAPlayer(name);
                for (Card card : cards) {
                    response.add(card.getRankName() + " " + card.getSuitName());
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>("Error: Player does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Get the list of players in descending card values order
    @GetMapping(path="/game/{id}/players")
    public ResponseEntity<Object> getListOfPlayers(@PathVariable int id) {
        Game game = games.get(id);
        if (game != null) {
            LinkedHashMap<String, Integer> response = new LinkedHashMap<>();
            ArrayList<Player> players = game.getSortedListOfPlayers();
            for(Player player : players) {
                response.put(player.getName(), player.getCardsValue());
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Get undealt cards count per suits
    @GetMapping(path="/game/{id}/suitCount")
    public ResponseEntity<Object> getUndealtCardsCountPerSuit(@PathVariable int id) {
        Game game = games.get(id);
        if (game != null) {
            return new ResponseEntity<>(game.getUndealtCardsBySuits(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Get undealt cards sorted per suits
    @GetMapping(path="/game/{id}/sortedSuit")
    public ResponseEntity<Object> getUndealtCardsSortedPerSuit(@PathVariable int id) {
        Game game = games.get(id);
        if (game != null) {
            ArrayList<String> response = new ArrayList<>();
            ArrayList<Card> cards = game.getUndealtCardsSortedBySuits();
            for (Card card : cards) {
                response.add(card.getRankName() + " " + card.getSuitName());
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

    // Shuffle game shoe
    @PostMapping(path="/game/{id}/shuffle")
    public ResponseEntity<String> shuffleGameShoe(@PathVariable int id) {

        Game game = games.get(id);
        if (game != null) {
            game.shuffleShoe();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Game does not exist", HttpStatus.NOT_FOUND);
    }

}
