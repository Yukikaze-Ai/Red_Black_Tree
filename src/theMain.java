import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * CS 3345
 * Project 4
 * Hongyun Du
 * hxd171530
 */

public class theMain {
	// pattern for command with ":"
	static Pattern p = Pattern.compile(":");
	static Pattern ins = Pattern.compile("Insert");
	static Pattern cont = Pattern.compile("Contains");
	// pattern for command without ":"
	static Pattern Pt = Pattern.compile("PrintTree");

	public static void main(String[] ags) {

		String inputPath = ags[0];
		String outputPath = ags[1];

		// String inputPath = "input.txt";
		// String outputPath = "output.txt";
		File inputFile = new File(inputPath);
		String theInput = "";

		// -----------
		try {
			// open the input file and the output file
			InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile));
			BufferedReader br = new BufferedReader(reader);
			File outputFile = new File(outputPath);
			outputFile.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			// get every lines in the input file.
			theInput = br.readLine();
			if (theInput == null) {
				out.write(String.valueOf("Can't recognize the type indicator"));
				br.close();
				out.close();
				return;
			} else {
				String S1 = "Integer";
				String S2 = "String";
				if (theInput.compareTo(S1) == 0) {
					Integers(br, out);
				} else if (theInput.compareTo(S2) == 0) {
					Strings(br, out);
				} else {
					out.write(String.valueOf("Can't recognize the type indicator"));
					br.close();
					out.close();
					return;
				}
			}

		} catch (Exception e) {
			System.out.print(e);
		}
		// -----
	}

	public static void Strings(BufferedReader in, BufferedWriter out) {
		//deal this the String data type
		RedBlackTree<String> theTree = new RedBlackTree<String>();
		String theInput = "";
		try {
			while ((theInput = in.readLine()) != null) {
				Matcher colon = p.matcher(theInput);
				if (colon.find() == true) {
					// if found ":"
					// try to find Insert or contains
					Matcher insert = ins.matcher(theInput);
					Matcher contains = cont.matcher(theInput);
					if (insert.find() == true) {
						try {
							String[] spl = theInput.split(":");
							out.write(String.valueOf(theTree.insert(spl[1])));
							out.newLine();
						} catch (Exception e) {
							out.write(String.valueOf("Error in insert: " + e));
							out.newLine();
						}
					} else if (contains.find() == true) {
						try {
							String[] spl = theInput.split(":");
							out.write(String.valueOf(theTree.contains(spl[1])));
							out.newLine();
						} catch (Exception e) {
							out.write(String.valueOf("Error in contains: " + e));
							out.newLine();
						}
					}
				} else {
					// if did not find ":"
					// try to find PrintTree
					// if did not find all these Pattern, then print the error message.
					Matcher PrintTree = Pt.matcher(theInput);
					if (PrintTree.find() == true) {
						out.write(String.valueOf(theTree));
						out.newLine();
					} else {
						try {
							throw (new Exception());
						} catch (Exception e) {
							out.write(String.valueOf("Error in Line: " + theInput));
							out.newLine();
						}
					}
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void Integers(BufferedReader in, BufferedWriter out) {
		//deal with the Integer data type. 
		String theInput = "";
		RedBlackTree<Integer> theTree = new RedBlackTree<Integer>();
		try {
			while ((theInput = in.readLine()) != null) {
				Matcher colon = p.matcher(theInput);
				if (colon.find() == true) {
					// if found ":"
					// try to find Insert or contains
					Matcher insert = ins.matcher(theInput);
					Matcher contains = cont.matcher(theInput);
					if (insert.find() == true) {
						try {
							String[] spl = theInput.split(":");
							out.write(String.valueOf(theTree.insert(Integer.parseInt(spl[1]))));
							out.newLine();
						} catch (Exception e) {
							out.write(String.valueOf("Error in insert: " + e));
							out.newLine();
						}
					} else if (contains.find() == true) {
						try {
							String[] spl = theInput.split(":");
							out.write(String.valueOf(theTree.contains(Integer.parseInt(spl[1]))));
							out.newLine();
						} catch (Exception e) {
							out.write(String.valueOf("Error in contains: " + e));
							out.newLine();
						}
					}
				} else {
					// if did not find ":"
					// try to find PrintTree
					// if did not find all these Pattern, then print the error message.
					Matcher PrintTree = Pt.matcher(theInput);
					if (PrintTree.find() == true) {
						out.write(String.valueOf(theTree));
						out.newLine();
					} else {
						try {
							throw (new Exception());
						} catch (Exception e) {
							out.write(String.valueOf("Error in Line: " + theInput));
							out.newLine();
						}
					}
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
