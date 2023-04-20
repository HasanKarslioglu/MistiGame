public class NoviceBot extends ComputerPlayer{
    public NoviceBot(String name) {
        super(name);
    }

    @Override
    public void playCard() {

        choose = rnd.nextInt(getHandCards().size());

        super.playCard();
    }
}
