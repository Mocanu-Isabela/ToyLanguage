package View;
import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Model.ProgramState;
import Model.adt.*;
import Model.expression.*;
import Model.statement.*;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.*;
import Controller.Controler;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

//    static Repository myRepository = new Repository();
//    static Controler myControler = new Controler(myRepository);

    public static void main(String[] args) throws Exception {

        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", IntType.T),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableExpression("v"))));

        IStatement example2 = new CompoundStatement( new VariableDeclarationStatement("a", IntType.T),
                new CompoundStatement(new VariableDeclarationStatement("b", IntType.T),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a", BoolType.T),
                new CompoundStatement(new VariableDeclarationStatement("v",  IntType.T),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(BoolValue.TRUE)),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf",
                new ValueExpression(new StringValue("D:\\Facultate\\ANUL II\\an 2 sem I\\Metode avansate de programare(MAP)\\Lab\\hw4\\test.in"))),
                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")), new CompoundStatement(new VariableDeclarationStatement("varc",new IntType()),
                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"), new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                     new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseRFileStatement(new VariableExpression("varf"))))))))));

//        IStatement example4v2 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
//                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
//                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
//                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
//                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
//                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
//                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
//                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
//                                                                        new CloseRFileStatement(new VariableExpression("varf"))))))))));
//
//        IStatement example4v3 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
//                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
//                        new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
//                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
//                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
//                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
//                                                                new CloseRFileStatement(new VariableExpression("varf"))))))))));

        IStatement example5 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(40))),
                                                        new PrintStatement(new HeapReadingExpresssion(new HeapReadingExpresssion(new VariableExpression("a"))))))))));

//        IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
//                        new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
//                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",
//                                        new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
//                                            new PrintStatement(new VariableExpression("v")))));

        IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),new CompoundStatement(
                new HeapAllocationStatement("v",new ValueExpression(new IntValue(20))),new CompoundStatement(
                new PrintStatement(new HeapReadingExpresssion(new VariableExpression("v"))),new CompoundStatement(
                new HeapWritingStatement("v",new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpresssion(new VariableExpression("v")),new ValueExpression(new IntValue(5))))))));

        IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(40))),
                new PrintStatement(new HeapReadingExpresssion(new HeapReadingExpresssion(new VariableExpression("a"))))))))));

        IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpresssion(new VariableExpression("a"))))))),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpresssion(new VariableExpression("a")))))))));

        IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("v", IntType.T),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new StringValue("l"))), new PrintStatement(new VariableExpression("v"))));

        IHeap<Integer, IValue> heap =  new Heap<>();
        IHeap<Integer, IValue> heap1 =  new Heap<>();
        IHeap<Integer, IValue> heap2 =  new Heap<>();
        IHeap<Integer, IValue> heap3 =  new Heap<>();

//        example1.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe1 = new TheStack<IStatement>();
        exe1.push(example1);
        ProgramState program1 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example1, new FileTable<>(), heap);
        IRepository repo1 = new Repository(program1, "log1.txt");
        Controler ctr1 = new Controler(repo1);

//        example2.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe2 = new TheStack<IStatement>();
        exe2.push(example2);
        ProgramState program2 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example2, new FileTable<>(), heap);
        IRepository repo2 = new Repository(program2, "log2.txt");
        Controler ctr2 = new Controler(repo2);

//        example3.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe3 = new TheStack<IStatement>();
        exe3.push(example3);
        ProgramState program3 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example3, new FileTable<>(), heap);
        IRepository repo3 = new Repository(program3, "log3.txt");
        Controler ctr3 = new Controler(repo3);

//        example4.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe4 = new TheStack<IStatement>();
        exe4.push(example4);
        ProgramState program4 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example4, new FileTable<>(), heap);
        IRepository repo4 = new Repository(program4, "log4.txt");
        Controler ctr4 = new Controler(repo4);

//        example5.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe5 = new TheStack<IStatement>();
        exe5.push(example5);
        ProgramState program5 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example5, new FileTable<>(), heap);
        IRepository repo5 = new Repository(program5, "log5.txt");
        Controler ctr5 = new Controler(repo5);

//        example6.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe6 = new TheStack<IStatement>();
        exe6.push(example6);
        ProgramState program6 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example6, new FileTable<>(), heap);
        IRepository repo6 = new Repository(program6, "log6.txt");
        Controler ctr6 = new Controler(repo6);

//        example7.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe7 = new TheStack<IStatement>();
        exe7.push(example7);
        ProgramState program7 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example7, new FileTable<>(), heap1);
        IRepository repo7 = new Repository(program7, "log7.txt");
        Controler ctr7 = new Controler(repo7);

//        example8.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe8 = new TheStack<IStatement>();
        exe8.push(example8);
        ProgramState program8 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example8, new FileTable<>(), heap2);
        IRepository repo8 = new Repository(program8, "log8.txt");
        Controler ctr8 = new Controler(repo8);

//        example9.typeCheck(new Dictionary<String, IType>());
        IStack<IStatement> exe9 = new TheStack<IStatement>();
        exe9.push(example9);
        ProgramState program9 = new ProgramState(new TheStack<>(), new Dictionary<>(), new TheList<>(), example9, new FileTable<>(), heap3);
        IRepository repo9 = new Repository(program9, "log9.txt");
        Controler ctr9 = new Controler(repo9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example1.toString(),ctr1));
        menu.addCommand(new RunExample("2",example2.toString(),ctr2));
        menu.addCommand(new RunExample("3",example3.toString(),ctr3));
        menu.addCommand(new RunExample("4",example4.toString(),ctr4));
        menu.addCommand(new RunExample("5",example5.toString(),ctr5));
        menu.addCommand(new RunExample("6",example6.toString(),ctr6));
        menu.addCommand(new RunExample("7",example7.toString(),ctr7));
        menu.addCommand(new RunExample("8",example8.toString(),ctr8));
        menu.addCommand(new RunExample("9",example9.toString(),ctr9));
        menu.show();
    }
}
