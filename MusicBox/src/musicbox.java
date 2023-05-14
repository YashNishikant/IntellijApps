import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.*;
import java.net.URL;

public class musicbox extends JFrame implements Runnable, ActionListener, AdjustmentListener {

    JToggleButton[][] buttons;
    JPanel buttonPanel;
    JScrollPane pane;
    String[] notes;
    Thread timing;
    String[] clipNames;
    Clip[] clip;
    String[] instrumentNames;
    JMenu tone, tempo, play_stop, clear, Instruments;
    JMenuItem[] instrumentitems;
    JMenuBar menuBar;
    JPanel menuButtonPanel;
    JButton playstopButton;
    JButton clearButton;
    JScrollBar tempobar;
    JMenu file;
    JMenu addRemove;
    JMenuItem save, load, addColumn, add10Columns, removeColumn, remove10Columns;
    int tempoInt;
    int col;
    boolean currentlyPlaying;
    String currSong;
    String currentDirectory;
    String pathstring;
    String[] notenames;
    JFileChooser fileChooser;
    FileNameExtensionFilter filter;
    public musicbox(){

        addRemove = new JMenu("Add/Remove");

        addColumn = new JMenuItem("Add Column");
        add10Columns = new JMenuItem("Add 10 Columns");
        removeColumn = new JMenuItem("Remove Column");
        remove10Columns = new JMenuItem("Remove 10 Columns");
        addColumn.addActionListener(this);
        add10Columns.addActionListener(this);
        removeColumn.addActionListener(this);
        remove10Columns.addActionListener(this);

        addRemove.add(addColumn);
        addRemove.add(add10Columns);
        addRemove.add(removeColumn);
        addRemove.add(remove10Columns);


        currSong = "";
        currentDirectory = System.getProperty("user.dir");
        tempoInt=200;
        tempobar = new JScrollBar(JScrollBar.HORIZONTAL, tempoInt, 0, 50, 350);
        tempobar.addAdjustmentListener(this);
        add(tempobar, BorderLayout.SOUTH);
        currentlyPlaying=false;

        fileChooser = new JFileChooser(currentDirectory);

        file = new JMenu("File");
        save = new JMenuItem("Save");
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.addActionListener(this);

        file.add(save);
        file.add(load);

        instrumentNames = new String[]{"Bell", "Glockenspiel", "Marimba", "Oboe", "Oh_Ah", "Piano"};
        instrumentitems = new JMenuItem[instrumentNames.length];
        Instruments = new JMenu("Instruments");

        for(int i = 0; i < instrumentNames.length; i++){
            instrumentitems[i]=new JMenuItem(instrumentNames[i]);
            instrumentitems[i].addActionListener(this);
            instrumentitems[i].putClientProperty("InstrumentName", instrumentNames[i]);
            Instruments.add(instrumentitems[i]);
        }

        notes = new String[]{"C4", "B3", "A#3", "A3", "G#3", "G3", "F#3", "F3", "E3", "D#3", "D3", "C#3", "C3"
                , "B2", "A#2", "A2", "G#2", "G2", "F#2", "F2", "E2", "D#2", "D2", "C#2", "C2"
                , "B1", "A#1", "A1", "G#1", "G1", "F#1", "F1", "E1", "D#1", "D1", "C#1", "C1"};

        clipNames = new String[]{
                "C4", "B3", "ASharp3", "A3", "GSharp3", "G3", "FSharp3", "F3", "E3", "DSharp3", "D3", "CSharp3", "C3"
                , "B2", "ASharp2", "A2", "GSharp2", "G2", "FSharp2", "F2", "E2", "DSharp2", "D2", "CSharp2", "C2"
                , "B1", "ASharp1", "A1", "GSharp1", "G1", "FSharp1", "F1", "E1", "DSharp1", "D1", "CSharp1", "C1"};

        clip = new Clip[clipNames.length];


        String initInstrument = instrumentNames[0];
        loadNotes(initInstrument);

        buttons = new JToggleButton[37][50];
        buttonPanel = new JPanel(new GridLayout(37,50));


        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[0].length; j++){
                buttons[i][j] = new JToggleButton();
                buttons[i][j].setPreferredSize(new Dimension(30,30));
                buttons[i][j].setMargin(new Insets(0,0,0,0));
                buttons[i][j].setText(notes[i]);
                buttonPanel.add(buttons[i][j]);
            }
        }

        pane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setSize(1000,600);
        add(pane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tone = new JMenu();
        tempo = new JMenu();
        play_stop = new JMenu();
        clear = new JMenu();
        menuBar = new JMenuBar();

        menuButtonPanel = new JPanel(new GridLayout(1,3));
        playstopButton = new JButton("Play");
        clearButton = new JButton("Clear");
        playstopButton.addActionListener(this);
        clearButton.addActionListener(this);

        menuButtonPanel.add(playstopButton);
        menuButtonPanel.add(clearButton);
        menuBar.add(file);
        menuBar.add(addRemove);
        menuBar.add(Instruments);
        menuBar.add(menuButtonPanel);


        add(menuBar, BorderLayout.NORTH);

        setVisible(true);

        timing = new Thread(this);
        timing.start();

    }

    public static void main(String[] args) {
        new musicbox();
    }

    void saveSong(){

        filter = new FileNameExtensionFilter("*.txt", ".txt");
        fileChooser.setFileFilter(filter);
        if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            File f = fileChooser.getSelectedFile();
            try{
                pathstring = f.getAbsolutePath();
                if(pathstring.substring(pathstring.length()-4).equals(".txt")){
                    pathstring = pathstring.substring(0,pathstring.length()-4);
                }

                notenames = new String[]{"c ","b ","a-","a ","g-","g ","f-","f ","e ","d-","d ","c-","c ","b ","a-","a ","g-","g ",
                        "f-","f ","e ","d-","d ","c-","c ","b ","a-","a ","g-","g ","f-","f ","e ","d-","d ","c-","c "};;

                currSong += tempoInt + " " + buttons[0].length + "\n";

                for(int i = 0; i < buttons.length; i++){
                    currSong += notenames[i];
                    for(int j = 0; j < buttons[0].length; j++){
                        if(buttons[i][j].isSelected()){
                            currSong+="x";
                        }
                        else{
                            currSong+="-";
                        }
                    }
                    currSong+="\n";
                }

                BufferedWriter outputstream = new BufferedWriter(new FileWriter(pathstring+".txt"));
                outputstream.write(currSong);
                outputstream.close();

            }catch(Exception e){
                System.out.print(e);
            }
        }
    }

    void loadFile(){
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try{
                File loadFile = fileChooser.getSelectedFile();
                BufferedReader input = new BufferedReader(new FileReader(loadFile));
                String temp = input.readLine();
                String[] pieces = temp.split(" ");
                tempoInt = Integer.parseInt(pieces[0]);
                buttons = new JToggleButton[37][Integer.parseInt(pieces[1])];
                Character[][] song = new Character[37][Integer.parseInt(pieces[1])];
                int row = 0;

                while((temp=input.readLine())!=null){
                    for(int i = 2; i < Integer.parseInt(pieces[1])+2; i++){
                        song[row][i-2]=temp.charAt(i);
                    }
                    row++;
                }
                setNotes(song);
            }
            catch(IOException e){

            }
        }
    }

    void setNotes(Character[][] song){
        pane.remove(buttonPanel);
        buttonPanel = new JPanel();
        buttons = new JToggleButton[37][song[0].length];
        buttonPanel.setLayout(new GridLayout(37, song[0].length));

        notes = new String[]{"C4", "B3", "A#3", "A3", "G#3", "G3", "F#3", "F3", "E3", "D#3", "D3", "C#3",
                "C3", "B2", "A#2", "A2", "G#2", "G2", "F#2", "F2", "E2", "D#2", "D2", "C#2",
                "C2", "B1", "A#1", "A1", "G#1", "G1", "F#1", "F1", "E1", "D#1", "D1", "C#1", "C1"};

        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[0].length; j++){
                buttons[i][j] = new JToggleButton();
                buttons[i][j].setPreferredSize(new Dimension(30,30));
                buttons[i][j].setMargin(new Insets(0,0,0,0));
                buttons[i][j].setText(notes[i]);
                buttonPanel.add(buttons[i][j]);
            }
        }

        remove(pane);
        pane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(pane);
        for(int i = 0; i < song.length; i++){
            for(int j = 0; j < song[0].length; j++){
                if(song[i][j].equals('x')){
                    buttons[i][j].setSelected(true);
                }
            }
        }

        revalidate();

    }

    void addColumns(int num){
        pane.remove(buttonPanel);
        buttonPanel = new JPanel();
        JToggleButton[][] buttons2 = new JToggleButton[37][buttons[0].length+num];
        buttonPanel = new JPanel(new GridLayout(buttons2.length, buttons2[0].length));

        for(int i = 0; i < buttons2.length; i++){
            for(int j = 0; j < buttons2[0].length; j++){
                buttons2[i][j] = new JToggleButton();
                buttons2[i][j].setPreferredSize(new Dimension(30,30));
                buttons2[i][j].setMargin(new Insets(0,0,0,0));
                buttons2[i][j].setText(notes[i]);
                try{
                    if(buttons[i][j].isSelected()){
                        buttons2[i][j].setSelected(true);
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){

                }
                buttonPanel.add(buttons2[i][j]);
            }
        }

        remove(pane);
        pane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(pane);
        buttons = buttons2;
        revalidate();
    }

    void removeColumns(int num){
        pane.remove(buttonPanel);
        buttonPanel = new JPanel();
        JToggleButton[][] buttons2 = new JToggleButton[37][buttons[0].length-num];
        buttonPanel = new JPanel(new GridLayout(buttons2.length, buttons2[0].length));

        for(int i = 0; i < buttons2.length; i++){
            for(int j = 0; j < buttons2[0].length; j++){
                buttons2[i][j] = buttons[i][j];
                buttonPanel.add(buttons2[i][j]);
            }
        }

        remove(pane);
        pane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(pane);
        buttons = buttons2;
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==add10Columns){
            addColumns(10);
        }
        if(e.getSource()==addColumn){
            addColumns(1);
        }
        if(e.getSource()==removeColumn){
            removeColumns(1);
        }
        if(e.getSource()==remove10Columns){
            removeColumns(10);
        }

        if(e.getSource()==load) {
            reset();
            loadFile();
        }

        if(e.getSource()==save){
            reset();
            saveSong();
        }

        if(e.getSource()==playstopButton){
            currentlyPlaying=!currentlyPlaying;
            if(currentlyPlaying){
                playstopButton.setText("Pause");
            }
            else{
                playstopButton.setText("Play");
            }
        }
        else {
            for(int i = 0; i < instrumentitems.length; i++){
                if(e.getSource() == instrumentitems[i]){
                    loadNotes(instrumentitems[i].getClientProperty("InstrumentName").toString());
                    reset();
                }
            }
        }

        if(e.getSource()==clearButton){
            for(int i = 0; i < buttons.length; i++){
                for(int j = 0; j < buttons[0].length; j++){
                    buttons[i][j].setSelected(false);
                }
            }
            reset();
        }
        else {
            for(int i = 0; i < instrumentitems.length; i++){
                if(e.getSource() == instrumentitems[i]){
                    loadNotes(instrumentitems[i].getClientProperty("InstrumentName").toString());
                    reset();
                }
            }
        }
    }

    void reset(){
        col=0;
        currentlyPlaying=false;
        playstopButton.setText("Play");
    }

    void loadNotes(String initInstrument){
        try {
            for(int x=0;x<clipNames.length;x++)
            {
                URL url = this.getClass().getClassLoader().getResource(initInstrument+"\\"+initInstrument+" - "+clipNames[x]+".wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                clip[x] = AudioSystem.getClip();
                clip[x].open(audioIn);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {


        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if(e.getSource()==tempobar){
            tempoInt = tempobar.getValue();
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                if(!currentlyPlaying){
                    timing.sleep(150);
                }
                else {
                    for (int r = 0; r < buttons.length; r++) {
                        if (buttons[r][col].isSelected()) {
                            clip[r].start();
                        }
                    }

                    timing.sleep((long)((1/(double)tempoInt)*(30000)));

                    for (int r = 0; r < buttons.length; r++) {
                        if (buttons[r][col].isSelected()) {
                            clip[r].stop();
                            clip[r].setFramePosition(0);
                        }
                    }
                    col++;
                    col %= buttons[0].length;
                }
            }
            catch(InterruptedException e){

            }
        }
    }
}