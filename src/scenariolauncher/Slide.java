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
    public Slide(Image imageRaw){
        try {
            if (imageRaw == null) {
                Image default_imageRaw = ImageIO.read(getClass().getResource("/scenariolauncher/resources/slide/default_slide.png"));
                this.setSlideImage(default_imageRaw);
            } else {
                this.setSlideImage(imageRaw);
            }
        } catch (IOException ex) {
                Logger.getLogger(Slide.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void setSlideImage(Image slide_raw) {
        this.slideIcon = slide_raw.getScaledInstance((int) (slide_raw.getWidth(null) * 0.07), -1, Image.SCALE_SMOOTH);
        this.slideMainImage = slide_raw.getScaledInstance((int) (slide_raw.getWidth(null) * 0.45), -1, Image.SCALE_SMOOTH);;
    }

    public Image getSlideIcon() {
        return slideIcon;
    }

    public Image getSlideMainImage() {
        return slideMainImage;
    }
}
