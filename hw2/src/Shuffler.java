package assignment2;
import assignment2.*;

public class Shuffler{
    private Card[] temp;
    public Card[] shuffle(Card[] array){
        Card temp = new Card();
        RandomIndex r = new RandomIndex();
        r.setSize(52);
		for(int i = array.length-1 ; i > 0; i--)
		{
	       int index = r.getNext();
		   temp.copyCard(array[index]);
		   array[index].copyCard(array[i]);
		   array[i].copyCard(temp);
		}
        return array;
    }
}
