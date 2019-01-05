/*****************************************
* Ryan Glassman
* rmg2203
* COMSW1004
* Programming Project 3
* 10.13.18
*
* A human Nim player
****************************************/
import java.util.Scanner;

public class Human
{

  private int choice;
  private Scanner input;

  public Human(){
    input= new Scanner(System.in);
    choice = -1;
  }

  public void move(int marblesLeft)
  {

    //used to make sure user choice is a legal move
    boolean allowed = false;

    while (!allowed)
    {
      //set the maximum legal move (half the pile)
      int maxLegal = marblesLeft / 2;

      //ask user for choice
      System.out.println("How many marbles do you want to take?");
      int take = input.nextInt();

      //check if user choice is legal, prompt for new choice if not
      if (take <= maxLegal && take > 0)
      {
        allowed = true;
        choice = take;
      }
      else
      {
        System.out.println("Invalid choice. Check the rules.");
      }
    }
  }

  public int getChoice()
  {
    return choice;
  }
}
