package assignment2;
import java.lang.Enum;
import assignment2.Card;

public class Player{
    //Player Info
    private String playerName;
    private int fortune;
    private boolean playing;
    private Card[] myCard;
    private String setType;
    private int rounds, cardNums;
    //Player mods
    public Player(){
        playerName = "UDF";
        fortune = -1;
        playing = true;
        cardNums = 0;
        rounds = 1;
        myCard = new Card[5];
        for(int i=0; i<5; i++)
            myCard[i] = new Card();
    }
    public void setPlayer(String name, int p)
    {
        playerName = name;
        fortune = p;
    }
    //Player gets card from deck
    public void getCard(Card c){
        if(cardNums == 5)
            return;
        else{
            myCard[cardNums].copyCard(c);
            cardNums++;
        }
    }
    //Player change card of index i on hand
    public void changeCard(Card c, int i){
        if( (i < 0) || (i > 5))
            return;
        myCard[i].copyCard(c);
    }
    //Mod fortune the boolean value stands for add(true) or deduct(false)
    public void modFortune(int p, boolean add){
        if(add)
            fortune += p;
        else
            fortune -= p;
    }
    //Return fortune status
    public int getP (){
        return fortune;
    }
    //Return playerName
    public String getName (){
        return playerName;
    }
    //get the hand Cards
    public Card[] getHandCard(){
        return myCard;
    }
    //Return current round
    public int getRound (){
        return rounds;
    }
    //Show the player cards they can keep
    public void displayCardChoice(){
        sortCard(myCard, 5);
        char array[] = {'a','b','c','d','e'};
        for(int i=0; i<5; i++)
        {
            System.out.print(" (" + array[i] + ")");
            myCard[i].showCard();
        }
    }
    //Player display final result of hand
    public void displayCardResult(){
        sortCard(myCard, 5);
        for(int i=0; i<5; i++)
            myCard[i].showCard();
        rounds ++;
        cardNums = 0;
    }
    //Player get type result from computer
    public void modSetType(String s){
        setType = s;
    }
    //Player print out set type
    public void printResult(){
        System.out.print(setType);
    }
    //Player Quits game
    public void quitGame(){
        playing = false;
    }
    //Check if Player is playing
    public boolean isPlaying(){
        return playing;
    }
    //Sort Card function
    public void sortCard(Card[] cardSet, int cardNum){
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
}
