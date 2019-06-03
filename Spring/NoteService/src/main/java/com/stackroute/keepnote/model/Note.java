package com.stackroute.keepnote.model;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
public class Note {
	/*
	 * This class should have eight fields
	 * (noteId,noteTitle,noteContent,noteStatus,createdAt,
	 * category,reminder,createdBy). This class should also contain the getters and
	 * setters for the fields along with the no-arg , parameterized constructor and
	 * toString method. The value of createdAt should not be accepted from the user
	 * but should be always initialized with the system date.
	 * 
	 */
	@Id
	private int noteId;
	private String noteTitle;
	private String noteContent;
	private String noteStatus;
	private Date CreatedAt = new Date();
	private Category category;
	private List<Reminder> reminder;
	private String createdBy;
	public Note() {
	}
	public Note(int noteId, String noteTitle, String noteContent, String noteStatus, Date CreatedAt, Category category,
			List<Reminder> reminders, String createdBy) {
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteStatus = noteStatus;
		this.CreatedAt = CreatedAt;
		this.category = category;
		this.reminder = reminders;
		this.createdBy = createdBy;
	}
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getNoteStatus() {
		return noteStatus;
	}
	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}
	public Date getNoteCreationDate() {
		return CreatedAt;
	}
	public void setNoteCreationDate(Date noteCreationDate) {
		this.CreatedAt = noteCreationDate;
	}
	public String getNoteCreatedBy() {
		return createdBy;
	}
	public void setNoteCreatedBy(String noteCreatedBy) {
		this.createdBy = noteCreatedBy;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Reminder> getReminders() {
		return reminder;
	}
	public void setReminders(List<Reminder> reminders) {
		this.reminder = reminders;
	}
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteTitle=" + noteTitle + ", noteContent=" + noteContent + ", noteStatus="
				+ noteStatus + ", CreatedAt=" + CreatedAt + ", category=" + category + ", reminder=" + reminder
				+ ", createdBy=" + createdBy + "]";
	}
}