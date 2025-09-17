package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * FlashCardPanel which contains flash card info and preferences.
 * @author Ryan Terpolilli
 */
public class FlashCardPanel extends JPanel {
	
	public enum displayType {FLIP, ALLINONE};
	
	private Image background;
	private Image notecard;
	private LinkedList<Term> history;
	private displayType pref;
	private boolean shuffle;
	private boolean answerSide;
	private JLabel questionLabel;
	private JLabel answerLabel;
	private JLabel notecardLabel;
	private LinkedList<Term> terms;
	private LinkedList<Term> activeTerms;
	private LinkedList<Term> remainingTerms;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Flash Card Panel using the terms list provided, and selected background and card images.
	 * @param terms Term and definition list for flash cards
	 * @param bg Background image
	 * @param card Card image
	 */
	public FlashCardPanel(LinkedList<Term> terms, Image bg, Image card) {
		setPreferredSize(new Dimension(1920, 1280));
		background = bg;
		notecard = card;
		pref = displayType.ALLINONE;
		this.terms = terms;
		activeTerms = terms;
		remainingTerms = activeTerms;
		answerSide = false;
		history = new LinkedList<Term>();
		shuffle = false;
		
		//Creates question label
		questionLabel = new JLabel("", SwingConstants.CENTER);
		questionLabel.setForeground(Color.BLACK);
		questionLabel.setOpaque(false);
		
		//Creates answer label
		answerLabel = new JLabel("", SwingConstants.CENTER);
		answerLabel.setFont(new Font("Arial", Font.BOLD, 20));
		answerLabel.setForeground(Color.BLACK);
		answerLabel.setOpaque(false);
		
		//Creates notecard label
		notecardLabel = new JLabel("", SwingConstants.CENTER);
		notecardLabel.setOpaque(false);
		notecardLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				//If flip is selected, flip to answer on click if question is currently displayed
				if(pref == displayType.FLIP) {
					if(!answerSide) {
						questionLabel.setText("");
						answerLabel.setText("<html>" + history.getLast().getDefinition() + "</html>");
					}
					repaint();
					return;
				}
				
				//Set current term, randomly if shuffling, sequentially if not
				Term t = null;
				if(shuffle) {
					if(remainingTerms.size() == 0) {
						remainingTerms.addAll(activeTerms);
					}
					int idx = (int) (Math.random() * terms.size());
					t = remainingTerms.remove(idx);
				} else {
					t = remainingTerms.removeFirst();
				}
				updateHistory(t);
				
				//Display current term, and answer if all in one
				if(pref == displayType.FLIP) {
					questionLabel.setText("<html>" + t.getTerm() + "</html>");
					answerLabel.setText("");
				} else {
					questionLabel.setText("<html>" + t.getTerm() + "</html>");
					answerLabel.setText("<html>" + t.getDefinition() + "</html>");
				}
				repaint();
			}
		}) ;
	}
	
	/**
	 * Sets display type. Flip flips card from question to answer, All in one displays answer below the question.
	 * @param pref Preference selection
	 */
	public void setDisplayType(displayType pref) {
		if(this.pref == displayType.FLIP && pref != displayType.FLIP) {
			answerSide = false;
		}
		this.pref = pref;
	}
	
	/**
	 * Sets a new termList for flash card terms
	 * @param termList term list
	 */
	public void setTerms(LinkedList<Term> termList) {
		terms = termList;
	}
	
	/**
	 * Gets the current term list
	 * @return list of terms
	 */
	public LinkedList<Term> getTerms() {
		return terms;
	}
	
	/**
	 * Gets currently active terms
	 * @return active terms
	 */
	public LinkedList<Term> getActiveTerms() {
		return activeTerms;
	}
	
	/**
	 * Adds a term from the term list to the active terms
	 * @param t term to add
	 */
	public void addActiveTerm(Term t) {
		activeTerms.add(t);
		remainingTerms = activeTerms;
	}
	
	/**
	 * Sets shuffle on or off
	 * @param shuf true for shuffle, false for sequential
	 */
	public void setShuffle(boolean shuf) {
		shuffle = shuf;
	}
	
	/**
	 * Updates history to a max of 50
	 * @param t Term to add to history
	 */
	private void updateHistory(Term t) {
		if(history.size() >= 50) {
			history.removeFirst();
		}
		history.addLast(t);
	}
	
	/**
	 * Adds a term to the term list and active terms
	 * @param t term to add
	 */
	public void addTerm(Term t) {
		terms.addLast(t);
		activeTerms.addLast(t);
		remainingTerms = activeTerms;
	}
	
	/**
	 * Removes a term from the term list and active terms
	 * @param t term to remove
	 */
	public void removeTerm(Term t) {
		terms.remove(t);
		activeTerms.remove(t);
		remainingTerms = activeTerms;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Draws background
		if(background != null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
		
		//Creates notecard label
		if(notecard != null) {
			//Sets card height and with relative to panel
			int cardWidth = getWidth() * 2 / 3;
			int cardHeight = (int) (cardWidth * .6);
			//Sets x and y relative to center within card
			int cardX = (getWidth() - cardWidth)/2;
			int cardY = (getHeight() - cardHeight)/2;
			
			g.drawImage(notecard, cardX, cardY, cardWidth, cardHeight, this);
			notecardLabel.setBounds(cardX, cardY, cardWidth, cardHeight);
			
			//Sets position of question and answer labels on the card
			switch(pref) {
				case displayType.FLIP:
					questionLabel.setBounds(cardX + 20, cardY + 20, cardWidth - 40, cardHeight /3);
					break;
				case displayType.ALLINONE:
					questionLabel.setBounds(cardX + 20, cardY + 20, cardWidth - 40, cardHeight /3);
					answerLabel.setBounds(cardX + 20, (cardY + cardHeight) / 2, cardWidth - 40, cardHeight / 3);
			}
			
		}
		
		this.setLayout(null);
				
		//Adds labels to panel
		questionLabel.setFont(new Font("Arial", Font.BOLD,(int) (getWidth() * 2 / 3 * .6 )/12));
		answerLabel.setFont(new Font("Arial", Font.BOLD,(int) (getWidth() * 2 / 3 * .6 )/24));
		add(questionLabel);
		add(answerLabel);
		add(notecardLabel);
	}
	
}
