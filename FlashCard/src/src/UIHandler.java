package src;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Handles UI functionality for the flash card app
 * @author Ryan Terpolilli
 */
public class UIHandler {
	
	//Term handler
	TermHandler tHandler;
	//Frame
	JFrame frame;
	//Flash Card Panel
	FlashCardPanel flashCardPanel;
	//Background image
	Image bg;
	//Notecard image
	Image notecard;
	
	/**
	 * Creates the UI Handler
	 */
	public UIHandler() {
		initializeFrame();
		Scanner scanner = new Scanner(System.in);
		do {
			try {
				tHandler = new TermHandler(getTermLocation(scanner));
			} catch (FileNotFoundException e) {
				System.out.println("Invalid file.");
			}
		} while(tHandler == null || tHandler.getActiveTerms() == null);
		scanner.close();
		startCards();
	}
	
	/**
	 * Creates the UI handler and uses the location provided for the term list. If an
	 * invalid location is provided, asks the user for a new file location until a valid
	 * location is found.
	 * @param termLoc Term list file location
	 */
	public UIHandler(String termLoc) {
		initializeFrame();
		Scanner scanner = new Scanner(System.in);
		try {
			tHandler = new TermHandler(termLoc);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file.");
			do {
				try {
					tHandler = new TermHandler(getTermLocation(scanner));
				} catch (FileNotFoundException f) {
					System.out.println("Invalid file.");
				}
			} while(tHandler.getActiveTerms() == null);
		}
		scanner.close();
		startCards();
	}
	
	//Helper function which initializes the frame
	private void initializeFrame() {
		frame = new JFrame("Flashcards");
		frame.setSize(1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		bg = null;
		notecard = null;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.print("Could not get system look and feel.");
		}
		try { //Default background
			bg = ImageIO.read(new File("felt background.jpg"));
		} catch(Exception e) {
			System.out.println("Could not load default background.");
		}
		try { //Default notecard
			notecard = ImageIO.read(new File("notecard.jpg"));
		} catch(Exception e) {
			System.out.println("Could not load default notecard.");
		}
	}
	
	/**
	 * Sets the location for the terms file
	 * @param termLoc File location
	 */
	public void setTermLocation(String termLoc) {
		tHandler.setTermLocation(termLoc);
	}
	
	//Helper function which starts the card display 
	private void startCards() {
		flashCardPanel = new FlashCardPanel(tHandler.getMasterTerms(), bg, notecard);
		frame.add(flashCardPanel);
		frame.setVisible(true);
	}
	
	//Helper function to prompt the user for a new file location.
	private String getTermLocation(Scanner scanner) {
		//TODO: Add UI for getting term location from user instead of console
		System.out.print("Enter terms file location:");
		String loc = scanner.nextLine();
		return loc;
	}
}
