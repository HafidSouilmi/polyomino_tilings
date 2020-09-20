import java.awt.Color;
import java.util.List;

//This class implements the data objects needed to create the dancing links data structure.
//We added an attribute "set" to help track the origin set the data was in
public class Data {
	Data U;
	Data D;
	Data L;
	Data R;
	//the set
	int set;
	Column C;
	
	public void Data(Data U,Data D,Data L, Data R, Column C)
	{
		this.U = U;
		this.D = D;
		this.L = D;
		this.R = R;
		this.C = C;
	}
	
	public void Data()
	{
		this.U = null;
		this.D = null;
		this.L = null;
		this.R = null;
		this.C = null;
	}

	public static Data Copy(Data d)
	{
		Data d_copy = new Data();
		d_copy.U = d.U;
		d_copy.D = d.D;
		d_copy.L = d.L;
		d_copy.R = d.R;
		d_copy.C = d.C;
		return d_copy;
	}

	 public static void main(String[] Args)
	 {
		 long startTime = System.nanoTime();
		 List<Polyomino> l = Polyomino.Generate_free(7,Color.black);
		 long endTime   = System.nanoTime();
		 long totalTime = (endTime - startTime)/1000000;
		 System.out.println(totalTime);
	 
		 //long startTime2 = System.nanoTime();
		 //List<Polyomino> l2 = RedelmeierGen.Redelmeier_GeneratorFixed(15);
		 //long endTime2   = System.nanoTime();
		 //long totalTime2 = (endTime2 - startTime2)/1000000;
		 //System.out.println(totalTime2);
	 }
	 

}
