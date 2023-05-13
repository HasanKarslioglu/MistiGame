import java.util.ArrayList;
import java.util.Arrays;


public class RegularBot extends ComputerPlayer {
    public RegularBot(String name) {
        super(name);
    }

    @Override
    public void playCard() {
        super.simulatePlaycard();
        for (int i = 0; i < possibleCardsPoint.length; i++) {
            if (possibleCardsPoint[i] > 0) {
                choosedCard = possibleCardsPoint[i];
            }
        }


        super.playCard();
    }

}




