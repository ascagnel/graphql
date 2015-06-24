package com.dibs.graphql.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.parse.TokenType;

public class QuerySerializer {
	private static final Log LOG = LogFactory.getLog(QuerySerializer.class);
	
	public void serialize(OutputStream stream, Query query) throws IOException {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Starting query serialization");
		}
		
		writeQuery(stream, query);
		
		if (LOG.isTraceEnabled()) {
			LOG.trace("Finished query serialization");
		}
		
	}
	
	private void writeAttributes(OutputStream stream, Query query) throws IOException {
		if (query.getParams() != null) {
			ArrayList<Map.Entry<String, String>> attributes = new ArrayList<>(query.getParams().entrySet());
			
			int attributeSize = attributes.size();
			
			stream.write(TokenType.ATTRIBUTE_START.getValue());
			
			for (int i = 0; i < attributeSize; i++) {
				boolean isLastAttribute = (i == (attributeSize - 1));
				
				Map.Entry<String, String> attribute = attributes.get(i);
				
				stream.write(attribute.getKey().getBytes());
				stream.write(TokenType.ATTRIBUTE_DELIM.getValue());
				stream.write(attribute.getValue().getBytes());
				
				if (!isLastAttribute) {
					stream.write(TokenType.OBJECT_DELIM.getValue());
				}
				
			}
			
			stream.write(TokenType.ATTRIBUTE_END.getValue());

		}
	}
	
	private void writeQuery(OutputStream stream, Query query) throws IOException {		
		if (query.getName() != null) {
			stream.write(query.getName().getBytes());
		}
		
		writeAttributes(stream, query);
		
		List<Query> subQueries = query.getSubQueries();
		
		if (subQueries != null && !subQueries.isEmpty()) {
			stream.write(TokenType.OBJECT_START.getValue());

			
			int subQueryCount = query.getSubQueries().size();
			
			for (int i = 0; i < subQueryCount; i++) {
				boolean isLastSubQuery = (i == (subQueryCount - 1));
				
				writeQuery(stream, subQueries.get(i));
				
				if (!isLastSubQuery) {
					stream.write(TokenType.OBJECT_DELIM.getValue());
				}
			}
			
			stream.write(TokenType.OBJECT_END.getValue());
		}
	}
}
