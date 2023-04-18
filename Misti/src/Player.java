import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public abstract class Player {

    //Bu scanner ve randomu ana classta yani player'da bir defa ve static olavak şekilde ürettim.Bu
    //Sayede bütün alt sınıfları aynı scanner ve randomu kullanacak birden fazla üretilmiş olamayacak
    static protected Scanner sc = new Scanner(System.in);
    static protected Random rnd = new Random();

    //Oyuncuların kartları tahtaya atması için tahtanın referansı lazım bunu da oyunun başında setliyoruz
    static protected ArrayList<Card> boardCardRef = new ArrayList<>();

    //-----------VARIABLES------------
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
    public abstract void playCard();


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
    public void setBoardCardRef(ArrayList<Card> boardCard) {this.boardCardRef = boardCard;}
}
