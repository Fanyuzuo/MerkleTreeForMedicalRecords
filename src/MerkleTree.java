import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/***
 * MerkleTree class implements MerkleTree by hashing patient files using SHA-256
 * ArrayList of patients files(txt files)
 * @author brihat
 *
 */

public class MerkleTree {
	public LinkedHashMap<File, String> patientFileHash;  //hashing the File 
	public LinkedList<String> patientList;
	public LinkedList<String> patientStrHash;
	public LinkedHashMap<String, String> patientHashList;   //hash the patient's hash again and keep here
	public LinkedList<String> changingList;
	public MerkleTree(LinkedList<String> PatientList) {
		this.patientFileHash = new LinkedHashMap<File, String>();
		this.patientHashList = new LinkedHashMap<String, String>();
		this.patientList = PatientList;
	}
	
	/***
	 * Method that creates base layer of the tree
	 * Double hashing the patient file
	 * @return
	 */
	
	public LinkedList<String> CreateFirstLayer() {
		LinkedList<String> patientStringHash = new LinkedList<String>();
		
		for(int i = 0; i < patientList.size(); i++) {
			String changeStringA = null;
			try {
				
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(patientList.get(i).getBytes(StandardCharsets.UTF_8));
	            byte[] key=md.digest();
	            //changeString = byteTostring(key);
	            changeStringA = byteTostring(key);
	            //System.out.println("hash : " + changetoString);
	        }
			catch (NoSuchAlgorithmException e) {
	            	e.printStackTrace();
	        }
			this.patientHashList.put(patientList.get(i), changeStringA);
			patientStringHash.add(changeStringA);
		}
		this.changingList = patientStringHash;
		this.patientStrHash = patientStringHash;
		return patientStringHash;
	}
	
	/***
	 * Method to create Rest of the layers
	 * Hashing combination of two hexdigest to create the tree
	 * @return
	 */
	
	public LinkedHashMap<String, String> CreateTree(LinkedList<String> listofpatients) {
		LinkedList<String> patientList = listofpatients;
		LinkedList<String> temp_list = new LinkedList<String>(); 
		//LinkedList<String> patient_list = new LinkedList<String>(); 
		//temp_list = this.changingList;
		String a = null;
		String b = null;	
		//System.out.println(patientList.size());
		if(patientList.size() > 1) {
			for(int i = 0; i < patientList.size(); i = i + 2) {
				//System.out.println(temp_list.size());
				a = patientList.get(i);
				String changetoString = null;
				//need an if condition for b
				if(i + 1 < patientList.size()) {
					b = patientList.get(i+1);
					String combineHash = null;
					combineHash = a + b;
					try {
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						
						md.update(combineHash.getBytes(StandardCharsets.UTF_8));
						byte[] key=md.digest();
						changetoString = byteTostring(key);
						//System.out.println("hash : " + changetoString);
						System.out.println("Just hashed combining both");
					}
					catch (NoSuchAlgorithmException e) {
		            		e.printStackTrace();
					}
					this.patientHashList.put(combineHash, changetoString);
					temp_list.add(changetoString);
				}
				else {
					try {
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(a.getBytes(StandardCharsets.UTF_8));
						byte[] key=md.digest();
						changetoString = byteTostring(key);
						//System.out.println("hash : " + changetoString);
						System.out.println("Just hash odd one");
					}
					catch (NoSuchAlgorithmException e) {
		            		e.printStackTrace();
					}
					this.patientHashList.put(a, changetoString);
					temp_list.add(changetoString);
				}	
			}
			
			return CreateTree(temp_list);
			
		}
		else {
			String changetoString = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(patientList.get(0).getBytes(StandardCharsets.UTF_8));
				byte[] key=md.digest();
				changetoString = byteTostring(key);
				//System.out.println("hash : " + changetoString);
				System.out.println("Just hash odd one");
			}
			catch (NoSuchAlgorithmException e) {
            		e.printStackTrace();
			}
			this.patientHashList.put(patientList.get(0), changetoString);
			temp_list.add(changetoString);
		}		
		
		return this.patientHashList;
	}
	
	/***
	 * 
	 * @param b
	 * @return
	 */
	public String byteTostring(byte[] b) {
		
		StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < b.length; i++) {
	    	buf.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
	     }
	       return buf.toString();
	}
	
	/***
	 * 
	 * @param inputfile
	 * @return
	 */
	public String hashFile(File inputfile) {
        File filename = inputfile;

        FileInputStream fin = null;
        String changetoString = null;
        try {

            fin = new FileInputStream(filename);
            byte fileContent[] = new byte[(int)filename.length()];
            
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
	
	public LinkedHashMap<String, String> getMerkleHashList() {
		return(this.patientHashList);
	}
}

	
