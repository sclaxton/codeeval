import java.io.*;
import java.util.*;
/**
 * String Substitution
 * @param String[] args1 = {filepath};
 * @return print finalString
 * @author Peijie
 *
 */
/*The substitution process example I used for implementing my methods:
 * S="10011011001", list[]={0110,1001,1001,0,10,11}; 
 * F1=list[0]=0110, R1=list[1]=1001, F2=list[2]=1001, R2=list[3]=0, F3=list[4]=10, R3=list[5]=11;
 * S1="101001,11001"
 * S2="101001,10" 
 * S3="11100110" done
 */

public class Main {

    private static String[] F;
	private static String[] R;
	//static String[] args1 = {"F://args1.txt"};//this file contains 2 lines: 10011011001;0110,1001,1001,0,10,11
	                                                                     // 1011011010;01,110,101,11,10,011 

	public static void main(String[] args) {
		//args = args1;   
		Main m = new Main();
		String s = null;
		Scanner x = m.openFile(args[0]);
		while(x.hasNext()){
			String line = m.readLine(x);
			s = m.extractString(line);
			m.extractxN(line);

			String finalString = findFinalString(s);
			//System.out.println("input from file: "+line);
			System.out.println(finalString);

		}
		m.closeFile(x);
	}

	/** Takes String S and searches within it a string that matches FN.
	 *  The matching string is replaced by string RN.
	 *  The modified string is returned.
	 *  
	 * @param s
	 * @return finalString
	 */

	private static String findFinalString(String s) {
		String finalString = "";
		int strLen = s.length();

		//Puts the characters in String S into an array
		String[] strArray = new String[strLen];
		for (int i = 0; i < strLen; i++){
			strArray[i] = "" + s.charAt(i);
		}

		int sizeFN = F.length;

		//Puts the characters of FN into an array
		for (int i = 0; i < sizeFN; i++){
			int FLen = F[i].length();
			String[] FArray = new String[FLen];
			for (int j = 0; j < FLen; j++){
				FArray[j] = "" + F[i].charAt(j);
			}

			//Initializes finalString to blank
			finalString = "";

			int j = 0;
			int k = 0;

			//Variable m serves as the marker where strings start to match
			int m = 0;

			//Finds a portion of S that matches with FN and restarts search at marker if mismatches at any point
			while (j < FLen && k < strLen){
				if(FArray[j].equals(strArray[k])){
					if(j == 0){
						m = k;
					}
					j++;
					k++;
				} else {
					if(j == 0){
						m = k;
					}
					j = 0;
					k = m + 1;
				}

				/* Replaces FN for RN by replacing the first array of the matching portion of S with RN and
				 * turning the rest of the portion blank
				 */
				if(j == FLen){
					strArray[m] = "R" + R[i];
					for(k = 1; k < FLen; k++){
						strArray[k + m] = "R";
					}
					j = 0;
					k = m + FLen;
				}
			}
		}
		for(int i = 0; i < strLen; i++){
			strArray[i] = strArray[i].replace("R", "");
			finalString = finalString + strArray[i];
		}
		return finalString;
	}
	
	/*openFile method takes String file as param, which is the pathname for File method to provide file source
	 * @param String file
	 * @return Scanner x
	 */
	private Scanner openFile(String file) {
		Scanner x = null;
		try {
			x = new Scanner(new File(file));//produces and assign value to x from scanned file
		} catch(Exception e) {
			System.out.println("Error reading file.");
		}
		return x;
	}

	private String readLine(Scanner x) {
		String line = x.next();
		return line;
	}

	private void closeFile(Scanner x) {
		x.close();
	}

	/** Separates String S from the input string by taking the string before the ';'
	 */

	public String extractString(String tc) {
		String[] parts = tc.split(";");
		String line = parts[0];
		return line;
	}

	/** Separates FN and RN from the input string by taking the string after the ';' and 
	 * alternately storing them values split by ',' into their proper arrays
	 */

	public void extractxN(String tc) {
		String[] parts = tc.split(";");
		String[] nlist = parts[1].split(",");
		int nLen = nlist.length;
		int i = 0;
		String[] Fn = new String[nLen/2];
		String[] Rn = new String[nLen/2];
		for(int j = 0; j < nlist.length; j = j+2) {
			Fn[i] = nlist[j];
			Rn[i] = nlist[j+1];
			i++;
		}
		F = Fn;
		R = Rn;
	}

	/** Prints array for checking purposes	 
	 */

	private static void printArray(String[] list) {
		for (int i = 0; i < list.length; i++){
			System.out.print(list[i] + ", ");
		}
		System.out.println();
	}
