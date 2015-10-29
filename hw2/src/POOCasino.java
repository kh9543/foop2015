import assignment2.Player;
import assignment2.Computer;
import assignment2.Card;
import java.util.Scanner;

public class POOCasino {
    public static void main(String[] args) {
        String temp;
        Player player = new Player();
        Computer computer = new Computer();
        //Author Info
        System.out.println("POOCasino Jacks or better, written by b02705010 Kuan-Hung Liu");
        //Enter UserName and Create Player to Initiall state
        System.out.print("Please enter your name: ");
        Scanner scanner = new Scanner(System.in);
        temp = scanner.next();
        player.setPlayer(temp,1000);
        //Greetings
        System.out.println("Welcome, " + player.getName() + ".");
        //Game Start
        while (player.isPlaying())
        {
            int bet;
            System.out.println("You have " + player.getP() + " P-dollars now.");
            System.out.print("Please enter your bet for round " + player.getRound() + " (1-5 or 0 for quitting the game): ");
            bet = scanner.nextInt();
            while((bet < 1) || (bet > 5)){
                if (bet == 0) {break;}
                System.out.print("Error,  P-dollar bet should be 1-5 (or 0 for quitting the game): ");
                bet = scanner.nextInt();
            }
            if(bet == 0){
                player.quitGame();
                break;
            }
            //Do the deduction
            computer.deductP(player, bet);
            //distribute,display Cardset and ask for action
            computer.distributeCards(player, 5);
            System.out.print("Your cards are");
            player.displayCardChoice();
            System.out.println("");
            System.out.print("Which cards do you want to keep? (Enter n to discard all): ");
            temp = scanner.next();
            computer.discardSet(player, temp);
            //Show result
            System.out.print("Your new cards are");
            player.displayCardResult();
            System.out.println(".");
            System.out.print("You get a ");
            int p = computer.checkAndPay(player);
            player.printResult();
            System.out.println(". The payoff is " + p + ".");
        }
        //End Game, farewell
        System.out.print("Good bye, " + player.getName() + "." + " You played for ");
        System.out.println(player.getRound()-1 + " round(s) and have " + player.getP() + " P-dollars now.");


    }
}
