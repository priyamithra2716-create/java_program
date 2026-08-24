import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class BankingTransactionManagementApplet extends Applet
        implements ActionListener {

    double balance = 10000.0;

    Choice transactionChoice;
    TextField amountField, accountField;
    Label balanceLabel, statusLabel;
    Button executeBtn, clearBtn;
    TextArea historyArea;

    public void init() {

        setLayout(new BorderLayout());

        Label title = new Label(
            "Banking Transaction Management",
            Label.CENTER
        );
        add(title, BorderLayout.NORTH);

        Panel panel = new Panel(new GridLayout(4, 2));

        panel.add(new Label("Transaction:"));

        transactionChoice = new Choice();
        transactionChoice.add("Deposit");
        transactionChoice.add("Withdrawal");
        transactionChoice.add("Fund Transfer");
        transactionChoice.add("Balance Inquiry");
        panel.add(transactionChoice);

        panel.add(new Label("Amount:"));
        amountField = new TextField();
        panel.add(amountField);

        panel.add(new Label("Account No:"));
        accountField = new TextField();
        panel.add(accountField);

        executeBtn = new Button("Execute");
        clearBtn = new Button("Clear");

        panel.add(executeBtn);
        panel.add(clearBtn);

        add(panel, BorderLayout.CENTER);

        balanceLabel = new Label("Balance: Rs. " + balance);
        statusLabel = new Label("Status: Ready");
        historyArea = new TextArea();

        Panel bottom = new Panel(new GridLayout(3, 1));
        bottom.add(balanceLabel);
        bottom.add(statusLabel);
        bottom.add(historyArea);

        add(bottom, BorderLayout.SOUTH);

        executeBtn.addActionListener(this);
        clearBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clearBtn) {
            amountField.setText("");
            accountField.setText("");
            statusLabel.setText("Status: Cleared");
            return;
        }

        String choice = transactionChoice.getSelectedItem();

        if (choice.equals("Balance Inquiry")) {
            balanceLabel.setText("Balance: Rs. " + balance);
            statusLabel.setText("Status: Balance displayed");
            historyArea.append("Balance Inquiry\n");
            return;
        }

        if (amountField.getText().equals("")) {
            statusLabel.setText("Status: Incomplete input");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (Exception ex) {
            statusLabel.setText("Status: Invalid amount");
            return;
        }

        if (amount <= 0) {
            statusLabel.setText("Status: Invalid amount");
            return;
        }

        if (choice.equals("Deposit")) {

            balance += amount;
            statusLabel.setText("Status: Deposit successful");
            historyArea.append("Deposited: Rs. " + amount + "\n");

        } else if (choice.equals("Withdrawal")) {

            if (amount > balance) {
                statusLabel.setText("Status: Insufficient balance");
                return;
            }

            balance -= amount;
            statusLabel.setText("Status: Withdrawal successful");
            historyArea.append("Withdrawn: Rs. " + amount + "\n");

        } else if (choice.equals("Fund Transfer")) {

            if (accountField.getText().equals("")) {
                statusLabel.setText("Status: Account number required");
                return;
            }

            if (amount > balance) {
                statusLabel.setText("Status: Insufficient balance");
                return;
            }

            balance -= amount;
            statusLabel.setText("Status: Transfer successful");

            historyArea.append(
                "Transferred Rs. " + amount +
                " to " + accountField.getText() + "\n"
            );
        }

        balanceLabel.setText("Balance: Rs. " + balance);
    }

    public static void main(String[] args) {

        System.out.println("Banking Transaction Management");
        System.out.println("Applet Loaded");
        System.out.println("Features:");
        System.out.println("Deposit");
        System.out.println("Withdrawal");
        System.out.println("Fund Transfer");
        System.out.println("Balance Inquiry");
        System.out.println("Initial Balance: Rs. 10000.0");
    }
}
