import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CurrencyConverterGUI extends JFrame {

    private static final Map<String, Double> rates = new LinkedHashMap<>();
    private static final Map<String, String> countryCurrency = new LinkedHashMap<>();
    private static final Map<String, String> countryFlags = new LinkedHashMap<>();

    private JComboBox<String> fromBox;
    private JComboBox<String> toBox;
    private JTextField amountField;
    private JLabel resultLabel;
    private JTextArea historyArea;

    public CurrencyConverterGUI() {
        setTitle("💱 Currency Converter");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initRates();
        initFlags();
        setupUI();
    }

    private void setupUI() {
        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        fromBox = new JComboBox<>(getCurrencyDisplayList());
        toBox = new JComboBox<>(getCurrencyDisplayList());
        amountField = new JTextField("");
        resultLabel = new JLabel("💲 Result will appear here");
        resultLabel.setForeground(Color.WHITE);

        JButton convertBtn = new JButton("Convert");

        convertBtn.addActionListener(e -> convert());

        topPanel.add(label("From Currency:"));
        topPanel.add(fromBox);
        topPanel.add(label("To Currency:"));
        topPanel.add(toBox);
        topPanel.add(label("Amount:"));
        topPanel.add(amountField);
        topPanel.add(convertBtn);
        topPanel.add(label("Result:"));
        topPanel.add(resultLabel);

        add(topPanel, BorderLayout.NORTH);

        // History area
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(40, 40, 40));
        historyArea.setForeground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🕘 Conversion History"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void setDarkButton(JButton btn) {
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    private void convert() {
        try {
            String fromItem = (String) fromBox.getSelectedItem();
            String toItem = (String) toBox.getSelectedItem();
            String fromCode = extractCurrencyCode(fromItem);
            String toCode = extractCurrencyCode(toItem);

            double amount = Double.parseDouble(amountField.getText());

            double usdAmount = amount / rates.get(fromCode);
            double result = usdAmount * rates.get(toCode);

            String resultText = String.format("%.2f %s = %.2f %s",
                    amount, fromCode, result, toCode);

            resultLabel.setText("✅ " + resultText);
            historyArea.append(resultText + "\n");

        } catch (NumberFormatException e) {
            resultLabel.setText("❌ Please enter a valid number.");
        }
    }

    private String[] getCurrencyDisplayList() {
        List<String> list = new ArrayList<>();
        for (String code : rates.keySet()) {
            String flag = countryFlags.getOrDefault(code, "🏳️");
            list.add(flag + " " + code);
        }
        return list.toArray(new String[0]);
    }

    private String extractCurrencyCode(String displayString) {
        String[] parts = displayString.split(" ");
        return parts[1];
    }

    private void initRates() {
        rates.put("USD", 1.0);
        rates.put("EUR", 0.94);
        rates.put("INR", 83.2);
        rates.put("JPY", 148.7);
        rates.put("GBP", 0.82);
        rates.put("AUD", 1.55);
    }

    private void initFlags() {
        countryFlags.put("USD", "🇺🇸");
        countryFlags.put("EUR", "🇪🇺");
        countryFlags.put("INR", "🇮🇳");
        countryFlags.put("JPY", "🇯🇵");
        countryFlags.put("GBP", "🇬🇧");
        countryFlags.put("AUD", "🇦🇺");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterGUI().setVisible(true));
    }
}