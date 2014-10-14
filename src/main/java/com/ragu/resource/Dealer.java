package com.ragu.resource;

import java.util.List;

public class Dealer {

    private List<Card> deck;
    private List<Card> played;

    public Dealer()
    {
        deck = createDeck();
    }

    public List<Card> getDeck() {
        return deck;
    }

    private List<Card> createDeck()
    {

        return null;
    }


    public List<Card> shuffle()
    {
        //Take deck and randomize
        return null;
    }

    public List<Card> share(Player[] players)
    {
        //remove from deck and give to user
        return null;
    }

}
