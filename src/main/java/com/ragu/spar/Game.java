package com.ragu.spar;

import com.ragu.messaging.Publisher;

import java.io.IOException;
import java.util.*;

public class Game {

    private int turn;
    private List<Card> playedCards;
    private Dealer dealer = new Dealer();
    private List<Player> players;
    public boolean hasGameStarted = false;
    private int winner;
    private Card leadCard;
    private boolean hasGameEnded = false;
    private UUID id;
    private String exchangeName = "Test";

    public List<Card> getPlayedCards() {
        return playedCards;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public int getWinner() {
        return winner;
    }

    public Card getLeadCard() {
        return leadCard;
    }

    public boolean isHasGameEnded() {
        return hasGameEnded;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public UUID getId() {
        return id;
    }

    Publisher publisher;
    //TODO Remove exchange name

    public Game(Player player) throws IOException {
        players = new ArrayList<>();
        players.add(player);
        playedCards = new ArrayList<>();
        id = UUID.randomUUID();
        publisher = new Publisher(exchangeName);
    }

    public void addPlayerToGame(Player player){
        if(!hasGameStarted && players.size()<=4){
            players.add(player);
        }

    }



//    public void run() throws IOException {
//        dealer.shuffle();
//        dealer.shareCards(players);
//        turn = getStartPlayer();
//        winner = turn;
//        while (true)
//        {
//            Player currentPlayer = players[turn];
//            if(currentPlayer.cards != null) {
//                List<Card> cards = currentPlayer.cards;
//                System.out.println("Play Card using 0-4 Player"+turn);
//                showCards(cards);
//                int playedCardIndex = validInputFromUser(cards);
//                Card playedCard = cards.get(playedCardIndex);
//                if (canPlayCard(leadCard, playedCard, currentPlayer.cards))
//                {
//                    currentPlayer.playCard(playedCard);
//                    publisher.publishMessage(playedCard.toString());
//                    playedCards.add(playedCard);
//                    winner = (leadCard != getLeadCard(playedCard)) ? turn : winner;
//                    leadCard = getLeadCard(playedCard);
//                    if(hasRoundEnded()) {
//                        getNewPlayerTurns();
//                    }
//                    else {
//                        getNextPlayerTurn();
//                    }
//                    //If next player has no cards left end game!!!
//                    if(hasGameEnded)
//                        break;
//                }
//            }
//        }
//
//        System.out.println("The winner is Player " + players[winner].getUsername());
//        publisher.closeAll();
//    }

    public boolean playCard(Player currentPlayer, Card playedCard) throws IOException {
        if (canPlayCard(leadCard, playedCard, currentPlayer.cards))
        {
            currentPlayer.playCard(playedCard);
            publisher.publishMessage(playedCard.toString());
            playedCards.add(playedCard);
            winner = (leadCard != getLeadCard(playedCard)) ? turn : winner;
            leadCard = getLeadCard(playedCard);
            return true;
        }
        return false;
    }

    public Card getCardFromIndex(List<Card>cards,int playedCard){
        int playedCardIndex = validInputFromUser(cards,playedCard);
        return cards.get(playedCardIndex);
    }

    private int validInputFromUser(List<Card>cards,int playedCardIndex) {
        if(playedCardIndex>=cards.size())
        {
            String message = String.format("%s not a valid card. Try again from 0 to %s",
                    playedCardIndex,cards.size());
            throw new IllegalArgumentException(message);
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
    boolean hasRoundEnded(){
        int max = players.stream()
                .map(playerCardsLeft -> playerCardsLeft.cards.size())
                .max((player1, player2) -> player1 - player2)
                .get();
        int min = players.stream()
                .map(playerCardsLeft->playerCardsLeft.cards.size())
                .min((player1,player2) -> player1-player2)
                .get();
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

    }

    private int getStartPlayer()
    {
        Random r = new Random();
        int low = 0;
        int high = players.size() -1;
        return r.nextInt(high-low) + low;
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

    boolean canPlayCard(Card leadCard, Card playedCard, List<Card>remainingCards)
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


    public void startGame() {
        dealer.shuffle();
        dealer.shareCards(players);
        hasGameStarted = true;
    }
    private void startTimer(){
        int delay =10000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dealer.shuffle();
                dealer.shareCards(players);
                hasGameStarted = true;
            }
        },delay);
    }
}
