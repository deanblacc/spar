package com.ragu.resource;

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

    public String toString()
    {
        return value.toString()+suit.toString();
    }
}
