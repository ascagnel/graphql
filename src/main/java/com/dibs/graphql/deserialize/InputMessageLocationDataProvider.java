package com.dibs.graphql.deserialize;

import com.dibs.graphql.deserialize.data.TokenData;

public interface InputMessageLocationDataProvider {

	public Integer getLineNumber();
	public Integer getColumnNumber();
	public TokenData getLastReadToken();
}
