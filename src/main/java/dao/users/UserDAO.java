package dao.users;

import dao.DataAccessObject;
import models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DataAccessObject<User> {

    private final static Logger logger = Logger.getLogger(UserDAO.class);

    private static final String FIND_BY_ID = "SELECT id, name, surname, balance FROM users WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, name, surname, balance FROM users";

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User findById(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while(resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setBalance(resultSet.getBigDecimal("balance"));
            }
            logger.info("findById method was invoked in UserDAO");
            return user.getId() != 0 ? user : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> findAll() {
        try(Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setBalance(resultSet.getBigDecimal("balance"));
                userList.add(user);
            }
            logger.info("findAll method was invoked in UserDAO");
            return userList.size() != 0 ? userList : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
