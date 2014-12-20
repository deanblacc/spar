package com.ragu.spar.resources;

import com.ragu.spar.Player;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nana on 12/6/14.
 */
@Path("/players")
public class PlayerResource {
    Map<String,Player> players = new HashMap<>();

    @POST
    @Path("{player}")
    public Response createPlayer(String username) throws IOException {
        Player player = new Player(username);
        players.put(username,player);
        //TODO save to db
        return Response.status(201).entity(player).build();
    }


}
