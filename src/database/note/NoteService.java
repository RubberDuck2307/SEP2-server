package database.note;

import database.SetParser;
import model.Employee;
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
    validateNote(note);
    String query = "INSERT INTO notes (title, note_text, creation_date) VALUES (?, ?, ?);";
    PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    statement.setString(1, note.getTitle());
    statement.setString(2, note.getNoteText());
    statement.setDate(3, Date.valueOf(note.getCreationDate()));
    statement.executeUpdate();
  }

  public void validateNote(Note note)
  {
    if(note.getTitle() == null || note.getTitle().trim().isEmpty()){
      throw new RuntimeException("Title cannot be null");
    }
    if(note.getNoteText() == null || note.getNoteText().trim().isEmpty()){
      throw new RuntimeException("Note text cannot be null");
    }
  }

  public void updateNote(Note note) throws SQLException
  {
    validateNote(note);
    if(note.getId() == null)
    {
      throw new RuntimeException("Id cannot be null");
    }

    String query =
        "UPDATE notes SET title = ?, note_text = ?, creation_date = ? "
            + "WHERE id = ?;";
    PreparedStatement st = conn.prepareStatement(query);
    st.setString(1, note.getTitle());
    st.setString(2, note.getNoteText());
    st.setDate(3, Date.valueOf(note.getCreationDate()));

    st.executeUpdate();
  }

  public NoteList getAllNotesSavedByEmployee(Integer workingNumber) throws SQLException
  {
    String query =
        "SELECT * FROM notes WHERE id in (SELECT id FROM notes WHERE working_number = " + workingNumber + " );";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    NoteList notes = setParser.getAllNotesFromSet(set);
    return notes;
  }
}
