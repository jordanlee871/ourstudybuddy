package com.billjc.speak.users.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.users.entity.User;

public class UserRowMapper implements RowMapper<User> {
	
	public User mapRow(ResultSet rs, int row) throws SQLException {
		User user = new User();
		user.setUser_id(rs.getLong("user_id"));
		user.setUser_name(rs.getString("user_name"));
		user.setUser_pwd(rs.getString("user_pwd"));
		user.setUser_role(rs.getInt("user_role"));
		user.setCreate_time(rs.getDate("create_time"));
		user.setStatus(rs.getInt("status"));
		return user;
	}

}
