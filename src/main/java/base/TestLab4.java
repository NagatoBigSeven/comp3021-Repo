package base;
import java.util.List;

public class TestLab4 {
    public static void main(String[] args){

        NoteBook nb = new NoteBook();
        nb.createNote("Java", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
        nb.createNote("Java", "course information", "Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
        nb.createNote("Lab", "Lab requirement","Each lab has 2 credits, 1 for attendance and the other is based the completeness of your lab.");

        nb.createNote("Course", "Time Tables");
        nb.createNote("Assignment", "Assignment Lists");
        nb.createNote("CSE", "Lab Session");
        nb.createNote("Java", "marking scheme", "The quizzes and lab grades will be given based on your attendance in quizzes and lab, respectively.");
        nb.createNote("Java", "java Attendance Checking");
        nb.createTextNote("Assignment", "______ End of Announcement _______");
        nb.createNote("Assignment", "Assignment Topic", "Enrollment System.");
        nb.createNote("Assignment", "Assignment Topic icon");
        nb.createNote("Assignment", "Due Date", "Hand in Oct 13, 11:59 pm.");
        nb.createNote("Assignment", "Due Date icon");
        nb.createNote("Assignment", "Total Weighting", "21% of the total course grade.");
        nb.createNote("Assignment", "Submission", "Through Canvas. Compress only the src folder in the project directory as a zip file. Also, provide your Github repository link on the Canvas submission comment, as mentioned in lab assignments.");
        nb.createNote("Assignment", "Guideline", "Make sure to read the assignment document thoroughly.");
        nb.createTextNote("Assignment", "----------- Attention -----------");


        nb.sortFolders();
        int findex = 0;
        for (Folder folder : nb.getFolders()) {
            if (folder.getName().equals("Assignment")){
                System.out.println("Folder " + findex++ + ":" + folder);
                List<Note> notes = folder.getNotes();
                int nindex = 0;
                for (Note note : notes) {
                    System.out.println("--" + nindex++ + ":" + note.toString());
                }
            }
        }

        List<Note> notes = nb.searchNotes("assignment or and system or date");
        System.out.println("Search Results:");

        if (notes == null || notes.isEmpty()) {
            System.out.println("No Search Results");
        } else  {
            for (Note note : notes) {
                System.out.println(note.toString());
            }
        }


    }
}