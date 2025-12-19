package gr.auth.ee.dsproject.pacman ;

import gr.auth.ee.dsproject.node.Node ;
import java.util.ArrayList ;

public class Creature implements gr.auth.ee.dsproject.pacman.AbstractCreature
{

  public String getName( )
  {
    return "Mine" ;
  }

  private int step = 1 ;
  private boolean amPrey ;

  public Creature ( boolean isPrey )
  {
    amPrey = isPrey ;
  }

  // Calculates Pacman's next move
  public int calculateNextPacmanPosition( Room[ ][ ] Maze , int[ ] currPosition )
  {
	 int depth = 0 ;
	 // Create root node
	 Node Root = new Node( currPosition[ 0 ] , currPosition[ 1 ] , depth , -1 , Maze , null ) ;
	 // Call createSubTreePacman
	 createSubTreePacman( depth + 1 , Root , Maze , currPosition ) ;
	 // Create an ArrayList to store the minimum-valued grandchildren
	 ArrayList< Node > minimumsArray = new ArrayList< Node >( ) ;
	 
	 for( int j = 0 ; j < Root.getChildren( ).size( ) ; j ++ ) // Min-Max algorithm
	    {
		 // Initialize variables to track the grandchild with the minimum evaluation
	    double minimum = Root.getChildren( ).get( j ).getChildren( ).get( 0 ).getNodeEvaluation( ) ;
	    Node Minimum = Root.getChildren( ).get( j ).getChildren( ).get( 0 ) ;
		 // Find the minimum evaluation among grandchildren
	    for ( int i = 1 ; i < Root.getChildren( ).get( j ).getChildren( ).size( ) ; i++ )
	        {
	    	
	    	if ( Root.getChildren( ).get( j ).getChildren( ).get( i ).getNodeEvaluation( ) < minimum )
	    		
	    	   { minimum = Root.getChildren( ).get( j ).getChildren( ).get( i ).getNodeEvaluation( ) ;
	    	     
	    	     Minimum = Root.getChildren( ).get( j ).getChildren( ).get( i ) ; }  
	        }
	    	
	    minimumsArray.add( Minimum ) ; // Store the minimum evaluation node in the dynamic list
	    
	    }  
	 // Similarly, find the maximum among the minimum evaluations and return its move
	 Node Maximum = minimumsArray.get( 0 ) ;
	 double maximum = minimumsArray.get( 0 ).getNodeEvaluation( ) ;
	 
	 for ( int i = 1 ; i < minimumsArray.size( ) ; i++ )
	 {
		 if ( minimumsArray.get( i ).getNodeEvaluation( ) > maximum )
			 
		    { maximum = minimumsArray.get( i ).getNodeEvaluation( ) ;
		    
		      Maximum = minimumsArray.get( i ) ; }
	 }
	    	
	    return Maximum.getNodeMove( ) ;
  }    
// Create subtree of depth 1
  void createSubTreePacman( int depth , Node parent , Room[ ][ ] Maze , int[ ] currPacmanPosition )
  {
	  // Array containing Pacman's available moves
	  boolean[ ] availablePacmanMoves = new boolean[ 4 ] ;
	  // Determine and store available moves as booleans
	  for ( int i = 0 ; i < 4 ; i ++ ) { 
		 
		  if ( Maze[ currPacmanPosition[ 0 ] ][ currPacmanPosition[ 1 ] ].walls[ i ] == 1 ) 
			  
		     { availablePacmanMoves[ i ] = true ; }
		  
		  else { availablePacmanMoves[ i ] = false ; }		  
      }
	  
	  Room[ ][ ] mazeCopy = null ;
	  int[ ] nextPacmanPosition = new int[ 2 ] ;
	  // For each available move
	  for ( int i = 0 ; i < 4 ; i ++ ) {
		  
		  if ( availablePacmanMoves[ i ] )
		  {
		    mazeCopy = PacmanUtilities.copy ( Maze ) ; // Copy to Maze 
		    nextPacmanPosition = PacmanUtilities.evaluateNextPosition( mazeCopy , currPacmanPosition , i , PacmanUtilities.borders ) ;
			  
			PacmanUtilities.movePacman( mazeCopy , currPacmanPosition , nextPacmanPosition) ; // Move Pacman on the copied maze
			 		   
		    Node Children = new Node( nextPacmanPosition[ 0 ] , nextPacmanPosition[ 1 ] , depth , i , mazeCopy , parent ) ;//Create child node
			 	
			parent.getChildren( ).add( Children ) ; // Add child to the parent's children list
		    
		    createSubTreeGhosts( depth + 1 , Children , mazeCopy , Children.getCurrentGhostPos( ) ); // Create subtree of depth 2
		  }
	  }
  }
  
// Subtree of depth 2
  void createSubTreeGhosts ( int depth , Node parent , Room[ ][ ] Maze , int[ ][ ] currGhostsPosition )
  {
	  Room[ ][ ] mazeCopy = null ;
	  ArrayList< int[ ][ ] > allAvailableGhostMoves = PacmanUtilities.allGhostMoves( Maze , currGhostsPosition ) ;
	  
	 for ( int i = 0 ; i < allAvailableGhostMoves.size( ) ; i++ )
	 { 		 
		 mazeCopy = PacmanUtilities.copy( Maze );// Copy Maze
		 // Move ghosts
		 PacmanUtilities.moveGhosts( mazeCopy , currGhostsPosition , allAvailableGhostMoves.get( i ) ) ;
	     // Create grandchild nodes
		 Node Grandchildren = new Node( parent.getNodeX( ) , parent.getNodeY( ) , depth , parent.getNodeMove( ) , mazeCopy , parent ) ;		 
	     // Add grandchildren to the parent node
		 parent.getChildren( ).add( Grandchildren ) ;	
	 }
  }

  public int[] getPacPos (Room[][] Maze)
  {
    int[] pacmanPos = new int[2];
    for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
      for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
        if (Maze[i][j].isPacman()) {
          pacmanPos[0] = i;
          pacmanPos[1] = j;
          return pacmanPos;
        }
      }
    }
    return pacmanPos;
  }

  public boolean[] comAvPos (Room[][] Maze, int[][] currentPos, int[] moves, int currentGhost)
  {

    boolean[] availablePositions = { true, true, true, true };

    int[][] newPos = new int[4][2];

    for (int i = 0; i < 4; i++) {

      if (Maze[currentPos[currentGhost][0]][currentPos[currentGhost][1]].walls[i] == 0) {
        availablePositions[i] = false;
        continue;
      }

      if (PacmanUtilities.flagColision(Maze, currentPos[currentGhost], i)) {
        availablePositions[i] = false;
      }

      else if (currentGhost == 0)
        continue;

      else {
        switch (i) {
        case Room.WEST:
          newPos[currentGhost][0] = currentPos[currentGhost][0];
          newPos[currentGhost][1] = currentPos[currentGhost][1] - 1;
          break;
        case Room.SOUTH:
          newPos[currentGhost][0] = currentPos[currentGhost][0] + 1;
          newPos[currentGhost][1] = currentPos[currentGhost][1];
          break;
        case Room.EAST:
          newPos[currentGhost][0] = currentPos[currentGhost][0];
          newPos[currentGhost][1] = currentPos[currentGhost][1] + 1;
          break;
        case Room.NORTH:
          newPos[currentGhost][0] = currentPos[currentGhost][0] - 1;
          newPos[currentGhost][1] = currentPos[currentGhost][1];

        }

        for (int j = (currentGhost - 1); j > -1; j--) {
          switch (moves[j]) {
          case Room.WEST:
            newPos[j][0] = currentPos[j][0];
            newPos[j][1] = currentPos[j][1] - 1;
            break;
          case Room.SOUTH:
            newPos[j][0] = currentPos[j][0] + 1;
            newPos[j][1] = currentPos[j][1];
            break;
          case Room.EAST:
            newPos[j][0] = currentPos[j][0];
            newPos[j][1] = currentPos[j][1] + 1;
            break;
          case Room.NORTH:
            newPos[j][0] = currentPos[j][0] - 1;
            newPos[j][1] = currentPos[j][1];
            // break;
          }

          if ((newPos[currentGhost][0] == newPos[j][0]) && (newPos[currentGhost][1] == newPos[j][1])) {

            availablePositions[i] = false;
            continue;
          }

          if ((newPos[currentGhost][0] == currentPos[j][0]) && (newPos[currentGhost][1] == currentPos[j][1]) && (newPos[j][0] == currentPos[currentGhost][0])
              && (newPos[j][1] == currentPos[currentGhost][1])) {

            availablePositions[i] = false;
          }
        }
      }
    }

    return availablePositions;
  }

  public int comBestPos (boolean[] availablePositions, int[] pacmanPosition, int[] currentPos)
  {

    int[] newVerticalDifference = new int[2];
    for (int i = 0; i < 2; i++)
      newVerticalDifference[i] = currentPos[i] - pacmanPosition[i];

    int[] distanceSquared = new int[4];

    for (int i = 0; i < 4; i++) {
      if (availablePositions[i] == true) {

        switch (i) {
        case Room.WEST:
          newVerticalDifference[1]--;
          break;
        case Room.SOUTH:
          newVerticalDifference[0]++;
          break;
        case Room.EAST:
          newVerticalDifference[1]++;
          break;
        case Room.NORTH:
          newVerticalDifference[0]--;
          break;
        }
        distanceSquared[i] = newVerticalDifference[0] * newVerticalDifference[0] + newVerticalDifference[1] * newVerticalDifference[1];
      } else
        distanceSquared[i] = PacmanUtilities.numberOfRows * PacmanUtilities.numberOfRows + PacmanUtilities.numberOfColumns * PacmanUtilities.numberOfColumns + 1;
    }

    int minDistance = distanceSquared[0];
    int minPosition = 0;

    for (int i = 1; i < 4; i++) {
      if (minDistance > distanceSquared[i]) {
        minDistance = distanceSquared[i];
        minPosition = i;
      }
    }
    return minPosition;
  }

  public int[] calculateNextGhostPosition (Room[][] Maze, int[][] currentPos)
  {

    int[] moves = new int[PacmanUtilities.numberOfGhosts];

    int[] pacmanPosition = new int[2];

    pacmanPosition = getPacPos(Maze);
    for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
      moves[i] = comBestPos(comAvPos(Maze, currentPos, moves, i), pacmanPosition, currentPos[i]);
    }

    return moves;
  }

  public boolean[] checkCollision (int[] moves, int[][] currentPos)
  {
    boolean[] collision = new boolean[PacmanUtilities.numberOfGhosts];

    int[][] newPos = new int[4][2];

    for (int i = 0; i < moves.length; i++) {

      if (moves[i] == 0) {
        if (currentPos[i][1] > 0) {
          newPos[i][0] = currentPos[i][0];
          newPos[i][1] = currentPos[i][1] - 1;
        } else {
          newPos[i][0] = currentPos[i][0];
          newPos[i][1] = PacmanUtilities.numberOfColumns - 1;
        }

      } else if (moves[i] == 1) {
        if (currentPos[i][0] < PacmanUtilities.numberOfRows - 1) {
          newPos[i][0] = currentPos[i][0] + 1;
          newPos[i][1] = currentPos[i][1];
        } else {
          newPos[i][0] = 0;
          newPos[i][1] = currentPos[i][1];
        }
      } else if (moves[i] == 2) {
        if (currentPos[i][1] < PacmanUtilities.numberOfColumns - 1) {
          newPos[i][0] = currentPos[i][0];
          newPos[i][1] = currentPos[i][1] + 1;
        } else {
          newPos[i][0] = currentPos[i][0];
          newPos[i][1] = 0;

        }
      } else {
        if (currentPos[i][0] > 0) {
          newPos[i][0] = currentPos[i][0] - 1;
          newPos[i][1] = currentPos[i][1];
        } else {

          newPos[i][0] = PacmanUtilities.numberOfRows - 1;
          newPos[i][1] = currentPos[i][1];

        }
      }
      collision[i] = false;
    }

    for (int k = 0; k < moves.length; k++) {

    }

    for (int i = 0; i < moves.length; i++) {
      for (int j = i + 1; j < moves.length; j++) {
        if (newPos[i][0] == newPos[j][0] && newPos[i][1] == newPos[j][1]) {

          collision[j] = true;
        }

        if (newPos[i][0] == currentPos[j][0] && newPos[i][1] == currentPos[j][1] && newPos[j][0] == currentPos[i][0] && newPos[j][1] == currentPos[i][1]) {

          collision[j] = true;
        }
      }
    }
    return collision;
  }
}