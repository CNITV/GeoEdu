package VianuEdu.backend.LessonLibrary;

import VianuEdu.backend.DatabaseHandling.JSONManager;
import VianuEdu.backend.Identification.Teacher;

import java.util.ArrayList;

public class Lesson {
	private String title;
	private String author;
	private String course;
	private int grade;
	private ArrayList<byte[]> pages;

	public Lesson(String title, Teacher author, int grade, ArrayList<byte[]> pages) {
		if (!(grade > 0 && grade <= 12)) {
			throw new IllegalArgumentException("Lesson must be between 1st and 12th grade!");
		}
		this.title = title;
		this.author = author.getFirstName() + " " + author.getLastName();
		this.course = author.getCourse();
		this.grade = grade;
		this.pages = pages;
	}

	public Lesson(String title, Teacher author, int grade) {
		this.title = title;
		this.author = author.getFirstName() + " " + author.getLastName();
		this.course = author.getCourse();
		this.grade = grade;
		this.pages = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(Teacher author) {
		this.author = author.getFirstName() + " " + author.getLastName();
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if (!(course.equals("Geo") || course.equals("Phi") || course.equals("Info") || course.equals("Math"))) {
			throw new IllegalArgumentException("Lesson must be part of a VianuEdu-compatible course!");
		}
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		if (!(grade > 0 && grade <= 12)) {
			throw new IllegalArgumentException("Lesson must be between 1st and 12th grade!");
		}
		this.grade = grade;
	}

	public void addPage(byte[] page) {
		pages.add(page);
	}

	public void swapPage(int firstIndex, int secondIndex) {
		if (firstIndex < 0 || firstIndex > pages.size()) {
			throw new IllegalArgumentException("First index out of bounds! Index must be between 0 and the number of pages!");
		} else if (secondIndex < 0 || secondIndex > pages.size()) {
			throw new IllegalArgumentException("Second index out of bounds! Index must be between 0 and the number of pages!");
		}
		byte[] auxiliary = pages.get(firstIndex);
		pages.set(firstIndex, pages.get(secondIndex));
		pages.set(secondIndex, auxiliary);
	}

	public void removePage(int index){
		if (index < 0 || index > pages.size()) {
			throw new IllegalArgumentException("Index must be between 0 and the number of pages!");
		}
		pages.remove(index);
	}

	@Override
	public String toString() {
		return JSONManager.toJSON(this);
	}
}
