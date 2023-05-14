public class NoviceBot extends ComputerPlayer{
    public NoviceBot(String name) {
        super(name, "N");
    }

    @Override
    public void playCard() {

        //It basically choose random card to play
        choosedCard = rnd.nextInt(getHandCards().size());

        super.playCard();
    }
}
