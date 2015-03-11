package com.ragu.spar;

public class Card {

    CardValue value;
    Suit suit;
    boolean isCardDried;

    public Suit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }

    public boolean getIsCardDried(){
        return isCardDried;
    }

    public Card(CardValue value,Suit suit)
    {
        this.value = value;
        this.suit = suit;
        isCardDried = false;
    }

    public Card(CardValue value,Suit suit, boolean isCardDried)
    {
        this.value = value;
        this.suit = suit;
        this.isCardDried = isCardDried;
    }

    public void dryCard(){
        this.isCardDried = true;
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
