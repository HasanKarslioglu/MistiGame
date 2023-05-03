import java.util.ArrayList;
import java.util.Collections;


public class Main {


    //Board cards
    private static ArrayList<Card> boardDeck = new ArrayList<>();

    //Undistributed cards which has 52 card in the beginning of the game
    private static ArrayList<Card> unDistributedDeck = new ArrayList<>();

    //It increases at the end of each round
    static private int round = 1;

    //It increases after each player plays
    static private int stepEachRound = 0;

    //The cards on the board will check which player was collected last. We defined the reference in bytes to take up little memory.
    private static int lastCollector;

    static private ArrayList<Player> playerList = new ArrayList<>();


    //It will be assigned after the asking user by using askHowManyPlayersWillPlay() method
    static private int numberOfPlayer;

    //Main method is being operating with just 3 method
    public static void main(String[] args) {
        //This function will be executed for one-time actions at the head of the game
        initializeGame();
        //This method will be executed for represent each round of the game
        loopGame();
        //This function will be executed for one-time actions at the end of the game
        endGame();
    }

    public static void initializeGame(){

        GameMode.setUnDistributedDeckRef(unDistributedDeck);
        GameMode.setPlayerList(playerList);
        Player.setBoardCardRef(boardDeck);
        GameMode.makeDeck();

        //----------------------------------------------------------------------
        //FOR TESTING
                    GameMode.initializeDeckFromFilePath();
                    numberOfPlayer = 4;

                    HumanPlayer human = new HumanPlayer("Hasan");
                    RegularBot bot1 = new RegularBot("Bot 1");
                    RegularBot bot2 = new RegularBot("Bot 2");
                    RegularBot bot3 = new RegularBot("Bot 3");

                    playerList.add(human);
                    playerList.add(bot1);
                    playerList.add(bot2);
                    playerList.add(bot3);

        //FOR TESTING
        //----------------------------------------------------------------------

        Collections.shuffle(unDistributedDeck);

        //It asks path of the CardValues.txt file for points
        GameMode.initializeDeckFromFilePath();

        //It asks how many players will play the misti game.
        GameMode.askHowManyPlayersWillPlay();

        //It asks firstly, will be there any human player and his or her name.
        //Then each computer's name and level.
        GameMode.askNamesAndLevels();

        //This method deals 4 cards to the ArrayList passed as its parameter.
        //In this case it fills the board with 4 cards.
        dealCards(boardDeck);
        
        //---------SHUFFLE-----------
        Collections.shuffle(unDistributedDeck);
        
        //---------CUT-----------
        //The cut method is already suppressing the deck.
        GameMode.cut();
    }

    public static void loopGame(){

        printUnDistributedDeck();

        //Dealing cards
        for (int i = 0; i < numberOfPlayer; i++) {
            dealCards(playerList.get(i).getHandCards());
        }

        printRound();

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < numberOfPlayer; i++) {
                playerList.get(i).playCard();
                stepEachRound++;

                if(boardDeck.isEmpty()){
                    lastCollector=i;
                }

                printRound();
            }
        }

        round++;

        //Checking is game ended?
        if (unDistributedDeck.size() != 0) {
            stepEachRound = 0;
            loopGame();
        }
    }

    public static void endGame(){
    	Top10List.top10Func();
        //Kimin kazandığı yazacak ekranda ve oyun bitecek
        //Oyuncunun tekrardan oynayıp oynamadığını soracaz
        
        System.out.println("------------END OF THE GAME------------");
        if(boardDeck.size()!=0){
            playerList.get(lastCollector).getCollectedCards().addAll(boardDeck);
            System.out.println(playerList.get(lastCollector)+" took the last cards on the board.");
        }
    }

    private static void printRound(){
        //Bütün bir roundu yazdırmak için olan kod
        System.out.println("-------------------ROUND "+ round +" ----- STEP "+ stepEachRound + "-------------------");

        String printBotsHand = "";
        for (int i = 1; i < numberOfPlayer; i++) {
            printBotsHand += playerList.get(i).getName() + "(" + printDeck(playerList.get(i).handCards) + ")   ";
        }

        System.out.println(printBotsHand);
        System.out.println();

        System.out.println("Board("+(printDeck(boardDeck)+")"));
        System.out.println();

        System.out.println("MyHand("+printDeck(playerList.get(0).handCards)+")");
        System.out.println("----------------------------------------------------------");
        //TO DO ilerde buna mod ekleyip eğer onu seçtiysek rakibin eli ve dağıtılmamış deste gösterilecek
        //Ya da ikisi de gizli olacak şeklinde ayarlayacağız. Şu an test aşamasında bu yüzden her şeyi gizlemeden
        //Gösteriyor
    }

    //Bir tane desteyi yazdırmak için olan yardımcı fonksiyon
    private static String printDeck(ArrayList<Card> list){
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            temp += list.get(i).getCardString();
        }
        return temp;
    }

    //Kartları dağıtan fonksiyon
    private static void dealCards(ArrayList<Card> cardList){
        for (int i = 0; i < 4; i++) {
            cardList.add(unDistributedDeck.remove(unDistributedDeck.size()-1));
        }
    }

    //Henüz dağıtılmamış olan ya da bir kısmı dağıtılmış olan ana destemizi yazdırıyor
    private static void printUnDistributedDeck(){
        String temp = "UnDistributedDeck:";
        for (int i = 0; i < unDistributedDeck.size(); i++) {
            temp += unDistributedDeck.get(i).getCardString();
        }
        System.out.println(temp);
    }
    public static ArrayList<Player> getPlayerList(){
    	return playerList;
    }

    public static void setNumberOfPlayer(int newNumberOfPlayer) {numberOfPlayer = newNumberOfPlayer;}
    public static int getNumberOfPlayer(){return numberOfPlayer;}
}
