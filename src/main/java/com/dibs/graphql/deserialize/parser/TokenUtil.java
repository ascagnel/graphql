package com.dibs.graphql.deserialize.parser;

import java.util.Set;

import com.dibs.graphql.data.deserialize.TokenData;
import com.dibs.graphql.data.deserialize.Punctuator;
import com.dibs.graphql.util.Util;

public class TokenUtil {

	public static final Set<Punctuator> ARGUMENT_KEY_TERMINATORS = Util.newHashSet(Punctuator.COLON);
	public static final Set<Punctuator> ARGUMENT_VALUE_TERMINATORS = Util.newHashSet(Punctuator.COMMA, Punctuator.CLOSE_BRACKET, Punctuator.CLOSE_PAREN);
	public static final Set<Punctuator> TOKEN_VALUE_TERMINATORS = Util.newHashSet(Punctuator.OPEN_PAREN, Punctuator.COMMA, Punctuator.OPEN_CURLY_BRACE, Punctuator.CLOSE_CURLY_BRACE);

	public static boolean isAttributeTerminator(TokenData token) {
		return token != null && token.getType() == Punctuator.CLOSE_PAREN;
	}	
	
	public static boolean isAttributeStart(TokenData token) {
		Punctuator tokenType = token.getType();
		
		return tokenType == Punctuator.OPEN_PAREN;
	}
	
	public static boolean isArrayStart(TokenData token) {
		Punctuator tokenType = token.getType();

		return tokenType == Punctuator.OPEN_BRACKET;
	}
	
	public static boolean isArrayTerminator(TokenData token) {
		Punctuator tokenType = token.getType();

		return tokenType == Punctuator.CLOSE_BRACKET;
	}
	
	public static boolean isTokenTerminator(TokenData token) {
		if (token == null) {
			return false;
		}
		Punctuator tokenType = token.getType();

		return tokenType == Punctuator.OPEN_CURLY_BRACE 
						|| tokenType == Punctuator.COMMA 
						|| tokenType == Punctuator.CLOSE_CURLY_BRACE;
	}
}
