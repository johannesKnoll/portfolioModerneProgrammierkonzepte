package ToDo;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NewTaskLayout extends JPanel {

    ReadJSON readJSON = new ReadJSON("tasks.json");

    public TitledBorder getTitledBorder(String text){
        LineBorder roundedLineBorder = new LineBorder(Color.black, 1, true);
        TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, text);

        return roundedTitledBorder;
    }

    public NewTaskLayout(){

         JLabel headline = new JLabel("Neue Aufgabe hinzufügen");
         headline.setBorder(new EmptyBorder(10, 10, 5, 10));

         JPanel all = new JPanel(new BorderLayout());

         JPanel task = new JPanel();
         task.setLayout(new BorderLayout());
         JTextField jTextField = new JTextField();
         jTextField.setMargin(new Insets(5, 3, 5, 0));
         task.add(jTextField, BorderLayout.CENTER);
         task.setBorder(getTitledBorder("Aufgabe"));

         JPanel description = new JPanel();
         description.setLayout(new BorderLayout());
         JTextArea textArea = new JTextArea();
         Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
         Border empty = new EmptyBorder(10, 10, 10, 10);
         CompoundBorder border = new CompoundBorder(line, empty);
         textArea.setBorder(border);
         description.add(textArea, BorderLayout.CENTER);
         description.setBorder(getTitledBorder("Description:"));
         description.setSize(new Dimension(250, 300));

         JDateChooser jDateChooser = new JDateChooser();
         jDateChooser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
         jDateChooser.setBorder(new EmptyBorder(10, 10, 10, 10));
         jDateChooser.setDateFormatString("dd.MM.yyyy");
         description.add(jDateChooser, BorderLayout.SOUTH);

         JButton jButton = new JButton("Hinzufügen");
         RoundedBorder roundedBorder = new RoundedBorder(10);
         Border emptyBorder = new EmptyBorder(2, 2, 2, 2);
         CompoundBorder compoundBorder = new CompoundBorder(roundedBorder, emptyBorder);
         jButton.setBorder(compoundBorder);
         jButton.setForeground(Color.WHITE);
         jButton.setBackground(Color.black);

         MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setForeground(Color.black);
                jButton.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setForeground(Color.white);
                jButton.setBackground(Color.black);
            }
         };

         jButton.addMouseListener(mouseAdapter);

         ActionListener actionListener = (ActionEvent actionEvent) ->{
            String taskInput = jTextField.getText();
            if(taskInput.equals("")){
                taskInput = "Keine Aufgabe angegeben";
            }
            String descriptionInput = textArea.getText();
            if(descriptionInput.equals("")){
                descriptionInput = "Keine Beschreibung angegeben";
            }
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dueDate = dateFormat.format(jDateChooser.getDate());
            if(dueDate == null){
                dueDate = "";
            }
            readJSON.addTask(taskInput, descriptionInput, dueDate);
            TasksLayout tasksLayout = new TasksLayout();
            Layout topFrame = (Layout) SwingUtilities.getWindowAncestor(this);
            topFrame.setTasksLayout(tasksLayout);
            jTextField.setText("");
            textArea.setText("");
            readJSON.flushJSON();
         };

         jButton.addActionListener(actionListener);

         all.add(task, BorderLayout.NORTH);
         all.add(description, BorderLayout.CENTER);
         all.add(jButton, BorderLayout.SOUTH);
         all.setBorder(new EmptyBorder(10, 10, 10, 10));



         setLayout(new BorderLayout());
         add(headline, BorderLayout.NORTH);
         add(all, BorderLayout.CENTER);
         setVisible(true);
    }

    public static void main(String[] args) {
        new NewTaskLayout();
    }
}
