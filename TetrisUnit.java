package student.surname.name.project2;

import java.awt.Color;

import drawing_framework.Animatable;
import drawing_framework.AnimationCanvas;

/**
 * Represents a square of a tetris shape (O,T,J..) in a Tetris Game
 * @author NEzgiYuceturk
 *
 */

public class TetrisUnit implements Animatable, Comparable<TetrisUnit> {
	private int x,y;
	
	public TetrisUnit () {
		this.x=0;
		this.y=0;
	}
	
	public TetrisUnit (int x, int y) {
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition (int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	/**
	 * Move in the direction one grid square
	 * @param dir Direction to move
	 */
	public void moveInDirection (Direction dir) {
//		if (dir == Direction.UP) {
//			y++;
		//} else 
		if (dir == Direction.DOWN) {
			y--;
		} else if (dir == Direction.LEFT) {
			x--;
		}else if (dir == Direction.RIGHT) {
			x++;
		}

	}

	public TetrisUnit copy () {
		return new TetrisUnit (this.getX(),this.getY());
		}

	@Override
	public void move(AnimationCanvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(AnimationCanvas canvas) {
		canvas.fillGridSquare(this.getX(), this.getY(), Color.red);
		
	}
	
	public boolean biggerY(TetrisUnit A) {
		return false;
	}
	
	public String toString () {
		return this.getX()+" - "+ this.getY();
	}

	@Override
	public int compareTo(TetrisUnit other) {
		int result=0;
		if (this.getY()<other.getY()) {
			result= -1;
		} else if (this.getY()==other.getY()) {
			if (this.getX() > other.getX()) {
				result =-1;
			} else {
				result= 1;
			}
		}else{
			result=1;
		}
		return result;
	}
}
