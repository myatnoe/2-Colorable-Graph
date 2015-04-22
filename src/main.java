import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class main {

	public static void main(String[] args) {
		try {
			// 1. make graph
			File file1 = new File("./largegraph2");
			Scanner scanner = new Scanner(file1);
			int size = scanner.nextInt();
			Graph g = new Graph(size);
			while(scanner.hasNext()){
				int from = scanner.nextInt();
				int to = scanner.nextInt();
				g.addEdge(from, to);
			}
			scanner.close();
			
			g.outputOddCycle("./OddCycle.txt");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
}
