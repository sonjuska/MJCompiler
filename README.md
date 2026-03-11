# MJCompiler – MicroJava Compiler

Compiler implementation for the MicroJava programming language, developed as part of the Programming Language Translators 1 (PP1) course at the School of Electrical Engineering, University of Belgrade.

The compiler translates syntactically and semantically correct MicroJava programs into MicroJava bytecode that runs on the MicroJava Virtual Machine (MJVM).

---

# Compiler Phases

The compiler consists of four main phases.

---

# 1. Lexical Analysis

Implemented using JFlex.

File:
spec/mjlexer.flex

Responsibilities:

- Tokenization of the source program
- Recognition of:
  - identifiers
  - constants
  - keywords
  - operators
  - comments
- Skipping whitespace and comments
- Reporting lexical errors

Output:

Token stream → Parser

---

# 2. Syntax Analysis

Implemented using AST-CUP (LALR(1) parser generator).

File:
spec/mjparser.cup

Responsibilities:

- Parsing token stream
- Building an Abstract Syntax Tree (AST)
- Error recovery
- Syntax error reporting

Generated classes:

rs.ac.bg.etf.pp1.ast.*

Example AST nodes:

- DesignatorStatement
- Expr
- Factor
- Term

---

# 3. Semantic Analysis

Implemented in:

SemanticAnalyzer.java

Responsibilities:

- Symbol table management
- Scope handling
- Type checking
- Context condition verification
- Detecting semantic errors

Uses the provided symbol table library:

symboltable-1.1.jar

Examples of semantic checks:

- variable declaration
- constant declaration
- array indexing
- method calls
- type compatibility
- return statements
- ternary operator validation

Example error:

ERROR: Incompatible types in assignment

---

# 4. Code Generation

Implemented in:

CodeGenerator.java

Responsibilities:

- Generating bytecode for the MicroJava Virtual Machine
- Traversing the AST
- Emitting MJVM instructions

Supported features (Level A):

- assignments
- arithmetic expressions
- ternary operator
- array access
- read / print
- increment / decrement
- method calls
- new array allocation

Example generated instruction sequence:

load
add
store

Output:

program.obj

---

# Running the Compiler

Compile the project and run:

java rs.ac.bg.etf.pp1.Compiler input.mj output.obj

Example:

java rs.ac.bg.etf.pp1.Compiler test/test301.mj test/test301.obj

---

# Running the Generated Program

Use the provided MJ runtime:

java -cp lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run test/test301.obj

---

# Example MicroJava Program

```java
program Test

int a;
int b;

{
    void main()
    {
        read(a);
        read(b);

        print(a + b);
    }
}
```

---

# Features Implemented

Level A functionality:

- variables and constants
- arrays
- assignments
- arithmetic expressions
- ternary operator
- read / print
- increment / decrement
- method calls
- new array allocation

---

# Technologies Used

- Java
- JFlex
- CUP / AST-CUP
- MicroJava VM
- Log4j
- Eclipse

---

# Author

Sonja Latinovic

School of Electrical Engineering  
University of Belgrade

Course:

Programming Language Translators 1 (PP1)

---

# License

Educational project – PP1 course.
