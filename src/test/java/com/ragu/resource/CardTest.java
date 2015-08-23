package com.ragu.resource;

import com.ragu.spar.Card;
import com.ragu.spar.CardValue;
import com.ragu.spar.Suit;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CardTest {

    private static final Card FIVE_CLUBS = new Card(CardValue.five, Suit.CLUBS);
    private static final Card SIX_CLUBS = new Card(CardValue.six,Suit.CLUBS);
    private static final Card ACE_HEARTS = new Card(CardValue.ace,Suit.HEARTS);
    private static final Card TEN_SPADES = new Card(CardValue.ten,Suit.SPADES);
    private static final Card QUEEN_CLUBS = new Card(CardValue.queen,Suit.CLUBS);

    @Test
    public void testToString()
    {
        Card card = new Card(CardValue.five,Suit.CLUBS);
        assertEquals("fiveCLUBS",card.toString());
    }

    @Test
    public void leadCardIsSameSuiteAndBigger()
    {
        assertEquals(SIX_CLUBS,Card.biggerCard(SIX_CLUBS,FIVE_CLUBS));
    }

    @Test
    public void leadCardIsSameSuiteAndSmaller()
    {
        assertEquals(SIX_CLUBS,Card.biggerCard(FIVE_CLUBS,SIX_CLUBS));
    }

    @Test
    public void leadCardIsDifferentSuiteAndSmaller()
    {
        assertEquals(SIX_CLUBS,Card.biggerCard(SIX_CLUBS,ACE_HEARTS));
    }

    @Test
    public void leadCardIsDifferentSuiteAndBigger()
    {
        assertEquals(ACE_HEARTS,Card.biggerCard(ACE_HEARTS,QUEEN_CLUBS));
    }
}
