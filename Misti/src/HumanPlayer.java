public class HumanPlayer extends Player {


    //-----------CONSTRUCTORS------------
    public HumanPlayer(String name){
        super(name);
    }


    //-----------METHODS------------
    @Override
    public void playCard() {
        //Basitçe oyuncuya soruyoruz ve girilen kartı yere atıyoruz
        System.out.println("Which card do you want to play enter 1 to " + getHandCards().size());
        int choose = sc.nextInt()-1;
        System.out.println(getName() +" played " + getHandCards().get(choose).getCardString());
        boardCardRef.add(getHandCards().remove(choose));
        //TO DO
        //Yanlış sayı girdiğimizde hata veriyor o düzeltilecek
    }
}
