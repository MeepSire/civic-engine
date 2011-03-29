// @author: Philipp Jean-Jacques

package core.graphics.filter;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class AntialiasingFilter extends GraphicsFilter {

    public AntialiasingFilter(){

    }

    @Override
    public BufferedImage apply(BufferedImage img){

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Graphics2D g2d = out.createGraphics();
        g2d.setRenderingHints(rh);

        g2d.drawImage(img, 0, 0, null);

        return out;
    }

}
