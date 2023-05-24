package database;

import model.Note;
import model.NoteList;

import java.sql.SQLException;

public interface NoteService
{
  void saveNote(Note note) throws SQLException;
  void validateNote(Note note);
  void updateNote(Note note) throws SQLException;
  NoteList getAllNotesSavedByEmployee(Integer workingNumber)
      throws SQLException;
}
