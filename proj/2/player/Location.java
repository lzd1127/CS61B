/* Location.java */

package player;

/**
 *  An implementation of a location on the game board.  Serves as a container.
 *  Contains the x and y coordinates.
 */

public class Location {
	protected int x;
	protected int y;

	// Creates an empty location.
	public Location() {
	}

	// Creates a location with the given x and y positions.
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// If the given location is the same as "this" location, returns true.
	public boolean equals(Object obj) {
		if ( ((Location) obj).x == x && ((Location) obj).y == y) {
			return true;
		}
		return false;
	}

	// Returns a string representation of the location.
	public String toString() {
	    String result = "(" + x + "," + y + ")";
	    return result;
	}
}