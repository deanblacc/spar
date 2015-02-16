package com.ragu.spar;

import com.ragu.messaging.Publisher;
import com.ragu.spar.exceptions.SparException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Game {

    private List<Card> playedCards;
    private Map<Player,Card> playerCardRoundMap;
    private Dealer dealer = new Dealer();
    private List<Player> players;
    public boolean isGameStarted = false;
    private Player winner;
    private Card leadCard;
    private boolean isGameEnded = false;
    private UUID id;
    private boolean hasLeaderPlayed = false;
    private String exchangeName = "Test";
    private Publisher publisher;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getWinner() {
        return winner;
    }

    public Card getLeadCard() {
        return leadCard;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public UUID getId() {
        return id;
    }

    public void setWinner(Player winner){
        this.winner = winner;
    }

    //TODO Remove exchange name

    public Game(Player player) throws IOException {
        players = new ArrayList<>();
        players.add(player);
        playedCards = new ArrayList<>();
        id = UUID.randomUUID();
        publisher = new Publisher(exchangeName);
        playerCardRoundMap = new HashMap<>();
    }

    public void forfeit(Player player) throws IOException {
        publisher.publishMessage(player.jsonify());
        players.remove(player);
        if(players.size() == 1){
            winner = players.get(0);
            //calculate score
            endGame();
        }
        else{
            if(player == winner){
                winner = null;
                endGame();
            }
        }
    }

    public void addPlayerToGame(Player player) throws SparException {
        if(!isGameStarted && players.size()<=4){
            players.add(player);
            logger.info("Added player to game");
        }
        else{
            String message = "Cannot not add anymore players to game";
            logger.error(message);
            throw new SparException(message);
        }

    }

    public void playCard(Player currentPlayer, Card playedCard) throws IOException, SparException {
        if (hasPlayerPlayed(currentPlayer)) {
            String message = "Player played already for this round";
            logger.warn(message);
            throw new SparException(message);
        }
        if (winner.equals(currentPlayer)) {
            leadCard=playedCard;
            hasLeaderPlayed = true;
        }
        if (!hasLeaderPlayed) {
            String message = "Winner needs to play first";
            logger.warn(message);
            throw new SparException(message);
        } else {
            if (canPlayCard(playedCard, currentPlayer.cards)) {
                currentPlayer.playCard(playedCard);
                publisher.publishMessage(playedCard.toString());
                playedCards.add(playedCard);
                playerCardRoundMap.put(currentPlayer, playedCard);
                if (hasRoundEnded()) {
                    setWinnings();
                    leadCard = null;
                    hasLeaderPlayed = false;
                    playerCardRoundMap.clear();
                }
            } else {
                currentPlayer.hasGbaa = true;
                publisher.publishMessage(currentPlayer.jsonify());
                endGame();
            }
        }
    }

    private boolean hasPlayerPlayed(Player currentPlayer) {
        for(Player playedPlayer : playerCardRoundMap.keySet()){
            if(currentPlayer.equals(playedPlayer)){
                return true;
            }
        }
        return false;
    }

    private void setWinnings() {
        for(Map.Entry<Player,Card> entry : playerCardRoundMap.entrySet()){
            Card playedCard = entry.getValue();
            Card tempCard = getLeadCard(playedCard);
            if(!tempCard.equals(leadCard)){
                winner = entry.getKey();
                leadCard = tempCard;
            }
        }
    }

    public Card getCardFromIndex(List<Card>cards,int playedCard) throws SparException {
        int playedCardIndex = validInputFromUser(cards,playedCard);
        return cards.get(playedCardIndex);
    }

    private int validInputFromUser(List<Card>cards,int playedCardIndex) throws SparException {
        if(playedCardIndex>=cards.size())
        {
            String message = String.format("%s not a valid card. Try again from 0 to %s",
                    playedCardIndex,cards.size());
            throw new SparException(message);
        }
        return playedCardIndex;
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
            isGameEnded = true;
            try {
                publisher.closeAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return max == min;
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
        {
            return playedCard;
        }
        else
        {
            return Card.biggerCard(leadCard,playedCard);
        }
    }

    boolean canPlayCard(Card playedCard, List<Card>remainingCards)
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

    public void startGame() throws SparException {
        if(players.size()<2){
            throw new SparException("Need 2 or more players to start a game");
        }
        dealer.shuffle();
        dealer.shareCards(players);
        isGameStarted = true;
        winner = players.get(getStartPlayer());
    }
    private void startTimer(){
        int delay =10000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    startGame();
                } catch (SparException e) {
                    e.printStackTrace();
                }
            }
        },delay);
    }

    public void endGame() throws IOException {
        publisher.closeAll();
        isGameEnded = true;
    }

}
