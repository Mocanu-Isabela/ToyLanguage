package View;

import Controller.Controler;
import Exceptions.TypeException;

public class RunExample extends Command{
    private Controler controller;

    public RunExample(String key, String desc, Controler ctr){
        super(key, desc);
        this.controller = ctr;
    }
    @Override
    public void execute() throws Exception {
        try{
            controller.typeCheck();
            controller.allStep();
        }
        catch(Exception e) {
            if (e instanceof TypeException) {
                System.out.println("TypeCheck error : " + e.getMessage());
            }
            else {
                //throw new MyException("Could not execute!");
                System.out.println(e.getMessage());
            }
        }
    }
}
