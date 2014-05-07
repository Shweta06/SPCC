import java.util.*;
class Expression
{
	String lhs;
	String rhs;
}
class Code_Optimization
{
	static Vector<Expression> code=new Vector<Expression>();
	static int n;
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter no. of lines:");
		n=sc.nextInt();
		System.out.println("Enter code:");
		for(int i=0;i<n;i++)
		{
			String s[]=sc.next().split("=");
			Expression e = new Expression();
			e.lhs=s[0];
			e.rhs=s[1];
			code.add(e);
		}
		for(int i=0;i<n;i++)
		find(code.get(i));
		System.out.println("\n"+"Optimized code:");
		for(int i=0;i<n;i++)
		{
			Expression e=code.get(i);
			if(!e.lhs.equals("")||!e.rhs.equals(""))
			System.out.println(e.lhs+"="+e.rhs);	
		}
	}
	static void find(Expression e)
	{
		for(int i=0;i<n;i++)
		{
			Expression e1=code.get(i);
			if(e1!=e)
			{
				if(e1.rhs.equals(e.rhs))//To check for common sub-expression
				eliminate(e,e1);
				else if(e1.rhs.contains(e.rhs))//To replace existing common sub expression with LHS
				e1.rhs=e1.rhs.replace(e.rhs,e.lhs);
			}
		}
	}
	static void eliminate(Expression e,Expression e1)
	{
		for(int i=0;i<n;i++)
		{
			Expression e2=code.get(i);
			if(e2!=e)
			{
				if(e2.rhs.contains(e1.lhs))
				e2.rhs=e2.rhs.replace(e1.lhs,e.lhs);	
			}
		}
		e1.lhs="";
		e1.rhs="";
	}

}
/*Output
 Enter no. of lines:8
Enter code:
t0=a+b
t1=h*t0
t2=a+b
t3=b+c
t4=t2+t3
t5=b+c
t6=t5+t0
a=t6

Optimized code:
t0=a+b
t1=h*t0
t3=b+c
t4=t0+t3
t6=t3+t0
a=t6
*/
