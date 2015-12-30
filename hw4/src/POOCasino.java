import foop.*;
import java.util.*;
import assignment4.*;

public class POOCasino{
    private static int ptsCounter(Hand pH)
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
    private static boolean isHard(Hand pH)
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
    public static void main(String[] argv){
        int nround = Integer.valueOf(argv[0]); 
        int nchips =  Integer.valueOf(argv[1]);
        int round = 0;
        int playerNum = 4;
        // init player
        Player players[] = new Player[4];
        PlayerB02705010 player1 = new PlayerB02705010(nchips);
        PlayerB02705010 player2 = new PlayerB02705010(nchips);
        PlayerB02705010 player3 = new PlayerB02705010(nchips);
        PlayerB02705010 player4 = new PlayerB02705010(nchips);
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;

        while(round < nround)
        {
            int roundPlayer = 0;
            boolean playing[] = new boolean[4];
            boolean insurance[] = new boolean[4];
            boolean surrender[] = new boolean[4];
            boolean spliting[] = new boolean[4];
            boolean double_down[] = new boolean[8];
            boolean busted[] = new boolean[8];
            boolean stand[] = new boolean[8];
            Hand playerHand[] = new Hand[8];
            double bet[] = new double[8];

            ArrayList<Hand> last_table = new ArrayList<Hand>();
            System.out.println("Game Started"+" - Round: "+round);
            Deck myDeck = new Deck(); //Create Deck
            //myDeck.getDeck();
            System.out.println("Make bet and Assign Cards ");
            for(int i = 0; i<4; i++)
            {
                bet[i] = players[i].make_bet(last_table,4,i);
                if(bet[i] > 0)
                {
                    try{
                        players[i].decrease_chips(bet[i]);
                        playing[i] = true;
                        System.out.println("Player"+(i+1)+": make bet "+bet[i]);
                        roundPlayer ++;
                    }
                    catch(Player.NegativeException ex){
                        System.out.println("Player"+i+": "+ex);
                    }
                    catch(Player.BrokeException ex)
                    {
                        System.out.println("Player"+i+": "+ex);
                    }
                }
            }
            Hand player_open[] = new Hand[4];
            ArrayList<Hand> current_table = new ArrayList<Hand>();
            if(roundPlayer > 0)
                System.out.print("Now players: ");
            for(int i=0; i<4 && playing[i]; i++) //assingCards to now playing players
            {
                System.out.print((i+1)+" ");
                playerHand[i] = new Hand(myDeck.assingCards());
                ArrayList<Card> temp = new ArrayList<Card>();
                temp.add(playerHand[i].getCards().get(0));
                player_open[i]= new Hand(temp);
                current_table.add(player_open[i]);
                /* //Debug section
                ArrayList<Card> temp = playerHand[i].getCards();
                for(int j=0 ; j< temp.size(); j++)
                    System.out.println(temp.get(j).getSuit()+":"+temp.get(j).getValue());
                */
            }
            if(roundPlayer > 0)
                System.out.println();
            if(roundPlayer != 0)
            {
                Hand dealerHand = new Hand(myDeck.assingCards());
                ArrayList<Card> temp = new ArrayList<Card>();
                temp.add(dealerHand.getCards().get(0));
                Hand dealer_open = new Hand(temp);
                current_table.add(dealer_open);
                System.out.println("Dealer face-up: "+dealer_open.getCards().get(0).getValue());
                if(dealerHand.getCards().get(0).getValue() == 1)
                {
                    System.out.println("Dealer gets an ACE! Asking player to buy insurance...");
                    for(int i=0; i<4 && playing[i]; i++)
                    {
                        if(players[i].buy_insurance(player_open[i].getCards().get(0),dealer_open.getCards().get(0),current_table))
                        {
                            try{
                                players[i].decrease_chips((0.5)*bet[i]);
                                insurance[i] = true;
                                System.out.println("Player"+(i+1)+" bought insurance!");
                            }
                            catch(Player.NegativeException ex){
                                System.out.println("Player"+(i+1)+": "+ex);
                            }
                            catch(Player.BrokeException ex)
                            {
                                System.out.println("Player"+(i+1)+": "+ex+"Can't buy insurance!");
                                try{
                                    players[i].increase_chips((0.5)*bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                        }
                    }
                    if(dealerHand.getCards().get(1).getValue() < 10)
                    {
                        //System.out.println("#4");
                        System.out.println("Dealer asking players to surrender...");
                        for(int i=0; i<4 && playing[i]; i++)
                        {
                            if(players[i].do_surrender(player_open[i].getCards().get(0),dealer_open.getCards().get(0),current_table) == true)
                            {
                                System.out.println("Player"+(i+1)+": "+"surrendered!");
                                surrender[i] = true;
                            }
                        }
                    }
                    else
                        System.out.println("Dealer blackjack!");
                }
                else if(dealerHand.getCards().get(0).getValue() <10 || dealerHand.getCards().get(1).getValue()!= 1)
                {
                    System.out.println("Dealer asking players to surrender...");
                    for(int i=0; i<4 && playing[i]; i++)
                    {
                        if(dealerHand.getCards().get(1).getValue() < 10)
                        {
                            if(players[i].do_surrender(player_open[i].getCards().get(0),dealer_open.getCards().get(0),current_table) == true)
                            {
                                System.out.println("Player"+(i+1)+": "+"surrendered!");
                                surrender[i] = true;
                            }
                        }

                    }

                }
                else
                {
                    System.out.println("Dealer blackjack!");
                }
                //For each player who did not choose to surrender, filp up the face down card
                current_table.clear();
                for(int i=0; i<4 && playing[i]; i++)
                {
                    if(!surrender[i])
                        current_table.add(playerHand[i]);
                }
                current_table.add(dealer_open);
                for(int i=0; i<4 && playing[i]; i++)
                {
                    if(!surrender[i])
                    {
                        if(playerHand[i].getCards().get(0).getValue() == playerHand[i].getCards().get(1).getValue())
                        {
                            if(players[i].do_split(playerHand[i].getCards(),dealer_open.getCards().get(0),current_table))
                            {
                                try{
                                    players[i].decrease_chips(bet[i]);
                                    System.out.println("Player"+(i+1)+" Splited!");
                                    int index = current_table.indexOf(playerHand[i]);
                                    spliting[i] = true;
                                    bet[i+4] = bet[i];
                                    //System.out.println(bet[i]+" "+bet[i]);
                                    ArrayList<Card> fst = new ArrayList<Card>();
                                    ArrayList<Card> snd = new ArrayList<Card>();
                                    fst.add(playerHand[i].getCards().get(0));
                                    snd.add(playerHand[i].getCards().get(1));
                                    playerHand[i] = new Hand(fst);
                                    playerHand[i+4] = new Hand(snd);
                                    current_table.remove(index);
                                    current_table.add(playerHand[i]);
                                    current_table.add(playerHand[i+4]);
                                }
                                catch(Player.NegativeException ex){
                                    System.out.println("Player"+(i+1)+": "+ex);
                                }
                                catch(Player.BrokeException ex)
                                {
                                    System.out.println("Player"+(i+1)+": "+ex+"Can't do spliting!");
                                    try{
                                        players[i].increase_chips(bet[i]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }

                            }
                        }
                        if(players[i].do_double(playerHand[i],dealer_open.getCards().get(0),current_table))
                        {
                            try{
                                players[i].decrease_chips(bet[i]);
                                double_down[i] = true;
                                bet[i] *= 2;
                                System.out.println("Player"+(i+1)+": double down!");
                            }
                            catch(Player.NegativeException ex){
                                System.out.println("Player"+(i+1)+": "+ex);
                            }
                            catch(Player.BrokeException ex)
                            {
                                System.out.println("Player"+(i+1)+": "+ex+"Can't do double down!");
                                try{
                                    players[i].increase_chips(bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                        }
                        if(spliting[i])
                        {
                            if(players[i].do_double(playerHand[i+4],dealer_open.getCards().get(0),current_table))
                            {
                                try{
                                    players[i].decrease_chips(bet[i+4]);
                                    double_down[i+4] = true;
                                    bet[i+4] *= 2;
                                    System.out.println("Player"+(i+1)+": double down! (Splited)");
                                }
                                catch(Player.NegativeException ex){
                                    System.out.println("Player"+(i+1)+": "+ex);
                                }
                                catch(Player.BrokeException ex)
                                {
                                    System.out.println("Player"+(i+1)+": "+ex+" Can't do double down!");
                                    try{
                                        players[i].increase_chips(bet[i+4]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }

                            }
                        }
                    }
                }
                for(int i=0; i<4 && playing[i]; i++)
                {
                    if(!surrender[i])
                    {
                        if(double_down[i])
                        {
                            int index = current_table.indexOf(playerHand[i]);
                            ArrayList<Card> varC = new ArrayList<Card>();
                            varC = playerHand[i].getCards();
                            varC.add(myDeck.assingCard());
                            Hand varH = new Hand(varC);
                            playerHand[i] = varH;
                            current_table.remove(index);
                            current_table.add(varH);
                            /* //print current_table
                           for(int j=0; j<current_table.size(); j++)
                           {
                               for(int k=0; k<current_table.get(j).getCards().size(); k++)
                                   System.out.print(current_table.get(j).getCards().get(k).getSuit()+"-"+current_table.get(j).getCards().get(k).getValue()+" ");
                               System.out.println("");
                           }
                           */
                           //Count pts here to state busted or stand
                           int pts = ptsCounter(varH);
                           if(pts > 21){
                               busted[i] = true;
                               System.out.println("Player"+(i+1)+": busted!");
                           }
                           else{
                               stand[i] = true;
                               System.out.println("Player"+(i+1)+": Stands!");
                           }
                        }
                        else
                        {
                            while(players[i].hit_me(playerHand[i],dealer_open.getCards().get(0),current_table) && !busted[i])
                            {
                                int index = current_table.indexOf(playerHand[i]);
                                ArrayList<Card> varC = new ArrayList<Card>();
                                varC = playerHand[i].getCards();
                                varC.add(myDeck.assingCard());
                                Hand varH = new Hand(varC);
                                playerHand[i] = varH;
                                current_table.remove(index);
                                current_table.add(varH);
                                //Count pts here to state busted
                                int pts = 0;
                                pts = ptsCounter(varH);
                                if(pts > 21)
                                {
                                    busted[i] = true;
                                    System.out.println("Player"+(i+1)+": busted!");
                                    break;
                                }
                            }
                            if(!busted[i])
                            {
                                //System.out.print("#2");
                                stand[i] = true;
                                System.out.println("Player"+(i+1)+": Stands!");
                            }

                        }
                        if(spliting[i])
                        {
                            if(double_down[i+4])
                            {
                                int index = current_table.indexOf(playerHand[i+4]);
                                ArrayList<Card> varC = new ArrayList<Card>();
                                varC = playerHand[i+4].getCards();
                                varC.add(myDeck.assingCard());
                                Hand varH = new Hand(varC);
                                playerHand[i+4] = varH;
                                current_table.remove(index);
                                current_table.add(varH);
                                stand[i+4] = true;
                                System.out.println("Player"+(i+1)+": Stands! (Splited)");
                            }
                            else
                            {
                                while(players[i].hit_me(playerHand[i+4],dealer_open.getCards().get(0),current_table) && !busted[i+4])
                                {
                                    int index = current_table.indexOf(playerHand[i+4]);
                                    ArrayList<Card> varC = new ArrayList<Card>();
                                    varC = playerHand[i+4].getCards();
                                    varC.add(myDeck.assingCard());
                                    Hand varH = new Hand(varC);
                                    playerHand[i+4] = varH;
                                    current_table.remove(index);
                                    current_table.add(varH);
                                    //The same as above
                                    int pts = 0;
                                    pts = ptsCounter(varH);
                                    if(pts > 21)
                                    {
                                        busted[i+4] = true;
                                        System.out.println("Player"+(i+1)+": busted! (Splited)");
                                        break;
                                    }
                                }
                                if(!busted[i+4])
                                {
                                    stand[i+4] = true;
                                    System.out.println("Player"+(i+1)+": Stands! (Splited)");
                                }
                            }
                        }
                    }
                }
                // Dealer action
                int dealerPts = 0;
                dealerPts = ptsCounter(dealerHand);
                while(dealerPts <= 17)
                {
                    dealerPts = ptsCounter(dealerHand);
                    if(dealerPts == 17 && isHard(dealerHand))
                        break;
                    ArrayList<Card> var = new ArrayList<Card>();
                    var = dealerHand.getCards();
                    var.add(myDeck.assingCard());
                    dealerHand = new Hand(var);
                }
                System.out.println("Dealer pts: "+dealerPts);
                for(int i = 0; i < playerNum && playing[i]; i++)
                {
                    int playerPts = ptsCounter(playerHand[i]);
                    if(surrender[i])
                    {
                        try{
                            players[i].increase_chips((0.5)*bet[i]);
                        }
                        catch(Player.NegativeException ex2){
                            System.out.println("Player"+(i+1)+": "+ex2);
                        }
                    }
                    else if(!busted[i])
                    {
                        if(playerPts == 21 && playerHand[i].getCards().size() ==2)
                        {
                            if(dealerPts == 21 && dealerHand.getCards().size() ==2)
                            {
                                //Push
                                try{
                                    players[i].increase_chips(bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                            else
                            {
                                try{
                                    players[i].increase_chips((2.5)*bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                        }
                        else if(dealerPts > 21)
                        {
                            try{
                                players[i].increase_chips((2)*bet[i]);
                            }
                            catch(Player.NegativeException ex2){
                                System.out.println("Player"+(i+1)+": "+ex2);
                            }
                        }
                        else if(dealerPts == 21 && dealerHand.getCards().size() ==2)
                        {
                            if(insurance[i])
                            {
                                try{
                                    players[i].increase_chips(bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                        }
                        else if(playerPts >= dealerPts)
                        {
                            if(playerPts == dealerPts)
                            {
                                try{
                                    players[i].increase_chips(bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                            else
                            {
                                try{
                                    players[i].increase_chips((2)*bet[i]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                        }
                        //#
                        for(int j=0; j <playerHand[i].getCards().size(); j++)
                            System.out.print(playerHand[i].getCards().get(j).getValue()+" ");

                        System.out.println("Player"+(i+1)+" pts: "+playerPts);

                    }
                    if(spliting[i])
                    {
                        playerPts = ptsCounter(playerHand[i+4]);
                        if(!busted[i+4])
                        {
                            if(playerPts == 21 && playerHand[i+4].getCards().size() ==2)
                            {
                                if(dealerPts == 21 && dealerHand.getCards().size() ==2)
                                {
                                    //Push
                                    try{
                                        players[i].increase_chips(bet[i+4]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }
                                else
                                {
                                    try{
                                        players[i].increase_chips((2.5)*bet[i+4]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }
                            }
                            else if(dealerPts > 21)
                            {
                                try{
                                    players[i].increase_chips((2)*bet[i+4]);
                                }
                                catch(Player.NegativeException ex2){
                                    System.out.println("Player"+(i+1)+": "+ex2);
                                }
                            }
                            else if(dealerPts == 21 && dealerHand.getCards().size() ==2)
                            {
                                if(insurance[i])
                                {
                                    try{
                                        players[i].increase_chips(bet[i+4]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }
                            }
                            else if(playerPts >= dealerPts)
                            {
                                if(playerPts == dealerPts)
                                {
                                    try{
                                        players[i].increase_chips(bet[i+4]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }
                                else
                                {
                                    try{
                                        players[i].increase_chips((2)*bet[i+4]);
                                    }
                                    catch(Player.NegativeException ex2){
                                        System.out.println("Player"+(i+1)+": "+ex2);
                                    }
                                }
                            }
                        }
                        System.out.println("Player"+(i+1)+" pts (Splited): "+playerPts);
                    }
                }
            }
            else
                System.out.println("No player!");
            for(int i = 0; i<playerNum; i++)
            {
                System.out.println("Player"+(i+1)+" Chips remaining: "+players[i].toString());
            }
            round ++;
        }

    }
}
