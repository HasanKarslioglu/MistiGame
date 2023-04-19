import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.awt.FocusTraversalPolicy;
import java.io.File;
import java.util.Scanner;

import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.text.StyledEditorKit.ForegroundAction;

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
    static private ArrayList<Player> playerList = new ArrayList<>();

    static private int numberOfPlayer;


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

        //BUNU KULLANICIYA SORACAĞIZ şu anlık 2 3 veya 4 için çalışıyo
        numberOfPlayer = 4;

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
      //---------SHUFFLE-----------
        Collections.shuffle(unDistributedDeck);
        //Collections methodlarından direkt shuffle'ı kart destesi için kullandık
        printUnDistributedDeck();
        //---------CUT-----------
        Cut();
        printUnDistributedDeck();
        
        
        //*Oyuncularımız oluşturuyoruz ilk versiyonda sadece 4 oynucu ve manuel oluşturuyoruz
        HumanPlayer human = new HumanPlayer("Hasan");
        ComputerPlayer bot1 = new ComputerPlayer("Bot 1");
        ComputerPlayer bot2 = new ComputerPlayer("Bot 2");
        ComputerPlayer bot3 = new ComputerPlayer("Bot 3");

        //*Oyuncularımızı player listemize ekliyoruz
        playerList.add(human);
        playerList.add(bot1);
        playerList.add(bot2);
        playerList.add(bot3);

        playerList.get(0).setBoardCardRef(boardDeck);

        //*Yere 4 tane kart açıyoruz
        for (int i = 0; i < 4; i++) {
            boardDeck.add(unDistributedDeck.remove(unDistributedDeck.size() - 1));
        }

        //TO DO
        //Kullanıcıdan bilgiler alınacak(Kaç oyuncu oynayacak 2 mi 3 mü 4 mü)
        //Bu belirtildikten sonra örneğin 3 kişi seçti, ilk oyuncu insan mı olacak bot mu seçtikten sonra adı ne
        //2. oyuncu insan mı bot mu seçtikten sonra adı ne
        //eğer bir defa bile insan seçilmişse bir daha o seçenek çıkmayacak diğerleri otomatik bot olacak ama adını ve
        //zorluk derecesini soracağız
        
        
        
        
        
        
        
        //Cut yapılacak(Bunu baştan yazcaz sanırım (eğer öyle bir metod yoksa))
        //Tek tek bütün oyuncuların adı ve zorluk derecesi girilecek
    }

    public static void loopGame(){

        printUnDistributedDeck();
        //Oyun bitene kadar roundlar şeklinde loopa giriyor bu fonksiyon
        //Her oyuncuya kartlar dağıtılıyor
        for (int i = 0; i < numberOfPlayer; i++) {
            dealCards(playerList.get(i));
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

    public static void printRound(){
        //Bütün bir roundu yazdırmak için olan kod
        System.out.println("-----------------ROUND "+ round +"------------------------");
        System.out.print("Bot1(" + printDeck(playerList.get(1).handCards) + ")  " +
                         "Bot2(" + printDeck(playerList.get(2).handCards) + ")  " +
                         "Bot3(" + printDeck(playerList.get(3).handCards) + ")");
        System.out.println("");
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
            temp += list.get(i).getCardString()+" ";
        }
        return temp;
    }

    //Kartları dağıtan fonksiyon
    private static void dealCards(Player player){
        for (int i = 0; i < 4; i++) {
            player.getHandCards().add(unDistributedDeck.remove(unDistributedDeck.size()-1));
        }
    }


    //Henüz dağıtılmamış olan ya da bir kısmı dağıtılmış olan ana destemizi yazdırıyor
    private static void printUnDistributedDeck(){
        String temp = "UnDistributedDeck:";
        for (int i = 0; i < unDistributedDeck.size(); i++) {
            temp += unDistributedDeck.get(i).getCardString()+" ";
        }
        System.out.println(temp);
    }

    public static void Cut() {
		Scanner scanner =new Scanner(System.in);
	
		System.out.println("Where do you want to cut the deck?");
		int cutCard=scanner.nextInt();//4
		Card[] tempCard=new Card[cutCard];

		for(int i=0;i<cutCard;i++) {
			 tempCard[i]=unDistributedDeck.get(Math.round((i-1)/2));
			 unDistributedDeck.remove(Math.round((i-1)/2));
			
		}
		System.out.println("unDistributedDeck size right now:"+unDistributedDeck.size());
		for(Card card : tempCard) {
			System.out.print(card.getCardString()+"|");
		}System.out.println("");
	for(int i=0;i<cutCard;i++) {
     unDistributedDeck.add(unDistributedDeck.size(), tempCard[i]);		
	}
}




}

