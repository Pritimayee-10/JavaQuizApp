import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizAppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}

// ----------------- Login Page -----------------
class LoginPage extends JFrame implements ActionListener {
    JTextField nameField, rollField, emailField;
    JButton submitBtn;

    LoginPage() {
        setTitle("Login - Quiz App");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 240, 255));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(220, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Enter Your Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // Roll Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Enter Roll Number:"), gbc);

        gbc.gridx = 1;
        rollField = new JTextField(20);
        panel.add(rollField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Enter Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Submit Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitBtn = new JButton("Proceed to Instructions");
        styleButton(submitBtn);
        submitBtn.addActionListener(this);
        panel.add(submitBtn, gbc);

        add(panel);
        setVisible(true);
    }

    void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (!roll.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Roll number must be exactly 10 digits!");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Enter a valid email address!");
            return;
        }

        dispose();
        new InstructionPage(name);
    }
}

// ----------------- Instruction Page -----------------
class InstructionPage extends JFrame implements ActionListener {
    String userName;
    JButton startBtn;

    InstructionPage(String name) {
        userName = name;
        setTitle("Instructions - Quiz App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 240, 255));

        setLayout(new BorderLayout(10, 10));

        JTextArea instructions = new JTextArea();
        instructions.setEditable(false);
        instructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
        instructions.setBackground(new Color(245, 250, 255));
        instructions.setText(
                "Welcome " + userName + "!\n\n" +
                        "Quiz Instructions:\n" +
                        "1. Total 10 questions.\n" +
                        "2. Navigate using Previous and Next buttons.\n" +
                        "3. Click Submit after answering all questions.\n" +
                        "4. Each question carries 1 mark.\n" +
                        "5. No negative marking.\n\n" +
                        "Best of Luck!"
        );

        JScrollPane scroll = new JScrollPane(instructions);
        add(scroll, BorderLayout.CENTER);

        startBtn = new JButton("Start Quiz");
        styleButton(startBtn);
        startBtn.addActionListener(this);
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(220, 240, 255));
        btnPanel.add(startBtn);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new QuizPage();
    }
}

// ----------------- Quiz Page -----------------
class QuizPage extends JFrame implements ActionListener {
    String[] questions = {
            "Who is known as the \"Father of the Indian Constitution\"?",
            "What is the default value of a boolean variable in Java?",
            "Which SQL keyword is used to retrieve unique records?",
            "Which country won the FIFA World Cup in 2018?",
            "What is the currency of Japan?",
            "Which organization regulates monetary policy in India?",
            "Which is the largest ocean in the world?",
            "Which Indian cricketer is known as the \"God of Cricket\"?",
            "Which company developed JavaScript?",
            "Who invented the telephone?"
    };

    String[][] options = {
            {" Mahatma Gandhi", "Jawaharlal Nehru", "Dr. B.R. Ambedkar", "Sardar Patel"},
            {"True", "False", "0", "Null"},
            {"DISTINCT", "UNIQUE", "SELECT", "DIFFERENT"},
            {"Germany","Brazil","France","Argentina"},
            {"Yen", "Peso", "Rupee", "Dollar"},
            {"SEBI","NABARD","IRDA","RBI"},
            {"Indian Ocean","Pacific Ocean","Arctic Ocean","Atlantic Ocean"},
            {"Sachin Tendulkar ","MS Dhoni","Virat Kohli","Rohit Sharma"},
            {"Microsoft","IBM","Google","Netscape"},
            {"Alexander Graham Bell ", "Thomas Edison", "Nikola Tesla", "Isaac Newton"}
    };

    char[] answers = {'C', 'B', 'A','C','A','D','B','A','D','A'};
    char[] userAnswers = new char[questions.length];
    int index = 0;
    int score = 0;

    JLabel questionLabel;
    JRadioButton optA, optB, optC, optD;
    ButtonGroup optionsGroup;
    JButton nextButton, prevButton, submitButton;

    QuizPage() {
        setTitle("Quiz - Java Swing App");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(220, 240, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        optA = new JRadioButton();
        optB = new JRadioButton();
        optC = new JRadioButton();
        optD = new JRadioButton();

        for (JRadioButton btn : new JRadioButton[]{optA, optB, optC, optD}) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 15));
            btn.setBackground(mainPanel.getBackground());
        }

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optA);
        optionsGroup.add(optB);
        optionsGroup.add(optC);
        optionsGroup.add(optD);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        optionsPanel.setBackground(mainPanel.getBackground());
        optionsPanel.add(optA);
        optionsPanel.add(optB);
        optionsPanel.add(optC);
        optionsPanel.add(optD);

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");

        styleButton(prevButton);
        styleButton(nextButton);
        styleButton(submitButton);

        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        submitButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(mainPanel.getBackground());
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        mainPanel.add(questionLabel, BorderLayout.NORTH);
        mainPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadQuestion();
        setVisible(true);
    }

    void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    void loadQuestion() {
        if (index >= 0 && index < questions.length) {
            questionLabel.setText("Q" + (index + 1) + ": " + questions[index]);
            optA.setText("A) " + options[index][0]);
            optB.setText("B) " + options[index][1]);
            optC.setText("C) " + options[index][2]);
            optD.setText("D) " + options[index][3]);

            optionsGroup.clearSelection();

            if (userAnswers[index] != 0) {
                if (userAnswers[index] == 'A') optA.setSelected(true);
                else if (userAnswers[index] == 'B') optB.setSelected(true);
                else if (userAnswers[index] == 'C') optC.setSelected(true);
                else if (userAnswers[index] == 'D') optD.setSelected(true);
            }

            nextButton.setEnabled(index < questions.length - 1);
            prevButton.setEnabled(index > 0);
        }
    }

    void saveAnswer() {
        char selected = ' ';
        if (optA.isSelected()) selected = 'A';
        else if (optB.isSelected()) selected = 'B';
        else if (optC.isSelected()) selected = 'C';
        else if (optD.isSelected()) selected = 'D';
        userAnswers[index] = selected;
    }

    void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] == answers[i]) score++;
        }
    }

    void showSummary() {
        calculateScore();
        getContentPane().removeAll();
        revalidate();
        repaint();

        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.setBackground(new Color(220, 240, 255));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea summary = new JTextArea();
        summary.setEditable(false);
        summary.setFont(new Font("SansSerif", Font.PLAIN, 15));
        summary.setBackground(new Color(245, 250, 255));

        double percentage = ((double) score / questions.length) * 100;
        summary.append("🎉 Quiz Completed!\n\n");
        summary.append("✅ Total Score: " + score + "/" + questions.length + "\n");
        summary.append("📊 Percentage: " + String.format("%.2f", percentage) + "%\n\n");
        summary.append("📋 Your Answers:\n\n");

        for (int i = 0; i < questions.length; i++) {
            String correctness = (userAnswers[i] == answers[i]) ? "✔ Correct" : "❌ Wrong";
            String yourAns = (userAnswers[i] == 0) ? "No Answer" : String.valueOf(userAnswers[i]);
            summary.append("Q" + (i + 1) + ": " + questions[i] + "\n");
            summary.append("Your Answer: " + yourAns + " | Correct Answer: " + answers[i] + " | " + correctness + "\n\n");
        }

        JScrollPane scrollPane = new JScrollPane(summary);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        JButton exitBtn = new JButton("Exit");
        styleButton(exitBtn);
        exitBtn.addActionListener(e -> System.exit(0));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(resultPanel.getBackground());
        btnPanel.add(exitBtn);

        JLabel thankYouLabel = new JLabel("Thank you for participating!", SwingConstants.CENTER);
        thankYouLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        thankYouLabel.setForeground(new Color(0, 80, 180));

        resultPanel.add(thankYouLabel, BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.add(btnPanel, BorderLayout.SOUTH);

        add(resultPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            saveAnswer();
            if (index < questions.length - 1) {
                index++;
                loadQuestion();
            }
        } else if (e.getSource() == prevButton) {
            saveAnswer();
            if (index > 0) {
                index--;
                loadQuestion();
            }
        } else if (e.getSource() == submitButton) {
            saveAnswer();
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to submit?", "Submit Quiz", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                showSummary();
            }
        }
    }
}
