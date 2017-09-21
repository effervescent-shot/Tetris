package student.surname.name.project2;

import java.awt.Color;
import java.util.Random;

import drawing_framework.AnimationCanvas;
import drawing_framework.GUI;

public class AIGame {
	Random rand = new Random();
	private AnimationCanvas  gameCanvas;
	private AnimationCanvas scoreCanvas;
	private TetrisScore score = new TetrisScore(0, scoreCanvas);
	private TetrisShapes tetris ;

	static int FRAME_RATE=15;
	private static final int TETRIS_SPEED =1;
	private static final int BH =20; 
	private static final int BW=10; 
	int whichShape;

	private boolean [][] board = new boolean [BH][BW];


	public AIGame (GUI gui) {

		gameCanvas = new AnimationCanvas (250,500,25,FRAME_RATE) ;
		scoreCanvas = new AnimationCanvas (150,500,1,FRAME_RATE);
		scoreCanvas.setBackground(Color.DARK_GRAY);
		scoreCanvas.setOpaque(false);
		gui.addCanvas(gameCanvas);
		gui.addCanvas(scoreCanvas);
		scoreCanvas.addObject(score);
		
		this.tetris = nextPiece();
		gameCanvas.addObject(tetris);
		
		gameCanvas.start();
		scoreCanvas.start();
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<6; j++) {
				board[i][j]=true;
			}
		}
		board[0][7] = true;
		board[0][8] = true;
		board[0][9] = true;
	}


	public void canvasControl ( boolean [][] board) {
		lineCheck(board);
	}

	public void lineCheck (boolean [][] board) {
		int howMany=0;
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
		whichShape = rand.nextInt(7);
		return new TetrisShapes (this, whichShape, board) ;

	}

	public void RLmove(int dx) {

	}
}
