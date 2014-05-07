import java.util.*;
class Code
{
	String lhs;
	String rhs[];
	String line;
	boolean b;
	Code()
	{
		lhs="";
		line="";
		rhs=new String[0];
		b=true;
	}
}
class Loop_Invariant
{
	static int n;
	static Vector<String> var=new Vector<String>();
	static Vector<Code> lines=new Vector<Code>();
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter no. of lines:");
		n=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter code:");
		for(int i=0;i<n;i++)
		{
			Code c=new Code();
			c.line=sc.nextLine();
			if(c.line.startsWith("for("))
			{	
				var.add(c.line.charAt(4)+"");//Store loop variable in var vector
				c.lhs=c.line;
			}
			else
			{
				String s[]=c.line.split("=");
				c.lhs=s[0].trim();
				if(s.length>1)
				c.rhs=s[1].split("\\[|\\]|\\s");//Split on the basis of [ or ] or white spaces
			}
			lines.add(c);
		}
		for(int i=0;i<n;i++)
		{
			Code c=lines.get(i);
			if(!findVariant(c))
			{
				if(!findSelf(c))
				c.b=false;
			}
		}
		System.out.println("\n"+"Optimized Code");
		for(int i=0;i<n;i++)//To display loop invariant code 
		{
			if(!lines.get(i).b)
			System.out.println(lines.get(i).line);	
		}
		for(int i=0;i<n;i++)//Display remaining code
		{
			if(lines.get(i).b)
			System.out.println(lines.get(i).line);	
		}
	}
	static boolean findVariant(Code c)//To check whether the loop variable present in expression
	{
		if(c.rhs.length==0)
		return true;	
		for(int i=0;i<c.rhs.length;i++)
		{
			for(int j=0;j<var.size();j++)
			{
				if(c.rhs[i].contains(var.get(j)))
				{	
					var.add(c.lhs);
					return true;
				}
			}
		}
		return false;
	}
	static boolean findSelf(Code c)//To check whether self operation is present or not
	{
		for(int i=0;i<c.rhs.length;i++)
		{
			if(c.rhs[i].equals(c.lhs))
			return true;		
		}
		return false;
	}
}
/*Output
 (1)Enter no. of lines:9
Enter code:
for(i=0;i<10;i++)
{
d = num /denom;
for(j=0;j<10;j++)
{
sum = sum + j;
k = d;
}
}

Optimized Code
d = num /denom;
k = d;
for(i=0;i<10;i++)
{
for(j=0;j<10;j++)
{
sum = sum + j;
}
}

(2)Enter no. of lines:6
Enter code:
for(i=0;i<10;i++)
{
a = a + b;
b = a[i] + n;
c= d + j;
}

Optimized Code
c= d + j;
for(i=0;i<10;i++)
{
a = a + b;
b = a[i] + n;
}
*/