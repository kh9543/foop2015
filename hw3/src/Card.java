package assignment3;

public class Card {
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
	public void modCard(String num, String s, int v){
		number = num;
		suit = s;
		value = v;
	}
	public void copyCard(Card c){
		number = c.getNum();
		suit = c.getSuit();
		value = c.value;
	}
	public void showCard() {
		System.out.print(" "+this.suit);
		System.out.print(this.number);
	}
	public String getNum() {
		return this.number;
	}
	public String getSuit(){
		return this.suit;
	}
	public int getValue(){
		return this.value;
	}
	public boolean biggerThen(Card c){
        if (this.getValue() > c.getValue())
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
