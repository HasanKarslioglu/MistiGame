import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {


    //*Tahtanın eli
    private static ArrayList<Card> boardDeck = new ArrayList<>();
    //*Dağıtılmamış kartlar
    private static ArrayList<Card> unDistributedDeck = new ArrayList<>();
    //*Round her bir turda artcak
    static private int round = 0;
    //*Bu ilerde eklencek her turda biri kart attığında step artacak
    //static private int stepEachRound = 0;
    //*Player listesi(add veya remove yapmayacağımız için dümdüz array kullandım arraylist yerine)
    static private Player[] playerList = new Player[4];


    //*Main sadece 3 kısımdan oluşacak
    public static void main(String[] args) {
        //*Oyunun başında tek seferde gerçekleşecek işlemler için bu fonksiyon çağrılacak
        initializeGame();
        //*Loop game sürekli kendini çağıracak
        loopGame();
        //*Oyunun sonunda tek seferde gerçekleşecek işlemler için bu fonksiyon çağrılacak
        endGame();
    }

    public static void initializeGame(){

        //Reading txt file that created before by hand
        try {
            File scoreText = new File("src\\CardValues.txt");
            Scanner sc = new Scanner(scoreText);

            while (sc.hasNextLine()){
                //Creating unDistributedDeck based on CardValues.txt
                String[] currentLine = sc.nextLine().split(" ");
                Card tempCard = new Card();
                tempCard.setSuit(currentLine[0].substring(0,1));
                tempCard.setCardFace(currentLine[0].substring(1));
                tempCard.setCardPoint(Integer.parseInt(currentLine[1]));
                unDistributedDeck.add(tempCard);
            }
        }catch (Exception e){
            System.out.println("Path couldn't found");
        }

        //*Oyuncularımız oluşturuyoruz ilk versiyonda sadece 4 oynucu ve manuel oluşturuyoruz
        HumanPlayer human = new HumanPlayer("Hasan");
        ComputerPlayer bot1 = new ComputerPlayer("Bot 1");
        ComputerPlayer bot2 = new ComputerPlayer("Bot 2");
        ComputerPlayer bot3 = new ComputerPlayer("Bot 3");

        //*Oyuncularımızı player listemize ekliyoruz
        playerList[0] = human;
        playerList[1] = bot1;
        playerList[2] = bot2;
        playerList[3] = bot3;

        //*Yere 4 tane kart açıyoruz
        for (int i = 0; i < 4; i++) {
            boardDeck.add(unDistributedDeck.remove(unDistributedDeck.size() - 1));
        }

        //TO DO
        //Kullanıcıdan bilgiler alınacak
        //Shuffle yapılacak
        //Cut yapılacak
        //Tek tek bütün oyuncuların adı ve zorluk derecesi girilecek
    }

    public static void loopGame(){

        //Oyun bitene kadar roundlar şeklinde loopa giriyor bu fonksiyon
        //Her oyuncuya kartlar dağıtılıyor
        for (int j = 0; j < 4; j++) {
            dealCards(playerList[j]);
        }
        //Kartlar printleniyor
        printRound();
        printUnDistributedDeck();

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

    public static void printRound(){
        System.out.println("-----------------ROUND "+ round +"------------------------");
        System.out.print("Bot1(" + printDeck(playerList[1].handCards) + ")  " +
                         "Bot2(" + printDeck(playerList[2].handCards) + ")  " +
                         "Bot3(" + printDeck(playerList[3].handCards) + ")");
        System.out.println("");
        System.out.println("");

        System.out.println("Board("+(printDeck(boardDeck)+")"));
        System.out.println("");

        System.out.println("MyHand("+printDeck(playerList[3].handCards)+")");
    }

    private static String printDeck(ArrayList<Card> list){
        String temp = "";
        for (Card each: list) {
            temp += each.getCardString();
        }
        return temp;
    }

    private static void dealCards(Player player){
        for (int i = 0; i < 4; i++) {
            player.getHandCards().add(unDistributedDeck.remove(unDistributedDeck.size()-1));
        }
    }

    private static void printUnDistributedDeck(){
        String temp = "UnDistributedDeck:";
        for (int i = 0; i < unDistributedDeck.size(); i++) {
            temp += unDistributedDeck.get(i).getCardString();
        }
        System.out.println(temp);
    }
}






