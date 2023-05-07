import java.util.ArrayList;

public class ComputerPlayer extends Player {

    protected int[] possibleCardsPoint=new int[4];

    //-----------CONSTRUCTORS------------
    public ComputerPlayer(String name){
        super(name);
    }

    //-----------METHODS------------

    protected void simulatePlaycard(){
        int[] cardPoints = new int[getHandCards().size()];


        for (int i=0;i<getHandCards().size();i++ ){
            boardCardRef.add(getHandCards().get(i));

            if(isCollectable() == false){
                cardPoints[i] = 0;

            }else{
                cardPoints[i]= ( super.calculateBoardPoints() );
                possibleCardsPoint[i]= cardPoints[i];

                System.out.println("Possible card: "+getHandCards().get(i).getCardFace()+
                        " Player will get: "+cardPoints[i]+" point.");
            }
            boardCardRef.remove(boardCardRef.size()-1);

        }

    }

}
