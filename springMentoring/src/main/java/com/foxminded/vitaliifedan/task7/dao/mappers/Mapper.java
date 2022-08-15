package com.foxminded.vitaliifedan.task7.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T>{

    T get(ResultSet rs) throws SQLException;

}
