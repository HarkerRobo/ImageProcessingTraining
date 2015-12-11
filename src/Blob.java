import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Blob {
	private List<Point> points;
	
	public Blob() {
		points = new LinkedList<>();
	}
	public Blob(List<Point> pointList) {
		points = pointList;
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}
	
	public List<Point> getPoints() {
		return points;
	}
}
