package DataEntrySystem; 
 
public class Student{ 
    private String name; 
    private int age; 
    private String roll_no; 
    public Student(String n , int a , String rollNo){ 
        this.name = n; 
        this.age = a; 
        this.roll_no = rollNo; 
    } 
    public String getName(){ 
        return name; 
    } 
    public int getAge(){ 
        return age; 
    } 
    public String getRollNo(){ 
        return roll_no; 
    } 
    public String toString(){ 
        return name + age + " (" + roll_no +")"; 
    } 
} 
 
public class UGStudent extends Student { 
    private String stream; 
 
    public UGStudent(String n, int a, String rollNo, String s) { 
        super(n, a, rollNo); 
        this.stream = s; 
    } 
 
    public String getStream() { 
        return stream; 
    } 
 
    @Override 
    public String toString() { 
        return "[Under-Graduate(UG) " + super.toString() + ", Stream: " + stream + "]"; 
    } 
} 
 
public class PGStudent extends Student { 
    private String specializa on; 
 
    public PGStudent(String n, int a, String rollNo, String spec) { 
        super(n, a, rollNo); 
        this.specializa on = spec; 
    } 
 
    public String getSpecializa on() { 
        return specializa on; 
    } 
 
    @Override 
    public String toString() { 
        return "[Post-Graduate(PG) " + super.toString() + ", Specializa on: " + specializa on + "]"; 
    } 
} 
 
public class PHDStudent extends Student { 
    private String thesisTopic; 
 
    public PHDStudent(String n, int a, String rollNo, String t_Topic) { 
        super(n, a, rollNo); 
        this.thesisTopic = t_Topic; 
    } 
 
    public String getThesisTopic() { 
        return thesisTopic; 
    } 
 
    @Override 
    public String toString() { 
        return "[Post-doctorate(PHD) " + super.toString() + ", Thesis: " + thesisTopic + "]"; 
    } 
} 
 
package GUI; 
 
import DataEntrySystem.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.u l.ArrayList; 
 
public class StudentEntryForm extends JFrame { 
    private JTextField nameField, ageField, rollField, extraField; 
    private JComboBox<String> typeBox; 
    private JTextArea displayArea; 
    private ArrayList<Student> studentList = new ArrayList<>(); 
 
    public StudentEntryForm() { 
        setTitle("Student Entry Form"); 
        setSize(650, 400); 
        setDefaultCloseOpera on(EXIT_ON_CLOSE); 
        setLayout(new FlowLayout()); 
 
        // Input fields 
        nameField = new JTextField(15); 
        ageField = new JTextField(5); 
        rollField = new JTextField(10); 
        extraField = new JTextField(15); 
 
        // Dropdown for student type 
        String[] types = {"Under-Graduate(UG)", "Post-Graduate(PG)", "PhD"}; 
        typeBox = new JComboBox<>(types); 
 
        // Display area for added students 
        displayArea = new JTextArea(10, 55); 
        displayArea.setEditable(false); 
 
        JBu on addBu on = new JBu on("Add"); 
 
        // Add components to frame 
        add(new JLabel("Name:")); add(nameField); 
        add(new JLabel("Age:")); add(ageField); 
        add(new JLabel("Roll No:")); add(rollField); 
        add(new JLabel("Student Type:")); add(typeBox); 
        add(new JLabel("Stream / Specializa on / Thesis:")); add(extraField); 
        add(addBu on); 
        add(new JScrollPane(displayArea)); 
 
        // Bu on click event 
        addBu on.addAc onListener(e -> { 
            try { 
                String name = nameField.getText().trim(); 
                int age = Integer.parseInt(ageField.getText().trim()); 
                String roll = rollField.getText().trim(); 
                String extra = extraField.getText().trim(); 
                String type = (String) typeBox.getSelectedItem(); 
 
                Student student; 
                if (type.equals("Under-Graduate(UG)")) { 
                    student = new UGStudent(name, age, roll, extra); 
                } else if (type.equals("Post-Graduate(PG)")) { 
                    student = new PGStudent(name, age, roll, extra); 
                } else { 
                    student = new PHDStudent(name, age, roll, extra); 
                } 
 
                studentList.add(student); 
                displayArea.append("Added: " + student.toString() + "\n"); 
 
                // Clear input fields 
                nameField.setText(""); 
                ageField.setText(""); 
                rollField.setText(""); 
                extraField.setText(""); 
            } catch (Excep on ex) { 
                JOp onPane.showMessageDialog(this, "Please enter valid values (age must be a 
number)."); 
            } 
        }); 
    } 
} 
 
package main_DataEntry; 
 
import GUI.StudentEntryForm; 
public class Main { 
    public sta c void main(String[] args) { 
        javax.swing.SwingU li es.invokeLater(() -> new StudentEntryForm().setVisible(true)); 
    } 
} 
 
 
 
 