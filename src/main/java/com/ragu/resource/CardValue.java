package com.ragu.resource;

/**
 * Created by nana on 10/14/14.
 */
public enum CardValue {
    two(2),
    three(3),
    four(4),
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

    public int getCardValue()
    {
        return cardValue;
    }
}
