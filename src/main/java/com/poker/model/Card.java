package com.poker.model;

/* Card model class
   Used to represent any card in a card deck expect for jokers
* */
public class Card {

    /* Private members */
    private final Suits suit;
    private final int rank;

    /* Public members */
    public enum Suits {
        HEARTS, SPADES, CLUBS, DIAMONDS
    }

    public Card(Suits suit, int rank) throws IllegalArgumentException {
        if (rank < 1 || rank > 13) {
            throw new IllegalArgumentException("Card value should be between 1 and 13");
        }
        this.suit = suit;
        this.rank = rank;
    }

    public Suits getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }

    public String getRankName()
    {
        String name = "";
        switch(rank) {
            case 1:
                name = "Ace";
                break;
            case 11:
                name = "Jack";
                break;
            case 12:
                name = "Queen";
                break;
            case 13:
                name = "King";
                break;
            default:
                name = String.valueOf(rank);
        }
        return name;
    }

    public String getSuitName() {
        String name = "";
        switch(suit) {
            case DIAMONDS:
                name = "Diamonds";
                break;
            case CLUBS:
                name = "Clubs";
                break;
            case HEARTS:
                name = "Hearts";
                break;
            case SPADES:
                name = "Spades";
                break;
        }
        return name;
    }
}
