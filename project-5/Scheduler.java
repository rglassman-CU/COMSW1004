/*****************************
 * Ryan Glassman
 * rmg2203
 * COMS W1004
 *
 * Scheduler.java
 * This class schedules talks at a conference.
 *
 * 11.20.18
 ***************************/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Scheduler
{

  public static ArrayList<Talk> makeTalks(String f)
  {
    ArrayList<Talk> talks = new ArrayList<Talk>();

    try
    {
      File inFile = new File(f);
      Scanner input = new Scanner(inFile);
      //for checking if times are in 24-hour format
      boolean milTime = false;

      while (input.hasNextLine())
      {
        String line = input.nextLine();

        //Issue warning if times are not in 24-hour format
        if ((line.contains("AM") || line.contains("PM")) & !milTime)
        {
          System.out.println("Please format times in 24-hr format. "
          + "Schedule may not be accurate.");
          milTime = true;
        }

        Talk t = Scheduler.constructTalk(line);

        //do not add talk if constructTalk() produced an error
        if (t != null)
        {
          talks.add(t);
        }
      }

      input.close();
      //reset military time warning
      milTime = false;
    }
    catch (FileNotFoundException x)
    {
      System.out.println("Can't find file: " + x.getMessage());
    }
    //thrown by constructTalk() method if there aren't 3 elements per line
    catch (NoSuchElementException y)
    {
      System.out.println(
      "Error building schedule; file formatted incorrectly. "
      + "\nSchedule may not be accurate.");
    }
    //thrown by Talk class constructor
    catch (NumberFormatException z)
    {
      System.out.println(z.getMessage() + "\nSchedule may not be accurate.");
    }
    catch (RuntimeException r)
    {
      System.out.println("Unknown runtime error: "+ r.getMessage());
    }

    return talks;
  }

  public static ArrayList<Talk> scheduleTalks(ArrayList<Talk> t)
  {
    ArrayList<Talk> scheduled = new ArrayList<Talk>();

    try
    {
      //sort list of talks by end time
      Collections.sort(t);
      //add first sorted talk, which will be the earliest-ending
      scheduled.add(t.get(0));

      for (int i = 1; i < t.size(); i++)
      {
        //get start time of next talk...
        int start = Integer.parseInt(t.get(i).getStart());
        //...and end time of last scheduled talk
        int end = Integer.parseInt(scheduled.get
        (scheduled.size() - 1).getEnd());
        //schedule new talk if it does not overlap
        if (start >= end)
        {
          scheduled.add(t.get(i));
        }
      }
    }
    //will be thrown if makeTalks() could not add any elements
    //(due to formatting errors)
    catch(IndexOutOfBoundsException x)
    {
      System.out.println("Input list provided is empty.");
    }
    catch (RuntimeException r)
    {
      System.out.println("Unknown runtime error: "+ r.getMessage());
    }

    return scheduled;
  }

  public static Talk constructTalk(String s) throws NoSuchElementException,
  IndexOutOfBoundsException, NumberFormatException
  {
    //clean common characters from string input, such as numbered elements,
    //dashes, colons, and AM and PM.
    String cleaned = s.replaceAll("^[\\d[.]]|[^a-zA-Z0-9\\s]|(AM)|(PM)","");

    Scanner talkBuild = new Scanner(cleaned);
    String name = talkBuild.next();
    String starts = talkBuild.next();
    String ends = talkBuild.next();

    starts = Scheduler.cleanZeroes(starts);
    ends = Scheduler.cleanZeroes(ends);

    Talk a = null;

    try
    {
      a = new Talk(name, starts, ends);
    }
    catch (IndexOutOfBoundsException i)
    {
      System.out.println(i.getMessage());
    }

    return a;
  }

  //add zeroes for common formatting errors (i.e., 9:00 or just 9)
  public static String cleanZeroes(String s)
  {
    String time = s;

    if (time.length() == 3)
    {
      time = "0" + time;
    }
    else if (time.length() == 1)
    {
      time = "0" + time + "00";
    }

    return time;
  }
}
