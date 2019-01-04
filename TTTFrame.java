import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** File: TTTFrame.java
 *  Author: Heather Amthauer
 *	Description: This class makes the JFrame that will hold
 *	the TTTPanel
 */
public class TTTFrame extends JFrame{
	public TTTFrame(){
		//set the title do the frame has 
		setTitle("Tic-Tac-Toe");
		//create a TTTPanel
		TTTPanel panel = new TTTPanel();
		//add the Panel to the Frame
		add(panel);
		pack();
	}
}


