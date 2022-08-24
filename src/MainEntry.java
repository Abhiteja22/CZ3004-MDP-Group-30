package mdp_test_own;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class MainEntry {
	public static void main(String[] args) {
		MainView mainView = new MainView();
		
		JFrame frame = new JFrame();
		int frameWidth = MainView.PANEL_WIDTH + 25;
		int frameHeight = MainView.PANEL_HEIGHT + 40;
					
		frame.setSize(frameWidth, frameHeight);
		frame.add(mainView);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}