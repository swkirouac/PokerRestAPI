package com.poker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private String playerName = "X Æ A-12";;

    @Test
    void testPlayerCreation() {
        // Given
        Player player = new Player(playerName);

        // Then
        Assertions.assertEquals(playerName, player.getName());
        Assertions.assertEquals(0, player.getCardsValue());
        Assertions.assertEquals(0, player.getCards().size());
    }

    @Test
    void testDealCard() {
        // Given
        Player player = new Player(playerName);

        // When
        player.dealCardToPlayer(new Card(Card.Suits.DIAMONDS, 2));
        player.dealCardToPlayer(new Card(Card.Suits.HEARTS, 8));
        player.dealCardToPlayer(new Card(Card.Suits.SPADES, 13));

        // Then
        Assertions.assertEquals(2+8+13, player.getCardsValue());
        Assertions.assertEquals(3, player.getCards().size());
    }
}
