package com.ragu.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by nana on 10/9/14.
 */
public class Game {

    int turn;
    List<Card> playedCards;
    Dealer dealer = new Dealer();
    Player[] players;
    int winner;
    Card leadCard;
    public Game()
    {
        Player player1 = new Player();
        Player player2 = new Player();
        players = new Player[]{player1, player2};
    }

    public static void main(String[] args) {

    }

    public void run()
    {
        List<Card>deck = this.dealer.shuffle();
        this.dealer.share(players);
        turn = getStartPlayer();
        winner = turn;
        while (true)
        {
            Player currentPlayer = players[turn];
            if(currentPlayer.getCards() != null) {
                List<Card> cards = currentPlayer.getCards();
                System.out.println("Play Card using 0-4");
                System.out.println(cards);
                Card playedCard = cards.get(getCardFromUser());
                currentPlayer.playCard(playedCard);
                playedCards.add(playedCard);
                winner = (leadCard != getLeadCard(playedCard))? turn: winner;
                leadCard = getLeadCard(playedCard);
                getNextPlayer();
            }
            System.out.println("The winner is Player " + players[winner].username);
        }
    }

     int getCardFromUser()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Play Card using 0-4");
        int value = sc.nextInt();
        if(value<0 || value >4)
        {
            throw new IllegalArgumentException();
        }
        return Integer.parseInt(sc.nextLine());
    }

    private int getStartPlayer()
    {
        Random r = new Random();
        int low = 0;
        int high = players.length -1;
        return r.nextInt(high-low) + low;
    }

    private void getNextPlayer()
    {
        if(turn>=players.length)
            turn = 0;
        else
            turn = turn+1;
    }

    private Card getLeadCard(Card playedCard)
    {
        if(leadCard == null)
        {return playedCard;}
        else
        {
            return Card.biggerCard(leadCard,playedCard);
        }
    }


}
