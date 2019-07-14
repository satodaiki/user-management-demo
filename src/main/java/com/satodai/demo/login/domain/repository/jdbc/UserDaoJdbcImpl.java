package com.satodai.demo.login.domain.repository.jdbc;

import com.satodai.demo.login.domain.model.User;
import com.satodai.demo.login.domain.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int count() throws DataAccessException {

        String sql = "" +
                "SELECT COUNT(*)" +
                "FROM m_user";

        int count = jdbc.queryForObject(sql, Integer.class);

        return count;
    }

    @Override
    public int insertOne(User user) throws DataAccessException {

        String sql = "" +
                "INSERT INTO m_user(" +
                "    user_id," +
                "    password," +
                "    user_name," +
                "    birthday," +
                "    age," +
                "    marriage," +
                "    role" +
                ") VALUES (?,?,?,?,?,?,?)";

        int rowNumber = jdbc.update(
                sql,
                user.getUserId(),
                user.getPassword(),
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getRole()
        );

        return rowNumber;
    }

    @Override
    public User selectOne(String userId) throws DataAccessException {

        String sql = "" +
                "SELECT * " +
                "FROM m_user " +
                "WHERE user_id = ?";

        Map<String, Object> record = jdbc.queryForMap(sql, userId);

        User user = populate(record);

        return user;
    }

    @Override
    public List<User> selectMany() throws DataAccessException {

        String sql = "" +
                "SELECT *" +
                "FROM m_user";

        List<Map<String, Object>> getList = jdbc.queryForList(sql);

        List<User> userList = new ArrayList<>();

        getList.forEach(record -> {

            User user = populate(record);

            userList.add(user);
        });

        return userList;
    }

    @Override
    public int updateOne(User user) throws DataAccessException {

        String sql = "" +
                "UPDATE m_user " +
                "SET" +
                "    password = ?," +
                "    user_name = ?," +
                "    birthday = ?," +
                "    age = ?," +
                "    marriage = ? " +
                "WHERE user_id = ?";

        int rowNumber = jdbc.update(sql,
                user.getPassword(),
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getUserId());

        // トランザクション確認のため、わざと例外をスローする
        // if (rowNumber > 0) {
        //     throw new DataAccessException("トランザクションテスト"){};
        // }

        return rowNumber;
    }

    @Override
    public int deleteOne(String userId) throws DataAccessException {

        String sql = "" +
                "DELETE FROM m_user " +
                "WHERE user_id = ?";

        int rowNumber = jdbc.update(sql, userId);

        return 0;
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
