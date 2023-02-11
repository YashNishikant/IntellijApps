
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class juliasetprogram extends JPanel implements AdjustmentListener, ActionListener, MouseMotionListener, KeyListener {

    BufferedImage juliaImage;
    JScrollPane juliaPane;
    JFrame jFrame;
    JPanel scrollPanel, labelPanel, panelPanel, buttonPanel;

    ArrayList<JScrollBar> scrollbarlist;
    ArrayList<JLabel> labellist;

    JButton light, reset, save;

    JScrollBar ABar, BBar, radBar, zoomBar, satBar, hueBar, brightBar, iterationBar;
    JLabel aLabel, bLabel, radLabel, zoomLabel, satLabel, hueLabel, brightLabel, iterationLabel;
    float aValue, bValue, radValue, zoomValue, satValue, hueValue, brightValue, iterationValue;

    float lx, ly;
    float panX, panY;
    boolean lightsource;

    JFileChooser fileChooser;

    public juliasetprogram(){

        String currDir=System.getProperty("user.dir");
        fileChooser=new JFileChooser(currDir);

        lightsource = false;

        panX = 0;
        panY = 0;

        lx = 500;
        ly = 100;

        scrollbarlist = new ArrayList<>();
        labellist = new ArrayList<>();

        jFrame = new JFrame("Julia Set Program");
        jFrame.setSize(1000,600);
        jFrame.add(this);

        scrollPanel = new JPanel();
        labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(100,50));
        scrollPanel.setLayout(new GridLayout(scrollbarlist.size(),1));
        labelPanel.setLayout(new GridLayout(scrollbarlist.size(),1));

        ABar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        BBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        radBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 1000, 4000);
        zoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 1000, 20000);
        satBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
        hueBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
        brightBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
        iterationBar = new JScrollBar(JScrollBar.HORIZONTAL, 300000, 0, 1000, 300000);

        aValue = 0;
        bValue = 0;
        radValue = 1;
        zoomValue = 1;
        satValue = 0;
        hueValue = 0;
        brightValue = 0;
        iterationValue = 300;

        scrollbarlist.add(ABar);
        scrollbarlist.add(BBar);
        scrollbarlist.add(radBar);
        scrollbarlist.add(zoomBar);
        scrollbarlist.add(satBar);
        scrollbarlist.add(hueBar);
        scrollbarlist.add(brightBar);
        scrollbarlist.add(iterationBar);

        for(JScrollBar sb : scrollbarlist){
            sb.addAdjustmentListener(this);
            scrollPanel.add(sb);
        }

        aLabel = new JLabel("A: " + aValue);
        bLabel = new JLabel("B " + bValue);
        radLabel = new JLabel("Radius " + radValue);
        zoomLabel = new JLabel("Zoom " + zoomValue);
        satLabel = new JLabel("Sat. " + satValue);
        hueLabel = new JLabel("Hue " + hueValue);
        brightLabel = new JLabel("Bright " + brightValue);
        iterationLabel = new JLabel("Iter " + iterationValue);

        labellist.add(aLabel);
        labellist.add(bLabel);
        labellist.add(radLabel);
        labellist.add(zoomLabel);
        labellist.add(satLabel);
        labellist.add(hueLabel);
        labellist.add(brightLabel);
        labellist.add(iterationLabel);

        for(JLabel jl : labellist){
            labelPanel.add(jl);
            jl.setOpaque(true);
        }

        panelPanel = new JPanel();
        panelPanel.setLayout(new BorderLayout());
        panelPanel.add(labelPanel, BorderLayout.WEST);
        panelPanel.add(scrollPanel, BorderLayout.CENTER);
        jFrame.add(panelPanel, BorderLayout.SOUTH);

        reset = new JButton("RESET");
        light = new JButton("LIGHT");
        save = new JButton("SAVE");
        reset.addActionListener(this);
        light.addActionListener(this);
        save.addActionListener(this);

        buttonPanel = new JPanel(new GridLayout(3,1));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.add(reset);
        buttonPanel.add(light);
        buttonPanel.add(save);
        jFrame.add(buttonPanel, BorderLayout.WEST);

        jFrame.addMouseMotionListener(this);
        jFrame.addKeyListener(this);
        jFrame.setFocusable(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(drawJulia(),0,0,null);

        if(lightsource) {
            g.setColor(Color.BLACK);
            g.fillRect((int) lx+1, (int) ly+10, 10, 20);
            g.setColor(Color.WHITE);
            g.fillRect((int) lx+1, (int) ly+23, 10, 1);
            g.setColor(Color.YELLOW);
            g.fillOval((int) lx-5, (int) ly, 20, 20);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == reset){
            aValue = 0;
            bValue = 0;
            radValue = 1;
            zoomValue = 1;
            satValue = 0;
            hueValue = 0;
            brightValue = 0;
            iterationValue = 300;
            ABar.setValue(0);
            BBar.setValue(0);
            radBar.setValue(1000);
            zoomBar.setValue(1000);
            satBar.setValue(0);
            hueBar.setValue(0);
            brightBar.setValue(0);
            iterationBar.setValue(300000);
            aLabel.setText("A: " + aValue);
            bLabel.setText("B: "+ bValue);
            radLabel.setText("Radius: " + radValue);
            zoomLabel.setText("Zoom: " + zoomValue);
            satLabel.setText("Sat.: " + satValue);
            hueLabel.setText("Hue: " + hueValue);
            brightLabel.setText("Bright: " + brightValue);
            iterationLabel.setText("Iter.: " + iterationValue);
        }
        if(e.getSource() == light){
            lightsource = !lightsource;
        }
        if(e.getSource() == save){
            saveImage();
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {

        if(e.getSource() == ABar){
            aValue =(float)ABar.getValue()/1000;
            aLabel.setText("A: " + aValue);
        }
        else if(e.getSource() == BBar){
            bValue = (float)BBar.getValue()/1000;
            bLabel.setText("B: "+ bValue);
        }
        else if(e.getSource() == radBar){
            radValue = (float)radBar.getValue()/1000;
            radLabel.setText("Radius: " + radValue);
        }
        else if(e.getSource() == zoomBar){
            zoomValue = (float)zoomBar.getValue()/1000;
            zoomLabel.setText("Zoom: " + zoomValue);
        }
        else if(e.getSource() == satBar){
            satValue = (float)satBar.getValue()/1000;
            satLabel.setText("Sat.: " + satValue);
        }
        else if(e.getSource() == hueBar){
            hueValue = (float)hueBar.getValue()/1000;
            hueLabel.setText("Hue: " + hueValue);
        }
        else if(e.getSource() == brightBar){
            brightValue = (float)brightBar.getValue()/1000;
            brightLabel.setText("Bright: " + brightValue);
        }
        else if(e.getSource() == iterationBar){
            iterationValue = (float)iterationBar.getValue()/1000;
            iterationLabel.setText("Iter.: " + iterationValue);
        }
        repaint();
    }

    public BufferedImage drawJulia(){

        int w = jFrame.getWidth();
        int h = jFrame.getHeight();

        juliaImage = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);

        float zoom;
        float maxIter = iterationValue;

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){

                zoom = zoomValue;
                float iter = maxIter;

                double zx = 1.5f*((j-w/2)/(0.5*zoom*w)) + panX;
                double zy = (i-h/2)/(0.5*zoom*h) + panY;

                while(((zx*zx + zy*zy) < 6) && (iter > 0)){
                    double d = (zx*zx - zy*zy) + aValue;
                    zy = 2 * 1/radValue * 5 * zx*zy + bValue;
                    zx = 1/radValue * 5 * d;
                    iter--;
                }

                int c;
                if(iter>0)
                    c = Color.HSBtoRGB(1.0f*iter/maxIter, 1, 1);
                else
                    if(!lightsource)
                        c = Color.HSBtoRGB(hueValue, satValue, brightValue);
                    else {
                        float br = 100/(float) dist(lx,ly,j,i);
                        if(br > 1)
                            br = 1;
                        c = Color.HSBtoRGB(hueValue, satValue, br);
                    }
                juliaImage.setRGB(j,i,c);
            }

        }

        return juliaImage;
    }

    double dist(float x1, float y1, float x2, float y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public void saveImage()
    {
        if(juliaImage!=null)
        {
            FileFilter filter=new FileNameExtensionFilter("*.png","png");
            fileChooser.setFileFilter(filter);
            if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                File file=fileChooser.getSelectedFile();
                try
                {
                    String st=file.getAbsolutePath();
                    if(st.indexOf(".png")>=0)
                        st=st.substring(0,st.length()-4);
                    ImageIO.write(juliaImage,"png",new File(st+".png"));
                }catch(IOException e)
                {
                    System.out.print("ERROR SAVING");
                }
            }
        }
    }

    public static void main(String[] args) {
        new juliasetprogram();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lx = e.getX();
        ly = e.getY();

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("e");

        if(e.getKeyCode()==KeyEvent.VK_W){
            panY -= 0.1;
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            panX -= 0.1;
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            panY += 0.1;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            panX += 0.1;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}


