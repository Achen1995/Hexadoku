/*********************************************************/
/*                                                       */
/* An applet to demonstrate recursion and backtracking   */
/* ===================================================   */
/*                                                       */
/* V0.3   18-MAR-2007  P. Tellenbach   www.heimetli.ch   */
/*                                                       */
/*********************************************************/
/**
 * Addison Chen
 * The goal of this program is to create a hexasudoku solver in 5 parts with 16x16.
**/
import java.applet.* ;
import java.awt.* ;
import java.io.*;
import java.util.*;

/**
 * Solves a sudoku puzzle by recursion and backtracking
 */
public class Hexadoku extends Applet implements Runnable
{
   /** The model */
   protected int model[][] ;

   /** The view */
   protected Button view[][] ;

   /** Creates the model and sets up the initial situation */
   protected void createModel()
   {
      model = new int[16][16] ;

      // Clear all cells
      for( int row = 0; row < 16; row++ )
         for( int col = 0; col < 16; col++ )
            model[row][col] = 0 ;
      
/**
      try{
        File inFile = new File("solution.txt");
        Scanner in = new Scanner(inFile);
      
        while (in.hasNextLine()) {
              String[] currentLine = in.nextLine().split(" ");
              String r = "", c = "", value = "";
              int spaceCount = 0;
              for(int i=0; i<currentLine.length;i++){
                      if(currentLine[i] != " " && spaceCount ==0){
                           r+= currentLine[i];
                      }
                      else if(currentLine[i] != " " && spaceCount ==1){
                           c+= currentLine[i];
                      }
                      else if(currentLine[i] != " " && spaceCount ==2){
                           value+= currentLine[i];
                      }
                      else if(currentLine[i] == " "){
                           spaceCount++;
                      }
              
              }
              
              model[Integer.parseInt(r)][Integer.parseInt(c)] = Integer.parseInt(value);
            } 
      in.close();
      }
      catch (FileNotFoundException e){}
      */
      
      // Create the initial situation
      model[0][0] = 11 ;
      model[0][2] = 5 ;
      model[0][5] = 12 ;
      model[0][8] = 13 ;
      model[0][11] = 10 ;

      model[1][1] = 13 ;
      model[1][6] = 5 ;
      model[1][8] = 1 ;
      model[1][10] = 14 ;
      model[1][12] = 2 ;

      model[2][3] = 9 ;
      model[2][7] = 2 ;
      model[2][9] = 5 ;
      model[2][12] = 14 ;
      model[2][14] = 7 ;

      model[3][0] = 4 ;
      model[3][2] = 12 ;
      model[3][4] = 8 ;
      model[3][11] = 16 ;
      model[3][13] = 10 ;

      model[4][1] = 9 ;
      model[4][3] = 11 ;
      model[4][6] = 4 ;
      model[4][8] = 3 ;
      model[4][9] = 6 ;
      model[4][15] = 7 ;

      model[5][0] = 6 ;
      model[5][2] = 8 ;
      model[5][4] = 10 ;
      model[5][5] = 13 ;
      model[5][7] = 16 ;
      model[5][10] = 11 ;
      model[5][12] = 15 ;
      model[5][14] = 9 ;

      model[6][1] = 3 ;
      model[6][6] = 2 ;
      model[6][8] = 15 ;
      model[6][13] = 13 ;
      model[6][15] = 1 ;

      model[7][0] = 16 ;
      model[7][3] = 2 ;
      model[7][5] = 1 ;
      model[7][7] = 5 ;
      model[7][9] = 14 ;
      model[7][11] = 8 ;

      model[8][2] = 16 ;
      model[8][4] = 13 ;
      model[8][5] = 4 ;
      model[8][14] = 12 ;
      
      model[9][2] = 10 ;
      model[9][3] = 3 ;
      model[9][5] = 7 ;
      model[9][8] = 5 ;
      model[9][10] = 16 ;
      model[9][12] = 9 ;
      model[9][15] = 15 ;
      
      model[10][0] = 15 ;
      model[10][2] = 11 ;
      model[10][4] = 1 ;
      model[10][7] = 9 ;
      model[10][9] = 2 ;
      model[10][11] = 3 ;
      model[10][13] = 4 ;
      
      model[11][6] = 8 ;
      model[11][10] = 4 ;
      model[11][12] = 10 ;
      
      model[12][0] = 2 ;
      model[12][2] = 4 ;
      model[12][11] = 12 ;
      model[12][14] = 5 ;
      
      model[13][1] = 12 ;
      model[13][3] = 13 ;
      model[13][7] = 1 ;
      model[13][10] = 10 ;
      model[13][13] = 3 ;
      model[13][15] = 8 ;
      
      model[14][0] = 8 ;
      model[14][2] = 9 ;
      model[14][4] = 4 ;
      model[14][6] = 3 ;
      model[14][9] = 7 ;
      model[14][12] = 11 ;
      model[14][14] = 13 ;
      
      model[15][2] = 5 ;
      model[15][3] = 14 ;
      model[15][7] = 11 ;
      model[15][10] = 1 ;
      model[15][11] = 6 ;
      model[15][15] = 10 ;


      
   }
   
   /** Creates an empty view */
   protected void createView()
   {
      setLayout( new GridLayout(16,16) ) ;

      view = new Button[16][16] ;

      // Create an empty view
      for( int row = 0; row < 16; row++ )
         for( int col = 0; col < 16; col++ )
         {
            view[row][col]  = new Button() ;
            add( view[row][col] ) ;
         }
   }

   /** Updates the view from the model */
   protected void updateView()
   {
      for( int row = 0; row < 16; row++ )
         for( int col = 0; col < 16; col++ )
            if( model[row][col] != 0 )
               view[row][col].setLabel( String.valueOf(model[row][col]) ) ;
            else
               view[row][col].setLabel( "" ) ;
    }

   /** This method is called by the browser when the applet is loaded */
   public void init()
   {
      createModel() ;
      createView() ;
      updateView() ;
   }

   /** Checks if num is an acceptable value for the given row */
   protected boolean checkRow( int row, int num )
   {
      for( int col = 0; col < 16; col++ )
         if( model[row][col] == num )
            return false ;

      return true ;
   }

   /** Checks if num is an acceptable value for the given column */
   protected boolean checkCol( int col, int num )
   {
      for( int row = 0; row < 16; row++ )
         if( model[row][col] == num )
            return false ;

      return true ;
   }

   /** Checks if num is an acceptable value for the box around row and col */
   protected boolean checkBox( int row, int col, int num )
   {
      row = (row / 4) * 4 ;
      col = (col / 4) * 4 ;

      for( int r = 0; r < 4; r++ )
         for( int c = 0; c < 4; c++ )
         if( model[row+r][col+c] == num )
            return false ;

      return true ;
   }

   /** This method is called by the browser to start the applet */
   public void start()
   {
      // This statement will start the method 'run' to in a new thread
      (new Thread(this)).start() ;
   }

   /** The active part begins here */
   public void run()
   {
      try
      {
         // Let the observers see the initial position
         Thread.sleep( 100 ) ;

         // Start to solve the puzzle in the left upper corner
         solve( 0, 0 ) ;
      }
      catch( Exception e )
      {
      }
   }

   /** Recursive function to find a valid number for one single cell */
   public void solve( int row, int col ) throws Exception
   {
      // Throw an exception to stop the process if the puzzle is solved
      if( row > 15 )
         throw new Exception( "Solution found" ) ;

      // If the cell is not empty, continue with the next cell
      if( model[row][col] != 0 )
         next( row, col ) ;
      else
      {
         // Find a valid number for the empty cell
         for( int num = 1; num < 17; num++ )
         {
            if( checkRow(row,num) && checkCol(col,num) && checkBox(row,col,num) )
            {
               model[row][col] = num ;
               updateView() ;

               // Let the observer see it
               Thread.sleep( 100 ) ;

               // Delegate work on the next cell to a recursive call
               next( row, col ) ;
            }
         }

         // No valid number was found, clean up and return to caller
         model[row][col] = 0 ;
         updateView() ;
      }
   }

   /** Calls solve for the next cell */
   public void next( int row, int col ) throws Exception
   {
      if( col < 15 )
         solve( row, col + 1 ) ;
      else
         solve( row + 1, 0 ) ;
   }
}
