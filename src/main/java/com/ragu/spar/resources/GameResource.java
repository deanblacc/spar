package com.ragu.spar.resources;

import com.ragu.spar.Card;
import com.ragu.spar.Game;
import com.ragu.spar.Player;
import com.ragu.spar.exceptions.SparException;
import com.ragu.spar.exceptions.SparExceptionMapper;

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
    public Game joinGame(@PathParam("gameid") String gameid, Player player) throws SparException {
        Game game = games.get(gameid);
        game.addPlayerToGame(player);
        return  game;
    }

    @DELETE
    @Path("{gameid}/players")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Game forfeitGame(@PathParam("gameid") String gameid, Player player) throws SparException, IOException {
        Game game = games.get(gameid);
        game.forfeit(player);
        return  game;
    }

    @PUT
    @Path("{gameid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(@PathParam("gameid")String gameid) {
        try {
            games.get(gameid).startGame();
            return  Response.status(200).entity(games.get(gameid).isGameStarted).build();
        } catch (SparException e) {
            return  Response.status(404).entity(games.get(gameid).isGameStarted).build();
        }
    }

    @GET
    @Path("{gameid}/players/{playerid}/cards")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> getCards(@PathParam("gameid")String gameid, @PathParam("playerid")int playerid){
        return games.get(gameid).getPlayers().get(playerid).getCards();
    }

    @PUT
    @Path("{gameid}/players/{playerid}/cards/{playedCard}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playCard(@PathParam("gameid")String gameid, @PathParam("playerid")int playerid,
                               @PathParam("playedCard") int playedCard) throws IOException {
        Player player = games.get(gameid).getPlayers().get(playerid);
        List<Card>cards = player.getCards();
        Card card;
        try {
            card = games.get(gameid).getCardFromIndex(cards,playedCard);
            games.get(gameid).playCard(player,card);
            return Response.status(200).entity(games.get(gameid).getPlayers().get(playerid).getCards()).build();
        } catch (SparException e) {
            return new SparExceptionMapper().toResponse(e);
        }
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
