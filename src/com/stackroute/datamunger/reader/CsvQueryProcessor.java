package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;

public class CsvQueryProcessor implements QueryProcessingEngine {
	private static BufferedReader bufferedReader;
	private static String line;
	private Header header;
	private RowDataTypeDefinitions rowDataTypeDefinitions;
	DataTypeDefinitions dataTypeDefinitions;
	DataSet dataSet = new DataSet();
	Row row;
	private long rowID = 1;
	Filter filter = new Filter();
	List<Boolean> flag = new ArrayList<Boolean>();
	String condition, propertyName, propertyValue;

	/*
	 * This method will take QueryParameter object as a parameter which contains
	 * the parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) {

		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */
		try {
			bufferedReader = new BufferedReader(new FileReader(queryParameter.getFile()));
			/*
			 * read the first line which contains the header and populate the
			 * header Map object
			 */

			String[] headerValue = getHeader(bufferedReader.readLine());

			/*
			 * read the next line which contains the first row of data and
			 * populate the RowDataTypeDefinition Map object.
			 */
			getColumnType(bufferedReader.readLine());

			/*
			 * once we have the header and dataTypeDefinitions maps populated,
			 * we can start reading from the first line. We will read one line
			 * at a time, then check whether the field values satisfy the
			 * conditions mentioned in the query,if yes, then we will add it to
			 * the resultSet. Otherwise, we will continue to read the next line.
			 * We will continue this till we have read till the last line of the
			 * CSV file.
			 */

			/*
			 * reset the buffered reader so that it can start reading from the
			 * first line
			 */
			bufferedReader = new BufferedReader(new FileReader(queryParameter.getFile()));
			bufferedReader.readLine();

			/*
			 * read one line at a time from the CSV file
			 */

			// while ((line = bufferedReader.readLine()) != null) {
			int x = 1;
			while (x != 0) {
				x--;
				line = bufferedReader.readLine();
				/*
				 * once we have read one line, we will split it into a String
				 * Array. This array will continue all the fields of the row.
				 * Please note that fields might contain spaces in between.
				 * Also, few fields might be empty.
				 */
				String[] columnValue = line.split(",", -1);
				/*
				 * if there are where condition(s) in the query, test the row
				 * fields against those conditions to check whether the selected
				 * row satifies the conditions
				 */
				if (queryParameter.getQUERY_TYPE().equalsIgnoreCase("where")) {
					/*
					 * from QueryParameter object, read one condition at a time
					 * and evaluate the same. For evaluating the conditions, we
					 * will use evaluateExpressions() method of Filter class.
					 * Please note that evaluation of expression will be done
					 * differently based on the data type of the field. In case
					 * the query is having multiple conditions, you need to
					 * evaluate the overall expression i.e. if we have OR
					 * operator between two conditions, then the row will be
					 * selected if any of the condition is satisfied. However,
					 * in case of AND operator, the row will be selected only if
					 * both of them are satisfied.
					 */
					List<Restriction> restrictions = queryParameter.getRestrictions();
					Iterator<Restriction> itr = restrictions.iterator();
					Restriction restriction = new Restriction();
					while (itr.hasNext()) {
						restriction = itr.next();
						System.out.println("Name: " + restriction.getPropertyName());
						propertyName = restriction.getPropertyName();
						System.out.println("Cond: " + restriction.getCondition());
						condition = restriction.getCondition();
						System.out.println("Value: " + restriction.getPropertyValue());
						propertyValue = restriction.getPropertyValue();

						flag.add(filter.evaluateExpression(condition, propertyValue,
								columnValue[header.get(propertyName)],
								rowDataTypeDefinitions.get(header.get(propertyName))));
					}
					/*
					 * check for multiple conditions in where clause for eg:
					 * where salary>20000 and city=Bangalore for eg: where
					 * salary>20000 or city=Bangalore and dept!=Sales
					 */

					/*
					 * if the overall condition expression evaluates to true,
					 * then we need to check if all columns are to be
					 * selected(select *) or few columns are to be
					 * selected(select col1,col2). In either of the cases, we
					 * will have to populate the row map object. Row Map object
					 * is having type <String,String> to contain field Index and
					 * field value for the selected fields. Once the row object
					 * is populated, add it to DataSet Map Object. DataSet Map
					 * object is having type <Long,Row> to hold the rowId (to be
					 * manually generated by incrementing a Long variable) and
					 * it's corresponding Row Object.
					 */}
				row = new Row();
				if (queryParameter.getFields().isEmpty() | queryParameter.getFields().contains("*")) {
					for (int i = 0; i < headerValue.length; i++) {
						row.put(headerValue[i], columnValue[i]);
					}
					dataSet.put(rowID++, row);

				} else {
					Iterator<String> itrString = queryParameter.getFields().iterator();
					while (itrString.hasNext()) {
						String field = itrString.next();
						row.put(field, columnValue[header.get(field)]);
					}
					dataSet.put(rowID++, row);
				}

			}

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		// System.out.println(dataSet);
		/* return dataset object */
		return dataSet;
	}

	/*
	 * implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */
	private String[] getHeader(String line) throws IOException {

		int counter = 0;
		header = new Header();
		String[] headerValue = line.split(",");
		for (String head : headerValue) {
			header.put(head, counter++);
		}
		return headerValue;
	}

	/*
	 * implementation of getColumnType() method. To find out the data types, we
	 * will read the first line from the file and extract the field values from
	 * it.
	 */
	public void getColumnType(String line) throws IOException {
		int counter = 1;
		String[] columnData = line.split(",", -1);
		rowDataTypeDefinitions = new RowDataTypeDefinitions();
		dataTypeDefinitions = new DataTypeDefinitions();
		for (String column : columnData) {
			rowDataTypeDefinitions.put(counter++, dataTypeDefinitions.getDataType(column));
		}
		return;
	}

}
