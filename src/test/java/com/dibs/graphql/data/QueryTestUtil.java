package com.dibs.graphql.data;

public class QueryTestUtil {

	public static String getUserString() {
		String userString = "{\n" + 
						"  user (id: 123) {\n" + 
						"    id,\n" + 
						"    name (style: FULL),\n" + 
						"    isViewerFriend,\n" + 
						"    profilePicture (width:50px, height: 100px) {\n" + 
						"      uri,\n" + 
						"      width,\n" + 
						"      height\n" + 
						"    }, test (field : [abc, def, fg, 456]) {\n" + 
						"		123 \n" + 
						"    } " +
						"  }\n" + 
						"}";
		
		return userString;
	}

	public static Query buildUser() {
		Query user = 
				new QueryBuilder()
					.subQuery(
						new QueryBuilder()
							.name("user")
							.param("id", new Integer(123))
							.subQuery(new QueryBuilder().name("id").build())
							.subQuery(new QueryBuilder().name("name").param("style", "FULL").build())
							.subQuery(new QueryBuilder().name("isViewerFriend").build())
							.subQuery(
								new QueryBuilder()
									.name("profilePicture")
									.param("width", "50px")
									.param("height", "100px")
									.subQuery(new QueryBuilder().name("uri").build())
									.subQuery(new QueryBuilder().name("width").build())
									.subQuery(new QueryBuilder().name("height").build())
								.build())
							.subQuery(
								new QueryBuilder()
									.name("test")
									.param("field", new String[]{"abc", "def", "fg", "456"})
									.subQuery(new QueryBuilder().name("123").build()).build()
							)
						.build()
					).build();
		return user;
	}
	
}
