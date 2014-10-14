package com.ragu.resource;

/**
 * Created by nana on 10/9/14.
 */
public class Card {
    CardValue value;
    Suit suit;

    public Card(CardValue value,Suit suit)
    {
        this.value = value;
        this.suit = suit;

    }
    public static Card biggerCard(Card leadCard, Card playedCard) {
        if(leadCard.suit == playedCard.suit)
        {
            if(leadCard.value.getCardValue()>playedCard.value.getCardValue())
            {
                return leadCard;
            }
            else
                return playedCard;
        }
        return leadCard;
    }
}
