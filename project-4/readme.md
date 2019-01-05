# COMSW1004 Programming Project 4: Video Poker
Course work for programming project 4 in Intro CS & Java. The task was to model a game of video poker, using four classes: 
- Card, which models a playing card and allows comparison of cards by rank and suit
- Deck, which models a deck of cards and allows for shuffling of the deck and dealing of cards
- Player, which models a human player and a computer player and keeps track of a player's bets and amount of money
- Game, which models the game of poker and includes methods for evaluating the worth of a hand of cards

Error handling was not expected; students were allowed to assume correct input.
Explanation of my implementation of the classes below: 

## Card

The card class is simple; it has two integer instance variables, one for 
rank and the other for suit. There are also array instance variables that 
store the names of the suits and non-numeral cards, for usee in the toString 
method.

I implemented a third variable, called 'absRank'for absolute rank, which 
implements the suit of the card as a decimal value. For example, the two of 
clubs has an absolute rank of 2.0, while the two of diamonds has an absolute 
rank of 2.1. This allows cards to be compared and sorted first by rank, then 
by suit, without needing multiple levels of comparison. The compareTo method 
simply sorts by using Double.compare on the absolute rank of the cards in 
question.

## Deck

The deck class is also simple. The constructor creates a 52-card deck using an
outer-inner loop structure; the outer loop is for suits, and the inner loop is 
for rank. The inner loop creates a card of the designated rank and suit, and 
assigns it to the correct position in the deck. The shuffle method generates 
two random numbers between 0 and 51, and exchanges the cards at the
corresponding positions in the array, 10,000 times. 

## Player

I opted not to use the removeCard method. It remains in the class for future
use, but for the purposes of this assignment, it made much more sense to 
create a replaceCard method using ArrayList.set. This way, the array list
indices are preserved while multiple cards are removed and replaced, 
eliminating the need for a significantly more complex method for exchanging 
cards. 

The toString method uses the toString method of the card class, and the sort 
method uses Collections.sort on the cards in the hand.

## Game

This class includes an int instance variable, odds, for storing the multiplier
value of the player's hand, and a String instance variable bestHand, for 
storing the name of that hand. 

All of the hand check methods, if they evaluate to true, call the betterHand
method, which updates odds and bestHand only if the hand in question is better
than what is currently stored (that is, if the worth of the hand being checked 
is greater than the number stored in odds). The checkHand method evaluates the 
hands from best possible to worst possible, but with the betterHand method,
technically this is not necessary.

There are multiple methods that implement individual pieces of gameplay (such 
as taking the player's bet, displaying the result and winnings, and so on), 
so that the play method remains slim and straightforward. The play method 
largely exists within a while loop, so that the game continues only while the
player has funds left in their bankroll and while they want to continue
playing. At the end of each round, the playAgain method asks the player if 
they would like to play again and returns a boolean, which is used to either
shuffle and reset the player's hand and bet before the loop restarts or to
exit the loop and end the game. 
