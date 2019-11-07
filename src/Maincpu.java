import javax.swing.UIManager;

public class Maincpu {
	public static void main(String ary[]){		 
		 try {  
			   UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

			  }  catch (Exception e) { }

		new CpuFrame();
	}
}
