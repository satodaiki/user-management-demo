package com.satodai.demo.login.domain.service;

import com.satodai.demo.login.domain.model.User;
import com.satodai.demo.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * ユーザーサービスクラス<br/>
 * 以下の検証を行っている<br/>
 * <ul>
 *     <li>トランザクションの検証</li>
 * </ul>
 */
@Transactional
@Service
public class UserService {

    // RowMapperのおためしのためにできたクラスとか
    // @Qualifier("UserDaoJdbcImpl")
    // @Qualifier("UserDaoJdbcImpl2")
    // @Qualifier("UserDaoJdbcImpl3")
    // @Qualifier("UserDaoJdbcImpl4")
    // @Qualifier("UserDaoNamedJdbcImpl")
    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    public boolean insert(User user) throws DataAccessException {
        int rowNumber = dao.insertOne(user);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }

        return result;
    }

    public int count() throws DataAccessException {
        return dao.count();
    }

    public List<User> selectMany() throws DataAccessException {
        return dao.selectMany();
    }

    public User selectOne(String userId) throws DataAccessException {
        return dao.selectOne(userId);
    }

    public boolean updateOne(User user) throws DataAccessException {
        int rowNumber = dao.updateOne(user);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }

        return result;
    }

    public boolean deleteOne(String userId) throws DataAccessException {
        int rowNumber = dao.deleteOne(userId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }

        return result;
    }

    public void userCsvOut() throws DataAccessException {
        dao.userCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {

        FileSystem fs = FileSystems.getDefault();

        Path p = fs.getPath(fileName);

        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
