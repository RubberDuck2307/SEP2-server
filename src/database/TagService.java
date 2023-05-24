package database;

import model.Tag;
import model.TagList;

import java.sql.SQLException;

public interface TagService {
    Tag getTag(Long id) throws SQLException;

    TagList getAllTags() throws SQLException;

    Long saveTag(Tag tag) throws SQLException;

    void deleteTag(Long id) throws SQLException;

    TagList getTagsOfTask(Long taskId) throws SQLException;
}
