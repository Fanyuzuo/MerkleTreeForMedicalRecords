import java.io.File;
import java.util.LinkedList;
public class Test {

	public static void main(String[] args) {
		LinkedList<File> listofFiles = new LinkedList<File>();
		LinkedList<String> listofhash = new LinkedList<String>();
		File[] files = new File("DataList/").listFiles();
		for(int i = 0; i < files.length; i++) {
			listofFiles.add(files[i]);
			System.out.println(files[i]);
		}
		
		MerkleLeaf ML = new MerkleLeaf(listofFiles);
		for(int i = 0; i < ML.getListFile().size(); i++) {
			listofhash.add(ML.hashFile(ML.getListFile().get(i)));
		}
		System.out.println(listofhash);
		
		MerkleTree MT = new MerkleTree(listofhash);
		MT.CreateFirstLayer();
		System.out.print(MT.changingList.size());
		System.out.println(MT.CreateTree(MT.changingList));
		System.out.println("Reached Here");
	}
	
}
