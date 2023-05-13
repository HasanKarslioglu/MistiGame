import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;


public abstract class Player {

    //-----------VARIABLES------------
    static protected ArrayList<Card> boardCardRef;
    static protected Random rnd = new Random();
    private String name;
    private int score;
    protected ArrayList<Card> handCards = new ArrayList<>();
    protected ArrayList<Card> collectedCards = new ArrayList<>();
    protected ArrayList<Card> mistiCards= new ArrayList<>();
    protected int choosedCard = 0;

    //-----------CONSTRUCTORS------------
    public Player(String name){
        this.name = name;
        score = 0;
    }

    //-----------METHODS------------
    //Her alt classın kendine ait bir play mekaniği olacak o yüzden bu abstract
    public void playCard(){
        //ÇOK ÖNEMLİ!!!!!!!!!!!!!!
        //ÖNCE Alt sınıfların playcardı çalışcak en son super.playCard() ı çalıştırınca buradaki fonksiyon çalışcak
        //Bu sayede kart seçimi ve oynanması olduktan sonra burada, boarddeckin son iki kartı aynı ise kartlar toplanacak


        System.out.println(getName() +" played " + getHandCards().get(choosedCard).getCardString());
        boardCardRef.add(getHandCards().remove(choosedCard));


        if (isCollectable())
        {
            if(isMisti()){
                System.out.println(getName()+" maked pisti");
                mistiCards.addAll(boardCardRef);
            }else{
                System.out.println(getName() + " Took all cards");
                collectedCards.addAll(boardCardRef);
            }
            calculatePlayerScore(boardCardRef);
            boardCardRef.clear();
        }
        //TODO Calculate player score!!!


    };
   
    protected boolean isCollectable(){
        return ((boardCardRef.size() > 1) &&
                ((boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals(boardCardRef.get(boardCardRef.size() - 2).getCardFace()))
                || (boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals("J"))));

    }
    protected boolean isMisti(){
        return (boardCardRef.size()==2
                && (boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals(boardCardRef.get(boardCardRef.size() - 2).getCardFace())));
    }

    //We use non-parameter for simulatePlayCard method
    protected int calculateBoardPoints(){
       int totalBoardPoints=0;
        for (Card card : boardCardRef) {
            totalBoardPoints += card.getCardPoint();
        }
    return totalBoardPoints;
    }
    //if cards collected after playing card, then game will calculate scores with each card points.Also if there is a Misti then
    //it will multiply the taken cards total points with 5.
    protected void calculatePlayerScore(ArrayList<Card>boardCardRef){
        for (Card card : boardCardRef) {
            if (isMisti()) {
                setScore(score += (5 * card.getCardPoint()));
            } else {
                setScore(score += (card.getCardPoint()));
            }
        }
    }

    //-----------GETTERS------------
    public String getName() {return name;}
    public int getScore() {return score;}
    public ArrayList<Card> getHandCards() {return handCards;}
    public ArrayList<Card> getCollectedCards() {return collectedCards;}
    public ArrayList<Card> getmistiCards() { return mistiCards;}

    //-----------SETTERS------------
    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}
    public void setHandCards(ArrayList<Card> handCards) {this.handCards = handCards;}
    public void setCollectedCards(ArrayList<Card> collectedCards) {this.collectedCards = collectedCards;}
    public static void setBoardCardRef(ArrayList<Card> boardCard) {boardCardRef = boardCard;}
    public void setmistiCards(ArrayList<Card> mistiCards) {this.mistiCards = mistiCards;}
}
