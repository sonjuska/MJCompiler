package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);

		if (args.length < 2) {
			log.error("Not enough arguments supplied! Usage: MJParserTest <source-file> <obj-file>");
			return;
		}

		File sourceCode = new File(args[0]);
		if (!sourceCode.exists()) {
			log.error("Source file [" + sourceCode.getAbsolutePath() + "] not found!");
			return;
		}

		File objFile = new File(args[1]);
		log.info("Compiling source file: " + sourceCode.getAbsolutePath());

		try (BufferedReader br = new BufferedReader(new FileReader(sourceCode))) {

			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();

			Program prog = (Program) (s.value);

			Tab.init();
			Tab.insert(Obj.Type, "bool", new Struct(Struct.Int));

			log.info(prog.toString(""));
			log.info("===================================");

			//semantika
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v);

			log.info(" Print count calls = " + v.printCallCount);
			log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			log.info("===================================");
			Tab.dump();

			//code generator
			if (!p.errorDetected && v.passed()) {

				log.info("Generating bytecode file: " + objFile.getAbsolutePath());
				if (objFile.exists()) objFile.delete();

				CodeGenerator codeGenerator = new CodeGenerator(v.designatorBase);
				prog.traverseBottomUp(codeGenerator);

				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));

				log.info("Parsiranje uspesno zavrseno!");
			} else {
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
		}catch(Exception e) {
			log.error("Parsiranje NIJE uspesno zavrseno!");
		}
	}
}