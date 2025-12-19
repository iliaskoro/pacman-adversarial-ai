package gr.auth.ee.dsproject.node ;

import gr.auth.ee.dsproject.pacman.PacmanUtilities ;
import gr.auth.ee.dsproject.pacman.Room ;

import java.util.ArrayList ;

public class Node
{
  int nodeX ;
  int nodeY ;
  int depth ;
  int nodeMove ;
  double nodeEvaluation ;
  int[ ][ ] currentGhostPos ;
  int[ ][ ] flagPos ;
  boolean[ ] currentFlagStatus ;

  Node parent ;
  ArrayList< Node > children = new ArrayList< Node >( ) ;

  // Constructor with the parameters used to initialize the class variables
  public Node ( int X , int Y , int D , int M , Room[ ][ ] Maze , Node P )
  {
  nodeX = X ;
  nodeY = Y ;
  depth = D ;
  nodeMove = M ; 
  currentGhostPos = findGhosts( Maze ) ;
  flagPos = findFlags( Maze ) ;
  currentFlagStatus = checkFlags( Maze ) ;
  parent = P ;
  nodeEvaluation = evaluate( ) ;
  }
  
  // Getter for nodeX
  public int getNodeX( )
  {
	return nodeX ;
  }

  // Getters for the variables used
  public int getNodeY( )
  {
	return nodeY ;
  }

  public int getDepth( )
  {
	return depth ;
  }

  public int getNodeMove( )
  {
	return nodeMove ;
  }

  public double getNodeEvaluation( )
  {
	return nodeEvaluation ;
  }

  public int[ ][ ] getCurrentGhostPos( )
  {
	return currentGhostPos ;
  }

  public int[ ][ ] getFlagPos( )
  {
	return flagPos ;
  }

  public boolean[ ] getCurrentFlagStatus( )
  {
	return currentFlagStatus ;
  }

  public Node getParent( )
  {
	return parent ;
  }

  public ArrayList< Node > getChildren( )
  {
	return children ;
  }

  // This method finds the positions of the ghosts on the board
  private int[ ][ ] findGhosts( Room[ ][ ] Maze )
  {
	  int[ ][ ] ghostsArray = new int[ 4 ][ 2 ] ;
	  int ghostsFound = 0 ;
	  
	  for (int y = 0 ; y < PacmanUtilities.numberOfColumns ; y ++ )
	  {
		  for (int x = 0 ; x < PacmanUtilities.numberOfRows ; x ++ )
		  {
			  if ( Maze[ x ][ y ].isGhost( ) )
			  { ghostsArray [ ghostsFound ] [ 0 ] = x ;
			    ghostsArray [ ghostsFound ] [ 1 ] = y ;  
			    ghostsFound++ ; }
		  }
	  }
	  return ghostsArray ;  
  }
// This method finds the positions of the flags on the board
  private int[ ][ ] findFlags( Room[ ][ ] Maze )
  {
	  int[ ][ ] flagsArray = new int[ 4 ][ 2 ] ; 
	  int flagsFound = 0 ;
	  
	  for ( int y = 0 ; y < PacmanUtilities.numberOfColumns ; y ++ )
	  {
		  for ( int x = 0 ; x < PacmanUtilities.numberOfRows ; x ++ )
		  {
			  if ( Maze[ x ][ y ].isFlag( ) )
			  { flagsArray [ flagsFound ][ 0 ] = x ;
			    flagsArray [ flagsFound ][ 1 ] = y ; 
			    flagsFound++ ; }
		  }
	  }
	  return flagsArray ;  
  }
// This method checks whether each flag has been captured	  
  private boolean[ ] checkFlags( Room[ ][ ] Maze )
  {
	  boolean[ ] flagStatus = new boolean[ 4 ] ;
	  
	  for ( int i = 0 ; i < 4 ; i ++ ) 
	  {
		  flagStatus[ i ] = Maze[ findFlags( Maze )[ i ][ 0 ] ][ findFlags( Maze )[ i ][ 1 ] ] . isCapturedFlag( ) ;
	  }
	  return flagStatus ;
  }
// Evaluation function
  private double evaluate( )
  {
    double evaluation ;
    
    //////////////////////////////////////////////////////////Ghosts////////////////////////////////////////////////////////
    int[ ] manhattanDistanceFromGhosts = new int[ 4 ] ; // Stores the Manhattan distance of Pacman from each ghost
    double[ ] manhattanDistanceFromGhostsEvaluations = new double[ 4 ] ;
    
    for ( int i = 0 ; i < 4 ; i++ )
    { manhattanDistanceFromGhosts[ i ] = ( Math.abs( nodeX - currentGhostPos[ i ][ 0 ] ) + Math.abs( nodeY - currentGhostPos[ i ][ 1 ] ) ) ; }
    
    for ( int i = 0 ; i < 4 ; i++ )
    {		
    	if ( manhattanDistanceFromGhosts[ i ] == 0 )
    	{ manhattanDistanceFromGhostsEvaluations[ i ] = - 1000 ; }
    	
    	else if ( manhattanDistanceFromGhosts[ i ] == 1 )
    	{ manhattanDistanceFromGhostsEvaluations[ i ] = - 750 ; }
    	
    	else if ( manhattanDistanceFromGhosts[ i ] == 2 )
    	{ manhattanDistanceFromGhostsEvaluations[ i ] = - 500 ; }
    	
    	else if ( manhattanDistanceFromGhosts[ i ] == 3 )
    	{ manhattanDistanceFromGhostsEvaluations[ i ] = - 250 ; }
    	
    	else if ( manhattanDistanceFromGhosts[ i ] == 4 )
    	{ manhattanDistanceFromGhostsEvaluations[ i ] = - 100 ; }}
    
    double manhattanDistanceFromGhostsEvaluation ;
    manhattanDistanceFromGhostsEvaluation = manhattanDistanceFromGhostsEvaluations[ 0 ] + manhattanDistanceFromGhostsEvaluations[ 1 ] + manhattanDistanceFromGhostsEvaluations[ 2 ] + manhattanDistanceFromGhostsEvaluations[ 3 ] ;
    
    int[ ] manhattanDistanceFromFlags = new int[ 4 ] ;
    double[ ] manhattanDistanceFromFlagsEvaluations = new double[ 4 ] ;
    
    for ( int i = 0 ; i < 4 ; i++ )
    { manhattanDistanceFromFlags[ i ] = ( Math.abs( nodeX - flagPos[ i ][ 0 ] ) + Math.abs( nodeY - flagPos[ i ][ 1 ] ) ) ; }
    
    for ( int i = 0 ; i < 4 ; i++ )
    {
    		if ( currentFlagStatus[ i ] == false )
    		
    			if ( manhattanDistanceFromFlags[ i ] == -1 )
    	    	{ manhattanDistanceFromFlagsEvaluations[ i ] = 400000000 ; }
    		
    			else if ( manhattanDistanceFromFlags[ i ] == 0 )
	    	{ manhattanDistanceFromFlagsEvaluations[ i ] = 100 ; }
    		
    			else if ( manhattanDistanceFromFlags[ i ] == 1 )
    	    	{ manhattanDistanceFromFlagsEvaluations[ i ] = 100 ; }
    			else
    	{ manhattanDistanceFromFlagsEvaluations[ i ] = ( - 50 / 35 ) * ( manhattanDistanceFromFlags[ i ] - 39 ) ; }	
    }
    
    double manhattanDistanceFromFlagsEvaluation ;
    manhattanDistanceFromFlagsEvaluation = manhattanDistanceFromFlagsEvaluations[ 0 ] + manhattanDistanceFromFlagsEvaluations[ 1 ] + manhattanDistanceFromFlagsEvaluations[ 2 ] + manhattanDistanceFromFlagsEvaluations[ 3 ] ;  	 
    evaluation =  manhattanDistanceFromGhostsEvaluation + manhattanDistanceFromFlagsEvaluation  ;
    
    return evaluation ;
  }
}
