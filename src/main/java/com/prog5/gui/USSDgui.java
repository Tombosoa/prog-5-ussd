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

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));

        final JPanel inputPanel = new JPanel(new BorderLayout());
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

        sendButton.addActionListener(e -> processInput());
        cancelButton.addActionListener(e -> System.exit(0));

        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processInput();
                }
            }
        });

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
        final StringBuilder builder = new StringBuilder("Yas et Moi\n\n");

        final List<Action> menu = engine.currentMenu;
        final int startIndex = engine.currentPage * USSDEngine.PAGE_SIZE;
        final int endIndex = Math.min(startIndex + USSDEngine.PAGE_SIZE, menu.size());

        for (int i = startIndex; i < endIndex; i++) {
            builder.append((i + 1)).append(" ").append(menu.get(i).getTitle()).append("\n");
        }

        if (endIndex < menu.size()) {
            builder.append("0  Page suivante\n");
        }

        if (engine.currentPage > 0) {
            builder.append("00 Page précédente\n");
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
