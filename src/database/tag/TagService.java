package database.tag;

import database.SetParser;
import model.Tag;
import model.TagList;
import model.Task;

import java.sql.*;

public class TagService {

    private Connection connection;
    private SetParser setParser;

    public TagService(Connection connection) {
        this.connection = connection;
        setParser = new SetParser();
    }

    public Tag getTag(Long id) throws SQLException {
        String query = "SELECT * FROM tags WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        Tag tag = setParser.getTagsFromSet(set).get(0);
        return tag;
    }

    public TagList getAllTags() throws SQLException {
        String query = "SELECT * FROM tags;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        TagList tagList = setParser.getTagsFromSet(set);
        return tagList;
    }

    public Long saveTag(Tag tag) throws SQLException {
        if (validateTag(tag)) {


            String query = "INSERT INTO tags (name) VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
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

    public void deleteTag(Long id) throws SQLException {
        String query = "DELETE FROM tags WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    public TagList getTagsOfTask(Long taskId) throws SQLException {
        String query = "SELECT * FROM tags WHERE id IN (SELECT tag_id FROM tag_task WHERE task_id = ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, taskId);
        ResultSet set = statement.executeQuery();
        TagList tagList = setParser.getTagsFromSet(set);
        return tagList;
    }

    private boolean validateTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag is null");
        }
        if (tag.getName() == null) {
            throw new IllegalArgumentException("Tag name is null");
        }
        return true;
    }


}
