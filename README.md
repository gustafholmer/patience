# patience
Patience (Solitaire) game written in Java

# Author: Gustaf Holmer

# How to run the program:
Compile, then run Solitaire.java.

# Program structure:
I have organized the program that it has a total of six classes, one of which is an abstract class from which three subclasses inherit. These three subclasses are the figures: square, circle and triangle. My program consists of 11 classes where two of them are abstract. The program also imports images from a folder that has all the gif files with the front and back of the cards.

# Solitaire.java
Is the main class that activates the program by creating a "playArea" object based on PlayArea.java and gives it a size and makes it visible. The buttons that control how the card deck is set are also here together with the button that turns on the Talong pile.

# PlayArea.java
This object handles everything that goes on in the program during the game. In the class there are several arraysLists that contain the different piles. There is also an arrayList for all the cards in the game. There are many important methods here that take care of everything from laying out the cards and piles at the start of the game to handling the movement of cards between different piles.
- CardDealer sorts all the cards in the right place using PlayType.java
- addCardOutlines lays out all the markings for the piles
- releaseCardInPile / doNotReleaseCardInPile ensures that a new card is created upon accepted / denied card transfer
- checkWhichPile is important as it is used a lot in the program to keep track of how high the card is loaded.
- cardnTop makes sure to turn and lock the cards in the piles

# MouseListener.java / ButtonClickListener.java
Used in PlayArea.java to record the interaction of the mouse / buttons with the game.

# PileOutline.java
Is the abstract class from which all piles inherit. Each inherited arrow has different types of parameters which type of card they allow.
