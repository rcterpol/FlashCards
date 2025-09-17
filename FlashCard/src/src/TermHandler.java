package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * Creates a term list from a file and handles maintenance of said term list
 * @author Ryan Terpolilli
 */
public class TermHandler {

	//Parser
	TermParser parser;
	//File location
	String fileLoc;
	
	/**
	 * Creates a new parser with the default delimiter
	 */
	public TermHandler() {
		parser = new TermParser(":");
	}
	
	/**
	 * Creates a parser with the default delimiter and parses the file at the location specified
	 * @param fileLoc File location
	 */
	public TermHandler(String fileLoc) throws FileNotFoundException {
		parser = new TermParser(":");
		this.fileLoc = fileLoc;
		parseTerms(fileLoc);
	}
	
	/**
	 * Sets the location of the terms file
	 * @param fileLoc File location
	 */
	public void setTermLocation(String fileLoc) {
		if (fileLoc.equals(this.fileLoc)) {
			return;
		}
		this.fileLoc = fileLoc;
	}
	
	/**
	 * Gets the location of the terms file
	 * @return fileLoc File location
	 */
	public String getTermLocation() {
		return fileLoc;
	}
	
	/**
	 * Sets the delimiter for the parser
	 * @param delimiter delimiter
	 */
	public void setDelimiter(String delimiter) {
		parser.setDelimiter(delimiter);
	}
	
	/**
	 * Gets the current delimiter for the parser
	 * @return delimiter for the parser
	 */
	public String getDelimiter() {
		return parser.getDelimiter();
	}
	
	/**
	 * Gets the master term list
	 * @return master term list
	 */
	public LinkedList<Term> getMasterTerms() {
		return parser.getTerms();
	}
	
	/**
	 * Gets the active term list
	 * @return active term list
	 */
	public LinkedList<Term> getActiveTerms() {
		LinkedList<Term> active = parser.getTerms();
		for(Term t : active) {
			if(!t.isActive()) {
				active.remove(t);
			}
		}
		return active;
	}
	
	/**
	 * Parses the terms from the file at the given location
	 * @param fileLoc File location
	 * @return List of terms from the specified file
	 */
	public LinkedList<Term> parseTerms(String fileLoc) throws FileNotFoundException {
		return parser.parseTerms(new File(fileLoc));
	}
}
