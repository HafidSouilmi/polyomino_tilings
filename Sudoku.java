import java.util.List;
import java.util.ArrayList;
//This class implements the dancing links algorithm to solve Sudoku problems
//We warn that the object Sudoku and the cultural definition of Sudoku don't match, here we define a Sudoku object as a triplet of integers (row,column,value)
//This definition is motivated by the fact that in our dancing links structure the "subsets" will correspond to these Sudoku objects

public class Sudoku {
	int row;
	int column;
	int value;

	
	public Sudoku(int row, int column, int value)
	{
		this.column = column;
		this.row    = row;
		this.value  = value;
	}

//Computes the row number of (R,C,N) in the Sudoku incidence matrix/ dancing links structure
	public int Hash(Sudoku s)
	{
		return (81*s.row + 9*s.column + s.value);
	}
	

//Decodes an integer into the corresponding (R,C,N) object it represents in the sudoku
	public static Sudoku DeHash(int x)
	{
		int value  = x%9;
		int column = ( (x -value)/9 )%9;
		int row    = (x - 9*column - value)/81;
		Sudoku s = new Sudoku(row,column,value);
		return s;
	}
	
//This methods gives the box label corresponding to the pair (row, column)
//The boxes are labeled as follow:
//0 1 2
//3 4 5
//6 7 8
	public static int Box(int r, int c) {
		if (r < 3 && c < 3) {
			return 0;
		}
		if (r < 6 && c < 3) {
			return 1;
		}
		if (r < 9 && c < 3) {
			return 2;
		}
		if (r < 3 && c < 6) {
			return 3;
		}
		if (r < 6 && c < 6) {
			return 4;
		}
		if (r < 9 && c < 6) {
			return 5;
		}
		if (r < 3 && c < 9) {
			return 6;
		}
		if (r < 6 && c < 9) {
			return 7;
		}
		if (r < 9 && c < 9) {
			return 8;
		} else {
			return 9;
		}
	}
	
//This method takes a list of Sudoku object representing the non empty squares of the Sudoku problem and then creates the dancing links matrix associated to it
//The matrix is first initialized in a standard fashion, then we clean up the rows corresponding to an already filled square in the Sudoku problem.
//We do this clean up operation to force the dancing links algorithm to not consider further possibilities for the already filled squares.
//Once we have done all of the above we return the matrix constructed.
	public static int[][] SudokuToMatrix(List<Sudoku> AlreadyFilled)
	{
		//intialization
		int[][] matrix = new int[9*9*9][9*9*4];
		for (int r=0; r<9; r++)
		{
			for (int c=0; c<9; c++)
			{
				for (int v=0; v<9; v++)
				{
					//1st constraint : 1 possible value per pair (row, column)
					matrix[v + 9*c + 81*r][0*81 + 9*r + c] = 1;
					
					//2nd constraint : 1 possible column per pair (row, value)
					matrix[v + 9*c + 81*r][1*81 + 9*r + v] = 1;
					
					//3rd constraint : 1 possible row per pair (column, value)
					matrix[v + 9*c + 81*r][2*81 + 9*c + v] = 1;
					
					//4th constraint : 1 possible pair (9-box, value)
					matrix[v + 9*c + 81*r][3*81 + 9*Box(r,c) + v] = 1;
					
				}
			}
		}
		
		//now we clean up the already filled parts of the Sudoku
		for (Sudoku s:AlreadyFilled)
		{
			int row    = s.row;
			int column = s.column;
			int value  = s.value;
			int box    = Box(row, column); 
			//cleaning up the (row,column), (row,value), (column, value), (9-box, value)
			for (int r=0; r<9; r++)
			{
				for (int c=0; c<9; c++)
				{
					for (int v=0; v<9; v++)
					{
						if ( (r==row&&c==column) || (r==row&&v==value) || (c==column&&v==value) || (Box(r,c)==box&&v==value))
						{
							//this row is now useless
							matrix[v + 9*c + 81*r][0*81 + 9*r + c]      = 0;
							matrix[v + 9*c + 81*r][1*81 + 9*r + v]      = 0;
							matrix[v + 9*c + 81*r][2*81 + 9*c + v]      = 0;
							matrix[v + 9*c + 81*r][3*81 + 9*Box(r,c) + v] = 0;
						}
					}
				}
			}
				
		}
		return matrix;
	}
//Prints a nice visual of the matrix
	public static void PrintMatrix(int[][] matrix)
	{
		int n = matrix.length;
		int m = matrix[0].length;
		for (int i=0; i<n; i++)
		{
			System.out.println(" ");
			for (int j=0; j<m; j++)
			{
				System.out.print(matrix[i][j]);
			}
		}
	}
//A modified version of this function in DancingLinks
	public static void ConvertSudoku(int[][] matrix, List<Sudoku> AlreadyFilled, DancingLinks DL)
	{
		int m = matrix.length;
		int n = matrix[0].length;
		
		//we add a row so that we can add the columns data type
		Data[][]DLmatrix = new Data[m+1][n];

		//adding in the columns:
		for (int i=0;i<n;i++)
		{
			Column C = new Column();
			C.S = 0;
			C.N = i;
			C.C = C;
			DLmatrix[0][i]=C;
			//creating the column of DLinks coresponding to the column i
			for (int k=1; k<m+1;k++)
			{
				Data d = new Data();
				d.C = C;
				DLmatrix[k][i] = d;
				d.set = k-1;
			}
		}
		
		//completes the links : U,D,L,R for the data objects
		for (int i=0;i<n;i++)
		{
			for (int j=0;j<m+1;j++)
			{
				Data d   = DLmatrix[j][i];
				Data d_U = DLmatrix[Math.floorMod(j-1, m+1)][i];
				Data d_D = DLmatrix[Math.floorMod(j+1,m+1)][i];
				Data d_L = DLmatrix[j][Math.floorMod(i-1,n)];
				Data d_R = DLmatrix[j][Math.floorMod(i+1,n)];
				
				d.U = d_U;
				d.D = d_D;
				d.L = d_L;
				d.R = d_R;
				
			}
		}
		
		//computes the Column.S part for each column object
		for (int k=0;k<n;k++)
		{

			int Compt = 0;
			for (int i=1;i<m+1;i++)
			{
				if (matrix[i-1][k]==1)
				{
					Column C = (Column) DLmatrix[0][k];
					C.S =C.S +1;
				}
				else
				//we delete the data object as shown in figure 3
				{
					Data d_empty = DLmatrix[i][k];
					d_empty.U.D = d_empty.D;
					d_empty.D.U = d_empty.U;
					d_empty.R.L = d_empty.L;
					d_empty.L.R = d_empty.R;
				}
			}
		}
		
		//adding the "Father" column H as in figure 3
		//creating H and adding its links alone 
		DL.H = new Column();
		DL.H.S =-1;
		DL.H.N =-1;
		DL.H.R = DLmatrix[0][0];
		DL.H.L = DLmatrix[0][n-1];
		//adding H to the general data
		DL.H.L.R = DL.H;
		DL.H.R.L = DL.H;
		
		//covering the columns corresponding to alreadyfilled elements
		for (Sudoku s: AlreadyFilled)
		{
			int row = s.row;
			int column = s.column;
			int value = s.value;
			int box = Box(row,column);
			
			for (int k=0; k<9; k++)
			{
				for(int l=0; l<9*9*4; l++)
				{
				//row-column
				DancingLinks.coverColumn(DLmatrix[81*row + 9*column +k + 1][l].C);
				//row-value
				DancingLinks.coverColumn(DLmatrix[81*row + 9*k + value + 1][l].C);
				//column-value
				DancingLinks.coverColumn(DLmatrix[81*k + 9*column + value + 1][l].C);
				}
			}
			//box-value
			for (int r=0; r<9; r++)
			{
				for (int c=0; c<9; c++)	
				{
					for (int l=0; l<9*9*4; l++)
					{
					DancingLinks.coverColumn(DLmatrix[81*r + 9*c + value +1][l].C);
					}
				}
			}
		}
		
	}
	
	public static List<List<Sudoku>> SudokuSolver_beta(List<Sudoku> AlreadyFilled)
	{
		int[][] matrix = SudokuToMatrix(AlreadyFilled);
		DancingLinks DL = new DancingLinks();
		ConvertSudoku(matrix,AlreadyFilled, DL);
		List<List<Data>> sols = DancingLinks.exactCover(DL);
		
		List<List<Sudoku>> liste = new ArrayList<List<Sudoku>>();
		for (List<Data> partition: sols)
		{
			List<Integer> subset_indexes = DancingLinks.DataToSubsetIndexes(partition);
			List<Sudoku> a_sol = new ArrayList<Sudoku>();
			for (Integer index: subset_indexes)
			{
				a_sol.add(DeHash(index));
			}
			
			liste.add(a_sol);
		}
		return liste;
		
	}
	
	public static List<List<Sudoku>> SudokuSolver(List<Sudoku> AlreadyFilled)
	{
		int[][] matrix = SudokuToMatrix(AlreadyFilled);
		DancingLinks DL = new DancingLinks();
		int[][] m = TakeOffColumns(matrix);
		DL.Convert(m);
		List<List<Data>> sols = DancingLinks.exactCover(DL);
		
		List<List<Sudoku>> liste = new ArrayList<List<Sudoku>>();
		
		for (List<Data> solution : sols)
		{
			List<Sudoku> l = new ArrayList<Sudoku>();
			for (Data d: solution)
			{
				int x = d.set;
				Sudoku s = DeHash(x);
				l.add(s);
			}
			liste.add(l);
		}
		return liste;
		//return sols;
		
	}
	
	public static void SudokuPrinter(List<Sudoku> AllreadyFilled)
	{
		System.out.println(" Initial problem : ");
		GetVisual(AllreadyFilled);
		
		List<List<Sudoku>> sols = SudokuSolver(AllreadyFilled);
		if (sols.size()==0)
		{
			System.out.println(" ");
			System.out.println(" There is no solution");
		}
		else
		{
			System.out.println(" ");
			System.out.println(" Resolution complete");
			for (List<Sudoku> solution:sols)
			{
				System.out.println(" A possible solution :");
				List<Sudoku> liste = new ArrayList<Sudoku>();
				liste.addAll(AllreadyFilled);
				liste.addAll(solution);
				GetVisual(liste);
				System.out.println("--------------------------");
			}
		}
		
	}
	
//Verifies if the column j of an incidence matrix is only filled with 0;
	public static boolean IsColumnZero(int[][] matrix, int j)
	{
		int comp = 0;
		for (int i=0; i<matrix.length; i++)
		{
			comp = comp+ matrix[i][j];
		}
		if (comp ==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
//This method takes a n*m incidence matrix and removes its empty columns (i.e columns with only zeros)
	public static int[][] TakeOffColumns(int[][] matrix)
	{
		List<Integer> C_zeros = new ArrayList<Integer>();
		int n=matrix.length;
		int m=matrix[0].length;
		for (int j=0; j<m; j++)
		{
			if (!(IsColumnZero(matrix, j)))
			{
				C_zeros.add(j);
			}
		}
		int m_new = C_zeros.size();
		int[][] new_matrix = new int[n][m_new];
		for (int i=0; i<n; i++)
		{
			for (int j=0; j<m_new; j++)
			{
				new_matrix[i][j] = matrix[i][C_zeros.get(j)];
			}
		}
		return new_matrix;
	}
	
	
	
//This method prints a nice visual of a list of Sudoku objects
	public static void GetVisual(List<Sudoku> l)
	{
		String[][] matrix = new String[9][9];
		for (int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++)
			{
				matrix[i][j] = " . ";
			}
		}
		for (Sudoku s:l)
		{
			int r = s.row;
			int c = s.column;
			int v = s.value;
			matrix[r][c] = " "+String.valueOf(v+1)+" ";
		}
		
		System.out.println(" ");
		for (int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++)
			{
			System.out.print(matrix[i][j]);
			if (j==8)
			{
				System.out.println(" ");
			}
			}
		}
	}

	public static void main(String[] args)
	{

		
		
		//PrintMatrix(m);
		//System.out.println(m[0][0]);
		List<Sudoku> partiels = new ArrayList<Sudoku>();
		Sudoku s1 = new Sudoku(0,3,1);
		Sudoku s2 = new Sudoku(0,4,5);
		Sudoku s3 = new Sudoku(0,6,6);
		Sudoku s4 = new Sudoku(0,8,0);
		Sudoku s5 = new Sudoku(1,0,5);
		Sudoku s6 = new Sudoku(1,1,7);
		Sudoku s7 = new Sudoku(1,4,6);
		Sudoku s8 = new Sudoku(1,7,8);
		Sudoku s9 = new Sudoku(2,0,0);
		Sudoku s10 = new Sudoku(2,1,8);
		Sudoku s11 = new Sudoku(2,5,3);
		Sudoku s12 = new Sudoku(2,6,4);
		Sudoku s13 = new Sudoku(3,0,7);
		Sudoku s14 = new Sudoku(3,1,1);
		Sudoku s15 = new Sudoku(3,3,0);
		Sudoku s16 = new Sudoku(3,7,3);
		Sudoku s17 = new Sudoku(4,2,3);
		Sudoku s18 = new Sudoku(4,3,5);
		Sudoku s19 = new Sudoku(4,5,1);
		Sudoku s20 = new Sudoku(4,6,8);
		Sudoku s21 = new Sudoku(5,1,4);
		Sudoku s22 = new Sudoku(5,5,2);
		Sudoku s23 = new Sudoku(5,7,1);
		Sudoku s24 = new Sudoku(5,8,7);
		Sudoku s25 = new Sudoku(6,2,8);
		Sudoku s26 = new Sudoku(6,3,2);
		Sudoku s27 = new Sudoku(6,7,6);
		Sudoku s28 = new Sudoku(6,8,3);
		Sudoku s29 = new Sudoku(7,1,3);
		Sudoku s30 = new Sudoku(7,4,4);
		Sudoku s31 = new Sudoku(7,7,2);
		Sudoku s32 = new Sudoku(7,8,5);
		Sudoku s33 = new Sudoku(8,0,6);
		Sudoku s34 = new Sudoku(8,2,2);
		Sudoku s35 = new Sudoku(8,4,0);
		Sudoku s36 = new Sudoku(8,5,7);

				
		partiels.add(s1);
		partiels.add(s2);
		partiels.add(s3);
		partiels.add(s4);
		partiels.add(s5);
		partiels.add(s6);
		partiels.add(s7);
		partiels.add(s8);
		partiels.add(s7);
		partiels.add(s9);
		partiels.add(s10);
		partiels.add(s11);
		partiels.add(s12);
		partiels.add(s13);
		partiels.add(s14);
		partiels.add(s15);
		partiels.add(s16);
		partiels.add(s17);
		partiels.add(s18);
		partiels.add(s19);
		partiels.add(s20);
		partiels.add(s21);
		partiels.add(s22);
		partiels.add(s23);
		partiels.add(s24);
		partiels.add(s25);
		partiels.add(s26);
		partiels.add(s27);
		partiels.add(s28);
		partiels.add(s27);
		partiels.add(s29);
		partiels.add(s30);
		partiels.add(s31);
		partiels.add(s32);
		partiels.add(s33);
		partiels.add(s34);
		partiels.add(s35);
		partiels.add(s36);
		
		int[][] m = SudokuToMatrix(partiels);
		int[][] m0= TakeOffColumns(m);
		
		for (int j=0; j<m0[0].length; j++)
		{
			if(IsColumnZero(m0,j))
			{
				System.out.println(" here s one");
			}
		}
		int[][]test = new int[4][4];
		test[0][0]=1;
		test[1][1]=1;
		test[2][2]=1;
		test[3][3]=1;
		//PrintMatrix(m0);
		//PrintMatrix(m);
		DancingLinks DL = new DancingLinks();
		DL.Convert(m0);
		//ConvertSudoku(m,new ArrayList<Sudoku>(),DL);
		List<List<Data>> sols = DancingLinks.exactCover(DL);
		//System.out.println(sols.size());
		List<Data> solution = sols.get(0);
		for (Data d: solution)
		{
			int x = d.set;
			//Sudoku s = DeHash(x);
			//System.out.print(s.row);
			//System.out.print(" ");
			//System.out.print(s.column);
			//System.out.print(" ");
			//System.out.println(s.value);
		}
		List<List<Sudoku>> l = SudokuSolver(partiels);
		//System.out.println(l.size());
		
		//System.out.println(solution.size());

		
		//List<List<Sudoku>> test = SudokuSolver(partiels);
		//System.out.println(test.size());
		
		System.out.println(" Initial problem : ");
		GetVisual(partiels);
		List<Sudoku> liste = new ArrayList<Sudoku>();
		liste.addAll(partiels);
		for ( Data d: solution)
		{
			int x = d.set;
			Sudoku s = DeHash(x);
			liste.add(s);
		}
		
		System.out.println(" ");
		System.out.println(" Resolution complete : ");
		GetVisual(liste);
		
		System.out.println("------------------");
		System.out.println(l.size());
		List<Sudoku> ll = l.get(0);
		for (Sudoku s:ll)
		{
			//System.out.println(" ");
			//System.out.print(s.row);
			//System.out.print(" ");
			//System.out.print(s.column);
			//System.out.print(" ");
			//System.out.print(s.value);
		}
		Sudoku q1 = new Sudoku(0,1,1);
		Sudoku q2 = new Sudoku(1,3,5);
		Sudoku q3 = new Sudoku(1,8,2);
		Sudoku q4 = new Sudoku(2,1,6);
		Sudoku q5 = new Sudoku(2,2,3);
		Sudoku q6 = new Sudoku(2,4,7);
		Sudoku q7 = new Sudoku(3,5,2);
		Sudoku q8 = new Sudoku(3,8,1);
		Sudoku q9 = new Sudoku(4,1,7);
		Sudoku q10 = new Sudoku(4,4,3);
		Sudoku q11 = new Sudoku(4,7,0);
		Sudoku q12 = new Sudoku(5,0,5);
		Sudoku q13 = new Sudoku(5,3,4);
		Sudoku q14 = new Sudoku(6,4,0);
		Sudoku q15 = new Sudoku(6,6,6);
		Sudoku q16 = new Sudoku(6,7,7);
		Sudoku q17 = new Sudoku(7,0,4);
		Sudoku q18 = new Sudoku(7,5,8);
		Sudoku q19 = new Sudoku(8,7,3);
		List<Sudoku> v = new ArrayList<Sudoku>();
		v.add(q1);
		v.add(q2);
		v.add(q3);
		v.add(q4);
		v.add(q5);
		v.add(q6);
		v.add(q7);
		v.add(q8);
		v.add(q9);
		v.add(q10);
		v.add(q11);
		v.add(q12);
		v.add(q13);
		v.add(q14);
		v.add(q15);
		v.add(q16);
		v.add(q17);
		v.add(q18);
		v.add(q19);
		
		SudokuPrinter(v);
	}
}
