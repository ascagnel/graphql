package com.dibs.graphql.data.response;

public class ErrorLocation {

	private Integer line;
	private Integer column;
	
	public ErrorLocation() {
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}
}
