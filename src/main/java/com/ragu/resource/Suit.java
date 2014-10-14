package com.ragu.resource;

/**
 * Created by nana on 10/9/14.
 */
public enum Suit {

    HEARTS("hearts"),
    SPADES("spades"),
    CLUBS("clubs"),
    DIAMONDS("diamonds");

    private String suit;

    private Suit(String suit)
    {
        this.suit = suit;
    }

    public String getSuit()
    {
        return suit;
    }
}
