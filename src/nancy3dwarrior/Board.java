package nancy3dwarrior;

public class Board {
    public static final int MIN_LEVEL = 3;
    public static final int MIN_SIZE = 3;
    private int[][][] board;
    private int level;
    private int size;
    
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
