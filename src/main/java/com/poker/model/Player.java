package com.poker.model;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;

    public Player(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
    }

    public String getName() {
        return name;
    }

    public void dealCardToPlayer(Card card) {
        cards.add(card);
    }

    public int getCardsValue() {
        int totalCardsValue = 0;
        for (int i=0; i<cards.size(); i++) {
            totalCardsValue += cards.get(i).getRank();
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
            if(element != null && this.name.equals(element.name)){
                return true;
            }
        }
        return false;
    }
}
