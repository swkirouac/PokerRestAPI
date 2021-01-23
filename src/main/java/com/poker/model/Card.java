package com.poker.model;

public class Card {
    public enum Suits {
        HEARTS, SPADES, CLUBS, DIAMONDS
    }

    private Suits suit;
    private int rank;

    public Card(Suits suit, int rank) {
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
