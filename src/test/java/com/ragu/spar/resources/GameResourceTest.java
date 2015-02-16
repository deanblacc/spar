package com.ragu.spar.resources;

import com.ragu.spar.Game;
import com.ragu.spar.Player;
import com.ragu.spar.exceptions.SparException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameResourceTest {
    public static final String PLAYER = "player1";
    GameResource gameResource = new GameResource();
    Map<String,Game> gameMap;
    Player player1;

    //TODO Test the JSON
    @Before
    public void setup() throws Exception {
        player1 = new Player(PLAYER);
        gameResource.createGame(player1);
        gameMap = gameResource.games;
    }

    @After
    public void tearDown() throws Exception {
        for(Map.Entry<String,Game>entry :gameMap.entrySet() ){
            entry.getValue().endGame();
        }
        gameMap.clear();
    }

    @Test
    public void testCreatesGame(){
        assertEquals(1, gameMap.size());
        for(Map.Entry<String,Game> entry : gameMap.entrySet()){
            Game game = entry.getValue();
            assertEquals(1,game.getPlayers().size());
            assertEquals(PLAYER,game.getPlayers().get(0).getUsername());
        }
    }

    @Test
    public void testJoinGame() throws SparException {
        Player player2 = new Player("player2");
        for(Map.Entry<String,Game>entry :gameMap.entrySet()){
            String gameId = entry.getKey();
            Game game = entry.getValue();
            gameResource.joinGame(gameId,player2);
            assertEquals(2,game.getPlayers().size());
            assertEquals(PLAYER,game.getPlayers().get(0).getUsername());
            assertEquals("player2",game.getPlayers().get(1).getUsername());
        }
    }

    @Test
    public void testStartGame() throws SparException {
        Player player2 = new Player("player2");
        for(Map.Entry<String,Game>entry :gameMap.entrySet()){
            String gameId = entry.getKey();
            Game game = entry.getValue();
            gameResource.joinGame(gameId,player2);
            gameResource.startGame(gameId);
            assertTrue(game.isGameStarted);
        }
    }

}
