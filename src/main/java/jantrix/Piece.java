package jantrix;

/**
 * Clase encargada de manejar una pieza determinada
 * @author alejandro
 */
public class Piece {
    private final int MOV_UP = 0;
    private final int MOV_DOWN = 1;
    private final int MOV_LEFT = 2;
    private final int MOV_RIGHT = 3;
    private final int MOV_NULL = 4;
    
    private int[] pos = new int[4];//Posicion de los miembros de cada pieza
    private int[] maxTurns = {1,3,0,3,3,1,1}; //Veces que pueden girar las piezas
    private int turnNumber = 0;
    private int pieceKind;
    
    private int[] vectorCols = {0,12,-1,1,0};
    
    private Game gameRef;
    
    private int[] turnsMatrix = { -24,-12,12,2,1,-1,    0,0,0,0,0,0,
                                  -12,-1,12,1,-12,-1,  12,1,-12,-1,12,1,
                                  1,12,13,0,0,0,       0,0,0,0,0,0,
                                  -13,-12,12,-11,1,-1, 13,12,-12,11,-1,1,
                                  -12,-11,12,1,13,-1,  12,11,-12,-1,-13,1,
                                  -12,1,13,1,12,11,    0,0,0,0,0,0,
                                  -11,1,12,-13,-12,1,  0,0,0,0,0,0};
    
    private int index;
    
    private boolean colision = false;
    private int i, inc = 0;
    
    private boolean downCollision = false;
    private boolean leftCollision = false;
    private boolean rightCollision = false;
    
    /**
     * Constructor
     * @param tipo El tipo de la pieza (consultar tabla)
     */
    public Piece(int tipo, Game game) {
        gameRef = game;
        placePiece(tipo,0,30);
    }

    
    /**
     * Posiciona una pieza en una posición y con un giro determinados. La pieza
     * consta de cuatro segmentos que son colocados según el tipo y el giro,
     * accediendo a turnsMatrix para saber la posición de cada segmento.
     * @param tipo El tipo de pieza (de 0 a 6)
     * @param giro En qué giro se encuentra la pieza
     * @param p Posición del segmento principal de la pieza
     */ 
    public void placePiece(int tipo, int giro, int p) {
        index = tipo*12 + giro*3;
        
        pos[0]=p; 
        try {
            for(i=1; i<4; i++)
                pos[i] = p + turnsMatrix[index + i - 1];
        } catch(ArrayIndexOutOfBoundsException e) {}
        
        gameRef.setMatrix(pos[0], tipo+1);
        gameRef.setMatrix(pos[1], tipo+1);
        gameRef.setMatrix(pos[2], tipo+1);
        gameRef.setMatrix(pos[3], tipo+1);
    }
    
    /**
     * Movement of the piece
     * @param movKind Indica hacia dónde se ha de move la pieza
     */
    public void move(int movKind) {
        int mov = MOV_NULL;
        
        calculateCollisions();
        
        gameRef.setMatrix(pos[0], -1);
        gameRef.setMatrix(pos[1], -1);
        gameRef.setMatrix(pos[2], -1);
        gameRef.setMatrix(pos[3], -1);
        
        switch(movKind) {
            case MOV_UP:
                //Sólo hace los giros permitidos por la pieza
                if(turnNumber < maxTurns[pieceKind]) turnNumber++;
                else turnNumber=0;
                
                if(!canTurn(turnNumber)) turnNumber--;
                mov = MOV_NULL;
                break;
           
            case MOV_DOWN:
                if(!downCollision) mov = MOV_DOWN;
                else {mov = MOV_NULL; gameRef.setNewPiece();}
                break;
                
            case MOV_LEFT:
                mov = !rightCollision ? MOV_LEFT : MOV_NULL;
                break;
            
            case MOV_RIGHT:
                mov = !rightCollision ? MOV_RIGHT : MOV_NULL;
                break;
        }
        
        placePiece(pieceKind, turnNumber, pos[0] + vectorCols[mov]);
    }
    
    private void calculateCollisions() {
        downCollision = collision(MOV_DOWN);
        leftCollision = collision(MOV_LEFT);
        rightCollision = collision(MOV_RIGHT);
    }
    
    /**
     * Checks if exists a collision
     * @param colKind Kind of the collision
     */ 
    public boolean collision(int colKind) {
        inc = vectorCols[colKind];
        colision = false;
        
        for(i=0; i<4; i++) {
            colision = gameRef.colMatrix[pos[i] + inc];
            if(colision) break;
        }
        
        return colision;
    }
    
    /**
     * Averigua si se puede girar la pieza consultando la matrix de colisiones
     * @param nextTurn El número siguiente del giro de la pieza
     * @return puedo Si la pieza puede ser girada o no
     */
    public boolean canTurn(int nextTurn) {
        index = pieceKind*12 + nextTurn*3;
        
        return !(gameRef.isOccupied(pos[0] + turnsMatrix[index]) ||
                 gameRef.isOccupied(pos[0] + turnsMatrix[index + 1]) ||
                 gameRef.isOccupied(pos[0] + turnsMatrix[index + 2]));
    }
    
    public void setTipoPiece(int tipo) { pieceKind = tipo; }
    
    public int getPos(int p) {return pos[p];}
    public int getGiro() { return turnNumber; }
    public int getKind() {return pieceKind;}
    public void setGiro(int giro) { turnNumber = giro; }
}
