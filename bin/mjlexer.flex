package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn+1);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn+1, value);
	}
	private int charErrStartLine;
	private int charErrStartCol;
	private StringBuilder charErrBuf = new StringBuilder();

%}

%cup
%line
%column

%xstate COMMENT
%xstate CHARERR

CHAR_ESC_N  = \\n
CHAR_ESC_R  = \\r
CHAR_ESC_T  = \\t
CHAR_ESC_0  = \\0
CHAR_ESC_Q  = \\\'
CHAR_ESC_BS = \\\\

PLAIN_CHAR  = [^'\\\r\n]
CHAR_ELEM = ({PLAIN_CHAR}|{CHAR_ESC_N}|{CHAR_ESC_R}|{CHAR_ESC_T}|{CHAR_ESC_0}|{CHAR_ESC_Q}|{CHAR_ESC_BS})
CHARCONST = \'({PLAIN_CHAR}|{CHAR_ESC_N}|{CHAR_ESC_R}|{CHAR_ESC_T}|{CHAR_ESC_0}|{CHAR_ESC_Q}|{CHAR_ESC_BS})\'

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" { }
"\r"   { }
"\n"   { }
"\f" 	{ }

"program"   { return new_symbol(sym.PROGRAM, yytext()); }
"break"     { return new_symbol(sym.BREAK, yytext()); }
"enum"    	{ return new_symbol(sym.ENUM, yytext()); }
"class"   	{ return new_symbol(sym.CLASS, yytext()); }
"abstract"    { return new_symbol(sym.ABSTRACT, yytext()); }
"else"    	{ return new_symbol(sym.ELSE, yytext()); }
"const"    	{ return new_symbol(sym.CONST, yytext()); }
"if"    	{ return new_symbol(sym.IF, yytext()); }
"new"    	{ return new_symbol(sym.NEW, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read"    	{ return new_symbol(sym.READ, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"extends"    { return new_symbol(sym.EXTENDS, yytext()); }
"continue"    { return new_symbol(sym.CONTINUE, yytext()); }
"for"    	{ return new_symbol(sym.FOR, yytext()); }
"length"    	{ return new_symbol(sym.LENGTH, yytext()); }
"switch"    { return new_symbol(sym.SWITCH, yytext()); }
"case"    	{ return new_symbol(sym.CASE, yytext()); }

<YYINITIAL> "//" 		{ yybegin(COMMENT); }

<COMMENT> . 			{ /* skip */ }
<COMMENT> "\r\n" 		{ yybegin(YYINITIAL); }
<COMMENT> "\n"			{ yybegin(YYINITIAL); }
<COMMENT> "\r" 			{ yybegin(YYINITIAL); }
<COMMENT> <<EOF>> {
    yybegin(YYINITIAL);
    return new_symbol(sym.EOF);
}

<YYINITIAL> "==" 		{ return new_symbol(sym.EQEQ, yytext()); }
<YYINITIAL> "!=" 		{ return new_symbol(sym.NEQ, yytext()); }
<YYINITIAL> ">=" 		{ return new_symbol(sym.GE, yytext()); }
<YYINITIAL> "<=" 		{ return new_symbol(sym.LE, yytext()); }
<YYINITIAL> "&&" 		{ return new_symbol(sym.ANDAND, yytext()); }
<YYINITIAL> "||" 		{ return new_symbol(sym.OROR, yytext()); }
<YYINITIAL> "++" 		{ return new_symbol(sym.INC, yytext()); }
<YYINITIAL> "--" 		{ return new_symbol(sym.DEC, yytext()); }
<YYINITIAL> "+" 		{ return new_symbol(sym.PLUS, yytext()); }
<YYINITIAL> "-" 		{ return new_symbol(sym.MINUS, yytext()); }
<YYINITIAL> "*" 		{ return new_symbol(sym.MUL, yytext()); }
<YYINITIAL> "/" 		{ return new_symbol(sym.DIV, yytext()); }
<YYINITIAL> "%" 		{ return new_symbol(sym.MOD, yytext()); }
<YYINITIAL> "=" 		{ return new_symbol(sym.ASSIGN, yytext()); }
<YYINITIAL> ">" 		{ return new_symbol(sym.GT, yytext()); }
<YYINITIAL> "<" 		{ return new_symbol(sym.LT, yytext()); }
<YYINITIAL> "?" 		{ return new_symbol(sym.QUESTION, yytext()); }
<YYINITIAL> ":" 		{ return new_symbol(sym.COLON, yytext()); }
<YYINITIAL> ";" 		{ return new_symbol(sym.SEMI, yytext()); }
<YYINITIAL> "," 		{ return new_symbol(sym.COMMA, yytext()); }
<YYINITIAL> "." 		{ return new_symbol(sym.DOT, yytext()); }
<YYINITIAL> "(" 		{ return new_symbol(sym.LPAREN, yytext()); }
<YYINITIAL> ")" 		{ return new_symbol(sym.RPAREN, yytext()); }
<YYINITIAL> "[" 		{ return new_symbol(sym.LBRACKET, yytext()); }
<YYINITIAL> "]" 		{ return new_symbol(sym.RBRACKET, yytext()); }
<YYINITIAL> "{" 		{ return new_symbol(sym.LBRACE, yytext()); }
<YYINITIAL> "}"			{ return new_symbol(sym.RBRACE, yytext()); }

[0-9]+  { return new_symbol(sym.NUMCONST, Integer.valueOf(yytext())); }        //numConst
"true"  { return new_symbol(sym.BOOLCONST, Boolean.TRUE); }                    //boolConst true
"false" { return new_symbol(sym.BOOLCONST, Boolean.FALSE); }                   //boolConst false


{CHARCONST} { return new_symbol(sym.CHARCONST, yytext()); }                    //charConst

\' {
    charErrStartLine = yyline + 1;
    charErrStartCol  = yycolumn + 1;
    charErrBuf.setLength(0);
    charErrBuf.append(yytext()); // "'"
    yybegin(CHARERR);
}

<CHARERR> "//" {
    //ending charerr before the comment
    System.out.println("[LEX ERROR]\t" + charErrBuf.toString() + " u liniji " + charErrStartLine + ", kolona " + charErrStartCol);
    yybegin(COMMENT);
}

<CHARERR> "\r\n" | "\n" | "\r" {
    System.out.println("[LEX ERROR]\t" + charErrBuf.toString() + " u liniji " + charErrStartLine + ", kolona " + charErrStartCol);
    yybegin(YYINITIAL);
}

<CHARERR> \' {
    charErrBuf.append(yytext()); 
    System.out.println("[LEX ERROR]\t" + charErrBuf.toString() + " u liniji " + charErrStartLine + ", kolona " + charErrStartCol);
    yybegin(YYINITIAL);
}

<CHARERR> <<EOF>> {
    System.out.println("[LEX ERROR]\t" + charErrBuf.toString() + " u liniji " + charErrStartLine + ", kolona " + charErrStartCol);
    yybegin(YYINITIAL);
    return new_symbol(sym.EOF);
}

<CHARERR> [^\n\r] {
    charErrBuf.append(yytext());
}


[a-zA-Z][a-zA-Z0-9_]* { return new_symbol(sym.IDENT, yytext()); }              //ident


. { System.out.println("[LEX ERROR]	" +yytext()+ " u liniji " + (yyline+1) + ", kolona " + (yycolumn+1)); }







