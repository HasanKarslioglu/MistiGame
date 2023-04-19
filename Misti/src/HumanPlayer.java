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
        int chooseInt = 1;
        while (true){
            try {
                String chooseStr = sc.nextLine().trim();
                chooseInt = Integer.parseInt(chooseStr) - 1;

            }catch (Exception e){
                System.out.println("Please enter a 'number' 1 to " + getHandCards().size());
                continue;
            }
            if (chooseInt >= getHandCards().size() || chooseInt < 0){
                System.out.println("Please enter just enter 1 to " + getHandCards().size());
                continue;
            }

            System.out.println(getName() +" played " + getHandCards().get(chooseInt).getCardString());
            boardCardRef.add(getHandCards().remove(chooseInt));
            break;
        }

        super.playCard();
    }
}
