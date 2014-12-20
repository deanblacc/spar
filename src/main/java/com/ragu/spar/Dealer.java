package com.ragu.spar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dealer {

    private List<Card> deck;
    private static final int TOP = 0;

    public Dealer()
    {
        deck = createDeck();
    }

    public List<Card> getDeck() {
        return deck;
    }

    private List<Card> createDeck()
    {
        deck = new ArrayList<>();
        for(int x=CardValue.five.getCardValue(); x<CardValue.ace.getCardValue();x++)
        {
            deck.add(new Card(CardValue.fromInt(x),Suit.CLUBS));
            deck.add(new Card(CardValue.fromInt(x),Suit.SPADES));
            deck.add(new Card(CardValue.fromInt(x),Suit.DIAMONDS));
            deck.add(new Card(CardValue.fromInt(x),Suit.HEARTS));
        }

        return deck;
    }


    public void shuffle()
    {
        Collections.shuffle(deck);
    }

    public void shareCards(List<Player> players)
    {
        for(Player player : players){
            player.setCards(shareCards());
        }

    }

    List<Card>shareCards()
    {
        List<Card>cards = new ArrayList<>();
        for(int x = 0; x<5 ; x++)
        {
            cards.add(deck.get(TOP));
            deck.remove(TOP);
        }
        return cards;

    }

}
