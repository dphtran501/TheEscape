package edu.orangecoastcollege.escapethecatcher;

/**
 * This class represents a zombie enemy on the game board.
 *
 * @author Michael Paulding
 */
public class Zombie
{

    private int mRow;
    private int mCol;

    /**
     * Moves the <code>Zombie</code> object in the game board towards the <code>Player</code> object.
     *
     * @param gameBoard The game board that the <code>Zombie</code> object moves on.
     * @param playerCol The column position of the <code>Player</code> object in the game board.
     * @param playerRow The row position of the <code>Player</code> object in the game board.
     */
    public void move(int[][] gameBoard, int playerCol, int playerRow)
    {
        if (mCol < playerCol && gameBoard[mRow][mCol + 1] != BoardCodes.OBSTACLE) mCol++;
        else if (mCol == playerCol && mRow < playerRow
                && gameBoard[mRow + 1][mCol] != BoardCodes.OBSTACLE) mRow++;
        else if (mCol == playerCol && mRow > playerRow
                && gameBoard[mRow - 1][mCol] != BoardCodes.OBSTACLE) mRow--;
        else if (mCol > playerCol && gameBoard[mRow][mCol - 1] != BoardCodes.OBSTACLE) mCol--;
        else if (mRow < playerRow && gameBoard[mRow + 1][mCol] != BoardCodes.OBSTACLE) mRow++;
        else if (mRow == playerRow && mCol < playerCol
                && gameBoard[mRow][mCol + 1] != BoardCodes.OBSTACLE) mCol++;
        else if (mRow == playerRow && mCol > playerCol
                && gameBoard[mRow][mCol - 1] != BoardCodes.OBSTACLE) mCol--;
        else if (mRow > playerRow && gameBoard[mRow - 1][mCol] != BoardCodes.OBSTACLE) mRow--;
        else if (mRow != playerRow || mCol != playerCol)
        {
            if (gameBoard[mRow][mCol + 1] != BoardCodes.OBSTACLE) mCol++;
            else if(gameBoard[mRow][mCol - 1] != BoardCodes.OBSTACLE) mCol--;
            else if(gameBoard[mRow + 1][mCol] != BoardCodes.OBSTACLE) mRow++;
            else if(gameBoard[mRow - 1][mCol] != BoardCodes.OBSTACLE) mRow--;
        }
    }

    /**
     * Sets the row position of the <code>Zombie</code> object in the game board.
     *
     * @param row The row position of the <code>Zombie</code> object in the game board.
     */
    public void setRow(int row)
    {
        mRow = row;
    }

    /**
     * Gets the row position of the <code>Zombie</code> object in the game board.
     *
     * @return The row position of the <code>Zombie</code> object in the game board.
     */
    public int getRow()
    {
        return mRow;
    }

    /**
     * Sets the column position of the <code>Zombie</code> object in the game board.
     *
     * @param col The column position of the <code>Zombie</code> object in the game board.
     */
    public void setCol(int col)
    {
        mCol = col;
    }

    /**
     * Gets the column position of the <code>Zombie</code> object in the game board.
     *
     * @return The column position of the <code>Zombie</code> object in the game board.
     */
    public int getCol()
    {
        return mCol;
    }

}
