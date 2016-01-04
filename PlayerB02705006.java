import foop.*;
import java.util.*;

public class PlayerB02705006 extends Player {

    public PlayerB02705006(int chips){super(chips);}
    int myBet = 0;                                                  //Records the bet this round
    int cardValue(Card c){
        if(2 <= c.getValue()  && c.getValue() <= 9)
            return (int)(c.getValue());
        else if (10 <= c.getValue() && c.getValue() <= 13)
            return 10;
        else
            return 11;
    }

    int handValue(Hand hv){
        ArrayList<Card> thisHand = hv.getCards();
        int valCount = 0;
        int aceCount = 0;
        for(int i = 0; i < thisHand.size(); i++){
            valCount += cardValue(thisHand.get(i));
            if(thisHand.get(i).getValue() == 1)
                aceCount++;
        //    System.out.println(countVal);
        }
        while(valCount > 21){
            int aceCredit = aceCount;
            while(aceCredit > 0){
                valCount -= 10;
                aceCredit--;
            }
        }
        return valCount;
    }

    boolean hardHand(Hand h){                                       //1 for hard, 0 for soft
        ArrayList<Card> myHand = h.getCards();
        boolean hasAce = false;
        int cardSum = 0;
        for(int i = 0; i < myHand.size(); i++){
            cardSum += cardValue(myHand.get(i));
            if(myHand.get(i).getValue() == 1){
                hasAce = true;
            }
        }
        if(!hasAce)
            return true;
        else{
            if(cardSum > 21)
                return true;
        }
        return false;
    }

    @Override
    public boolean buy_insurance(Card my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){               //Insurance sucks, never buy them
        return false;
    }
    @Override
    public int make_bet(java.util.ArrayList<Hand> last_table, int total_player, int my_position){   //Bet 1/10 of my current cash regardless of other players
        myBet = (int)(this.get_chips() /10);
        return myBet;
    }
    @Override
    public boolean do_surrender(Card my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){                //Since we only get to see the face-up card, there is no point to surrender
        return false;
    }
    @Override
    public boolean do_split(java.util.ArrayList<Card> my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
        int myValue = cardValue(my_open.get(0));
        int dealValue = cardValue(dealer_open);
        if(myValue == 11 || myValue == 8)
            return true;
        else if ((myValue == 2 || myValue == 3 || myValue == 6 || myValue == 7 || myValue == 9) && (dealValue < 7))
            return true;
        else
            return false;
    }
    @Override
    public boolean do_double(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
        int myValue = handValue(my_open);
        int dValue = cardValue(dealer_open);
        if(this.get_chips() - myBet > 0){                                           //Only if we have enough money can we condiser doubling
            if(!hardHand(my_open) && (myValue >= 16 && myValue <= 18) && dValue <= 6)
                return true;
            else{
                if(myValue == 9 && dValue <= 6)
                    return true;
                if(myValue == 10 || myValue == 11){
                    if(myValue > dValue)
                        return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean hit_me(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
        int myValue = handValue(my_open);
        int dValue = cardValue(dealer_open);
        if(hardHand(my_open)){
            if(myValue >= 4 && myValue <= 8)
                return true;
            else if((myValue == 10 || myValue == 11) && myValue > dValue)
                return true;
            else if((myValue == 9 || (myValue >= 12 && myValue <= 16)) && dValue >= 7)
                return true;
            else
                return false;
        }
        else{
            if(myValue >= 13 && myValue <= 15)
                return true;
            else if(myValue >= 16 && myValue <= 18 && dValue >= 7)
                return true;
            else
                return false;
        }
    }

    @Override
    public java.lang.String toString(){
        return String.valueOf(get_chips());
    }
}
