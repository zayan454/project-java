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
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD (US DOLLARS)", 1.0);
        exchangeRates.put("EUR (EURO)", 0.92);
        exchangeRates.put("GBP (BRITISH POUND)", 0.79);
        exchangeRates.put("JPY (JAPANESE YEN)", 149.50);
        exchangeRates.put("CAD (CANADIAN DOLLAR)", 1.36);
        exchangeRates.put("AUD (AUSTRALIAN DOLLAR)", 1.53);
        exchangeRates.put("CHF (SWISS FRANC)", 0.88);
        exchangeRates.put("CNY (CHINESE YUAN)", 7.24);
        exchangeRates.put("INR (INDIAN RUPEE)", 83.12);
        exchangeRates.put("MXN (MEXICAN PESO)", 17.08);
        exchangeRates.put("BRL (BRAZILIAN REAL)", 4.98);
        exchangeRates.put("ZAR (SOUTH AFRICAN RAND)", 18.65);
        exchangeRates.put("KRW (SOUTH KOREAN WON)", 1338.50);
        exchangeRates.put("SGD (SINGAPORE DOLLAR)", 1.34);
        exchangeRates.put("NZD (NEW ZEALAND DOLLAR)", 1.67);
        exchangeRates.put("SAR (SAUDI RIYAL)", 3.75);
        exchangeRates.put("PKR (PAKISTANI RUPEE)", 277.00);
        exchangeRates.put("BDT (BANGLADESHI TAKA)", 118.50);
        exchangeRates.put("LKR (SRI LANKAN RUPEE)", 304.00);
        exchangeRates.put("NPR (NEPALESE RUPEE)", 132.60);
        exchangeRates.put("THB (THAI BAHT)", 36.60);
        exchangeRates.put("MYR (MALAYSIAN RINGGIT)", 4.77);
        exchangeRates.put("IDR (INDONESIAN RUPIAH)", 15700.00);
        exchangeRates.put("VND (VIETNAMESE DONG)", 24400.00);
        exchangeRates.put("ILS (ISRAELI SHEKEL)", 3.85);
        exchangeRates.put("QAR (QATARI RIYAL)", 3.64);
        exchangeRates.put("KWD (KUWAITI DINAR)", 0.31);
        exchangeRates.put("BHD (BAHRAINI DINAR)", 0.38);
        exchangeRates.put("OMR (OMANI RIAL)", 0.39);
        exchangeRates.put("JOD (JORDANIAN DINAR)", 0.71);
        exchangeRates.put("PLN (POLISH ZLOTY)", 4.04);
        exchangeRates.put("CZK (CZECH KORUNA)", 23.30);
        exchangeRates.put("HUF (HUNGARIAN FORINT)", 364.00);
        exchangeRates.put("RON (ROMANIAN LEU)", 4.57);
        exchangeRates.put("BGN (BULGARIAN LEV)", 1.80);
        exchangeRates.put("HRK (CROATIAN KUNA)", 7.05);
        exchangeRates.put("UAH (UKRAINIAN HRYVNIA)", 40.20);
        exchangeRates.put("ISK (ICELANDIC KRONA)", 138.60);
        exchangeRates.put("EGP (EGYPTIAN POUND)", 48.00);
        exchangeRates.put("KES (KENYAN SHILLING)", 130.50);
        exchangeRates.put("NGN (NIGERIAN NAIRA)", 1560.00);
        exchangeRates.put("GHS (GHANAIAN CEDI)", 15.30);
        exchangeRates.put("MAD (MOROCCAN DIRHAM)", 10.00);
        exchangeRates.put("TND (TUNISIAN DINAR)", 3.10);
        exchangeRates.put("ETB (ETHIOPIAN BIRR)", 114.00);
        exchangeRates.put("ARS (ARGENTINE PESO)", 960.00);
        exchangeRates.put("CLP (CHILEAN PESO)", 950.00);
        exchangeRates.put("COP (COLOMBIAN PESO)", 4090.00);
        exchangeRates.put("PEN (PERUVIAN SOL)", 3.72);
        exchangeRates.put("UYU (URUGUAYAN PESO)", 41.00);
        exchangeRates.put("VEF (VENEZUELAN BOLIVAR)", 36.00);
        exchangeRates.put("DOP (DOMINICAN PESO)", 59.00);
        exchangeRates.put("CRC (COSTA RICAN COLON)", 524.00);
        exchangeRates.put("FJD (FIJIAN DOLLAR)", 2.26);
        exchangeRates.put("PGK (PAPUA NEW GUINEA KINA)", 3.78);
        exchangeRates.put("SBD (SOLOMON ISLANDS DOLLAR)", 8.35);
        exchangeRates.put("TOP (TONGAN PA’ANGA)", 2.40);
        exchangeRates.put("WST (SAMOAN TALA)", 2.80);
        exchangeRates.put("VUV (VANUATU VATU)", 119.00);
        exchangeRates.put("XOF (WEST AFRICAN CFA FRANC)", 604.00);
        exchangeRates.put("XAF (CENTRAL AFRICAN CFA FRANC)", 604.00);
        exchangeRates.put("XCD (EAST CARIBBEAN DOLLAR)", 2.70);
        exchangeRates.put("BSD (BAHAMIAN DOLLAR)", 1.00);
        exchangeRates.put("BBD (BARBADIAN DOLLAR)", 2.00);
        exchangeRates.put("TTD (TRINIDAD & TOBAGO DOLLAR)", 6.80);
        exchangeRates.put("JMD (JAMAICAN DOLLAR)", 156.00);
        exchangeRates.put("KYD (CAYMAN ISLANDS DOLLAR)", 0.82);
        exchangeRates.put("BZD (BELIZE DOLLAR)", 2.00);
        exchangeRates.put("ANG (NETHERLANDS ANTILLEAN GUILDER)", 1.79);
        exchangeRates.put("HTG (HAITIAN GOURDE)", 132.00);
        exchangeRates.put("SRD (SURINAMESE DOLLAR)", 37.50);
        exchangeRates.put("MWK (MALAWIAN KWACHA)", 1740.00);
        exchangeRates.put("ZMW (ZAMBIAN KWACHA)", 26.00);
        exchangeRates.put("MUR (MAURITIAN RUPEE)", 46.00);
        exchangeRates.put("SCR (SEYCHELLES RUPEE)", 14.00);
    }

    private void initializeUI() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.GRAY);

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.GRAY);
        tabbedPane.setFont(new Font("Poppins", Font.BOLD, 13));

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
        setIconImage(new ImageIcon("icon.jpeg").getImage());
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("CURRENCY CONVERTER");
        title.setFont(new Font("Poppins", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm");
        JLabel dateLabel = new JLabel(sdf.format(new Date()));
        dateLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        dateLabel.setForeground(Color.LIGHT_GRAY);

        JPanel textPanel = new JPanel(new BorderLayout(0, 5));
        textPanel.setOpaque(false);
        textPanel.add(title, BorderLayout.NORTH);
        textPanel.add(dateLabel, BorderLayout.SOUTH);

        header.add(textPanel, BorderLayout.WEST);
        return header;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.BLACK);
            }

            public void mousePressed(MouseEvent evt) {
                button.setBackground(new Color(60, 60, 60));
            }

            public void mouseReleased(MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30));
            }
        });

        return button;
    }

    private JPanel createConverterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                new EmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        amountField = new JTextField("100");
        amountField.setFont(new Font("Poppins", Font.PLAIN, 16));
        amountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                new EmptyBorder(8, 10, 8, 10)));
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        inputPanel.add(fromLabel, gbc);

        gbc.gridx = 1;
        String[] currencies = exchangeRates.keySet().toArray(new String[0]);
        Arrays.sort(currencies);
        fromCurrency = new JComboBox<>(currencies);
        fromCurrency.setFont(new Font("Poppins", Font.PLAIN, 14));
        fromCurrency.setBackground(Color.WHITE);
        inputPanel.add(fromCurrency, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton swapButton = createStyledButton("SWAP");
        swapButton.setFont(new Font("Poppins", Font.BOLD, 16));
        swapButton.addActionListener(e -> swapCurrencies());
        inputPanel.add(swapButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        inputPanel.add(toLabel, gbc);

        gbc.gridx = 1;
        toCurrency = new JComboBox<>(currencies);
        toCurrency.setFont(new Font("Poppins", Font.PLAIN, 14));
        toCurrency.setBackground(Color.WHITE);
        toCurrency.setSelectedIndex(1);
        inputPanel.add(toCurrency, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton convertButton = createStyledButton("CONVERT");
        convertButton.setFont(new Font("Poppins", Font.BOLD, 16));
        convertButton.addActionListener(e -> performConversion());
        inputPanel.add(convertButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Poppins", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                new EmptyBorder(15, 15, 15, 15)));
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
        label.setFont(new Font("Poppins", Font.BOLD, 14));
        topPanel.add(label);

        JButton multiConvertBtn = createStyledButton("CONVERT ALL");
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Poppins", Font.PLAIN, 12));
        historyTable.setRowHeight(25);
        historyTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton clearButton = createStyledButton("CLEAR HISTORY");
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
        label.setFont(new Font("Poppins", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        DefaultListModel<String> favListModel = new DefaultListModel<>();
        JList<String> favList = new JList<>(favListModel);
        favList.setFont(new Font("Poppins", Font.PLAIN, 14));
        favList.setSelectionBackground(Color.LIGHT_GRAY);
        favList.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(favList);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = createStyledButton("ADD CURRENT PAIR");
        addButton.addActionListener(e -> {
            String pair = fromCurrency.getSelectedItem() + " → " + toCurrency.getSelectedItem();
            if (favoritePairs.add(pair)) favListModel.addElement(pair);
        });
        buttonPanel.add(addButton);

        JButton removeButton = createStyledButton("REMOVE SELECTED");
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
            output.append(String.format("  Inverse Rate: 1 %s = %.4f %s\n\n", to, 1 / rate, from));
            output.append("═══════════════════════════════════════════\n");

            resultArea.setText(output.toString());

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
                            new EmptyBorder(10, 10, 10, 10)));

                    JLabel currencyLabel = new JLabel(currency);
                    currencyLabel.setFont(new Font("Poppins", Font.BOLD, 16));

                    JLabel resultLabel = new JLabel(df.format(result));
                    resultLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
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