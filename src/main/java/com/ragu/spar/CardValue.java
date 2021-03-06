package com.ragu.spar;

/**
 * Created by nana on 10/14/14.
 */
public enum CardValue {
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9),
    ten(10),
    jack(11),
    queen(12),
    king(13),
    ace(14);

    private int cardValue;

    private CardValue(int cardValue)
    {
        this.cardValue = cardValue;
    }

    public static CardValue fromInt(int value)
    {
        for(CardValue cardValue : CardValue.values())
        {
            if(cardValue.getCardValue() == value)
                return cardValue;
        }
        throw new IllegalArgumentException("Value not a card number");
    }

    public int getCardValue()
    {
        return cardValue;
    }
}
