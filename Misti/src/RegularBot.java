public class RegularBot extends ComputerPlayer{
    public RegularBot(String name) {
        super(name);
    }

    @Override
    public void playCard() {

        int indexOfMatchingCard = isThereMatchingCard();
        int indexOfJokerCard = isThereJoker();

        if (indexOfMatchingCard != -1){
            choose = indexOfMatchingCard;
        }else if(indexOfJokerCard != -1 && boardCardRef.size() > 0) {
            choose = indexOfJokerCard;
        }

        super.playCard();
    }

    private int isThereMatchingCard(){
        if (boardCardRef.size() == 0) return -1;
        for (int i = 0; i < getHandCards().size(); i++) {
            if (boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals(getHandCards().get(i).getCardFace()))
                return i;
        }
        //-1 means there is no matching with last board card
        return -1;
    }

    private int isThereJoker(){
        if (boardCardRef.size() == 0) return -1;
        for (int i = 0; i < getHandCards().size(); i++) {
            if (handCards.get(i).getCardFace().equals("J"))
                return i;
        }
        //-1 means there is no matching with last board card
        return -1;
    }
}
