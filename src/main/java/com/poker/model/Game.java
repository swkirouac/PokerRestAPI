package com.poker.model;

import java.util.*;

/* Basic poker game class
*  This class allows to add/remove players from a poker game.
*  Decks can be added and shuffled
*  The class also makes available the current score and undealt cards
* */
public class Game {

    /* Private members */
    private final ArrayList<Player> players;
    private final Shoe shoe;

    /* Public members */
    public Game() {
        this.players = new ArrayList<>();
        this.shoe = new Shoe();
    }

    public boolean addPlayer(String playerName) {
        if (!players.contains(new Player(playerName))) {
            players.add(new Player(playerName));
            return true;
        }
        // Return false if the player already exists
        return false;
    }

    public boolean removePlayer(String playerName) {
        int index = players.indexOf(new Player(playerName));
        if (index != -1) {
            Player player = players.get(players.indexOf(new Player(playerName)));
            ArrayList<Card> cards = player.getCards();
            shoe.addCardsToShoe(cards);
            players.remove(player);
            return true;
        }
        // Return false if the player does not exists
        return false;
    }

    public void addDeckToShoe() {
        shoe.addDeck();
    }

    public void shuffleShoe() {
        shoe.shuffle();
    }

    public boolean dealCard(String playerName) {
        try{
            int index = players.indexOf(new Player(playerName));
            if (index != -1) {
                Card card = shoe.dealCard();
                players.get(index).dealCardToPlayer(card);
                return true;
            }
            // Return false is player does not exist
            return false;
        } catch (IndexOutOfBoundsException e) {
            // Return false if there`s no more cards available to deal
            return false;
        }
    }

    public ArrayList<Card> getListOfCardForAPlayer(String playerName) {
        int index = players.indexOf(new Player(playerName));
        if (index != -1) {
            return players.get(index).getCards();
        }
        // Return null if player does not exist
        return null;
    }

    public ArrayList<Player> getSortedListOfPlayers() {
        // Sorted players in descending orders (best hand of cards first)
        players.sort((lhs, rhs) -> Integer.compare(rhs.getCardsValue(), lhs.getCardsValue()));
        return players;
    }

    // Get the count of how many cards per suit are left
    // undealt in the game deck (example: 5 hearts, 3 spades, etc.)
    public LinkedHashMap<String, Integer> getUndealtCardsBySuits() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        int diamonds = 0, hearts = 0, spades = 0, clubs = 0;

        // Count number of cards in each suits
        for (Card card : shoe.getRemainingCards()) {
            switch(card.getSuit()) {
                case DIAMONDS:
                    diamonds++;
                    break;
                case CLUBS:
                    clubs++;
                    break;
                case HEARTS:
                    hearts++;
                    break;
                case SPADES:
                    spades++;
                    break;
            }
        }

        // Save result
        result.put("Hearts", hearts);
        result.put("Spades", spades);
        result.put("Clubs", clubs);
        result.put("Diamonds", diamonds);

        return result;
    }

    // Get the count of each card (suit and value) remaining in the game shoe
    // sorted by suit ( hearts, spades, clubs, and diamonds) and face value
    // from high value to low value (King, Queen, Jack, 10….2, Ace with value of 1)
    public ArrayList<Card> getUndealtCardsSortedBySuits() {

        ArrayList<Card> cards = new ArrayList<>(shoe.getRemainingCards());

        // Sorted cards in descending orders (King, Queen, Jack, 10, ..., Ace)
        cards.sort((lhs, rhs) -> {
            if (lhs.getSuit().ordinal() > rhs.getSuit().ordinal())
                return 1;
            else if (lhs.getSuit().ordinal() < rhs.getSuit().ordinal())
                return -1;
            else
                return Integer.compare(rhs.getRank(), lhs.getRank());
        });

        return cards;
    }
}
