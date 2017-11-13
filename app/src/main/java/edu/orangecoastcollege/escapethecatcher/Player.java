package edu.orangecoastcollege.escapethecatcher;

/**
 * This class represents the player in the game board.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 7, 2017
 */
public class Player
{
    private int mRow;
    private int mCol;

    /**
     * Moves the <code>Player</code> object in game board.
     *
     * @param gameBoard The game board that the <code>Player</code> object moves on.
     * @param direction The direction in which the <code>Player</code> object should move on the
     *                  game board.
     */
    public void move(int[][] gameBoard, String direction)
    {

        // Implement the logic for the move operation
        // If the gameBoard is obstacle free in the direction requested,
        // Move the player in the intended direction.  Otherwise, do nothing (player loses turn)

        switch (direction)
        {
            case "UP":
                if (gameBoard[mRow - 1][mCol] != BoardCodes.OBSTACLE) mRow--;
                break;
            case "DOWN":
                if (gameBoard[mRow + 1][mCol] != BoardCodes.OBSTACLE) mRow++;
                break;
            case "LEFT":
                if (gameBoard[mRow][mCol - 1] != BoardCodes.OBSTACLE) mCol--;
                break;
            case "RIGHT":
                if (gameBoard[mRow][mCol + 1] != BoardCodes.OBSTACLE) mCol++;
                break;
        }

    }

    /**
     * Sets the row position of the <code>Player</code> object in game board.
     *
     * @param row The row position of the <code>Player</code> object in game board.
     */
    public void setRow(int row)
    {
        mRow = row;
    }

    /**
     * Gets the row position of the <code>Player</code> object in game board.
     *
     * @return The row position of the <code>Player</code> object in game board.
     */
    public int getRow()
    {
        return mRow;
    }

    /**
     * Sets the column position of the <code>Player</code> object in game board.
     *
     * @param col The column position of the <code>Player</code> object in game board.
     */
    public void setCol(int col)
    {
        mCol = col;
    }

    /**
     * Gets the column position of the <code>Player</code> object in game board.
     *
     * @return The column position of the <code>Player</code> object in game board.
     */
    public int getCol()
    {
        return mCol;
    }

}
