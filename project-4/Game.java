/*****************************
* Ryan Glassman
* rmg2203
* COMS W1004
*
* Game.java
* This class models a game of video poker.
*
* 11.3.18
***************************/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Game
{

  private Player p;
  private Deck cards;
  private final int LAST_CARD = 4;
  //used for locating the last card in a hand (index 4)

  private int odds;
  //tracks the value of the best hand

  private String bestHand;

  //used for testing hands
  public Game(String[] testHand)
  {
    p = new Player();
    cards = new Deck();
    odds = -1;
    //initialize odds to negative value so it will always be reset

    List<String> suitCode = Arrays.asList("c", "d", "h", "s");

    cards.shuffle();

    //parse args to cards, fill player hand with those cards
    for (int i = 0; i < testHand.length; i++)
    {
      int rank = Integer.parseInt(testHand[i].substring(1));
      String suitLetter = testHand[i].substring(0, 1);

      int suit = suitCode.indexOf(suitLetter) + 1;

      Card c = new Card(suit, rank);
      p.addCard(c);
    }
  }

  public Game()
  {
    p = new Player();
    cards = new Deck();
    odds = -1;

    cards.shuffle();
  }

  public void play()
  {
    boolean again = true;

    //plays only while player still has funds and wants to continue
    while (p.getBankroll() > 0.0 && again)
    {
      this.dealNewHand();

      p.sort();

      double bet = this.takeBet();
      p.bets(bet);

      this.exchangeCards();

      String playerHand = this.checkHand(p.getHand());
      p.winnings(odds);

      this.getResult(playerHand);

      again = this.playAgain();

      if (again)
      {
        cards.shuffle();
        p.reset();
        odds = -1;
      }
    }

    System.out.println("Goodbye!");
  }

  public String checkHand(ArrayList<Card> hand)
  {
    //checks hand from best to worst possible
    this.isRoyalFlush(hand);
    this.isStraightFlush(hand);
    this.isFour(hand);
    this.isFullHouse(hand);
    this.isFlush(hand);
    this.isStraight(hand);
    this.isThree(hand);
    this.isTwoPair(hand);
    this.isPair(hand);

    //sets best hand to high card if player has nothing else
    if (odds < 0)
    {
      odds = 0;
      bestHand = hand.get(LAST_CARD).toString();
    }

    return bestHand;
  }

  //takes user input and exchanges the cards they don't want
  public void exchangeCards()
  {
    Scanner input = new Scanner(System.in);
    System.out.println("Your cards:" + "\n" + p.toString());

    System.out.println("Input the numbers of the cards you wish "
    + "to replace, separated by spaces."
    + "\n" + "If none, simply press enter.");

    String user = input.nextLine();

    if (!user.isEmpty())
    {
      String[] split = user.split(" ");

      for (String element : split)
      {
        int c = Integer.parseInt(element) - 1;

        Card replace = cards.deal();
        p.replaceCard(c, replace);
      }

      p.sort();
      System.out.println("Your new cards:" + "\n" + p.toString());
    }
  }

  /*
  * methods that compare by rank rely on player hand being sorted,
  * which it always will be due to the construction of the play(),
  * dealNewHand() and exchangeCards() methods.
  *
  * all methods use the betterHand() method to update odds and bestHand
  * if and only if they're the better hand.
  */

  public void betterHand(int i, String s)
  {
    if (i > odds)
    {
      odds = i;
      bestHand = s;
    }
  }

  //checks if two adjacent cards are of the same rank
  public boolean isPair(ArrayList<Card> hand)
  {
    int worth = 1;
    boolean pair = false;

    for (int i = 0; i < LAST_CARD && !pair; i++)
    {
      int a = hand.get(i).getRank();
      int b = hand.get(i+1).getRank();

      if (a == b)
      {
        pair = true;
        this.betterHand(worth, "pair");
      }
    }

    return pair;
  }

  public boolean isTwoPair(ArrayList<Card> hand)
  {
    int worth = 2;
    boolean twoPair = false;
    int numPairs = 0;

    for (int i = 0; i < LAST_CARD; i++)
    {
      int a = hand.get(i).getRank();
      int b = hand.get(i+1).getRank();

      //updates number of pairs
      //and skips the next card if a pair is found
      if(a == b)
      {
        numPairs++;
        i++;
      }
    }

    if (numPairs == 2)
    {
      twoPair = true;
      this.betterHand(worth, "two pair");
    }

    return twoPair;
  }

  public boolean isThree(ArrayList<Card> hand)
  {
    int worth = 3;
    boolean three = false;

    for (int i = 0; i <= 2 && !three; i++)
    {
      int a = hand.get(i).getRank();
      int c = hand.get(i+2).getRank();

      //if cards that are two apart in a sorted hand have same rank,
      //so will the card between them
      if (a == c)
      {
        three = true;
        this.betterHand(worth, "three of a kind");
      }
    }

    return three;
  }

  public boolean isStraight(ArrayList<Card> hand)
  {
    int worth = 4;
    boolean straight = false;
    int first = hand.get(0).getRank();
    int second = hand.get(1).getRank();
    int last = hand.get(LAST_CARD).getRank();

    int i = 0;

    //handles high straight case (ace high)
    if (first == 1 && last == 13)
    {
      if (last - second == 3 && !this.isPair(hand))
      {
        straight = true;
        this.betterHand(worth, "straight");
      }
    }
    //handles all other cases
    else if (last - first == 4 && !this.isPair(hand))
    {
      straight = true;
      this.betterHand(worth, "straight");
    }

    return straight;
  }

  public boolean isFlush(ArrayList<Card> hand)
  {
    int worth = 5;
    boolean flush = true;

    //checks if all cards in a hand are of same suit,
    //makes flush false if not
    for (int i = 0; i < LAST_CARD && flush; i++)
    {
      int a = hand.get(i).getSuit();
      int b = hand.get(i+1).getSuit();

      if (a != b)
      {
        flush = false;
      }
    }

    if (flush)
    {
      this.betterHand(worth, "flush");
    }

    return flush;
  }

  public boolean isFullHouse(ArrayList<Card> hand)
  {
    //everywhere you look, there's a heart, a hand to hold onto...
    int worth = 6;
    boolean fullHouse = false;

    if (this.isThree(hand) && this.isTwoPair(hand) && !this.isFour(hand))
    {
      fullHouse = true;
      this.betterHand(worth, "full house");
    }

    return fullHouse;
  }

  public boolean isFour(ArrayList<Card> hand)
  {
    int worth = 25;
    boolean four = false;
    for (int i = 0; i < 2 && !four; i++)
    {
      int a = hand.get(i).getRank();
      int d = hand.get(i+3).getRank();

      if (a == d)
      {
        four = true;
        this.betterHand(worth, "four of a kind");
      }
    }

    return four;
  }

  public boolean isStraightFlush(ArrayList<Card> hand)
  {
    int worth = 50;
    boolean straightFlush = false;

    if (this.isStraight(hand) && this.isFlush(hand))
    {
      straightFlush = true;
      this.betterHand(worth, "straight flush");
    }

    return straightFlush;
  }

  public boolean isRoyalFlush(ArrayList<Card> hand)
  {
    int worth = 250;
    boolean royalFlush = false;
    int last = hand.get(LAST_CARD).getRank();

    if (this.isStraightFlush(hand) && last == 13)
    {
      royalFlush = true;

      //technically not necessary to call betterHand(),
      //but it's better to keep everything consistent.
      this.betterHand(worth, "royal flush");
    }

    return royalFlush;
  }

  //deals five new cards if the player has none
  public void dealNewHand()
  {
    if (p.getHand().isEmpty())
    {
      for (int i = 1; i <= 5; i++)
      {
        Card c = cards.deal();
        p.addCard(c);
      }
    }
  }

  //asks player for bet, checks that they have sufficient funds.
  //updates bet if so, asks for a lower bet if not.
  public double takeBet()
  {
    Scanner input = new Scanner(System.in);
    System.out.println("You have " + p.getBankroll() + " tokens."
    + "\n"
    + "How many would you like to bet? 1.0 – 5.0");
    double intendedBet = Double.parseDouble(input.nextLine());

    while (intendedBet > p.getBankroll())
    {
      System.out.println("Sorry, you don't have that much. "
      + "Please input a smaller amount.");
      intendedBet = Double.parseDouble(input.nextLine());
    }

    return intendedBet;
  }

  //asks player if they would like to play again
  public boolean playAgain()
  {
    boolean playAgain = true;

    Scanner input = new Scanner (System.in);
    if (p.getBankroll() > 0.0)
    {
      System.out.println("Play again? 1 for yes, 0 for no");
      int anotherRound = Integer.parseInt(input.nextLine());
      if (anotherRound == 0)
      {
        playAgain = false;
      }
    }
    else
    {
      System.out.println("Sorry, you're broke.");
    }

    return playAgain;
  }

  //prints result and winnings
  public void getResult(String s)
  {
    System.out.println("Your hand: " + s);
    System.out.println("Your winnings: " + odds * p.getBet());
    System.out.println("Your new bankroll: " + p.getBankroll());
  }
}
