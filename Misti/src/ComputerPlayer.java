import java.util.Arrays;

public class ComputerPlayer extends Player {

    protected int[] possibleCardsPoint=new int[4];

    //-----------CONSTRUCTORS------------
    public ComputerPlayer(String name, String level){
        super(name, level);
    }

    //-----------METHODS-----------


    //The "simulatePlayCard" function iterates through each card in player hand, calculates the
    //possible scores it can achieve by playing that card, and saves it to an array.
    protected void simulatePlaycard(){
        //in every step the saved points will be renewed.
        Arrays.fill(possibleCardsPoint,0);

        int[] cardPoints = new int[getHandCards().size()];

        for (int i=0;i<getHandCards().size();i++){
            boardCardRef.add(getHandCards().get(i));

            if(!isCollectable()){
                cardPoints[i] = 0;

            } else if (isMisti()) {
                cardPoints[i]=5*(super.calculateBoardPoints());
                possibleCardsPoint[i]=cardPoints[i];
                if(GameMode.isVerbose()){   //If game runs with verbose mode, then player will see bots possible card/s and points.
                    //Else player will not see this information.
                    System.out.println("Possible Misti card: "+getHandCards().get(i).getCardFace()+
                            " Player will get: "+cardPoints[i]+" point.");
                }
            } else{
                cardPoints[i]= ( super.calculateBoardPoints() );
                possibleCardsPoint[i]= cardPoints[i];
                if(GameMode.isVerbose()){   //If game runs with verbose mode, then player will see bots possible card/s and points.
                                            //Else player will not see this information.
                    System.out.println("If "+ getName() + " plays '"+getHandCards().get(i).getCardFace()+
                            "' will get: "+cardPoints[i]+" point.");
                }
            }
            boardCardRef.remove(boardCardRef.size()-1);

        }

    }

}
