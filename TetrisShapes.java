package student.surname.name.project2;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import drawing_framework.Animatable;
import drawing_framework.AnimationCanvas;



/**
 * Regular shapes in classic Tetris Game
 *  Represented by a linked list
 *  Can also be added to an AnimationCanvas
 *  @author NEzgiYuceturk
 */
public class TetrisShapes implements Animatable {

	private boolean AImode = false;
	TetrisGame game;
	AIGame game2;
	ArrayList<TetrisUnit> shape;
	public int whichShape;

	private int frameCounter;
	private Direction direction;
	public final TetrisUnit CENTER = new TetrisUnit(5,18);
	public boolean stable;
	private int rotatedPosition;
	private boolean [][] board;
	private Color [] colorful = {Color.BLUE,Color.CYAN,Color.GREEN, Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED, Color.YELLOW};
	Random rand = new Random ();
	private Color color;
	/**
	 * Constructor
	 * @param game Game that tetris shapes played
	 * @param moveFrameInterval Shapes will move once per moveFrameInterval frames ?????
	 * @param whichShape random number to reach different shapes methods  
	 */

	public TetrisShapes (TetrisGame game, int whichShape, boolean [][] board) {
		this.board= board;
		this.game= game;
		this.whichShape = whichShape;
		if (whichShape==0) {
			shape = OShape ();
		} else if ( whichShape ==1) {
			shape = TShape();
		} else if ( whichShape ==2) {
			shape = IShape();
		} else if ( whichShape ==3) {
			shape = SShape();
		} else if ( whichShape ==4) {
			shape = ZShape();
		} else if ( whichShape ==5) {
			shape = LShape();
			setL();
		} else if ( whichShape ==6) {
			shape = JShape();
		}


		stable = false;
		rotatedPosition =0; // rearrange it later on
		color = colorful [rand.nextInt(8)];
	} 





	public TetrisShapes( AIGame aigame,
			int whichShape, boolean [][] board2) {
		this.board= board2;
		this.game2= aigame;
		this.AImode= true;
		if (whichShape==0) {
			shape = OShape ();
			setO();
		} else if ( whichShape ==1) {
			shape = TShape();
			setT();
		} else if ( whichShape ==2) {
			shape = IShape();
			setI();
		} else if ( whichShape ==3) {
			shape = SShape();
			setS();
		} else if ( whichShape ==4) {
			shape = ZShape();
			setZ();
		} else if ( whichShape ==5) {
			shape = LShape();
			setL();
		} else if ( whichShape ==6) {
			shape = JShape();
			setJ();
		}
		//frameCounter =0;

		stable = false;
		rotatedPosition =0; // rearrange it later on
		color = colorful [rand.nextInt(8)];

	}

	public boolean mustStop () {
		for (TetrisUnit unit: shape) { 

			if (unit.getY()-1<0 ) {
				stable = true;

				while (!shape.isEmpty()) {
					board [shape.get(0).getY()][shape.get(0).getX()] = true;
					shape.remove(0);
				} 

				if(AImode) {
					game2.canvasControl(board);
					game2.nextFallingPiece();
				} else {
					game.canvasControl(board);
					game.nextFallingPiece();
				}
				return true;
			} else if (board[unit.getY()-1][unit.getX()] ) {
				while (!shape.isEmpty()) {
					board [shape.get(0).getY()][shape.get(0).getX()] = true;
					shape.remove(0);
				} 

				if(AImode) {
					game2.canvasControl(board);
					game2.nextFallingPiece();
				} else {
					game.canvasControl(board);
					game.nextFallingPiece();
				}
				return true;
			} 

		}
		return false;
	}


	@Override
	public void draw(AnimationCanvas canvas) {
		if (!gameOver()) {
		for (TetrisUnit unit : shape) {
			canvas.fillGridSquare(unit.getX(), unit.getY(), this.color);
		}

		for (int i=0; i<board.length; i++) {
			for (int j=0 ; j<board[0].length; j++) {
				if (board [i][j]) {
					canvas.fillGridSquare(j, i, Color.GRAY);
				}
			}
		}
		} else  {
			canvas.drawText("GAME OVER", 1, 10, Color.RED);
		}
	}

	public void RLmove (Direction dir) {
		boolean canGo =true;
		if (dir == Direction.RIGHT ) {
			for (TetrisUnit unit : shape) {
				if (unit.getX()+1>board[0].length-1) {
					canGo= false;
				} 
			}
			if(canGo) {
				for (TetrisUnit unit : shape) {
					unit.setX (unit.getX()+1);
				}
			}
		} else if (dir == Direction.LEFT) {
			for (TetrisUnit unit : shape) {
				if (unit.getX()-1<0) {
					canGo= false;
				} 
			}
			if(canGo) {
				for (TetrisUnit unit : shape) {
					unit.setX (unit.getX()-1);
				}
			}
		}
	}

	public void rotate() {

		TetrisUnit head = new TetrisUnit (shape.get(0).getX(),shape.get(0).getY());

		//ArrayList<TetrisUnit> RS = new ArrayList<>();

		for (int i=0; i<4 ; i++) {
			int dx = shape.get(i).getX()- head.getX();
			int dy = shape.get(i).getY()- head.getY();

			shape.get(i).setX(-dy+head.getX());
			shape.get(i).setY(dx+head.getY());
		}

		for (TetrisUnit unit: shape) {
			if(unit.getX()<0 ) {
				while ( unit.getX()<0) {
					for (TetrisUnit u2 : shape) {
						u2.setX(u2.getX()+1);
					}
				}	
			}
			else if (unit.getX()>9) {
				while( unit.getX()>9) {
					for (TetrisUnit u2 : shape) {
						u2.setX(u2.getX()-1);
					}
				}
			}
		}
	}
	@Override
	public void move(AnimationCanvas canvas) {
		applyGravity();

	}

	public void applyGravity()
	{
		if (!mustStop()) {
			for (TetrisUnit unit : shape) {
				unit.setY(unit.getY()-1);
			} 
		}
	}

	public boolean gameOver () {
		for (int i=0; i<board[0].length; i++) {
			if (board[board.length-1][i]) {
				return true;
			}
		}
		return false;
	}





	public ArrayList<TetrisUnit> OShape() {
		ArrayList<TetrisUnit> OShape = new ArrayList<TetrisUnit> () ;
		OShape.add(CENTER);
		OShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()));
		OShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()-1));
		OShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()-1));
		return OShape;
	}

	public ArrayList<TetrisUnit> TShape() {
		ArrayList<TetrisUnit> TShape = new ArrayList<TetrisUnit> () ;
		TShape.add(CENTER);
		TShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()));
		TShape.add(new TetrisUnit (CENTER.getX()+1,CENTER.getY()));
		TShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()-1));
		return TShape;
	}

	public ArrayList<TetrisUnit> ZShape() {
		ArrayList<TetrisUnit> ZShape = new ArrayList<TetrisUnit> () ;
		ZShape.add(CENTER);
		ZShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()));
		ZShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()-1));
		ZShape.add(new TetrisUnit (CENTER.getX()+1,CENTER.getY()-1));
		return ZShape;
	}

	public ArrayList<TetrisUnit> SShape() {
		ArrayList<TetrisUnit> SShape = new ArrayList<TetrisUnit> () ;
		SShape.add(CENTER);
		SShape.add(new TetrisUnit (CENTER.getX()+1,CENTER.getY()));
		SShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()-1));
		SShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()-1));
		return SShape;
	}

	public ArrayList<TetrisUnit> IShape() {
		ArrayList<TetrisUnit> IShape = new ArrayList<TetrisUnit> () ;
		IShape.add(CENTER);
		IShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()+1));
		IShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()-1));
		IShape.add(new TetrisUnit (CENTER.getX(),CENTER.getY()-2));
		return IShape;
	}

	public ArrayList<TetrisUnit> LShape() {
		ArrayList<TetrisUnit> LShape = new ArrayList<TetrisUnit> () ;
		LShape.add(CENTER);
		LShape.add(new TetrisUnit (CENTER.getX()+1,CENTER.getY()+1));
		LShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()));
		LShape.add(new TetrisUnit (CENTER.getX()+1,CENTER.getY()));
		return LShape;
	}

	public ArrayList<TetrisUnit> JShape() {
		ArrayList<TetrisUnit> JShape = new ArrayList<TetrisUnit> () ;
		JShape.add(CENTER);
		JShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()+1));
		JShape.add(new TetrisUnit (CENTER.getX()+1,CENTER.getY()));
		JShape.add(new TetrisUnit (CENTER.getX()-1,CENTER.getY()));
		return JShape;
	}

	public void translateUntil (int C) {
		int temp = shape.get(0).getX();
		for (TetrisUnit unit: shape){
			unit.setX(unit.getX()-temp+C);
		}
		for (TetrisUnit unit: shape) {
			if(unit.getX()<0 ) {
				while ( unit.getX()<0) {
					for (TetrisUnit u2 : shape) {
						u2.setX(u2.getX()+1);
					}
				}	
			}
			else if (unit.getX()>9) {
				while( unit.getX()>9) {
					for (TetrisUnit u2 : shape) {
						u2.setX(u2.getX()-1);
					}
				}
			}

		}
	}

	public void setO() {

		ArrayList<Point> possible = new ArrayList<Point> ();
		for (int i=2 ; i< board.length; i++) {
			for (int j=1; j<board[0].length; j++) {

				if (!board[i][j] && !board[i][j-1] && !board[i-1][j]&& !board[i-1][j-1] && board[i-2][j-1] && board[i-2][j]) {
					possible.add(new Point (j,i));
				}

			}
		}

		if (!possible.isEmpty()) {
			int x=0;
			int y=19;
			for (Point p : possible) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		} else {
			translateUntil(rand.nextInt(7)+2);
		}


	} 

	public void setZ() {
		ArrayList<Point> possible = new ArrayList<Point> ();
		for (int i=0 ; i< board.length-2; i++) {
			for (int j=1; j<board[0].length; j++) {

				if (board[i][j] && !board[i+1][j]&& !board[i+2][j] && !board[i][j-1] && !board[i+1][j-1]) {
					possible.add(new Point (j-1,i));
				}
			}

			if (board[i][1] && !board[i][0] && !board[i+1][0] && !board[i+1][1]) {
				possible.add(new Point (0,i));
			}  // canvas ýn sol kenarý düzelemini kontrol ediyor.
		}

		if (!possible.isEmpty()) {
			rotate();
			int x=0;
			int y=19;
			for (Point p : possible) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y=(int)p.getY();
				}
			}
			translateUntil(x);
		} else {

			for (int i=0 ; i< board.length-1; i++) {
				for (int j=2; j<board[0].length-1; j++) {

					if (board[i][j] && !board[i+1][j]&& !board[i][j-2] && !board[i][j-1] && !board[i+1][j+1]) {
						possible.add(new Point (j+1,i));
					}
				}
			}

			if (!possible.isEmpty()) {
				int x=0;
				int y=19;
				for (Point p : possible) {
					if(p.getY()<y) {
						x= (int)p.getX();
						y=(int)p.getY();
					}
				}
				translateUntil(x);
			} else {
				translateUntil(rand.nextInt(8)+1);
			}
		}


	} 

	public void setS() { 
		ArrayList<Point> possible = new ArrayList<Point> ();
		for (int i=0; i< board.length-2; i++) {
			for (int j=0; j<board[0].length-1; j++) {

				if (board[i][j]&& !board[i][j+1] && !board[i+1][j+1] && !board[i+1][j] && !board[i+2][j] ) { //zaten sýfýra da koyabilir su an
					possible.add(new Point (j,i));
				}
			}

			if (!board[i][9] && board[i][8] && !board[i+1][9] && !board[i+1][8]) { // canvasýn sað kenarýný kontrol ediyor
				possible.add(new Point(8,i));
			}
		}

		if (!possible.isEmpty()) {
			rotate();
			int x=0;
			int y=19;
			for (Point p : possible) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		} else {

			for (int i=0 ; i< board.length-1; i++) {
				for (int j=2; j<board[0].length; j++) {

					if (board[i][j]&& !board[i][j-1] && !board[i][j-2] && !board[i+1][j] ) {
						possible.add(new Point (j-1,i));
					}
				}

			}
			if (!possible.isEmpty()) {
				int x=0;
				int y=19;
				for (Point p : possible) {
					if(p.getY()<y) {
						x= (int)p.getX();
						y= (int)p.getY();
					}
				}
				translateUntil(x);
			} else {
				translateUntil(rand.nextInt(8)+1);
			}
		}

	} 

	public void setL() {
		ArrayList<Point> possible = new ArrayList<Point> ();
		for (int i=0; i<board.length-2; i++) {
			for (int j=0; j<board[0].length-1; j++) {
				if (board[i][j] && board[i+1][j] && !board[i][j+1] && !board[i+1][j+1] && !board[i+2][j] && !board[i+2][j+1]) {
					possible.add(new Point (j+1,i));
				}
			}
			if (board[i][8]&& board[i+1][8] && !board[i][9] && !board[i+1][9] && !board[i+2][8] && board[i+2][9]) {
				possible.add(new Point (9,i));
			}
		}

		if (!possible.isEmpty()) {
			rotate();
			int x=0;
			int y=19;
			for (Point p : possible) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		} else {
			for (int i=0; i<board.length-1; i++) {
				for (int j=1; j<board[0].length-1; j++) {
					if (board[i][j] && board[i][j+1] && !board[i][j-1] && !board[i+1][j+1] && !board[i+1][j] && board[i+1][j-1]) {
						possible.add(new Point (j,i));
					}

					if (!board[i][0] && board[i][1] && board[i][2] && !board[i+1][1] && !board[i+1][2] && !board[i+1][0]) {
						possible.add (new Point (0,i));
					}
				}

				if (!possible.isEmpty()) {
					rotate();
					rotate();
					int x=0;
					int y=19;
					for (Point p : possible) {
						if(p.getY()<y) {
							x= (int)p.getX();
							y= (int)p.getY();
						}
					}
					translateUntil(x);
				}	else {
					translateUntil(rand.nextInt(8)+1);
				}
			}
		}

	}

	public void setJ() {
		ArrayList<Point> possible = new ArrayList<Point>();
			for (int i=0; i<board.length-2; i++) {
				for (int j=1; j< board[0].length; j++) {
					if (board[i][j] && board[i+1][j] && !board[i+2][j] && !board[i+2][j-1] && !board[i+1][j-1] && board[i][j-1]) {
						possible.add(new Point(j-1,i));
					}
				}
				if (!board[i][0] && board[i][1]&& !board[i+1][0]&& board[i+1][1] && !board[i+2][0]&& !board[i+2][1]) {
					possible.add(new Point (0,i));
				}
			}
			
			if (!possible.isEmpty()) {
				rotate();
				rotate();
				rotate();
				int x=0;
				int y=19;
				for (Point p : possible) {
					if(p.getY()<y) {
						x= (int)p.getX();
						y= (int)p.getY();
					}
				}
				translateUntil(x);
			} else {
				for (int i=0; i<board.length-1; i++) {
					for (int j=1; j<board[0].length-1; j++) {
						if(board[i][j] && board[i][j-1] && !board[i][j+1] && !board[i+1][j] && !board[i+1][j+1] && !board[i+1][j-1]) {
							possible.add(new Point(i,j));
						}
					}
					if (!board[i][9]&& board[i][8] && board[i][7] && !board[i+1][9] && !board[i+1][8] && !board[i+1][7]) {
						possible.add(new Point (8,i));
					}
				}
				
				if (!possible.isEmpty()) {
					rotate();
					rotate();
					int x=0;
					int y=19;
					for (Point p : possible) {
						if(p.getY()<y) {
							x= (int)p.getX();
							y= (int)p.getY();
						}
					}
					translateUntil(x);
				} else {
					translateUntil(rand.nextInt(8)+1);
				}
			}

	}

	public void setI() {
		ArrayList<Point> possible = new ArrayList<Point> ();

		for (int i=0 ; i< board.length-3; i++) {
			for (int j=1; j<board[0].length-1; j++) {
				if(!board[i][j]) {
					if (board[i+1][j+1]&&board[i+2][j+1] && board[i+3][j+1] && board[i+1][j-1] && board[i+2][j-1] && board[i+3][j-1]) {
						possible.add(new Point (j,i));
					}
				}
			}

			if(!board [i][0] && !board[i+1][0] && !board[i+2][0] && board [i][1] && board[i+1][1]) {
				possible.add(new Point (0,i));
			}if (!board[i][9] && !board[i+1][9] && !board[i+2][9]) {
				possible.add(new Point (9,i));
			}

		} 

		if (possible.isEmpty()) {
			for (int i=0 ; i< board.length-2; i++) {
				for (int j=1; j<board[0].length-1; j++) {
					if(!board[i][j]) {
						if (board[i][j+1] && board[i+1][j+1]&&board[i+2][j+1] && board[i][j-1] && board[i+1][j-1] && board[i+2][j-1]) {
							possible.add(new Point (j,i));
						}
					}
				}
			}
		}

		if (!possible.isEmpty()) {
			int x=0;
			int y=19;
			for (Point p : possible) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		} else {
			for (int i=0; i<board.length-1 ;i++) {
				for(int j=1; j<board[0].length-2 ; j++){
					if (!board[i+1][j] && !board[i+1][j-1] && !board[i+1][j+1] && !board[i+1][j+2]) {
						possible.add(new Point (j,i));
					}
				}
			}

			if (!possible.isEmpty()) {
				int x=0;
				int y=19;
				for (Point p : possible) {
					if(p.getY()<y) {
						x= (int)p.getX();
						y= (int)p.getY();
					}
				}
				rotate();
				translateUntil(x);
			} else {
				rotate();
				translateUntil(rand.nextInt(7)+1);
			}
		}

	}


	public void setT() {
		ArrayList<Point> possible1 = new ArrayList<Point> ();
		ArrayList<Point> possible2 = new ArrayList<Point> ();
		ArrayList<Point> possible3 = new ArrayList<Point> ();

		for (int i=0 ; i< board.length-2; i++) {
			for (int j=1; j<board[0].length-1; j++) {

				if (!board[i][j] && board[i][j+1] && !board[i+1][j+1] && board[i][j-1] && !board[i+1][j-1] && !board[i+1][j])  {
					possible3.add(new Point (j,i));
					System.out.println("kbdjknvlk");
				} else if (board[i][j] && board[i+1][j+1] && board[i][j+1] && !board[i+1][j]&& !board[i+2][j] ) {
					possible1.add(new Point (j,i));
				} else if (board[i][j] && board[i+1][j-1] && board[i][j-1] && !board[i+1][j] && !board[i+2][j]) {
					possible2.add(new Point (j,i));
				}
			}

			if (!board[i][0] && board[i][1] && !board[i+1][1] && !board[i+2][1] && !board[i+1][0] && !board[i+2][0]) {
				possible1.add(new Point(0,i));
			} if (!board[i][9] && board[i][8] && !board[i+1][8] && !board[i+2][8] && !board[i+1][9] && !board[i+2][9]) {
				possible2.add(new Point(9,i));
			}
		}

		if (!possible3.isEmpty()) {
			int x=0;
			int y=19;
			for (Point p : possible1) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		} else if (!possible1.isEmpty()) {
			rotate();
			int x=0;
			int y=19;
			for (Point p : possible1) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		}
		else if (!possible2.isEmpty()) {
			rotate();
			rotate();
			rotate();
			int x=0;
			int y=19;
			for (Point p : possible1) {
				if(p.getY()<y) {
					x= (int)p.getX();
					y= (int)p.getY();
				}
			}
			translateUntil(x);
		} else {
			rotate();
			rotate();
			translateUntil(rand.nextInt(8)+1);
		}
	}
}
























