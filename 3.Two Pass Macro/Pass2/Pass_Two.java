import java.io.*;
import java.util.*;
class Read
{
	File MDT=new File("MDT.txt");
	File MNT=new File("MNT.txt");
	static Scanner sc;	
	Vector<String> m1=new Vector<String>();
	Vector<String> m2=new Vector<String>();
	void readMNT()throws Exception
	{
		sc=new Scanner(MNT);
		while(sc.hasNextLine())
		{
			String s1[]=sc.nextLine().split("\t");
			m1.add(s1[1]+"\t"+s1[2]);
		}
	}
	void readMDT()throws Exception
	{
		sc=new Scanner(MDT);
		while(sc.hasNextLine())
		{
			String s1[]=sc.nextLine().split("\t");
			m2.add(s1[1]);
		}
	}
	int checkMacro(String s)
	{
		for(int i=0;i<m1.size();i++)
		{
			String s2[]=m1.get(i).split("\t");
			String s1=s2[0];
			if(s.equalsIgnoreCase(s1))
			return i;
		}
		return -1;
	}
	int getMDTC(int i)
	{
		String s[]=m1.get(i).split("\t");
		return Integer.parseInt(s[1]);
	}
	void write(int mdtc,Write w,ALA a)
	{
		int j=mdtc;
		String s=m2.get(j++);
		s=m2.get(j++);
		while(!s.equalsIgnoreCase("MEND"))
		{
			String s1[]=s.split(" ");
			String n=s1[0]+" ";
			String s2[]=s1[1].split(",");
			for(int i=0;i<s2.length;i++)
			{
				if(s2[i].charAt(0)!='#')
				n=n+s2[i]+",";
				else
				{
					int h=Integer.parseInt(s2[i].charAt(1)+"");
					h--;
					n=n+a.v.get(h)+",";
				}
			}
			n=n.substring(0,n.length()-1);
			w.writeOut(n);
			s=m2.get(j++);
		}
	}	
}
class Write
{
	PrintWriter pw;
	void init()throws Exception
	{
		pw=new PrintWriter("output.txt");
	}
	void writeOut(String s)
	{
		pw.println(s);
		pw.flush();
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
class Pass_Two
{
	public static void main(String args[])throws Exception
	{
		Read r=new Read();
		Write w=new Write();
		r.readMNT();
		r.readMDT();
		w.init();
		Vector<ALA> ala=new Vector<ALA>();
		ALA a=new ALA();
		File input=new File("pass1.txt");
		Scanner sc=new Scanner(input);
		while(sc.hasNextLine())
		{
			a=new ALA();
			String s=sc.nextLine();
			String s1[]=s.split(" ");
			int j=r.checkMacro(s1[0]);
			if(j==-1)
			{
				w.writeOut(s);
			}
			else
			{
				String s2[]=prepareALA(s1[1]);
				for(int i=0;i<s1.length;i++)
				a.add(s2[i]);	
				a.makeALA(s1[0]);
				int mdtc=r.getMDTC(j);
				r.write(mdtc-1,w,a);
			}
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
