package com.ragu.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by nana on 10/11/14.
 */
public class GameTest
{
    Game game;

    @Before
    public void setup()
    {
        game = new Game();
    }

    @Test
    public void getCardFromPlayer()
    {
        game.getCardFromUser();
    }


}
