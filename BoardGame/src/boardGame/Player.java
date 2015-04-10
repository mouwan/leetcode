package boardGame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public abstract class Player {
	Board board;
	ArrayList<Piece> mylist = new ArrayList<Piece>();
	ArrayList<Piece> yourlist = new ArrayList<Piece>();
	ArrayList<Point> availablePoint = new ArrayList<Point>();
	/**Initialize a board
	 * @param board
	 */
	public Player(Board board, ArrayList<Piece> list_tmp,
			ArrayList<Piece> list_other) {
		this.board = board;
		this.mylist = list_tmp;
		this.yourlist = list_other;

	}
	
	/**
	 * Find available move count in Y direction
	 * 
	 * @param tmp
	 * @param count
	 * @return
	 */
	public int findCountY(Piece tmp) {
		int count = 0;

		for (int i = 0; i < 8; i++) {
			if (!this.board.isEmpty(i, tmp.getColumn())) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Find available move count in X direction
	 * 
	 * @param tmp
	 * @param count
	 * @return
	 */
	public int findCountX(Piece tmp) {
		int count = 0;
		try {
			for (int i = 0; i < 8; i++) {
				if (!this.board.isEmpty(tmp.getRow(), i)) {
					count = count + 1;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e35) {

		}
		return count;
	}

	/**
	 * Find available move count in inverse diagonal
	 * 
	 * @param tmp
	 * @param count
	 * @return
	 */
	public int findCountInverse(Piece tmp) {
		int count = 0;
		int m = tmp.getRow() + tmp.getColumn();

		for (int i = 0; i < 8; i++) {
			if (this.board.isLegalPosition(i, m - i)) {
				if (!this.board.isEmpty(i, m - i)) {
					count = count + 1;
				}
			}

		}
		return count;
	}

	/**
	 * Find available move count in main diagonal
	 * 
	 * @param tmp
	 * @param count
	 * @return
	 */
	public int findCountDiagonal(Piece tmp) {
		int count = 0;
		int n = tmp.getRow() - tmp.getColumn();

		for (int i = 0; i < 8; i++) {
			if (this.board.isLegalPosition(i, i - n)) {
				if (!this.board.isEmpty(i, i - n)) {
					count = count + 1;
				}
			}

		}
		return count;
	}
	
	public abstract boolean legalMoveY_North(Piece tmp);
	public abstract boolean legalMoveY_South(Piece tmp);
	public abstract boolean legalMoveX_left(Piece tmp);
	public abstract boolean legalMoveX_right(Piece tmp);
	public abstract boolean legalMoveInver_up(Piece tmp);
	public abstract boolean legalMoveInver_down(Piece tmp);
	public abstract boolean legalMoveMain_up(Piece tmp);
	public abstract boolean legalMoveMain_down(Piece tmp); 
	public abstract void findPossiblePoint(Piece tmp,ArrayList<Point>availablePoint);
	public abstract void makeMove(Piece tmp);
	
	
	public Set findNeighbor(Piece piece,ArrayList<Piece>list){
		Set neighbor = new HashSet();
		Piece neigh;
		for (int i = piece.getRow() - 1; i < piece.getRow() + 2; i++) {
			for (int j = piece.getColumn() - 1; j < piece.getColumn() + 2; j++) {
				
					if (board.isLegalPosition(i, j)) {

						if (!board.isEmpty(i, j)) {
							neigh = board.getPiece(i, j);
							if (list.contains(neigh)) {
								neighbor.add(neigh);
							}
						}
					}
			}
		}return neighbor;
	}

	/**check win
	 * @param list-represent the player's list
	 * @return
	 */
	public boolean win(ArrayList<Piece>list){
		Set<Piece> pieceConnected = new HashSet<Piece>();
		pieceConnected.addAll(findNeighbor(list.get(0),list));
		int size =0;
		int size2 =1;
		while(size != size2){
			java.util.Iterator iter = pieceConnected.iterator();
			Set<Piece> temPieceConnect = new HashSet<Piece>();
			while(iter.hasNext()){
				Piece piece = (Piece) iter.next();
				if (findNeighbor(piece,list).size()>0){
					temPieceConnect.addAll(findNeighbor(piece,list));
				}
			}size = pieceConnected.size();
			pieceConnected = temPieceConnect;
			size2 = pieceConnected.size();
		}if (pieceConnected.size()==list.size()){
			return true;
		}else{
			return false;
		}
	
	}
	

}
