import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class IndustrialProductionControlApplet extends Applet implements ActionListener, ItemListener {

    static class Machine {
        String name;
        String status = "STOPPED";
        int productCount = 0;
        Machine(String name) { this.name = name; }
    }

    Machine machine1 = new Machine("Machine 1");
    Machine machine2 = new Machine("Machine 2");
    Machine machine3 = new Machine("Machine 3");
    Machine[] machines = {machine1, machine2, machine3};

    int selectedIndex = 0;
    Label statusLabel, countLabel, selectionLabel;
    Choice machineChoice;
    TextArea reportArea;
    Button startBtn, stopBtn, pauseBtn, resumeBtn, resetBtn, generateReportBtn, viewAllBtn;

    public void init() {
        setLayout(new BorderLayout());

        Panel topPanel = new Panel(new FlowLayout());
        selectionLabel = new Label("Select Machine:");
        machineChoice = new Choice();
        machineChoice.add("Machine 1");
        machineChoice.add("Machine 2");
        machineChoice.add("Machine 3");
        machineChoice.addItemListener(this);

        statusLabel = new Label("Status: STOPPED");
        countLabel = new Label("Count: 0");

        topPanel.add(selectionLabel);
        topPanel.add(machineChoice);
        topPanel.add(statusLabel);
        topPanel.add(countLabel);
        add(topPanel, BorderLayout.NORTH);

        reportArea = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
        Panel centerPanel = new Panel(new GridLayout(1, 1));
        centerPanel.add(reportArea);
        add(centerPanel, BorderLayout.CENTER);

        Panel buttonPanel = new Panel(new FlowLayout());
        startBtn = new Button("Start");
        stopBtn = new Button("Stop");
        pauseBtn = new Button("Pause");
        resumeBtn = new Button("Resume");
        resetBtn = new Button("Reset");
        generateReportBtn = new Button("Generate Report");
        viewAllBtn = new Button("View All Machines");

        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);
        pauseBtn.addActionListener(this);
        resumeBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        generateReportBtn.addActionListener(this);
        viewAllBtn.addActionListener(this);

        buttonPanel.add(startBtn);
        buttonPanel.add(stopBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(resumeBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(generateReportBtn);
        buttonPanel.add(viewAllBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        updateUI();
        log("Applet Initialized. Select a machine to control.");
    }

    public void itemStateChanged(ItemEvent e) {
        selectedIndex = machineChoice.getSelectedIndex();
        updateUI();
        log("Selected: " + machines[selectedIndex].name);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        Machine current = machines[selectedIndex];

        if (cmd.equals("Start")) {
            startMachine(current);
        } else if (cmd.equals("Stop")) {
            stopMachine(current);
        } else if (cmd.equals("Pause")) {
            pauseMachine(current);
        } else if (cmd.equals("Resume")) {
            resumeMachine(current);
        } else if (cmd.equals("Reset")) {
            resetMachine(current);
        } else if (cmd.equals("Generate Report")) {
            generateReport();
        } else if (cmd.equals("View All Machines")) {
            viewAllMachines();
        }
        updateUI();
    }

    private void startMachine(Machine m) {
        if (m.status.equals("RUNNING")) {
            log("ERROR: Already RUNNING");
            return;
        }
        m.status = "RUNNING";
        m.productCount++;
        log(m.name + " Started. Production Count: " + m.productCount);
    }

    private void stopMachine(Machine m) {
        if (m.status.equals("STOPPED")) {
            log("ERROR: Already STOPPED");
            return;
        }
        m.status = "STOPPED";
        log(m.name + " Stopped.");
    }

    private void pauseMachine(Machine m) {
        if (!m.status.equals("RUNNING")) {
            log("ERROR: Cannot pause STOPPED machine");
            return;
        }
        m.status = "PAUSED";
        log(m.name + " Paused.");
    }

    private void resumeMachine(Machine m) {
        if (!m.status.equals("PAUSED")) {
            log("ERROR: Only PAUSED machine can be resumed");
            return;
        }
        m.status = "RUNNING";
        log(m.name + " Resumed.");
    }

    private void resetMachine(Machine m) {
        m.productCount = 0;
        m.status = "STOPPED";
        log(m.name + " Reset to 0.");
    }

    private void updateUI() {
        Machine current = machines[selectedIndex];
        statusLabel.setText("Status: " + current.status);
        countLabel.setText("Count: " + current.productCount);
    }

    private void log(String msg) {
        reportArea.append(msg + "\n");
    }

    private void generateReport() {
        StringBuilder sb = new StringBuilder("---- PRODUCTION REPORT ----\n");
        for (Machine m : machines) {
            sb.append("Machine: ").append(m.name).append(" | Status: ").append(m.status).append(" | Count: ").append(m.productCount).append("\n");
        }
        sb.append("---------------------------\n");
        reportArea.setText(sb.toString());
    }

    private void viewAllMachines() {
        StringBuilder sb = new StringBuilder("---- ALL MACHINES STATUS ----\n");
        for (Machine m : machines) {
            sb.append(m.name).append(" -> ").append(m.status).append(" (").append(m.productCount).append(")\n");
        }
        reportArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println("Industrial Production Control Applet - Use Delegation Event Model");
        System.out.println("Machine 1, Machine 2, Machine 3");
        System.out.println("Operations: Start, Stop, Pause, Resume, Reset with validation");
        System.out.println("Features: FlowLayout, GridLayout, BorderLayout with Menubar");
        System.out.println("Report: Production Report, FlowLayout, GridLayout");
    }
}
