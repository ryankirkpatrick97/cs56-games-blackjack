package edu.ucsb.cs56.projects.game.blackjack;

import java.util.*;
import java.io.*;

/** Blackjack Class has the following: Deck, ArrayList (list for players), Player (dealer), 
 *and Card (next card to show). This class also contains methods for hitting and staying. 
 @author Brian Wan
 @author Fanny Kuang
 @author Eric Palyan
 @version 2014.02.27
*/

public class Blackjack{
    private Deck d;
    public ArrayList<Player> players;
    private Player dealer;
    private Card displayCard;
    public int p1wins, p2wins, p3wins, p1losses, p2losses, p3losses;
    public int p1won, p2won, p3won, p1lost, p2lost, p3lost;
    
    /** constructor
     */
    public Blackjack(){
	d = new Deck();
	dealer = new Player();
	dealer.drawCard(d.draw());
	dealer.drawCard(d.draw());
	dealer.setName("Dealer");
	
	players = new ArrayList<Player>();
	for(int i=0; i < 3; i++){
	    Player newPlayer = new Player();
	    newPlayer.drawCard(d.draw());
	    newPlayer.drawCard(d.draw());
	    players.add(newPlayer);
	}
	
	displayCard = null;
    }
    
    public void saveStats() {
    	switch(players.size()) {
    		case 1: try {
    		File file1 = new File("Stats1.txt");
    		FileWriter writer1 = new FileWriter(file1);
    		writer1.write(p1wins + " " + p1losses + " " + p1won + " " + p1lost + "\n");
    		writer1.close();
    		} catch(Exception ex) { } 
    		case 2: try {
    		File file1 = new File("Stats1.txt");
    		File file2 = new File("Stats2.txt");
    		FileWriter writer1 = new FileWriter(file1);
    		FileWriter writer2 = new FileWriter(file2);
    		writer1.write(p1wins + " " + p1losses + " " + p1won + " " + p1lost + "\n");
    		writer2.write(p2wins + " " + p2losses + " " + p2won + " " + p2lost + "\n");
    		writer1.close();
    		writer2.close();
    		} catch(Exception ex) { } 
    	        case 3: try {
    		File file1 = new File("Stats1.txt");
    		File file2 = new File("Stats2.txt");
    		File file3 = new File("Stats3.txt");
    		FileWriter writer1 = new FileWriter(file1);
    		FileWriter writer2 = new FileWriter(file2);
    		FileWriter writer3 = new FileWriter(file3);
    		writer1.write(p1wins + " " + p1losses + " " + p1won + " " + p1lost + "\n");
    		writer2.write(p2wins + " " + p2losses + " " + p2won + " " + p2lost + "\n");
    		writer3.write(p3wins + " " + p3losses + " " + p3won + " " + p3lost + "\n");
    		writer1.close();
    		writer2.close();
    		writer3.close();
    		} catch(Exception ex) { }
    	}
    }
    
    public void resetStats() {
    	p1wins = p2wins = p3wins = p1losses = p2losses = p3losses = p1won = p2won = p3won = p1lost = p2lost = p3lost = 0;
    }
    
    public void loadStats() {
    	try {
    	File file = new File("Stats.txt");
    	BufferedReader reader = new BufferedReader(new FileReader(file));
	String line;
	
	line = reader.readLine();
	String [] stats1 = line.split("\\s+");
	p1wins = Integer.parseInt(stats1[0]); p1losses = Integer.parseInt(stats1[1]); p1won = Integer.parseInt(stats1[2]); p1lost = Integer.parseInt(stats1[3]);
	line = reader.readLine();
	String [] stats2 = line.split("\\s+");
	p2wins = Integer.parseInt(stats2[0]); p2losses = Integer.parseInt(stats2[1]); p2won = Integer.parseInt(stats2[2]); p2lost = Integer.parseInt(stats2[3]);
	line = reader.readLine();
	String [] stats3 = line.split("\\s+");
	p3wins = Integer.parseInt(stats3[0]); p3losses = Integer.parseInt(stats3[1]); p3won = Integer.parseInt(stats3[2]); p3lost = Integer.parseInt(stats3[3]);
	
	reader.close();
    	} catch(Exception ex) { }
    }
    
    /** 4 arg constructor for testing purposes
	@param dh Dealer's hand
	@param p1 Player 1's hand
	@param p2 Player 2's hand
	@param p3 Player 3's hand
    */
    public Blackjack(Hand dh, Hand p1, Hand p2, Hand p3){
	d = new Deck();
	dealer = new Player();
	dealer.setHand(dh);
	dealer.setName("Dealer");
	
	players = new ArrayList<Player>();
	for(int i=0; i < 3;){
	    Player newPlayer = new Player();
	    newPlayer.setName("Player " + ++i);
	    players.add(newPlayer);
	}
	players.get(0).setHand(p1);
	players.get(1).setHand(p2);
	players.get(2).setHand(p3);
	
	displayCard = null;
    }
    /** initializes a new round by dealing new hands to every player
     */
    public void newRound(){
	d = new Deck();
	players.get(0).newHand(d); players.get(0).resetNumberOfCards();
	players.get(1).newHand(d); players.get(1).resetNumberOfCards();
	players.get(2).newHand(d); players.get(2).resetNumberOfCards();
	dealer.newHand(d);
    }
    
    /** returns false if the dealer busted
     */
    public boolean dealerNotBust(){
	return dealer.isNotBust();
    }
    
    /** returns reference to dealer Player
     */
    public Player getDealer(){
	return dealer;
    }
    
    /** returns reference to Player within players ArrayList according to index
	@param index integer of player (i.e. 1, 2, or 3)
    */
    public Player getPlayer(int index){
	return players.get(index-1);
    }
    
    /** returns reference to Player in South
     */
    public Player getPlayerS(){
	if(players.size() > 0)
	    return players.get(0);
	return null;
    }
    
    /** returns reference to Player in East
     */
    public Player getPlayerE(){
	if(players.size() > 1)
	    return players.get(1);
	return null;
    }
    
    /** returns reference to Player in West
     */
    public Player getPlayerW(){
	if(players.size() > 2)
	    return players.get(2);
	return null;
    }
    
    /** returns the value of the dealer's hand with aces valued at 1
     */
    public int dealerHandValue(){
	return dealer.getHandValue();
    }
    
    /** returns the value of the dealer's hand with aces valued at 11 or returns -1 if the value 
     *  of the dealer's hand with aces valued at 11 makes the value greater than 21
     */
    public int dealerSecondValue(){
	return dealer.getSecondHandValue();
    }
    
    /** returns a reference to the Card that the dealer is showing
     */
    public Card getDealerCard(){
	return dealer.getHand().getFirstCard();
    }
    
    /** makes the dealer hit and returns a reference to the card he drew so it can be displayed
     */
    public Card dealerHit(){
	displayCard = d.draw();
	dealer.drawCard(displayCard);
	return displayCard;
    }
    
    /** returns a reference to the dealer's hand
     */
    public Hand getDealerHand(){
	return dealer.getHand();
    }
    
    /** makes the player passed as a parameter hit and returns a reference to the card drawn so
     *  it can be displayed
     @param player 
    */
    public Card playerHit(Player player){
	displayCard = d.draw();
	player.drawCard(displayCard);
	player.setNumberOfCards(1); // add number of cards by 1
   
	return displayCard;
    }
    
    /** decides whether or not the dealer should stay i.e. dealer has 17 or 
     *  higher
     */
    public boolean dealerShouldStay(){
	if(dealerHandValue() >= 17 || dealerSecondValue() >= 17)
	    return true;
	return false;	
    }
    
    /** returns true if dealer has blackjack
     */
    public boolean dealerHasBlackjack(){
	return dealer.hasBlackjack();
    }
    
    /** returns true if player passed to method has blackjack
	@param player to check for blackjack
    */
    public boolean playerHasBlackjack(Player player){
	return player.hasBlackjack();
    }
    
    /** formats a String to return the value of just the dealer's first card during the player's turn
     */
    public String displayDealerCardValue(){
	return "Dealer's hand value: " + dealer.getHand().getFirstCard().getValue();
    }
    
    /** evaluates the winner of the game
	@param player to compare if won against dealer
    */
    public char evaluateWinner(Player player){
	if(!player.isNotBust()) // if player is bust, dealer wins
	    return 'D';
	else if(!dealerNotBust()) // if dealer bust, player wins
	    return 'P';
	else if(dealer.hasBlackjack()) // if dealer has a blackjack, dealer wins
	    return 'D';
	else if(player.hasBlackjack()) // if player has a blackjack, player wins
	    return 'P';
	else if (dealer.getNumberOfCards() == 5) // if dealer has a 5 card charlie, dealer wins
	    return 'D';
	else if (player.getNumberOfCards() == 5) // if player has a 5 card charlie, player wins 
	    return 'P';
	else if(dealer.getSecondHandValue() >= 
		player.getSecondHandValue() && 
		dealer.getSecondHandValue() >= 
		player.getHandValue())
	    return 'D';
	else if(dealer.getHandValue() >= player.getSecondHandValue() && dealer.getHandValue() >= player.getHandValue())
	    return 'D';
	else
	    return 'P';
    }
}//class blackjack
