import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** File: TTT.java
 *  Author: Heather Amthauer
 *	Description: This class is the main for the Tic-Tac-Toe game.
 *  It generates a TTTframe that will hold a JPanel for all of
 *	the game components.
 */

public class TTT  {
	/** The entry main() method */
	public static void main(String[] args) {
				//make a TTTFrame
				TTTFrame frame = new TTTFrame();
				//make it so the Frame closes when the x is clicked
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//make the frame 1000 pixels by 1000 pixels
				frame.setSize(1000,1000);
				//make the frame visible
				frame.setVisible(true);
		   }
}
	


