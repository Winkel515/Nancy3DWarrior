package nancy3dwarrior;

public class Board {
    public static final int MIN_LEVEL = 3;
    public static final int MAX_LEVEL = 10;
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 10;
    private int[][][] board;
    private final int level;
    private final int size;
    
    public Board(){
        level = 3;
        size = 4;
        createBoard();
    }
    
    public Board(int l, int x) {
        level = l;
        size = x;
        createBoard();
    }

    public int getLevel() {
        return level;
    }

    public int getSize() {
        return size;
    }
    
    public int getEnergyAdj(int l, int x, int y){
        return board[l][x][y];
    }
    
    public int getEnergyAdj(Player p) {
        return board[p.getLevel()][p.getX()][p.getY()];
    }
    
    private void createBoard() {
        board = new int[level][size][size];
        for(int l = 0; l < board.length; l++) {
            for(int x = 0; x < board[l].length; x++) {
                for(int y = 0; y < board[l][x].length; y++) {
                    int sum = l+x+y;
                    if(sum == 0)          // To fit the sample run
                        board[l][x][y] = 0; 
                    else if(sum % 3 == 0) // If multiple of 3, assign -3
                        board[l][x][y] = -3;
                    else if(sum % 5 == 0) // If multiple of 5, assign -2
                        board[l][x][y] = -2;
                    else if(sum % 7 == 0) // If multiple of 7, assign 2
                        board[l][x][y] = 2;
                    else                  // If none apply, assign 0
                        board[l][x][y] = 0;
                }
            }
        }
    }
    
    public int[] potentialLocation(Player p, int move) {
        int lxy[] = new int[3]; //Stores the level, x, y of the potential location after a move
        lxy[2] = (p.getY() + move); // Calculating and Storing potential y without mod
        lxy[1] = (int)(p.getX() + Math.floor(lxy[2]*1.0/size)); // Calculating and Storing potential x without mod
        lxy[0] = (int)(p.getLevel() + Math.floor(lxy[1]*1.0/size)); // Calculating and Storing potential level
        lxy[2] %= size; // Adjusting mode size
        lxy[1] %= size; // Adjusting mode size
        if(lxy[2] < 0)
            lxy[2] += size;
        if(lxy[1] < 0)
            lxy[1] += size;
        return lxy;
    }
    
    public Player occupiedBy(int l, int x, int y, Player[] players){ //Return location of the player occupying lxy position
        for(Player player: players){
            if(player.getLevel() == l && player.getX() == x && player.getY() == y)
                return player;
        }
        return null;
    }
    
    @Override
    public String toString() {
        String boardString = "";
        for(int l = 0; l < board.length; l++) {
            boardString += "Level " + l + "\n---------\n";
            for(int x = 0; x < board[l].length; x++) {
                for(int y = 0; y < board[l][x].length; y++) {
                    boardString += "\t" + board[l][x][y];
                }
                boardString += "\n";
            }
        }
        return boardString;
    }
}
