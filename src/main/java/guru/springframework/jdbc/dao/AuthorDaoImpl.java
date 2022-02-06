package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jt on 8/20/21.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource source;

    public AuthorDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Author getById(Long id) {

        Connection connection = null;
        Author author = new Author();

        try {
            connection = source.getConnection();
            var prepareStatement = connection.prepareStatement("SELECT * FROM author where id= ?");
            prepareStatement.setLong(1,id);
            ResultSet resultSet = prepareStatement.executeQuery();

            if(resultSet.next())
            {

                author.setId(id);
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));
            }

            resultSet.close();
            prepareStatement.close();

            return author;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {

            }
        }


        return null;
    }
}
