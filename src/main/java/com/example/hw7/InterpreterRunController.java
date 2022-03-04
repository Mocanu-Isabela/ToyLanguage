package com.example.hw7;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import Controller.Controler;
import Exceptions.MyException;
import Model.ProgramState;
import Model.adt.IStack;
import Model.statement.IStatement;
import Model.value.IValue;
import com.example.hw7.Exceptions.FinishedProgramException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InterpreterRunController implements Initializable{
//    @FXML
//    private Button exitButton;

    @FXML
    private ListView<String> exeStackView;
    @FXML
    private TableView<Map.Entry<String, IValue>> symTable;
    @FXML
    private TableColumn<Map.Entry<String, IValue>,String> symTableNames;
    @FXML
    private TableColumn<Map.Entry<String, IValue>,String> symTableValues;
    @FXML
    private TextField programStatesCount;
    @FXML
    private Button execButton;
    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer,IValue>, Integer> heapTableAddr;
    @FXML
    private TableColumn<Map.Entry<Integer,IValue>, String> heapTableValues;
    @FXML
    private ListView<String> outView;
    @FXML
    private ListView<String> fileTableView;
    private Controler controller;
    @FXML
    private ListView<Integer> programIdentifiers;

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getId).collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getRepository().getProgramList();

        programIdentifiers.setItems(FXCollections.observableArrayList(controller.getRepository().getProgramList().stream().map(ProgramState::getId).collect(Collectors.toList())));
        programIdentifiers.refresh();

        programStatesCount.setText("" + programStates.size());
    }


    public void executeOneStep() {
        if(controller==null){
            Alert error = new Alert(Alert.AlertType.ERROR,"No program selected!");
            error.show();
            execButton.setDisable(true);
            return;
        }
        ProgramState programState = getSelectedProgramState();
        if(programState!=null && !programState.isNotCompleted()){
            Alert error = new Alert(Alert.AlertType.ERROR,"Nothing left to execute!");
            error.show();
            return;
        }
        try {
            controller.executeOneStep();
            changeProgramState(programState);
            if(controller.getRepository().getProgramList().size()==0)
                execButton.setDisable(true);
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
            execButton.setDisable(true);
        }
    }

    private void changeProgramState(ProgramState currentProgram) {
        if (currentProgram == null) {
            return;
        }
        populateProgramStateIdentifiers();
        populateExeStackView(currentProgram);
        populateSymTableView(currentProgram);
        populateOutView(currentProgram);
        populateFileTableView(currentProgram);
        populateHeapTableView(currentProgram);
    }
    private void populateHeapTableView(ProgramState program){
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(program.getHeap().toHashMap().entrySet())));
        heapTableView.refresh();
    }

    private void populateOutView(ProgramState program){
        outView.setItems(FXCollections.observableArrayList(program.getOutputList().getContent()));
    }

    private void populateFileTableView(ProgramState program){
        fileTableView.setItems(FXCollections.observableArrayList(program.getFileTable().get_content().keySet()));
    }

    private void populateExeStackView(ProgramState program) {
        IStack<IStatement> stack = program.getExecutionStack();
        List<String> stackOutput = new ArrayList<>();
        for (IStatement stm : stack.getStack()){
            stackOutput.add(stm.toString());
        }
        Collections.reverse(stackOutput);
        exeStackView.setItems(FXCollections.observableArrayList(stackOutput));

    }

    private void populateSymTableView(ProgramState givenProgramState){
        symTable.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getSymbolTable().get_content().entrySet())));
        symTable.refresh();
    }

    public void setController(Controler contr){
        this.controller = contr;
        populateProgramStateIdentifiers();
        execButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heapTableAddr.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());

        symTableNames.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        symTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        programIdentifiers.setOnMouseClicked(mouseEvent -> changeProgramState(getSelectedProgramState()));
        execButton.setDisable(true);
    }

    private ProgramState getSelectedProgramState(){
        if(programIdentifiers.getSelectionModel().getSelectedIndex() == -1)
            return null;
        int id = programIdentifiers.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramWithId(id);
    }

    public Controler getController(){
        return controller;
    }
    
    
    
//    public void RunProgramController(com.example.hw7.Controller.Controler myControler)
//    {
//        this.myControler = myControler;
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//    startProgram();
//    idListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent mouseEvent) {
//            setStackAndSymbols();
//        }
//    });
//    exitButton.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent actionEvent) {
//            Stage stage = (Stage) tableHeap.getScene().getWindow();
//            stage.close();
//        }
//    });
//    runButton.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent actionEvent) {
//            try{
//                myControler.oneStepForAllProgramGUI();
//                updateGUI;
//            }
//            catch (FinishedProgramException exc){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Program finished!");
//                alert.setContentText("Program successfully finished!");
//                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
//                confirm.setDefaultButton(false);
//                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent");
//                alert.showAndWait();
//                Stage stage = (Stage) tableHeap.getScene().getWindow();
//                stage.close();
//            }
//            catch(Exceptions.MyException exc){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Program finished!");
//                alert.setContentText(exc.getMessage());
//                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
//                confirm.setDefaultButton(false);
//                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent");
//                alert.showAndWait();
//                Stage stage = (Stage) tableHeap.getScene().getWindow();
//                stage.close();
//            }
//        }
//    });
//    }
//
//    public void startProgram(){
//        setProgramStateList();
//        idListView.getSelectionModel().selectFirst();
//        setNumberLabel();
//        setOutputList();
//        setStackList(0);
//        setFileList();
//        setHeapTable();
//        setSymbolsTable(0);
//    }
//
//    public void updateGUI(){
//        setProgramStateList();
//        if(idListView.getSelectionModel().getSelectedItems() == null) {
//            idListView.getSelectionModel().selectFirst();
//        }
//        setNumberLabel();
//        setOutputList();
//        setFileList();
//        setHeapTable();
//        setStackAndSymbols();
//    }
//
//    public void setNumberLabel(){
//        nrProgramStates.setText("The number of Program States: " + myControler.getRepository().getProgramsList().size());
//    }
//
//    public void setProgramStateList(){
//        idItems.clear();
//        for(Model.ProgramState programState : myControler.getRepository().getProgramsList()) {
//            idItems.add(Integer.toString(programState.getId()));
//        }
//        idListView.setItems(idItems);
//    }
//    
//    public void setOutputList(){
//        outItems.clear();
//        for(Model.value.IValue value : myControler.getRepository().getProgramList().get(0).getOutputList().getList()) {
//            outItems.add(value.toString());
//        }
//        outListView.setItems(outItems);
//    }
//
//    public void setStackAndSymbols(){
//        int index = 0, indexOfCurrentProgram = -1;
//        for(Model.ProgramState ps : myControler.getRepository().getProgramsList()) {
//            if(ps.getId() == Integer.parseInt(idListView.getSelectionModel().getSelectedItem())) {
//                indexOfCurrentProgram = index;
//                break;
//            }
//            index++;
//        }
//        if(indexOfCurrentProgram == -1)
//            indexOfCurrentProgram = 0;
//        setSymbolsTable(indexOfCurrentProgram);
//        setStackList(indexOfCurrentProgram);
//    }
//
//    public void setStackList(int index){
//        stackItems.clear();
//        for(Model.statement.IStatement statement : myControler.getRepository().getProgramsList().get(index).getExecutionStack().getStackAsList()) {
//            stackItems.add(0, statement.toString());
//        }
//        stackListView.setItems(stackItems);
//    }
//
//    public void setFileList(){
//        fileItems.clear();
//        for(Model.value.StringValue fileName : myControler.getRepository().getProgramsList().get(0).getFileTable().getKeys()) {
//            fileItems.add(fileName.toString());
//        }
//        filesListView.setItems(fileItems);
//    }
//
//    public void setHeapTable(){
//        heapItems.clear();
//        addressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
//        valueHeapColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
//        heapItems.addAll(myControler.getRepository().getProgramsList().get(0).getHeap().getContent().entrySet());
//        tableHeap.setItems(heapItems);
//    }
//
//    public void setSymbolsTable(int index){
//        symbolsTableItems.clear();
//        variableNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
//        valueSymbolsTableColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
//        symbolsTableItems.addAll(myControler.getRepository().getProgramsList().get(index).getSymbolsTable.getContent().entrySet());
//        tableSymbolsTable.setItems(symbolsTableItems);
//    }

}
