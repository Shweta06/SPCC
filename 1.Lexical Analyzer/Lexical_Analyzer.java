import java.util.*;
import java.io.*;
class Read
{
	File sp_sym=new File("special_symbols.txt");
	File key=new File("keywords.txt");
	File op=new File("operators.txt");
	static Scanner sc;
	char[] readSpecialSymbol()throws Exception
	{
		sc=new Scanner(sp_sym);
		String s=sc.nextLine();
		String s1[]=s.split(" ");
		char c[]=new char[s1.length];
		for(int i=0;i<s1.length;i++)
		c[i]=s1[i].charAt(0);
		return c;	
	}
	char[] readOperator()throws Exception
	{
		sc=new Scanner(op);
		String s=sc.nextLine();
		String s1[]=s.split(" ");
		char c[]=new char[s1.length];
		for(int i=0;i<s1.length;i++)
		c[i]=s1[i].charAt(0);
		return c;	
	}
	String[] readKeywords()throws Exception
	{
		sc=new Scanner(key);
		String s[]=sc.nextLine().split(" ");
		return s;	
	}	
}
class Write
{
	PrintWriter pw;
	void writeOutput(String s)throws Exception
	{
		pw=new PrintWriter("Output.txt");
		pw.println(s);
		pw.flush();
	}
	void writeFunctions(String s)throws Exception
	{
		pw=new PrintWriter("Functions.txt");
		pw.println(s);
		pw.flush();
	}
	void writeSymbols(String s)throws Exception
	{
		pw=new PrintWriter("Symbols.txt");
		pw.println(s);
		pw.flush();
	}	
}
class Lexical_Analyzer
{
	static char sp_sym[],op[];
	static String key[];
	public static void main(String args[])throws Exception
	{
		File input=new File("input.txt");
		Scanner sc=new Scanner(input);
		Read r=new Read();
		Write w=new Write();
		sp_sym=r.readSpecialSymbol();
		op=r.readOperator();
		key=r.readKeywords();
		Vector<String>sym=new Vector<String>();//Vector to store symbols
		Vector<String>fun=new Vector<String>();//Vector to store functions
		String output="";//Output String
		String symbol="";//Symbol String
		String function="";//Function String	
		while(sc.hasNextLine())
		{
			char c[]=sc.nextLine().toCharArray();
			int start=0,end=0;
			for(int i=0;i<c.length;i++)
			{
				if(c[i]=='"')
				{
					output=output+c[i];
					System.out.print(c[i]);
					i++;
					while(c[i]!='"')
					{	
						output=output+c[i];
						System.out.print(c[i]);
						i++;
					}
					output=output+c[i]+" ";
					System.out.print(c[i]+" ");
				}
				else
				{
					String temp=find(c[i]);//Check for special symbol or operator	
					if(c[i]=='('&&c[i+1]!='"')//Function found
					{
						String h=new String(c,start,end);
						fun.add(h);
						int j=fun.indexOf(h);
						function=function+h+" ";
						h="Fun#"+j+" ";
						output=output+h;
						System.out.print(h);
						start=i+1;
						end=0;					
					}	
					else if(temp!=null)
					{
						if(start==i)//Special Symbol or operator found
						{
							System.out.print(temp);
							output=output+temp;
							start=i+1;
							end=0;
						}
						else//Check for Keyword or Symbols
						{
							String temp1=find_Key(new String(c,start,end));
							if(temp1!=null)//Keyword found
							{
								System.out.print(temp1);
								output=output+temp1;
							}
							else//Symbol found
							{
								String h=new String(c,start,end);
								if(!sym.contains(h))
								{
									sym.add(h);
									symbol=symbol+h+" ";
								}
								int j=sym.indexOf(h);
								h="Sym#"+j+" ";
								output=output+h;
								System.out.print(h);	
							}
							System.out.print(temp);
							output=output+temp;
							start=i+1;
							end=-1;		
						}		
					}
					end++;
				}
			}
			System.out.println();
			output=output+"\n";	
		}
		w.writeOutput(output);
		w.writeSymbols(symbol);
		w.writeFunctions(function);	
	}
	static String find(char c)
	{
		for(int i=0;i<sp_sym.length;i++)//loop to find out special symbol
		{
			if(sp_sym[i]==c)
			return new String("Sp_Sym#"+(i+1)+" ");	
		}
		for(int i=0;i<op.length;i++)//loop to find out operators
		{
			if(op[i]==c)
			return new String("Op#"+(i+1)+" ");	
		}
		if(c==' ')
		return new String(" ");
		return null;		
	}
	static String find_Key(String s)
	{
		for(int i=0;i<key.length;i++)
		{
			if(key[i].equals(s))
			return "key#"+(i+1);	
		}
		return null;	
	}			
}