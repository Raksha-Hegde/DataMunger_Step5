package com.stackroute.datamunger.query;

import java.util.Iterator;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.stackroute.datamunger.query.parser.Restriction;

//this class contains methods to evaluate expressions
public class Filter {

	private Boolean flag = false;
	private Boolean conditionFlag;

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
	ScriptEngineManager sem = new ScriptEngineManager();
	ScriptEngine javaScript = sem.getEngineByName("JavaScript");

	public Boolean evaluateExpression(String condition, String propertyValue, String columnValue,
			String columnDataType) {
		switch (condition) {
		case "=": {
			flag = equalTo(propertyValue, columnValue, columnDataType);
			break;
		}
		case "!=": {
			flag = notEqualTo(propertyValue, columnValue, columnDataType);
			break;
		}
		case "<=": {
			flag = lessThanOrEqualTo(propertyValue, columnValue, columnDataType);
			break;
		}
		case "<": {
			flag = lessThan(propertyValue, columnValue, columnDataType);
			break;
		}
		case ">=": {
			flag = greaterThanOrEqualTo(propertyValue, columnValue, columnDataType);
			break;
		}
		case ">": {
			flag = greaterThan(propertyValue, columnValue, columnDataType);
			break;
		}

		}
		return flag;
	}

	// method containing implementation of equalTo operator
	private Boolean equalTo(String propertyValue, String columnValue, String columnDataType) {
		if (columnDataType.equals("java.lang.String")) {
			{
				conditionFlag = propertyValue.equals(columnValue);
			}
		} else if (columnDataType.equals("java.lang.Integer")) {
			conditionFlag = (Integer.parseInt(propertyValue) == Integer.parseInt(columnValue));
		} else if (columnDataType.equals("java.lang.Float")) {
			conditionFlag = (Float.parseFloat(propertyValue) == Float.parseFloat(columnValue));
		} else if (columnDataType.equals("java.util.Date")) {
			

		}
		return conditionFlag;

	}

	// method containing implementation of notEqualTo operator
	private Boolean notEqualTo(String propertyValue, String columnValue, String columnDataType) {
		if (columnDataType.equals("java.lang.String")) {
			conditionFlag = !propertyValue.equals(columnValue);
		} else if (columnDataType.equals("java.lang.Integer")) {
			conditionFlag = (Integer.parseInt(propertyValue) != Integer.parseInt(columnValue));
		} else if (columnDataType.equals("java.lang.Float")) {
			conditionFlag = (Float.parseFloat(propertyValue) != Float.parseFloat(columnValue));
		} else if (columnDataType.equals("java.util.Date")) {

		}
		return conditionFlag;

	}

	// method containing implementation of greaterThan operator
	private Boolean greaterThan(String propertyValue, String columnValue, String columnDataType) {
		if (columnDataType.equals("java.lang.String")) {
			conditionFlag = propertyValue.equals(columnValue);
		} else if (columnDataType.equals("java.lang.Integer")) {
			conditionFlag = (Integer.parseInt(propertyValue) > Integer.parseInt(columnValue));
		} else if (columnDataType.equals("java.lang.Float")) {
			conditionFlag = (Float.parseFloat(propertyValue) > Float.parseFloat(columnValue));
		} else if (columnDataType.equals("java.util.Date")) {

		}
		return conditionFlag;

	}

	// method containing implementation of greaterThanOrEqualTo operator
	private Boolean greaterThanOrEqualTo(String propertyValue, String columnValue, String columnDataType) {
		if (columnDataType.equals("java.lang.String")) {
			conditionFlag = propertyValue.equals(columnValue);
		} else if (columnDataType.equals("java.lang.Integer")) {
			conditionFlag = (Integer.parseInt(propertyValue) >= Integer.parseInt(columnValue));
		} else if (columnDataType.equals("java.lang.Float")) {
			conditionFlag = (Float.parseFloat(propertyValue) >= Float.parseFloat(columnValue));
		} else if (columnDataType.equals("java.util.Date")) {

		}
		return conditionFlag;

	}

	// method containing implementation of lessThan operator
	private Boolean lessThan(String propertyValue, String columnValue, String columnDataType) {
		if (columnDataType.equals("java.lang.String")) {
			conditionFlag = propertyValue.equals(columnValue);
		} else if (columnDataType.equals("java.lang.Integer")) {
			conditionFlag = (Integer.parseInt(propertyValue) < Integer.parseInt(columnValue));
		} else if (columnDataType.equals("java.lang.Float")) {
			conditionFlag = (Float.parseFloat(propertyValue) < Float.parseFloat(columnValue));
		} else if (columnDataType.equals("java.util.Date")) {

		}
		return conditionFlag;

	}

	// method containing implementation of lessThanOrEqualTo operator
	private Boolean lessThanOrEqualTo(String propertyValue, String columnValue, String columnDataType) {
		if (columnDataType.equals("java.lang.String")) {
			conditionFlag = propertyValue.equals(columnValue);
		} else if (columnDataType.equals("java.lang.Integer")) {
			conditionFlag = (Integer.parseInt(propertyValue) <= Integer.parseInt(columnValue));
		} else if (columnDataType.equals("java.lang.Float")) {
			conditionFlag = (Float.parseFloat(propertyValue) <= Float.parseFloat(columnValue));
		} else if (columnDataType.equals("java.util.Date")) {

		}
		return conditionFlag;

	}

}
