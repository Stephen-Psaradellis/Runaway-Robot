package AI1;

public class AI1 {
	protected static class Board{
		protected class Tile{
			int color; //0 = white, 1 = green, 2 = red
			String locID;
			public Tile below;
			public Tile side;
			public void setColor(int c) {color = c;}
			public int getColor() {return color;}
			public boolean valid() {return color == 2;}
			Tile(){color = 0;}
			Tile(int c){color = c;}
		}
		
		protected static class Tree{
			protected class Node{
				String loop;
				Node dChild;
				Node rChild;
				
				Node(String s){loop = s;}
				public void createChildren() {
					if(loop != null) {
						this.dChild = new Node(this.loop + "d");
						this.rChild = new Node(this.loop + "r");
					}
				}
				public void createChildrenRec(int i) {
					if (i > 0) {
						createChildren();
						dChild.createChildrenRec(i-1);
						rChild.createChildrenRec(i-1);
					}
					else {
						dChild = null;
						rChild = null;
					}
				}
			}
			Node root;
			Node position;
			Node open[];
			int openIndex;
			int openMax;
			Node closed[];
			int closedIndex;
			int closedMax;
			Tree(String s, int m){
				root = new Node(s);
				root.createChildrenRec(m);
				open = new Node[(int) Math.pow(2.0, (double) m+1)];
				closed = new Node[(int) Math.pow(2.0, (double) m+1)];
				openIndex = closedIndex = 0;
			}
			
			void DFSprep() {
				position = root;
				open[0] = root;
				open[1] = null;
				openIndex = 1;
				openMax = 0;
				closed[0] = null;
				closedIndex = 0;
				closedMax = 0;
				
			}
			String DFSsearch(int min) {
				if(openIndex == 0)
					return null;
				
				if(openIndex > openMax)
					openMax = openIndex;
				
				String ret = null;
				if(position.loop.length() >= min) {
					ret = position.loop;
				}
				
				for(int i = 0; i < openIndex; i++) {
					open[i] = open[i+1];
				}
				openIndex--;
				
				closed[closedIndex] = position;
				closedIndex++;
				if(closedIndex > closedMax)
					closedMax = closedIndex;
				
				if(position.rChild != null) {
					for(int i = openIndex; i >= 0; i--) {
						open[i+1] = open[i];
					}
					open[0] = position.rChild;
					openIndex++;
				}
				if(position.dChild != null) {
					for(int i = openIndex; i >= 0; i--) {
						open[i+1] = open[i];
					}
					open[0] = position.dChild;
					openIndex++;
					if(openIndex > openMax)
						openMax = openIndex;
					open[0] = position.dChild;
				}
				
				position = open[0];
				if(ret != null)
					return ret;
				return DFSsearch(min);
			}
		}
		
		Tile grid[][];
		Tree path;
		int min;
		int max;
		public Tile position;
		Board(int h, int w, int mi, int ma) {
			min = mi;
			max = ma;
			path = new Tree("",ma);
			grid = new Tile[h][w];
			int m,n;
			for(m = 0; m < h; m++) {
				for(n = 0; n < w; n++) {
					grid[m][n] = new Tile();
					grid[m][n].locID = m + "," + n;
				}
			}
			for(m = 0; m < h-1; m++) {
				for(n = 0; n < w-1; n++) {
					grid[m][n].side = grid[m][n+1];
					grid[m][n].below = grid[m+1][n];
				}
				grid[m][n].color = 1;
			}
			for(n=0;n<h;n++) {
				grid[m][n].color = 1;
			}
			position = grid[0][0];
		}
		public void setRed(int[] x, int[] y, int n) {
			for(int i = 0; i < n; i++)
				grid[y[i]][x[i]].color = 2;
		}
		public boolean trial(String s) {
			if(s == null || s == "")
				return false;
			boolean ok = true;
			position = grid[0][0];
			while(ok) {
				for(int i = 0; i < s.length(); i++) {
					if(position.color == 2) {
						ok = false;
						break;
					}
					else if(position.color == 1)
						return true;
					if(s.charAt(i) == 'd')
						position = position.below;
					else if(s.charAt(i) == 'r')
						position = position.side;
				}
			}
			return false; //extra case, should never be called
		}
		public String solve() {
			String s;
			path.DFSprep();
			while((s = path.DFSsearch(min)) != null) {
				if(trial(s) == true) {
					System.out.println("Completed with loop " + s + ", max open = " + path.openMax + ", max closed = " + path.closedMax);
					return s;
				}
			}
			System.out.println("Could not solve this board");
			return null;
		}
	}
	
	public static void main(String[] args) {
		//create boards 1-5
		Board[] tests = new Board[5];
		tests[0] = new Board(9,9,3,5);
		tests[1] = new Board(9,9,3,5);
		tests[2] = new Board(10,10,4,6);
		tests[3] = new Board(11,11,4,6);
		tests[4] = new Board(11,11,4,6);
		
		int x0[] = {7,3,7,5,6,5,7,4,0,0,1,3,7,0,5,6};
		int y0[] = {0,1,1,2,2,3,3,4,5,6,6,6,6,7,7,7};
		int x1[] = {3,4,7,0,1,3,6,0,1,3,4,7,0,2,6};
		int y1[] = {0,1,1,3,3,5,5,6,6,6,6,6,7,7,7};
		int x2[] = {6,7,4,0,5,3,6,8,2,0,2,6,7,1,5};
		int y2[] = {0,0,1,2,2,3,3,4,5,6,6,7,7,8,8};
		int x3[] = {2,3,5,2,4,7,4,7,9,2,7,8,0,4,0,1,3,2,8,0,3,5,8};
		int y3[] = {0,0,0,1,1,1,2,2,3,4,4,4,5,6,7,7,7,8,8,9,9,9,9};
		int x4[] = {3,6,7,2,3,5,9,4,1,3,4,5,0,1,7,1,2,4,8};
		int y4[] = {0,0,0,1,1,2,3,5,6,6,6,6,8,8,8,9,9,9,9};
		
		tests[0].setRed(x0, y0, x0.length);
		tests[1].setRed(x1, y1, x1.length);
		tests[2].setRed(x2, y2, x2.length);
		tests[3].setRed(x3, y3, x3.length);
		tests[4].setRed(x4, y4, x4.length);
		//solve boards 1-5
		
		for(int i = 0; i < 5; i++) {
			tests[i].solve();
		}
	}
}