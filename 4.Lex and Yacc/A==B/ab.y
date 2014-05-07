%{
	#include<stdio.h>
	#include<stdlib.h>
	int diff=0;
	void yyerror(char *s);
%}
%start line
%token char_a char_b
%%
line	:	seq	{if(diff==0) printf("Syntax correct\n");else printf("Syntax incorrect\n");}
	;
seq	:	char_a seq	{diff++;}
	|	char_b seq	{diff--;}
	|
	;
%%
int main(void)
{
	yyparse();
	return 0;
}
void yyerror(char *s)
{
	printf("%s",s);
	exit(0);
}
