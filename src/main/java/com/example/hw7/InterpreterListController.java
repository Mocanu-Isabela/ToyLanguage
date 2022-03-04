package com.example.hw7;

import Controller.Controler;
import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.*;
import Model.expression.*;
import Model.statement.*;
import Model.types.*;
import Model.value.IValue;
import Model.value.IntValue;
import Repo.IRepository;
import Repo.Repository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InterpreterListController implements Initializable {
    @FXML
    private Button runButton;
    @FXML
    private ListView<IStatement> programsList;

    private InterpreterRunController interpreterController;

    public InterpreterRunController getInterpreterController(){
        return interpreterController;
    }

    public void setInterpreterController(InterpreterRunController main){
        this.interpreterController = main;
    }

    @FXML
    private IStatement selectExample(ActionEvent actionEvent){
        return programsList.getItems().get(programsList.getSelectionModel().getSelectedIndex());
    }

    private List<IStatement> setUp() {
        List<IStatement> controllers = new ArrayList<>();

        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", IntType.T),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new Model.value.IntValue(2))), new PrintStatement(new
                        Model.expression.VariableExpression("v"))));

        IStatement example2 = new CompoundStatement( new VariableDeclarationStatement("a", IntType.T),
                new CompoundStatement(new VariableDeclarationStatement("b", IntType.T),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new Model.value.IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new Model.value.IntValue(3)), new ValueExpression(new Model.value.IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                                        Model.value.IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a", BoolType.T),
                new CompoundStatement(new VariableDeclarationStatement("v",  IntType.T),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(Model.value.BoolValue.TRUE)),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        Model.value.IntValue(2))), new AssignmentStatement("v", new ValueExpression(new Model.value.IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf",
                new ValueExpression(new Model.value.StringValue("D:\\Facultate\\ANUL II\\an 2 sem I\\Metode avansate de programare(MAP)\\Lab\\hw4\\test.in"))),
                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")), new CompoundStatement(new VariableDeclarationStatement("varc",new IntType()),
                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"), new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseRFileStatement(new VariableExpression("varf"))))))))));

        IStatement example5 =new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",
                                        new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new Model.value.IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new Model.value.IntValue(30))),
                                                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new Model.value.IntValue(40))),
                                                        new PrintStatement(new HeapReadingExpresssion(new HeapReadingExpresssion(new VariableExpression("a"))))))))));

        IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),new CompoundStatement(
                new HeapAllocationStatement("v",new ValueExpression(new Model.value.IntValue(20))),new CompoundStatement(
                new PrintStatement(new HeapReadingExpresssion(new VariableExpression("v"))),new CompoundStatement(
                new HeapWritingStatement("v",new ValueExpression(new Model.value.IntValue(30))),
                new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpresssion(new VariableExpression("v")),new ValueExpression(new Model.value.IntValue(5))))))));

        IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new Model.value.IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new Model.value.IntValue(30))),
                                                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new Model.value.IntValue(40))),
                                                        new PrintStatement(new HeapReadingExpresssion(new HeapReadingExpresssion(new VariableExpression("a"))))))))));

        IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new Model.value.IntValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new Model.value.IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new Model.value.IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new Model.value.IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpresssion(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpresssion(new VariableExpression("a")))))))));

        IStatement example10 = new CompoundStatement(new VariableDeclarationStatement("v", IntType.T),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new Model.value.StringValue("r"))), new PrintStatement(new VariableExpression("v"))));

        controllers.add(example1);
        controllers.add(example2);
        controllers.add(example3);
        controllers.add(example4);
        controllers.add(example5);
        controllers.add(example6);
        controllers.add(example7);
        controllers.add(example8);
        controllers.add(example9);
        controllers.add(example10);

        return controllers;
    }


    @FXML
    public void setList() {
        List<IStatement> list = setUp();

        programsList.setItems(FXCollections.observableArrayList(list));
        programsList.getSelectionModel().select(0);
        runButton.setOnAction(actionEvent -> {
            int index = programsList.getSelectionModel().getSelectedIndex();
            IStatement selectedProgram = programsList.getItems().get(index);
            index++;
            ProgramState programState = new ProgramState(selectedProgram);
            IRepository repo = new Repository("log" + index + ".txt");
            Controler controller = new Controler(repo);
            controller.addProgram(programState);
            try{
                selectedProgram.typeCheck(new Dictionary<String, IType>());
                interpreterController.setController(controller);
            } catch(MyException | NonExistentKeyDictionaryException | TypeException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
            interpreterController.setController(controller);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList();
    }
}