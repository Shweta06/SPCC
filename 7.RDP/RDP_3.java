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
			S();
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
	static void S()
	{
		A();
		try
		{
			if(input.charAt(index)=='a')
			{
				index++;
				if(input.charAt(index)=='b')
				{	
					index++;
					A();
				}
				else
					error();	
			}
			else
			return ;
		}
		catch(Exception e)
		{
			if(input.charAt(input.length()-1)=='b')
			return;
			else
			error();	
		}
	}
	static void A()
	{
		if(input.charAt(index)=='a')
		{
			index++;
			if(input.charAt(index)=='b')
			index++;
			else
			error();	
		}
		else 
		return;	
	}
	static void error()
	{
		System.out.println("Invalid String");
		System.exit(0);
	}
}
/* Grammar
S-->AabA
A-->ab|epsilon
*/
/* Output
 (1) Enter String:abababa
   Invalid String
 (2) Enter String:ab
	Valid String

 */
