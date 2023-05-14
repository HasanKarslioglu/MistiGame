public class Card {

    //-----------VARIABLES------------
    private String suit;
    private String cardFace;
    private int cardPoint;

    //-----------CONSTRUCTORS------------
    public Card(){
        // # means empty card
        suit = "#";
        cardFace = "#";
        //Default value is 1, based on project description
        cardPoint = 1;
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
