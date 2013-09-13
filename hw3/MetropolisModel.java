

import javax.swing.table.AbstractTableModel;
import java.sql.*;

/* 
 * CS108 Student: This file will be used to help the staff grade your assignment.
 * You can modify this file as much as you like, provided three restrictions:
 * 1) Do not change the class/file name
 * 		- The class/file name should be MetropolisModel
 * 2) Do not modify the MetropolisControl interface
 * 3) MetropolisModel must implement the MetropolisControl interface
 * 		- You can modify MetropolisModel to inherit/implement any additional class/interface
 */
public class MetropolisModel extends AbstractTableModel implements MetropolisControl {

    private int rowsCount = 0;
    private int colCount = 0;
    private ResultSet lastResults = null;

    private void setTableState(ResultSet rs) throws SQLException {
        lastResults = rs;
        ResultSetMetaData rsmd = rs.getMetaData();
        colCount = rsmd.getColumnCount();
        rs.last();
        rowsCount =  rs.getRow();
        fireTableStructureChanged();
        System.out.println(rowsCount+" "+colCount);     //for debug
    }

	public ResultSet search(String metropolis, String continent, String population, boolean populationLargerThan, boolean exactMatch) {
        ResultSet rs = null;
        try {
            Connection con = MyDBDemo.getConnection();
            String query = createSearchQuery(metropolis, continent, population, populationLargerThan, exactMatch);

            PreparedStatement preparedStatement = con.prepareStatement(query);
            int position = 1;

            if(!metropolis.isEmpty())preparedStatement.setString(position++, percentSignDecorator(metropolis, exactMatch));
            if(!continent.isEmpty()) preparedStatement.setString(position++, percentSignDecorator(continent, exactMatch));
            if(!population.isEmpty())preparedStatement.setString(position,   population);

            rs = preparedStatement.executeQuery();
            //System.out.println(preparedStatement);
            setTableState(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
	}

    private String createSearchQuery(String metropolis, String continent, String population, boolean populationLargerThan, boolean exactMatch) {
        String query = "SELECT * FROM metropolises ";

        if(!metropolis.isEmpty() || !continent.isEmpty() || !population.isEmpty()){
            query+="WHERE ";

            String conditionEnding = exactMatch ? "= ? ": "LIKE ? ";
            String populationCondition = exactMatch ? conditionEnding : populationLargerThan ? "> ? " : "< ? ";

            boolean needAnd = false;


            if(!metropolis.isEmpty()) {
                needAnd = true;
                query += "metropolis " + conditionEnding;
            }
            if(!continent.isEmpty())   {
                if (needAnd) query += "AND ";
                needAnd = true;
                query += "continent " + conditionEnding;
            }
            if(!population.isEmpty())     {
                if (needAnd) query += "AND ";
                query += "population " + populationCondition;
            }
        }

        query+=";";

        return query;

    }

    private String percentSignDecorator(String population, boolean exactMatch) {
        String cond = exactMatch? "": "%";
        return cond + population + cond;
    }

    public void add(String metropolis, String continent, String population) {
        try {
            Connection con = MyDBDemo.getConnection();

            String query = "INSERT INTO metropolises VALUES(?,?,?);";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,metropolis);
            preparedStatement.setString(2,continent);
            preparedStatement.setString(3,population);
            preparedStatement.executeUpdate();

           search(metropolis, continent, population, true, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

    @Override
    public int getRowCount() {
        return rowsCount;
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        try {
            if(lastResults!=null){
                lastResults.absolute(rowIndex + 1);
                result = lastResults.getObject(columnIndex + 1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
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