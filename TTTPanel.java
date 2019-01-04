import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * File: TTTPanel.java Author: Heather Amthauer Description: This class makes
 * the tic-tac-toe panel The user is allowed to select a one-player version or a
 * two-player version. One-player version is not implemented Two-player version
 * is turn based. First X marks a square then O moves.
 */

public class TTTPanel extends JPanel {

	private String turn; // keep track if it is X or O's turn
	private boolean onePlay; // keep track of one player or two player

	private JLabel onePlayersName;
	private JLabel twoPlayerX;
	private JLabel twoPlayerO;
	private JTextField nameOnePlayer;
	private JTextField nameX;
	private JTextField nameO;
	private JButton player1Name;
	private JButton player2Name;

	private JPanel topPanel; // panel to hold radio buttons and info
	private JRadioButton onePlayer; // for user to select if they want to play
									// agains tthe computer
	private JRadioButton twoPlayer; // for user to select if they want to play
									// against another human
	private ButtonGroup group; // for mutual exclsion of player selection
	private JLabel info; // to communicate instructions to user

	private JPanel panel; // panel for buttons for squares
	private JButton upperR; // upper right corner
	private JButton upperM; // upper middle
	private JButton upperL; // upper left corner
	private JButton middleR; // middle right
	private JButton middleM; // middle of board
	private JButton middleL; // middle left
	private JButton lowerR; // lower right corner
	private JButton lowerM; // lower middle
	private JButton lowerL; // lower left corner

	private JButton reset; // button to reset board

	private Font font1 = new Font("Courier", Font.BOLD, 50);// so X and O are
															// larger

	/**
	 * Constructor that initalizes all the components for GUI
	 *
	 */
	public TTTPanel() {
		onePlayersName = new JLabel(" Your Name: ", JLabel.RIGHT); // making the Your Name text to appear
		twoPlayerX = new JLabel(" Player X's Name: ", JLabel.RIGHT); // making Player X's name text to appear
		twoPlayerO = new JLabel(" Player O's Name: ", JLabel.RIGHT); // making Player O's name text to appear

		onePlayersName.setBackground(Color.BLUE); // making the Your Name text color to be blue
		twoPlayerX.setBackground(Color.BLUE); // making the Player X's name text color to be blue
		twoPlayerO.setBackground(Color.GREEN); // making the Player O's name text color to be green
		
		nameOnePlayer = new JTextField(20); // making the text field size to be 20
		nameX = new JTextField(10); // making the text field size to be 10
		nameO = new JTextField(10); // making the text field size to be 10

		player1Name = new JButton("Submit Name"); // making a Submit Name button for the one player option
		player2Name = new JButton("Submit Name"); // making a Submit Name button for the two player option

		/*
		 * This shows the message when the name was not written in the one player option name field. 
		 * After the name is written, player1Name.setEnabled(false); will make the Submit Name button to be
		 * disabled before starting the game.
		 * */
		player1Name.addActionListener((e) -> {
			if (nameOnePlayer.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "You need to enter your name!");
			} else {
				player1Name.setEnabled(false);
				nameOnePlayer.setEnabled(false);
				turn = "X";
				onePlay = true;
				twoPlayer.setEnabled(false);
				info.setText("It's " + nameOnePlayer.getText() + "'s turn");
				player1Name.setEnabled(false);
				startGame();
			}
		});

		 /*
		 * This shows the message when the name was not written in the two player option name field. 
		 * If one of names are not written on the field, it will show the error message saying that which player
		 * has not entered the name. After both player's names are written, and if the Submit Name button is clicked, the game will start
		 * player2Name.setEnabled(false); will make the Submit Name button to be disabled before starting the game.
		 * */
		player2Name.addActionListener((e) -> {
			if (nameX.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Player X needs a name!");
			} else if (nameO.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Player O needs a name!");
			} else if (nameX.getText().equals(nameO.getText())) {
				player1Name.setEnabled(false);
				nameX.setText(nameX.getText() + "1");
				nameO.setText(nameO.getText() + "2");
				nameX.setEnabled(false);
				nameO.setEnabled(false);

				turn = "X";

				onePlay = false;
				onePlayer.setEnabled(false);
				info.setText("It's " + nameX.getText() + "'s turn");
				player2Name.setEnabled(false);
				startGame();
			} else {
				player1Name.setEnabled(false);
				nameX.setEnabled(false);
				nameO.setEnabled(false);

				turn = "X";

				onePlay = false;
				onePlayer.setEnabled(false);
				info.setText("It's " + nameX.getText() + "'s turn");
				player2Name.setEnabled(false);
				startGame();
			}
		});
		
		// set the layout for the panel that will hold all the game components
		setLayout(new BorderLayout());
		// make radio buttons for player options
		onePlayer = new JRadioButton("One Player");
		twoPlayer = new JRadioButton("Two Player");

		// make the radio buttons mutual exclusive
		group = new ButtonGroup();
		group.add(onePlayer);
		group.add(twoPlayer);

		// Add item listeners to radio buttons so we can tell when they have
		// been selected
		ItemListener oneplay = new RadioOnePlayer();
		onePlayer.addItemListener(oneplay);
		ItemListener twoplay = new RadioTwoPlayer();
		twoPlayer.addItemListener(twoplay);

		// initialize top panel that will hold radio buttons
		topPanel = new JPanel();
		// set the layout so things will be added horizontally
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		// add radio buttons
		topPanel.add(onePlayer);
		topPanel.add(twoPlayer);
		// initialize info and make the text red
		info = new JLabel("Select how many players");
		info.setForeground(Color.red);
		// add the info
		topPanel.add(info);
		// add the top panel to the top of the TTT panel
		add(topPanel, BorderLayout.NORTH);

		// initialize the reset button
		reset = new JButton("Reset");
		// action listener for reset
		ActionListener resetBoard = new ResetAction();
		reset.addActionListener(resetBoard);
		// add reset button to bottom of the TTT panel
		add(reset, BorderLayout.SOUTH);

		// action listeners for all the squares
		ActionListener takeSquare = new TakeSquareAction();

		// add square buttons in 3x3 grid
		panel = new JPanel();
		// this sets the layout to 3x3 with a 5 pixel border
		// that goes around the grid cells
		panel.setLayout(new GridLayout(3, 3, 5, 5));

		// for all buttons, set them to blank text
		// add action listener then
		// add to panel
		upperL = new JButton(" ");
		upperL.setFont(font1);
		upperL.addActionListener(takeSquare);
		panel.add(upperL);

		upperM = new JButton(" ");
		upperM.setFont(font1);
		upperM.addActionListener(takeSquare);
		panel.add(upperM);

		upperR = new JButton(" ");
		upperR.setFont(font1);
		upperR.addActionListener(takeSquare);
		panel.add(upperR);

		middleL = new JButton(" ");
		middleL.setFont(font1);
		middleL.addActionListener(takeSquare);
		panel.add(middleL);

		middleM = new JButton(" ");
		middleM.setFont(font1);
		middleM.addActionListener(takeSquare);
		panel.add(middleM);

		middleR = new JButton(" ");
		middleR.setFont(font1);
		middleR.addActionListener(takeSquare);
		panel.add(middleR);

		lowerL = new JButton(" ");
		lowerL.setFont(font1);
		lowerL.addActionListener(takeSquare);
		panel.add(lowerL);

		lowerM = new JButton(" ");
		lowerM.setFont(font1);
		lowerM.addActionListener(takeSquare);
		panel.add(lowerM);

		lowerR = new JButton(" ");
		lowerR.setFont(font1);
		lowerR.addActionListener(takeSquare);
		panel.add(lowerR);

		// add the grid panel to the center of the TTTPanel
		add(panel, BorderLayout.CENTER);
		disableAllButtons();// make sure the user selects one or two player
							// first

	}

	/**
	 * This class will reset the text for all the buttons to a blank and enable
	 * them all again so they can be clicked
	 */
	private class ResetAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			// set text to blank for all buttons and color to white
			upperL.setText(" ");
			upperL.setBackground(Color.WHITE);
			upperM.setText(" ");
			upperM.setBackground(Color.WHITE);
			upperR.setText(" ");
			upperR.setBackground(Color.WHITE);

			middleL.setText(" ");
			middleL.setBackground(Color.WHITE);
			middleM.setText(" ");
			middleM.setBackground(Color.WHITE);
			middleR.setText(" ");
			middleR.setBackground(Color.WHITE);

			lowerL.setText(" ");
			lowerL.setBackground(Color.WHITE);
			lowerM.setText(" ");
			lowerM.setBackground(Color.WHITE);
			lowerR.setText(" ");
			lowerR.setBackground(Color.WHITE);

			// clear all of the player options
			group.clearSelection();
			// enable player options
			onePlayer.setEnabled(true);
			twoPlayer.setEnabled(true);
			// set info text
			info.setText("Select how many players");
			nameOnePlayer.setText("");
			nameOnePlayer.setEnabled(true);
			nameX.setText("");
			nameX.setEnabled(true);
			nameO.setText("");
			nameO.setEnabled(true);
			player1Name.setEnabled(true);
			player2Name.setEnabled(true);

			// make it so the user has to select player option first disable
			// buttons
			disableAllButtons();
			topPanel.revalidate();
			topPanel.repaint();

			topPanel.remove(nameOnePlayer);
			topPanel.remove(nameX);
			topPanel.remove(nameO);
			topPanel.remove(onePlayersName);
			topPanel.remove(twoPlayerX);
			topPanel.remove(twoPlayerO);
			topPanel.remove(player1Name);
			topPanel.remove(player2Name);
		}

	}

	/**
	 * RadioOnePlayer class sets the game up for one player
	 */
	private class RadioOnePlayer implements ItemListener {
		public void itemStateChanged(ItemEvent e) {

			twoPlayer.setEnabled(false);
			topPanel.add(onePlayersName);
			topPanel.add(nameOnePlayer);
			info.setText("Enter your Name");
			topPanel.add(player1Name);
		}
	}

	/**
	 * RadioTwoPlayer class sets the game up for two players It will be turn
	 * based. X is first.
	 */
	private class RadioTwoPlayer implements ItemListener {
		public void itemStateChanged(ItemEvent e) {

			onePlayer.setEnabled(false);
			topPanel.add(twoPlayerX);
			topPanel.add(nameX);
			topPanel.add(twoPlayerO);
			topPanel.add(nameO);
			info.setText("Enter Your Name");
			topPanel.add(player2Name);
		}
	}

	/**
	 * TakeSquareAction class When square buttons are clicked - it will mark the
	 * square appropriately. Appropriate moves are based on if it is two player
	 * or one player.
	 */
	private class TakeSquareAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// two player option
			if (!onePlay) {
				// get the button that was pressed
				JButton b = (JButton) event.getSource();
				// X's turn
				if (turn.equals("X")) {
					// set the button text to X
					b.setText("X");
					// disable the button
					b.setEnabled(false);
					b.setBackground(Color.BLUE);
					// check if it won the game
					if (checkForWin()) {
						info.setText("X is the winner!!!");
						disableAllButtons();
					} else {
						// else check for a draw
						if (checkForDraw()) {
							info.setText("Cat's game!");
						} else {
							turn = "O";
							info.setText("It's O's turn");
						}
					}
					// if game is not over - it is O's turn
				}
				// O's turn
				else {
					// set the button text to O
					b.setText("O");
					b.setBackground(Color.GREEN);
					// disable the button
					b.setEnabled(false);
					if (checkForWin()) {
						info.setText("O is the winner!!!");
						disableAllButtons();
					} else {
						// else check for a draw
						if (checkForDraw()) {
							info.setText("Cat's game!");
						} else {
							turn = "X";
							info.setText("It's X's turn");
						}
					}
					// if game is not over - it is O's turn

					// check if it won the game
					// else check for a draw
					// if game is not over - it is X's turn
				}
			}
			// one player option
			else {
				/* You need to create code for your counter move */
				// get the button that was pressed
				JButton b = (JButton) event.getSource();
				// X's turn
				if (turn.equals("X")) {
					// set the button text to X
					b.setText("X");
					// disable the button
					b.setEnabled(false);
					b.setBackground(Color.BLUE);
					// check if it won the game
					if (checkForWin()) {
						info.setText("X is the winner!!!");
						disableAllButtons();
					} else {
						// else check for a draw
						if (checkForDraw()) {
							info.setText("Cat's game!");
						} else {
							turn = "O";
							info.setText("It's O's turn");
							aiStrategy(); // after X's turn is over, the computer will play with O by 
										  //following the aiStrategy method that I wrote. 
							
						}
					}
					// if game is not over - it is O's turn

				}
				// O's turn
				else {
					b.setText("O");
					b.setBackground(Color.GREEN);
					b.setEnabled(false);
					if (checkForWin()) {
						info.setText("O is the winner!!!");
						disableAllButtons();
					} else {
						// else check for a draw
						if (checkForDraw()) {
							info.setText("Cat's game!");
						} else {
							turn = "X";
							info.setText("It's X's turn");
						}
					}
					// if game is not over - it is O's turn

					// check if it won the game
					// else check for a draw
					// if game is not over - it is X's turn
				}
			}

		}
	}

	/**
	 * checkForWin This goes through all the options for a three in a row win
	 * 
	 * @return true if there are three X's or three O's in a row, otherwise
	 *         false
	 */
	public boolean checkForWin() {
		if (((upperR.getText().equals(upperM.getText())) && (upperR.getText().equals(upperL.getText()))
				&& (!upperR.getText().equals(" ")))
				|| ((middleR.getText().equals(middleM.getText())) && (middleR.getText().equals(middleL.getText()))
						&& (!middleR.getText().equals(" ")))
				|| ((lowerR.getText().equals(lowerM.getText())) && (lowerR.getText().equals(lowerL.getText()))
						&& (!lowerR.getText().equals(" ")))
				|| ((upperR.getText().equals(middleR.getText())) && (upperR.getText().equals(lowerR.getText()))
						&& (!upperR.getText().equals(" ")))
				|| ((upperM.getText().equals(middleM.getText())) && (upperM.getText().equals(lowerM.getText()))
						&& (!upperM.getText().equals(" ")))
				|| ((upperL.getText().equals(middleL.getText())) && (upperL.getText().equals(lowerL.getText()))
						&& (!upperL.getText().equals(" ")))
				|| ((upperR.getText().equals(middleM.getText())) && (upperR.getText().equals(lowerL.getText()))
						&& (!upperR.getText().equals(" ")))
				|| ((upperL.getText().equals(middleM.getText())) && (upperL.getText().equals(lowerR.getText()))
						&& (!upperL.getText().equals(" ")))) {
			return true;
		} else
			return false;
	}

	/**
	 * checkForDraw This goes through and sees if all the squares are not blank.
	 * If all the squares are marked then it is a draw
	 * 
	 * @return true if none of the squares are blank.
	 */
	public boolean checkForDraw() {
		if (!upperR.getText().equals(" ") && !upperM.getText().equals(" ") && !upperL.getText().equals(" ")
				&& !middleR.getText().equals(" ") && !middleM.getText().equals(" ") && !middleL.getText().equals(" ")
				&& !lowerR.getText().equals(" ") && !lowerM.getText().equals(" ") && !lowerL.getText().equals(" ")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * disableAllButtons Sets all of the square buttons to false so the user
	 * cannot click them
	 */
	public void disableAllButtons() {
		upperR.setEnabled(false);
		upperM.setEnabled(false);
		upperL.setEnabled(false);
		middleR.setEnabled(false);
		middleM.setEnabled(false);
		middleL.setEnabled(false);
		lowerR.setEnabled(false);
		lowerM.setEnabled(false);
		lowerL.setEnabled(false);
	}

	/**
	 * startGame Enables all of the square buttons
	 */
	public void startGame() {
		upperR.setEnabled(true);
		upperM.setEnabled(true);
		upperL.setEnabled(true);
		middleR.setEnabled(true);
		middleM.setEnabled(true);
		middleL.setEnabled(true);
		lowerR.setEnabled(true);
		lowerM.setEnabled(true);
		lowerL.setEnabled(true);
	}

	/**
	 * aiStrategy Strategies that makes not to lose (win or tie)
	 */
	public void aiStrategy() {
		// Below codes are the strategies for the win move. When there are 2Os
		// in a row, O need to play for the other blank in order to win
		/*
		 * strategies for the upper three blanks. When there are two O in a row,
		 * and the other one is empty and O's turn, this strategy let you put
		 * the O on the empty square.
		 */
		if (upperL.getText().equals("O") && upperM.getText().equals("O") && !upperR.getText().equals("X")
				&& !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (upperR.getText().equals("O") && upperM.getText().equals("O") && !upperL.getText().equals("X")
				&& !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (upperL.getText().equals("O") && upperR.getText().equals("O") && !upperM.getText().equals("X")
				&& !upperM.getText().equals("O")) {
			upperM.doClick();
		}
		/*
		 * strategies for the middle three blanks. When there are two O in a
		 * row, and the other one is empty and O's turn, this strategy let you
		 * put the O on the empty square.
		 */
		else if (middleL.getText().equals("O") && middleM.getText().equals("O") && !middleR.getText().equals("X")
				&& !middleR.getText().equals("O")) {
			middleR.doClick();
		} else if (middleR.getText().equals("O") && middleM.getText().equals("O") && !middleL.getText().equals("X")
				&& !middleL.getText().equals("O")) {
			middleL.doClick();
		} else if (middleL.getText().equals("O") && middleR.getText().equals("O") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the lower three blanks. When there are two O in a row,
		 * and the other one is empty and O's turn, this strategy let you put
		 * the O on the empty square.
		 */
		else if (lowerL.getText().equals("O") && lowerM.getText().equals("O") && !lowerR.getText().equals("X")
				&& !lowerR.getText().equals("O")) {
			lowerR.doClick();
		} else if (lowerR.getText().equals("O") && lowerM.getText().equals("O") && !lowerL.getText().equals("X")
				&& !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerL.getText().equals("O") && lowerR.getText().equals("O") && !lowerM.getText().equals("X")
				&& !lowerM.getText().equals("O")) {
			lowerM.doClick();
		}
		/*
		 * strategies for the left three blanks. When there are two O in a row,
		 * and the other one is empty and O's turn, this strategy let you put
		 * the O on the empty square.
		 */
		else if (upperL.getText().equals("O") && middleL.getText().equals("O") && !lowerL.getText().equals("X")
				&& !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerL.getText().equals("O") && middleL.getText().equals("O") && !upperL.getText().equals("X")
				&& !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (lowerL.getText().equals("O") && upperL.getText().equals("O") && !middleL.getText().equals("X")
				&& !middleL.getText().equals("O")) {
			middleL.doClick();
		}
		/*
		 * strategies for the middle three blanks (height). When there are two O
		 * in a row, and the other one is empty and O's turn, this strategy let
		 * you put the O on the empty square.
		 */
		else if (upperM.getText().equals("O") && middleM.getText().equals("O") && !lowerM.getText().equals("X")
				&& !lowerM.getText().equals("O")) {
			lowerM.doClick();
		} else if (lowerM.getText().equals("O") && middleM.getText().equals("O") && !upperM.getText().equals("X")
				&& !upperM.getText().equals("O")) {
			upperM.doClick();
		} else if (lowerM.getText().equals("O") && upperM.getText().equals("O") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the right three blanks. When there are two O in a row,
		 * and the other one is empty and O's turn, this strategy let you put
		 * the O on the empty square.
		 */
		else if (upperR.getText().equals("O") && middleR.getText().equals("O") && !lowerR.getText().equals("X")
				&& !lowerR.getText().equals("O")) {
			lowerR.doClick();
		} else if (lowerR.getText().equals("O") && middleR.getText().equals("O") && !upperR.getText().equals("X")
				&& !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (lowerR.getText().equals("O") && upperR.getText().equals("O") && !middleR.getText().equals("X")
				&& !middleR.getText().equals("O")) {
			middleR.doClick();
		}
		/*
		 * strategies for the diagonal three blanks, starting from the upperL
		 * and ends with lowerR or from lowerR to upperL. When there are two O
		 * in a row, and the other one is empty and O's turn, this strategy let
		 * you put the O on the empty square.
		 */
		else if (upperL.getText().equals("O") && middleM.getText().equals("O") && !lowerR.getText().equals("X")
				&& !lowerR.getText().equals("O")) {
			lowerR.doClick();
		} else if (lowerR.getText().equals("O") && middleM.getText().equals("O") && !upperL.getText().equals("X")
				&& !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (upperL.getText().equals("O") && lowerR.getText().equals("O") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the diagonal three blanks, starting from the upperR
		 * and ends with lowerL or from lowerL to upperR. When there are two O
		 * in a row, and the other one is empty and O's turn, this strategy let
		 * you put the O on the empty square.
		 */
		else if (upperR.getText().equals("O") && middleM.getText().equals("O") && !lowerL.getText().equals("X")
				&& !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerL.getText().equals("O") && middleM.getText().equals("O") && !upperR.getText().equals("X")
				&& !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (upperR.getText().equals("O") && lowerL.getText().equals("O") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		// below codes are the strategies for the block move. When there are 2Xs
		// in a row, O need to block the other blank in order to not to lose
		/*
		 * strategies for the upper three blanks. When there are two Xs in a
		 * row, and the other one is empty and O's turn, this strategy let you
		 * put the O on the empty square and let the O not to lose.
		 */
		else if (upperL.getText().equals("X") && upperM.getText().equals("X") && !upperR.getText().equals("X")
				&& !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (upperR.getText().equals("X") && upperM.getText().equals("X") && !upperL.getText().equals("X")
				&& !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (upperL.getText().equals("X") && upperR.getText().equals("X") && !upperM.getText().equals("X")
				&& !upperM.getText().equals("O")) {
			upperM.doClick();
		}
		/*
		 * strategies for the middle(width) three blanks. When there are two Xs
		 * in a row, and the other one is empty and O's turn, this strategy let
		 * you put the O on the empty square and let the O not to lose.
		 */
		else if (middleL.getText().equals("X") && middleM.getText().equals("X") && !middleR.getText().equals("X")
				&& !middleR.getText().equals("O")) {
			middleR.doClick();
		} else if (middleR.getText().equals("X") && middleM.getText().equals("X") && !middleL.getText().equals("X")
				&& !middleL.getText().equals("O")) {
			middleL.doClick();
		} else if (middleL.getText().equals("X") && middleR.getText().equals("X") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the lower three blanks. When there are two Xs in a
		 * row, and the other one is empty and O's turn, this strategy let you
		 * put the O on the empty square and let the O not to lose.
		 */
		else if (lowerL.getText().equals("X") && lowerM.getText().equals("X") && !lowerR.getText().equals("X")
				&& !lowerR.getText().equals("O")) {
			lowerR.doClick();
		} else if (lowerR.getText().equals("X") && lowerM.getText().equals("X") && !lowerL.getText().equals("X")
				&& !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerL.getText().equals("X") && lowerR.getText().equals("X") && !lowerM.getText().equals("X")
				&& !lowerM.getText().equals("O")) {
			lowerM.doClick();
		}
		/*
		 * strategies for the left three blanks. When there are two Xs in a row,
		 * and the other one is empty and O's turn, this strategy let you put
		 * the O on the empty square and let the O not to lose.
		 */
		else if (upperL.getText().equals("X") && middleL.getText().equals("X") && !lowerL.getText().equals("X")
				&& !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerL.getText().equals("X") && middleL.getText().equals("X") && !upperL.getText().equals("X")
				&& !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (lowerL.getText().equals("X") && upperL.getText().equals("X") && !middleL.getText().equals("X")
				&& !middleL.getText().equals("O")) {
			middleL.doClick();
		}
		/*
		 * strategies for the middle (height) three blanks. When there are two
		 * Xs in a row, and the other one is empty and O's turn, this strategy
		 * let you put the O on the empty square and let the O not to lose.
		 */
		else if (upperM.getText().equals("X") && middleM.getText().equals("X") && !lowerM.getText().equals("X")
				&& !lowerM.getText().equals("O")) {
			lowerM.doClick();
		} else if (lowerM.getText().equals("X") && middleM.getText().equals("X") && !upperM.getText().equals("X")
				&& !upperM.getText().equals("O")) {
			upperM.doClick();
		} else if (lowerM.getText().equals("X") && upperM.getText().equals("X") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the right three blanks. When there are two Xs in a
		 * row, and the other one is empty and O's turn, this strategy let you
		 * put the O on the empty square and let the O not to lose.
		 */
		else if (upperR.getText().equals("X") && middleR.getText().equals("X") && !lowerR.getText().equals("X")
				&& !lowerR.getText().equals("O")) {
			lowerR.doClick();
		} else if (lowerR.getText().equals("X") && middleR.getText().equals("X") && !upperR.getText().equals("X")
				&& !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (lowerR.getText().equals("X") && upperR.getText().equals("X") && !middleR.getText().equals("X")
				&& !middleR.getText().equals("O")) {
			middleR.doClick();
		}
		/*
		 * strategies for the diagonal three blanks, starting from the upperL
		 * and ends with lowerR or from lowerR to upperL. When there are two Xs
		 * in a row, and the other one is empty and O's turn, this strategy let
		 * you put the O on the empty square and let the O not to lose.
		 */
		else if (upperL.getText().equals("X") && middleM.getText().equals("X") && !lowerR.getText().equals("X")
				&& !lowerR.getText().equals("O")) {
			lowerR.doClick();
		} else if (lowerR.getText().equals("X") && middleM.getText().equals("X") && !upperL.getText().equals("X")
				&& !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (upperL.getText().equals("X") && lowerR.getText().equals("X") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the diagonal three blanks, starting from the upperR
		 * and ends with lowerL or from lowerL to upperR. When there are two Xs
		 * in a row, and the other one is empty and O's turn, this strategy let
		 * you put the O on the empty square and let the O not to lose.
		 */
		else if (upperR.getText().equals("X") && middleM.getText().equals("X") && !lowerL.getText().equals("X")
				&& !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerL.getText().equals("X") && middleM.getText().equals("X") && !upperR.getText().equals("X")
				&& !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (upperR.getText().equals("X") && lowerL.getText().equals("X") && !middleM.getText().equals("X")
				&& !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		// play to block (end here)
		// strategies for the middle blank. When the middle square is empty, and
		// if it's O's turn, put the O in the middle square.
		else if (!middleM.getText().equals("X") && !middleM.getText().equals("O")) {
			middleM.doClick();
		}
		/*
		 * strategies for the corner blanks. When the corner squares (upperL,
		 * upperR, lowerL, lowerR) are empty, and if it's O's turn, put the O in
		 * the corner squares.
		 */
		else if (!upperL.getText().equals("X") && !upperL.getText().equals("O")) {
			upperL.doClick();
		} else if (!upperR.getText().equals("X") && !upperR.getText().equals("O")) {
			upperR.doClick();
		} else if (!lowerL.getText().equals("X") && !lowerL.getText().equals("O")) {
			lowerL.doClick();
		} else if (lowerR.getText().equals("X") && lowerR.getText().equals("O")) {
			lowerR.doClick();
		}
		/*
		 * strategies for the side blanks. When the side squares (middleL,
		 * middleR, uppperM, lowerM) are empty, and if it's O's turn, put the O
		 * in the side squares.
		 */
		else if (!upperM.getText().equals("X") && !upperM.getText().equals("O")) {
			upperM.doClick();
		} else if (!middleL.getText().equals("X") && !middleL.getText().equals("O")) {
			middleL.doClick();
		} else if (!lowerM.getText().equals("X") && !lowerM.getText().equals("O")) {
			lowerM.doClick();
		} else if (!middleR.getText().equals("X") && !middleR.getText().equals("O")) {
			middleR.doClick();
		}
	}
}
