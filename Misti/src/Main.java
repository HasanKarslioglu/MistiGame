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
    static private int numberOfPlayer = 0;


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

        //Calling the setter methods because the GameMode needs to access
        //these variables through their pointers.
        GameMode.setUnDistributedDeckRef(unDistributedDeck);
        GameMode.setPlayerList(playerList);
        Player.setBoardCardRef(boardDeck);

        //It creates deck
        GameMode.createDeck();

        //It asks path of the CardValues.txt file for each card points
        GameMode.initializeDeckFromFilePath();

        //It asks how many players will play the misti game.
        GameMode.askHowManyPlayersWillPlay();

        //It asks firstly, will be there any human player and his or her name.
        //Then each computer's name and level.
        GameMode.askNamesAndLevels();

        //It asks, game will play with verbose mode or not.
        GameMode.askVerBoseMode();

        //---------SHUFFLE-----------
        Collections.shuffle(unDistributedDeck);
        
        //---------CUT-----------
        //The cut method is already suppressing the deck.
        GameMode.cut();

        //This method deals 4 cards to the ArrayList passed as its parameter.
        //In this case it fills the board with 4 cards.
        dealCards(boardDeck);
    }

    public static void loopGame(){

        //Dealing cards
        for (int i = 0; i < numberOfPlayer; i++) {
            dealCards(playerList.get(i).getHandCards());
        }

        printRound();               //Printing Round

        //Following for loop allows us to play card for each player
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < numberOfPlayer; i++) {
                playerList.get(i).playCard();
                stepEachRound++;

                //It is necessary for collecting all cards that left on the board at end of the game
                if(boardDeck.isEmpty()){
                    lastCollector=i;
                }
                printRound();       //Printing Round
            }
        }


        //Checking is game ended?
        if (unDistributedDeck.size() != 0) {
            stepEachRound = 0;
            round++;
            loopGame();
        }
    }



    public static void endGame(){

        if(boardDeck.size()!=0){
            playerList.get(lastCollector).getCollectedCards().addAll(boardDeck);
            System.out.println(playerList.get(lastCollector).getName()+" took the last cards on the board.");
        }
        System.out.println("------------END OF THE GAME------------");


        ArrayList<Integer>scores=new ArrayList<>();
        for (int i =0 ; i < numberOfPlayer; i++){
            scores.add(playerList.get(i).getScore());
        }

        Collections.sort(scores,Collections.reverseOrder());
        for(int i = 0; i < numberOfPlayer; i++){
            int score=scores.get(i);
            String playerName="";
            for(int j = 0; j < numberOfPlayer; j++){
                if(playerList.get(j).getScore()==score){
                    playerName=playerList.get(j).getName();
                    break;
                }
            }
            System.out.println(playerName + "'s score: "+ score +" points");
        }
        System.out.println("----------------------TOP 10 LIST----------------------");
        Top10List.setPlayerList(playerList);
    	Top10List.organizeWinnerList();
    }
    private static void printRound(){
        //If the verbose on then the game will show the undistributed deck, bots hands and scores
        if (GameMode.isVerbose()){
            printRoundOnVerbose();
            //Print undistributed deck if the verbose mode is enabled
            printUnDistributedDeck();
        }
        else{
            printRoundOffVerbose();
        }
    }

    private static void printRoundOffVerbose(){
        //Printing entire round...
        System.out.println("-------------------ROUND "+ round +" ----- STEP "+ stepEachRound + "-------------------");
        String printBots = "";
        for (int i = 1; i < numberOfPlayer; i++) {
            printBots += playerList.get(i).getName() +"[----] ";
        }

        System.out.println(printBots);
        System.out.println();
        System.out.println("Board("+(printSingleDeck(boardDeck)+")"));
        System.out.println();

        System.out.println(playerList.get(0).getName() +"("+ printSingleDeck(playerList.get(0).handCards)+")  Score: "+playerList.get(0).getScore());
        System.out.println("----------------------------------------------------------");
    }

    private static void printRoundOnVerbose(){

        //Printing entire round...
        System.out.println("-------------------ROUND "+ round +" ----- STEP "+ stepEachRound + "-------------------");

        //Printing bots hands
        String printBotsHand = "";
        for (int i = 1; i < numberOfPlayer; i++) {
            printBotsHand += playerList.get(i).getName() + "(" + printSingleDeck(playerList.get(i).handCards) + ")|Score:"
            + playerList.get(i).getScore()+"|   ";
        }

        System.out.println(printBotsHand);
        System.out.println();

        System.out.println("Board("+(printSingleDeck(boardDeck)+")"));
        System.out.println();

        System.out.println(playerList.get(0).getName() +"("+ printSingleDeck(playerList.get(0).handCards)+") |Score:"+playerList.get(0).getScore()+"|");
        System.out.println("----------------------------------------------------------");
    }

    //A helper-method to print a single deck in side by side form
    private static String printSingleDeck(ArrayList<Card> list){
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            temp += list.get(i).getCardString() + " ";
        }
        if (temp.length() > 2)
            temp = temp.substring(0, temp.length()-1);
        return temp;
    }

    private static void dealCards(ArrayList<Card> cardList){
        // Deal four cards to each player
        for (int i = 0; i < 4; i++) {
            cardList.add(unDistributedDeck.remove(unDistributedDeck.size()-1));
        }
    }

    private static void printUnDistributedDeck(){
        //Prints the cards that have not been distributed or partially distributed from the main deck
        String temp = "UnDistributedDeck:";
        for (int i = 0; i < unDistributedDeck.size(); i++) {
            temp += unDistributedDeck.get(i).getCardString();
        }
        System.out.println(temp);
    }
    public static ArrayList<Player> getPlayerList(){
    	return playerList;
    }

    //-----------GETTERS------------
    public static void setNumberOfPlayer(int newNumber){numberOfPlayer = newNumber;}
    //-----------SETTERS------------
    public static int getNumberOfPlayer(){return numberOfPlayer;}

}
