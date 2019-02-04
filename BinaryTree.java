import java.util.ArrayList;

class BinaryTree 
{ 
    // Root of Binary Tree 
    Coordinate root; 
  
    // Constructors 
    BinaryTree(int key) 
    { 
        root = new Coordinate(); 
    } 
  
    BinaryTree() 
    { 
        root = null; 
    } 
    
    
    public BinaryTree growTree(int[][] grid)
    {
    	BinaryTree tree = new BinaryTree();
    	Maze maze = new Maze(grid);
    	Coordinate current = new Coordinate(0,0);
    	tree.root = current;
    	ArrayList<Coordinate> validMoves = maze.validMoves(grid,current);
    	
    	while(maze.validMoves(grid,current) != null) {
    		tree.root.left = maze.validMoves(grid,current);
    		
    	}
    	
    	tree.root = null;
    	
    	
    	
		return tree;	
    }
    

    
  
    public static void main(String[] args) 
    { 
        BinaryTree tree = new BinaryTree(); 
  

        tree.root = new Node(1); 

        tree.root.left = new Node(2); 
        tree.root.right = new Node(3); 

        tree.root.left.left = new Node(4); 
    } 
} 