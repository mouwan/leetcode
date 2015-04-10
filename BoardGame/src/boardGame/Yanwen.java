package boardGame;

import java.awt.Point;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Yanwen extends Player {

	LinesOfAction test;

	
	Iterator iter;
	Random random = new Random();
	Random random2 = new Random();

	public Yanwen(Board board, ArrayList<Piece> list_tmp,
			ArrayList<Piece> list_other) {
		super(board,list_tmp,list_other);

	}

	
	/**
	 * Check whether it's possible to move to north
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveY_North(Piece tmp) {
		int count = this.findCountY(tmp);
		int row = tmp.getRow() - count;
		int column = tmp.getColumn();
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = row + 1; i < tmp.getRow(); i++) {
					if (!yourlist.contains(this.board.getPiece(i, column))) {

						return true;
					}
				}
			}
		}
		return false;

	}

	/**
	 * Check whether it's possible to move to south
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveY_South(Piece tmp) {
		int count = this.findCountY(tmp);
		int row = tmp.getRow() + count;
		int column = tmp.getColumn();
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = tmp.getRow() + 1; i < tmp.getRow() + count; i++) {
					if (!yourlist.contains(this.board.getPiece(i, column))) {

						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check whether it's possible to move to west
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveX_left(Piece tmp) {
		int count = this.findCountX(tmp);
		int row = tmp.getRow();
		int column = tmp.getColumn() - count;
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = tmp.getColumn() - count + 1; i < tmp.getColumn(); i++) {
					if (!yourlist.contains(this.board.getPiece(row, i))) {

						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check whether it's possible to move to east
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveX_right(Piece tmp) {
		int count = this.findCountX(tmp);
		int row = tmp.getRow();
		int column = tmp.getColumn() + count;
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = tmp.getColumn() + 1; i < tmp.getColumn() + count; i++) {
					if (!yourlist.contains(this.board.getPiece(row, i))) {

						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * When the piece on inverse diagonal, check whether it 's possible to move
	 * it up
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveInver_up(Piece tmp) {
		int count = this.findCountInverse(tmp);
		
		int m = tmp.getColumn() + tmp.getRow();
		int row = tmp.getRow() - count;
		int column = tmp.getColumn() + count;
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = row + 1; i < tmp.getRow(); i++) {
					if ((!yourlist.contains(this.board.getPiece(i, m - i)) && this.board
							.isLegalPosition(i, m - i))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * When the piece on inverse diagonal, check whether it 's possible to move
	 * it down
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveInver_down(Piece tmp) {
		int count = this.findCountInverse(tmp);
		int m = tmp.getColumn() + tmp.getRow();
		int row = tmp.getRow() + count;
		int column = tmp.getColumn() - count;
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = tmp.getRow() + 1; i < row; i++) {
					if ((!yourlist.contains(this.board.getPiece(i, m - i)) && this.board
							.isLegalPosition(i, m - i))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * When the piece on main diagonal, check whether it 's possible to move it
	 * up
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveMain_up(Piece tmp) {
		int count = this.findCountDiagonal(tmp);
		int n = -tmp.getColumn() + tmp.getRow();
		int row = tmp.getRow() - count;
		int column = tmp.getColumn() - count;
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = row + 1; i < tmp.getRow(); i++) {
					if ((!yourlist.contains(this.board.getPiece(i, i - n)) && this.board
							.isLegalPosition(i, i - n))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * When the piece on main diagonal, check whether it 's possible to move it
	 * down
	 * 
	 * @param tmp
	 * @return
	 */
	@Override
	public boolean legalMoveMain_down(Piece tmp) {
		int count = this.findCountDiagonal(tmp);
		int n = -tmp.getColumn() + tmp.getRow();
		int row = tmp.getRow() + count;
		int column = tmp.getColumn() + count;
		if (this.board.isLegalPosition(row, column)) {
			if (!mylist.contains(this.board.getPiece(row, column))) {
				for (int i = tmp.getRow() + 1; i < row; i++) {
					if ((!yourlist.contains(this.board.getPiece(i, i - n)) && this.board
							.isLegalPosition(i, i - n))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Figure out all the possible position of one piece in a list
	 * 
	 * @param tmp
	 */
	@Override
	public void findPossiblePoint(Piece tmp,ArrayList<Point>availablePoint) {
		int count;
		availablePoint.clear();

		if (legalMoveY_North(tmp)) {
			count = findCountY(tmp);
			availablePoint
					.add(new Point(tmp.getRow() - count, tmp.getColumn()));
		}
		if (legalMoveY_South(tmp)) {
			count = findCountY(tmp);
			availablePoint
					.add(new Point(tmp.getRow() + count, tmp.getColumn()));
		}
		if (legalMoveX_left(tmp)) {
			count = findCountX(tmp);
			availablePoint
					.add(new Point(tmp.getRow(), tmp.getColumn() - count));
		}
		if (legalMoveX_right(tmp)) {
			count = findCountX(tmp);
			availablePoint
					.add(new Point(tmp.getRow(), tmp.getColumn() + count));
		}
		if (legalMoveInver_up(tmp)) {
			count = findCountInverse(tmp);
			availablePoint.add(new Point(tmp.getRow() - count, tmp.getColumn()
					+ count));
		}
		if (legalMoveInver_down(tmp)) {
			count = findCountInverse(tmp);
			availablePoint.add(new Point(tmp.getRow() + count, tmp.getColumn()
					- count));
		}
		if (legalMoveMain_up(tmp)) {
			count = findCountDiagonal(tmp);
			availablePoint.add(new Point(tmp.getRow() - count, tmp.getColumn()
					- count));
		}
		if (legalMoveMain_down(tmp)) {
			count = findCountDiagonal(tmp);
			availablePoint.add(new Point(tmp.getRow() + count, tmp.getColumn()
					+ count));
		}
		else{
			availablePoint= null;
		}
	}

	/**
	 * Randomly move the piece to its available point, if the destination position contains other player's piece, remove it
	 * 
	 * @param tmp
	 */
	@Override
	public void makeMove(Piece tmp) {
		try {
			findPossiblePoint(tmp,availablePoint);
			if (availablePoint.size() !=0){
			Random rand = new Random();
			Random rand2 = new Random();
			int random = rand.nextInt(availablePoint.size());

			Point tmp_point = new Point();
			tmp_point = availablePoint.get(random);

			int newRow = (int) tmp_point.x;
			int newColumn = (int) tmp_point.y;
			if (yourlist.contains(this.board.getPiece(newRow, newColumn))&& (availablePoint.size()>1)){
				int random2 = rand2.nextInt(availablePoint.size());
				while (random == random2){
					 random2 = rand2.nextInt(availablePoint.size());
				}
				Point tmp_point2 = availablePoint.get(random2);
				int newRow2 = tmp_point2.x;
				int newColumn2 = tmp_point2.y;
				tmp.moveTo(newRow2, newColumn2);
			}
			else{
				yourlist.remove(this.board.getPiece(newRow, newColumn));
				this.board.remove(this.board.getPiece(newRow, newColumn));
				tmp.moveTo(newRow, newColumn);
			}
			
		}} catch (ArrayIndexOutOfBoundsException e3) {

		}
	}
	/**Find the same color neighbor of one piece in a certain area
	 * @param piece
	 * @return a set of neighbors
	 */
	public Set findNeighbor(Piece piece){
		Set neighbor = new HashSet();
		Piece neigh;
		for (int i = piece.getRow() - 1; i < piece.getRow() + 2; i++) {
			for (int j = piece.getColumn() - 1; j < piece.getColumn() + 2; j++) {
				
					if (this.board.isLegalPosition(i, j)) {

						if (!this.board.isEmpty(i, j)) {
							neigh = board.getPiece(i, j);
							if (mylist.contains(neigh)) {
								neighbor.add(neigh);
							}
						}
					}
			}
		}return neighbor;
	}
	
	/**Figure out the connect area of one piece
	 * @param list-the list that contains one player's all the pieces
	 * @return
	 */
	public boolean checkConnect(ArrayList<Piece>list){
		Set<Piece> pieceConnected = new HashSet<Piece>();
		pieceConnected.addAll(findNeighbor(list.get(0)));
		int size =0;
		int size2 =1;
		while(size != size2){
			java.util.Iterator iter = pieceConnected.iterator();
			Set<Piece> temPieceConnect = new HashSet<Piece>();
			while(iter.hasNext()){
				Piece piece = (Piece) iter.next();
				if (findNeighbor(piece).size()>0){
					temPieceConnect.addAll(findNeighbor(piece));
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
