package com.dibs.graphql.breakroom.dao;

import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.data.request.QueryTree;

public interface BreakRoomDao {

	public BreakRoom read(QueryTree query);
}
