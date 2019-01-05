/*****************************************
* Ryan Glassman
* rmg2203
* COMSW1004
* Programming Project 3
* 10.14.18
*
* This class converts ZIP codes to barcodes, and vice versa.
****************************************/

public class Zipcode
{
  private int zip;
  private String barcode;

  //store the barcode conversions for each digit in order
  private String[] weightTable =
  {
    "||:::",
    ":::||",
    "::|:|",
    "::||:",
    ":|::|",
    ":|:|:",
    ":||::",
    "|:::|",
    "|::|:",
    "|:|::",
  };

  //if user provides ZIP, store it and convert to barcode
  public Zipcode(int userZIP)
  {
    zip = userZIP;
    this.ZIPToBar();
  }

  //and vice versa
  public Zipcode(String userBarcode)
  {
    barcode = userBarcode;
    this.barToZIP();
  }

  public String getBarcode()
  {
    return barcode;
  }

  public int getZIPcode()
  {
    return zip;
  }

  private void barToZIP()
  {
    //for building ZIP code one digit at a time
    StringBuilder ZIPbuilder = new StringBuilder();

    /* loop through barcode 5 characters at a time,
    /  skipping opening frame bar and stopping before check digit
    */
    for (int i = 1; i <= 21; i += 5)
    {
      //set start and end of substring
      int start = i;
      int end = i + 5;

      //create 5-character substring
      String barDigit = barcode.substring(start, end);

      //used for comparing substring to weightTable
      boolean same = false;

      //loop through weightTable, comparing substring to each entry
      for (int a = 0; !same; a++)
      {
        /* when matching code is found, append corresponding
        * decimal digit to ZIPbuilder and stop the loop
        */
        if (barDigit.equals(weightTable[a]))
        {
          same = true;
          ZIPbuilder.append(a);
        }
      }

      //store StringBuilder as String...
      String ZIPstring = ZIPbuilder.toString();

      //...and String as int, storing in zip
      zip = Integer.parseInt(ZIPstring);
    }
  }

  private void ZIPToBar()
  {
    //create int array to store ZIP code digits for loop operations
    int[] ZIParray = new int[5];

    //for assembling the barcode; begin with opening frame bar
    StringBuilder barBuilder = new StringBuilder("|");

    /* save ZIP code in new variable, ZIPloop, to perform
    * division operations. Then loop over ZIPloop, storing digits
    * in int array in reverse order.
    */
    int ZIPLoop = zip;

    for (int i = 4; i >= 0; i--)
    {
      int digit = ZIPLoop % 10;
      ZIParray[i] = digit;
      ZIPLoop /= 10;
    }

    /* for each digit in ZIParray, append the corresponding entry
    * in weightTable to barBuilder. Simultaneously, add
    * each digit in the array to ZIPsum, which will be used
    * to calculate the check digit.
    */
    int ZIPsum = 0;

    for (int i = 0; i < ZIParray.length; i++)
    {
      int compare = ZIParray[i];
      barBuilder.append(weightTable[compare]);
      ZIPsum += compare;
    }

    //find the first multiple of 10 greater than the sum of the digits
    int nearestTen = (int) Math.ceil(ZIPsum / 10.0) * 10;

    //calculate the check digit
    int checkDigit = nearestTen - ZIPsum;

    //append the corresponding digit code to the barcode
    barBuilder.append(weightTable[checkDigit]);

    //append the ending frame bar
    barBuilder.append("|");

    //store the StringBuilder (as a String) in barcode
    barcode = barBuilder.toString();
  }
}
