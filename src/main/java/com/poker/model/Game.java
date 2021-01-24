package com.poker.model;

import java.util.*;

public class Game {
    private ArrayList<Player> players;
    private Shoe shoe;

    public Game() {
        this.players = new ArrayList<>();
        this.shoe = new Shoe();
    }

    public boolean addPlayer(String playerName) {
        if (players.contains(new Player(playerName))) {
            return false;
        }
        players.add(new Player(playerName));
        return true;
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
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Card> getListOfCardForAPlayer(String playerName) {
        int index = players.indexOf(new Player(playerName));
        if (index != -1) {
            return players.get(index).getCards();
        }
        return null;
    }

    public ArrayList<Player> getSortedListOfPlayers() {
        // Sorted players in descending orders
        players.sort((lhs, rhs) -> Integer.compare(rhs.getCardsValue(), lhs.getCardsValue()));

        return players;
    }

    public HashMap<String, Integer> getUndealtCardsBySuits() {

        HashMap<String, Integer> result = new HashMap<>();
        int diamonds = 0, hearts = 0, spades = 0, clubs = 0;
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

        result.put("Hearts", hearts);
        result.put("Spades", spades);
        result.put("Clubs", clubs);
        result.put("Diamonds", diamonds);

        return result;
    }

    public ArrayList<Card> getUndealtCardsSortedBySuits() {

        ArrayList<Card> cards = new ArrayList<>(shoe.getRemainingCards());

        // Sorted players in descending orders
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
