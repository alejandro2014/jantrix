package jantrix;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.Image;
import java.util.Random;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JLabel;
 
public class Game extends JFrame {
//public class Game {
    private final int MOV_UP = 0;
    private final int MOV_DOWN = 1;
    private final int MOV_LEFT = 2;
    private final int MOV_RIGHT = 3;
    
    private BufferStrategy bf;
    private Graphics g;
    //private Image background;
    private Image icon;
    private Image[] tile = new Image[9];
    private KeybListener keybl = null;//new KeybListener();
    private Piece piece;
    private Random rnd;
    
    public int[] matrix = new int[276];
    public boolean[] colMatrix = new boolean[276];
    private int[] completeLines = new int[5];
    private int[] samplingArray = {8,7,6,5,3,2,1,3,2,1};
    private int[] posIniPiece = {42,30,30,30,30,30,30};
    private int[] pieceValues = {1,1,4,2,2,3,3};
    private int[] nextPieceMatrix = {296, 103, 296, 126, 296, 149, 296, 172,
                                     307, 115, 307, 138, 307, 161, 284, 138,
                                     284, 127, 307, 127, 284, 150, 307, 150,
                                     284, 115, 307, 115, 307, 138, 307, 161,
                                     284, 138, 284, 115, 284, 161, 307, 115,
                                     284, 115, 284, 138, 307, 138, 307, 161,
                                     284, 138, 284, 161, 307, 115, 307, 138};
    
    private boolean startedGame = false;
    private boolean gameOver = false;
    private boolean newPiece = false;
    
    private int level = 0;
    private int score = 0;
    private int lines = 0;
    private int pieceKind, pieceKind2;
    private int wait, stepsLeft;
    private int i;
    private int iteration = 0;
    
    private int modulo;
    private int cellKind;
    private Window window;
    private Container container;
    
    /**
     * The constructor initializes the game properly
     * @param path
     * @param kl
     */
    public Game(KeybListener keyblis, Window win){//, Container contain) {
        keybl = keyblis;
        window = win;
        container = win.getContentPane();
        
        container.setFocusable(true);
        container.addKeyListener(keybl);
        container.requestFocus();
        
        bf = window.getBf();
        
        for(i=0;i<9;i++) tile[i] = window.getTile(i);
        this.initializeVariables();
        this.gameLoop();
    }

    /**
     * Initializes the game variables
     */
    private void initializeVariables() {
        rnd = new Random();
        
        pieceKind = rnd.nextInt() % 7;
        if(pieceKind < 0) pieceKind *= -1;
        pieceKind2 = rnd.nextInt() % 7;
        if(pieceKind2 < 0) pieceKind2 *= -1;
        
        piece = new Piece(pieceKind,this);
        newPiece();
        startedGame = true;
        
        for(i=0; i<276; i++) {
            modulo = i%12;
            if(modulo == 0 || modulo == 11 || i>251) {
                matrix[i] = 0; colMatrix[i] = true;
            } else {
                matrix[i] = -1; colMatrix[i] = false;
            }
        }
        
        for(i=0; i<5; i++) completeLines[i] = -1;    
        for(i=0; i<4; i++) keybl.setMovTable(i, false);
    }
    
    /**
     * Game Loop, controls the flow of the game
     */
    private void gameLoop() {
        while(!gameOver) {
            if(level < 7) wait = 100; else wait = 25;
            stepsLeft = samplingArray[level];
            
            while(stepsLeft > 0) {
                readKeyboard();
                checkLines();
                drawStuff();
                stepsLeft--;
                try {Thread.sleep(wait);} catch(Exception e) {}
            }
            piece.move(MOV_DOWN);
            drawStuff();
            
            if(newPiece) {
                if((gameOver = checkGameOver()) == false) newPiece();
            }
            
            iteration++;
        }
        
        JDialog dialog = new JDialog(this,"¡Juego terminado!",true);
        dialog.add(new JLabel("Siempre puedes echar otra partida..."));
        dialog.setSize(300,200);
        dialog.setLocation(100,100);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private void readKeyboard() {
        if(keybl.getMovTable(MOV_UP)) piece.move(MOV_UP);
        else if(keybl.getMovTable(MOV_DOWN)) piece.move(MOV_DOWN);
        else if(keybl.getMovTable(MOV_LEFT)) piece.move(MOV_LEFT);
        else if(keybl.getMovTable(MOV_RIGHT)) piece.move(MOV_RIGHT);
        
        for(i=0; i<4; i++) keybl.setMovTable(i, false);
    }
    
    /**
     * DrawStuff draws the scene
     */
    private void drawStuff() {
        //Toolkit.getDefaultToolkit().sync();
        g = bf.getDrawGraphics();
        bf.show();
        Image background = window.getBackg();
        try {
            g.drawImage(background, 4, 24, this);
            
            for(i=12; i<264; i++) {
                cellKind = matrix[i];
            
                if(cellKind > 0)
                    g.drawImage(tile[cellKind], (i%12)*23-3, (i/12)*23+17, this);
            }
            
            drawPiece(piece);
            drawNextPiece(pieceKind2);
            
            g.setColor(Color.WHITE);
            g.drawString("" + (level+1), 303, 259);
            g.drawString("" + score, 303, 339);
            g.drawString("" + lines, 303, 429);
            
        } finally {bf.show(); g.dispose();}
    }
    
    /**
     * New piece creation
     */
    public void newPiece() {
        newPiece = false;
        // When a new piece is creates, it writes in the collision matrix
        if(startedGame) {
            colMatrix[piece.getPos(0)] = true;
            colMatrix[piece.getPos(1)] = true;
            colMatrix[piece.getPos(2)] = true;
            colMatrix[piece.getPos(3)] = true;
        }
        
        if(startedGame)
        score += (level+1)*pieceValues[pieceKind];
        
        //Random piece
        pieceKind = pieceKind2;
        pieceKind2 = rnd.nextInt() % 7;
        if(pieceKind2 < 0) pieceKind2 *= -1;
        
        //Sets the features of the new piece
        piece.setTipoPiece(pieceKind);
        piece.setGiro(0);
        piece.placePiece(pieceKind, 0, posIniPiece[pieceKind]);
    }
    
    /**
     * Drawing piece method, used to avoid the uncomplete draw of the piece
     * @param piece Piece to be drawed
     */ 
    private void drawPiece(Piece piece) {
        int kind = piece.getKind() + 1;
        int pos0 = piece.getPos(0);
        int pos1 = piece.getPos(1);
        int pos2 = piece.getPos(2);
        int pos3 = piece.getPos(3);
        
        g.drawImage(tile[kind], (pos0%12)*23-3, (pos0/12)*23+17, this);
        g.drawImage(tile[kind], (pos1%12)*23-3, (pos1/12)*23+17, this);
        g.drawImage(tile[kind], (pos2%12)*23-3, (pos2/12)*23+17, this);
        g.drawImage(tile[kind], (pos3%12)*23-3, (pos3/12)*23+17, this);
    }
    
    /**
     * This method draws the next piece that will appear
     * @param kind Kind of the next piece. Necessary for
     * access the matrix properly
     */
    private void drawNextPiece(int kind) {
	//TODO Use Points instead of coordinates
        int pos = kind*8;
        int x1 = nextPieceMatrix[pos], y1= nextPieceMatrix[pos+1];
        int x2 = nextPieceMatrix[pos+2], y2= nextPieceMatrix[pos+3];
        int x3 = nextPieceMatrix[pos+4], y3= nextPieceMatrix[pos+5];
        int x4 = nextPieceMatrix[pos+6], y4= nextPieceMatrix[pos+7];
        
        g.drawImage(tile[kind + 1], x1, y1, this);
        g.drawImage(tile[kind + 1], x2, y2, this);
        g.drawImage(tile[kind + 1], x3, y3, this);
        g.drawImage(tile[kind + 1], x4, y4, this);
    }
    
    /**
     * Metodo utilizado para comprobar si hay lineas completas
     */
    public void checkLines() {
        int cont2 = 0;
        int cont3, cont4;
        int casillasVacias;
        int vInicial, vFinal;
        
        for(cont3=20; cont3>0; cont3--) {
            vInicial = cont3*12 + 1;
            vFinal = vInicial + 10;
            casillasVacias = 10;
          
            //Bucle para todas las casillas de una fila
            for(cont4=vInicial; cont4<vFinal; cont4++) {
                if(colMatrix[cont4]) casillasVacias--;
            }
            
            if(casillasVacias == 0) {
                completeLines[cont2] = cont3;
                cont2++;
            }
        }
        removeLines();
    }
    
    /**
     * Delete the complete lines and update the matrix
     */
    public void removeLines() {
        int compLines = 0;
        int cont2 = 0;
        
        for(cont2=20; cont2>0; cont2--) {
            while(completeLines[compLines] == cont2) {
                cont2--; compLines++;
            }
            this.copyLine(cont2, cont2+compLines);
        }
        
        lines += compLines;
        score += 10*(level+1)*compLines;
        level = lines/20;
        if(level>9) level=9;
        
        for(cont2=1; cont2<compLines+1; cont2++) copyLine(0,cont2);   
        for(cont2=0; cont2<5; cont2++) completeLines[cont2] = -1;
    }
    
    /**
     * Copy a line
     * @param source Source line
     * @param destination Destination line
     */
//TODO Is it possible to replace this wit an built-int function?
    private void copyLine(int source, int destination) {
        int iniCellSource = source*12 + 1;
        int iniCellDestin = destination*12 + 1;
        int cont2;
        int sourceCell;
        
        for(cont2=0; cont2<10; cont2++) {
            sourceCell = iniCellSource + cont2;
            
            if(piece.getPos(0) != sourceCell && piece.getPos(1) != sourceCell &&
                piece.getPos(2) != sourceCell && piece.getPos(3) != sourceCell) {
                matrix[iniCellDestin+cont2] = matrix[sourceCell];
                colMatrix[iniCellDestin+cont2] = colMatrix[sourceCell];
            }
        }
    }
    
    /**
     * Ckecks the condition necessary for the game over
     * @return true if the game is over, false otherwise
     */
    public boolean checkGameOver() {
        return ((posIniPiece[piece.getKind()] == piece.getPos(0)) && iteration > 0);
    }
    
    public int getTipoPiece() { return pieceKind; }
    public int getColor(int pos) { return matrix[pos]; }
    public boolean isOccupied(int pos) { return colMatrix[pos]; }
    public int getGiro() { return piece.getGiro(); }
    public void setMatrix(int pos, int kind) {matrix[pos] = kind;}
    public void setNewPiece() {newPiece = true;}
}
