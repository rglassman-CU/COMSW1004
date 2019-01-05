# COMS W1004 Programming Project 5: Talk Scheduler
Course work for programming project 5 in Intro CS & Java. The task was to write a program that, given a text file containing a list of lectures and their start and end times, schedules the maximum number of talks in one room.

This was done using two classes:
- Talk, which models a talk and implements the comparable interface
- Scheduler, which implements the scheduling algorithm and includes methods for creating an ArrayList of talks and creating individual talks

Error handling was expected. Explanations of my implementation of the classes below: 

## Talk

The Talk class stores the name of the speaker, the start time, and the end time
as strings. It contains a series of error checks, which are called at
construction.

errorCheck() calls its branch checks and catches IndexOutOfBoundsExceptions,
which may be thrown by digitCheck() if somehow it is passed a very poorly
formatted input (which it won't if the Talk is instantiated via the
constructTalk() method in Scheduler).

All of the branch check methods throw NumberFormatExceptions:

digitCheck() throws an exception if any of the characters in the start or end
times are not digits. hourCheck() throws an exception if the first two digits
of the start or end time, as an integer, are greater than 24. minuteCheck()
throws an exception if the last two digits of the start or end time, as an
integer, are greater than 59. All of these exceptions include the name
associated with the talk that generated the exception, which hopefully makes it 
easier for the user to correct formatting errors.

CompareTo() converts the end time of both talks to be compared to integers and
uses Integer.compare() to see which is smaller (i.e., ends earlier). ToString()
returns a formatted string for neat printing.

## Scheduler 

Scheduler is a collection of four static methods: makeTalks(),
scheduleTalks(), constructTalk(), and cleanZeroes()

makeTalks() takes in a filename as a string. It constructs a new File object,
then iterates over each line of the file. It passes each line as a string to
constructTalk(), which first cleans the line of common characters such as
dashes, colons, numbered list elements, and AM and PM. It attempts to correctly
format numbers by adding missing zeroes using the cleanZeroes() method. If the
input file is correctly formatted, or close, the cleaned string should contain
a name and two numbers: start and end time. constructTalk() then scans the
cleaned line, makes a new talk using input.next(), and returns the new talk.
This talk is then added to the ArrayList in makeTalks(), provided it is not
null--constructTalks() returns null if an error occurs.

If any line of the input file contains AM or PM, makeTalks() issues a
warning that the times should be formatted in 24-hour format, but continues
building the list. If the times really are in 12-hour format, this will of
course cause issues--a useful improvement on this class would be to include
code for converting 12-hour to 24-hour format.

makeTalks() handles FileNotFoundExceptions; NoSuchElementExceptions, which are
thrown by constructTalk() if there are not three elements (name, start, end)
on a line; and NumberFormatExceptions, thrown by the Talk constructor and
explained above. It also has a general catch-all for RuntimeExceptions that
are not caught by the other three catches.

scheduleTalks() implements the algorithm we discussed in class; it first sorts
the provided array list by end time, then schedules the first talk (the one
that ends earliest). It looks at the next item in the provided list and checks
to see if it overlaps with the last scheduled talk. If it does not, that item
is scheduled.

scheduleTalks() handles IndexOutOfBoundsExceptions, which will occur if
makeTalks() was unsuccessful in adding any elements to its array list of
talks. In such a case, the array list passed to scheduleTalks() will be
empty.
