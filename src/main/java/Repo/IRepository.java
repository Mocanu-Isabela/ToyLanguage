package Repo;
import Exceptions.MyException;
import Exceptions.RepositoryException;
import Model.ProgramState;

import java.util.List;

public interface IRepository {
    void addProgram(ProgramState newProgram);
//    ProgramState getCurrentProgram() throws RepositoryException;
    void logProgramStateExec(ProgramState prgState) throws RepositoryException, MyException;

    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programStateList);

    ProgramState getProgramWithId(int id);
}
