import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HospitalEmergencyManagementApplet extends Applet
        implements ActionListener {

    TextField patientIdField, patientNameField, ageField;
    Choice emergencyChoice, doctorChoice, priorityChoice;
    Checkbox criticalCheck, insuranceCheck;
    TextArea displayArea;
    Button registerBtn, clearBtn;

    HashSet<String> patientIds = new HashSet<String>();

    public void init() {

        setLayout(new BorderLayout());

        Panel p = new Panel(new GridLayout(7, 2));

        p.add(new Label("Patient ID:"));
        patientIdField = new TextField();
        p.add(patientIdField);

        p.add(new Label("Patient Name:"));
        patientNameField = new TextField();
        p.add(patientNameField);

        p.add(new Label("Age:"));
        ageField = new TextField();
        p.add(ageField);

        p.add(new Label("Emergency:"));
        emergencyChoice = new Choice();
        emergencyChoice.add("Accident");
        emergencyChoice.add("Heart Attack");
        emergencyChoice.add("Fever");
        emergencyChoice.add("Other");
        p.add(emergencyChoice);

        p.add(new Label("Doctor:"));
        doctorChoice = new Choice();
        doctorChoice.add("Dr. Kumar");
        doctorChoice.add("Dr. Priya");
        doctorChoice.add("Dr. Arun");
        p.add(doctorChoice);

        p.add(new Label("Priority:"));
        priorityChoice = new Choice();
        priorityChoice.add("Low");
        priorityChoice.add("Medium");
        priorityChoice.add("High");
        p.add(priorityChoice);

        criticalCheck = new Checkbox("Critical Patient");
        insuranceCheck = new Checkbox("Insurance");
        p.add(criticalCheck);
        p.add(insuranceCheck);

        registerBtn = new Button("Register");
        clearBtn = new Button("Clear");

        p.add(registerBtn);
        p.add(clearBtn);

        add(p, BorderLayout.NORTH);

        displayArea = new TextArea();
        add(displayArea, BorderLayout.CENTER);

        registerBtn.addActionListener(this);
        clearBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clearBtn) {
            patientIdField.setText("");
            patientNameField.setText("");
            ageField.setText("");
            displayArea.setText("");
            return;
        }

        String id = patientIdField.getText();
        String name = patientNameField.getText();
        String ageText = ageField.getText();

        if (id.equals("") || name.equals("") || ageText.equals("")) {
            displayArea.setText("Please enter all required details.");
            return;
        }

        if (patientIds.contains(id)) {
            displayArea.setText("Duplicate Patient ID!");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (Exception ex) {
            displayArea.setText("Invalid age!");
            return;
        }

        if (age <= 0) {
            displayArea.setText("Invalid age!");
            return;
        }

        String emergency = emergencyChoice.getSelectedItem();
        String doctor = doctorChoice.getSelectedItem();

        String priority;

        if (criticalCheck.getState() ||
            emergency.equals("Heart Attack") ||
            emergency.equals("Accident")) {
            priority = "HIGH";
        } else {
            priority = priorityChoice.getSelectedItem();
        }

        patientIds.add(id);

        displayArea.setText(
            "Patient Registered Successfully\n" +
            "Patient ID: " + id + "\n" +
            "Name: " + name + "\n" +
            "Age: " + age + "\n" +
            "Emergency: " + emergency + "\n" +
            "Doctor: " + doctor + "\n" +
            "Priority: " + priority + "\n" +
            "Insurance: " +
            (insuranceCheck.getState() ? "Yes" : "No")
        );

        if (priority.equals("HIGH")) {
            displayArea.append("\nALERT: Critical patient!");
        }
    }

    public static void main(String[] args) {

        System.out.println("Hospital Emergency Management");
        System.out.println("Applet Loaded");
        System.out.println("Uses Choice, Checkbox, TextField,");
        System.out.println("TextArea, Button and MenuBar");
        System.out.println("Patient registration and validation enabled");
        System.out.println("Duplicate registration prevented");
        System.out.println("Critical patient alerts enabled");
    }
}
