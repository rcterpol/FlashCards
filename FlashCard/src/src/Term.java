package src;

import java.util.LinkedList;

/**
 * Contains term and relevant information: definition, abbreviations, and active status
 * @author Ryan Terpolilli
 */
public class Term {
	
	// Term
	String term;
	// Definition of term
	String definition;
	// Possible abbreviations for term
	LinkedList<String> abbrev;
	//Active status
	boolean active;
	
	/**
	 * Creates a term object from the term and its definition
	 * @param term Term
	 * @param definition Definition
	 */
	public Term(String term, String definition) {
		this.term = term;
		this.definition = definition;
		this.abbrev = new LinkedList<String>();
		active = true;
	}
	
	/**
	 * Creates a term object from the term, its definition, and any possible abbreviations
	 * @param term Term
	 * @param abbrev Abbreviations
	 * @param definition Definition
	 */
	public Term(String term, LinkedList<String> abbrev, String definition) {
		this.term = term;
		this.definition = definition;
		this.abbrev = abbrev;
		active = true;
	}

	/**
	 * Gets the term
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * Sets the term
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * Gets the definition
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * Sets the definition
	 * @param definition the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	/**
	 * Gets the list of abbreviations
	 * @return the list of abbreviations
	 */
	public LinkedList<String> getAbbrev() {
		return abbrev;
	}
	
	/**
	 * Adds an abbreviation to the list
	 * @param abbrev the abbreviation to add
	 */
	public void addAbbrev(String abbrev) {
		this.abbrev.add(abbrev);
	}
	
	/**
	 * Adds multiple abbreviations to the list
	 * @param abbrev the list of abbreviations to add
	 */
	public void addAbbrev(LinkedList<String> abbrev) {
		this.abbrev.addAll(abbrev);
	}
	
	/**
	 * Removes abbreviation from the list
	 * @param abbrev the abbreviation to remove
	 * @return the removed string, or null if not found
	 */
	public String removeAbbrev(String abbrev) {
		return this.abbrev.remove(abbrev) ? abbrev : null;
	}
	
	/**
	 * Sets the activity state for the term
	 * @param active the activity state of the term
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Gets the activity state for the term
	 * @return the activity state of the term
	 */
	public boolean isActive() {
		return active;
	}
}
