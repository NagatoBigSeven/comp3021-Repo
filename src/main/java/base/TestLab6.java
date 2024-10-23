package base;

import java.util.List;

public class TestLab6 {
	public static void main(String[] args) throws Exception{
		
		NoteBook nb = new NoteBook();
		nb.createNote("Java", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createNote("Java", "course information", "Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createNote("Lab", "Lab requirement","Each lab has 2 credits, 1 for attendance and the other is based the completeness of your lab.");

		nb.createNote("Course", "Time Tables");
		nb.createNote("CSE", "Lab Session");
		nb.createNote("Java", "marking scheme", "The quizzes and lab grades will be given based on your attendance in quizzes and lab, respectively.");
		nb.createNote("Java", "java Attendance Checking");
		nb.createNote("Assignment", "Assignment Topic", "Enrollment System.");
		nb.createNote("Assignment", "Assignment Topic icon");
		nb.createNote("Assignment", "Due Date", "Hand in Oct 13, 11:59 pm.");
		nb.createNote("Assignment", "Due Date icon");
		nb.createNote("Assignment", "Total Weighting", "21% of the total course grade.");
		nb.createNote("Assignment", "Submission", "through Canvas. Compress only the src folder in the project directory as a zip file. Also, provide your Github repository link on the Canvas submission comment, as mentioned in lab assignments.");
		nb.createNote("Assignment", "Guideline", "make sure to read the assignment document thoroughly.");

		List<Note> notes = nb.searchNotes("assignment or due or weighting");
		for (Note note : notes) {
			if (note instanceof TextNote) ((TextNote) note).iconify();
		}

		for (Folder folder : nb.getFolders()) {
			if (folder.getName().equals("Assignment")){
				// cloned
				System.out.println("=============== Cloned folder ==================");
				Folder folderAssignmentCloned = folder.clone();
				nb.sortFolders();
				notes = folderAssignmentCloned.getNotes();
				int nindex = 0;
				for (Note note : notes) {
					System.out.println("--" + nindex++ + ":" + note.toString());
				}

				// original
				System.out.println("============== Original folder =================");
				notes = folder.getNotes();
				nindex = 0;
				for (Note note : notes) {
					System.out.println("--" + nindex++ + ":" + note.toString());
				}
				break;
			}
		}
	}
}
