import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameMode {
    static private boolean isVerbose;
    static private final String[] suits = {"S","C","H","D"};
    static private final String[] cardFaces = {"A","2","3","4","5","6","7",
            "8","9","10","J","Q","K"};

    //These references will be filled in the main. Thanks to this, GameMode class
    //will be able to reach playerList
    private static ArrayList<Card> unDistributedDeckRef;
    private static ArrayList<Player> playerList;
    static private Scanner sc = new Scanner(System.in);

    public static void createDeck(){

        // Generates a deck of cards by iterating through each suit and card face.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card tempCard = new Card();
                tempCard.setSuit(suits[i]);
                tempCard.setCardFace(cardFaces[j]);
                //Each card's points will be changed after the calling initializeDeckFromFilePath()
                unDistributedDeckRef.add(tempCard);
            }
        }
    }

    public static void initializeDeckFromFilePath(){
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
            if (currentLine.startsWith("**")){
                changeAllCardPoints(Integer.parseInt(currentLine.split(" ")[1]));
            }else {
                //Otherwise, the line will be stored in the lines array, which will be used to change other card values
                lines.add(currentLine.trim());
            }
        }

        if (scanText != null)
            scanText.close();


        //For each card in the undistributed deck
        for (Card eachCard: unDistributedDeckRef) {

            // Iterate over each line in the card values txt file
            for (String eachLine : lines) {

                //If the line starts with a "*", it means it contains
                //a card face value and its corresponding point value
                if (eachLine.startsWith("*")){
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

    //This method uses by double star(**) in the card values text
    private static void changeAllCardPoints(int newPoint){
        for(Card each : unDistributedDeckRef){
            each.setCardPoint(newPoint);
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

    public static void askHowManyPlayersWillPlay(){

        System.out.println("How many player will play the game. Enter 2, 3 or 4");
        int numberOfPlayer = 0;
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

        Main.setNumberOfPlayer(numberOfPlayer);
    }


    public static void askVerBoseMode(){
        System.out.println("Will you play in verbose mode? type 'y' for yes 'n' for no");
        while (true){
            String input=sc.nextLine().trim();
            if (input.equalsIgnoreCase("y")) {            //Checks whether the user's input corresponds to 'yes'
                isVerbose=true;
                break;
            }else if(input.equalsIgnoreCase("n")){      //Checks whether the user's input corresponds to 'no'
                isVerbose=false;
                break;
            }else{
                System.out.println("You entered invalid word. Please just type 'y' for yes 'n' for no");
            }
        }

    }
    public static void askNamesAndLevels(){

        //In the beginning of the asking, we don't know will be there any human
        //That's why, we assume there won't be a human
        //If there will be one, the number of bots decrease by one
        int numberOfBots = Main.getNumberOfPlayer();
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

            //Asking name of each bot
            System.out.println("What will be "+ (i+1) + ". bot name?");
            String name = sc.nextLine().trim();

            //Asking level of each bot
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
            case 3:
                return new ExpertBot(name);
            default:
                System.out.println("Couldn't generate bot");
                return null;
        }
    }


    public static void cut() {
        //In this method, program choose the card with random. Then, method create a new deck which start with cutted card
        //and remove 0 to cutted card from undistribured deck. Finally tempdeck will added from the end

        Random random=new Random();
        int cutcard=random.nextInt(50)+2;//cutcard will be choosen between 2. and 51. cards. So random will be select number 0-49
        //when we add 2 cutcard will be initilaze between 2-51
        if(isVerbose()){
            System.out.println("Computer cut the deck from "+cutcard+". card");
        }
        List<Card> tempdeck=new ArrayList<>(unDistributedDeckRef.subList(cutcard,unDistributedDeckRef.size()));
        tempdeck.addAll(unDistributedDeckRef.subList(0,cutcard));
        unDistributedDeckRef.clear();
        unDistributedDeckRef.addAll(tempdeck);
    }

    public static void setUnDistributedDeckRef(ArrayList newUnDistributedDeck){unDistributedDeckRef = newUnDistributedDeck;}
    public static void setPlayerList(ArrayList<Player> newPlayerList){playerList = newPlayerList;}
    public static boolean isVerbose() {return isVerbose;}
}
