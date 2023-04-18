import java.util.ArrayList;

public abstract class Player {

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
}
