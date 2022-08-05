package com.codeWithAshith.SpringBatch.data;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getInt("id"), 
                rs.getString("name"),
                rs.getString("location"),
                rs.getTimestamp("birth_date")
                );
    }
}
