import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Task8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Task 8.a
		//Figure 1
		Polyomino fig5a = Polyomino.Figure5_1();
		List<Polyomino> pentaminos = Polyomino.Generate_free(5, Color.red);
		//Hashing keys
		int n = 100;
		int m = 1000000;
		System.out.println(" ");
		System.out.print("There is ");
		long startTime1 = System.nanoTime();
		List<List<Polyomino>> solutions1 = Polyomino.TilingSolverOnce(fig5a, pentaminos, n, m);
		System.out.print(solutions1.size());
		System.out.println(" ways of tiling the first figure.");
		long stopTime1 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime1 - startTime1)/1000000000.+" seconds");
		
		System.out.println("------------------------------");
		//Task 8.a
		//Figure 2
		Polyomino fig5b = Polyomino.Figure5_2(5);
		//Hashing keys
		System.out.println(" ");
		System.out.print("There is ");
		long startTime2 = System.nanoTime();
		List<List<Polyomino>> solutions2 = Polyomino.TilingSolverOnce(fig5b, pentaminos, n, m);
		System.out.print(solutions2.size());
		System.out.println(" ways of tiling the second figure.");
		long stopTime2 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime2 - startTime2)/1000000000.+" seconds");
		
		System.out.println("------------------------------");
		//Task 8.a
		//Figure "
		Polyomino fig5c = Polyomino.Figure5_3();
		//Hashing keys
		System.out.println(" ");
		System.out.print("There is ");
		long startTime3 = System.nanoTime();
		List<List<Polyomino>> solutions3 = Polyomino.TilingSolverOnce(fig5c, pentaminos, n, m);
		System.out.print(solutions3.size());
		System.out.println(" ways of tiling the third figure.");
		long stopTime3 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime3 - startTime3)/1000000000.+" seconds");
		
		
		System.out.println("------------------------------");
		//Task 8.b
		for (int i=1;i<7;i++)
		{
			Polyomino square = Polyomino.Rectangle(i, i);
			List<Polyomino> tiles = Polyomino.Translations_Inside(square,Polyomino.Generate_fixed(i, Color.black));
			System.out.println(" ");
			System.out.print("There is ");
			long startTime4 = System.nanoTime();
			List<List<Polyomino>> solutions4 = Polyomino.TilingSolver(square, tiles, n);
			System.out.print(solutions4.size());
			System.out.println(" ways of tiling a "+ i+"*"+i+" square with fixed polyominos of size "+i);
			long stopTime4 = System.nanoTime();
			System.out.print("Execution time :");
			System.out.println((stopTime4 - startTime4)/1000000000.+" seconds");
		}
		
		System.out.println("------------------------------");
		//Task 8.c
		List<Polyomino> octaminos = Polyomino.Generate_free(8, Color.red);
		int k =4;
		List<Integer> index = new ArrayList<Integer>();
		long startTime5 = System.nanoTime();
		for (int i=0; i<octaminos.size(); i++)
		{
			Polyomino p = octaminos.get(i);
			List<List<Polyomino>> sols = Polyomino.Can_Cover_Own(p, k);
			if ( sols.size() >0)
			{
				index.add(i);
				
			}
		}
		System.out.println(" ");
		System.out.println("There is "+index.size()+ " octominos that can cover their own 4-dilate.");
		long stopTime5 = System.nanoTime();
		System.out.print("Execution time :");
		System.out.println((stopTime5 - startTime5)/1000000000.+" seconds");
		
	}

}
