package student.surname.name.project2;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import drawing_framework.Animatable;
import drawing_framework.AnimationCanvas;
import drawing_framework.GUI;
/**
 * @author NEzgiYuceturk
 * @version 1.1
 *
 */

enum Direction {UP, DOWN, LEFT, RIGHT};
/*
 * Enums temporary values
 */

public class TetrisGame implements KeyListener {

	Random rand = new Random();
	private AnimationCanvas  gameCanvas;
	private AnimationCanvas scoreCanvas;
	private TetrisScore score = new TetrisScore(0, scoreCanvas);;
	private TetrisShapes tetris ;

	static int FRAME_RATE=5;
	private static final int TETRIS_SPEED =1;
	private static final int BH =20; 
	private static final int BW=10; 
	int whichShape;
	private boolean [][] board = new boolean [BH][BW];

	/**
	 * 
	 * @param gui Main connection with main class
	 * opens a window to play 
	 * Game automatically produce its canvas to move objects(shapes)
	 * as well as another canvas which is to record score
 	 * The game automatically sets its own board to trace shapes
 	 * This board is in boolean type and markes shapes' coordinates on it
 	 */
	public TetrisGame (GUI gui) {
		gameCanvas = new AnimationCanvas (250,500,25,FRAME_RATE) ;

		scoreCanvas = new AnimationCanvas (150,500,1,FRAME_RATE);
		scoreCanvas.setBackground(Color.DARK_GRAY);
		scoreCanvas.setOpaque(false);
		gui.addCanvas(gameCanvas);

		gui.addCanvas(scoreCanvas);
		scoreCanvas.addObject(score);

		gameCanvas.addKeyListener (this);
		gameCanvas.setFocusable (true);

		this.tetris = nextPiece();
		gameCanvas.addObject(tetris);

		gameCanvas.start();
		scoreCanvas.start();


	}
	/**
	 * 
	 * @param board canvas control board
	 */
	public void canvasControl ( boolean [][] board) {
		lineCheck(board);
	}
	/**
	 * 
	 * @param board canvas control board
	 * Checks all lines and clears if one is full
	 */
	public void lineCheck (boolean [][] board) {
		int howMany =0;
		int index;
		int i=0; 
		do {
			int count =0;
			for (int j=0; j<BW; j++ ) {
				if (board [i][j]) {
					count++;
				}
			}
			if (count==BW) {
				howMany++;
				score.setScore(score.getScore()+howMany*howMany*100);
				for (int k=i; k<BH-1;k++) {
					for (int j=0; j<BW; j++) {
						boolean temp = board[k+1][j];
						board[k][j] = temp;
					}
				}

			}
			else {
				i++;
			}
		} while (i<BH);


		howMany=0;
	}

	public void nextFallingPiece () {
		gameCanvas.removeObject(tetris);
		scoreCanvas.removeObject(score);
		scoreCanvas.addObject(score);
		tetris= nextPiece();
		gameCanvas.addObject(tetris);

	}
	public TetrisShapes nextPiece () {
		whichShape= rand.nextInt(7);
		return new TetrisShapes (this,whichShape , board) ;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			tetris.rotate();
		} else if (e.getKeyCode()== KeyEvent.VK_DOWN) {
			tetris.applyGravity();

		} else if ( e.getKeyCode() == KeyEvent.VK_LEFT) {
			tetris.RLmove(Direction.LEFT);
		}else if ( e.getKeyCode() == KeyEvent.VK_RIGHT) {
			tetris.RLmove(Direction.RIGHT);
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}


}