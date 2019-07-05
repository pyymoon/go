package com.rpc.calculate;

import com.rpc.calculate.shared.SharedStruct;
import com.rpc.calculate.tutorial.Calculator;
import com.rpc.calculate.tutorial.InvalidOperation;
import com.rpc.calculate.tutorial.Work;
import org.apache.thrift.TException;

import java.util.HashMap;

public class CalculateHandler implements Calculator.Iface {
    private HashMap<Integer, SharedStruct> log;

    public CalculateHandler() {
        log = new HashMap<Integer, SharedStruct>();
    }

    @Override
    public void ping() throws TException {
        System.out.println("ping()");
    }

    @Override
    public int add(int num1, int num2){
        System.out.println("add("+num1+","+num2+")");
        return num1 + num2;
    }

    @Override
    public int calculate(int logid, Work w) throws InvalidOperation {
        System.out.println("calculate("+logid+",{"+w.op+","+w.num1+","+w.num2+"})");
        int val =0;
        switch (w.op){
            case ADD:
                val = w.num1 + w.num2;
                break;
            case SUBTRACT:
                val = w.num1 - w.num2;
                break;
            case MULTIPLY:
                val = w.num1 * w.num2;
                break;
            case DIVIDE:
                if(w.num2 == 0){
                    InvalidOperation io = new InvalidOperation();
                    io.whatOp = w.op.getValue();
                    io.why = "Cannot divide by 0";
                    throw io;
                }
                val = w.num1 / w.num2;
                break;
                default:
                    InvalidOperation io = new InvalidOperation();
                    io.whatOp = w.op.getValue();
                    io.why = "unknown operation";
                    throw io;
        }

        SharedStruct sharedStruct = new SharedStruct();
        sharedStruct.key = logid;
        sharedStruct.value = Integer.toString(val);
        log.put(logid,sharedStruct);

        return val;
    }

    @Override
    public SharedStruct getStruct(int key) throws TException {
        System.out.println("getStruct("+key+")");
        return log.get(key);
    }

    @Override
    public void zip() throws TException {
        System.out.println("zip()");
    }
}
