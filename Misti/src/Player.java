import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public abstract class Player {

    //-----------VARIABLES------------

    static protected ArrayList<Card> boardCardRef;                      // Reference to the board cards
    static protected Random rnd = new Random();
    private String name;
    private int score;
    private String level;
    protected ArrayList<Card> handCards = new ArrayList<>();            // Player's hand cards
    protected ArrayList<Card> collectedCards = new ArrayList<>();       // Cards collected by the player
    protected ArrayList<Card> mistiCards= new ArrayList<>();
    // Index of the chosen card, it will be changed in child class based on it's tactics
    protected int choosedCard = 0;

    //HashMap that maps card faces to their corresponding indices
    private HashMap<String, Integer> cardMappings = new HashMap<>() {{
        put("A", 0);
        put("2", 1);
        put("3", 2);
        put("4", 3);
        put("5", 4);
        put("6", 5);
        put("7", 6);
        put("8", 7);
        put("9", 8);
        put("10", 9);
        put("J", 10);
        put("Q", 11);
        put("K", 12);
    }};

    //-----------CONSTRUCTORS------------
    public Player(String name, String level){
        this.name = name;
        this.level = level;
        score = 0;
    }

    //-----------METHODS------------
    public void playCard(){
        System.out.println(getName() +" played " + getHandCards().get(choosedCard).getCardString());
        // Add the played card to the board card reference and remove it from the player's hand
        boardCardRef.add(getHandCards().remove(choosedCard));

        updatePlayedCardsArr();

        // Check if the played cards are collectable, on the other word, can player earn cards...
        if (isCollectable())
        {
            //Check if the player made a misti
            if(isMisti()){
                System.out.println(getName()+" maked pisti");
                mistiCards.addAll(boardCardRef);
            }else{
                System.out.println(getName() + " Took all cards");
                collectedCards.addAll(boardCardRef);
            }
            //Calculate the player's score based on the collected cards
            calculatePlayerScore(boardCardRef);
            boardCardRef.clear();
        }

        //Reset the chosen card index
        choosedCard = 0;
    }

    //Saves played cards to array and increases it
    private void updatePlayedCardsArr(){
        //Get the face of the last card played on the board
        String lastCardFace = boardCardRef.get(boardCardRef.size()-1).getCardFace();
        // Get the reference to the played cards array from the ExpertBot class
        int [] playedCards = ExpertBot.getPlayedCards();

        Integer cardIndex = cardMappings.get(lastCardFace);
        if (cardIndex != null) {
            playedCards[cardIndex]++;
        }
    }

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
    public HashMap<String, Integer> getCardMap(){return cardMappings;}
    public String getLevel(){return level;}

    //-----------SETTERS------------
    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}
    public static void setBoardCardRef(ArrayList<Card> boardCard) {boardCardRef = boardCard;}
}
