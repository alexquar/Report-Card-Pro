// Alex, Anisah, Parker
// May 29 2023
// Report Card Pro
// A program for teachers to view, edit, and add student information and marks

import java.io.*;
import java.io.File;
import java.awt.print.*;
import java.util.Scanner;

class Main {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    System.out.println("Welcome to Report Card Pro!");
    System.out.println();
    // while loop to keep program going until user indicates they are done
    System.out.println(
        "Type Print to get printable files of current students. \nType Add to add a new student. \nType Delete to delete an existing student. \nType Edit to edit an existing students information. \nType Look Up to view an existing student's marks. \nType Average if you would like to see a students average. \nType Finished whenever you are done.");
    System.out.println();
    System.out.println("Enter what you would like to do:");
    String input = sc.nextLine();

    
    int count = 0;
    
    //while loops to keep program going until user is finished
    while (!input.equalsIgnoreCase("Finished")) {

    //if statement to begin print function
      if (input.equalsIgnoreCase("Print")) {
      Print();
        count++;
      }
      
    //if statement to begin look up function
      if(input.equalsIgnoreCase("look up")){
        LookUp();
        count++;
      }

    //if statement to begin add function
      if (input.equalsIgnoreCase("Add")) {
        Add();
        count++;
      }
      
    //if statement to begin delete function
      if (input.equalsIgnoreCase("Delete")) {
        Delete();
        count++;
      }

      //if statement to begin average function
      if (input.equalsIgnoreCase("Average")) {
        Average();
        count++;
      }
      
    //if statement to begin edit function
      if (input.equalsIgnoreCase("Edit")) {
        String name=checkEdit();
        Edit(name);
        count++;
      }
      if(count==0){
      //else statement to make sure a valid function is requested 
      System.out.println("Not a valid request. Please enter a correct word for one of the functions of Report Card Pro.");

      }
      
      System.out.println();
      System.out.println("Enter what you would like to do next:");
      
      //input will restart the while loop
      count=0;
      input = sc.nextLine();
    }

  }

  // Creates a new file that a be opened/printed by the user for the desired
  // student (with their classes and marks)
  public static void Print() throws IOException {
    Scanner sc = new Scanner(System.in);
 
    // students name for printing

    String student = CheckExists();

    File classList = new File("classList.txt");
    Scanner classScan = new Scanner(classList);

    // while loop to check classlist until student is found
    while (classScan.hasNext()) {

      // temp variable for the next student in the class list

      String current = classScan.nextLine();

      // if statement to check when correct student is found in the list
      if (student.equalsIgnoreCase(current)) {
        student = current;
      }
    }

    // names for the students classes and marks files
    String classes = student + "Classes.txt";
    String marksF = student + "Marks.txt";

    File classFile = new File(classes);
    File marksFile = new File(marksF);

    Scanner inputC = new Scanner(classFile);
    Scanner inputM = new Scanner(marksFile);

    // arrays of the students marks and classes
    String[] courses = new String[4];
    String[] marks = new String[4];

    File outFile = new File(student + "Printable.txt");

    PrintStream output = new PrintStream(outFile);

    // for loops to take in marks and courses for a specific student for printing
    for (int k = 0; k < 4; k++) {
      courses[k] = inputC.nextLine();
      marks[k] = inputM.next();

      output.println(courses[k] + ": " + marks[k]);
    }

    System.out.println("A new printable file has been created for " + student);

  }

  // Method to call when user is trying to add a new student. The student will be
  // added to the classlist file and new files will be made for their classes and
  // marks
  public static void Add() throws IOException {
    Scanner sc = new Scanner(System.in);

    // uses the checkNew() method to take in a new student that is not part of the
    // current class list
    String name = checkNew();
    File outMarks = new File(name + "Marks.txt");
    File outClasses = new File(name + "Classes.txt");
    PrintStream outM = new PrintStream(outMarks);
    PrintStream outC = new PrintStream(outClasses);
    // for loop to sort through the current classes and marks of a given student
    for (int k = 0; k < 4; k++) {
      // displays the course number for visual clarity
      int courseNum = k + 1;
      // calls the CheckClass method which allows users to enter a new class and
      // confirm that it is in the proper form
      String course = CheckClass(courseNum);
      outC.println(course);

      // calls the CheckMark method which allows users to enter a new mark and
      // confirms that it is an integer between 0 & 100
      int mark = CheckMark(courseNum);
      outM.println(mark);
    }

    // uses the addToClassList() method which adds to students to the class list
    addToClassList(name);
    
  }

  // Adds a new student's name to the file containing the classlist
  public static void addToClassList(String name) throws IOException {
    File inFile = new File("classList.txt");
    Scanner input = new Scanner(inFile);
    File outfile = new File("temp.txt");
    PrintStream out = new PrintStream(outfile);

    // takes in all of the names from the class list
    while (input.hasNext()) {
      // temp variable for input from class list
      String k = input.nextLine();
      // prints to a temp file
      out.println(k);
    }
    File j = new File("temp.txt");
    Scanner in = new Scanner(j);
    File p = new File("classList.txt");
    PrintStream output = new PrintStream(p);
    // prints all of the names back to the class list
    while (in.hasNext()) {
      // takes in names from a temp file
      String current = in.nextLine();
      // prints back to classlist
      output.println(current);
    }
    // adds the new name to the class list
    output.println(name);

  }

  // Method to call when trying to delete a student. The student's name will be
  // removed from the classlist file and their classes/grades files will be
  // deleted
  public static void Delete() throws IOException {
    Scanner sc = new Scanner(System.in);
    // uses the check exists method to see if the student entered to be deleted
    // actaully exists
    String student = CheckExists();
    File C = new File(student + "Classes.txt");
    File M = new File(student + "Marks.txt");
    // deletes the mark and classes file for the student
    C.delete();
    M.delete();
    File inFile = new File("classList.txt");
    Scanner input = new Scanner(inFile);
    File outfile = new File("temp.txt");
    PrintStream out = new PrintStream(outfile);
    // while loop to take in names from the classlist
    while (input.hasNext()) {
      String k = input.nextLine();
      // puts the names in a temp file
      out.println(k);
    }
    File j = new File("temp.txt");
    Scanner in = new Scanner(j);
    File p = new File("classList.txt");
    PrintStream output = new PrintStream(p);
    // while loop to take names back from the temp file
    while (in.hasNext()) {
      // current takes in the names
      String current = in.nextLine();
      // if statement to check if the current name is equal to the name of the student
      // we are trying to delete
      if (!current.equalsIgnoreCase(student)) {
        // if it does not equal the student we are trying to delete the names in put
        // back in the classlist file. This means the name of the student being deleted
        // does not get added back to the class list
        output.println(current);
      }

    }
    System.out.println("Student has been successfully deleted.");

  }

  // Method for looking up a student's information (by last name). The student's
  // files will be located and their classes & marks will be displayed
  public static void LookUp() throws IOException {
    //a counter that is used later to check if the student exists
    int count = 0;
    Scanner sc = new Scanner(System.in);
    File inFile = new File("classList.txt");
    Scanner in = new Scanner(inFile);

    System.out.println("Enter the last name of the student who you would like to look up");
    //last name of the student to search for
    String lastName = sc.next();
    //makes it lower case to account for user input error
    lastName = lastName.toLowerCase();
    System.out.println("Enter the first name of the student who you would like to look up");
    //takes in the first name of the student not for searching but as a precaution in case students have the same last name
    String firstName = sc.next();
    //makes it lower case to account for user input error
    firstName = firstName.toLowerCase();

    //while loop to take in names from the classlist file
    while (in.hasNext()) {
      //variable for the names
      String student = in.nextLine();
      //makes the lower case to prevent user input error
      String studentLow = student.toLowerCase();

      //if statement to check the names to see if it equals the student we are trying to find
      if (studentLow.contains(lastName) && studentLow.contains(firstName)) {
        //prints out the students information all together
        System.out.println("Student: " + student);
        System.out.println("Here is " + student + "'s classes and marks");
        String classes = student + "Classes.txt";
        String marksF = student + "Marks.txt";

        File classFile = new File(classes);
        File marksFile = new File(marksF);

        Scanner inputC = new Scanner(classFile);
        Scanner inputM = new Scanner(marksFile);
//arrays for student info 
        String[] courses = new String[4];
        String[] marks = new String[4];

        //for loops to take in the students info 
        for (int k = 0; k < 4; k++) {
          //populating the arrays
          courses[k] = inputC.nextLine();
          marks[k] = inputM.next();
          System.out.println(courses[k] + ": " + marks[k]);
        }
        //adds to the count so we can ensure the user was found 
        count++;
      }
    }
    //if the count is still at zero we know the student doesnt exist and the user is asked to input another name
    if (count == 0) {
      System.out.println("There is no student by that name. Please try a new name.");
      //the method uses recursion to call itself and repeat the process
      LookUp();
    }
  }

  // Method to edit an existing student's classes and/or grades. This will first
  // determine if the student exists and then read out each of their classes, the
  // corresponding marks, and ask if they need to be changed.
  public static void Edit(String name) throws IOException {

    Scanner sc = new Scanner(System.in);
    File inFileM = new File(name + "Marks.txt");
    Scanner inputM = new Scanner(inFileM);
    File inFileC = new File(name + "Classes.txt");
    Scanner inputC = new Scanner(inFileC);
    //arrays for student info 
    String[] courses = new String[4];
    int[] marks = new int[4];

    //for loop to take in user info
    for (int k = 0; k < 4; k++) {
      //populates the arrays
      courses[k] = inputC.nextLine();
      marks[k] = inputM.nextInt();
      //asks the user if they want to edit specific marks
      System.out.println("Would you like to change the mark for " + courses[k]
          + ". The current mark for this course is " + marks[k] + ".");
//method to check if the answer equals the required yes or no
      String answer = CheckAnswer();

      //if it equals yes we change the mark
      //if the user entered no nothing happens and we move on
      if (answer.equalsIgnoreCase("Yes")) {
        //uses the chechkmark method to get a new valid mark
        int newMark = CheckMark(k + 1);
        marks[k] = newMark;
      }
    }

    //prints all marks and classes back to their respective files
    
    PrintStream outC = new PrintStream(name + "Classes.txt");
    for (int j = 0; j < 4; j++) {
      outC.println(courses[j]);
    }
    PrintStream outM = new PrintStream(name + "Marks.txt");
    for (int j = 0; j < 4; j++) {
      outM.println(marks[j]);
    }
  }

  // Method for viewing a student's average. The student will be located and then
  // their average will be outputted
  public static void Average() throws IOException {
    Scanner sc = new Scanner(System.in);
    //uses the method to take in a students name and makes sure it is in the classlist
    String name = CheckAvgName();

    File inFileM = new File(name + "Marks.txt");
    Scanner inM = new Scanner(inFileM);
  //total that will be used to calculate the average
    int total = 0;
  //while loop to take in marks
    while (inM.hasNextInt()) {
  //variable to take in the next mark 
      int mark = inM.nextInt();
  //adds it to the total
      total += mark;
    }
//calculates the average
    double average = total / 4.0;
    double roundedAvg = Math.round(average);
//rounds and prints the average
    System.out.println(name + "'s average is " + roundedAvg + ".");
  }

    // all the checks for user input errors:

  // Called when user tries to add a new student. Checks if the student already exists in the classlist, when the user enters a student with a totally new name, the name will be returned
  public static String checkNew() throws IOException {
    Scanner sc = new Scanner(System.in);
    File list = new File("classList.txt");
    Scanner classin = new Scanner(list);
    String student=" ";
    System.out.println("Enter the name of a new student you are adding");
    //takes in name for new student and checks if it is an int or string
    if(sc.hasNextInt()){
      System.out.println("Not a valid name, please try again.");
      student= checkNew();
    }
    else if(sc.hasNext()){
           student = sc.nextLine();
    }
//while loop to bring in names from class list
    while (classin.hasNext()) {
      //checks names from classlist to see if student already exists 
      if (student.equalsIgnoreCase(classin.nextLine())) {
        //if the student already exists recursion is used to get a new inputed name
        System.out.println("Student already exists");
        student = checkNew();
      }
    }
    //returns the name for the new student to where the methods was called
    return student;
}

  // Called when user is trying to edit a student's information. Checks if the student exists in the classlist, only returns the student name when an existing student is entered
  public static String checkEdit() throws IOException {
    Scanner sc = new Scanner(System.in);
    File list = new File("classList.txt");
    Scanner classin = new Scanner(list);
    System.out.println("Enter the name of the student you are editing");
    // takes in name for the student to edit
    String student = sc.nextLine();
    // creates an int variable for use when reading through the classlist file, starts at 0
    int count = 0;
//while loop to bring in names from class list
    while (classin.hasNext()) {

      // creates a string for the current line of the classlist file 
      String current = classin.nextLine();
      // checks if the entered name is the same as current line (name) of the classlist file
      if (student.equalsIgnoreCase(current)) {
        // if they're the same, sets the entered name equal to the current line
        student = current;
        // count increases by 1
        count++;
      }
    }
// if the count is 0, the student was not found in any line of the list
    if (count == 0) {       
      System.out.println("Student does not exist");
        //if the student already exists recursion is used to get a new inputed name
      student = checkEdit();
    }
    //returns the name for a valid, existing student to where the methods was called
    return student;
  }


  // Called when user is entering a new/updated mark for a class. Checks if user enters an integer value and returns the integer when done
  public static int CheckMark(int courseNum) throws IOException {
    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter an integer mark for course " + courseNum + ":");
//the variable for the eventual new mark
    int newMark = 0;
//checks to make sure the user entered an int
    if(!sc.hasNextInt()) {
      // if it is int recursion is used to get a new input 
      System.out.println("Not an integer.");
      newMark = CheckMark(courseNum);
    } else {
      //if it is indeed an int it is saved to the variable 
      newMark = sc.nextInt();
    }
//if statement to make sure the mark is within the appropriate range
    if (newMark > 100 || newMark < 0) {
      //if it is not recursion is used to get a new input
      System.out.println("Mark must be between 0 and 100");
      newMark = CheckMark(courseNum);
    }
//returns the new mark to where it was called
    return newMark;
  }

// Called when a user is trying to delete a student. Checks if the student exists in the classlist, only returns the student name when an existing student is entered
  public static String CheckExists() throws IOException {
    Scanner sc = new Scanner(System.in);
    File list = new File("classList.txt");
    Scanner classin = new Scanner(list);
    System.out.println("Enter the name of an existing student:");
    //takes in name of student to be deleted
    String student = sc.nextLine();
    //count that will be used to check if student exists
    int count = 0;
    String temp;
    //while loop to bring in names from class list
    while (classin.hasNext()) {
      //if statement to check if the name equals a student that exists
      temp=classin.nextLine();
      if (student.equalsIgnoreCase(temp)) {
        //adds to the count to confirm the student exists
        student=temp;
        count++;
        
      }
    }
    //if the count is still 0 we know the student does not exist
    if (count == 0) {
      System.out.println("Student doesn't exist");
      //uses recursion to call itself and get a new name
      student = CheckExists();
    }
    //returns the students name to be deleted
    return student;
  }

    // Called when user is entering a new/updated  class name. Checks if user enters a String value ando returns the String when that is done
  public static String CheckClass(int courseNum) throws IOException {
    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter the name for course " + courseNum + ":");

  // instantiates a string for a new class name
    String newClass = " ";
// if the user enters an integer
    if (sc.hasNextInt()) {
      //uses recursion to call this method again and get a valid string for the class name
      System.out.println("Not a course name.");
      newClass = CheckClass(courseNum);
    } else {
      // if the user enters a string value, the new class name is the string 
      newClass = sc.nextLine();
    }
    //returns the class name to be add/updated in the student's file
    return newClass;
  }

   // Called when a user is asked a yes or no question. Checks if the answer is either 'yes' or 'no', only returns the answer when either 'yes' or 'no' is typed.
  public static String CheckAnswer() throws IOException {
    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter yes or no");
// creates a string variable for the user's response to the yes/no question
    String answer = sc.nextLine();

    // if the answer is neither yes nor no
    if (!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("no")) {
            //uses recursion to call this method again and get a yes or no answer
      System.out.println("Please try again, answer must be yes or no.");
      answer = CheckAnswer();
    }
// when the answer is either yes or no, the user's answer is returned to the method that it's called in
    return answer;
  }

// Called when a user wants the average of a student. Checks if the student exists in the classlist, only returns the student name when an existing student is entered
  public static String CheckAvgName() throws IOException {
    Scanner sc = new Scanner(System.in);
    File list = new File("classList.txt");
    Scanner classin = new Scanner(list);
    System.out.println("Enter the name of the student whose average you would like to view");
    //takes in student name
    String student = sc.nextLine();
    //count to check if student exists
    int count = 0;
    String temp;
    //while loop to take in names from the classlist
    while (classin.hasNext()) {
      //if statement to check if the student matches any existing names 
      temp=classin.nextLine();
      if (student.equalsIgnoreCase(temp)) {
        //if it does, count is added and the name is kept
        student=temp;
        count++;
      }
    }
    //if the count is still at zero we know the student does not exist
    if (count == 0) {
      System.out.println("That student doesn't exist!");
      //recursion is used to get a new name 
      student = CheckAvgName();
    }
    return student;
  }

}
