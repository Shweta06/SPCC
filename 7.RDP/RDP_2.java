import java.util.*;
class RDP 
{
	static String input;//input String
	static int index=0;//index
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter String:");
		input=sc.next();
		try
		{
			E();
		}
		catch(Exception e)
		{
			error();
		}
		if(index==input.length())
		System.out.println("Valid String");
		else
		System.out.println("Invalid String");	
	}
	static void E()
	{
		if(input.charAt(index)=='x')
		{
			index++;
			if(input.charAt(index)=='+')
			{
				index++;
				T();
			}
			else
			error();	
		}
		else
		error();	
	}
	static void T()
	{
		if(input.charAt(index)=='(')
		{
			index++;
			E();
			if(input.charAt(index)==')')
			index++;
			else
			error();	
		}
		else if(input.charAt(index)=='x')
		index++;
		else
		error();	
	}
	static void error()
	{
		System.out.println("Invalid String");
		System.exit(0);
	}
}
/* Grammar
E-->x+T
T-->(E)|x
*/
/* Output
 (1) Enter String:x+(x+(x+x))
    Valid String
 (2) Enter String:x+((x+x))
    Invalid String
 */
