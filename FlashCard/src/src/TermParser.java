package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Parses terms from an input file using a specified delimiter
 * @author Ryan Terpolilli
 */
public class TermParser {
	
	//List of terms
	LinkedList<Term> terms;
	//Delimiter for parsing
	String delimiter;
	
	//Creates a parser using the specified delimiter
	public TermParser(String delimiter) {
		terms = new LinkedList<Term>();
		this.delimiter = delimiter;
	}
	
	/**
	 * Gets the list of terms
	 * @return the list of terms
	 */
	public LinkedList<Term> getTerms() {
		return terms;
	}
	
	/**
	 * Sets the delimiter to use
	 * @param delimiter delimiter to use
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	/**
	 * Gets the current delimiter for the parser
	 * @return the current delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}
	
	/**
	 * Parses terms from a given file
	 * @param file file to parse
	 * @return the list of terms parsed
	 * @throws FileNotFoundException if file cannot be accessed or is not found
	 */
	public LinkedList<Term> parseTerms(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		String term;
		
		//Breaks the file into lines and passes each line to a helper function for parsing
		while(scanner.hasNextLine()) {
			StringBuilder line = new StringBuilder();
			line.append(scanner.nextLine());
			term = line.toString();
			parseTerm(term);
		}
		
		scanner.close();
		
		return terms;
	}
	
	//Parses the term from the given line
	private void parseTerm(String term) {
		//Splits the line according to the delimiter
		String[] vals = term.split(delimiter);
		
		//If there is no delimiter, print an error message
		if(vals.length < 2) {
			System.err.print("Not enough values to create term: \"" + term + "\" using delimiter: " + delimiter + "\n");
		} else { //Otherwise, parse the line
			String t = vals[0];
			LinkedList<String> abbrevs = new LinkedList<String>();
			//The first item is the term, and any abbreviations should be in parentheses.
			String[] abb = t.split("[(/)]+", 11);
			t = abb[0];
			for(int i = 1; i < abb.length; i ++) {
				if(abb[i].length() > 0) {
					abbrevs.add(abb[i]);
				}
			}
			String def = vals[vals.length - 1].trim();
			//Add the term to the term list
			terms.add(new Term(t, abbrevs, def));
		}
	}
	
}
