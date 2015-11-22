package assignment3;
import assignment3.Card;

public class Player {
	private int ID;
	private int cardNum;
	boolean win = false;
	private Card[] cardSet = new Card[15];
	public Player() {
		for(int i=0; i<15; i++)
			cardSet[i] = new Card();
	}
	public int reportID() {
		return ID;
	}
	public void modPlayer(int i, int n, int offset, Card[] array){
		ID = i;
		cardNum = n;
		for(int j=0; j<n; j++)
			cardSet[j].copyCard(array[offset+j]);
	}
	public void showPlayer(){
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
	public void dropCard(){
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
	public boolean noCard() {
		if(cardNum == 0)
			return true;
		else
			return false;
	}
	public Card lossCard(int i) {
		Card temp = new Card(cardSet[i].getNum(), cardSet[i].getSuit(), cardSet[i].getValue());
		cardSet[i].copyCard(cardSet[cardNum-1]);
		cardNum--;
		this.sortCard();
		return temp;
	}
	public void getCard(Card c) {
		cardNum++;
		cardSet[cardNum-1].copyCard(c);
		this.sortCard();
		this.dropCard();
		this.showPlayer();
	}
	public void sortCard(){
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
	public int report(){
		return cardNum;
	}
	public boolean checkWinner(){
		if(this.noCard())
			return (win=true);
		else
			return false;
	}
}
