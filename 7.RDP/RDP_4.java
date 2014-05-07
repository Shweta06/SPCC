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
		if(input.charAt(index)=='a')
		{
			index++;
			if(input.charAt(index)=='b')
			{
				index++;
				A();
			}
		}
		else 
		error();	
				
	}
	static void A()
	{
		try
		{
			if(input.charAt(index)=='a')
			{
				index++;
				A();	
			}
			else 
			B();
		}
		catch(Exception e)
		{
			B();
		}
	}
	static void B()
	{
		try
		{
			if(input.charAt(index)=='b')
			{
				index++;
				if(input.charAt(index)=='c')
					index++;
				else 
					return;
			}
			else 
			return;
		}
		catch(Exception e)
		{
			return;
		}
	}
	static void error()
	{
		System.out.println("Invalid String");
		System.exit(0);
	}
}
/* Grammar
S-->abA
A-->aA|B
B-->bc|b|epsilon
*/
/* Output
 (1)Enter String:aba
	Valid String
 (2)Enter String:abba
   Invalid String


 */
