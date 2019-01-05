/*****************************
 * Ryan Glassman
 * rmg2203
 * COMS W1004
 * 
 * Game.java
 * This class models a deck of 52 playing cards.
 * 
 * 11.3.18
 ***************************/

public class Deck 
{
	
    private Card[] cards;
    private int top; // the index of the top of the deck

	
    public Deck()
    {
        
        cards = new Card[52];
        
        //outer loop handles suits
        for (int i = 1; i <= 4; i++)
        {
            //inner loop handles rank
            for (int j = 1; j <= 13; j++)
            {
                int position = 13 * (i - 1) + j - 1;
                Card c = new Card(i, j);
                cards[position] = c;
            }
        }
    }
	
    //exchange two cards at random, 10,000 times
    public void shuffle()
    {
        for (int i = 0; i < 10000; i++)
        {
            int a = (int) (Math.random() * 52);
            int b = (int) (Math.random() * 52);
            
            Card temp = cards[a];
            cards[a] = cards[b];
            cards[b] = temp;
        }
        top = 0;
    }
	
    public Card deal(){
		
        Card c = cards[top];
        top++;
        return c;
    }
    
    public String toString()
    {
        StringBuilder wholeDeck = new StringBuilder();
        for (Card element : cards)
        {
            wholeDeck.append(element.toString());
            wholeDeck.append("\n");
        }
        return wholeDeck.toString();
    }
}
