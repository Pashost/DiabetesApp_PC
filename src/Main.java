import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;

public class Main extends JFrame {
    private JLabel TExtOnTheBotom;
    private JButton button1;
    private JPanel Jpamel;
    private JTextField textField1;
    private JTextField textField3;
    private JLabel Time;
    private JLabel ShugarLBL;
    private JLabel XBLBL;
    private JButton getThePrevioudsResultsButton;
    private JCheckBox checkBox1;
    private JButton settingsButton;

    public Main(){
        setContentPane(Jpamel);
        setTitle("Diabetes App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(330,200);
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double sugarValue = parseNumber(textField1.getText()).doubleValue();
                    double XBValue = parseNumber(textField3.getText()).doubleValue();
                    double insulinValue = Colkulations(sugarValue, XBValue);
                    saveData(insulinValue,sugarValue,XBValue);
                    JOptionPane.showMessageDialog(null,"Insulin Value: " + insulinValue);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
                    textField1.setText("");
                    textField3.setText("");
                }
            }
        });
        getThePrevioudsResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadFileWindow readFileWindow = new ReadFileWindow();
                readFileWindow.setVisible(true);
            }
        });
    }
    public static void main(String[] args) {
        new Main();
    }
    public double Colkulations(double sugar, double XB){
        double result = (sugar - 6) / 2 + XB;
        return result;
    }
    private Number parseNumber(String text) throws NumberFormatException {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw e;
        }
    }
    private void saveData(double insulinValue, double sugarValue, double XBValue) {
        try {
            File file = new File("insulin_values.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            StringBuilder oldContent = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    oldContent.append(line).append(System.lineSeparator());
                }
            }
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            String newContent = "\t " + "Year :" + year + " Month :" + month + " Day : " + day + " Time:" + ":" + hour + ":" + minute + ":" + second + "\n" +
                    "SugarValue: " + sugarValue + "\n" +
                    "Food Coefficient: " + XBValue + "\n" +
                    "Insulin Value: " + insulinValue + "\n";
            FileWriter writer = new FileWriter(file, true);
            if (oldContent.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(newContent);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
