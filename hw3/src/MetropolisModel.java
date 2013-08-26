package assign3;

import java.sql.ResultSet;

/* 
 * CS108 Student: This file will be used to help the staff grade your assignment.
 * You can modify this file as much as you like, provided three restrictions:
 * 1) Do not change the class/file name
 * 		- The class/file name should be MetropolisModel
 * 2) Do not modify the MetropolisControl interface
 * 3) MetropolisModel must implement the MetropolisControl interface
 * 		- You can modify MetropolisModel to inherit/implement any additional class/interface
 */
public class MetropolisModel implements MetropolisControl {

	public ResultSet search(String metropolis, String continent, String population, boolean populationLargerThan, boolean exactMatch) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(String metropolis, String continent, String population) {
		// TODO Auto-generated method stub
	}
}

interface MetropolisControl {
	/**
	 * Searches the Metropolis data-set for the provided search parameters.
	 * Returns the query results as a java.sql.ResultSet
	 * 
	 * @param metropolis value of the metropolis field
	 * @param continent value of the continent field
	 * @param population value of the population field
	 * @param populationLargerThan True if "Population Larger Than" has been selected
	 * @param exactMatch True if "Exact Match" has been selected
	 * 
	 * @return resultSet Results for the given query
	 */
	public ResultSet search(String metropolis, String continent, String population, boolean populationLargerThan, boolean exactMatch);
	
	/**
	 * Adds the entry to the Metropolis data-set.
	 * 
	 * @param metropolis value of the metropolis field
	 * @param continent value of the continent field
	 * @param population value of the population field
	 */
	public void add(String metropolis, String continent, String population);
}