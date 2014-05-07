import java.util.*;
import java.io.*;
class Symbols
{
	Vector<String>na=new Vector<String>();//Stores Name
	Vector<Integer>val=new Vector<Integer>();//Stores Values
	Vector<Integer>len=new Vector<Integer>();//Stores Length
}
class Literals
{
	Vector<String>na=new Vector<String>();//Stores Name
	Vector<Integer>val=new Vector<Integer>();//Stores Values
}
class Read
{
	Symbols s=new Symbols();
	Literals l=new Literals();
	Vector<String>m=new Vector<String>();//Stores Machine OP-CODE
	Vector<String>p=new Vector<String>();//Stores Pseudo OP-CODE
	File mot=new File("MOT.txt");
	File pot=new File("POT.txt");
	File st=new File("ST.txt");
	File lt=new File("LT.txt");
	static Scanner sc;
	void readMOT()throws Exception//Read MOT
	{
		System.out.println("\nMachine Opcode Table\n"+"\nName\t"+"Opcode\t"+"Length\t"+"RR/RX\n");
		sc=new Scanner(mot);
		while(sc.hasNextLine())
		{
			String s1=sc.nextLine();
			System.out.println(s1);
			String s2[]=s1.split("\t");
			m.add(s2[0]);	
		}	
	}
	void readPOT()throws Exception//Read MOT
	{
		System.out.println("\nPseudo Opcode Table\n"+"\nOpcode\n");
		sc=new Scanner(pot);
		while(sc.hasNextLine())
		{
			String s1[]=sc.nextLine().split("\t");
			System.out.println(s1[0]);
			p.add(s1[0]);	
		}	
	}
	void readST()throws Exception//Read Symbol Table
	{
		sc=new Scanner(st);
		System.out.println("\nSymbol Table\n"+"\nName\t"+"Value\t"+"Lenght\t"+"R/A\n");
		while(sc.hasNextLine())
		{
			String s1=sc.nextLine();
			System.out.println(s1);
			String s2[]=s1.split("\t");
			s.na.add(s2[0]);
			s.val.add(Integer.parseInt(s2[1]));
			s.len.add(Integer.parseInt(s2[2]));	
		}
	}
	void readLT()throws Exception//Read Literal Table
	{
		sc=new Scanner(lt);
		System.out.println("\nLiteral Table\n"+"\nName\t"+"Value\t"+"Lenght\t"+"R/A\n");
		while(sc.hasNextLine())
		{
			String s1=sc.nextLine();
			System.out.println(s1);
			String s2[]=s1.split("\t");
			l.na.add(s2[0]);
			l.val.add(Integer.parseInt(s2[1]));	
		}
	}
	int searchPOT(String s1[])//Method to search in POT
	{
		for(int i=0;i<s1.length;i++)
		{
			if(p.contains(s1[i]))//If Pseudo opcode found return index
			return p.indexOf(s1[i]);	
		}
		return -1;//If Pseudo opcode not found return -1
	}
	int searchBase(String s1)//Searched for the Base Register Value
	{
		if(s.na.contains(s1))
		{	
			int i=s.na.indexOf(s1);
			return s.val.get(i);
		}
		return -1;
	}
	int searchMOT(String s1)//Method to search in MOT
	{
		if(m.contains(s1))//If Machine opcode found return index
		return m.indexOf(s1);
		return -1;//If Machine opcode not found return -1	
	}
	int searchSym(String s1)//Search for The Symbol
	{
		if(s.na.contains(s1))
		return s.val.get(s.na.indexOf(s1));
		return -1;	
	}
	int searchLit(String s1)//Search For the Literal
	{
		if(l.na.contains(s1))
		return l.val.get(l.na.indexOf(s1));
		return -1;		
	}
	void displayLT(int n,Write w)//Display Literals 
	{
		for(int i=0;i<l.na.size();i++)
		{
			n=n+4;
			String h=l.na.get(i);
			String g=n+"\t"+Integer.toBinaryString(Integer.parseInt(h.substring(3,h.length()-1)));
			System.out.println(g);
			w.writeOp(g);
		}	
	}	
}
class Write
{
	File bt=new File("BT.txt");
	File op=new File("output.txt");
	PrintWriter pw1,pw2;
	void init()throws Exception
	{
		pw1=new PrintWriter(bt);
		pw2=new PrintWriter(op);
	}
	void writeBT(String s)//Writes into Base Table
	{
		pw1.println(s);
		pw1.flush();
	}
	void writeOp(String s)//Writes into output file
	{
		pw2.println(s);
		pw2.flush();
	}			
}
class Pass_Two
{
	public static void main(String args[])throws Exception
	{
		Read r=new Read();
		Write w=new Write();
		w.init();
		r.readMOT();//Read MOT
		r.readPOT();//Read POT
		r.readST();//Read Symbol Table
		r.readLT();//Read Literal Table
		File input=new File("input.txt");//Input File
		File output=new File("Pass1.txt");//Output Of Pass-1
		Scanner in=new Scanner(input);
		Scanner out=new Scanner(output);
		boolean b[]=new boolean[15];
		boolean check=false;
		int base=0;//Current Base Register Value
		String q="";
		System.out.println("\nOUTPUT OF PASS-2\n");
		while(in.hasNextLine())
		{
			String s=in.nextLine();
			String s1[]=s.split(" ");
			int a=r.searchPOT(s1);
			if(a!=-1)//Pseudo Op-Code Found
			{
				switch(a)
				{
					case 0://For Start
					{
						String s2=out.nextLine();//Read a line from output of pass-1
						w.writeOp(s2);//Write in output of pass-2
						System.out.println(s2);
						break;
					}
					case 1://For Using
					{
						String h[]=s1[1].split(",");
						base=r.searchBase(h[1]);//Search For base Register
						if(base!=-1)//If Variable found
						b[base-1]=true;
						else if(base==-1)//If Register No. Found
						{
							base=Integer.parseInt(h[1]);
							b[base-1]=true;
						}	
						String s2=out.nextLine();//Read a line from output of pass-1
						w.writeOp(s2);//Write in output of pass-2
						System.out.println(s2);
						break;
					}
					case 2://For DC
					{
						if(s1[2].charAt(0)=='F'||s1[2].charAt(0)=='D')//Constant type specified
						{		
							s1[2]=s1[2].substring(2,s1[2].length()-1);
							int i=-1;
							for(int j=0;j<s1[2].length();j++)//Identify whether 1 or more constant are defined
							{
								if(s1[2].charAt(j)==',')
								{
									i=0;
									break;
								}	
							}	
							if(i==-1)//Single Constant
							{
								String h[]=out.nextLine().split("\t");//Read From Output Of Pass-1
								String g=h[0]+"\t"+Integer.toBinaryString(Integer.parseInt(h[1]));
								System.out.println(g);
								w.writeOp(g);//Write in output of pass-2
							}
							else//More than one constant
							{
								String y[]=s1[2].split(",");
								for(int j=0;j<y.length;j++)
								{
									String h[]=out.nextLine().split("\t");//Read From Output Of Pass-1
									String g=h[0]+"\t"+Integer.toBinaryString(Integer.parseInt(h[1]));
									System.out.println(g);
									w.writeOp(g);//Write in output of pass-2
								}	
							}		
						}
						else//Contanst type not specified
						{
							String h[]=out.nextLine().split("\t");//Read From Output Of Pass-1
							String g=h[0]+"\t"+Integer.toBinaryString(Integer.parseInt(h[1]));
							System.out.println(g);
							w.writeOp(g);//Write in output of pass-2
						}		
						break;
					}
					case 3://For DS
					case 4://For EQU
					case 5://For Drop
					{
						String s2=out.nextLine();//Read a line from output of pass-1
						w.writeOp(s2);//Write in output of pass-2
						System.out.println(s2);
						String s3[]=s2.split("\t");
						q=s3[0];
					}
					break;
					case 6://For End
					{
						String s2=out.nextLine();//Read a line from output of pass-1
						w.writeOp(s2);//Write in output of pass-2
						System.out.println(s2);
						String s3[]=s2.split("\t");
						q=s3[0];
						if(!check)
						{	
							int u=Integer.parseInt(q);
							r.displayLT(u,w);//Display the literals
						}
						break;
					}
					case 7://For Ltorg
					{
						String s2=out.nextLine();//Read a line from output of pass-1
						w.writeOp(s2);//Write in output of pass-2
						System.out.println(s2);
						String s3[]=s2.split("\t");
						q=s3[0];
						check=true;
						int u=Integer.parseInt(q);
						r.displayLT(u,w);//Display the literals
						break;
					}//Same operation to be performed for all Pseudo Op-codes(Case:3 onwards)
				}	
			}
			else//Pseudo Op-Code Not Found
			{
				int u=1;//For The Read String
				int k=r.searchMOT(s1[0]);
				if(k==-1)//If lablel found
				u=2;
				String h=out.nextLine();//Read Line From Output Of pass-1
				String g[]=h.split("\t");
				String g1[]=g[1].split(" ");
				String n=g[0]+"\t"+g1[0]+"   ";
				g=s1[u].split(",");
				for(int i=0;i<g.length;i++)
				{
					try//Number
					{
						int e=Integer.parseInt(g[i]);
						n=n+e+",";
					}
					catch(Exception e)//Symbol Or Literal
					{
						int j=r.searchSym(g[i]);//Search For Symbol in Symbol Table
						if(j!=-1)//Symbol Found
						{
							n=n+j+"(0,"+base+"),";
						}	
						else//Literal Found
						{
							j=r.searchLit(g[i]);//Search For Literal in Literal Table
							n=n+j+"(0,"+base+"),";
						}	
					}
				}
				n=n.substring(0,n.length()-1);
				System.out.println(n);
				w.writeOp(n);//Writes into Output file of pass 1	
			}	
		}		
		System.out.println("\nBASE TABLE\n");
		for(int i=0;i<b.length;i++)//Wrtie into base Table
		{
			String h=(i+1)+"\t";
			if(b[i])
			{	
				h=h+"Y\t"+"00";
				w.writeBT(h);
			}
			else
			{
				h=h+"N\t"+"  ";	
				w.writeBT(h);
			}
			System.out.println(h);		
		}	
	}	
}