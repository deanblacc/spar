Spar is a card game (from ghana). This project I am playing around with dropwizard for my REST service and rabbitmq for messaging.

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
curl -XGET https://localhost:9000/{game}
```

###Join game
```bash
curl -XPOST https://localhost:9000/{gameid}/players
{
  "username":"Player_Username"
}
```

###Forfeit game
```bash
curl -XDELETE https://localhost:9000/{gameid}/players
{
  "username":"Player_Username"
}
```

###Start game
```bash
curl -XPUT https://localhost:9000/{gameid}
```

###Show player cards
```bash
curl -XGET https://localhost:9000/{gameid}/players/{playerid}/cards
```

###Play card
```bash
curl -XPUT https://localhost:9000/{gameid}/players/{playerid}/cards/{playedCard}
```

###Show player in game stats
```bash
curl -XGET https://localhost:9000/{game}/players/{player}
```
