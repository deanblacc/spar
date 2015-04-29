Spar is a card game (from ghana). This project I am playing around with dropwizard for my REST service and rabbitmq for messaging.

How to use
Create new game
curl -XPOST https://localhost:9000/games
{
  "username":"Player_Username"
}

Get all games
curl -XGET https://localhost:9000/games

Show game status
curl -XGET https://localhost:9000/{game}

Join game
curl -XPOST https://localhost:9000/{gameid}/players
{
  "username":"Player_Username"
}

Forfeit game
curl -XDELETE https://localhost:9000/{gameid}/players
{
  "username":"Player_Username"
}

Start game
curl -XPUT https://localhost:9000/{gameid}

Show player cards
curl -XGET https://localhost:9000/{gameid}/players/{playerid}/cards

Play card
curl -XPUT https://localhost:9000/{gameid}/players/{playerid}/cards/{playedCard}

Show player in game stats
curl -XGET https://localhost:9000/{game}/players/{player}
