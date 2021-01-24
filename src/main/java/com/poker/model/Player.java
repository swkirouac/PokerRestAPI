package com.poker.model;

import java.util.ArrayList;

public class Player {
    private final String name;
    private final ArrayList<Card> cards;

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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            Player element = (Player) obj;
            return this.name.equals(element.name);
        }
        return false;
    }
}
