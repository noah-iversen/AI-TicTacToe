package me.noahiversen.tictactoe;

public class PointAndScore {

	private Point point;
	private int score;
	
	public PointAndScore(Point point, int score) {
		this.point = point;
		this.score = score;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public int getScore() {
		return score;
	}
}
