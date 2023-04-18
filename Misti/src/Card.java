public class Card {

    //-----------VARIABLES------------
    private String suit;
    private String cardFace;
    private int cardPoint;

    //-----------CONSTRUCTORS------------
    public Card(){
        //# means empty card
        suit = "#";
        cardFace = "#";
        cardPoint = 0;
    }
    public Card(String suit, String cardFace, int cardPoint) {
        this.suit = suit;
        this.cardFace = cardFace;
        this.cardPoint = cardPoint;
    }

    //-----------METHODS------------
    public String getCardString(){
        return suit + cardFace;
    }

    //-----------GETTERS------------
    public String getSuit() {return suit;}
    public String getCardFace() {return cardFace;}
    public int getCardPoint() {return cardPoint;}

    //-----------SETTERS------------
    public void setSuit(String suit) {this.suit = suit;}
    public void setCardFace(String cardFace) {this.cardFace = cardFace;}
    public void setCardPoint(int cardPoint) {this.cardPoint = cardPoint;}
}
