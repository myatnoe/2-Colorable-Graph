import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.management.*;

public class main {

	public static void main(String[] args) {
		try {
			long startSystemTimeNano = getSystemTime();
			long startUserTimeNano = getUserTime();
			// 1. make graph
			File file1 = new File(args[0]);
			Scanner scanner = new Scanner(file1);
			int size = scanner.nextInt();
			Graph g = new Graph(size);
			while(scanner.hasNext()){
				int u = scanner.nextInt();
				int v = scanner.nextInt();
				g.addEdge(u, v);
			}
			scanner.close();
			// 2.  TwoColorable finds odd cycle
			TwoColorable c = new TwoColorable(g);
			c.outputOddCycle(args[1]);
			
			long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
			long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
			System.out.println("System Time "+taskSystemTimeNano/1000000000.0 + " seconds");
			System.out.println("User Time   "+taskUserTimeNano/1000000000.0 + " seconds");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/** Get CPU time in nanoseconds. */
	public static long getCpuTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadCpuTime( ) : 0L;
	}
	 
	/** Get user time in nanoseconds. */
	public static long getUserTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadUserTime( ) : 0L;
	}

	/** Get system time in nanoseconds. */
	public static long getSystemTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        (bean.getCurrentThreadCpuTime( ) - bean.getCurrentThreadUserTime( )) : 0L;
	}
}
