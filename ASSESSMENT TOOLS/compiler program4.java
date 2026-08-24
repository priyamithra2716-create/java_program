import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class SmartFactoryMonitoringAndAlertApplet
        extends Applet implements ActionListener {

    CardLayout cardLayout;
    Panel cardPanel;

    Choice modeChoice, machineChoice;
    Scrollbar tempBar, pressureBar, productionBar;

    Label tempLabel, pressureLabel;
    Label productionLabel, machineStatusLabel;
    Label alertLabel;

    Button updateBtn, startBtn, stopBtn;
    TextArea alertArea;

    public void init() {

        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        Panel normalPanel = createPanel("NORMAL MODE");
        Panel warningPanel = createPanel("WARNING MODE");
        Panel emergencyPanel = createPanel("EMERGENCY MODE");

        cardPanel.add(normalPanel, "Normal");
        cardPanel.add(warningPanel, "Warning");
        cardPanel.add(emergencyPanel, "Emergency");

        add(cardPanel, BorderLayout.CENTER);

        Panel top = new Panel(new GridLayout(1, 2));

        top.add(new Label("Mode:"));

        modeChoice = new Choice();
        modeChoice.add("Normal");
        modeChoice.add("Warning");
        modeChoice.add("Emergency");
        modeChoice.addItemListener(e -> changeMode());

        top.add(modeChoice);

        add(top, BorderLayout.NORTH);

        alertArea = new TextArea();
        add(alertArea, BorderLayout.SOUTH);
    }

    Panel createPanel(String mode) {

        Panel p = new Panel(new GridLayout(6, 2));

        p.add(new Label("Temperature:"));
        tempLabel = new Label("25 C");
        p.add(tempLabel);

        p.add(new Label("Pressure:"));
        pressureLabel = new Label("50");
        p.add(pressureLabel);

        p.add(new Label("Production:"));
        productionLabel = new Label("80%");
        p.add(productionLabel);

        p.add(new Label("Machine:"));

        machineChoice = new Choice();
        machineChoice.add("Machine 1");
        machineChoice.add("Machine 2");
        machineChoice.add("Machine 3");
        p.add(machineChoice);

        p.add(new Label("Machine Status:"));
        machineStatusLabel = new Label("Stopped");
        p.add(machineStatusLabel);

        updateBtn = new Button("Update");
        startBtn = new Button("Start");
        stopBtn = new Button("Stop");

        updateBtn.addActionListener(this);
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);

        p.add(updateBtn);
        p.add(startBtn);
        p.add(stopBtn);

        return p;
    }

    void changeMode() {

        String mode = modeChoice.getSelectedItem();

        if (mode.equals("Normal")) {
            cardLayout.show(cardPanel, "Normal");
            alertArea.setText("System operating normally.");
        }
        else if (mode.equals("Warning")) {
            cardLayout.show(cardPanel, "Warning");
            alertArea.setText("WARNING: Check factory conditions.");
        }
        else {
            cardLayout.show(cardPanel, "Emergency");
            alertArea.setText("EMERGENCY: Stop conflicting operations!");
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startBtn) {

            if (modeChoice.getSelectedItem().equals("Emergency")) {
                alertArea.setText(
                    "ALERT: Machine cannot be started in Emergency mode."
                );
            }
            else {
                machineStatusLabel.setText("Running");
                alertArea.setText("Machine started successfully.");
            }
        }

        else if (e.getSource() == stopBtn) {

            machineStatusLabel.setText("Stopped");
            alertArea.setText("Machine stopped.");
        }

        else if (e.getSource() == updateBtn) {

            tempLabel.setText("30 C");
            pressureLabel.setText("60");
            productionLabel.setText("90%");

            alertArea.setText(
                "Factory parameters updated successfully."
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("Smart Factory Monitoring and Alert Applet");
        System.out.println("Applet Loaded");
        System.out.println("Normal Mode");
        System.out.println("Warning Mode");
        System.out.println("Emergency Mode");
        System.out.println("Temperature Monitoring");
        System.out.println("Pressure Monitoring");
        System.out.println("Machine Status Monitoring");
        System.out.println("Production Level Monitoring");
        System.out.println("Alerts Enabled");
    }
}
