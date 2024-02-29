import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class Settings extends JFrame {
    private JPanel jPanel;
    private JButton resetButton;
    private JButton confirmButton;
    private JTextArea a0TextArea;
    private String savedText = "";

    private final Preferences preferences;

    public Settings() {
        preferences = Preferences.userNodeForPackage(Settings.class);

        setContentPane(jPanel);
        setTitle("Settings");
        setSize(250, 100);
        setLocationRelativeTo(null);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textValue = a0TextArea.getText();
                saveText(textValue);
            }
        });

        String savedText = preferences.get("textValue", "");
        a0TextArea.setText(savedText);
    }

    public String getSavedText() {
        return preferences.get("textValue", "");
    }

    private void saveText(String text) {
        preferences.put("textValue", text);
        savedText = text;
        System.out.println("Text value saved: " + text);
    }
}