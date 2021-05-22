import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;

public class Application {

    public static Color decodeARGB(String color) {
        int endIndex = color.length();
        int beginIndex = 0;
        if (color.charAt(0) == '#') {
            beginIndex = 1;
            if (endIndex > 9) {
                endIndex = 9;
            }
        }
        final int v = (int) Long.parseLong(color, beginIndex, endIndex, 16);
        return new Color((v >> 16) & 0xFF, (v >> 8) & 0xFF, v & 0xFF, (v >> 24) & 0xFF);
    }

    public static void main(String[] args) throws Exception {
        BufferedImage img = createImg();
        ImageIO.write(img, "png", new File("d:/1.png"));
    }

    public static BufferedImage createImg() {
        final BufferedImage img = new BufferedImage(300, 20, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics g = img.getGraphics();
        g.setColor(Color.decode("#ffffff"));
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        {
            //注意字体得保证操作系统存在且支持中文
            final Font font = new Font(Font.SERIF, Font.PLAIN, 12);
            g.setFont(font);
            g.setColor(Color.BLACK);
            final String str = "xXpP国";
            //字体尺寸测量，不精确
            FontMetrics fm = g.getFontMetrics();
            //根据实际文本测量，精确
            LineMetrics lm = fm.getLineMetrics(str, g);

            //对于常用中英文leading都是空白，所以视觉高度应减去leading
            float visualHeight = lm.getHeight() - lm.getLeading();
            /*
             *垂直y坐标应该为=visualHeight-descent+(imgHeight-visualHeight)/2
             * 化简为：y=(imgHeight+visualHeight)/2-descent
             */
            float y = Math.round((img.getHeight() + visualHeight) / 2 - lm.getDescent());
            float x = Math.round((img.getWidth() - fm.stringWidth(str)) / 2.0f);
            g.drawString(str, (int) x, (int) y);
        }
        return img;
    }
}