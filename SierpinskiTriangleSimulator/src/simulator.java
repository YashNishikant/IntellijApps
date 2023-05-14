import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class simulator extends JPanel implements KeyListener, AdjustmentListener {

    JPanel GUI;
    JFrame frame;
    JScrollBar bar;
    JPanel panel;
    double val;

    ArrayList<Point> pointList = new ArrayList<>();
    public simulator(){

        bar = new JScrollBar(JScrollBar.HORIZONTAL, 50,0,1,99);
        val = bar.getValue()/100.0;
        bar.addAdjustmentListener(this);
        panel = new JPanel();

        frame = new JFrame();
        frame.setSize(800,800);
        frame.add(this);

        //triangleProcess();
        hexagonProcess();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(this);

        frame.add(bar, BorderLayout.SOUTH);

    }

    public void triangleProcess(){

        int[] xpoints = new int[]{frame.getWidth()/2, 100, frame.getWidth()-100};
        int[] ypoints = new int[]{100, frame.getHeight()-100, frame.getHeight()-100};

        pointList.add(new Point(xpoints[0], ypoints[0], Color.WHITE));
        pointList.add(new Point(xpoints[1], ypoints[1], Color.WHITE));
        pointList.add(new Point(xpoints[2], ypoints[2], Color.WHITE));

        Polygon tri = new Polygon(xpoints, ypoints, xpoints.length);

        int x=0, y=0;
        while(!tri.contains(x,y)){
            x = (int)(Math.random()*frame.getWidth());
            y = (int)(Math.random()*frame.getHeight());
        }

        buildPoints(new Point(x,y,Color.WHITE));
    }

    public void hexagonProcess(){

        int[] ypoints = new int[]{100, 100, frame.getHeight()-100, frame.getHeight()-100, frame.getHeight()/2, frame.getHeight()/2};
        int[] xpoints = new int[]{frame.getWidth()/2-195,frame.getWidth()/2+195,frame.getWidth()/2-195, frame.getWidth()/2+195, 50, frame.getWidth()-50};

        pointList.add(new Point(xpoints[0], ypoints[0], Color.WHITE));
        pointList.add(new Point(xpoints[1], ypoints[1], Color.WHITE));
        pointList.add(new Point(xpoints[2], ypoints[2], Color.WHITE));
        pointList.add(new Point(xpoints[3], ypoints[3], Color.WHITE));
        pointList.add(new Point(xpoints[4], ypoints[4], Color.WHITE));
        pointList.add(new Point(xpoints[5], ypoints[5], Color.WHITE));

        Polygon tri = new Polygon(xpoints, ypoints, xpoints.length);

        int x=0, y=0;
        while(!tri.contains(x,y)){
            x = (int)(Math.random()*frame.getWidth());
            y = (int)(Math.random()*frame.getHeight());
        }

        buildPointsHex(new Point(x,y,Color.WHITE));
    }

    public void buildPoints(Point p){
        Point randompoint = pointList.get((int)(Math.random()*3));
        pointList.add(new Point(((p.getX() + randompoint.getX())/2),((p.getY() + randompoint.getY())/2), Color.WHITE));
    }
    public void buildPointsHex(Point p){
        Point randompoint = pointList.get((int)(Math.random()*6));

        int x = p.getX() + (int)((randompoint.getX()-p.getX())*val);
        int y = p.getY() + (int)((randompoint.getY()-p.getY())*val);

        pointList.add(new Point(x,y, Color.WHITE));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, frame.getWidth(), frame.getHeight());

        for(Point p: pointList){
            g.setColor(p.getColor());
            g.fillOval(p.getX(),p.getY(), 5, 5);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_1){
            for(int i = 0; i < 50; i++){
                buildPoints(pointList.get(pointList.size()-1));
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_2){
            for(int i = 0; i < 50; i++){
                buildPointsHex(pointList.get(pointList.size()-1));
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public static void main(String[] args) {
        new simulator();
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if(e.getSource()==bar){
            val = bar.getValue()/100.0;
        }
    }


    public class Point{
        private int x;
        private int y;
        private Color color;
        public Point(int x, int y, Color color){
            this.x=x;
            this.y=y;
            this.color=color;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public Color getColor(){
            return color;
        }

    }

}

