/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariolauncher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ScenarioSlide extends JPanel implements MouseListener {

    final String[] pictureNames = {"Abstract Shapes.jpg", "Abstract.jpg",
        "Antelope Canyon.jpg", "Bahamas Aerial.jpg", "Blue Pond.jpg",
        "Color Burst 1.jpg", "Color Burst 2.jpg", "Color Burst 3.jpg",
        "Death Valley.jpg", "Desert.jpg"};

    private JFrame frame;
    private Image bgImage, toolbarImage, slidesImage,
            insertMenuImage, insertSubMenuImage, fileChooserImage,
            fileChooserConfirmImage, slideMenuImage, iconImage,
            iconMenuImage, iconMenuRecordingImage, iconImageRecording;

    private List actionList;
    protected JLabel files[];
    protected int selectedFileIndex;

    private List<Slide> slideList;
    private int slideFocused;

    private List<Rectangle> activeButton;
    private Map<Rectangle, String> rectActionMap;

    private boolean isRecording;
    private List<State> stateList;

    /*
    * checkpoints states
    * new slide
    * picture insert
     */
    public ScenarioSlide(JFrame frame) {

        this.frame = frame;
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

            Image slideMenuImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/SlideMenu.png"));
            slideMenuImage = slideMenuImageRaw.getScaledInstance((int) (slideMenuImageRaw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);

            Image iconImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/icon_default.png"));
            iconImage = iconImageRaw.getScaledInstance((int) (iconImageRaw.getWidth(null) * 0.14), -1, Image.SCALE_SMOOTH);

            Image iconImageRecordingRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/icon_recording.png"));
            iconImageRecording = iconImageRecordingRaw.getScaledInstance((int) (iconImageRecordingRaw.getWidth(null) * 0.14), -1, Image.SCALE_SMOOTH);

            Image iconMenuImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/icon_menu.png"));
            iconMenuImage = iconMenuImageRaw.getScaledInstance((int) (iconMenuImageRaw.getWidth(null) * 0.14), -1, Image.SCALE_SMOOTH);

            Image iconMenuRecordingImageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/icon_menu_recording.png"));
            iconMenuRecordingImage = iconMenuRecordingImageRaw.getScaledInstance((int) (iconMenuRecordingImageRaw.getWidth(null) * 0.14), -1, Image.SCALE_SMOOTH);

        } catch (IOException ex) {
            Logger.getLogger(ScenarioSlide.class.getName()).log(Level.SEVERE, null, ex);
        }

        frame.add(this);
        frame.setLocationRelativeTo(null);
        this.addMouseListener(this);
        this.setVisible(true);
        frame.setVisible(true);

        actionList = new ArrayList<String>();
        actionList.add("base");
//        actionList.add("clicked insert");
//        actionList.add("clicked insert submenu");
//        actionList.add("clicked upload");
//        actionList.add("clicked open");

        activeButton = new ArrayList<Rectangle>();
        rectActionMap = new HashMap<Rectangle, String>();

        slideList = new ArrayList<Slide>();
        slideList.add(new Slide(null));
        slideFocused = 0;

        files = new JLabel[10];
        for (int i = 0; i < files.length; i++) {
            files[i] = new JLabel(pictureNames[i]);
            files[i].setBounds(490, 164 + (17 * i), 200, 16);
            files[i].addMouseListener(new fileSelectorListener());
            files[i].setBackground(UIManager.getColor("Panel.background"));
            files[i].setOpaque(true);
//            this.add(files[i]);
        }
        selectedFileIndex = -1;

        stateList = new ArrayList<State>();

        // magical part
        // up to down
        // starting image index
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
        g.drawImage(toolbarImage, 0, 0, null);
        g.drawImage(slidesImage, 0, toolbarImage.getHeight(null), null);
        activeButton.clear();

        //paint slide 6 max
        //create icon
        for (int i = 0; i < slideList.size(); i++) {
            g.drawImage(slideList.get(i).getSlideIcon(), 38, 200 + (90 * i), null);
            if (this.slideFocused != i) {
                g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                activeButton.add(new Rectangle(38, 200 + (90 * i), slideList.get(i).getSlideIcon().getWidth(null), slideList.get(i).getSlideIcon().getHeight(null)));
                g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                rectActionMap.put(activeButton.get(activeButton.size() - 1), "switch slide");
            }
        }
        //create main slide
        g.drawImage(slideList.get(slideFocused).getSlideMainImage(), 300, 210, null);

        //draw icon
        if (!actionList.get(actionList.size() - 1).equals("clicked icon")) {
            if (this.isRecording) {
                g.drawImage(iconImageRecording, 915, 0, null);
            } else {
                g.drawImage(iconImage, 915, 0, null);
            }
            activeButton.add(new Rectangle(915, 0, 30, 19));
            g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
            g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
            rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked icon");
        }

        if (actionList.get(actionList.size() - 1).equals("base")) {
            activeButton.add(new Rectangle(155, 140, 40, 15));
            g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
            g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
            rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked insert");

            activeButton.add(new Rectangle(196, 140, 40, 15));
            g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
            g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
            rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked slide");
        } else {
            if (actionList.get(actionList.size() - 1).equals("clicked insert")) {
                g.drawImage(insertMenuImage, 155, toolbarImage.getHeight(null) + 115, null); //315
                activeButton.add(new Rectangle(155, 210, 185, 25));
                g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked insert submenu");
            } else if (actionList.get(actionList.size() - 1).equals("clicked insert submenu")) {
                g.drawImage(insertSubMenuImage, 155, toolbarImage.getHeight(null) + 115, null);
                activeButton.add(new Rectangle(345, 212, 165, 33));
                g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked upload");
            } else if (actionList.get(actionList.size() - 1).equals("clicked upload")) {
                g.drawImage(fileChooserImage, 325, toolbarImage.getHeight(null) + 86, null);
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getParent() != this) {
                        this.add(files[i]);
                    }
                }
                if (selectedFileIndex > -1) {
                    g.drawImage(fileChooserConfirmImage, 892, toolbarImage.getHeight(null) + 443, null);
                    activeButton.add(new Rectangle(892, 463, 69, 20));
                    g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                    g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                    rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked open");
                }
            } else if (actionList.get(actionList.size() - 1).equals("clicked slide")) {
                g.drawImage(slideMenuImage, 196, toolbarImage.getHeight(null) + 115, null);
                activeButton.add(new Rectangle(200, 160, 240, 30));
                g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked new slide");
            } else if (actionList.get(actionList.size() - 1).equals("clicked icon")) {
                if (this.isRecording) {
                    g.drawImage(iconMenuRecordingImage, 905, 0, null);
                    activeButton.add(new Rectangle(910, 20, 140, 30));
                    g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                    g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                    rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked stop record");
                } else {
                    g.drawImage(iconMenuImage, 905, 0, null);
                    activeButton.add(new Rectangle(910, 20, 140, 30));
                    g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                    g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                    rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked record");
                    if (!stateList.isEmpty()) {
                        activeButton.add(new Rectangle(910, 50, 140, 30));
                        g.setColor(newColorWithAlpha(Color.GRAY, 0.2));
                        g.fillRect(activeButton.get(activeButton.size() - 1).x, activeButton.get(activeButton.size() - 1).y, activeButton.get(activeButton.size() - 1).width, activeButton.get(activeButton.size() - 1).height);
                        rectActionMap.put(activeButton.get(activeButton.size() - 1), "clicked play");
                    }
                }
            }
        }
    }

    private Color newColorWithAlpha(Color original, Double alpha) {
        return new Color(original.getRed(), original.getGreen(), original.getBlue(), (int) (255 * alpha));
    }

    protected void redraw() {
        revalidate();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println(e.getPoint());
        for (Rectangle rect : activeButton) {
            if (rect.contains(e.getPoint())) {
                actionList.add(rectActionMap.get(rect));
                if (rectActionMap.get(rect).equals("clicked open")) {
                    if (this.isRecording) {
                        stateList.add(new State("clicked open", selectedFileIndex, this.slideFocused));
                    }
                    slideList.get(slideFocused).setSlideImage(pictureNames[selectedFileIndex]);
                    files[selectedFileIndex].setBackground(UIManager.getColor("Panel.background"));
                    for (int i = 0; i < files.length; i++) {
                        this.remove(files[i]);
                        selectedFileIndex = -1;
                    }
                    actionList.add("base");
                } else if (rectActionMap.get(rect).equals("clicked new slide")) {
                    if (this.isRecording) {
                        stateList.add(new State("clicked new slide", this.slideList.size()));
                    }
                    addNewSlide();
                } else if (rectActionMap.get(rect).equals("switch slide")) {
                    this.slideFocused = (int) (rect.y - 200) / 90;
//                    if (this.isRecording) {
//                        stateList.add(new State("switch slide", this.slideFocused));
//                    }
                    actionList.add("base");
                } else if (rectActionMap.get(rect).equals("clicked record")) {
                    stateList.clear();
                    this.isRecording = true;
                    actionList.add("base");
                } else if (rectActionMap.get(rect).equals("clicked stop record")) {
                    this.isRecording = false;
                    actionList.add("base");
                } else if (rectActionMap.get(rect).equals("clicked play")) {
//                    for (State state : stateList) {
//                        System.out.println(state);
//                    }

//                    System.out.println("==============");
                    int lastFileIndex = -1, lastLastFileIndex = -1;
                    int lastLocIndex = -1, lastLastLocIndex = -1;
                    for (int i = 0; i < stateList.size(); i++) {
                        if (stateList.get(i).getAction().equals("clicked open")) {
                            if (lastFileIndex != -1) {
                                lastLastFileIndex = lastFileIndex;
                            }
                            lastFileIndex = stateList.get(i).getData();
                            if (lastLocIndex != -1) {
                                lastLastLocIndex = lastLocIndex;
                            }
                            lastLocIndex = stateList.get(i).getLocIndex();
                        }
                    }
//                    System.out.println("last " + lastFileIndex + " at index: " + lastLocIndex);
//                    System.out.println("lastlast " + lastLastFileIndex + " at index: " + lastLastLocIndex);
                    int targetIndex = lastFileIndex;
                    for (State state : stateList) {
                        if (state.getAction().equals("clicked new slide")) {
                            if(this.slideList.size() == 5) {
                                return;
                            }
                            addNewSlide();
//                            System.out.println("new slide");
                        } 
//                        else if (state.getAction().equals("switch slide")) {
//                            if (lastLastLocIndex != -1 && lastLocIndex != lastLastLocIndex) {
//                                if (lastLocIndex > lastLastLocIndex) {
//                                    targetLocIndex++;
//                                } else {
//                                    targetLocIndex--;
//                                }
//                            }
//                            if(targetLocIndex == -1 && targetLocIndex == 6) {
//                                return;
//                            }
//                            this.slideFocused = lastLocIndex;
//                        } 
                        else if (state.getAction().equals("clicked open")) {
                            if (lastLastFileIndex != -1 && lastFileIndex != lastLastFileIndex) {
                                if (lastFileIndex > lastLastFileIndex) {
                                    targetIndex++;
                                } else {
                                    targetIndex--;
                                }
                            }

//                            System.out.println("open " + targetIndex + " at index: " + slideFocused);
                            slideList.get(slideFocused).setSlideImage(pictureNames[targetIndex]);
                        }
                    }
                    actionList.add("base");
                }
                redraw();
                return;
            }
        }

    }

    private void addNewSlide() {
        this.slideList.add(new Slide(null));
        this.slideFocused = this.slideList.size() - 1;
        actionList.add("base");
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class fileSelectorListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel clickedLabel = ((JLabel) e.getSource());
            if (selectedFileIndex > -1 && clickedLabel.equals(files[selectedFileIndex])) {
                System.out.println("same");
                if (files[selectedFileIndex].getBackground().equals(Color.red)) {
                    files[selectedFileIndex].setBackground(UIManager.getColor("Panel.background"));
                    selectedFileIndex = -1;
                }
            } else {
                clickedLabel.setBackground(Color.red);
                if (selectedFileIndex > -1) {
                    files[selectedFileIndex].setBackground(UIManager.getColor("Panel.background"));
                }
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getBackground().equals(Color.red)) {
                        selectedFileIndex = i;
                    }
                }
            }
            redraw();
        }

    }
}
