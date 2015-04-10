package boardGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * A test class for the board game API.
 */
public class LinesOfAction extends JFrame implements Runnable {
	Board board;
	JPanel buttonPanel;
	JButton quitButton;
	JButton stepButton;
	JButton playButton;
	JButton clearButton;
	JSlider speedControl;

	JMenu select = new JMenu("Selction");
	JMenuBar bar = new JMenuBar();
	int speed;
	LinesOfAction self;
	static Piece testBlock, testBlock2;
	Random random = new Random();
	final int ROWS = 8;
	final int COLUMNS = 8;
	boolean blueFirst = false, redFirst = false, stepbystep = false,
			reset = false;
	int j = 0;

	Yanwen yanwen;
	Yanwen2 yanwen2;
	Player check;
	ArrayList<Piece> redlist = new ArrayList<Piece>();
	ArrayList<Piece> bluelist = new ArrayList<Piece>();
	ArrayList<Piece> temlist = new ArrayList<Piece>();

	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 15;

	public LinesOfAction() {
		board = new Board(ROWS, COLUMNS);

		yanwen = new Yanwen(board, redlist, bluelist);
		yanwen2 = new Yanwen2(board, bluelist, redlist);
		
	}

	public static void main(String[] args) {
		LinesOfAction test = new LinesOfAction();
		try {

			test.doTheTrick();
			Thread.sleep(500);
			JOptionPane
					.showMessageDialog(
							null,
							"Please choose which one goes first,otherwise the default will let blue goes first.\n",
							"Information", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			test.board.dump();
		}
	}

	void doTheTrick() {
		// Provide access to this object from anonymous listeners
		setTitle("Lines of Action");
		setJMenuBar(bar);
		self = this;

		// Create a Board (a kind of JPanel) and add it to this Frame.
		// board = new Board(ROWS, COLUMNS);
		JPanel display = board.getJPanel();
		getContentPane().add(display, BorderLayout.CENTER);

		// Install button panel
		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		createSpeed();
		setmenubar();
		installPlayButton();
		installStepButton();
		installQuitButton();
		implementCloseBox();

		// Magic incantation to make this board visible; must be
		// done before any pieces are placed on the board

		pack();
		setSize(820, 920);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		setVisible(true);

		doThingsWithPieces();

	}

	void setmenubar() {

		bar.add(select);
		JMenuItem i = new JMenuItem("Blue Goes First");
		select.add(i);
		i.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueFirst = true;
			}
		});
		JMenuItem i2 = new JMenuItem("Red Goes First");
		select.add(i2);
		i2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redFirst = true;
			}
		});
	}

	/**
	 * Installs a button to do a handful of things to test the program.
	 */
	private void installPlayButton() {
		// Install Test button
		playButton = new JButton("AutoPlay");
		buttonPanel.add(playButton);
		if (playButton.getComponentListeners().length > 0) {
			playButton.setEnabled(false);
		} else {
			playButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Thread t = new Thread(self);
					t.start();
				}
			});
		}
	}

	/**
	 * Install "step by step button"
	 * 
	 */
	public void installStepButton() {

		stepButton = new JButton("Step by Step");
		buttonPanel.add(stepButton);
		if (stepButton.getComponentListeners().length > 0) {
			stepButton.setEnabled(false);
		} else {
			stepButton.addActionListener(new ActionListener() {
				;
				public void actionPerformed(ActionEvent e) {

					Thread t = new Thread(self);
					t.start();
					stepbystep = true;
					j++;

				}
			});
		}

	}

	/**
	 * Installs a button to quit the program.
	 */
	private void installQuitButton() {
		// Install Quit button
		
		quitButton = new JButton("Quit");
		buttonPanel.add(quitButton);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Quits the program when the window is closed.
	 */
	private void implementCloseBox() {
		// Implement window close box
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	/**
	 * Place both player's pieces on the board
	 * 
	 */
	private void doThingsWithPieces() {

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
     * 
     */
	protected void dumpEverything() {
		board.dump();

	}

	/**
	 * Set speed controller for the game
	 * 
	 */
	public void createSpeed() {

		final JSlider controller = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);

		controller.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = controller.getValue();
				board.setSpeed(value);
				revalidate();
				repaint();
			}
		});
		controller.setMajorTickSpacing(10);
		controller.setMinorTickSpacing(1);
		controller.setPaintTicks(true);
		controller.setPaintLabels(true);
		buttonPanel.add(controller);
	}

	public void changeSpeed(int speed) {
		board.setSpeed(speed);
	}

	public Board getBoard() {
		return board;
	}

	/**Move a red piece
	 * @return
	 */
	public boolean redmove() {
		boolean isWin = false;
		Random rand = new Random();
		int random = rand.nextInt(redlist.size());
		testBlock = redlist.get(random);
		yanwen.makeMove(testBlock);
		if (yanwen.win(redlist)) {
			System.out.println("Red win");
			JOptionPane.showMessageDialog(null, "Congratuation! You win!.",
					"Red win", JOptionPane.INFORMATION_MESSAGE);
			isWin = true;
			resetAll();
		}
		return isWin;
	}

	/**Reset the board after it wins
	 * 
	 */
	public void resetAll() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				board.remove(i, j);
			}
		}
		doThingsWithPieces();
	}

	/**Move a blue piece
	 * @return
	 */
	public boolean bluemove() {
		boolean isWin = false;
		Random rand = new Random();
		int random = rand.nextInt(bluelist.size());
		testBlock2 = bluelist.get(random);
		yanwen2.makeMove(testBlock2);
		if (yanwen2.win(bluelist)) {
			System.out.println("Blue Win");
			JOptionPane.showMessageDialog(null, "Congratuation! You win!.",
					"Blue win", JOptionPane.INFORMATION_MESSAGE);
			isWin = true;
			resetAll();
		}
		return isWin;
	}

	public void makeSingleMove() {
		boolean isWin;
		if (redFirst) {
			if (j % 2 == 1) {
				isWin = redmove();
				if (isWin)
					return;
			} else {
				isWin = bluemove();
				if (isWin)
					return;
			}
		} else {
			if (j % 2 == 1) {
				isWin = bluemove();
				if (isWin)
					return;
			} else {

				isWin = redmove();
				if (isWin)
					return;

			}
		}

	}

	public void run() {

		if (stepbystep) {
			makeSingleMove();
		} else {
			boolean isWin;
			if (redFirst) {
				int whoWon = 0;
				while ((whoWon != 1)) {
					isWin = redmove();
					if (isWin) {

						break;
					} else {
						isWin = bluemove();
						if (isWin) {
							break;
						}

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
					}
				}
			} else {
				int whoWon = 0;
				while ((whoWon != 1)) {
					isWin = bluemove();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					if (isWin) {
						break;
					} else {
						isWin = redmove();
						if (isWin) {
							break;
						}
					}
				}
			}
		}
	}
}