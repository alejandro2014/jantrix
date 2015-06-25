package jantrix;

import java.awt.event.*;

/**
 * The KeybListener class handle the keyboard input
 * @author alejandro
 */
public class KeybListener implements KeyListener{
    private final int MOV_UP = 0;
    private final int MOV_DOWN = 1;
    private final int MOV_LEFT = 2;
    private final int MOV_RIGHT = 3;
    private final int KEY_ENTER = 4;
    
    private boolean[] movTable = new boolean[5];
    
     /**
     * Keyboard handle
     * @param k
     */
    public void keyPressed(KeyEvent k) {
        switch(k.getKeyCode()) {
            case KeyEvent.VK_UP: movTable[MOV_UP] = true; break;
            case KeyEvent.VK_DOWN: movTable[MOV_DOWN] = true; break;
            case KeyEvent.VK_LEFT: movTable[MOV_LEFT] = true; break;
            case KeyEvent.VK_RIGHT: movTable[MOV_RIGHT] = true; break;
            case KeyEvent.VK_ENTER: movTable[KEY_ENTER] = true; break;
        }
    }
    
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
    public void setMovTable(int pos, boolean value) {movTable[pos] = value;}
    public boolean getMovTable(int pos) {return movTable[pos];}
}
