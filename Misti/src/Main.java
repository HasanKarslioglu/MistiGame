import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private static byte lastCollector;

    static private ArrayList<Player> playerList = new ArrayList<>();


    //It will be assigned after the asking user by using askHowManyPlayersWillPlay() method
    static private int numberOfPlayer;

    //Too many method will use this static scanner object
    static private Scanner sc = new Scanner(System.in);

    static private final String[] suits = {"♠","♣","♥","♦"};
    static private final String[] cardFaces = {"A","2","3","4","5","6","7",
                                               "8","9","10","J","Q","K"};



    //Main method is being operating with just 3 method
    public static void main(String[] args) {
        //This function will be executed for one-time actions at the head of the game
        initializeGame();
        //This method will be executed for represent each round of the game
        loopGame();
        //This function will be executed for one-time actions at the end of the game
        //endGame();
    }

    public static void initializeGame(){

        //----------------------------------------------------------------------
        //FOR TESTING
                    makeDeck();
                    //initializeDeck();
                    initializeDeckFromFilePath();
                    numberOfPlayer = 4;

                    HumanPlayer human = new HumanPlayer("Hasan");
                    RegularBot bot1 = new RegularBot("Bot 1");
                    RegularBot bot2 = new RegularBot("Bot 2");
                    RegularBot bot3 = new RegularBot("Bot 3");

                    playerList.add(human);
                    playerList.add(bot1);
                    playerList.add(bot2);
                    playerList.add(bot3);

        //Collections.shuffle(unDistributedDeck);


        //FOR TESTING
        //----------------------------------------------------------------------

        //It asks path of the CardValues.txt file for points
        //initializeDeckFromFilePath();

        //It asks how many players will play the misti game.
        //askHowManyPlayersWillPlay();

        //It asks firstly, will be there any human player and his or her name.
        //Then each computer's name and level.
        //askNamesAndLevels();
        Player.setBoardCardRef(boardDeck);

        //This method deals 4 cards to the ArrayList passed as its parameter.
        //In this case it fills the board with 4 cards.
        dealCards(boardDeck);
        
        //---------SHUFFLE-----------
        Collections.shuffle(unDistributedDeck);
        
        //---------CUT-----------
        //The cut method is already suppressing the deck.
        cut();
        //printUnDistributedDeck();


    }

    public static void loopGame(){

        printUnDistributedDeck();
        
        for (int i = 0; i < numberOfPlayer; i++) {
            dealCards(playerList.get(i).getHandCards());
        }

        printRound();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < numberOfPlayer; i++) {
                playerList.get(i).playCard();
                stepEachRound++;
              if(boardDeck.isEmpty()){
                  lastCollector=(byte)i;
              }
                printRound();

            }
        }

        round++;

        if (unDistributedDeck.size() != 0) {
            stepEachRound = 0;
            loopGame();
        }
    }

    public static void endGame(){
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

    private static void askHowManyPlayersWillPlay(){
        do {
            try {
                //Scanning user input and converted to an integer and saved to numberOfPlayer
                numberOfPlayer = Integer.parseInt(sc.nextLine());
                //It checks whether the number is valid or invalid for us
                if (numberOfPlayer < 2 || numberOfPlayer > 4) {
                    System.out.println("Please enter 'just' 2, 3 or 4");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter just number: 2, 3 or 4");
            }
        } while (numberOfPlayer < 2 || numberOfPlayer > 4);
    }

    private static void askNamesAndLevels(){

        int numberOfBots = numberOfPlayer;
        System.out.println("Will there be a human player in the game? type 'y' for yes 'n' for no");
        while(true){
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("y")){           //Checks whether the user's input corresponds to 'yes'
                System.out.println("What will be human player name?");
                String name = sc.nextLine().trim();
                playerList.add(0, new HumanPlayer(name));        //Create new human player and add it to the player list
                System.out.println("Human added successfully");
                numberOfBots--;          //If there is any human player number of bots must be equal to (totalplayer-1)
                break;
            }else if(input.equalsIgnoreCase("n")){      //Checks whether the user's input corresponds to 'no'
                break;
            }else{
                System.out.println("You entered invalid word. Please just type 'y' for yes 'n' for no");
            }
        }

        for (int i = 0; i < numberOfBots; i++) {

            System.out.println("What will be "+ (i+1) + ". bot name?");
            String name = sc.nextLine().trim();

            int level;
            System.out.println("What will be " + name + "' level?");
            System.out.println("Enter 1 for novice bot, 2 for regular bot, 3 for expert bot");

            //Keep asking user for bot level until a valid input is entered
            while (true){
                try{
                    String levelStr = sc.nextLine().trim();
                    level = Integer.parseInt(levelStr);
                    //If level is valid loop must be ended
                    if (level >= 1 && level <= 3) break;

                    System.out.println("You entered invalid number. Just 1, 2 or 3 will be accepted.");
                }catch (NumberFormatException e){
                    System.out.println("Please enter 'just number' like 1, 2 or 3");
                }
            }

            //Create new bot and add it to the player list
            //And also this uses helper-method called createBot. Please refer to that method for more information.
            playerList.add(createBot(level, name));
            System.out.println(playerList.get(playerList.size()-1).getName()+" Added successfully. It level is "+ playerList.get(playerList.size()-1).getClass().getSimpleName());
        }
    }

    public static void cut() {
        //bu methodta bilgisayar randomla destenin kesileceği kartı seciyor tempdeck arraylisti ile undistributeddecki
        // ortadan kesilen destesi başa geçiyor

        Random random=new Random();
        int cutcard=random.nextInt(50)+2;//cutcard will be choosen between 2. and 51. cards. So random will be select number 0-49
                                               //when we add 2 cutcard will be initilaze between 2-51
        System.out.println("Computer cut the deck from "+cutcard+" .card");

       List<Card>tempdeck=new ArrayList<>(unDistributedDeck.subList(cutcard,unDistributedDeck.size()));
       tempdeck.addAll(unDistributedDeck.subList(0,cutcard));
       unDistributedDeck.clear();
       unDistributedDeck.addAll(tempdeck);
        System.out.println("The cutted deck: ");
        for (int i = 0; i < unDistributedDeck.size(); i++) {
            System.out.print(unDistributedDeck.get(i).getCardString() + " ");
        }
    }

    //Creates a new bot with the given level and name then returns reference of that bot or null.
    private static Player createBot(int level, String name){
        switch (level){
            case 1:
                return new NoviceBot(name);
            case 2:
                return new RegularBot(name);
            default:
                System.out.println("Couldn't generate bot");
                return null;
        }
    }

    private static void initializeDeckFromFilePath(){

        //Lines going to be store each line of cardValues.txt except double star line
        ArrayList<String> lines = new ArrayList<>();

        System.out.println("Please enter path of card values text");
        Scanner scanText = null;
        //While loop asks path of txt file that contains card values
        while(true){
            try {
                //Reading file path from command line
                String filePath = sc.nextLine().trim();
                File scoreText = new File(filePath);
                //Creating scanner that will scan CardValues.txt
                scanText = new Scanner(scoreText);
                System.out.println("Path founded successfully.");
                break;
            }catch (FileNotFoundException e){
                System.out.println("Path couldn't found. Enter path of the your txt file. Please try again.");
            }catch (IOException e) {
                System.out.println("Error reading file. Please try again.");
            }
        }

        //Following while loop scanning txt file and storing to 'lines array' without double star
        while (scanText.hasNextLine()){
            String currentLine = scanText.nextLine();

            //If a double star is found in any line of the txt file, all card
            //values will be changed based on the double star value
            if (currentLine.substring(0,2).equals("**")){
                changeAllCardPoints(Integer.parseInt(currentLine.split(" ")[1]));
            }else {
                //Otherwise, the line will be stored in the lines array, which will be used to change other card values
                lines.add(currentLine.trim());
            }
        }

        if (scanText != null)
                scanText.close();


        //For each card in the undistributed deck
        for (Card eachCard: unDistributedDeck) {

            // Iterate over each line in the card values txt file
            for (String eachLine : lines) {

                //If the line starts with a "*", it means it contains
                //a card face value and its corresponding point value
                if (eachLine.substring(0,1).equals("*")){
                    //If the current card face matches the face value in the line
                    //Change the card point based on the point value in the line
                    if (eachCard.getCardFace().equals(eachLine.split(" ")[0].substring(1))){
                        changeCardPointBasedOnLine(eachLine, eachCard);
                        break;
                    }

                //Otherwise, the line contains a suit value and its corresponding point value
                }else {
                    //If the current card suit matches the suit value in the line
                    //Change the card point based on the point value in the line
                    if (eachCard.getSuit().equals(eachLine.substring(0,1))){
                        changeCardPointBasedOnLine(eachLine, eachCard);
                        break;
                    }
                }
            }
        }
    }


    //This helper-method takes a new point value and a card object, and sets the card point to the new point value
    private static void changeCardPointBasedOnLine(String newPoint, Card card){
        try{
            card.setCardPoint(Integer.parseInt(newPoint.split(" ")[1]));
        }catch (NumberFormatException e){
            System.out.println("There were problems while reading card values txt file");
            System.out.println("Please check your txt file, the format must be <suit><cardFace> <point>");
        }
    }

    //THIS METHOD WILL BE USE FOR JUST TESTING!!!
    private static void initializeDeck(){
        ArrayList<String> lines = new ArrayList<>();

        try {
            File scoreText = new File("C:\\Users\\Hasan\\Desktop\\MistiGame\\Misti\\src\\CardValues.txt");
            Scanner scanText = new Scanner(scoreText);
            while (scanText.hasNextLine()){
                String currentLine = scanText.nextLine();
                if (currentLine.substring(0,2).equals("**")){
                    changeAllCardPoints(Integer.parseInt(currentLine.split(" ")[1]));
                }else {
                    lines.add(currentLine.trim());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            for (Card eachCard: unDistributedDeck) {
                for (String eachLine : lines) {
                    if (eachLine.substring(0,1).equals("*")){
                        if (eachCard.getCardFace().equals(eachLine.split(" ")[0].substring(1))){
                            eachCard.setCardPoint(Integer.parseInt(eachLine.split(" ")[1]));
                            break;
                        }
                    }else {
                        if (eachCard.getSuit().equals(eachLine.substring(0,1))){
                            eachCard.setCardPoint(Integer.parseInt(eachLine.split(" ")[1]));
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void changeAllCardPoints(int newPoint){
        for(Card each : unDistributedDeck){
            each.setCardPoint(newPoint);
        }
    }
    private static void makeDeck(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card tempCard = new Card();
                tempCard.setSuit(suits[i]);
                tempCard.setCardFace(cardFaces[j]);
                tempCard.setCardPoint(0);
                unDistributedDeck.add(tempCard);
            }
        }
    }
}
