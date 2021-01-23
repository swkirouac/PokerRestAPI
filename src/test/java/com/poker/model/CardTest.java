package com.poker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    void testRankNames() {
        Assertions.assertEquals("Ace", new Card(Card.Suits.DIAMONDS, 1).getRankName());
        Assertions.assertEquals("2", new Card(Card.Suits.DIAMONDS, 2).getRankName());
        Assertions.assertEquals("3", new Card(Card.Suits.DIAMONDS, 3).getRankName());
        Assertions.assertEquals("4", new Card(Card.Suits.DIAMONDS, 4).getRankName());
        Assertions.assertEquals("5", new Card(Card.Suits.DIAMONDS, 5).getRankName());
        Assertions.assertEquals("6", new Card(Card.Suits.DIAMONDS, 6).getRankName());
        Assertions.assertEquals("7", new Card(Card.Suits.DIAMONDS, 7).getRankName());
        Assertions.assertEquals("8", new Card(Card.Suits.DIAMONDS, 8).getRankName());
        Assertions.assertEquals("9", new Card(Card.Suits.DIAMONDS, 9).getRankName());
        Assertions.assertEquals("10", new Card(Card.Suits.DIAMONDS, 10).getRankName());
        Assertions.assertEquals("Jack", new Card(Card.Suits.DIAMONDS, 11).getRankName());
        Assertions.assertEquals("Queen", new Card(Card.Suits.DIAMONDS, 12).getRankName());
        Assertions.assertEquals("King", new Card(Card.Suits.DIAMONDS, 13).getRankName());
    }

    @Test
    void testSuitNames() {
        Assertions.assertEquals("Diamonds", new Card(Card.Suits.DIAMONDS, 1).getSuitName());
        Assertions.assertEquals("Spades", new Card(Card.Suits.SPADES, 2).getSuitName());
        Assertions.assertEquals("Hearts", new Card(Card.Suits.HEARTS, 3).getSuitName());
        Assertions.assertEquals("Clubs", new Card(Card.Suits.CLUBS, 4).getSuitName());
    }
}
