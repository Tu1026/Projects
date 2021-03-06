package ui.gui;

import exception.NameNotValidException;
import model.Goal;

import javax.swing.*;
import java.awt.*;

//menu for managing goals

public class GoalsPanel extends JPanel {
    private GridBagConstraints gc = new GridBagConstraints();
    private JTextField nameField = new JTextField(10);
    private JTextField costField = new JTextField(10);
    private JTextField desireField = new JTextField(10);

    //EFFECTS: construct the menu for managing goals
    public GoalsPanel() {
        Dimension size = getPreferredSize();
        size.width = 400;
        setPreferredSize(size);
        setBorder(BorderFactory.createTitledBorder("Manage Goals"));
        setLayout(new GridBagLayout());
        makeLabels();
        makeFields();
        makeAddToGoalsButton();
        makeTextArea();
        makeBackToMenuButton();
        makeNewDeleteButton();
        makeNewDeleteFirstButton();
    }

    //MODIFIES: this
    //EFFECTS: make a button that takes user back to the main menu
    private void makeBackToMenuButton() {
        JButton b2 = new JButton("Go back to main menu");
        b2.setToolTipText("Hit this button to go back to menu");
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        gc.gridx = 1;
        gc.gridy = 5;
        add(b2, gc);
        b2.addActionListener(e -> MainFrame.getInstance().changePanel(new MainPanel()));
    }

    //MODIFIES: this
    //EFFECTS: make a not to the user about the instruction of using this app
    private void makeTextArea() {
        final JTextArea notice = new JTextArea();
        notice.append("You need to fill in all three text columns before using"
                + "the add button" + "\n" + "Desire point has to be a whole number between 1-10 \n"
                + "To delete just input the name of goal you want to delete and hit the delete button \n"
                + "***Most Importantly don't forget to SAVE your progress with the upper left menu");
        Font font = new Font("Times new Roman", Font.BOLD, 20);
        notice.setFont(font);
        notice.setLineWrap(true);
        notice.setWrapStyleWord(true);
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 2;
        gc.gridx = 1;
        gc.gridy = 4;
        add(notice, gc);

    }

    //MODIFIES: this
    //EFFECTS: make a button that when hit takes user inputs to make a new goal
    private void makeAddToGoalsButton() {
        JButton b1 = new JButton("Add to Goals");
        b1.setToolTipText("Hit this button after inputting a name, cost, and desire point to add a new goal");
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 2;
        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(b1, gc);
        b1.addActionListener(e -> {
            if (!nameField.getText().equals("") && !costField.getText().equals("")
                    && !desireField.getText().equals("")) {
                String nameOfGoal = nameField.getText();
                try {
                    Double costOfGoal = Double.parseDouble(costField.getText());
                    Integer desireOfGoal = Integer.parseInt(desireField.getText());
                    makeNewGoals(nameOfGoal, costOfGoal, desireOfGoal);
                    MainFrame.getInstance().goalsState();
                } catch (NumberFormatException e2) {
                    errorCostMessage();
                }
            }
        });
    }

    //EFFECTS: pop up an error message about inputing invalid type of cost
    public void errorCostMessage() {
        String message = "Only positive numeric number for cost!!!";
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //MODIFIES: this
    //EFFECTS: make text columns to take user input
    private void makeFields() {
        gc.anchor = GridBagConstraints.LINE_START;
        gc.weighty = 2;
        gc.weightx = 2;
        gc.gridx = 1;
        gc.gridy = 0;
        add(nameField, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(costField, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(desireField, gc);
    }

    //MODIFIES: this
    //EFFECTS: make labels to the text fields
    public void makeLabels() {
        JLabel nameOfItem = new JLabel("Name of Goal: ");
        JLabel costOfGoal = new JLabel("Cost of Goal: ");
        JLabel desireOfGoal = new JLabel("Desire for Goal (1-10): ");
        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 2;
        gc.weighty = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        add(nameOfItem, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(costOfGoal, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(desireOfGoal, gc);
    }


    //MODIFIES: MainFrame
    //EFFECTS: add a new goal to list of goal in the Main Frame file
    public void makeNewGoals(String n, Double c, Integer d) {
        Goal newGoal = new Goal(n, c, d);
        GuiData.getGoals().addToGoals(newGoal);
    }

    //MODIFIES: this, MainFrame
    //EFFECTS: adda button that deletes a given goal when given input by user
    public void makeNewDeleteButton() {
        JButton deleteButton = new JButton("Delete a Goal");
        deleteButton.setToolTipText("Hit this button after inputting the name of the goal that you want to delete"
                + "in the name field");
        gc.weighty = 0.5;
        gc.weightx = 0.5;
        gc.gridx = 0;
        gc.gridy = 3;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridheight = 2;
        add(deleteButton, gc);
        deleteButton.addActionListener(e -> {
            if (!nameField.getText().equals("")) {
                String remover = nameField.getText();
                try {
                    GuiData.getGoals().removeGivenGoal(remover);
                    MainFrame.getInstance().goalsState();
                } catch (NameNotValidException e2) {
                    noSuchGoalError();
                }
            }
        });
    }

    //EFFECTS: pop up an error and tell user could not find such goal with given name
    public void noSuchGoalError() {
        String message = "No goal with that name was found";
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //MODIFIES: this, MainFrame
    //EFFECTS: remove the first goal on the list of goals
    public void makeNewDeleteFirstButton() {
        JButton deleteFirstButton = new JButton("Delete the first Goal");
        deleteFirstButton.setToolTipText("Delete the first goal in the goals");
        gc.weighty = 0.5;
        gc.weightx = 0.5;
        gc.gridx = 0;
        gc.gridy = 5;
        gc.fill = GridBagConstraints.BOTH;
        add(deleteFirstButton, gc);
        deleteFirstButton.addActionListener(e -> {
            try {
                GuiData.getGoals().removeIthGoal(1);
                MainFrame.getInstance().goalsState();
            } catch (IndexOutOfBoundsException ex) {
                //do nothing
            }
        });
    }
}
