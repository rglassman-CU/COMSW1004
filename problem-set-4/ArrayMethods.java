/**
* ArrayMethods - Manipulate Array Contents
*
* COMS W1004
*
* Ryan Glassman
* rmg2203
* 10/26/18
*/

import java.util.Arrays;
import java.util.ArrayList;

public class ArrayMethods {

  // private instance variables
  private int[] values;
  // this is for  resetting purposes, otherwise not needed
  private int[] originalValues;
  private int last;

  //constructor
  public ArrayMethods(int[] initialValues) {
    values = initialValues;
    originalValues = Arrays.copyOf(values, values.length);
    last = values.length - 1;
  }

  // pretty printing for testing purposes
  public void printArray() {
    for (int i : values) {
      System.out.print(i + " ");
    }
    System.out.println("");
  }

  // resetting back to original values for testing purposes
  public void resetValues() {
    values = Arrays.copyOf(originalValues, originalValues.length);
  }

  // a. swap the first and last elements
  public void swapFirstAndLast() {
    // your code here
    int temp = values[0];
    values[0] = values[last];
    values[last] = temp;
  }

  // b. shift all element to right and wraparound
  public void shiftRight() {
    // your code here
    int temp = values[last];
    for (int i = values.length - 2; i >= 0; i--) {
      values[i+1] = values[i];
    }

    values[0] = temp;
  }

  // c. replace even elements with zero
  public void replaceEven() {
    // your code here
    for (int i = 0; i < values.length; i++) {
      if (values[i] % 2 == 0) {
        values[i] = 0;
      }
    }
  }

  // d. replace middle elements with larger of two neighbors
  public void replaceMiddle() {
    // your code here
    int compareLeft = values[0];
    int hold = values[1];

    for (int i = 1; i < last; i++) {
      int replace = Math.max(compareLeft, values[i+1]);
      compareLeft = values[i];
      values[i] = replace;
      hold = values[i+1];
    }
  }

  // e. Remove middle one or two elements
  public void removeMiddle() {
    // your code here

    int median = values.length / 2;

    //odd case
    if (values.length % 2 != 0) {
      int[] shorter = new int[values.length - 1];

      int j = 0;
      for (int i = 0; i < values.length; i++) {
        if (i != median) {
          shorter[j] = values[i];
          j++;
        }
      }
      values = shorter;
    }

    //even case
    else {
      int[] shorter = new int[values.length - 2];

      int j = 0;
      for (int i = 0; i < values.length; i++) {
        if (i != median && i != median - 1) {
          shorter[j] = values[i];
          j++;
        }
      }
      values = shorter;
    }
  }

  // f. Move even elements to the front
  public void moveEven() {

    int evenCount = 0;

    for (int element : values) {
      if (element % 2 == 0) {
        evenCount++;
      }
    }

    int[] evens = new int[evenCount];
    int[] odds = new int[values.length - evenCount];

    int evenPos = 0;
    int oddPos = 0;

    for (int i = 0; i < values.length; i++) {
      if (values[i] % 2 == 0) {
        evens[evenPos] = values[i];
        evenPos++;
      }
      else {
        odds[oddPos] = values[i];
        oddPos++;
      }
    }

    for (int i = 0; i < evens.length; i++) {
      values[i] = evens[i];
    }

    oddPos = 0;
    for (int i = evens.length; i < values.length; i++) {
      values[i] = odds[oddPos];
      oddPos++;
    }

  }

  // g. Return second-largest element
  public int secondLargest() {
    // your code here
    int largest = values[0];

    for (int element : values) {
      largest = Math.max(largest, element);
    }

    int secondLargest = values[0];
    for (int element : values) {
      if (element != largest) {
        secondLargest = Math.max(element, secondLargest);
      }
    }
    return secondLargest;
  }

  // h. Check if sorted in increasing order
  public boolean sortedIncreasing() {
    // your code here
    boolean increasing = true;

    for (int i = 0; i < last; i++) {
      if (values[i] > values[i+1]) {
        increasing = false;
      }
    }

    return increasing;
  }

  // i. Check if contains two adjacent duplicate elements
  public boolean adjacentDups() {
    // your code here
    boolean duplicates = false;

    for (int i = 0; i < last; i++) {
      if (values[i] == values[i+1]) {
        duplicates = true;
      }
    }
    return duplicates;
  }

  // j. Check if contains any duplicate elements
  public boolean containsDups() {

    boolean duplicates = false;

    for (int i = 0; (i < values.length) && (!duplicates); i++) {
      int index = values[i];
      int j = i + 1;
      while ((j < values.length) && (!duplicates)) {
        if (values[j] == index) {
          duplicates = true;
        }
        else {
          j++;
        }
      }

    }
    return duplicates;
  }
}
