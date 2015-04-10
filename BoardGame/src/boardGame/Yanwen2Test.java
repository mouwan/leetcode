package boardGame;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class Yanwen2Test {

	private int ROWS = 8;
	private int COLUMNS = 8;
	Piece my_tmp;
	Piece tmp_1, tmp_2, tmp_3;
	Board board, board_test;
	Random random = new Random();
	ArrayList<Piece> mylist = new ArrayList<Piece>();
	ArrayList<Piece> yourlist = new ArrayList<Piece>();
	ArrayList<Piece> redlist = new ArrayList<Piece>();
	ArrayList<Piece> bluelist = new ArrayList<Piece>();
	ArrayList<Point> availablePoint = new ArrayList<Point>();

	@Before
	public void setUp() throws Exception {
		board = new Board(ROWS, COLUMNS);

		for (int i = 1; i < 7; i++) {
			Piece redPiece = new RoundPiece();
			redPiece.place(board, 0, i);
			redlist.add(redPiece);
		}
		for (int j = 1; j < 7; j++) {
			Piece bluePiece = new RoundPiece(Color.BLUE);

			bluePiece.place(board, j, 0);
			bluelist.add(bluePiece);
		}
		for (int i = 1; i < 7; i++) {
			Piece redPiece = new RoundPiece();
			redPiece.place(board, 7, i);
			redlist.add(redPiece);
		}
		for (int j = 1; j < 7; j++) {
			Piece bluePiece = new RoundPiece(Color.BLUE);

			bluePiece.place(board, j, 7);
			bluelist.add(bluePiece);
		}

	}

	/**
	 * Helper function to test win(), set up a win board
	 * 
	 */
	public void setWinBoard() {

		board_test = new Board(ROWS, COLUMNS);
		mylist.clear();
		my_tmp = new RoundPiece(Color.BLUE);
		board_test.place(my_tmp, 1, 1);
		mylist.add(my_tmp);
		for (int i = 2; i < 6; i++) {
			Piece place = new RoundPiece(Color.BLUE);
			board_test.place(place, i, i);
			mylist.add(place);
		}
	}

	/**
	 * Set up an non-win board
	 * 
	 */
	public void setNonWin() {
		board_test = new Board(ROWS, COLUMNS);
		mylist.clear();
		my_tmp = new RoundPiece(Color.BLUE);
		board_test.place(my_tmp, 1, 3);
		mylist.add(my_tmp);
		for (int i = 0; i < 8; i++, i++) {
			Piece place = new RoundPiece(Color.BLUE);
			board_test.place(place, i, i);
			mylist.add(place);
		}
	}

	@Test
	public void testIsWin() {
		this.setWinBoard();

		Yanwen2 me = new Yanwen2(board_test, mylist, new ArrayList<Piece>());
		assertTrue(me.win(mylist));
		this.setNonWin();
		Yanwen2 mee = new Yanwen2(board_test, mylist, new ArrayList<Piece>());
		assertFalse(mee.win(mylist));
		//test a non win situation
		this.setNonWin();
		Yanwen2 momo = new Yanwen2(board_test,mylist,new ArrayList<Piece>());
		assertFalse(momo.win(mylist));

	}

	/**
	 * Test the available move count in Y direction
	 * 
	 */
	@Test
	public void testFindCountY() {
		tmp_1 = board.getPiece(0, 3);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(2, tem.findCountY(tmp_1));
		tmp_2 = board.getPiece(3, 7);
		assertEquals(6, tem.findCountY(tmp_2));
		assertNotEquals(3,tem.findCountY(tmp_2));

	}

	/**
	 * Test the available move count in x direction
	 * 
	 */
	@Test
	public void testFindCountX() {
		tmp_1 = board.getPiece(0, 3);
		Yanwen tem = new Yanwen(board, bluelist, new ArrayList<Piece>());
		assertEquals(6, tem.findCountX(tmp_1));
		tmp_2 = board.getPiece(3, 7);
		assertEquals(2, tem.findCountX(tmp_2));

	}

	/**
	 * Test the available move count in inverse diagonal
	 * 
	 */
	@Test
	public void testFindCountInverse() {
		tmp_1 = board.getPiece(7, 3);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(2, tem.findCountInverse(tmp_1));
		assertNotEquals(3,tem.findCountInverse(tmp_1));
		tmp_2 = board.getPiece(4, 7);
		assertEquals(2, tem.findCountInverse(tmp_2));

	}

	/**
	 * Test the available move count in main diagonal
	 * 
	 */
	@Test
	public void testFindDiagonal() {
		tmp_1 = board.getPiece(0, 5);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(2, tem.findCountInverse(tmp_1));
		tmp_2 = board.getPiece(4, 7);
		assertEquals(2, tem.findCountInverse(tmp_2));

	}

	/**
	 * Test whether it's possible to move to North
	 * 
	 */
	@Test
	public void testLegalMoveY_North() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(7, 2);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(false, tem.legalMoveY_North(tmp_1));
		assertEquals(true, tem.legalMoveY_North(tmp_3));

	}

	/**
	 * Test whether it's possible to move to south
	 * 
	 */
	@Test
	public void testLegalMoveY_South() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(7, 2);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(true, tem.legalMoveY_South(tmp_1));
		assertEquals(false, tem.legalMoveY_South(tmp_3));
	}

	/**
	 * Test whether it's possible to move to the left in x direction
	 * 
	 */
	@Test
	public void testLegalMoveX_left() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(3, 0);
		tmp_2 = board.getPiece(3, 7);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(false, tem.legalMoveX_left(tmp_1));
		assertEquals(false, tem.legalMoveX_left(tmp_3));
		assertFalse(tem.legalMoveX_left(tmp_3));
		//assertEquals(true, tem.legalMoveX_left(tmp_2));
		assertTrue(tem.legalMoveX_left(tmp_2));
	}

	/**
	 * Test whether it's possible to move to the right in x direction
	 * 
	 */
	@Test
	public void testLegalMoveX_right() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(3, 7);
		tmp_2 = board.getPiece(0, 1);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(false, tem.legalMoveX_right(tmp_1));
		assertEquals(false, tem.legalMoveX_right(tmp_3));
		assertEquals(true, tem.legalMoveX_right(tmp_2));
	}

	/**
	 * Test whether it's possible to move up along the inverse diagonal
	 * 
	 */
	@Test
	public void testLegalMoveInver_up() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(3, 7);
		tmp_2 = board.getPiece(7, 1);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertFalse(tem.legalMoveInver_up(tmp_1));
		assertTrue(tem.legalMoveInver_up(tmp_2));
		assertEquals(false, tem.legalMoveInver_up(tmp_1));
		assertEquals(false, tem.legalMoveInver_up(tmp_3));
		assertEquals(true, tem.legalMoveInver_up(tmp_2));

	}

	/**
	 * Test whether it's possible to move down along the inverse diagonal
	 * 
	 */
	@Test
	public void testLegalMoveInver_down() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(3, 7);
		tmp_2 = board.getPiece(7, 1);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(true, tem.legalMoveInver_down(tmp_1));
		assertEquals(true, tem.legalMoveInver_down(tmp_3));
		assertEquals(false, tem.legalMoveInver_down(tmp_2));

	}

	/**
	 * Test whether it's possible to move up along the main diagonal
	 * 
	 */
	@Test
	public void testLegalMoveMain_up() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(3, 7);
		tmp_2 = board.getPiece(7, 1);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertEquals(false, tem.legalMoveMain_up(tmp_1));
		assertEquals(false, tem.legalMoveMain_up(tmp_2));
		assertEquals(true, tem.legalMoveMain_up(tmp_3));
	}

	/**
	 * Test whether it's possible to move down along the main diagonal
	 * 
	 */
	@Test
	public void testLegalMoveMain_down() {
		tmp_1 = board.getPiece(0, 4);
		tmp_3 = board.getPiece(0, 2);
		tmp_2 = board.getPiece(7, 2);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		assertTrue(tem.legalMoveMain_down(tmp_1));
		assertFalse(tem.legalMoveMain_down(tmp_2));
		
		assertEquals(false, tem.legalMoveMain_down(tmp_2));
		assertEquals(true, tem.legalMoveMain_down(tmp_3));
	}

	/**Test the available position of a piece
	 * 
	 */
	@Test
	public void testFindPossiblePoint() {
		tmp_1 = board.getPiece(0, 3);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		tem.findPossiblePoint(tmp_1,availablePoint);
		assertEquals(3,availablePoint.size());
		Point point1 = new Point(2,3);
		assertEquals(point1, availablePoint.get(0));
		Point point2 = new Point(2,1);
		assertEquals(point2, availablePoint.get(1));
		Point point3 = new Point(2,5);
		assertEquals(point3, availablePoint.get(2));				
	}
	/**Test the neighbor of a piece in a specific area
	 * 
	 */
	@Test
	public void testFindNeighbor(){
		tmp_1 = board.getPiece(3, 0);
		Yanwen2 tem = new Yanwen2(board, bluelist, new ArrayList<Piece>());
		Set neighbor = new HashSet();
		Set compare = new HashSet();
		neighbor=tem.findNeighbor(tmp_1);
		Piece p1 = board.getPiece(2, 0);
		compare.add(p1);
		Piece p2 = board.getPiece(4, 0);
		compare.add(p2);
		compare.add(tmp_1);
		assertEquals(compare,neighbor);
		
		
	}
}
