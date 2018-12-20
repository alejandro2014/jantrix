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

    private String graphicsPath = "./src/main/resources/graphics";

    public Window() {
        //Initializes the game window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setTitle("Jantrix v0.8");
        this.setSize(377,520);
        this.setResizable(false);

        loadGraphics();

        this.setIconImage(icon);
        this.setVisible(true);

        this.createBufferStrategy(2);
        this.setLocation(100,100);
        this.setVisible(true);

        bf = this.getBufferStrategy();

        jantrixLoop();
    }

    private void loadGraphics() {
        try {
            menuBackg = loadGraphic("presentacion2");
            gameBackg = loadGraphic("fondo");
            icon = loadGraphic("icono");

            for(i = 1; i < 8; i++) {
                tile[i] = loadGraphic(Integer.toString(i));
            }
        } catch(Exception e) {
            System.out.println("Graphics hasn't been loaded");
        }
    }

    private Image loadGraphic(String graphicName) {
        String graphicPath = graphicsPath + "/" + graphicName + ".png";

        return getToolkit().getImage(graphicPath);
    }

    private void jantrixLoop() {
        while(jantrixState != EXIT) {
            switch(jantrixState) {
                case MENU: setGameStatus(null, new Menu(keybl,this), menuBackg); break;
                case GAME: setGameStatus(new Game(keybl,this), null, gameBackg); break;

                jantrixState = setJantrixState(jantrixState, exitCode);
            }
        }
    }

    private void setGameStatus(Game game, Menu menu, Image background) {
        this.game = game;
        this.menu = menu;
        this.currentBackg = background;
    }

    private int setJantrixState() {
        switch(jantrixState) {
            case MENU:
                if(exitCode == OPT_NEWGAME) jantrixState = GAME;
                else if(exitCode == OPT_EXIT) jantrixState = EXIT;
                break;

            case GAME:
                jantrixState = MENU;
                break;
        }

        return jantrixState;
    }

    public BufferStrategy getBf() {return bf;}
    public Image getBackg() {return currentBackg;}
    public void setExitCode(int code) {exitCode = code;}
    public Image getTile(int pos) {return tile[pos];}
}
