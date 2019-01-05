/*****************************************
* Ryan Glassman
* rmg2203
* COMSW1004
* Programming Project 3
* 10.13.18
*
* This class models a game of Nim
****************************************/
public class Game
{

  private int marbles;

  //randomly set to 0 or 1 when game starts. Decides who goes first
  private int first;

  //keeps track of whose turn it is (and who wins when game ends)
  private int turnCount;

  private Human humanPlayer;
  private Computer computerPlayer;

  //used for generating random pile size
  private final int PILE_MIN = 10;
  private final int PILE_MAX = 100;

  public Game(int difficulty)
  {
    //set the pile to a random integer between 10 and 100
    marbles = (int)(Math.random() * ((PILE_MAX - PILE_MIN) + 1)) + PILE_MIN;

    //pick a random number between 0 and 1
    first = (int) Math.round(Math.random());

    turnCount = -1;

    //create human and computer player
    humanPlayer = new Human();
    computerPlayer = new Computer(difficulty);
  }

  public void play()
  {
    System.out.println(marbles + " marbles to start.");

    if (first == 0)
    {
      this.compMove();
    }

    //keep taking turns while there is more than one marble left
    while (marbles > 1)
    {
      this.humanMove();

      if (marbles > 1)
      {
        this.compMove();
      }
    }

    //whoever's turn it is when there is 1 marble left loses
    if (turnCount == 1)
    {
      System.out.println("You win!");
    }
    else if (turnCount == 2)
    {
      System.out.println("You lose!");
    }
    // your code here
  }

  private void compMove()
  {
    computerPlayer.move(marbles);

    //decrease marbles in pile by computer choice
    marbles = marbles - computerPlayer.getChoice();

    //tell user what the computer player did
    int lastTurn = computerPlayer.getChoice();
    System.out.println("Computer took " + lastTurn + " marbles.");

    turnCount = 2;
  }

  private void humanMove()
  {
    System.out.println("There are " + marbles + " marbles left.");
    humanPlayer.move(marbles);

    //decrease marbles in pile by user choice
    marbles = marbles - humanPlayer.getChoice();

    turnCount = 1;
  }
}
