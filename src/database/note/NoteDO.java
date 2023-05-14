package database.note;

import model.Note;

public class NoteDO
{
  private String id;
  private String title;
  private String noteText;
  private String creationDate;

  public NoteDO(Note note)
  {
    if(note.getId() == null)
    {
      id = "NULL";
    }
    else {
      id = note.getId().toString();
    }

    if(note.getTitle() == null)
    {
      throw new RuntimeException("Title cannot be null");
    }
    else
    {
      title = "'" + note.getTitle() + "'";
    }

    if(note.getNoteText() == null)
    {
      throw new RuntimeException("Note text cannot be null");
    }
    else
    {
      noteText = "'" + note.getNoteText() + "'";
    }

    if(note.getCreationDate() == null)
    {
      throw new RuntimeException("Creation date cannot be null");
    }
    else
    {
      creationDate = "'" + note.getCreationDate() + "'";
    }
  }

  public String getId()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }

  public String getNoteText()
  {
    return noteText;
  }

  public String getCreationDate()
  {
    return creationDate;
  }
}
