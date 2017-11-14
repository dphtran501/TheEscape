package edu.orangecoastcollege.escapethecatcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity launches the game by creating the game board and characters and defining the
 * player's and enemy's in-game movements.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 7, 2017
 */
public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener
{
    private GestureDetector gestureDetector;

    //FLING THRESHOLD VELOCITY
    final int FLING_THRESHOLD = 500;

    //BOARD INFORMATION
    final int SQUARE = 180;
    final int OFFSET = 5;
    final int COLS = 8;
    final int ROWS = 8;
    final int gameBoard[][] = {{1, 1, 1, 1, 1, 1, 1, 1},
                               {1, 2, 2, 1, 2, 2, 1, 1},
                               {1, 2, 2, 2, 2, 2, 2, 1},
                               {1, 2, 1, 2, 2, 2, 2, 1},
                               {1, 2, 2, 2, 2, 2, 1, 1},
                               {1, 2, 2, 1, 2, 2, 2, 3},
                               {1, 2, 1, 2, 2, 2, 2, 1},
                               {1, 1, 1, 1, 1, 1, 1, 1}};
    private List<ImageView> allGameObjects;
    private Player player;
    private Zombie zombie;

    //LAYOUT AND INTERACTIVE INFORMATION
    private RelativeLayout activityGameRelativeLayout;
    private ImageView zombieImageView;
    private ImageView playerImageView;
    private TextView winsTextView;
    private TextView lossesTextView;

    private int exitRow;
    private int exitCol;

    //  WINS AND LOSSES
    private int wins;
    private int losses;

    private LayoutInflater layoutInflater;

    /**
     * Initializes <code>GameActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        activityGameRelativeLayout = (RelativeLayout) findViewById(R.id.activity_game);
        winsTextView = (TextView) findViewById(R.id.winsTextView);
        lossesTextView = (TextView) findViewById(R.id.lossesTextView);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        allGameObjects = new ArrayList<>();

        // Instantiate our gesture detector
        gestureDetector = new GestureDetector(this, this);

        startNewGame();
    }

    /**
     * Resets the game board and character positions.
     */
    private void startNewGame()
    {
        //TASK 1:  CLEAR THE BOARD (ALL IMAGE VIEWS)
        /*
        for (int i = 0; i < allGameObjects.size(); i++) {
            ImageView visualObj = allGameObjects.get(i);
            activityGameRelativeLayout.removeView(visualObj);
        }
        */
        for (ImageView iv : allGameObjects) activityGameRelativeLayout.removeView(iv);
        allGameObjects.clear();

        //TASK 2:  REBUILD THE  BOARD
        buildGameBoard();

        //TASK 3:  ADD THE PLAYERS
        createZombie();
        createPlayer();

        winsTextView.setText(getString(R.string.wins, wins));
        lossesTextView.setText(getString(R.string.losses, losses));
    }

    /**
     * Creates the game board.
     */
    private void buildGameBoard()
    {
        // Inflate the entire game board (obstacles and exit)
        // (everything but the player and zombie)

        // Loop through each
        ImageView viewToInflate;
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                viewToInflate = null;
                switch (gameBoard[row][col])
                {
                    case BoardCodes.OBSTACLE:
                        viewToInflate = (ImageView) layoutInflater.inflate(R.layout.obstacle_layout, null);
                        break;
                    case BoardCodes.EXIT:
                        viewToInflate = (ImageView) layoutInflater.inflate(R.layout.exit_layout, null);
                        exitRow = row;
                        exitCol = col;
                        break;
                }

                if (viewToInflate != null)
                {
                    // Set x and y position of the viewToInflate
                    viewToInflate.setX(col * SQUARE + OFFSET);
                    viewToInflate.setY(row * SQUARE + OFFSET);
                    // Add the view to the relative layout and list of ImageViews
                    activityGameRelativeLayout.addView(viewToInflate);
                    allGameObjects.add(viewToInflate);
                }
            }
        }

    }

    /**
     * Sets the zombie position on the game board.
     */
    private void createZombie()
    {
        // Determine where to place the Zombie (at game start)
        // Then, inflate the zombie layout
        int row = 2;
        int col = 4;
        zombieImageView = (ImageView) layoutInflater.inflate(R.layout.zombie_layout, null);
        zombieImageView.setX(col * SQUARE + OFFSET);
        zombieImageView.setY(row * SQUARE + OFFSET);
        // Add to relative layout and the list
        activityGameRelativeLayout.addView(zombieImageView);
        allGameObjects.add(zombieImageView);
        // Instantiate the Zombie
        zombie = new Zombie();
        zombie.setRow(row);
        zombie.setCol(col);
    }

    /**
     * Sets the player position on the game board.
     */
    private void createPlayer()
    {
        // Determine where to place the Player (at game start)
        // Then, inflate the player layout
        int row = 1;
        int col = 1;
        playerImageView = (ImageView) layoutInflater.inflate(R.layout.player_layout, null);
        playerImageView.setX(col * SQUARE + OFFSET);
        playerImageView.setY(col * SQUARE + OFFSET);
        // Add to relative layout and the list
        activityGameRelativeLayout.addView(playerImageView);
        allGameObjects.add(playerImageView);
        // Instantiate the Player
        player = new Player();
        player.setRow(row);
        player.setCol(col);
    }

    /**
     * Moves the player based on the fling gesture from the user.
     *
     * @param velocityX The fling velocity in the X direction.
     * @param velocityY The fling velocity in the Y direction.
     */
    private void movePlayer(float velocityX, float velocityY)
    {
        // This method gets called by the onFling event
        // Be sure to implement the move method in the Player (model) class

        float absX = Math.abs(velocityX);
        float absY = Math.abs(velocityY);
        String direction;

        // Determine which absolute velocity is greater (x or y)
        // If x is negative, move player left.  Else if x is positive, move player right.
        // If y is negative, move player down.  Else if y is positive, move player up.
        if (absX >= absY) direction = (velocityX < 0) ? "LEFT" : "RIGHT";
        else direction = (velocityY < 0) ? "UP" : "DOWN";

        player.move(gameBoard, direction);
        // Move the image view as well
        playerImageView.setX(player.getCol() * SQUARE + OFFSET);
        playerImageView.setY(player.getRow() * SQUARE + OFFSET);
        // Move the zombie as well
        zombie.move(gameBoard, player.getCol(), player.getRow());
        zombieImageView.setX(zombie.getCol() * SQUARE + OFFSET);
        zombieImageView.setY(zombie.getRow() * SQUARE + OFFSET);

        // Make 2 decisions:
        // 1) Check to see if Player has reached the exit row and col (WIN)
        // 2) Check to see if Player and Zombie are touching (LOSE)
        if (player.getCol() == exitCol && player.getRow() == exitRow)
        {
            wins++;
            startNewGame();
        } else if (player.getCol() == zombie.getCol() && player.getRow() == zombie.getRow())
        {
            losses++;
            startNewGame();
        }
    }

    /**
     * Implement this method to handle touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * User made contact with device. Every gesture begins with onDown.
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onDown(MotionEvent motionEvent)
    {
        return false;
    }

    /**
     * Down event where user does not let go, short duration of time.
     *
     * @param motionEvent The motion event triggering the touch.
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    /**
     * Similar to an onSingleTapConfirmed, but it could be part of a double tap.
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent)
    {
        return false;
    }

    /**
     * Down event, followed by a press and lateral movement, without letting go.
     *
     * @param motionEvent  The event where the scroll originated.
     * @param motionEvent1 The event where the scroll stopped.
     * @param distanceX    The distance in X direction (pixels).
     * @param distanceY    The distance in Y direction (pixels).
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) { return false; }

    /**
     * Down event, followed by a long hold.
     *
     * @param motionEvent The motion event triggering the touch.
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {}

    /**
     * Similar to a scroll, with faster velocity and user releases contact with device.
     *
     * @param motionEvent  The event where the scroll originated.
     * @param motionEvent1 The event where the scroll stopped.
     * @param velocityX    Velocity in the X direction.
     * @param velocityY    Velocity in the Y direction.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY)
    {
        movePlayer(velocityX, velocityY);
        return true;
    }
}
