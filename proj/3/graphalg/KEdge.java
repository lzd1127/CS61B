package graphalg;
import java.util.*;

public class KEdge implements Comparable {

	protected Object v1;
	protected Object v2;
	protected int w;

	public KEdge (Object obj1, Object obj2, int weight) {
		v1 = obj1;
		v2 = obj2;
		w = weight;
	}

	public int compareTo(Object o) {
		if (w > ((KEdge) o).w) {
			return 1;
		} else if (w < ((KEdge) o).w) {
			return -1;
		} else {
			return 0;
		}
	}
}