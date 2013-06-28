//TPoint.java

package tetris;

/*
 This is just a trivial "struct" type class --
 it simply holds an int x/y point for use by Tetris,
 and supports equals() and toString().
 We'll allow public access to x/y, so this
 is not an object really.
 */
public class TPoint {
	public int x;
	public int y;

	// Creates a TPoint based in int x,y
	public TPoint(int x, int y) {
		// questionable style but convenient --
		// params with same name as ivars

		this.x = x;
		this.y = y;
	}

	// Creates a TPoint, copied from an existing TPoint
	public TPoint(TPoint point) {
		this.x = point.x;
		this.y = point.y;
	}

	// Standard equals() override
	public boolean equals(Object other) {
		// standard two checks for equals()
		if (this == other) return true;
		if (!(other instanceof TPoint)) return false;

		// check if other point same as us
		TPoint pt = (TPoint)other;
		return(x==pt.x && y==pt.y);
	}

	// Standard toString() override, produce
	// human-readable String from object
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
