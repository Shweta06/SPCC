import java.util.*;
class Code_Generation 
{
	static char reg[]=new char[50];//Array to store all variables present in register
	static int count;//Counter on the register
	public static void main(String args[])
	{
		int n;
		String input[];
		String output="";//String to store output
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter no. of lines:");
		n=sc.nextInt();
		input=new String[n];
		System.out.println("Enter code:");
		for(int i=0;i<n;i++)
		input[i]=sc.next();
		output=output+"\n";
		for(int i=0;i<n;i++)
		{
			char temp[]=input[i].toCharArray();
			int k=find(temp[2]);
			int j=find(temp[4]);
			if(k==-1)//We have to move first operand into register
			{
				output=output+"MOV  "+"R"+count+","+temp[2]+"\n";
				reg[count]=temp[2];
			}	
			String s=findOperator(temp[3]);//find out operator
			output=output+s;
			if(j==-1)//Second Operand is not present in register
			{
				output=output+"R"+count+","+temp[4]+"\n";
				reg[count++]=temp[0];
			}
			else if(k==-1)//Second operator is present
			{
				output=output+"R"+count+",R"+j+"\n";
				reg[count++]=temp[0];
			}
			else if(j!=-1&&k!=-1)//Both operand present in register
			{
				output=output+"R"+k+",R"+j+"\n";
				reg[k]=temp[0];
			}
			if(i==input.length-1)//At last line we have to move final value in operand back
			{
				int h=find(temp[0]);
				output=output+"MOV  "+temp[0]+",R"+h+"\n";
			}
			output=output+"\n";
		}
		System.out.println(output);//Display output
	}
	static int find(char c)
	{
		for(int i=0;i<reg.length;i++)
		{
			if(reg[i]==c)
			return i;	
		}
		return -1;
	}
	static String findOperator(char c)
	{
		switch(c)
		{
			case '+':
			return "ADD  ";
			case '-':
			return "SUB  ";
			case '*':
			return "MUL  ";
			case '/':
			return "DIV  ";
		}
		return "";
	}
}
/*OUTPUT
 (1) Enter no. of lines:4
Enter code:
t=a*b
u=a-c
v=a*t
w=v+u

MOV  R0,a
MUL  R0,b

MOV  R1,a
SUB  R1,c

MOV  R2,a
MUL  R2,R0

ADD  R2,R1
MOV  w,R2

(2) Enter no. of lines:4
Enter code:
t=a-b
u=a-c
v=t+u
d=v+u

MOV  R0,a
SUB  R0,b

MOV  R1,a
SUB  R1,c

ADD  R0,R1

ADD  R0,R1
MOV  d,R0

(3) Enter no. of lines:4
Enter code:
t=a+b
u=a-c
v=a*t
w=v+u

MOV  R0,a
ADD  R0,b

MOV  R1,a
SUB  R1,c

MOV  R2,a
MUL  R2,R0

ADD  R2,R1
MOV  w,R2


 */


