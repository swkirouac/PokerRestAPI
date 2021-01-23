package com.poker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Shoe {
    private ArrayList<Card> cards;

    // Create one deck in the shoe
    private void Create1Deck() {
        for(int i = 1; i <= 13; i++) {
            cards.add(new Card(Card.Suits.DIAMONDS, i));
        }
        for(int i = 1; i <= 13; i++) {
            cards.add(new Card(Card.Suits.CLUBS, i));
        }
        for(int i = 1; i <= 13; i++) {
            cards.add(new Card(Card.Suits.HEARTS, i));
        }
        for(int i = 1; i <= 13; i++) {
            cards.add(new Card(Card.Suits.SPADES, i));
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public Shoe() {
        cards = new ArrayList<Card>();
        Create1Deck();
    }

    public void addDeck() {
        Create1Deck();
    }

    public void addCardsToShoe(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }

    public ArrayList<Card> getRemainingCards() {
        return cards;
    }

    /* This function remove a card from the shoe.
       It should be added in the player list.
     */
    public Card dealCard() throws Exception {
        if (cards.size() == 0)
            throw new Exception("No more cards to deal");

        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

    public void shuffle() {
        ArrayList<Card> shuffledCards = new ArrayList<Card>();
        while (!cards.isEmpty()) {
            int location = (int)(Math.random()*cards.size());
            shuffledCards.add(cards.get(location));
            cards.remove(location);
        }
        cards = shuffledCards;
    }
}
