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
       ArrayList<Card> matchedCards= isThereMatchingCard();

       if(matchedCards!=null){
           int[] cardPoints=new int[getHandCards().size()];

           ArrayList<Card>maxPointCards=new ArrayList<Card>();

           for (int i=0;i<cardPoints.length;i++){
               cardPoints[i]=getHandCards().get(i).getCardPoint();
           }
           Arrays.sort(cardPoints);
           int maxPointValue=cardPoints[0];//en küçük değere eşitledik
           for (int i=0;i<cardPoints.length;i++){
               if(cardPoints[i]>=maxPointValue){
                   maxPointValue=cardPoints[i];
                   maxPointCards.add(getHandCards().get(i));
               }
           }
           for(int i=0;i<maxPointCards.size();i++) {
               if (matchedCards.contains(maxPointCards.get(i))) {
                   for(int j=0;j<getHandCards().size();j++){
                       if(maxPointCards.get(i)==getHandCards().get(j)){
                           choose=j;
                       }
                   }

               }
           }


  }

   return choose;
    }



}
