package boardGame;


public class PieceMover implements Runnable{

    Piece piece;
    int newRow;
    int newColumn;
    
    public PieceMover(Piece piece) {
        this.piece = piece;
    }
    
    public void setFinalCoords(int newRow, int newColumn){
       this.newRow = newRow;
       this.newColumn = newColumn;
    }

    @Override
    public void run() {
        piece.setSpeed(2);
        piece.moveTo(newRow, newColumn);
    }
    

}
