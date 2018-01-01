package com.stackroute.datamunger.query;

import java.util.HashMap;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.reader.CsvQueryProcessor;

public class Query {

	/*
	 * This method will: 1.parse the query and populate the QueryParameter
	 * object 2.Based on the type of query, it will select the appropriate Query
	 * processor.
	 */
	public HashMap executeQuery(String queryString) {

		QueryParser queryParser = new QueryParser();

		/*
		 * call parseQuery() method of the class by passing the queryString
		 * which will return object of QueryParameter
		 */
		QueryParameter queryParameter = queryParser.parseQuery(queryString);

		/*
		 * call the getResultSet() method of CsvQueryProcessor class by passing
		 * the QueryParameter Object to it. This method is supposed to return
		 * resultSet which is a HashMap
		 */
		CsvQueryProcessor queryProcessor = new CsvQueryProcessor();
		return queryProcessor.getResultSet(queryParameter);
	}

}
