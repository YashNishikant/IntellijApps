import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class PaintProgram extends JPanel implements MouseListener, MouseMotionListener, ActionListener, AdjustmentListener, ChangeListener {
    public static void main(String[]args){PaintProgram app=new PaintProgram();}

    JFrame frame;
    Stack<ArrayList<Point>> freeLines;
    ArrayList<Point> points;
    boolean drawingFreeLine;
    Color currColor;
    int penWidth;
;
    JMenuBar bar;
    JMenu colorMenu;
    Color[] colors;
    JMenuItem[] colorOptions;
    JColorChooser cc;
    JButton reset;
    JScrollBar penWithBar;
    JMenu file;

    JMenuItem clear, exit, load, save;
    ImageIcon saveIMG, loadIMG;

    JFileChooser filechooser;
    BufferedImage loadedImage;

    public PaintProgram(){

        UIManager.put("JMenuItem.opaque", true);

        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");
        colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, new Color(75,0,130), new Color(143,0,255)};
        colorOptions = new JMenuItem[colors.length];
        reset = new JButton();

        clear = new JMenuItem("Clear");
        exit = new JMenuItem("Exit");
        load = new JMenuItem("Load", KeyEvent.VK_L);
        save = new JMenuItem("Save", KeyEvent.VK_S);

        clear.addActionListener(this);
        exit.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        saveIMG = new ImageIcon("saveIMG.png");
        saveIMG = new ImageIcon(saveIMG.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        loadIMG = new ImageIcon("loadIMG.png");
        loadIMG = new ImageIcon(loadIMG.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));

        save.setIcon(saveIMG);
        load.setIcon(loadIMG);

        file = new JMenu("File");
        file.add(save);
        file.add(load);
        file.add(clear);
        file.add(exit);

        colorMenu.setLayout(new GridLayout(8,1));
        for(int i = 0; i < colors.length; i++){
            colorOptions[i]=new JMenuItem();
            colorOptions[i].setOpaque(true);
            colorOptions[i].setBackground(colors[i]);
            colorOptions[i].addActionListener(this);
            colorOptions[i].setPreferredSize(new Dimension(50,30));
            colorOptions[i].putClientProperty("colorIndex", i);
            colorMenu.add(colorOptions[i]);

        }

        currColor = colors[0];
        cc=new JColorChooser();
        cc.getSelectionModel().addChangeListener(this);
        colorMenu.add(cc);

        penWithBar = new JScrollBar(JScrollBar.HORIZONTAL,1,0,1,100);
        penWithBar.addAdjustmentListener(this);
        penWidth = penWithBar.getValue();

        bar.add(file);
        bar.add(penWithBar);
        bar.add(colorMenu);
        bar.add(reset);

        currColor = Color.BLUE;
        penWidth = 4;

        frame=new JFrame();
        frame=new JFrame("PaintProgram");
        frame.setSize(800,800);
        frame.setBackground(currColor.WHITE);
        frame.add(this);
        frame.add(bar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        points = new ArrayList<Point>();
        freeLines = new Stack<ArrayList<Point>>();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        String currDir = System.getProperty("user.dir");
        filechooser = new JFileChooser(currDir);



        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,frame.getWidth(), frame.getHeight());
        Graphics2D g2d = (Graphics2D)(g);



        if(loadedImage!=null){
            g.drawImage(loadedImage,0,0,this);
        }

        Iterator it = freeLines.iterator();
        while(it.hasNext()){
            ArrayList<Point> p= (ArrayList<Point>)it.next();
            if(p.size() > 0){
                g2d.setStroke(new BasicStroke(p.get(0).getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.setColor(p.get(0).getColor());

                for(int a = 0; a < p.size()-1; a++){
                    Point p1 = p.get(a);
                    Point p2 = p.get(a+1);
                    g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                }
            }

        }


        if(drawingFreeLine){
            g2d.setStroke(new BasicStroke(points.get(0).getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(currColor);

            for(int a = 0; a < points.size()-1; a++){
                Point p1 = points.get(a);
                Point p2 = points.get(a+1);
                g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == exit){
            System.exit(0);

        }
        else if(e.getSource() == clear){
            freeLines = new Stack<ArrayList<Point>>();
            loadedImage=null;
            repaint();
        }
        else if(e.getSource()==save){
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            filechooser.setFileFilter(filter);

            if(filechooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                File file = filechooser.getSelectedFile();
                try{
                    String a = file.getAbsolutePath();
                    if(a.indexOf(".png")>=0){
                        a=a.substring(0,a.length()-4);
                        ImageIO.write(createImage(),"png", new File(a+".png"));
                    }
                }
                catch(IOException exception){

                }

            }
        }
        else if(e.getSource()==load){
               filechooser.showOpenDialog(null);
               File imgFile = filechooser.getSelectedFile();
               if(imgFile!=null && imgFile.toString().indexOf(".png")>=0){
                   try{
                       loadedImage=ImageIO.read(imgFile);

                   }catch(IOException ion){}

                   freeLines = new Stack<ArrayList<Point>>();
                   repaint();
               }
               else{
                   if(imgFile!=null){
                       JOptionPane.showMessageDialog(null, "Wrong file type. Select a PNG file.");
                   }
               }
        }
        else{
            int index = (int)((JMenuItem)e.getSource()).getClientProperty("colorIndex");
            currColor = colors[index];
        }

    }

    public BufferedImage createImage(){
        int width = this.getWidth();
        int height = this.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        g2d.dispose();
        return image;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        penWidth = penWithBar.getValue();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if(points.size() > 0)
            freeLines.push(points);

        points = new ArrayList<Point>();
        drawingFreeLine = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        drawingFreeLine=true;
        points.add(new Point(e.getX(), e.getY(), currColor, penWidth));
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        currColor = cc.getColor();
    }

    public class Point {
        int x,y,penWidth;
        Color color;

        public Point(int x, int y, Color color, int penWidth){
            this.x = x;
            this.y = y;
            this.color = color;
            this.penWidth = penWidth;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getPenWidth() {
            return penWidth;
        }

        public Color getColor() {
            return color;
        }
    }

}