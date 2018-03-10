import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class MerkleLeaf {
	public LinkedList<File> filename;
	
	public MerkleLeaf(LinkedList<File> name) {
		this.filename = name;
	}
	
	public LinkedList<File> getListFile() {
		return filename;
	}
	
	public String byteTostring(byte[] b) {
		
		StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < b.length; i++) {
	    	buf.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
	     }
	       return buf.toString();
	   }
	
	
	
	public String hashFile(File inputfile) {
		File oneFile = inputfile;
        

        FileInputStream fin = null;
        String changetoString = null;
        try {

            fin = new FileInputStream(oneFile);
            byte fileContent[] = new byte[(int)oneFile.length()];
            
            try {
            	MessageDigest md = MessageDigest.getInstance("SHA-256");
            	byte[] key=md.digest(fileContent);
            	changetoString = byteTostring(key);
            	//System.out.println("hash : " + changetoString);
        
            	}
     catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

            fin.read(fileContent);
            String s = new String(fileContent);
           // System.out.println("File content: " + s);

        }

        catch (FileNotFoundException e) {

            System.out.println("File not found" + e);

        }

        catch (IOException ioe) {

            System.out.println("Exception while reading file " + ioe);
        }

        finally {

            try {

                if (fin != null) {

                    fin.close();
                }

            }

            catch (IOException ioe) {

                System.out.println("Error while closing stream: " + ioe);

            }

        }
		return changetoString;
        
	}

}
