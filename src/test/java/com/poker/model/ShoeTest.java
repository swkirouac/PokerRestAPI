package com.poker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoeTest {

    @Test
    void testShoeCreation() {
        // Verify there`s 52 cards
        Assertions.assertEquals(52, new Shoe().getRemainingCards().size());
    }

    @Test
    void testAddDeck() {
        // Given
        Shoe shoe = new Shoe();

        // When
        shoe.addDeck();

        // Then
        Assertions.assertEquals(104, shoe.getRemainingCards().size());
    }

    @Test
    void testDeal1Card() throws Exception {
        // Given
        Shoe shoe = new Shoe();

        // When
        Card card = shoe.dealCard();

        // Then
        Assertions.assertEquals(51, shoe.getRemainingCards().size());
        Assertions.assertFalse(shoe.getRemainingCards().contains(card));
    }

    @Test
    void testDeal53Card() throws Exception {
        // Given
        Shoe shoe = new Shoe();

        // When
        Card card;
        for (int i = 0; i < 52; i++) {
            card = shoe.dealCard();
        }
        Exception e = assertThrows(Exception.class, shoe::dealCard);

        // Then
        Assertions.assertEquals(0, shoe.getRemainingCards().size());
        Assertions.assertEquals("No more cards to deal", e.getMessage());
    }

    @Test
    void testNotShuffle() {
        // Given
        Shoe shoe1 = new Shoe();
        Shoe shoe2 = new Shoe();

        // When ... nothing

        // Then
        StringBuilder shoe1String = new StringBuilder();
        StringBuilder shoe2String = new StringBuilder();
        for (int i = 0; i < shoe1.getRemainingCards().size(); i++) {
            shoe1String.append(" ").append(shoe1.getRemainingCards().get(i).getRankName());
            shoe2String.append(" ").append(shoe2.getRemainingCards().get(i).getRankName());
        }
        Assertions.assertEquals(shoe1String.toString(), shoe2String.toString());
    }

    @Test
    void testShuffle() {
        // Given
        Shoe shoe1 = new Shoe();
        Shoe shoe2 = new Shoe();

        // When
        shoe1.shuffle();

        // Then
        StringBuilder shoe1String = new StringBuilder();
        StringBuilder shoe2String = new StringBuilder();
        for (int i = 0; i < shoe1.getRemainingCards().size(); i++) {
            shoe1String.append(" ").append(shoe1.getRemainingCards().get(i).getRankName());
            shoe2String.append(" ").append(shoe2.getRemainingCards().get(i).getRankName());
        }
        Assertions.assertNotEquals(shoe1String.toString(), shoe2String.toString());
    }

    @Test
    void testDealAndAddCards() throws Exception {
        // Given
        Shoe shoe = new Shoe();

        // When
        Card card = shoe.dealCard();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card);
        shoe.addCardsToShoe(cards);

        // Then
        Assertions.assertEquals(52, shoe.getRemainingCards().size());
        Assertions.assertTrue(shoe.getRemainingCards().contains(card));
    }
}
