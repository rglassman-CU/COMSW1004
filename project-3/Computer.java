/*****************************************
* Ryan Glassman
* rmg2203
* COMSW1004
* Programming Project 3
* 10.13.18
*
* A computer Nim player
****************************************/

public class Computer
{

  private int mode;
  private int choice;

  public Computer(int m)
  {
    mode = m;
    choice = -1;
  }

  public void move(int marblesLeft)
  {
    //select smart or dumb move based on mode
    if (mode == 1)
    {
      this.dumbMove(marblesLeft);
    }
    else if (mode == 2)
    {
      this.smartMove(marblesLeft);
    }
  }

  private void dumbMove(int marblesLeft)
  {
    //generate a random integer between 0 and half the marbles left
    int random = (int) Math.round(Math.random() * marblesLeft / 2);

    //strip 0 from choices
    choice = Math.max(1, random);
  }

  private void smartMove(int marblesLeft)
  {
    //find the greatest integer n such that 2^n < marblesLeft
    int n = (int) Math.floor(Math.log(marblesLeft) / Math.log(2));

    //ideal move leaves 2^n - 1 marbles in pile
    int ideal = marblesLeft - (int) Math.pow(2, n) + 1;

    //set the maximum legal move (half the pile)
    int maxLegal = marblesLeft / 2;

    //check if ideal move is legal; if not, make random move
    if (ideal <= maxLegal)
    {
      choice = ideal;
    }
    else
    {
      this.dumbMove(marblesLeft);
    }
  }

  public int getChoice()
  {
    return choice;
  }
}
