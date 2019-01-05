/*****************************
 * Ryan Glassman
 * rmg2203
 * COMS W1004
 *
 * Player.java
 * This class models a poker player with a hand of cards.
 *
 * 11.3.18
 ***************************/

import java.util.ArrayList;
import java.util.Collections;

public class Player
{
    private ArrayList<Card> hand; // the player's cards
    private double bankroll;
    private double bet;

    public Player()
    {
        hand = new ArrayList<Card>();

        //arbitrarily chose for player to start with 20 tokens
        bankroll = 20.0;
        bet = 0.0;
    }

    public void addCard(Card c)
    {
        hand.add(c);
        // add the card c to the player's hand
    }

    public void removeCard(Card c)
    {
        hand.remove(c);
        // remove the card c from the player's hand
    }

    //used for exchanging cards
    public void replaceCard(int i, Card c)
    {
        hand.set(i, c);
    }

    public void bets(double amt)
    {
        bet = amt;
        bankroll -= bet;
    }

    public void winnings(double odds)
    {
        double winnings = odds * bet;
        bankroll += winnings;
        //adjust bankroll if player wins
    }

    public double getBankroll()
    {
        return bankroll;
        //return current balance of bankroll
    }

    public double getBet()
    {
        return bet;
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public void reset()
    {
        hand.clear();
        bet = 0.0;
    }

    public String toString()
    {
        StringBuilder yourHand = new StringBuilder();
        for(Card element : hand)
        {
            yourHand.append(element.toString());
            yourHand.append("\n");
        }

        return yourHand.toString();
    }

    public void sort()
    {
        Collections.sort(hand);
    }
}
