# PokerRestAPI
A Basic Deck of Cards Game

### Create a new game

POST /games

* **Success Response:**
  * **Code:** 201 <br />
  **Content:** `{ "id" : {id} }`

### Delete a game

DELETE /game/{id}

* **Success Response:**
  * **Code:** 200 <br />
  **Content:** `{ "id" : {id} }`

  
* **Error Response:**
  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

### View all games

GET /games

* **Success Response:**
  * **Code:** 200 <br />
  **Content:** `{ "Games id" : [1, 2, 5, 6] }`

### Add a deck to a game shoe

POST /game/{id}/addDeck

* **Success Response:**
  * **Code:** 201 <br />

  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

### Add player to a game

POST /game/{id}/player/{name}

* **Success Response:**
  * **Code:** 201 <br />
    **Content:** `{ "player" : "{name}" }`

  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

  OR

  * **Code:** 406 <br />
    **Content:** `{ error : "{name} already exists" }`


### Remove player from a game

DELETE /game/{id}/player/{name}

* **Success Response:**
  * **Code:** 200 <br />
  **Content:** `{ "player" : "{name}" }`

  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

  OR

  * **Code:** 406 <br />
    **Content:** `{ error : "{name} already exists" }`

### Deal card to a player

POST /game/{id}/player/{name}/dealCard

* **Success Response:**
  * **Code:** 200 <br />

  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

  OR

  * **Code:** 406 <br />
    **Content:** `{ error : "{name} already exists" }`

### Get list of cards for a player

GET /game/{id}/player/{name}

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ "player" : "{name}", "cards" : [ "Ace Spades", "5 Diamonds" ] }`

  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

  OR

  * **Code:** 406 <br />
    **Content:** `{ error : "{name} already exists" }`

### Get the list of players in descending card values order

GET /game/{id}/players

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ "players": [ {"player":{name}, "cardsValue":{value}}, ...]}`

  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`


### Get undealt cards count per suits

GET /game/{id}/suitCount

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ "suits": [ "4 Spades", "5 Diamonds" ]}`
  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

### Get undealt cards sorted per suits

GET /game/{id}/sortedSuit

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ "cards": [ "Ace Spades", "5 Diamonds" ]}`
  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`

### Shuffle game shoe
POST /game/{id}/shuffle

* **Success Response:**
  * **Code:** 200 <br />
  
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** `{ error : "{id} does not exist" }`