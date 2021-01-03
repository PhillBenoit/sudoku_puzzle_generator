
public class Location {
	
	public int row, column, value;
	
	public Location() {
		row = 0;
		column = 0;
		value = 0;
	}
	
	public Location(int x, int y, int value) {
		row = x;
		column = y;
		this.value = value;
	}
	
	public void Print() {
		System.out.println("row: " + row + " column: " + column + " value: " + value);
	}

}
