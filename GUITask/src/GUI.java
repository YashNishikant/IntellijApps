import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel buttonPanel, bigPanel;
    JMenuBar menuBar;
    GridLayout buttonGrid, menuGrid, bigGrid;
    JButton north, east, south, west, reset;
    JMenu fontOptions, fontSizes, textColors, textBackgroundColors, buttonOutlineColors;
    JMenuItem[] fontOptionsArr, fontSizesArr, textColorsArr, textBackgroundColorsArr, buttonOutlineColorsArr;
    String[] fontNamesStr, backgroundColorNamesStr, textColorNamesStr, buttonOutlineColorsStr;
    JTextArea textArea;
    Font currentFont;
    int currentFontSize;
    int[] fontSizesInt;

    Font[] allFonts;
    Color[] borders, texts, outlines, backgrounds;


    public GUI() {
        frame=new JFrame("GUI");
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800,800));
        menuBar=new JMenuBar();

        fontOptions=new JMenu("Font");
        fontSizes=new JMenu("Size");
        textColors=new JMenu("Color");
        textBackgroundColors=new JMenu("Background Color");
        buttonOutlineColors=new JMenu("Outline Color");

        fontOptions.setLayout(new GridLayout(1,6));
        fontSizes.setLayout(new GridLayout(1,6));
        textColors.setLayout(new GridLayout(1,6));
        textBackgroundColors.setLayout(new GridLayout(1,6));
        buttonOutlineColors.setLayout(new GridLayout(1,6));

        fontOptionsArr=new JMenuItem[3];
        fontSizesArr=new JMenuItem[3];
        textColorsArr=new JMenuItem[3];
        textBackgroundColorsArr=new JMenuItem[3];
        buttonOutlineColorsArr=new JMenuItem[4];

        menuBar.add(fontOptions);
        menuBar.add(fontSizes);
        menuBar.add(textColors);
        menuBar.add(textBackgroundColors);
        menuBar.add(buttonOutlineColors);

        fontNamesStr= new String[]{"Times New Roman", "Arial", "Consolas"};
        allFonts=new Font[3];
        fontSizesInt=new int[]{12, 12, 12};

        textColorNamesStr=new String[]{"Red", "Blue", "Random"};
        texts=new Color[]{Color.RED, Color.BLUE, new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256))};


        for(int a=0; a < allFonts.length; a++){
            allFonts[a]=new Font(fontNamesStr[a], Font.PLAIN, fontSizesInt[a]);
            currentFont = allFonts[0];

            fontOptionsArr[a]=new JMenuItem(fontNamesStr[a]);
            fontOptionsArr[a].setFont(allFonts[a]);
            fontOptionsArr[a].addActionListener(this);
            fontOptions.add(fontOptionsArr[a]);

            fontSizesArr[a]=new JMenuItem(""+fontSizesInt[a]);
            fontSizesArr[a].setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[a]));
            fontSizesArr[a].addActionListener(this);
            fontSizes.add(fontSizesArr[a]);

            textColorsArr[a]=new JMenuItem(textColorNamesStr[a]);
            textColorsArr[a].setForeground(texts[a]);
            textColorsArr[a].addActionListener(this);
            textColors.add(textColorsArr[a]);
        }
        backgroundColorNamesStr=new String[]{"Red", "Green", "Blue", "Random"};
        backgrounds=new Color[]{Color.RED, Color.GREEN, Color.BLUE, new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256))};
        for(int a=0; a<textBackgroundColorsArr.length; a++){
            textBackgroundColorsArr[a]=new JMenuItem(backgroundColorNamesStr[a]);
            textBackgroundColorsArr[a].setBackground(backgrounds[a]);
            textBackgroundColorsArr[a].addActionListener(this);
            buttonOutlineColors.add(textBackgroundColorsArr[a]);
        }

        reset = new JButton("RESET");
        reset.addActionListener(this);
        menuBar.add(reset);

        north = new JButton("NORTH");
        south = new JButton("SOUTH");
        east = new JButton("EAST");
        west = new JButton("WEST");

        bigGrid = new GridLayout(1,2);
        bigPanel = new JPanel(bigGrid);
        buttonGrid = new GridLayout(1,4);
        buttonPanel = new JPanel(buttonGrid);
        buttonPanel.add(north);
        buttonPanel.add(south);
        buttonPanel.add(east);
        buttonPanel.add(west);

        north.addActionListener(this);
        south.addActionListener(this);
        east.addActionListener(this);
        west.addActionListener(this);

        north.setBorder(new LineBorder(texts[0]));
        south.setBorder(new LineBorder(texts[0]));
        east.setBorder(new LineBorder(texts[0]));
        west.setBorder(new LineBorder(texts[0]));

        textArea = new JTextArea();
        textArea.setBackground(backgrounds[0]);
        textArea.setForeground(texts[0]);

        textArea.setFont(currentFont);

        bigPanel.add(buttonPanel);
        bigPanel.add(menuBar);
        frame.add(bigPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == north){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(1,4));
            bigPanel.setLayout(new GridLayout(1,2));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar);
            frame.add(bigPanel, BorderLayout.NORTH);
            frame.revalidate();

        }

        if(e.getSource() == south){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(1,4));
            bigPanel.setLayout(new GridLayout(1,2));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar, BorderLayout.SOUTH);
            frame.add(bigPanel, BorderLayout.SOUTH);
            frame.revalidate();
        }
        if(e.getSource() == east){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(4,1));
            bigPanel.setLayout(new GridLayout(2,1));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(6,1);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar, BorderLayout.EAST);
            frame.add(bigPanel, BorderLayout.EAST);
            frame.revalidate();

        }
        if(e.getSource() == west){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(4,1));
            bigPanel.setLayout(new GridLayout(2,1));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(6,1);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar, BorderLayout.WEST);
            frame.add(bigPanel, BorderLayout.WEST);
            frame.revalidate();
        }
    }

    public static void main(String[] args) {
        new GUI();
    }

}
