package com.poker.model;

import java.util.*;

public class Game {
    private ArrayList<Player> players;
    private Shoe shoe;

    public Game() {
        this.players = new ArrayList<Player>();
        this.shoe = new Shoe();
    }

    public void addPlayer(String playerName) {
        players.add(new Player(playerName));
    }

    public void removePlayer(String playerName) {
        int index = players.indexOf(new Player(playerName));
        if (index != -1) {
            Player player = players.get(players.indexOf(new Player(playerName)));
            ArrayList<Card> cards = player.getCards();
            shoe.addCardsToShoe(cards);
            players.remove(player);
        }
    }

    public void addDeckToShoe() {
        shoe.addDeck();
    }

    public void shuffleShoe() {
        shoe.shuffle();
    }

    public void dealCard(String playerName) {
        try{
            int index = players.indexOf(new Player(playerName));
            if (index != -1) {
                Card card = shoe.dealCard();
                players.get(index).dealCardToPlayer(card);
            }
        } catch (Exception e) {

        }
    }

    public ArrayList<Card> getListOfCardForAPlayer(String playerName) {
        int index = players.indexOf(new Player(playerName));
        if (index != -1) {
            return players.get(index).getCards();
        }
        return new ArrayList<Card>();
    }

    public ArrayList<Player> getSortedListOfPlayers() {

        // Sorted players in descending orders
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player lhs, Player rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getCardsValue() > rhs.getCardsValue() ? -1 : (lhs.getCardsValue() < rhs.getCardsValue()) ? 1 : 0;
            }
        });

        return players;
    }

    public HashMap<String, Integer> getUndealtCardsBySuits() {

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        int diamonds = 0, hearts = 0, spades = 0, clubs = 0;
        List<Card> cards = shoe.getRemainingCards();
        for (int i=0; i<cards.size(); i++) {
            switch(cards.get(i).getSuit()) {
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

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0; i<shoe.getRemainingCards().size(); i++) {
            cards.add(shoe.getRemainingCards().get(i));
        }

        // Sorted players in descending orders
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card lhs, Card rhs) {
                if (lhs.getSuit().ordinal() > rhs.getSuit().ordinal())
                    return 1;
                else if (lhs.getSuit().ordinal() < rhs.getSuit().ordinal())
                    return -1;
                else
                    return lhs.getRank() > rhs.getRank() ? -1 : (lhs.getRank() < rhs.getRank()) ? 1 : 0;
            }
        });

        return cards;
    }
}
