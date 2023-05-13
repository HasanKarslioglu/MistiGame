public class RegularBot extends ComputerPlayer {
    public RegularBot(String name) {
        super(name);
    }

    @Override
    public void playCard() {
        super.simulatePlaycard();
        int possiblePoint=0;
        for (int i = 0; i < possibleCardsPoint.length; i++) {
           //if points are bigger than 0 then it will choose a card
               //if previous possible card have more points choose card stay at previous card
                if( possibleCardsPoint[i] > possiblePoint){
                    possiblePoint=possibleCardsPoint[i];
                    choosedCard=i;
                }
        }
       //if there is no positive points then bot will play 1.card
        if(possiblePoint==0){
            choosedCard=0;
        }
        super.playCard();
    }

}




