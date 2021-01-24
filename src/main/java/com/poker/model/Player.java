package com.poker.model;

import java.util.ArrayList;

/* Player class
* Represent a player in a poker game.
* The player is identified by it`s name and it can hold cards
* If a player quits the game, don`t forget to take back his/her cards
* */
public class Player {

    /* Private members */
    private final String name;
    private final ArrayList<Card> cards;

    /* Public members */
    public Player(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void dealCardToPlayer(Card card) {
        cards.add(card);
    }

    // Calculate the sum of the face values of the player cards
    // The suit have no effect on card value
    public int getCardsValue() {
        int totalCardsValue = 0;
        for (Card card : cards) {
            totalCardsValue += card.getRank();
        }
        return totalCardsValue;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    // Override equals to be able to identify if a player
    // is trying to play more than 1 hand in the same game
    // That would not be fair...
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            Player element = (Player) obj;
            return this.name.equals(element.name);
        }
        return false;
    }
}
