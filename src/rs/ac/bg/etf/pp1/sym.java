package rs.ac.bg.etf.pp1;

/**
 * Stub sym.java for lexer-only testing (no CUP/parser yet).
 * Later, CUP will generate sym.java from mjparser.cup.
 */
public class sym {

    //Special
    public static final int EOF   = 0;
    public static final int error = 1;

    //Identifiers & constants
    public static final int IDENT      = 2;
    public static final int NUMCONST   = 3;
    public static final int CHARCONST  = 4;
    public static final int BOOLCONST  = 5;

    //Keywords
    public static final int PROGRAM   = 10; // "program"
    public static final int BREAK     = 11; // "break"
    public static final int ENUM      = 12; // "enum"
    public static final int CLASS     = 13; // "class"
    public static final int ABSTRACT  = 14; // "abstract"
    public static final int ELSE      = 15; // "else"
    public static final int CONST     = 16; // "const"
    public static final int IF        = 17; // "if"
    public static final int NEW       = 18; // "new"
    public static final int PRINT     = 19; // "print"
    public static final int READ      = 20; // "read"
    public static final int RETURN    = 21; // "return"
    public static final int VOID      = 22; // "void"
    public static final int EXTENDS   = 23; // "extends"
    public static final int CONTINUE  = 24; // "continue"
    public static final int FOR       = 25; // "for"
    public static final int LENGTH    = 26; // "length"
    public static final int SWITCH    = 27; // "switch"
    public static final int CASE      = 28; // "case"

    //Operators
    public static final int PLUS        = 40; // +
    public static final int MINUS       = 41; // -
    public static final int MUL         = 42; // *
    public static final int DIV         = 43; // /
    public static final int MOD         = 44; // %

    public static final int EQEQ        = 45; // ==
    public static final int NEQ         = 46; // !=
    public static final int GT          = 47; // >
    public static final int GE          = 48; // >=
    public static final int LT          = 49; // <
    public static final int LE          = 50; // <=

    public static final int ANDAND      = 51; // &&
    public static final int OROR        = 52; // ||

    public static final int ASSIGN      = 53; // =

    public static final int INC         = 54; // ++
    public static final int DEC         = 55; // --

    public static final int QUESTION    = 56; // ?
    public static final int COLON       = 57; // :

    //Separators / punctuation
    public static final int SEMI        = 60; // ;
    public static final int COMMA       = 61; // ,
    public static final int DOT         = 62; // .

    public static final int LPAREN      = 63; // (
    public static final int RPAREN      = 64; // )
    public static final int LBRACKET    = 65; // [
    public static final int RBRACKET    = 66; // ]
    public static final int LBRACE      = 67; // {
    public static final int RBRACE      = 68; // }
}
