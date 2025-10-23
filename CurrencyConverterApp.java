import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CurrencyConverterApp extends JFrame {
    private JTextField amountField;
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextArea resultArea;
    private DefaultTableModel historyTableModel;
    private JTable historyTable;
    private Map<String, Double> exchangeRates;
    private Set<String> favoritePairs;
    private JPanel multiConvertPanel;
    private DecimalFormat df;

    public CurrencyConverterApp() {
        df = new DecimalFormat("#,##0.00");
        favoritePairs = new HashSet<>();
        initializeExchangeRates();
        initializeUI();
    }

    private void initializeExchangeRates() {
        // Base currency: USD
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD(US DOLLARS)", 1.0);
        exchangeRates.put("EUR(EURO)", 0.92);
        exchangeRates.put("GBP(British Pound)", 0.79);
        exchangeRates.put("JPY(JAPANESE Yen)", 149.50);
        exchangeRates.put("CAD(CANDIAN DOLLARS)", 1.36);
        exchangeRates.put("AUD(AUSTRALIAN DOLLARS)", 1.53);
        exchangeRates.put("CHF", 0.88);
        exchangeRates.put("CNY", 7.24);
        exchangeRates.put("INR", 83.12);
        exchangeRates.put("MXN", 17.08);
        exchangeRates.put("BRL", 4.98);
        exchangeRates.put("ZAR", 18.65);
        exchangeRates.put("KRW", 1338.50);
        exchangeRates.put("SGD", 1.34);
        exchangeRates.put("NZD", 1.67);
    }

    private void initializeUI() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.GRAY);

        // Header
        mainPanel.add(createHeader(), BorderLayout.NORTH);

        // Center content with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.GRAY);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        
        tabbedPane.addTab("Converter", createConverterPanel());
        tabbedPane.addTab("Multi-Convert", createMultiConvertPanel());
        tabbedPane.addTab("History", createHistoryPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.BLACK);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("CURRENCY CONVERTER");
        title.setFont(new Font("Sans-Serif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm");
        JLabel dateLabel = new JLabel(sdf.format(new Date()));
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        dateLabel.setForeground(Color.LIGHT_GRAY);

        JPanel textPanel = new JPanel(new BorderLayout(0, 5));
        textPanel.setOpaque(false);
        textPanel.add(title, BorderLayout.NORTH);
        textPanel.add(dateLabel, BorderLayout.SOUTH);

        header.add(textPanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createConverterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Amount
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        amountField = new JTextField("100 ");
        amountField.setFont(new Font("Roboto", Font.BOLD, 16));
        amountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        inputPanel.add(amountField, gbc);

        // From Currency
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(fromLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] currencies = exchangeRates.keySet().toArray(new String[0]);
        Arrays.sort(currencies);
        fromCurrency = new JComboBox<>(currencies);
        fromCurrency.setFont(new Font("Arial", Font.PLAIN, 14));
        fromCurrency.setBackground(Color.WHITE);
        fromCurrency.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.add(fromCurrency, gbc);

        // Swap Button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton swapButton = new JButton("SWAP");
        swapButton.setFont(new Font("Arial", Font.BOLD, 20));
        swapButton.setBackground(Color.WHITE);
        swapButton.setForeground(Color.BLACK);
        swapButton.setFocusPainted(false);
        swapButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        swapButton.addActionListener(e -> swapCurrencies());
        inputPanel.add(swapButton, gbc);

        // To Currency
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(toLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        toCurrency = new JComboBox<>(currencies);
        toCurrency.setFont(new Font("Arial", Font.PLAIN, 14));
        toCurrency.setBackground(Color.WHITE);
        toCurrency.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        toCurrency.setSelectedIndex(1);
        inputPanel.add(toCurrency, gbc);

        // Convert Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton convertButton = new JButton("CONVERT");
        convertButton.setFont(new Font("Arial", Font.BOLD, 16));
        convertButton.setBackground(Color.BLACK);
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(true);
        convertButton.setBorder(new EmptyBorder(12, 30, 12, 30));
        convertButton.addActionListener(e -> performConversion());
        inputPanel.add(convertButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        resultArea.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMultiConvertPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Convert to multiple currencies:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(label);

        JButton multiConvertBtn = new JButton("CONVERT ALL");
        multiConvertBtn.setFont(new Font("Arial", Font.BOLD, 12));
        multiConvertBtn.setBackground(Color.BLACK);
        multiConvertBtn.setForeground(Color.WHITE);
        multiConvertBtn.setFocusPainted(false);
        multiConvertBtn.addActionListener(e -> performMultiConversion());
        topPanel.add(multiConvertBtn);

        panel.add(topPanel, BorderLayout.NORTH);

        multiConvertPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        multiConvertPanel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(multiConvertPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columns = {"Time", "Amount", "From", "To", "Result"};
        historyTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 12));
        historyTable.setRowHeight(25);
        historyTable.setGridColor(Color.BLACK);
        historyTable.setShowGrid(true);
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        historyTable.getTableHeader().setBackground(Color.BLACK);
        historyTable.getTableHeader().setForeground(Color.WHITE);
        historyTable.setSelectionBackground(Color.LIGHT_GRAY);
        historyTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton clearButton = new JButton("CLEAR HISTORY");
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> historyTableModel.setRowCount(0));
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFavoritesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Favorite Currency Pairs");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(label, BorderLayout.NORTH);

        DefaultListModel<String> favListModel = new DefaultListModel<>();
        JList<String> favList = new JList<>(favListModel);
        favList.setFont(new Font("Arial", Font.PLAIN, 14));
        favList.setSelectionBackground(Color.LIGHT_GRAY);
        favList.setSelectionForeground(Color.BLACK);
        favList.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(favList);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("ADD CURRENT PAIR");
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            String pair = fromCurrency.getSelectedItem() + " → " + toCurrency.getSelectedItem();
            if (favoritePairs.add(pair)) {
                favListModel.addElement(pair);
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("REMOVE SELECTED");
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.setBackground(Color.BLACK);
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> {
            String selected = favList.getSelectedValue();
            if (selected != null) {
                favoritePairs.remove(selected);
                favListModel.removeElement(selected);
            }
        });
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void swapCurrencies() {
        int fromIndex = fromCurrency.getSelectedIndex();
        int toIndex = toCurrency.getSelectedIndex();
        fromCurrency.setSelectedIndex(toIndex);
        toCurrency.setSelectedIndex(fromIndex);
    }

    private void performConversion() {
        try {
            double amount = Double.parseDouble(amountField.getText().replace(",", ""));
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();

            double result = convertCurrency(amount, from, to);
            double rate = exchangeRates.get(to) / exchangeRates.get(from);

            StringBuilder output = new StringBuilder();
            output.append("═══════════════════════════════════════════\n");
            output.append("  CONVERSION RESULT\n");
            output.append("═══════════════════════════════════════════\n\n");
            output.append(String.format("  %s %s = %s %s\n\n", 
                df.format(amount), from, df.format(result), to));
            output.append(String.format("  Exchange Rate: 1 %s = %.4f %s\n", from, rate, to));
            output.append(String.format("  Inverse Rate: 1 %s = %.4f %s\n\n", to, 1/rate, from));
            output.append("═══════════════════════════════════════════\n");

            resultArea.setText(output.toString());

            // Add to history
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            historyTableModel.addRow(new Object[]{
                sdf.format(new Date()),
                df.format(amount),
                from,
                to,
                df.format(result)
            });

        } catch (NumberFormatException ex) {
            resultArea.setText("ERROR: Please enter a valid number");
        }
    }

    private void performMultiConversion() {
        try {
            double amount = Double.parseDouble(amountField.getText().replace(",", ""));
            String from = (String) fromCurrency.getSelectedItem();

            multiConvertPanel.removeAll();

            for (String currency : exchangeRates.keySet()) {
                if (!currency.equals(from)) {
                    double result = convertCurrency(amount, from, currency);
                    
                    JPanel itemPanel = new JPanel(new BorderLayout(5, 5));
                    itemPanel.setBackground(Color.WHITE);
                    itemPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 1),
                        new EmptyBorder(10, 10, 10, 10)
                    ));

                    JLabel currencyLabel = new JLabel(currency);
                    currencyLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    
                    JLabel resultLabel = new JLabel(df.format(result));
                    resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                    itemPanel.add(currencyLabel, BorderLayout.WEST);
                    itemPanel.add(resultLabel, BorderLayout.EAST);

                    multiConvertPanel.add(itemPanel);
                }
            }

            multiConvertPanel.revalidate();
            multiConvertPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double convertCurrency(double amount, String from, String to) {
        double fromRate = exchangeRates.get(from);
        double toRate = exchangeRates.get(to);
        return amount * (toRate / fromRate);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CurrencyConverterApp();
        });
    }
}