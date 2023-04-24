import java.util.ArrayList;
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

        if (isCollectable())
        {
            System.out.println(getName() + " Took all cards");
            collectedCards.addAll(boardCardRef);
            boardCardRef.clear();
        }
        //TODO Calculate player score!!!
    };

    private boolean isCollectable(){
        return ((boardCardRef.size() > 1) &&
                ((boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals(boardCardRef.get(boardCardRef.size() - 2).getCardFace()))
                || (boardCardRef.get(boardCardRef.size() - 1).getCardFace().equals("J"))));
    }


    //-----------GETTERS------------
    public String getName() {return name;}
    public int getScore() {return score;}
    public ArrayList<Card> getHandCards() {return handCards;}
    public ArrayList<Card> getCollectedCards() {return collectedCards;}

    //-----------SETTERS------------
    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}
    public void setHandCards(ArrayList<Card> handCards) {this.handCards = handCards;}
    public void setCollectedCards(ArrayList<Card> collectedCards) {this.collectedCards = collectedCards;}
    public static void setBoardCardRef(ArrayList<Card> boardCard) {boardCardRef = boardCard;}
}
