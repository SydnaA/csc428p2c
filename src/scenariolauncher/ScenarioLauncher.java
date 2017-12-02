/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariolauncher;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author sydna
 */
public class ScenarioLauncher implements ActionListener {
    
    private JFrame frame;
    private JPanel panel;
    final String[] scenarioArr = { "S1: Slides", "S2: Title Extraction", "S3: TV series" };
    
    public ScenarioLauncher() {
        frame = new JFrame();
        panel = new JPanel(new GridLayout(3,0));
        JLabel label = new JLabel("CSC428 Scenario Launcher", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        JComboBox jcb = new JComboBox(scenarioArr);
        JButton button = new JButton("Start");
        button.setActionCommand("start");
        button.addActionListener(this);
        panel.add(label);
        panel.add(jcb);
        panel.add(button);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ScenarioLauncher();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start")) {
            panel.setVisible(false);
            frame.remove(panel);
            panel=null;
            frame.setVisible(false);
            new ScenarioSlide(frame);
        }
    }
    
}
