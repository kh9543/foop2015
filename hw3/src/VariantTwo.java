package assignment3;
import assignment3.Card;
import assignment3.Player;
import assignment3.OldMaid;
import assignment3.VariantOne;

public class VariantTwo extends VariantOne{
    public void punishment(){
        String[] type={"100 Push-ups","100 Knee-bands","100 Sit-ups","10km Jogging"};
        Player loser = whoIsLoser();
        shuffleCard(cardArray);
        int index;
        switch(cardArray[51].getSuit()){
            case "C":
                index=0;
                break;
            case "D":
                index=1;
                break;
            case "H":
                index=2;
                break;
            case "S":
                index=3;
                break;
            default:
                index=0;
        }
        System.out.print("The card drawed from the deck is");
        cardArray[51].showCard();
        System.out.println();
        System.out.println("Player"+loser.reportID()+"!"+" Your punishment is "+type[index]+"!");
    }
}
