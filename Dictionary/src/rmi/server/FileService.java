package rmi.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileService {
	private static HashMap<String, String> dictinary = new HashMap<>();
	private static String word = null;
	String fileName = "dict.txt";
	
	public FileService() 
	{
		if(dictinary.size() == 0) ProcessFile();
	}
	
	public String getDefinition(String word)
	{
		return dictinary.getOrDefault(word, "String not found");
	}

	public Boolean AddDefinition(String word, String definition)
	{
		dictinary.put(word, definition);
		return true;
	}
	
	public Boolean ModifyDefinition(String word, String newDefinition)
	{
		dictinary.replace(word, newDefinition);
		return true;
	}
	
	public Boolean DeleteDefinition(String word)
	{
		
		dictinary.remove(word);
		return true;
		
	}
	
	public static void ProcessFile()
	{
		// Open the file
		FileInputStream fstream;
		try {
			fstream = new FileInputStream("File/dict.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		StringBuilder definition = new StringBuilder();
		String strLine;
		//Processing the dict.txt. Words are all Capital and definitions are after "defn:"
		while ((strLine = br.readLine()) != null)   {
			if(isUpperCase(strLine))
			{
				word = strLine;
				System.out.println("Word: "+word);
			}
			if(strLine.contains("Defn:"))
			{
				String defnLine;
				while((defnLine = br.readLine()) != null )
				{
					if(isUpperCase(defnLine))
						{
							System.out.println("Adding to map");
							// Adding to hashmap
							dictinary.put(word, definition.toString());
							word = defnLine;
							System.out.println("Word: "+word);
						}
					else
					{
						System.out.println ("Defn: "+defnLine);
						//System.in.read();
						definition.append(defnLine+ "\n");						
					}
				}
				System.out.println("Adding last entry to the map");
				dictinary.put(word, definition.toString());
			}
			
		}
		//Close the input stream
		br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dictinary.size()); 
	}

	//Checking for all capitals. Means it is a word.
	public static boolean isUpperCase(String s)
	{
	    if(s.length() == 0) return false;
		for (int i=0; i<s.length(); i++)
	    {
	        if (Character.isLowerCase(s.charAt(i)))
	        {
	            return false;
	        }
	    }
	    return true;
	}
}
