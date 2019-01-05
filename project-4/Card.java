/*****************************
 * Ryan Glassman
 * rmg2203
 * COMS W1004
 * 
 * Card.java
 * This class models a playing card.
 * 
 * 11.3.18
 ***************************/

public class Card implements Comparable<Card>
{
	
    private int suit; // use integers 1-4 to encode the suit
    private int rank; // use integers 1-13 to encode the rank
    private double absRank;
    private String[] suitNames = 
    {
        "clubs",
        "diamonds",
        "hearts",
        "spades"
    };
    
    //names of non-numbered cards
    private String[] royals = 
    {
        "ace",
        "jack",
        "queen",
        "king"
    };
	
    public Card(int s, int r)
    {
        //make a card with suit s and value v
        suit = s;
        rank = r;
        //absolute rank allows cards to be sorted first by rank,
        //then by suit, without the need for if statements.
        absRank = rank + suit * 0.1;
    }
	
    public int compareTo(Card c)
    {
        //sorts cards by absolute rank (rank first, then suit)
        int compare = Double.compare(this.absRank, c.absRank);
        return compare;
    }
	
    public String toString()
    {
        //find name of suit
        String suitName = suitNames[this.suit - 1];
        
        //for numbered cards
        if (this.rank > 1 && this.rank < 11)
        {
            return Integer.toString(rank) + " of " + suitName;
        }
        //for aces
        else if (this.rank == 1)
        {
            return royals[rank - 1] + " of " + suitName;
        }
        //for face cards
        else
        {
            return royals[rank - 10] + " of " + suitName;
        }	
    }
    
    //not used by Game class, but could be useful in other applications
    public double getAbsRank()
    {
        return absRank;
    }
    
    public int getRank()
    {
        return rank;
    }
    
    public int getSuit()
    {
        return suit;
    }
}
