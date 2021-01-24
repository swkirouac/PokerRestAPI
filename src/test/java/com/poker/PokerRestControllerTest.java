package com.poker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PokerRestControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void createGame() {
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/games"), HttpMethod.POST, null, String.class);

        Assertions.assertEquals("0", response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @AfterEach
    public void deleteGame() {
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0"), HttpMethod.DELETE, null, String.class);

        Assertions.assertEquals("0", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllGames() {
        // Given
        restTemplate.exchange(createURLWithPort("/games"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/games"), HttpMethod.POST, null, String.class);

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/games"), HttpMethod.GET, null, String.class);

        // Then
        Assertions.assertEquals("[0,1,2]", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteGameThatDoesNotExist() {
        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/1"), HttpMethod.DELETE, null, String.class);

        // Then
        Assertions.assertEquals("Error: Game does not exist", response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddPlayer() {
        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);

        // Then
        Assertions.assertEquals("Bob", response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testPlayerThatAlreadyExists() {
        // When
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);

        // Then
        Assertions.assertEquals("Error: Player already exists", response.getBody());
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void testDeletePlayer() {
        // When
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.DELETE, null, String.class);

        // Then
        Assertions.assertEquals("Bob", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeletePlayerThatDoesNotExists() {
        // When
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Patrick"), HttpMethod.DELETE, null, String.class);

        // Then
        Assertions.assertEquals("Error: Player does not exist", response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDealCardToPlayers() {
        // Given
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDealTooManyCards() {
        // Given
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/addDeck"), HttpMethod.POST, null, String.class);

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);
        for (int i = 1; i<=104; i++) {
            response = restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);
        }

        // Then
        Assertions.assertEquals("Error: No more cards to deal", response.getBody());
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void testSortedGetListOfPlayers() {
        // Given
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/player/Patrick"), HttpMethod.POST, null, String.class);

        for (int i=0; i<8; i++) {
            restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);
            restTemplate.exchange(createURLWithPort("/game/0/player/Patrick/dealCard"), HttpMethod.POST, null, String.class);
        }

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/players"), HttpMethod.GET, null, String.class);

        // Then
        Assertions.assertEquals("{\"Bob\":51,\"Patrick\":46}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetListOfCardOfAPlayer() {
        // Given
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/player/Patrick"), HttpMethod.POST, null, String.class);

        for (int i=0; i<8; i++) {
            restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);
            restTemplate.exchange(createURLWithPort("/game/0/player/Patrick/dealCard"), HttpMethod.POST, null, String.class);
        }

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/player/Patrick"), HttpMethod.GET, null, String.class);

        // Then
        Assertions.assertEquals("[\"2 Diamonds\",\"4 Diamonds\",\"6 Diamonds\",\"8 Diamonds\",\"10 Diamonds\",\"Queen Diamonds\",\"Ace Clubs\",\"3 Clubs\"]", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCountOfUndealtCardsPerSuit() {
        // Given
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/player/Patrick"), HttpMethod.POST, null, String.class);

        for (int i=0; i<13; i++) {
            restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);
            restTemplate.exchange(createURLWithPort("/game/0/player/Patrick/dealCard"), HttpMethod.POST, null, String.class);
        }
        restTemplate.exchange(createURLWithPort("/game/0/addDeck"), HttpMethod.POST, null, String.class);

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/suitCount"), HttpMethod.GET, null, String.class);

        // Then
        Assertions.assertEquals("{\"Spades\":26,\"Hearts\":26,\"Diamonds\":13,\"Clubs\":13}", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetOrderedUndealtCardsPerSuit() {
        // Given
        restTemplate.exchange(createURLWithPort("/game/0/player/Bob"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/player/Patrick"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/player/Sandy"), HttpMethod.POST, null, String.class);
        restTemplate.exchange(createURLWithPort("/game/0/player/Pearl"), HttpMethod.POST, null, String.class);

        for (int i=0; i<13; i++) {
            restTemplate.exchange(createURLWithPort("/game/0/player/Bob/dealCard"), HttpMethod.POST, null, String.class);
            restTemplate.exchange(createURLWithPort("/game/0/player/Patrick/dealCard"), HttpMethod.POST, null, String.class);
            restTemplate.exchange(createURLWithPort("/game/0/player/Sandy/dealCard"), HttpMethod.POST, null, String.class);
            restTemplate.exchange(createURLWithPort("/game/0/player/Pearl/dealCard"), HttpMethod.POST, null, String.class);
        }
        restTemplate.exchange(createURLWithPort("/game/0/player/Sandy"), HttpMethod.DELETE, null, String.class);

        // When
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/game/0/sortedSuit"), HttpMethod.GET, null, String.class);

        // Then
        Assertions.assertEquals("[\"King Hearts\",\"9 Hearts\",\"5 Hearts\",\"Ace Hearts\"," +
                                        "\"Queen Spades\",\"8 Spades\",\"4 Spades\"," +
                                        "\"10 Clubs\",\"6 Clubs\",\"2 Clubs\"," +
                                        "\"Jack Diamonds\",\"7 Diamonds\",\"3 Diamonds\"]", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
