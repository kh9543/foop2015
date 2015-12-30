package assignment4;
import foop.*;
import java.util.*;


public class Deck {
    private ArrayList<Card> myDeck = new ArrayList<Card>();
    public Deck(){
        for(int i=1; i<5; i++)
            for(int j=1; j<14; j++)
                myDeck.add(new Card((byte)i,(byte)j));
        this.shuffleDeck();
    }
    public void shuffleDeck()
    {
        Collections.shuffle(myDeck);
    }
    public ArrayList<Card> getDeck()
    {
        for(int i=0; i<myDeck.size(); i++)
        {
            System.out.println(myDeck.get(i).getSuit()+":"+myDeck.get(i).getValue());
        }
        return myDeck;
    }
    public ArrayList<Card> assingCards()
    {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        if(myDeck.size() < 2)
            System.out.println("Error: Deck Emptied");
        else{
            cardArray.add(myDeck.get(0));
            cardArray.add(myDeck.get(1));
            myDeck.remove(0);
            myDeck.remove(0);
        }
        return cardArray;
    }
    public Card assingCard()
    {
        Card rst;
        if(myDeck.size() < 1)
            System.out.println("Error: Deck Emptied");
        rst = myDeck.get(0);
        myDeck.remove(0);
        return rst;
    }
}
