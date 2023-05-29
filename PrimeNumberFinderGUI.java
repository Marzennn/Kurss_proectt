import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumberFinderGUI extends JFrame {
    private JTextField startTextField;
    private JTextField endTextField;
    private JTextArea outputTextArea;
    private JButton findPrimesButton;
    private JButton findPrimesSieveButton;

    public PrimeNumberFinderGUI() {
        setTitle("Пошук простих чисел");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 10, 0);

        JLabel titleLabel = new JLabel("Пошук простих чисел");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20));
        titleLabel.setForeground(Color.black);
        mainPanel.add(titleLabel, constraints);

        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 10, 0);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel startLabel = new JLabel("Початок інтервалу:");
        startLabel.setFont(startLabel.getFont().deriveFont(Font.BOLD, 17));
        startTextField = new JTextField(10);
        JLabel endLabel = new JLabel("Кінець інтервалу:");
        endTextField = new JTextField(10);
        endLabel.setFont(endLabel.getFont().deriveFont(Font.BOLD, 17));
        findPrimesButton = new JButton("Знайти прості числа");
        findPrimesButton.setPreferredSize(new Dimension(350, 40));
        findPrimesSieveButton = new JButton("Знайти прості числа (решето Ератосфена)");
        findPrimesSieveButton.setPreferredSize(new Dimension(450, 40));

        inputPanel.add(startLabel);
        inputPanel.add(startTextField);
        inputPanel.add(endLabel);
        inputPanel.add(endTextField);

        mainPanel.add(inputPanel, constraints);

        constraints.gridy = 2;
        constraints.insets = new Insets(0, 0, 0, 0);

        outputTextArea = new JTextArea(1, 60);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(false);
        outputTextArea.setWrapStyleWord(false);
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 17));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        mainPanel.add(scrollPane, constraints);

        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 0, 0);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        findPrimesButton.setFont(findPrimesButton.getFont().deriveFont(Font.BOLD, 17));
        buttonPanel.add(findPrimesButton);
        findPrimesSieveButton.setFont(findPrimesSieveButton.getFont().deriveFont(Font.BOLD, 17));
        buttonPanel.add(findPrimesSieveButton);

        mainPanel.add(buttonPanel, constraints);

        findPrimesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPrimes();
            }
        });

        findPrimesSieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPrimesSieve();
            }
        });

        add(mainPanel);
    }

    private void findPrimes() {
        int start = Integer.parseInt(startTextField.getText());
        int end = Integer.parseInt(endTextField.getText());

        List<Integer> primes = findPrimes(start, end);

        outputTextArea.setText("");
        outputTextArea.append("Прості числа між " + start + " та " + end + ": ");
        for (int prime : primes) {
            outputTextArea.append(String.valueOf(prime) + " ");
        }
    }

    private void findPrimesSieve() {
        int start = Integer.parseInt(startTextField.getText());
        int end = Integer.parseInt(endTextField.getText());

        List<Integer> primes = findPrimesSieve(start, end);

        outputTextArea.setText("");
        outputTextArea.append("Прості числа між " + start + " та " + end + " (решето Ератосфена): ");
        for (int prime : primes) {
            outputTextArea.append(String.valueOf(prime) + " ");
        }
    }

    private List<Integer> findPrimes(int start, int end) {
        List<Integer> primes = new ArrayList<>();

        for (int number = start; number <= end; number++) {
            if (isPrime(number)) {
                primes.add(number);
            }
        }

        return primes;
    }

    private List<Integer> findPrimesSieve(int start, int end) {
        boolean[] isComposite = new boolean[end + 1];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i * i <= end; i++) {
            if (!isComposite[i]) {
                for (int j = i * i; j <= end; j += i) {
                    isComposite[j] = true;
                }
            }
        }

        for (int i = start; i <= end; i++) {
            if (!isComposite[i]) {
                primes.add(i);
            }
        }

        return primes;
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        if (Math.sqrt(number) % 1 == 0) {
            return false;
        }

        int a = (int) Math.ceil(Math.sqrt(number));
        int bSquared = a * a - number;
        int b = (int) Math.sqrt(bSquared);

        while (b * b != bSquared) {
            a++;
            bSquared = a * a - number;
            b = (int) Math.sqrt(bSquared);
            if (a > number) {
                return false;
            }
        }

        int factor1 = a + b;
        int factor2 = a - b;

        return factor1 == 1 || factor1 == number || factor2 == 1 || factor2 == number;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrimeNumberFinderGUI gui = new PrimeNumberFinderGUI();
                gui.setVisible(true);
            }
        });
    }
}