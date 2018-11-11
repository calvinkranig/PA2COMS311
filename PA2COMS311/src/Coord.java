

public class Coord {
	
		private int x;
		private int y;

		public Coord(int x, int y) {

			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return (x << 16) + y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj.getClass() != this.getClass()) {
				return false;
			} else {
				return (((Coord) obj).x == this.x && ((Coord) obj).y == this.y);

			}
		}
		
		public int x(){
			return this.x;
		}
		
		public int y(){
			return this.y;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public void setY(int y) {
			this.y = y;
		}
		
}

