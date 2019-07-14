package com.satodai.demo.login.domain.repository.jdbc;

import com.satodai.demo.login.domain.model.User;
import com.satodai.demo.login.domain.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public int count() {

        String sql = "" +
                "SELECT COUNT(*)" +
                "FROM m_user";

        SqlParameterSource params = new MapSqlParameterSource();

        return jdbc.queryForObject(sql, params, Integer.class);
    }

    @Override
    public int insertOne(User user) {

        String sql = "" +
                "INSERT INTO m_user(" +
                "    user_id," +
                "    password," +
                "    user_name," +
                "    birthday," +
                "    age," +
                "    marriage," +
                "    role" +
                ") VALUES (" +
                "    :userId," +
                "    :password," +
                "    :userName," +
                "    :birthday," +
                "    :age," +
                "    :marriage," +
                "    :role" +
                ")";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("userName", user.getUserName())
                .addValue("birthday", user.getBirthday())
                .addValue("age", user.getAge())
                .addValue("marriage", user.isMarriage())
                .addValue("role", user.getRole());

        return jdbc.update(sql, params);
    }

    @Override
    public User selectOne(String userId) {

        String sql = "" +
                "SELECT * " +
                "FROM m_user " +
                "WHERE user_id = :userId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        // NamedParameterJdbtTemplateでは、RowMapperは使える
        // BeanPropertyRowMapperは使えない
        RowMapper<User> rowMapper = new UserRowMapper();

        return jdbc.queryForObject(sql, params, rowMapper);
    }

    @Override
    public List<User> selectMany() {

        String sql = "" +
                "SELECT * " +
                "FROM m_user";

        SqlParameterSource params = new MapSqlParameterSource();

        List<Map<String, Object>> getList = jdbc.queryForList(sql, params);

        List<User> userList = new ArrayList<>();

        getList.forEach(record -> userList.add(populate(record)));

        return userList;
    }

    @Override
    public int updateOne(User user) {

        String sql = "" +
                "UPDATE m_user " +
                "SET " +
                "    password = :password," +
                "    user_name = :userName," +
                "    birthday = :birthday," +
                "    age = :age," +
                "    marriage = :marriage " +
                "WHERE user_id = :userId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("userName", user.getUserName())
                .addValue("birthday", user.getBirthday())
                .addValue("age", user.getAge())
                .addValue("marriage", user.isMarriage());

        return jdbc.update(sql, params);
    }

    @Override
    public int deleteOne(String userId) throws DataAccessException {

        String sql = "" +
                "DELETE FROM m_user " +
                "WHERE user_id = :userId";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbc.update(sql, params);
    }

    @Override
    public void userCsvOut() throws DataAccessException {
        String sql = "" +
                "SELECT * " +
                "FROM m_user";

        UserRowCallbackHandler handler = new UserRowCallbackHandler();

        jdbc.query(sql, handler);
    }

    private User populate(Map map) {

        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);

        return mapper.map(map, User.class);
    }
}
