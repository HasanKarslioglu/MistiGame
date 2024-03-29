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

    public static void initializeDeckFromFilePath(String path){
        //Lines going to be store each line of cardValues.txt except double star line
        ArrayList<String> lines = new ArrayList<>();

        Scanner scanText = null;
        //While loop asks path of txt file that contains card values
        while(true){
            try {
                File scoreText = new File(path);
                //Creating scanner that will scan CardValues.txt
                scanText = new Scanner(scoreText);
                System.out.println("Path founded successfully.");
                break;
            }catch (FileNotFoundException e){
                System.out.println("Path couldn't found, please close and restart");
            }catch (IOException e) {
                System.out.println("Error reading file. please close and restart");
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



    //Creates a new bot with the given level and name then returns reference of that bot or null.
    public static void createBot(String name, String level){
        switch (level){
            case "N":
                playerList.add(new NoviceBot(name)); break;
            case "R":
                playerList.add(new RegularBot(name)); break;
            case "E":
                playerList.add(new ExpertBot(name)); break;
            case "H":
                playerList.add(0, new HumanPlayer(name));
        }
    }

    public static void initializeGameFromArguments(String[] args){

        Main.setNumberOfPlayer(Integer.parseInt(args[0]));

        //It asks path of the CardValues.txt file for each card points
        GameMode.initializeDeckFromFilePath(args[1]);


        //It creates players and after that adding playerList based on arguments
        switch (Main.getNumberOfPlayer()){
            case 2:
                GameMode.createBot(args[2],args[3]);
                GameMode.createBot(args[4],args[5]);
                GameMode.setVerboseMode(Boolean.parseBoolean(args[6]));
                break;
            case 3:
                GameMode.createBot(args[2],args[3]);
                GameMode.createBot(args[4],args[5]);
                GameMode.createBot(args[6],args[7]);
                GameMode.setVerboseMode(Boolean.parseBoolean(args[8]));
                break;
            case 4:
                GameMode.createBot(args[2],args[3]);
                GameMode.createBot(args[4],args[5]);
                GameMode.createBot(args[6],args[7]);
                GameMode.createBot(args[8],args[9]);
                GameMode.setVerboseMode(Boolean.parseBoolean(args[10]));
                break;
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
    public static void setVerboseMode(boolean bl){isVerbose = bl;}
}
