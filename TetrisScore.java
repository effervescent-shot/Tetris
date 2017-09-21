package student.surname.name.project2;

import java.awt.Color;

import drawing_framework.Animatable;
import drawing_framework.AnimationCanvas;

public class TetrisScore implements Animatable{
	private static int score ;
	private AnimationCanvas scoreCanvas;
	
	public TetrisScore (int score, AnimationCanvas scoreCanvas){
		this.score= score;
		this.scoreCanvas= scoreCanvas;
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		TetrisScore.score = score;
	}

	@Override
	public void move(AnimationCanvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(AnimationCanvas scanvas) {
		scanvas.drawText("SCORE", 15.0, 150.0, Color.RED);
		scanvas.drawText(Integer.toString(score), 50.0, 100.0, Color.RED);
	}

}
