package org.nicsoft.DB.Parser.SQL;

public enum KeyWordType {
	CLAUSE,
	MODIFIER,
	JOIN,
	JOIN_FILTER,
	SET_OPERATION,
	PARANTHESES,
	SUB,
	ALIAS_PREFIX,
	ATOM,
	LIST_DELIMITER,
	BINARY_OPERATOR,
	LIST_OPERATOR,
	NEGATOR,
	JSON_OPERATOR,
	NULL,
	AGGREGATE_OPERATOR,
	WINDOW_SPECIFIER,
	SORT_ORDER,
	LOGIC,
	FUNCTION,
	DATATYPE,
	SPECIAL,
	UNKNOWN;
}