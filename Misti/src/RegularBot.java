import java.util.ArrayList;
import java.util.Arrays;


public class RegularBot extends ComputerPlayer{
    public RegularBot(String name) {
        super(name);
    }

    @Override
    public void playCard() {
        int indexOfJokerCard = isThereJoker();
        simulatePlayCard();

        if(indexOfJokerCard != -1 && boardCardRef.size() > 0) {
            choose = indexOfJokerCard;
        }

        super.playCard();
    }

    private ArrayList<Card> isThereMatchingCard(){
        if (boardCardRef.size() == 0) return null;
       ArrayList<Card> matchedCards=new ArrayList<>();
        for (int i = 0; i < getHandCards().size(); i++) {
            if (boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals(getHandCards().get(i).getCardFace())){
                matchedCards.add(getHandCards().get(i));

        }
 }
        if(matchedCards.size()==0){
            return null;
        }
        return matchedCards;

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

    private int simulatePlayCard(){
       //matched cards will collect from isThereMatchingCard() method and will add in simulateCard's matchedCards arraylist.
        ArrayList<Card> matchedCards= isThereMatchingCard();

        //if there is no matched cards then it will check for joker
       if(matchedCards!=null) {
           //if there is matched card or cards then we will calculate each cards total points with board cards.
           int[] cardPoints = new int[getHandCards().size()];


           ArrayList<Card> maxPointCards = new ArrayList<Card>();
           
           for (int i = 0; i < cardPoints.length; i++) {
               cardPoints[i] = super.calculateBoardPoints();
               cardPoints[i] += getHandCards().get(i).getCardPoint();
           }
           //after calcultaion method will sort points small to large Value.
           Arrays.sort(cardPoints);

           int maxPointValue = cardPoints[0];
           //Here the loop will control if the cardpoints are bigger than the smallest value and bigger than 0
           for (int i = 1; i < cardPoints.length; i++) {
               if (cardPoints[i] > 0 && cardPoints[i] >= maxPointValue) {
                   maxPointValue = cardPoints[i];
                   maxPointCards.add(getHandCards().get(i));
               }
           }
           //It will check if there is maxpointcards and this card or cards will also in matchedCards.

             for(int i=0;i<matchedCards.size();i++){
                 if(maxPointCards.contains(matchedCards.get(i))){
                     for (int k = 0; k < maxPointCards.size(); k++) {
                         if (matchedCards.get(i)==maxPointCards.get(k)) {
                             for (int j = 0; j < getHandCards().size(); j++) {
                                 if (maxPointCards.get(k) == getHandCards().get(j)) {
                                     choose = j;
                                 }
                             }

                         }
                     }
                          //if matched card does not in maxPointCards, then program will select another card except marched card.

                     for (int j = 0; j < getHandCards().size(); j++) {
                         if (matchedCards.get(i) != getHandCards().get(j)) {
                                  choose = j;
                              }
                          }

                  }


           }

       }

   return choose;
    }



}
