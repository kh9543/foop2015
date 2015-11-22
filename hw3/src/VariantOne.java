package assignment3;
import assignment3.Card;
import assignment3.Player;
import assignment3.OldMaid;

public class VariantOne extends OldMaid{
    public Card[] createDeck(){
        Card[] cardArray = new Card[52];
		for(int i=0; i<52; i++)
			cardArray[i] = new Card();
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
			int n = 13;
			if(i==3)
				n = 12;
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
    public Player whoIsLoser(){
        for(int i=0; i<4; i++)
            if(!players[i].checkWinner()){
                System.out.println("Player"+i+" is jiji-nuki");
                return players[i];
            }
        return players[0];
    }
}
