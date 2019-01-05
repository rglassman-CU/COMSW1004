# COMSW1004 Programming Project 3: Nim & Zipcode
Course work for programming project 3 in Intro CS & Java. There were two tasks:

1: model a game of Nim with a computer player that has two modes; easy and smart mode. Easy mode selects a random legal move, while smart mode plays a strategy that is unbeatable under most conditions. 
2: write a program that can convert a ZIP code to a USPS barcode, and vice versa. 

Error handling was not required; students could assume correct input. There was some confusion about the storing of zip as an int and handling of leading zeroes. 
Explanation of my solutions is below:

## Nim 

I created separate move methods within the Game class, humanMove and 
compMove, to deal with the ways in which the user moves affected the game
conditions. These methods call the move methods from the Human and Computer 
classes, and then adjust the game parameters according to the outputs of 
those methods. This allowed me to keep the play method clean and simple, and
easy to understand.

For the Computer class, I created two methods, dumbMove and smartMove, to 
define computer player behavior based on difficulty. dumbMove selects a random
legal move, and smartMove selects the move that leaves 2^n - 1 marbles in the
pile, as the prompt instructed. smartMove uses dumbMove if the ideal move is 
not possible, i.e. if the human player has left 2^n - 1 marbles in the pile 
already.

The Human class is pretty simple. It asks the user to make a move, and then
checks if the move is legal; if it’s not, the user is asked to pick another 
move.

## Zipcode 

First off, to store the barcode conversion table in the prompt, I created a 
string array, weightTable, as an instance variable. This way both conversion 
methods could access it.

The real workhorse methods in this class are BarToZIP and ZIPToBar, which do
what they sound like they do; convert a barcode to a ZIP code, and vice versa.
These methods are longer than I would have liked, but I didn’t see any good 
opportunities to split them up. Each method really does do one thing, even
though they take a couple of steps to get there.

The Zipcode constructor is overloaded. If the user provides an integer zipcode,
it is stored in ZIP and ZIPToBar is called to construct the corresponding 
barcode. If the user provides a string barcode, the reverse is done, with 
BarToZIP. 

BarToZIP loops through a barcode, creating five character substrings and 
comparing them to the entries in weightTable. When it finds the weightTable 
entry that matches the substring, it appends the corresponding index position 
(not the entry) in weightTable to a StringBuilder. This is done because the
index position in weightTable also corresponds to the represented digit; i.e., 
weightTable[0] is the barcode representation of the digit 0, and so forth. 

I used a StringBuilder rather than constructing an integer because some ZIP 
codes start with 0, even though the TAs said their test classes would not. I
realized after the fact that as long as zip is stored as an int, it will 
always drop the leading zero, but using my methodology, if you wanted to solve
that problem all you’d need to do is store zip as a string. You wouldn’t need
to rewrite the method.

ZIPToBar first stores each digit in a ZIP code in an int array, ZIParray, and
creates a StringBuilder for assembling the barcode, pre-loaded with the 
leading frame bar. It then does two things at once as it loops over ZIParray.
For each entry:

1. The entry at the corresponding index position in weightTable is appended to
a StringBuilder. For example, if the first entry in ZIParray is 3, 
weightTable[3], or ::||:, is appended to the StringBuilder.

2. The entry is added to ZIPsum, which keeps the sum of the digits in the ZIP
code for later use in calculating the check digit.

The check digit is then calculated, and the corresponding entry in weightTable
is appended to the StringBuilder. The ending frame bar is added and the builder 
is saved as a string in barcode.
