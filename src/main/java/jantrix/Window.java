package jantrix;

import java.awt.Image;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Window extends JFrame {
    private final int OPT_REMAIN = -1;
    private final int OPT_NEWGAME = 0;
    private final int OPT_SELECTLEVEL = 1;
    private final int OPT_MAXSCORES = 2;
    private final int OPT_EXIT = 3;
    
    private final int MENU = 0;
    private final int GAME = 1;
    private final int EXIT = 2;
    
    private Image menuBackg;
    private Image gameBackg;
    private Image icon;
    private Image[] tile = new Image[9];
    private Image currentBackg;
    
    private KeybListener keybl = new KeybListener();
    private BufferStrategy bf;
    private Menu menu = null;
    private Game game = null;
    
    private int i;
    private int exitCode = OPT_REMAIN;
    private int jantrixState = MENU;
    
    public Window() {
        //Initializes the game window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setTitle("Jantrix v0.8");
        this.setSize(377,520);
        this.setResizable(false);
        
        //Load the game graphics
        try {
            menuBackg = getToolkit().getImage("./src/main/resources/graphics/presentacion2.png");
            gameBackg = getToolkit().getImage("./src/main/resources/graphics/fondo.png");
            icon = getToolkit().getImage("./src/main/resources/graphics/icono.png");
            for(i=1; i<8; i++) {
                tile[i] = getToolkit().getImage("./src/main/resources/graphics/" + i + ".png");
            }
            
        } catch(Exception e) {System.out.println("Graphics hasn't been loaded");}
        
        this.setIconImage(icon);
        this.setVisible(true);
        
        this.createBufferStrategy(2);
        this.setLocation(100,100);
        this.setVisible(true);
        
        bf = this.getBufferStrategy();
        
        jantrixLoop();
    }
    
    private void jantrixLoop() {
        while(jantrixState != EXIT) {
            switch(jantrixState) {
                case MENU:
                    game = null;
                    currentBackg = menuBackg;
                    
                    menu = new Menu(keybl,this);
                    if(exitCode == OPT_NEWGAME) jantrixState = GAME;
                    else if(exitCode == OPT_EXIT) jantrixState = EXIT;
                    break;
                
                case GAME:
                    menu = null;
                    currentBackg = gameBackg;
                    jantrixState = MENU;
                    game = new Game(keybl,this);
                    break;
            }
        }
    }
    
    public BufferStrategy getBf() {return bf;}
    public Image getBackg() {return currentBackg;}
    public void setExitCode(int code) {exitCode = code;}
    public Image getTile(int pos) {return tile[pos];}
}
