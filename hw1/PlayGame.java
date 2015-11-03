import java.util.*;
class Card {
	private String suit;
	private String number;
	private int value;
	public Card(){
		suit = "UDF";
		number = "N";
	}
	public Card(String num, String s, int v){
		number = num;
		suit = s;
		value = v;
	}
	void modCard(String num, String s, int v){
		number = num;
		suit = s;
		value = v;
	}
	void copyCard(Card c){
		number = c.getNum();
		suit = c.getSuit();
		value = c.value;
	}
	void showCard() {
		System.out.print(" "+this.suit);
		System.out.print(this.number);
	}
	String getNum() {
		return this.number;
	}
	String getSuit(){
		return this.suit;
	}
	int getValue(){
		return this.value;
	}
	boolean biggerThen(Card c){
		if (this.getSuit().equals("R"))
				return false;
		else if (c.getSuit().equals("R"))
				return true;
    else if (this.getValue() > c.getValue())
				return true;
		else if (this.getValue() < c.getValue())
				return false;
		else
			if(this.suit.compareTo(c.getSuit())>0)
				return true;
			else
				return false;
	}
}

class Player {
	private int ID;
	private int cardNum;
	boolean win = false;
	private Card[] cardSet = new Card[15];
	public Player() {
		for(int i=0; i<15; i++)
			cardSet[i] = new Card();
	}

	void modPlayer(int i, int n, int offset, Card[] array){
		ID = i;
		cardNum = n;
		for(int j=0; j<n; j++)
			cardSet[j].copyCard(array[offset+j]);
	}

	void showPlayer(){
		System.out.print("Player"+ID+":");
		if (cardNum==0)
		{
			System.out.println("");
			return;
		}
		for(int i=0; i<cardNum; i++)
			cardSet[i].showCard();
		System.out.println("");
	}

	void dropCard(){
		boolean found = true;
		while(found){
			if(cardNum < 2)
				break;
			for(int i=0; i<cardNum-1; i++)
			{
				found = false;
				if(cardSet[i].getNum().equals(cardSet[i+1].getNum()) && !cardSet[i].getNum().equals("0"))
				{
					cardSet[i].copyCard(cardSet[cardNum-1]);
					cardSet[i+1].copyCard(cardSet[cardNum-2]);
					cardNum = cardNum - 2;
					this.sortCard();
					found = true;
					break;
				}
				if(i==(cardNum-2))
				{
					found = false;
					break;
				}
			}
		}
	}

	boolean noCard() {
		if(cardNum == 0)
			return true;
		else
			return false;
	}

	Card lossCard(int i) {
		Card temp = new Card(cardSet[i].getNum(), cardSet[i].getSuit(), cardSet[i].getValue());
		cardSet[i].copyCard(cardSet[cardNum-1]);
		cardNum--;
		this.sortCard();
		return temp;
	}

	void getCard(Card c) {
		cardNum++;
		cardSet[cardNum-1].copyCard(c);
		this.sortCard();
		this.dropCard();
		this.showPlayer();
	}

	void sortCard(){
		if (cardNum==0 || cardNum==1)
			return;
		Card temp;
		int i,j;
		for (i=1; i<cardNum; i++)
		{
			temp = new Card(cardSet[i].getNum(), cardSet[i].getSuit(), cardSet[i].getValue());
			for (j=i-1; j>=0 && cardSet[j].biggerThen(temp); j--)
				cardSet[j+1].copyCard(cardSet[j]);
			cardSet[j+1].copyCard(temp);
		}
	}
	int report(){
		return cardNum;
	}
	boolean checkWinner(){
		if(this.noCard())
			return (win=true);
		else
			return false;
	}

}

public class PlayGame {
	public static void main(String[] argv){
		//Create Card array
		Card[] cardArray = new Card[54];
		for(int i=0; i<54; i++)
			cardArray[i] = new Card();
		cardArray[52].modCard("0", "R", -1);
		cardArray[53].modCard("0", "B", -2);

		//Factory producing cards ...
		for(int i=0; i<52; i++)
		{
			String temp;
			int v;
			if((i%13)==0)
			{
				v = 14;
				temp = "A";
			}
			else if((i%13)==12)
			{
				v = 13;
				temp = "K";
			}
			else if((i%13)==11)
			{
				v = 12;
				temp = "Q";
			}
			else if((i%13)==10)
			{
				v = 11;
				temp = "J";
			}
			else
			{
				v = i%13 + 1;
				temp = Integer.toString(i%13+1);
			}
			if((i%4)==0)
				cardArray[i].modCard(temp, "C", v);
			else if((i%4)==1)
				cardArray[i].modCard(temp, "D", v);
			else if((i%4)==2)
				cardArray[i].modCard(temp, "H", v);
			else
				cardArray[i].modCard(temp, "S", v);

		}
		//shuffle the card
 		shuffleCard(cardArray);
		//start dealing cards
		System.out.println("Deal cards");

		Player[] players = new Player[4];

		int count = 0;
		for(int i = 0; i < 4; i++)
		{
			players[i] = new Player();
			int n = 14;
			if(i==2 || i==3)
				n = 13;
			players[i].modPlayer(i, n, count, cardArray);
			count += n;
		}
		for(int i = 0; i < 4; i++)
		{
			players[i].sortCard();
			players[i].showPlayer();
		}

		//Drop Cards
		System.out.println("Drop cards");

		for(int i = 0; i < 4; i++)
		{
			players[i].dropCard();
			players[i].showPlayer();
		}

		//Game Start
		System.out.println("Game start");
		int round = 0;
		boolean end = false;
		while (!end)
		{
			int winners=0;
			for(int i=0; i<4; i++)
			{
				if(players[i].noCard())
				{
					if(end)
						System.out.print(" and ");
					System.out.print("Player"+i);
					end = true;
					winners++;
				}
			}
			if(end && winners ==1)
			{
				System.out.println(" wins");
				System.out.println("Basic game over");
				break;
			}
			else if(end && winners ==2)
			{
				System.out.println(" win");
				System.out.println("Basic game over");
				break;
			}
			int toID = round%4;
			int fromID = (round+1)%4;
			Random r = new Random();
			Card lostCard = new Card();
			lostCard.copyCard(players[fromID].lossCard(r.nextInt(players[fromID].report())));
			System.out.println("Player"+toID+" draws a card from Player"+fromID+" "+lostCard.getSuit()+lostCard.getNum());
			players[toID].getCard(lostCard);
			players[fromID].showPlayer();
			round++;
		}

		//Bonus Game
		System.out.println("Continue");
		boolean bonusEnd = false;
		boolean mWinner = false;
		round--;
		int toID = round%4;
		int fromID = (round+1)%4;
		while (!bonusEnd)
		{
			int winners = 0;
			for(int i=0; i<4; i++)
			{
				if(players[i].checkWinner())
					winners ++;
			}
			if(winners == 3)
			{
				System.out.println("Bonus game over");
				bonusEnd = true;
				break;
			}

			if(!players[fromID].checkWinner())
			{
				toID = fromID;
				fromID = (fromID+1)%4;
				while(players[fromID].checkWinner())
					fromID = (fromID+1)%4;
			}
			else
			{
				toID = (fromID+1)%4;
				while(players[toID].checkWinner())
					toID = (toID+1)%4;
				fromID = (toID+1)%4;
				while(players[fromID].checkWinner())
					fromID = (fromID+1)%4;
			}

			if(toID!=fromID)
			{
				Random r = new Random();
				Card lostCard = new Card();
				lostCard.copyCard(players[fromID].lossCard(r.nextInt(players[fromID].report())));
				System.out.println("Player"+toID+" draws a card from Player"+fromID+" "+lostCard.getSuit()+lostCard.getNum());
				players[toID].getCard(lostCard);
				players[fromID].showPlayer();
				if(players[toID].noCard() && players[fromID].noCard())
					System.out.println("Player"+toID+" and Player"+fromID+" win");
				else if(players[toID].noCard())
					System.out.println("Player"+ toID+" wins");
				else if(players[fromID].noCard())
					System.out.println("Player"+ fromID+" wins");
				round++;
			}
		}
	}
	public static void shuffleCard(Card[] array){
 		Random r = new Random();
		Card temp = new Card();
		for(int i = array.length-1 ; i > 0; i--)
		{
	       int index = r.nextInt(i+1);
		   temp.copyCard(array[index]);
		   array[index].copyCard(array[i]);
		   array[i].copyCard(temp);
		}
	}
}
