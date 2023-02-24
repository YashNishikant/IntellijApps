import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class minesweeper extends JFrame implements MouseListener, ActionListener {


    ImageIcon[] numbers = new ImageIcon[8];
    ImageIcon flag, mine, smile, win, wait, dead;

    JToggleButton[][] buttons;
    JPanel buttonPanel;
    JMenuBar jmenubar;
    JMenu jMenu;
    JMenuItem beginner, intermediate, advanced;
    JButton reset;
    JTextField timeField;

    int numMines;
    int selectedCount;
    int timepassed;
    boolean first;
    boolean gameOver;

    Timer timer;
    GraphicsEnvironment ge;
    Font clockFont;

    String st;

    public minesweeper(){

        st = "assets\\";

        smile = new ImageIcon(st + "smile0.png");
        win = new ImageIcon(st + "win0.png");
        wait = new ImageIcon(st + "wait0.png");
        dead = new ImageIcon(st + "dead0.png");

        timepassed = 0;
        
        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            clockFont=Font.createFont(Font.TRUETYPE_FONT,
                    new File(st+"digital-7.ttf"));
            ge.registerFont(clockFont);
        } catch (IOException |FontFormatException e) {
            System.out.println("Font Error");
        }

        first=true;

        timeField = new JTextField("   "+timepassed);
        timeField.setFont(clockFont.deriveFont(18f));
        timeField.setBackground(Color.BLACK);
        timeField.setForeground(Color.GREEN);

        jmenubar = new JMenuBar();
        jmenubar.setPreferredSize(new Dimension(50,50));
        jMenu = new JMenu("Menu");
        jMenu.setPreferredSize(new Dimension(100,50));
        reset = new JButton();
        reset.setIcon(smile);
        beginner = new JMenuItem("Beginner");
        intermediate = new JMenuItem("Intermediate");
        advanced = new JMenuItem("Advanced");

        jMenu.add(beginner);
        jMenu.add(intermediate);
        jMenu.add(advanced);
        jmenubar.add(timeField);
        jmenubar.add(jMenu, BorderLayout.EAST);
        jmenubar.add(reset, BorderLayout.WEST);

        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        reset.addActionListener(this);

        this.add(jmenubar, BorderLayout.NORTH);


        numMines=10;
        setGrid(9,9);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==beginner){
            numMines = 10;
            setGrid(9,9);
        }
        if(e.getSource()==intermediate){
            numMines = 40;
            setGrid(16,16);
        }
        if(e.getSource()==advanced){
            numMines = 99;
            setGrid(16,40);
            this.resize(new Dimension((int)(this.getWidth()*2.25),(int)(this.getHeight()/1.4)));
        }
        if(e.getSource()==reset){
            reset();
        }
        if(timer!=null)
            timer.cancel();
        timepassed = 0;
        timeField.setText("   " + timepassed);
    }

    void reset(){
        reset.setIcon(smile);
        if(numMines==10){
            setGrid(9,9);
        }
        if(numMines==40){
            setGrid(16,16);
        }
        if(numMines==99){
            setGrid(16,40);
        }
    }

    void setGrid(int rows, int cols){

        gameOver = false;
        first = true;

        for(int i = 0; i < numbers.length; i++){
            numbers[i] = new ImageIcon(st + +(i+1)+".png");
            numbers[i] =new ImageIcon(numbers[i].getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        }

        flag = new ImageIcon(st + "flag.png");
        flag =new ImageIcon(flag.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        mine = new ImageIcon(st + "mine0.png");
        mine =new ImageIcon(mine.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        if(buttons!=null)
            this.remove(buttonPanel);
        buttonPanel = new JPanel();
        buttons = new JToggleButton[rows][cols];
        buttonPanel.setLayout(new GridLayout(rows,cols));

        for(int c = 0; c < cols; c++){
            for(int r = 0; r < rows; r++){
                buttons[r][c]=new JToggleButton();
                buttons[r][c].putClientProperty("row", r);
                buttons[r][c].putClientProperty("col", c);
                buttons[r][c].putClientProperty("state", 0);
                buttons[r][c].putClientProperty("flag", false);
                buttons[r][c].addMouseListener(this);
                buttonPanel.add(buttons[r][c]);
            }
        }
        this.add(buttonPanel, BorderLayout.CENTER);
        this.setSize(rows*50, cols*50);
        this.revalidate();
    }

    public void disablebuttons(){
        for(int r = 0; r < buttons.length; r++){
            for(int c = 0; c < buttons[0].length; c++) {
                buttons[r][c].setEnabled(false);
                if((int)buttons[r][c].getClientProperty("state")==-1){
                    buttons[r][c].setIcon(mine);
                    buttons[r][c].setDisabledIcon(mine);
                }
            }
        }
    }

    public static void main(String[] args) {
        new minesweeper();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int rowClicked=(int)((JToggleButton)e.getComponent()).getClientProperty("row");
        int colClicked=(int)((JToggleButton)e.getComponent()).getClientProperty("col");

        if(!gameOver) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                if (first) {
                    dropMines(rowClicked, colClicked);
                    timer = new Timer();
                    timer.schedule(new UpdateTimer(),0,1000);
                    first = false;
                }
                if ((int) (buttons[rowClicked][colClicked].getClientProperty("state")) == -1) {
                    for (int i = 0; i < buttons.length; i++) {
                        for (int j = 0; j < buttons[0].length; j++) {
                            if ((int) (buttons[i][j].getClientProperty("state")) == -1)
                                buttons[i][j].setIcon(mine);
                        }
                    }
                    disablebuttons();
                    gameOver = true;
                    reset.setIcon(dead);
                    JOptionPane.showMessageDialog(null, "u got bombed");
                    selectedCount = 0;
                    timer.cancel();
                } else {

                    reset.setIcon(smile);

                    selectedCount++;

                    expand(rowClicked, colClicked);

                    if(selectedCount==(buttons.length * buttons[0].length-numMines)){
                        reset.setIcon(win);
                        JOptionPane.showMessageDialog(null, "u win!");
                        selectedCount = 0;
                        disablebuttons();
                        timer.cancel();
                        gameOver=true;
                    }

                }
            }
            else if (!first && e.getButton() == MouseEvent.BUTTON3){
                if (buttons[rowClicked][colClicked].getIcon() == null && !buttons[rowClicked][colClicked].isSelected()) {
                    buttons[rowClicked][colClicked].putClientProperty("flag",true);
                    buttons[rowClicked][colClicked].setSelected(false);
                    buttons[rowClicked][colClicked].setIcon(flag);
                    buttons[rowClicked][colClicked].setDisabledIcon(flag);
                    buttons[rowClicked][colClicked].setEnabled(false);
                } else if (flag.equals(buttons[rowClicked][colClicked].getIcon())) {
                    buttons[rowClicked][colClicked].setIcon(null);
                    buttons[rowClicked][colClicked].setDisabledIcon(null);
                    buttons[rowClicked][colClicked].setEnabled(true);
                }
            }
        }
    }

    void expand(int r, int c){
        if(!flag.equals(buttons[r][c].getIcon())) {
            if (!buttons[r][c].isSelected()) {
                buttons[r][c].setSelected(true);
                selectedCount++;
            }
            int state = (int) (buttons[r][c].getClientProperty("state"));

            if (state > 0 && !(boolean) (buttons[r][c].getClientProperty("flag"))) {
                buttons[r][c].setDisabledIcon(numbers[state - 1]);
                buttons[r][c].setIcon(numbers[state - 1]);
            } else {
                for (int i = r - 1; i <= r + 1; i++) {
                    for (int j = c - 1; j <= c + 1; j++) {
                        try {
                            if (!buttons[i][j].isSelected())
                                expand(i, j);
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                    }
                }
            }
        }
    }

    void dropMines(int row, int col){
        int count = numMines;

        while(count>0){
            int r= (int)(Math.random()*buttons.length);
            int c= (int)(Math.random()*buttons[0].length);
            int state=(int)(buttons[r][c].getClientProperty("state"));
            if(Math.abs(r-row)>1 && Math.abs(c-col)>1 && state == 0){
                buttons[r][c].putClientProperty("state",-1);
                count--;
            }
        }
        for(int r=0; r<buttons.length;r++) {
            for (int c = 0; c < buttons[0].length; c++) {
                int state = (int)(buttons[r][c].getClientProperty("state"));
                if(state!=-1){
                    count=0;
                    for(int a=r-1;a<=r+1;a++){
                        for(int b=c-1;b<=c+1;b++){
                            try {
                                state = (int) (buttons[a][b].getClientProperty("state"));
                                if (state == -1)
                                    count++;
                            }
                            catch(ArrayIndexOutOfBoundsException e){}
                        }
                    }
                    buttons[r][c].putClientProperty("state", count);

                }

            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            reset.setIcon(wait);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    class UpdateTimer extends TimerTask{
        public void run() {
            if(!gameOver){
                timepassed++;
                timeField.setText("  "+timepassed);
            }
        }
    }
}