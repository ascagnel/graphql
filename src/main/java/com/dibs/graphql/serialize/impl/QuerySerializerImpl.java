package com.dibs.graphql.serialize.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.deserialize.data.Punctuator;
import com.dibs.graphql.serialize.Serializer;

public class QuerySerializerImpl implements Serializer<Query> {
	private static final Log LOG = LogFactory.getLog(QuerySerializerImpl.class);
	
	private static final byte[] LINE_SEPARATOR = System.lineSeparator().getBytes();
	private static final byte[] TAB = "\t".getBytes();
	private static final byte[] SPACE = " ".getBytes();
	
	private boolean isPrettyPrint;
	
	public QuerySerializerImpl() {
		init();
	}
	
	public void setPrettyPrint(boolean isPrettyPrint) {
		this.isPrettyPrint = isPrettyPrint;
	}

	private void init() {
		isPrettyPrint = false;
	}
	
	@Override
	public void serialize(OutputStream stream, Query query) throws IOException {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Starting query serialization");
		}
		
		writeQuery(stream, query, 0, true);
			
		if (LOG.isTraceEnabled()) {
			LOG.trace("Finished query serialization");
		}
	}
	
	
	private void writeArguments(OutputStream stream, Query query) throws IOException {
		if (isPrettyPrint) {
			stream.write(SPACE);
		}
		
		ArrayList<Map.Entry<String, Object>> arguments = new ArrayList<>(query.getArguments().entrySet());
				
		stream.write(Punctuator.OPEN_PAREN.getValue());
		
		int argumentSize = arguments.size();

		for (int i = 0; i < argumentSize; i++) {
			boolean isLastAttribute = (i == (argumentSize - 1));
			
			Map.Entry<String, Object> argument = arguments.get(i);
			
			stream.write(argument.getKey().getBytes());
			stream.write(Punctuator.COLON.getValue());
			if (argument.getValue().getClass().isArray()) {
				stream.write(Arrays.toString((Object[])argument.getValue()).getBytes());
			} else {
				stream.write(argument.getValue().toString().getBytes());
			}
			
			if (!isLastAttribute) {
				stream.write(Punctuator.COMMA.getValue());
				
				if (isPrettyPrint) {
					stream.write(SPACE);
				}
			}
		}
		
		stream.write(Punctuator.CLOSE_PAREN.getValue());
	}
	
	private void writeQuery(OutputStream stream, Query query, int depth, boolean isLastChild) throws IOException {	
		if (isPrettyPrint) {
			indent(stream, depth);
		}
		
		if (query.getAlias() != null) {
			stream.write(query.getAlias().getBytes());
			stream.write(Punctuator.COLON.getValue());

			if (isPrettyPrint) {
				stream.write(SPACE);
			}
		}
		
		if (query.getName() != null) {
			stream.write(query.getName().getBytes());
		}
		
		if (query.getArguments() != null) {
			writeArguments(stream, query);
		}
		
		List<Query> subQueries = query.getSubQueries();
		
		if (subQueries != null && !subQueries.isEmpty()) {
			// Weird, but solves the case of padding the root node and not padding a comma
			if (query.getName() != null && isPrettyPrint) {
				stream.write(SPACE);
			}
			stream.write(Punctuator.OPEN_CURLY_BRACE.getValue());
			
			if (isPrettyPrint) {
				stream.write(LINE_SEPARATOR);
			}
			
			int subQueryCount = query.getSubQueries().size();
			
			for (int i = 0; i < subQueryCount; i++) {
				boolean isLastSubQuery = (i == (subQueryCount - 1));
				
				writeQuery(stream, subQueries.get(i), depth + 1, isLastSubQuery);
			}
			
			if (isPrettyPrint) {
				indent(stream, depth);
			}
			
			stream.write(Punctuator.CLOSE_CURLY_BRACE.getValue());
		}

		if (!isLastChild) {
			stream.write(Punctuator.COMMA.getValue());
		}
		
		if (isPrettyPrint) {
			stream.write(LINE_SEPARATOR);
		}
	}
	
	private void indent(OutputStream outputStream, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			outputStream.write(TAB);
		}
	}
}
