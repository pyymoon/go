package com.rpc.calculate;

import com.rpc.calculate.shared.SharedStruct;
import com.rpc.calculate.tutorial.Calculator;
import com.rpc.calculate.tutorial.InvalidOperation;
import com.rpc.calculate.tutorial.Operation;
import com.rpc.calculate.tutorial.Work;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {
    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.out.println("please enter 'simple or 'secure'");
//            System.exit(0);
//        }

        try {
            TTransport tTransport;
//            if (args[0].contains("simple")){
            tTransport = new TSocket("localhost",9090);
            tTransport.open();
//            }
//            else{
//                TSSLTransportFactory.TSSLTransportParameters para =
//                        new TSSLTransportFactory.TSSLTransportParameters();
//                para.setTrustStore("/users/ouyangle/idea/go/src/test/.truststore",
//                        "thrift", "SunX509", "JKS");
//                tTransport = TSSLTransportFactory.getClientSocket("localhost",9091,0,para);
//            }

            TProtocol tProtocol = new TBinaryProtocol(tTransport);
            Calculator.Client client = new Calculator.Client(tProtocol);

            perform(client);
            tTransport.close();
        }catch (TException x){
            x.printStackTrace();
        }
    }

    private static void perform(Calculator.Client client) throws TException{
        client.ping();
        System.out.println("ping()");

        int sum = client.add(1,1);
        System.out.println("1+1=" + sum);

        Work work = new Work();
        work.op = Operation.DIVIDE;
        work.num1 = 1;
        work.num2 = 0;
        try {
            int quote = client.calculate(1,work);
            System.out.println("who we can divide by 0");
        }catch (InvalidOperation io){
            System.out.println("Invalid operation:"+io.why);
        }

        work.op = Operation.SUBTRACT;
        work.num1 = 15;
        work.num2 = 10;
        try {
            int diff = client.calculate(1,work);
            System.out.println("15-10="+diff);
        }catch (InvalidOperation io){
            System.out.println("Invalid operation:"+io.why);
        }

        SharedStruct log = client.getStruct(1);
        System.out.println("Check log:"+log.value);
    }
}
