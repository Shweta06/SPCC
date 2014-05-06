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
		if(input.charAt(index)=='c')
		{
			index++;
			W();
			if(input.charAt(index)=='d')
			index++;
			else
			error();	
		}
		else
		error();	
	}
	static void W()
	{
		if(input.charAt(index)=='a')
		{
			index++;
			if(input.charAt(index)=='b')
			index++;
			else 
			return;	
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
S-->cWd
W-->ab|a|epsilon
*/
/* Output
 (1) Enter String:cabd
	Valid String
 (2) Enter String:cfwd
	Invalid String
 */
