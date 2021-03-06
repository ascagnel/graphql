package com.dibs.graphql.breakroom.dao.impl;


import org.springframework.stereotype.Component;

import com.dibs.graphql.breakroom.dao.BreakRoomDao;
import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.data.request.QueryTree;

@Component
public class BreakRoomDaoImpl implements BreakRoomDao {

	@Override
	public BreakRoom read(QueryTree query) {
		BreakRoom breakRoom = new BreakRoom();
		breakRoom.setName("The Most Beautiful Break Room on Earth");
		breakRoom.setId((Integer)query.getArgumentValue(BreakRoom.ID_PROPERTY));
		return breakRoom;
	}

}
