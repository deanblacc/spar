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

    @PUT
    @Path("{game}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Game joinGame(@PathParam("game") String game,Player player){
        Game gameObject = games.get(game);
        gameObject.addPlayerToGame(player);
        return  gameObject;
    }

    @GET
    @Path("{game}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game getGame(@PathParam("game") String game){
        return games.get(game);
    }

    @GET
    @Path("{game}/players/{player}/cards")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> getCards(@PathParam("game")String game, @PathParam("player")int player){
        return games.get(game).getPlayers()[player].obtainCards();
    }

    @GET
    @Path("{game}/players/{player}")
    @Produces(MediaType.APPLICATION_JSON)
    public Player getPlayer(@PathParam("game")String game, @PathParam("player")int player){
        return games.get(game).getPlayers()[player];
    }
}
