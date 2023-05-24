package database;

import model.Tag;
import model.TagList;

import java.sql.*;

/**
 * The class that handles database operations related to tags.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class DefaultTagService implements TagService {

    private Connection connection;
    private SetParser setParser;

    /**
     * The constructor sets the connection to the given parameter and initializes the SetParser.
     * @param connection
     */
    public DefaultTagService(Connection connection) {
        this.connection = connection;
        setParser = new SetParser();
    }

    /**
     * gets a tag with the given id
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Tag getTag(Long id) throws SQLException {
        String query = "SELECT * FROM tags WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        Tag tag = setParser.getTagsFromSet(set).get(0);
        return tag;
    }

    /**
     *
     * @return all tags from database
     * @throws SQLException
     */
    @Override
    public TagList getAllTags() throws SQLException {
        String query = "SELECT * FROM tags;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        TagList tagList = setParser.getTagsFromSet(set);
        return tagList;
    }

    /**
     * saves the tag to the database
     * @param tag
     * @return
     * @throws SQLException
     */
    @Override
    public Long saveTag(Tag tag) throws SQLException {
        if (validateTag(tag)) {


            String query = "INSERT INTO tags (name, color) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            statement.setString(2, tag.getColor());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating tag failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            Long id;
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
            } else {
                throw new SQLException("Creating tag failed, no ID obtained.");
            }
            return id;
        }
        else {
            throw new IllegalArgumentException("Tag is not valid");
        }
    }

    /**
     * deletes the tag with the given id from the database
     * @param id
     * @throws SQLException
     */
    @Override
    public void deleteTag(Long id) throws SQLException {
        dismissTagFromAllTasks(id);
        String query = "DELETE FROM tags WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    /**
     * gets all tags of the given task
     * @param taskId
     * @return
     * @throws SQLException
     */
    @Override
    public TagList getTagsOfTask(Long taskId) throws SQLException {
        String query = "SELECT * FROM tags WHERE id IN (SELECT tag_id FROM tag_task WHERE task_id = ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, taskId);
        ResultSet set = statement.executeQuery();
        TagList tagList = setParser.getTagsFromSet(set);
        return tagList;
    }

    /**
     * removes tag from every task
     * @param tagId
     * @throws SQLException
     */
    private void dismissTagFromAllTasks(Long tagId) throws SQLException {
        String query = "DELETE FROM tag_task WHERE tag_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, tagId);
        statement.executeUpdate();
    }

    /**
     * check if tag is valid to be saved in the database or throw exception if not
     * @param tag
     * @return
     */

    private boolean validateTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag is null");
        }
        if (tag.getName() == null) {
            throw new IllegalArgumentException("Tag name is null");
        }
        if (tag.getColor() == null) {
            throw new IllegalArgumentException("Tag color is null");
        }
        return true;
    }



}
