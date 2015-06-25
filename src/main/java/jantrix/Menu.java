package jantrix;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Menu extends JFrame {
    private final int NUM_OPTIONS = 4;
    
    private final int MOV_UP = 0;
    private final int MOV_DOWN = 1;
    private final int MOV_LEFT = 2;
    private final int MOV_RIGHT = 3;
    private final int MOV_ENTER = 4;
    
    private final int OPT_REMAIN = -1;
    private final int OPT_NEWGAME = 0;
    private final int OPT_SELECTLEVEL = 1;
    private final int OPT_MAXSCORES = 2;
    private final int OPT_EXIT = 3;
    
    private String[] textOptions = {"Empezar juego", "Elegir nivel",
                                "Máximas puntuaciones", "Salir"};
    private int[] xOptions = {98,120,47,161};
    private int[] yOptions = {200,240,280,320};
    private Image icon;
    
    private int i;
    //private boolean exitMenu = false;
    private BufferStrategy bf;
    private Graphics g;
    private int select = 0;
    
    private Color cSelected = new Color(200,200,200);
    private Color cNoSelected = new Color(200,200,40);
    private Font menuFont = new Font("fSelected", Font.ITALIC|Font.BOLD, 24);
    private KeybListener keybl = null;
    private int exitMenu = OPT_REMAIN;
    private Window window;
    private Container container;
    
    public Menu(KeybListener keyblis, Window win) {
        keybl = keyblis;
        window = win;
        container = win.getContentPane();//contain;
        
        container.setFocusable(true);
        container.addKeyListener(keybl);
        container.requestFocus();
	
        bf = window.getBufferStrategy();
        this.menuLoop();
    }

    private void menuLoop() {
        while(exitMenu == OPT_REMAIN) {
            this.readKeyboard();
            this.drawStuff();
            try {Thread.sleep(100);} catch(Exception e){}
        }
        window.setExitCode(exitMenu);
    }
    
    private void drawStuff() {
        g = bf.getDrawGraphics();
        Image background = window.getBackg();
        
        try {
            g.drawImage(background, 4, 24, this);
            g.setFont(menuFont);
            
            for(i=0; i<NUM_OPTIONS; i++) {
                if(i!=select) g.setColor(cNoSelected);
                else g.setColor(cSelected); 
                g.drawString(textOptions[i], xOptions[i], yOptions[i]);   
            }
            
        } finally {bf.show(); g.dispose();}
    }

    private void readKeyboard() {
        if(keybl.getMovTable(MOV_ENTER)) {exitMenu = select;}
        else if(keybl.getMovTable(MOV_UP)){select--; if(select == -1) select = NUM_OPTIONS-1;}
        else if(keybl.getMovTable(MOV_DOWN)){select++; if(select == NUM_OPTIONS) select = 0;}
        //else if(keybl.getMovTable(MOV_ENTER)) {exitMenu = select;}
        //else if(keybl.getMovTable(MOV_LEFT))
        //else if(keybl.getMovTable(MOV_RIGHT))
        
        for(i=0; i<5; i++) keybl.setMovTable(i, false);
    }
}
