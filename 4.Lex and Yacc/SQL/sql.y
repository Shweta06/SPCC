%{
	#include<stdio.h>
	#include<stdlib.h>
	void yyerror(char *s);
%}
%start line
%token select1 from where and star comma quote equal identifier constant
%%
line	:	select1 fields from tables		{printf("Syntax correct\n");}
	|	select1 fields from tables where conditions	{printf("syntax correct\n");}
	;
fields	:	star				
	|	identifier	
	|	identifier comma fields
	;
tables	:	identifier
	|	identifier comma tables
	;
conditions	:	condition
		|	condition and conditions
		;
condition	:	identifier equal constant
		|	identifier equal quote identifier quote
		;
%%
int main(void)
{
	yyparse();
	return 1;
}
void yyerror(char *s)
{
	printf("%s\n",s);
	exit(0);
}
