import foop.*;
import java.util.*;


public class PlayerB02705010 extends Player{
    private final int chipsT;
    public PlayerB02705010(int chips)
    {
        super(chips);
        chipsT = chips;
    }
    public boolean buy_insurance(Card my_open, Card dealer_open, java.util.ArrayList<Hand> current_table)
    {
        /*
        for(int i = 0; i < current_table.size(); i++)
            for(int j = 0; j < current_table.get(i).getCards().size(); j++)
                System.out.println("Table: "+ current_table.get(i).getCards().get(j).getValue());
        */
        /*
        if(my_open.getValue() == 1 || my_open.getValue() >= 8)
            return false;
        else if(dealer_open.getValue() ==1)
            return true;
        */
        return false;
    }
    public boolean do_surrender(Card my_open, Card dealer_open,java.util.ArrayList<Hand> current_table)
    {

        return false;
    }
    public boolean do_split(java.util.ArrayList<Card> my_open, Card dealer_open, java.util.ArrayList<Hand> current_table)
    {
        int myPts = my_open.get(0).getValue();
        if(myPts == 10 || myPts ==  11 || myPts ==  12 || myPts ==  13)
            return false;
        else if(myPts == 5)
            return false;
        else if(myPts <= 7 && (dealer_open.getValue() > 8 || dealer_open.getValue() == 1 ))
            return false;
        else if(dealer_open.getValue() == 10 || dealer_open.getValue() == 7)
        {
            if(myPts == 9)
                return false;
        }
        return true;
    }
    public boolean do_double(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table)
    {
        //System.out.println("Before double"+get_chips());
        int pts = ptsCounter(my_open);
        if(isHard(my_open))
        {
            if( pts == 9 || pts == 10 || pts== 11)
                return true;
        }
        else{
            if(dealer_open.getValue() >=4 && dealer_open.getValue() <= 6)
            {
                if(pts <= 17)
                    return true;
            }
        }
        return false;
    }
    public boolean hit_me(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table)
    {
        int pts = ptsCounter(my_open);
        if(isHard(my_open))
        {
            if(pts >= 17)
                return false;
            else if(pts >= 13 && dealer_open.getValue() < 7 && dealer_open.getValue()!=1)
                return false;
        }
        else
        {
            if(pts >= 18)
                return false;
        }
        return true;
    }
    public int make_bet(java.util.ArrayList<Hand> last_table, int total_player,int my_position)
    {
        int bet = (chipsT/40);
        if(this.get_chips()-bet >= 0)
            return bet;
        else
            return 0;
    }
    public String toString()
    {
        String rst = String.valueOf(this.get_chips());
        return rst;
    }
    private int ptsCounter(Hand pH)
    {
        boolean hasAce = false;
        int rst = 0;
        for(int i=0; i<pH.getCards().size(); i++)
        {
            int val = pH.getCards().get(i).getValue();
            if(val == 11 || val == 12 || val == 13)
                rst += 10;
            else
                rst += val;
            if(val == 1)
                hasAce = true;
        }
        if(hasAce && (rst+10)<=21)
            rst += 10;
        return rst;
    }
    private boolean isHard(Hand pH)
    {
        boolean hasAce = false;
        int rst = 0;
        for(int i = 0; i<pH.getCards().size(); i++)
        {
            int val = pH.getCards().get(i).getValue();
            if(val == 11 || val == 12 || val == 13)
                rst += 10;
            else
                rst += val;
            if(val == 1)
                hasAce = true;
        }
        if(hasAce && rst+10 <= 21)
            return false;
        else
            return true;
    }
}
