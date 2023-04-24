import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class Main {


    //Board cards
    private static ArrayList<Card> boardDeck = new ArrayList<>();

    //Undistributed cards which has 52 card in the beginning of the game
    private static ArrayList<Card> unDistributedDeck = new ArrayList<>();

    //It increases at the end of each round
    static private int round = 1;

    //It increases after each player plays
    static private int stepEachRound = 0;

    static private ArrayList<Player> playerList = new ArrayList<>();


    //It will be assigned after the asking user by using askHowManyPlayersWillPlay() method
    static private int numberOfPlayer;

    //Too many method will use this static scanner object
    static private Scanner sc = new Scanner(System.in);


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

        //----------------------------------------------------------------------
        //FOR TESTING
                    readCardValuesText();
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

        //It asks path of the CardValues.txt file for points
        //initializeDeckFromFilePath();

        //It asks how many players will play the misti game.
        //askHowManyPlayersWillPlay();

        //It asks firstly, will be there any human player and his or her name.
        //Then each computer's name and level.
        //askNamesAndLevels();
        Player.setBoardCardRef(boardDeck);
        Collections.shuffle(unDistributedDeck);

        //This method deals 4 cards to the ArrayList passed as its parameter.
        //In this case it fills the board with 4 cards.
        dealCards(boardDeck);

    }

    public static void loopGame(){

        printUnDistributedDeck();
        //Oyun bitene kadar roundlar şeklinde loopa giriyor bu fonksiyon
        //Her oyuncuya kartlar dağıtılıyor
        for (int i = 0; i < numberOfPlayer; i++) {
            dealCards(playerList.get(i).getHandCards());
        }
        //Kartlar printleniyor
        printRound();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < numberOfPlayer; i++) {
                playerList.get(i).playCard();
                printRound();
            }
        }



        //ROund arttırılıyor
        round++;
        //Oyun bitene kadar roundlar şeklinde loopa giriyor bu fonksiyon
        if (unDistributedDeck.size() != 0)
            loopGame();

        //To do bu fonksiyonun son hali şöyle görünmeli
        //Kartlar dağıtılacak herkese aynı anda sonra bütün eller printlenecek
        //Sıra bizdeyse biz kart seçip atıcaz
        //Sıra bizde değilse bot random sayı atıcak
        //Her kart atıldığında round printlenecek
        //Dağıtılacak kart kalmışsa fonksiyon baştan yine çağrılacak
        //Kart kalmamışsa loop game bitecek end game başlayacak.
    }

    public static void endGame(){
        //Oyun sonu yerde kalan kartlar son kazanana verilecek
        //Kimin kazandığı yazacak ekranda ve oyun bitecek
        //Oyuncunun tekrardan oynayıp oynamadığını soracaz
    }



    private static void printRound(){
        //Bütün bir roundu yazdırmak için olan kod
        System.out.println("-------------------ROUND "+ round +" ----- STEP "+ stepEachRound + "-------------------");

        String printBotsHand = "";
        for (int i = 1; i < numberOfPlayer; i++) {
            printBotsHand += playerList.get(i).getName() + "(" + printDeck(playerList.get(i).handCards) + ")   ";
        }

        System.out.println(printBotsHand);
        System.out.println("");

        System.out.println("Board("+(printDeck(boardDeck)+")"));
        System.out.println("");

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

    private static void readCardValuesText(){
        //Reading txt file that created before by hand
        try {
            File scoreText = new File("src\\CardValues.txt");
            Scanner scanText = new Scanner(scoreText);

            while (scanText.hasNextLine()){
                //Creating unDistributedDeck based on CardValues.txt
                String[] currentLine = scanText.nextLine().split(" ");

                Card tempCard = new Card();
                tempCard.setSuit(currentLine[0].substring(0,1));
                tempCard.setCardFace(currentLine[0].substring(1));
                tempCard.setCardPoint(Integer.parseInt(currentLine[1]));

                unDistributedDeck.add(tempCard);
            }

        }catch (Exception e){
            System.out.println("Path couldn't found. It must be in the src file. Please try again.");
            readCardValuesText();
        }
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

        System.out.println("Please enter path of card values text");
        Scanner scanText = null;
        while(true){
            try {
                //Reading file path from command line
                String filePath = sc.nextLine().trim();
                File scoreText = new File(filePath);
                System.out.println("Path founded successfully.");
                //Creating scanner that will scan CardValues.txt
                scanText = new Scanner(scoreText);

                while (scanText.hasNextLine()){
                    //Creating unDistributedDeck based on CardValues.txt
                    String[] currentLine = scanText.nextLine().split(" ");

                    //It checks is there any error while reading each line
                    if (currentLine.length != 2) {
                        System.out.println("Invalid line in card values file: " + Arrays.toString(currentLine));
                        continue;
                    }

                    //Creating empty card that will be filled based on each line of CardValues.txt
                    Card tempCard = new Card();
                    //Filling card suit
                    tempCard.setSuit(currentLine[0].substring(0,1));
                    //Filling card face
                    tempCard.setCardFace(currentLine[0].substring(1));
                    //Filling card point
                    tempCard.setCardPoint(Integer.parseInt(currentLine[1]));

                    unDistributedDeck.add(tempCard);
                }
                break;

            }catch (FileNotFoundException e){
                System.out.println("Path couldn't found. It must be in the src file. Please try again.");
            }catch (IOException e) {
                System.out.println("Error reading file. Please try again.");
            }finally {
                if (scanText != null)
                    scanText.close();
            }
        }
    }
}






