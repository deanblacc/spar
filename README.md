#Spar

Spar is a card game (from ghana). Wrote this to learn how to design RESTful system. So far written only the server side code. Client side code for iOS, Android and the web is yet to be written.

##Technologies Used
#### Dropwizard 
* RESTful web service for java. Simple and easy to use.

#### RabbitMq
* Messaging bus. Used to send messages between devices. Chose this messaging platform because it has an extensive client library for different languages

#### Gradle
* Build tool. Cleaner the and just better than maven (IMHO)

##How to use

###Create new game

```bash
curl -XPOST https://localhost:9000/games
{
  "username":"Player_Username"
}
```


###Get all games
```bash
curl -XGET https://localhost:9000/games
```

###Show game status
```bash
curl -XGET https://localhost:9000/games/{gameid}
```

###Join game
```bash
curl -XPOST https://localhost:9000/games/{gameid}/players
{
  "username":"Player_Username"
}
```

###Forfeit game
```bash
curl -XDELETE https://localhost:9000/games/{gameid}/players
{
  "username":"Player_Username"
}
```

###Start game
```bash
curl -XPUT https://localhost:9000/games/{gameid}
```

###Show player cards
```bash
curl -XGET https://localhost:9000/games/{gameid}/players/{playerid}/cards
```

###Play card
```bash
curl -XPUT https://localhost:9000/games/{gameid}/players/{playerid}/cards/{playedCard}
```

###Show player in game stats
```bash
curl -XGET https://localhost:9000/games/{game}/players/{player}
```
