package com.ragu.spar;

import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.List;
import java.util.Stack;

import org.json.JSONObject;
/**
 * Created by nana on 10/9/14.
 */
public class Player {

    private String username;
    boolean hasGbaa;
    List<Card> cards;
    Stack<Card> playedCards = new Stack<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Player(){}

    public Player(String username)
    {
        this.username = username;
    }

    public void playCard(Card card)
    {
        playedCards.push(card);
        cards.remove(card);

    }

    public String jsonify(){
        JSONObject playerJsonObject = new JSONObject();
        playerJsonObject.put("username", this.username);
        playerJsonObject.put("hasGbaa", this.hasGbaa);
        playerJsonObject.put("cards",this.cards);
        return playerJsonObject.toString();
    }


}
