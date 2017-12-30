package com.stackroute.datamunger.query;

import java.util.Iterator;
import java.util.List;
import com.stackroute.datamunger.query.parser.Restriction;

//this class contains methods to evaluate expressions
public class Filter {

	private boolean flag = false;
	private static Restriction restriction;
	private String condition, propertyName, propertyValue;
	Header header;
	RowDataTypeDefinitions rowDataTypeDefinitions = new RowDataTypeDefinitions();
	/*
	 * the evaluateExpression() method of this class is responsible for
	 * evaluating the expressions mentioned in the query. It has to be noted
	 * that the process of evaluating expressions will be different for
	 * different data types. there are 6 operators that can exist within a query
	 * i.e. >=,<=,<,>,!=,= This method should be able to evaluate all of them.
	 * Note: while evaluating string expressions, please handle uppercase and
	 * lowercase
	 * 
	 */

	public boolean evaluateExpression(List<Restriction> list) {
		Iterator<Restriction> itr = list.iterator();
		restriction = new Restriction();
		while (itr.hasNext()) {
			restriction = itr.next();
			System.out.println("Name: " + restriction.getPropertyName());
			propertyName = restriction.getPropertyName();
			System.out.println("Cond: " + restriction.getCondition());
			condition = restriction.getCondition();
			System.out.println("Value: " + restriction.getPropertyValue());
			propertyValue = restriction.getPropertyValue();
			
			
		}
		return flag;
	}

	// method containing implementation of equalTo operator
	private boolean equalTo() {

		return false;
	}

	// method containing implementation of notEqualTo operator

	// method containing implementation of greaterThan operator

	// method containing implementation of greaterThanOrEqualTo operator

	// method containing implementation of lessThan operator

	// method containing implementation of lessThanOrEqualTo operator

}
