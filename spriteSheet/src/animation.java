import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;


public class animation extends JPanel implements Runnable{

    BufferedImage imageSheet;
    BufferedImage[] images;

    int width;
    int height;

    int xIMG;
    int yIMG;

    JFrame frame;

    public animation(){
        frame = new JFrame();
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        images = new BufferedImage[10];

        xIMG = 0;
        yIMG = 0;
        width=115;
        height=130;
        try{
            imageSheet = ImageIO.read(new File("C:\\github\\IntellijJavaApps\\spriteSheet\\src\\sheet.png"));
            int i = 0;
            for(int y = 0; y < 2; y++){
                for(int x = 0; x < 5; x++){

                    xIMG = x*width;

                    System.out.print((yIMG + height) + " ");

                    images[i] = imageSheet.getSubimage(xIMG,yIMG,width,height);
                    images[i] = resize(images[i], width, height);


                    i++;
                }
                xIMG = 0;
                yIMG*=2;
                System.out.println();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }

        frame.add(new JLabel(new ImageIcon(images[0])));
        frame.add(new JLabel(new ImageIcon(images[1])));
        frame.add(new JLabel(new ImageIcon(images[2])));
        frame.add(new JLabel(new ImageIcon(images[3])));
        frame.add(new JLabel(new ImageIcon(images[4])));
        frame.add(new JLabel(new ImageIcon(images[5])));
        frame.add(new JLabel(new ImageIcon(images[6])));
        frame.add(new JLabel(new ImageIcon(images[7])));
        frame.add(new JLabel(new ImageIcon(images[8])));
        frame.add(new JLabel(new ImageIcon(images[9])));

    }

    public BufferedImage resize(BufferedImage image, int width, int height)
    {
        Image temp = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        BufferedImage scaledVersion = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledVersion.createGraphics();
        g2.drawImage(temp,0,0,null);
        g2.dispose();
        return scaledVersion;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
    }

    public static void main(String[] args) {
        new animation();
    }

    @Override
    public void run() {

    }
}

