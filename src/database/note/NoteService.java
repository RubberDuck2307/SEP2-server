package database.note;

import database.SetParser;
import model.Note;
import model.NoteList;

import java.sql.*;

public class NoteService
{
  private Connection conn;
  private SetParser setParser;

  public NoteService(Connection conn)
  {
    this.conn = conn;
    setParser = new SetParser();
  }

  public void saveNote(Note note) throws SQLException
  {
    NoteDO noteDO = new NoteDO(note);

    String query = "INSERT INTO notes (title, note_text, creation_date) VALUES (" + noteDO.getTitle() + ", " + noteDO.getNoteText() + ", " + noteDO.getCreationDate() + ");";
    Statement statement = conn.createStatement();
    statement.executeUpdate(query);
  }

  public void updateNote(Note note) throws SQLException
  {
    NoteDO noteDO = new NoteDO(note);

    String query = "UPDATE notes SET title = " + noteDO.getTitle() + ", note_text = " + noteDO.getNoteText() + ", creation_date = "
        + noteDO.getCreationDate() + " WHERE id = " + noteDO.getId() + ";";
    Statement statement = conn.createStatement();
    statement.executeUpdate(query);
  }

  public NoteList getAllNotesSavedByEmployee(Integer workingNumber) throws SQLException
  {
    String query = "SELECT * FROM notes WHERE id in (SELECT id FROM notes WHERE working_number = " + workingNumber + " );";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    NoteList noteList = setParser.getAllNotesFromSet(set);
    return noteList;
  }
}
