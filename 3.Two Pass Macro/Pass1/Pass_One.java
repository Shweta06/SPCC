import java.util.*;
import java.io.*;
class Write
{
	File out=new File("pass1.txt");
	File MDT=new File("MDT.txt");
	File MNT=new File("MNT.txt");
	PrintWriter pw1,pw2,pw3;
	void init()throws Exception
	{
		pw1=new PrintWriter(out);
		pw2=new PrintWriter(MDT);
		pw3=new PrintWriter(MNT);
	}
	void writeOutput(String s)//Writes into output of Pass1
	{
		pw1.println(s);
		pw1.flush();
	}
	void writeMDT(String s)//Writes into MDT table
	{
		pw2.println(s);
		pw2.flush();
	}
	void writeMNT(String s)//Writes into MNT table
	{
		pw3.println(s);
		pw3.flush();
	}		
}
class ALA
{
	Vector<String> v=new Vector<String>();
	PrintWriter pw;	
	void add(String s)
	{
		v.add(s);
	}
	int check(String s)
	{
		return v.indexOf(s);
	}
	void makeALA(String s)throws Exception
	{
		s="ALA_"+s+".txt";
		pw=new PrintWriter(s);
		for(int i=0;i<v.size();i++)
		{
			String s1=(i+1)+"\t"+v.get(i);
			pw.println(s1);
			pw.flush();
		}
		pw.close();	
	}		
}
class Pass_one
{
	public static void main(String args[])throws Exception
	{
		File input=new File("input.txt");
		Scanner sc=new Scanner(input);
		Write w=new Write();
		w.init();
		Vector<ALA> ala=new Vector<ALA>();
		String m="";
		ALA a=new ALA();
		int MDTC=1,MNTC=1;//Pointers to MDT and MNT table
		while(sc.hasNextLine())
		{
			String s=sc.nextLine();
			if(s.equalsIgnoreCase("MACRO"))
			{
				a=new ALA();
				s=sc.nextLine();
				String s1[]=s.split(" ");
				w.writeMNT(MNTC+"\t"+s1[0]+"\t"+MDTC);
				m=m+s1[0]+"\t";
				MNTC++;
				w.writeMDT(MDTC+"\t"+s);
				MDTC++;
				s1=prepareALA(s1[1]);
				for(int i=0;i<s1.length;i++)
				a.add(s1[i]);	
				do
				{
					s=sc.nextLine();
					String n=s;
					if(!s.equalsIgnoreCase("MEND"))
					{
						
						s1=s.split(" ");
						n=s1[0]+" ";
						String s2[]=s1[1].split(",");
						for(int i=0;i<s2.length;i++)
						{
							int j=a.check(s2[i]);
							if(j==-1)
							n=n+s2[i]+",";
							else
							n=n+"#"+(j+1)+",";
						}
						n=n.substring(0,n.length()-1);
					}	
					w.writeMDT(MDTC+"\t"+n);
					MDTC++;	
				}while(!s.equalsIgnoreCase("MEND"));
				ala.add(a);
			}
			else
			w.writeOutput(s);	
		}
		String s1[]=m.split("\t");
		for(int i=0;i<ala.size();i++)
		{
			a=ala.get(i);
			a.makeALA(s1[i]);
		}	
	}
	static String[] prepareALA(String s)
	{
		String s1[]=s.split(",");
		String s2[]=new String[s1.length];
		for(int i=0;i<s2.length;i++)
		{
			int j=s1[i].indexOf("=");
			if(j==-1)
			s2[i]=s1[i];
			else
			{
				s2[i]=s1[i].substring(0,j);
			}
		}
		return s2;
	}	
}
