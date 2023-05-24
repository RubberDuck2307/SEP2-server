package model;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class representing an note.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class Note implements Serializable {
  /**
   * The id of the note.
   */
  private Long id;
  /**
   * The title of the note.
   */
  private String title;
  /**
   * The note text of the note.
   */
  private String noteText;
  /**
   * The creation date of the note.
   */
  private LocalDate creationDate;

  /**
   * 4-argument constructor for the class Employee. Does not set UserProfile.
   * @param id
   * @param title
   * @param noteText
   * @param creationDate
   */

  public Note(Long id, String title, String noteText, LocalDate creationDate)
  {
    this.id = id;
    this.title = title;
    this.noteText = noteText;
    this.creationDate = creationDate;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getNoteText()
  {
    return noteText;
  }

  public void setNoteText(String noteText)
  {
    this.noteText = noteText;
  }

  public LocalDate getCreationDate()
  {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate)
  {
    this.creationDate = creationDate;
  }

  @Override
  public String toString() {
    return "Note{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", noteText='" + noteText + '\'' +
        ", creationDate='" + creationDate +
        '}';
  }
}
