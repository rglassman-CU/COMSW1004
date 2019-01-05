/*****************************
 * Ryan Glassman
 * rmg2203
 * COMS W1004
 *
 * Talk.java
 * This class models a talk at a conference.
 *
 * 11.20.18
 ***************************/

public class Talk implements Comparable<Talk>
{
  //store all attributes as strings to preserve leading zeros in times
  private String name;
  private String starts;
  private String ends;

  //throw NumberFormatException to be handled by Scheduler class
  public Talk (String n, String s, String e) throws NumberFormatException
  {
    name = n;
    starts = s;
    ends = e;

    this.errorCheck();
  }

  //compare talks by end time (24-hr time)
  public int compareTo(Talk t)
  {
    int a = Integer.parseInt(this.ends);
    int b = Integer.parseInt(t.ends);

    int compare = Integer.compare(a, b);

    return compare;
  }

  public String toString()
  {
    return String.format("%-20s%8s%8s", name, starts, ends);
  }

  //getStart() and getEnd() for use by Scheduler.scheduleTalks()
  public String getStart()
  {
    return starts;
  }

  public String getEnd()
  {
    return ends;
  }

  //check if start and end strings are properly formatted time values
  //(will throw NumberFormatException if not)
  public void errorCheck() throws NumberFormatException,
  IndexOutOfBoundsException
  {
    try
    {
      this.digitCheck();
      this.hourCheck();
      this.minuteCheck();
    }
    catch (IndexOutOfBoundsException i)
    {
      throw new IndexOutOfBoundsException("for " + name + ":"
      + " unknown number format error. "
      + "Check that times are formatted correctly");
    }
  }

  //check that all characters in starts and ends are digits
  public void digitCheck() throws NumberFormatException,
  IndexOutOfBoundsException
  {
    for (int i = 0; i < starts.length(); i++)
    {
      boolean startTime = Character.isDigit(starts.charAt(i));
      boolean endTime = Character.isDigit(ends.charAt(i));
      if (!startTime)
      {
        throw new NumberFormatException("for " + name + ": "
        + starts + " is not a valid time value");
      }
      else if(!endTime)
      {
        throw new NumberFormatException("for " + name + ": "
        + ends + " is not a valid time value");
      }
    }
  }

  //check that hour values are between 00 and 24
  public void hourCheck() throws NumberFormatException
  {

    int startHours = Integer.parseInt(starts.substring(0,2));
    int endHours = Integer.parseInt(ends.substring(0,2));

    if (startHours < 0 || startHours > 24)
    {
      throw new NumberFormatException("for " + name + ": "
      + starts + " does not have a valid hour value; "
      + "must be between 00 and 24");
    }
    else if (endHours < 0 || endHours > 24)
    {
      throw new NumberFormatException("for " + name + ": "
      + ends + " does not have a valid hour value; "
      + "must be between 00 and 24");
    }
  }

  //check that minute values are between 00 and 59
  public void minuteCheck() throws NumberFormatException
  {
    int startMinutes = Integer.parseInt(starts.substring(2));
    int endMinutes = Integer.parseInt(ends.substring(2));

    if (startMinutes > 59)
    {
      throw new NumberFormatException("for " + name + ": "
      + starts + " does not have a valid minute value; "
      + "must be between 00 and 59");
    }
    else if (endMinutes > 59)
    {
      throw new NumberFormatException("for " + name + ": "
      + ends + " does not have a valid minute value; "
      + "must be between 00 and 59");
    }
  }
}
