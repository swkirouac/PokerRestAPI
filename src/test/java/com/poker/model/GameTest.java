package com.poker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameTest {

    @Test
    void testAddPlayer() {
        // Given
        Game game = new Game();

        // When
        game.addPlayer("Bob");
        game.addPlayer("Patrick");

        // Then
        List<Player> players = game.getSortedListOfPlayers();
        String playerString = players.get(0).getName() + "&" + players.get(1).getName();
        Assertions.assertEquals("Bob&Patrick", playerString);
        Assertions.assertEquals(2, players.size());
    }

    @Test
    void testRemovePlayer() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");
        game.addPlayer("Patrick");

        // When
        game.removePlayer("Bob");

        // Then
        List<Player> players = game.getSortedListOfPlayers();
        Assertions.assertEquals(1, players.size());
        Assertions.assertEquals("Patrick", players.get(0).getName());

    }

    @Test
    void testDealCardToPlayer() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");

        // When
        game.dealCard("Bob");

        // Then
        Assertions.assertEquals(51, game.getUndealtCardsSortedBySuits().size());
        Assertions.assertEquals(1, game.getListOfCardForAPlayer("Bob").size());
    }

    @Test
    void testRemovePlayerWithCards() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");
        game.addPlayer("Patrick");
        game.dealCard("Bob");

        // When
        game.removePlayer("Bob");

        // Then
        List<Player> players = game.getSortedListOfPlayers();
        Assertions.assertEquals(1, players.size());
        Assertions.assertEquals("Patrick", players.get(0).getName());
        Assertions.assertEquals(52, game.getUndealtCardsSortedBySuits().size());
    }

    @Test
    void testDealCardToUnknownPlayer() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");

        // When
        game.dealCard("Patrick");

        // Then
        Assertions.assertEquals(52, game.getUndealtCardsSortedBySuits().size());
        Assertions.assertEquals(0, game.getListOfCardForAPlayer("Bob").size());
    }

    @Test
    void testRemoveUnknownPlayer() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");

        // When
        game.removePlayer("Patrick");

        // Then
        List<Player> players = game.getSortedListOfPlayers();
        Assertions.assertEquals(1, players.size());
        Assertions.assertEquals("Bob", players.get(0).getName());
    }

    @Test
    void testAddDeckToShoe() {
        // Given
        Game game = new Game();

        // When
        game.addDeckToShoe();

        // Then
        Assertions.assertEquals(104, game.getUndealtCardsSortedBySuits().size());
    }

    @Test
    void testShuffleShoe() {
        // Given
        Game game1 = new Game();
        Game game2 = new Game();
        game1.addPlayer("Bob");
        game2.addPlayer("Bob");

        // When
        game1.shuffleShoe();
        for (int i=0; i<8; i++) {
            game1.dealCard("Bob");
            game2.dealCard("Bob");
        }

        // Then
        Assertions.assertEquals(8, game1.getListOfCardForAPlayer("Bob").size());
        Assertions.assertEquals(8, game2.getListOfCardForAPlayer("Bob").size());
        Assertions.assertNotEquals(game1.getSortedListOfPlayers().get(0).getCardsValue(),
                                   game2.getSortedListOfPlayers().get(0).getCardsValue());
    }

    @Test
    void testGetSortedListOfPlayers() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");
        game.addPlayer("Patrick");

        // When
        for (int i=0; i<8; i++) {
            game.dealCard("Bob");
            game.dealCard("Patrick");
        }

        // Then
        Assertions.assertEquals(8, game.getListOfCardForAPlayer("Bob").size());
        Assertions.assertEquals(8, game.getListOfCardForAPlayer("Patrick").size());
        Assertions.assertEquals("Bob", game.getSortedListOfPlayers().get(0).getName());
        Assertions.assertEquals("Patrick", game.getSortedListOfPlayers().get(1).getName());
        Assertions.assertEquals(51, game.getSortedListOfPlayers().get(0).getCardsValue());
        Assertions.assertEquals(46, game.getSortedListOfPlayers().get(1).getCardsValue());
    }

    @Test
    void testGetUndealtCardsBySuits() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");
        game.addPlayer("Patrick");
        game.addPlayer("Sandy");
        game.addPlayer("Gary");

        // When
        for (int i=0; i<13; i++) {
            game.dealCard("Bob");
            game.dealCard("Patrick");
            game.dealCard("Sandy");
            game.dealCard("Gary");
        }
        game.removePlayer("Gary");

        // Then
        HashMap<String, Integer> cardMap = game.getUndealtCardsBySuits();
        Assertions.assertEquals(3, cardMap.get("Hearts"));
        Assertions.assertEquals(4, cardMap.get("Spades"));
        Assertions.assertEquals(3, cardMap.get("Clubs"));
        Assertions.assertEquals(3, cardMap.get("Diamonds"));
    }

    @Test
    void testGetUndealtCardsSortedBySuits() {
        // Given
        Game game = new Game();
        game.addPlayer("Bob");
        game.addPlayer("Patrick");
        game.addPlayer("Sandy");
        game.addPlayer("Gary");

        // When
        for (int i=0; i<13; i++) {
            game.dealCard("Bob");
            game.dealCard("Patrick");
            game.dealCard("Sandy");
            game.dealCard("Gary");
        }
        game.removePlayer("Gary");

        // Then
        ArrayList<Card> cards = game.getUndealtCardsSortedBySuits();
        String expected = "Hearts 10, Hearts 6, Hearts 2, " +
                "Spades King, Spades 9, Spades 5, Spades Ace, " +
                "Clubs Jack, Clubs 7, Clubs 3, " +
                "Diamonds Queen, Diamonds 8, Diamonds 4, ";
        StringBuilder result = new StringBuilder();
        for (Card card : cards) {
            result.append(card.getSuitName()).append(" ").append(card.getRankName()).append(", ");
        }
        Assertions.assertEquals(13, cards.size());
        Assertions.assertEquals(expected, result.toString());
    }
}
