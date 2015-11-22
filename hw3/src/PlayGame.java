import assignment3.OldMaid;
import assignment3.VariantOne;
import assignment3.VariantTwo;

public class PlayGame{
    public static void main(String[] args){
        System.out.println("Now Playing: OldMaid");
        OldMaid game = new OldMaid();
        game.gameStart();

        System.out.println("Now Playing: VariantOne");
        VariantOne game1 = new VariantOne();
        game1.gameStart();
        game1.whoIsLoser();

        System.out.println("Now Playing: VariantTwo");
        VariantTwo game2 = new VariantTwo();
        game2.gameStart();
        game2.punishment();
    }
}
