package com.ragu.spar.resources;

import com.ragu.spar.Card;
import com.ragu.spar.Game;
import com.ragu.spar.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/games")
public class GameResource {
    Map<String,Game> games = new HashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(Player player) throws IOException {
        Game game = new Game(player);
        String id = game.getId().toString();
        games.put(id,game);
        return Response.status(201).entity(game).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAllGames(){
        return Response.status(200).entity(games).build();
    }

    @POST
    @Path("{gameid}/players")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Game joinGame(@PathParam("gameid") String gameid, Player player){
        Game game = games.get(gameid);
        game.addPlayerToGame(player);
        return  game;
    }

    @PUT
    @Path("{gameid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(@PathParam("gameid")String gameid){
        games.get(gameid).startGame();
        return  Response.status(200).entity(games.get(gameid).hasGameStarted).build();
    }

    @GET
    @Path("{game}/players/{player}/cards")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> getCards(@PathParam("game")String game, @PathParam("player")int player){
        return games.get(game).getPlayers().get(player).getCards();
    }

    @PUT
    @Path("{game}/players/{playerid}/cards/{playedCard}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> playCard(@PathParam("game")String game, @PathParam("playerid")int playerid,
                               @PathParam("playedCard") int playedCard) throws IOException {
        Player player = games.get(game).getPlayers().get(playerid);
        List<Card>cards = player.getCards();
        Card card = games.get(game).getCardFromIndex(cards,playedCard);
        games.get(game).playCard(player,card);
        return games.get(game).getPlayers().get(playerid).getCards();
    }

    @GET
    @Path("{game}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game getGame(@PathParam("game") String game){
        return games.get(game);
    }

    @GET
    @Path("{game}/players/{player}")
    @Produces(MediaType.APPLICATION_JSON)
    public Player getPlayer(@PathParam("game")String game, @PathParam("player")int player){
        return games.get(game).getPlayers().get(player);
    }
}
