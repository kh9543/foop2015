package assignment2;
import assignment2.Shuffler;
import assignment2.Card;
import assignment2.Player;

public class Computer {
    private int bet;
    private int distPointer; //To record the distribution of deck
    private Card[] deck;
    private Shuffler shMachine;
    public Computer()
    {
        distPointer = 0;
        deck = new Card[52];
        shMachine = new Shuffler();
        for(int i=0; i<52; i++)
        {
            deck[i] = new Card();
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
                deck[i].modCard(temp, "C", v);
            else if((i%4)==1)
                deck[i].modCard(temp, "D", v);
            else if((i%4)==2)
                deck[i].modCard(temp, "H", v);
            else
                deck[i].modCard(temp, "S", v);
        }
        shuffleCards();
    }

    public void shuffleCards(){
        for (int i = 0; i<52; i++)
            deck[i].copyCard(shMachine.shuffle(deck)[i]);
    }
    //Distribute distNum of cards to player
    public void distributeCards(Player p, int distNum){
        if (distNum > 5)
            return;
        for (int i=0; i<distNum; i++)
        {
            p.getCard(deck[distPointer]);
            distPointer++;
        }
    }
    public void discardSet(Player p, String s){
        boolean change[] = {true, true, true, true, true};
        if(s.compareTo("n") == 0)
        {
            for(int i = 0; i < 5; i++)
                change[i] = true;
        }
        else{
            if(s.contains("a"))
                change[0] = false;
            if(s.contains("b"))
                change[1] = false;
            if(s.contains("c"))
                change[2] = false;
            if(s.contains("d"))
                change[3] = false;
            if(s.contains("e"))
                change[4] = false;
        }
        for(int i=0; i<5; i++)
        {
            if(change[i])
            {
                p.changeCard(deck[distPointer], i);
                distPointer++;
            }
        }
        distPointer = 0;
        shuffleCards();
    }

    public void deductP(Player p, int x){
        if ((x<1) || (x>5))
            return;
        else{
            p.modFortune(x, false);
            bet = x;
        }
    }
    //beneath are functions to examine results
    public int checkAndPay(Player p){
        int payOff = 0;
        if(check_royal_flush(p.getHandCard())){
            p.modSetType("royal flush");
            if(bet == 5)
                payOff = 4000;
            else
                payOff = bet * 250;
        }
        else if(check_straight_flush(p.getHandCard())){
            p.modSetType("straight flush");
            payOff = bet * 50;
        }
        else if(check_four_of_a_kind(p.getHandCard())){
            p.modSetType("four of a kind");
            payOff = bet * 25;
        }
        else if(check_full_house(p.getHandCard())){
            p.modSetType("full House");
            payOff = bet * 9;
        }
        else if(check_flush(p.getHandCard())){
            p.modSetType("flush");
            payOff = bet * 6;
        }
        else if(check_straight(p.getHandCard())){
            p.modSetType("straight");
            payOff = bet * 4;
        }
        else if(check_three_of_a_kind(p.getHandCard())){
            p.modSetType("three of a kind");
            payOff = bet * 3;
        }
        else if(check_two_pair(p.getHandCard())){
            p.modSetType("two pair");
            payOff = bet * 2;
        }
        else if(check_JacksOrBetter(p.getHandCard())){
            p.modSetType("Jacks or better");
            payOff = bet * 1;
        }
        else
            p.modSetType("others");

        p.modFortune(payOff, true);
        return payOff;
    }

    public int check_pairs(Card[] onHand){
        boolean add, prev = false;
        int num = 0;
        for(int i=0; i<4; i++)
        {
            add = false;
            if(onHand[i].getValue() == onHand[i+1].getValue() && !prev){
                add = true;
                prev = add;
            }
            else if(onHand[i].getValue() != onHand[i+1].getValue())
                prev = false;
            if(add)
                num++;
        }
        return num;
    }
    public boolean check_JacksOrBetter(Card[] onHand){
        boolean result = false;
        if(check_pairs(onHand) ==1 && !check_four_of_a_kind(onHand) && !check_three_of_a_kind_U(onHand))
        {
            for(int i=0; i<4; i++)
            {
                if(onHand[i].getValue()>10 && onHand[i].getValue()==onHand[i+1].getValue())
                    result = true;
            }
        }
        return result;
    }
    public boolean check_two_pair(Card[] onHand){
        if(check_pairs(onHand) == 2 && !check_three_of_a_kind_U(onHand)){
            return true;
        }
        else
            return false;
    }
    public boolean check_three_of_a_kind_U(Card[] onHand){
        boolean[] result = {true, true, true};
        if(check_four_of_a_kind(onHand))
            return false;
        else{
            for(int i=0; i<3; i++)
                for(int j=i; j<i+2; j++)
                    if(onHand[j].getValue() != onHand[j+1].getValue()){
                        result[i] = false;
                        break;
                    }
            return (result[0] || result[1] || result[2]);
        }
    }

    public boolean check_three_of_a_kind(Card[] onHand){
        if(check_three_of_a_kind_U(onHand) && check_pairs(onHand) == 1)
            return true;
        else
            return false;
    }

    public boolean check_full_house(Card[] onHand){
        if(check_pairs(onHand)==2 && check_three_of_a_kind_U(onHand))
            return true;
        else
            return false;
    }

    public boolean check_four_of_a_kind(Card[] onHand){
        boolean[] result = {true, true};
        for(int i=0; i<2; i++)
            for(int j=i; j<i+3; j++)
                if(onHand[j].getValue() != onHand[j+1].getValue()){
                    result[i] = false;
                    break;
                }
        return (result[0] || result[1]);
    }
    public boolean check_straight(Card[] onHand){
        boolean isStraight = true;
        boolean special = true;
        for(int i=0; i<4; i++)
        {
            if(onHand[i].getValue()+1 != onHand[i+1].getValue())
                isStraight = false;
            if(onHand[4].getValue()!=14 || onHand[i].getValue()!=(2+i))
                special = false;
        }
        return (isStraight || special);
    }

    public boolean check_flush(Card[] onHand){
        boolean isFlush = true;
        for(int i=0; i<4; i++)
        {
            if(onHand[i].getSuit().compareTo(onHand[i+1].getSuit())!=0)
                isFlush = false;
        }
        return isFlush;

    }

    public boolean check_straight_flush(Card[] onHand){
        boolean isStraightFlush = false;
        if(check_straight(onHand) && check_flush(onHand))
            isStraightFlush = true;
        return isStraightFlush;
    }

    public boolean check_royal_flush(Card[] onHand){
        boolean isRoyalFlush = false;
        if(check_straight_flush(onHand) && onHand[0].getValue()==10)
            isRoyalFlush = true;
        return isRoyalFlush;
    }

    /*Debug
    public void displayALL(){
        System.out.println("======================");
        for(int i = 0; i<52; i++)
            deck[i].showCard();
        System.out.println("");
        System.out.println("======================");
    }*/
}
