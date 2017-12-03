/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariolauncher;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author sydna
 */
public class Slide {
    private Image slideIcon, slideMainImage;
    public Slide(String imageRaw){
        if (imageRaw == null) {
            this.setSlideImage("default_slide.png");
        } else {
            this.setSlideImage(imageRaw);
        }
    }

    public void setSlideImage(String slide_raw_string) {
        try {
            Image slide_raw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/pictures/"+slide_raw_string));
            if(slide_raw_string.equals("default_slide.png")) {
                this.slideIcon = slide_raw.getScaledInstance((int) (slide_raw.getWidth(null) * 0.07), -1, Image.SCALE_SMOOTH);
                this.slideMainImage = slide_raw.getScaledInstance((int) (slide_raw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);
            } else {
                this.slideIcon = slide_raw.getScaledInstance((int) (slide_raw.getWidth(null) * 0.027), -1, Image.SCALE_SMOOTH);
                this.slideMainImage = slide_raw.getScaledInstance((int) (slide_raw.getWidth(null) * 0.18), -1, Image.SCALE_SMOOTH);
            }
        } catch (IOException ex) {
            Logger.getLogger(Slide.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Image getSlideIcon() {
        return slideIcon;
    }

    public Image getSlideMainImage() {
        return slideMainImage;
    }
}
