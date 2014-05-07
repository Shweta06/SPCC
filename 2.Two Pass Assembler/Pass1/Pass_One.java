import java.util.*;
import java.io.*;
class Read
{
	File mot=new File("MOT.txt");
	File pot=new File("POT.txt");
	Vector<String> m1,p1;
	Vector<Integer> m2;
	static Scanner sc;
	void readMOT()throws Exception//Method to Read MOT
	{
		sc=new Scanner(mot);
		m1=new Vector<String>();//Stores Machine OP-CODE
		m2=new Vector<Integer>();//Stores length of OP-CODE
		while(sc.hasNextLine())
		{
			String s=sc.nextLine();
			String s1[]=s.split("\t");
			m1.add(s1[0]);
			m2.add(Integer.parseInt(s1[2]));
		}
	}
	void displayMOT()//Method to display MOT
	{
		System.out.println();
		System.out.println("Machine OP-CODE Table");
		System.out.println();
		System.out.println("Op-code"+"\t"+"   Lenght");
		for(int i=0;i<m1.size();i++)
		{
			System.out.println("  "+m1.get(i)+"\t      "+m2.get(i));
		}
	}
	void readPOT()throws Exception//Method to read POT
	{
		sc=new Scanner(pot);
		p1=new Vector<String>();//Stores Pseudo OP-CODE
		while(sc.hasNextLine())
		{
			String s=sc.nextLine();
			String s1[]=s.split("\t");
			p1.add(s1[0]);
		}
	}	
	void dipslayPOT()//Method to display POT
	{
		System.out.println();
		System.out.println("PSEUDO OP-CODE Table");
		System.out.println();
		for(int i=0;i<p1.size();i++)
		{
			System.out.println(p1.get(i));
		}
	}
	int searchPOT(String s1[])//Method to search in POT
	{
		for(int i=0;i<s1.length;i++)
		{
			if(p1.contains(s1[i]))//If Pseudo opcode found return index
			return p1.indexOf(s1[i]);	
		}
		return -1;//If Pseudo opcode not found return -1
	}
	int searchMOT(String s1)//Method to search in MOT
	{
		if(m1.contains(s1))//If Machine opcode found return index
		return m2.get(m1.indexOf(s1));
		return -1;//If Machine opcode not found return -1	
	}			
}
class Write
{
	File st=new File("ST.txt");
	File lt=new File("LT.txt");
	File pass1op=new File("Pass1.txt");
	PrintWriter p1,p2,p3;
	static Scanner sc;
	void init()throws Exception
	{
		p1=new PrintWriter(st);
		p2=new PrintWriter(lt);
		p3=new PrintWriter(pass1op);
	}	
	void writeSymbol(String s)//Writes into Symbol Table
	{
		p1.println(s);
		p1.flush();
	}
	void writeLiteral(String s)//Writes into literal Table 
	{
		p2.println(s);
		p2.flush();
	}
	void writeOp(String s)//Writes into Output of pass1
	{
		p3.println(s);
		p3.flush();
	}
	void displaySym()throws Exception//Display Symbol Table
	{
		System.out.println("\nSymbol Table");
		//System.out.println("\nName"+"\tValue"+"\tLength"+"\tR/A");
		System.out.println();
		sc=new Scanner(st);
		while(sc.hasNextLine())
		System.out.println(sc.nextLine());
		System.out.println();	
	}
	void displayLit()throws Exception//Display Literal Table
	{
		System.out.println("\nLiteral Table");
		//System.out.println("\nName"+"\tValue"+"\tLength"+"\tR/A");
		System.out.println();
		sc=new Scanner(lt);
		while(sc.hasNextLine())
		System.out.println(sc.nextLine());
		System.out.println();
	}					
}
class Symbols
{
	Vector<String>v=new Vector<String>();
	void add(String s)//Add into symbol vector
	{
		v.add(s);
	}
	int searchSym(String s)//search into symbol vector
	{
		if(v.contains(s))
		return v.indexOf(s);//If found return index
		return -1;//If bot found return -1	
	}
}
class Literal
{
	Vector<String>v=new Vector<String>();
	void add(String s)
	{
		v.add(s);
	}	
}
class Pass_One 
{
	public static void main(String args[])throws Exception
	{
		Read r=new Read();
		Write w=new Write();
		Symbols sym=new Symbols();
		Literal l=new Literal();
		r.readMOT();
		r.displayMOT();
		r.readPOT();
		r.dipslayPOT();
		w.init();
		System.out.println();
		boolean b=false;
		File input=new File("input.txt");//Input file 
		Scanner sc=new Scanner(input);//Scanner to read input file
		int lc=0;//Location Counter initialised to zero
		System.out.println("OUTPUT OF PASS-1\n");
		while(sc.hasNextLine())
		{
			String s=sc.nextLine();
			String s1[]=s.split(" ");
			int a=r.searchPOT(s1);//Search for the Pseudo opcode
			if(a!=-1)//Pseudo Opcode found
			{
				switch(a)
				{
					case 0://For Start
					{
						System.out.println(lc);
						w.writeOp(lc+"");//Writes into Output file of pass 1
						sym.add(s1[0]);//Add symbol into symbol vector
						w.writeSymbol(s1[0]+"\t"+lc+"\t"+"01"+"\t"+"R");//writes symbol into symbol table
						break;
					}
					case 1://For Using
					{
						System.out.println(lc);
						w.writeOp(lc+"");//Writes into Output file of pass 1
						break;
					}
					case 2://For DC
					{
						if(s1[2].charAt(0)=='F'||s1[2].charAt(0)=='D')//Constant type specified
						{
							int u=0;
							if(s1[2].charAt(0)=='F')//For Full Word
							u=4;
							else if(s1[2].charAt(0)=='D')//For Double Word
							u=8;
							if(u==4)//For Full Word	
							{
								if(lc%4!=0)//Memory adjustment for full word
								lc=lc+2;	
							}
							if(u==8)//For Double Word
							{
								if(lc%8!=0)//Memory adjustment for double word
								{
									if((lc+2)%8!=0)
									lc=lc+2;
									else if((lc+4)%8!=0)
									lc=lc+4;
									else
									lc=lc+6;			
								}	
							}	
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
								System.out.println(lc+"\t"+s1[2]);
								w.writeOp(lc+"\t"+s1[2]);//Writes into Output file of pass 1
								sym.add(s1[0]);//Add symbol into symbol vector
								w.writeSymbol(s1[0]+"\t"+lc+"\t0"+u+"\t"+"R");//writes symbol into symbol table
								lc=lc+u;
							}
							else//More than one constant
							{
								String y[]=s1[2].split(",");
								int q=0;
								if(s1[2].charAt(0)=='F')
								q=4*y.length;
								else 
								{
									if(s1[2].charAt(0)=='D')
									q=8*y.length;
									else
									q=4*y.length;
								}	
								w.writeSymbol(s1[0]+"\t"+lc+"\t"+q+"\t"+"R");//writes symbol into symbol table
								for(int j=0;j<y.length;j++)
								{
									System.out.println(lc+"\t"+y[j]);
									w.writeOp(lc+"\t"+y[j]);//Writes into Output file of pass 1
									sym.add(s1[0]);//Add symbol into symbol vector
									lc=lc+u;
								}	
							}		
						}
						else//Contanst type not specified
						{
							sym.add(s1[0]);//Add symbol into symbol vector
							if(lc%4!=0)//Memory Adjustment
							lc=lc+2;
							System.out.println(lc+"\t"+s1[2]);
							w.writeOp(lc+"\t"+s1[2]);//Writes into Output file of pass 1	
							w.writeSymbol(s1[0]+"\t"+lc+"\t"+04+"\t"+"R");//writes symbol into symbol table
							lc=lc+04;
						}		
						break;				
					}
					case 3://For DS
					{
						System.out.println(lc);
						int j=4;
						w.writeOp(lc+"");//Writes into Output file of pass 1
						if(s1[2].charAt(s1[2].length()-1)=='F')//Check for Full Word
						{
							j=4;
							if(lc%4!=0)//Memory Adjustment
							lc=lc+2;	
						}		
						else if(s1[2].charAt(s1[2].length()-1)=='D')//Check for Double Word
						{
							j=8;
							if(lc%8!=0)//Memory adjustment for double word              
							{
								if((lc+2)%8!=0)
								lc=lc+2;
								else if((lc+4)%8!=0)
								lc=lc+4;
								else
								lc=lc+6;			
							}
						}
						int k=Integer.parseInt(s1[2].substring(0,s1[2].length()-1));//calculate no. of storage spaces			
						sym.add(s1[0]);//Add symbol into symbol vector
						w.writeSymbol(s1[0]+"\t"+lc+"\t"+(k*j)+"\t"+"R");//writes symbol into symbol table
						lc=lc+k*j;
						break;
					}
					case 4://For EQU
					{
						System.out.println(lc);
						w.writeOp(lc+"");//Writes into Output file of pass 1
						sym.add(s1[0]);//Add symbol into symbol vector
						w.writeSymbol(s1[0]+"\t"+s1[2]+"\t"+"01"+"\t"+"A");//writes symbol into symbol table
						break;
					}
					case 5://For Drop
					{
						System.out.println(lc);
						w.writeOp(lc+"");//Writes into Output file of pass 1
						break;
					}
					case 6://For End
					{
						System.out.println(lc);
						w.writeOp(lc+"");//Writes into Output file of pass 1
						if(!b)
						{		
							for(int i=0;i<l.v.size();i++)
							{
								String n=l.v.get(i)+"\t"+lc+"\t"+"04"+"\t"+"R";
								w.writeLiteral(n);//Write into Literal Table
								lc=lc+4;
							}
						}
						break;
					}
					case 7://For LTORG
					{
						System.out.println(lc);
						w.writeOp(lc+"");//Writes into Output file of pass 1
						b=true;
						for(int i=0;i<l.v.size();i++)
						{
							String n=l.v.get(i)+"\t"+lc+"\t"+"04"+"\t"+"R";
							w.writeLiteral(n);//Write into Literal Table
							lc=lc+4;
						}
						break;
					}
				}	
			}
			else//Pseudo Opcode not found
			{
				int u=1,v=0;
				int i=r.searchMOT(s1[0]);
				if(i==-1)//If lablel found
				{
					v=1;
					u=2;
					sym.add(s1[0]);//Add symbol into symbol vector
					w.writeSymbol(s1[0]+"\t"+lc+"\t"+"01"+"\t"+"R");//writes symbol into symbol table					
				}
				i=r.searchMOT(s1[v]);
				String g[]=s1[u].split(",");
				int y[]=new int[g.length];		
				for(int q=0;q<g.length;q++)//Check whether it is a symbol or not
				{
					try//Number
					{
						int e=Integer.parseInt(g[q]);
						y[q]=0;
					}
					catch(Exception e)//Symbol or Literal
					{
						int z=searchLit(g[q]);//search for literal
						if(z==1)//literal found	
						l.add(g[q]);	
						y[q]=-1;
					}				
				}
				String n=lc+"\t"+s1[v]+"   ";
				for(int q=0;q<g.length;q++)//Get the output
				{
					if(y[q]==-1)
					 n=n+"___,";	
					 else
					 n=n+g[q]+",";			
				}
				n=n.substring(0,n.length()-1);
				System.out.println(n);
				w.writeOp(n);//Writes into Output file of pass 1
				lc=lc+i;		
			}
		}
		w.displaySym();//Display Symbol Table
		w.displayLit();//Display Literal Table
	}
	static int searchLit(String s)//Search for literal
	{
		if(s.charAt(1)=='F'&&s.charAt(0)=='=')
		return 1;
		return -1;	
	}	
}
