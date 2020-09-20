import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

// Frame for the vizualization
class Image2dViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;
	static int xLocation = 0;
	Image2d img;

	public Image2dViewer(Image2d img) {
		this.img = img;
		this.setLocation(xLocation, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new Image2dComponent(img));
		pack();
		setVisible(true);
		xLocation += img.getWidth();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ColoredPolygon> cp = new ArrayList<ColoredPolygon> ();
		List<Edge> e = new ArrayList<Edge> ();
		
		int width = 1000;
		int height = 1000;
		
		//Image2d ig  = new Image2d(width,height);
		
		//Image2dViewer im = new Image2dViewer(ig);
		
		int[] x = new int[] {0,0,1,1,1,1,1,2,2};
		int[] y = new int[] {0,4,0,1,2,3,4,0,4};
		Color c = Color.black;
		//ig.addPolygon(x,y,c);
		
		
		int[] xx = new int[] {10,10,11,11,11,11,11,12,12};
		int[] yy = new int[] {10,14,10,11,12,13,14,10,14};
		Color cc = Color.black;
		//ig.addPolygon(xx, yy, cc);
		
		ColoredPolygon test = new ColoredPolygon(new int[]{1},new int[]{1},Color.black );
		ColoredPolygon test0=Image2d.homo(1, 1, Color.black, 10);

		
		//ig.addPolygon(test0.polygon.xpoints, test0.polygon.ypoints, Color.black);
		
		int n = 400;
		int[] lx = new int[n*n];
		int[] ly = new int[n*n];
		for (int i=0;i<n;i++) 
		{
			for (int j=0; j<n; j++)
			{
				lx[i+n*j]=i;
				ly[i+n*j]=j;
			}
		}
		//ig.addPolygon(lx,ly, Color.BLACK);
	
		
		int[] lxx = new int[3*n];
		int[] lyy = new int[3*n];
		
		for (int i=0;i<n;i++) {
			lxx[i]=i;
			lyy[i]=0;
			
			lxx[i+n]=n/2;
			lyy[i+n]=i+1;
			
			lxx[i+2*n]=i;
			lyy[i+2*n]=n+1;
		}
		//ig.addPolygon(lxx, lyy, Color.black);
		
		//Image2d ig  = new Image2d(width,height);
		int[] llx = new int[] {0,100,100,0};
		int[] lly = new int[] {0,0,100,100};
		//ig.addPolygon(llx, lly, Color.black);
		

		//Polyomino p = new Polyomino(sq,Color.black);
		
		//Image2d ig = Image2d.DrawablePolyo(p,10);
		
		//Image2dViewer im = new Image2dViewer(ig);
		
		String s1 = "[(0,0),(0,4),(1,0),(1,1),(1,2),(1,3),(1,4),(2,0),(2,4)]";
		String s2 = "[(0,0),(0,1),(0,2),(0,3),(0,4),(1,2),(1,3),(2,1),(2,2),(3,0),(3,1),(3,2),(3,3),(3,4)]";
		String s3 = "[(0,0),(0,1),(0,2),(0,3),(0,4),(1,2),(1,4),(2,4)]";
		String s30= "[(0,4),(0,3),(0,2),(0,1),(0,0),(1,2),(1,0),(2,0)]";
		String s4 = "[(0,1),(0,2),(0,3),(0,4),(1,1),(2,0),(2,1),(2,2)]";
		String s5 = "[(0,0),(0,3),(0,4),(1,0),(1,1),(1,4),(2,0),(2,1),(2,2),(2,4),(3,0),(3,2),(3,3),(3,4)]";
		String s6 = "[(0,0),(0,3),(1,0),(1,1),(1,2),(1,3),(1,4),(2,0)]";
		
		
		Polyomino p1 = Polyomino.StringtoPolyo(s1, Color.black);
		Polyomino p2 = Polyomino.StringtoPolyo(s2, Color.black);
		Polyomino p3 = Polyomino.StringtoPolyo(s3, Color.black);
		Polyomino p4 = Polyomino.StringtoPolyo(s4, Color.black);
		Polyomino p5 = Polyomino.StringtoPolyo(s5, Color.black);
		Polyomino p6 = Polyomino.StringtoPolyo(s6, Color.black);
		
		
		p1.Dilatation(10);
		p1.Updown(700);
		p2.Dilatation(10);
		p2.Updown(700);
		p3.Dilatation(10);
		p3.Updown(700);
		p4.Dilatation(10);
		p4.Updown(700);
		p5.Dilatation(10);
		p5.Updown(700);
		p6.Dilatation(10);
		p6.Updown(700);
		Image2d ig  = new Image2d(1000,1000);
		
		//ig.DrawablePolyoM(p, 10);
		List<Polyomino> liste = new ArrayList<Polyomino>();
		//liste.add(p1);
		//liste.add(p2);
		//liste.add(p3);
		//liste.add(p4);
		//liste.add(p5);
		//liste.add(p6);
				
		
	
		//Image2d ig = Image2d.DrawablePolyo(p,10);
		
		
		
		
		List<Polyomino> l_3 = Polyomino.Generate_fixed(4, Color.black);
		for (Polyomino p:l_3)
		{
			p.Dilatation(10);
			p.Updown(700);
			//ig.addEdges(p, 10);
			//liste.add(p);
		}
		
		
		
		String s = "[(0,0),(0,1),(1,0),(1,1),(2,0),(2,1)]";
		Polyomino groundset = Polyomino.StringtoPolyo(s, Color.black);
		
		String v1 = "[(0,0)]";
		String v2 = "[(1,0),(0,1),(1,1)]";
		String v3 = "[(2,0),(2,1)]";
		
		Polyomino t1 = Polyomino.StringtoPolyo(v1, Color.black);
		Polyomino t2 = Polyomino.StringtoPolyo(v2, Color.black);
		Polyomino t3 = Polyomino.StringtoPolyo(v3, Color.black);
		List<Polyomino> tiles = new ArrayList<Polyomino>();
		tiles.add(t1);
		tiles.add(t2);
		tiles.add(t3);
		
		List<List<Polyomino>> liste_sol = Polyomino.TilingSolver(groundset, tiles, 10);
		List<Polyomino> une_sol = liste_sol.get(0);
		for (Polyomino p:une_sol)
		{
			p.Dilatation(20);
			p.Updown(700);
			//ig.addEdges(p, 20);
			//liste.add(p);
		}
		
		List<Polyomino> freepenta = Polyomino.Generate_free(5, Color.black);
		for (Polyomino penta: freepenta)
		{
			//penta.Dilatation(10);
			//penta.Updown(700);
			//liste.add(penta);
		}
		
		Polyomino fig5 = Polyomino.Figure5_2(2);
		Polyomino penta1 = freepenta.get(2);
		
		List<Polyomino> trans = Polyomino.Translations_Inside(fig5,penta1);
		Polyomino possib = trans.get(2);
		//System.out.println(trans.size());
		fig5.Dilatation(10);
		fig5.Updown(700);
		//liste.add(fig5);
		possib.Dilatation(10);
		possib.Updown(700);
		//ig.addEdges(possib,10);
		//liste.add(penta1);
		//ig.Drawable(liste, 10, 0);
		Image2dViewer im = new Image2dViewer(ig);
		
		
		Polyomino Rec = Polyomino.Rectangle(3, 3);
		List<Polyomino> fixed_n =  Polyomino.Generate_fixed(1, Color.black);
		List<Polyomino> translations = Polyomino.Translations_Inside(Rec,fixed_n);
		for (Polyomino ppo:trans)
		{
			ppo.Dilatation(10);
			ppo.Updown(700);
			ig.addEdges(ppo,10);
			liste.add(ppo);
		}
		//List<List<Polyomino>> sols = Polyomino.TilingSolver(Rec, translations, 100);
		//System.out.println(sols.size());
		
		Rec.Dilatation(10);
		Rec.Updown(700);
		//liste.add(Rec);
		ig.Drawable(liste,10, 0);
		
		
	}
	
	
	
	
}