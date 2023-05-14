import java.util.ArrayList;
import java.util.Arrays;


public class ExpertBot extends ComputerPlayer {

    // It stores the count of how many cards have been played before
    private static int [] playedCards = new int[13];

    // It stores the count of how many cards have been played before but only for the hand
    private int [] eachHandCardPlayedNumber = new int[4];

    public ExpertBot(String name) {
        super(name, "E");
    }

    @Override
    public void playCard() {

        //Simulate playing a card to calculate how many point will be able to get for each card.
        simulatePlaycard();
        //This function reorganizes the number of times each card in the player's hand
        //has been played based on 'playedCards' array.
        updateEachHandCardPlayedNumber();

        // The method findHigestNumbers returns the indexes of the most frequently played cards.
        // For example, if the eachHandCardPlayedNumber array is [7, J, 10, 3], where 7 is played 2 times, 10 is played 1 time, and the others are not played,
        // the array of indexes should be [2, 0, 1, 0].
        // When calling findHigestNumbers, it returns 0 (which means the first card is played the most).
        // Another example: If the array is [2, 2, 0, 1], it returns [0, 1] (indicating the first and second cards are the most played).
        ArrayList<Integer> indexOfMostUsedCard = findHigestNumbers(eachHandCardPlayedNumber);

        int possiblePoint=0;
        for (int i = 0; i < getHandCards().size(); i++) {
            //if points are bigger than 0 then it will choose a card
            //if previous possible card have more points choose card stay at previous card
            if(possibleCardsPoint[i] > possiblePoint){
                possiblePoint=possibleCardsPoint[i];
                choosedCard=i;
            }
        }
        //It means there is no positive points cards on our hand.
        if (possiblePoint == 0){
            choosedCard = findMinimumPointCard(indexOfMostUsedCard);
        }

        for (int i = 0; i < indexOfMostUsedCard.size(); i++) {
            indexOfMostUsedCard.set(i, 0);
        }

        super.playCard();
    }


    //The purpose of this method is to select the card with the
    //lowest point value when the player is unable to collect any cards.
    public int findMinimumPointCard(ArrayList<Integer> indexOfMostUsedCard) {

        int minScoreIndex = indexOfMostUsedCard.get(0);
        int minScore = getHandCards().get(minScoreIndex).getCardPoint();

        for (int i = 1; i < indexOfMostUsedCard.size(); i++) {
            int index = indexOfMostUsedCard.get(i);
            // Check if the index is valid (-1 means it is not a playable card)
            if (index != -1){
                int score = getHandCards().get(index).getCardPoint();
                if (score < minScore) {
                    // Update the minimum score and its corresponding index
                    minScore = score;
                    minScoreIndex = index;
                }
            }
        }

        // Print information if in verbose mode
        if (GameMode.isVerbose()){
            System.out.println(getName()+" playing minimum point card because he can't collect any cards...");
            System.out.println("Card(Min-Score) index: "+minScoreIndex+" card point: "+minScore);
        }

        // Return the index of the minimum score card
        return minScoreIndex;
    }


    //That method finds the highest number in an array, and then finds and
    //returns the indexes of all elements that have the same highest value.
    public static ArrayList<Integer> findHigestNumbers(int[] newArray) {
        // Find the highest number in the array
        int higestNumber = newArray[0];
        for (int i = 1; i < newArray.length; i++) {
            if (newArray[i] > higestNumber) {
                higestNumber = newArray[i];
            }
        }

        // Find the indexes of the highest numbers in the array
        ArrayList<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < newArray.length; i++) {
            if (newArray[i] == higestNumber) {
                indexs.add(i);
            }
        }

        // Return the indexes of the highest numbers
        return indexs;
    }


    //This function reorganizes the number of times each card in the player's hand
    //has been played based on 'playedCards' array.
    private void updateEachHandCardPlayedNumber(){

        int a = 0;
        // Update the played card numbers for each card in the player's hand
        for (int i = 0; i < getHandCards().size(); i++) {
            String cardFace = getHandCards().get(i).getCardFace();

            // Check if the card face is present in the card map
            if (super.getCardMap().containsKey(cardFace)) {
                // Update the played card number for the current card in the hand
                eachHandCardPlayedNumber[i] = playedCards[super.getCardMap().get(cardFace)];
            }
            a++;
        }

        // Set the remaining elements in eachHandCardPlayedNumber to -1
        for (int i = a; i < 4; i++) {
            eachHandCardPlayedNumber[i] = -1;
        }

        // Print the number of played cards for debugging purposes (if verbose mode is enabled)
        if (GameMode.isVerbose()){
            System.out.println("All number of played cards(0. index = A, 1.index = 2, 2.index = 3) "+Arrays.toString(playedCards));
        }
    }

    public static int[] getPlayedCards(){return playedCards;}
}

