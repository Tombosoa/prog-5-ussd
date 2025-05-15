package com.prog5.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.prog5.menu.engine.USSDEngine;
import com.prog5.menu.action.Action;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.util.List;

public class USSDgui extends JFrame {
    private final USSDEngine engine;
    private final JTextArea displayArea;
    private final JTextField inputField;

    public USSDgui() {
        engine = new USSDEngine();
        setTitle("Simulateur USSD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        final JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputPanel.add(inputField, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        final JButton sendButton = new JButton("Send");
        final JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);

        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processInput();
                }
            }
        });

        sendButton.addActionListener(e -> processInput());

        cancelButton.addActionListener(e -> System.exit(0));

        updateDisplay();
    }

    private void processInput() {
        final String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            engine.processInput(input);
            inputField.setText("");
            updateDisplay();
        }
    }

    private void updateDisplay() {
        final StringBuilder builder = new StringBuilder("Yas et Moi \n \n");

        final List<Action> currentMenu = engine.getCurrentMenu();

        for (int i = 0; i < currentMenu.size(); i++) {
            final Action action = currentMenu.get(i);
            final String title = action.getTitle();

            if ("Pejy manaraka".equals(title) &&
                    !currentMenu.isEmpty() &&
                    currentMenu.get(0).getTitle().equals("MVOLA")) {
                builder.append("0 ").append(title).append("\n");
            } else if ("Pejy aloha".equals(title) && !currentMenu.isEmpty() &&
                    currentMenu.get(0).getTitle().equals("Mon identité")) {
                builder.append("00 ").append(title).append("\n");
            } else {
                builder.append((i + 1)).append(" ").append(title).append("\n");
            }
        }

        displayArea.setText(builder.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final USSDgui app = new USSDgui();
            app.setVisible(true);
        });
    }
}
