package assignment3;
import java.util.*;
import assignment3.Card;
import assignment3.Player;

public class OldMaid{
    Card[] cardArray = createDeck();
    Player[] players = dealCards(cardArray);
    public Card[] createDeck(){
        //Create Card array
		//Factory producing cards ...
		//shuffle the card
        Card[] cardArray = new Card[54];
		for(int i=0; i<54; i++)
			cardArray[i] = new Card();
		cardArray[52].modCard("0", "R", -1);
		cardArray[53].modCard("0", "B", -2);
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
        shuffleCard(cardArray);
        return cardArray;
    }
    public Player[] dealCards(Card[] cardArray){
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
        return players;
    }
    public void playerDropCards(Player[] players){
        //Drop Cards
		System.out.println("Drop cards");
		for(int i = 0; i < 4; i++)
		{
			players[i].dropCard();
			players[i].showPlayer();
		}
    }
    public void gameStart(){
        playerDropCards(players);
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
	public void shuffleCard(Card[] array){
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
