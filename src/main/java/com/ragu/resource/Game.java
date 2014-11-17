package com.ragu.resource;

import java.util.*;

public class Game {

    int turn;
    List<Card> playedCards;
    Dealer dealer = new Dealer();
    Player[] players;
    int winner;
    Card leadCard;
    private boolean hasGameEnded = false;

    public Game()
    {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        players = new Player[]{player1, player2};
        playedCards = new ArrayList<>();
    }

    public static void main(String[] args) {
        new Game().run();
    }

    public void run()
    {
        dealer.shuffle();
        dealer.shareCards(players);
        turn = getStartPlayer();
        winner = turn;
        while (true)
        {
            Player currentPlayer = players[turn];
            if(currentPlayer.getCards() != null) {
                List<Card> cards = currentPlayer.getCards();
                System.out.println("Play Card using 0-4 Player"+turn);
                showCards(cards);
                int playedCardIndex = validInputFromUser(cards);
                Card playedCard = cards.get(playedCardIndex);
                if (canPlayCard(leadCard, playedCard, currentPlayer.getCards()))
                {
                    currentPlayer.playCard(playedCard);
                    playedCards.add(playedCard);
                    winner = (leadCard != getLeadCard(playedCard)) ? turn : winner;
                    leadCard = getLeadCard(playedCard);
                    getNextPlayerTurn();
                    if(hasRoundEnded()) {
                        getNewPlayerTurns();
                    }
                    //If next player has no cards left end game!!!
                    if(hasGameEnded)
                        break;
                }
            }
        }
        System.out.println("The winner is Player " + players[winner].username);
    }

    private int validInputFromUser(List<Card>cards) {
        int playedCardIndex = getCardFromUser();
        if(playedCardIndex>=cards.size())
        {
            System.out.println(String.format("%s not a valid card. Try again from 0 to %s",
                    playedCardIndex,cards.size()));
            return validInputFromUser(cards);
        }
        return playedCardIndex;
    }

    private void showCards(List<Card> cards)
    {
        int counter = 0;
        for(Card card : cards)
        {
            System.out.println(card.toString() + " -> " + counter);
            counter++;
        }
    }
//  TODO find max and min compare to get to see if round is done
    boolean hasRoundEnded(){
        int max = 0;
// Arrays.asList(players).stream()
//                .max(player -> player.getCards().size());
        int min = 0;
        if(max == 0 && min == 0) {
            hasGameEnded = true;
        }
        if(max == min) {
            return true;
        }
        return false;
    }

    void getNewPlayerTurns()
    {
        Player [] newPlayerTurn = new Player[players.length];
        int oldIndex = winner;
        for (int x=0; x<newPlayerTurn.length;x++)
        {
            if(oldIndex>=players.length -1){
                oldIndex = 0;
            }
            newPlayerTurn[x]=players[oldIndex];
            oldIndex ++;
        }
        players = newPlayerTurn;
    }

    int getCardFromUser()
    {
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();
        return value;
    }

    private int getStartPlayer()
    {
        Random r = new Random();
        int low = 0;
        int high = players.length -1;
        return r.nextInt(high-low) + low;
    }

    private void getNextPlayerTurn()
    {
        if(turn>=players.length-1)
            turn = 0;
        else
            turn = turn+1;
    }

    private Card  getLeadCard(Card playedCard)
    {
        if(leadCard == null)
        {return playedCard;}
        else
        {
            return Card.biggerCard(leadCard,playedCard);
        }
    }

    public boolean canPlayCard(Card leadCard, Card playedCard, List<Card>remainingCards)
    {
        if(leadCard == null)
            return true;
        if(leadCard.suit.equals(playedCard.suit)) {
            return true;
        }
        else {
            for (Card card : remainingCards) {
                if (leadCard.suit.equals(card.suit))
                    return false;
            }
            return true;
        }
    }


}
