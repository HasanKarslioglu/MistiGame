public class NoviceBot extends ComputerPlayer{
    public NoviceBot(String name) {
        super(name);
    }

    @Override
    public void playCard() {

        choosedCard = rnd.nextInt(getHandCards().size());

        super.playCard();
    }
}
