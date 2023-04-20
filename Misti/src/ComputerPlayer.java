public class ComputerPlayer extends Player {

    //Buradaki choose değişkeni basic düzeyde direkt 0ı gösteriyor yani computer hiçbir
    //Plan program yapmadan hep ilk kartı atıyor... Bu deneme süreci olduğu için böyle.

    //Bu sınıftan extend edilecek olan novice bot sınıfı choose değişkenini rastgele belirleyecek ve sadece
    //Bu değişkeni değiştirecek geri kalan algoritma aynı olacak

    //Bu sınıftan extend edilecek olan regular bot sınıfı choose değişkenini daha isabetli belirleyecek.
    //Yerde aynı sayı varsa atacak yoksa ve yerde joker varsa atacak o da yoksa rasgele kart atacak.

    //Yine bu sınıftan üretilecek olan expert bot ise daha iyi oynayacak. (Bütün oyunda o zamana kadar atılmış olan
    // kartları hafızasında tutacak örneğin 2.turdayız ve yere daha önce 3 defa 7 atıldı bu botumuzda da 7 var ise onu
    // atacak çünkü kimse alamaz bu sayede)

    //Diğer sınıflar sadece choose belirlemeye yarayacak kısacası... bu sayede alttaki playCard() fonksiyonu tamamen
    //aynı olacak.

    //UNUTMA IMPORTANT PLAYER CLASSINDAKİ BÜTÜN PRİVATE OLMAYAN DEĞİŞKENLER VE METHODLAR BU SINIFTA DA GEÇERLİ
    protected int choose = 0;

    //-----------CONSTRUCTORS------------
    public ComputerPlayer(String name){
        super(name);
    }

    //-----------METHODS------------
    @Override
    public void playCard() {

        System.out.println(getName() +" played " + getHandCards().get(choose).getCardString());
        boardCardRef.add(getHandCards().remove(choose));
        super.playCard();
        choose = 0;
    }
}
