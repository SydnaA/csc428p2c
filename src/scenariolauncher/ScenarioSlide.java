/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariolauncher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author sydna
 */
public class ScenarioSlide extends JPanel {

    private JFrame frame;
    private Image bgImage, toolbarImage, slidesImage,
            insertMenuImage, insertSubMenuImage, fileChooserImage,
            fileChooserConfirmImage;

    private List actionList;
    protected JLabel files[];
    protected int selectedFileIndex;

    public ScenarioSlide(JFrame frame) {

        this.frame = frame;

        //toolbar
//            Image toolbarImg = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/Sierra.jpg"));
//            Image scaledToolbarImg = toolbarImg.getScaledInstance(1024, toolbarImg.getHeight(null), Image.SCALE_SMOOTH);
//            JLabel toolbarLabel = new JLabel(new ImageIcon(scaledToolbarImg));
//            frame.add(toolbarLabel);
        frame.setSize(1280, 720);
        this.setLayout(null);

        try {
            Image bgImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/Sierra.jpg"));
            bgImage = bgImageRaw.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

            Image toolbarImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/Toolbar.png"));
            toolbarImage = toolbarImageRaw.getScaledInstance(frame.getWidth(), -1, Image.SCALE_SMOOTH);

            Image slideImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/Slides.png"));
            slidesImage = slideImageRaw.getScaledInstance(frame.getWidth(), -1, Image.SCALE_SMOOTH);

            Image insertMenuImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/InsertMenu.png"));
            insertMenuImage = insertMenuImageRaw.getScaledInstance((int) (insertMenuImageRaw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);

            Image insertSubMenuImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/InsertSubMenu.png"));
            insertSubMenuImage = insertSubMenuImageRaw.getScaledInstance((int) (insertSubMenuImageRaw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);

            Image fileChooserImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/UploadPrompt.png"));
            fileChooserImage = fileChooserImageRaw.getScaledInstance((int) (fileChooserImageRaw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);

            Image fileChooserConfirmImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/OpenButtonEnabled.png"));
            fileChooserConfirmImage = fileChooserConfirmImageRaw.getScaledInstance((int) (fileChooserConfirmImageRaw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);

//            Image finderImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/FileList.png"));
//            finderImage = finderImageRaw.getScaledInstance(-1, (int) (slideImageRaw.getHeight(null) * 0.5), Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(ScenarioSlide.class.getName()).log(Level.SEVERE, null, ex);
        }

        files = new JLabel[10];
        for (int i = 0; i < files.length; i++) {
            files[i] = new JLabel("picture_" + (i + 1) + ".png");
            files[i].setBounds(490, 164 + (17 * i), 200, 16);
            files[i].addMouseListener(new fileSelectorListener());
            files[i].setBackground(UIManager.getColor("Panel.background"));
            files[i].setOpaque(true);
            this.add(files[i]);
        }
        selectedFileIndex = -1;

        frame.add(this);
        frame.setLocationRelativeTo(null);
        this.setVisible(true);
        frame.setVisible(true);

        actionList = new ArrayList<String>();
        actionList.add("clicked insert");
        actionList.add("clicked insert submenu");
        actionList.add("clicked upload");
        actionList.add("selected imagefile");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
        g.drawImage(toolbarImage, 0, 0, null);
        g.drawImage(slidesImage, 0, toolbarImage.getHeight(null), null);

        if (actionList.get(actionList.size() - 1).equals("clicked insert")) {
            g.drawImage(insertMenuImage, 155, toolbarImage.getHeight(null) + 115, null);
        } else if (actionList.get(actionList.size() - 1).equals("clicked insert submenu")) {
            g.drawImage(insertSubMenuImage, 155, toolbarImage.getHeight(null) + 115, null);
        } else if (actionList.get(actionList.size() - 1).equals("clicked upload")) {
            g.drawImage(fileChooserImage, 325, toolbarImage.getHeight(null) + 86, null);
        } else if (actionList.get(actionList.size() - 1).equals("selected imagefile")) {
            g.drawImage(fileChooserImage, 325, toolbarImage.getHeight(null) + 86, null);
            if (selectedFileIndex > -1) {
              g.drawImage(fileChooserConfirmImage, 892, toolbarImage.getHeight(null) + 443, null);
                System.out.println("TRYING TO PAINT");
            }
        }

        //g.drawImage(finderImage, slidesImage.getWidth(null), toolbarImage.getHeight(null), null);
    }
    
    protected void redraw() {
        revalidate();
        repaint();
    }

    private class fileSelectorListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel clickedLabel = ((JLabel) e.getSource());
            clickedLabel.setBackground(Color.red);
            if (selectedFileIndex > -1) {
                files[selectedFileIndex].setBackground(UIManager.getColor("Panel.background"));
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].getBackground().equals(Color.red)) {
                    selectedFileIndex = i;
                    redraw();
                    return;
                }
            }
        }

    }
}
