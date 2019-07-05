package com.rpc.calculate;

import com.rpc.calculate.tutorial.Calculator;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {
    public static  CalculateHandler calculateHandler;
    public static Calculator.Processor processor;

    public static void main(String[] args) {
        try {
            calculateHandler = new CalculateHandler();
            processor = new Calculator.Processor(calculateHandler);
            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor);
                }
            };
//            Runnable secure = new Runnable() {
//                @Override
//                public void run() {
//                    secure(processor);
//                }
//            };

            new Thread(simple).start();
//            new Thread(secure).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void simple(Calculator.Processor processor){
        try {
            TServerTransport tServerTransport = new TServerSocket(9090);
            TServer tServer = new TSimpleServer(
                    new TServer.Args(tServerTransport).processor(processor));

            System.out.println("starting the simple server");
            tServer.serve();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void secure(Calculator.Processor processor){
//        try {
//            TSSLTransportFactory.TSSLTransportParameters para = new TSSLTransportFactory.TSSLTransportParameters();
//            para.setKeyStore("/users/ouyangle/idea/go/src/test/.keystore",
//                    "thrift",null,null);
//
//            TServerTransport tServerTransport = TSSLTransportFactory.getServerSocket(
//                    9091,0,null,para);
//            TServer tServer = new TSimpleServer(
//                    new TServer.Args(tServerTransport).processor(processor));
//
//            System.out.println("Starting the secure server....");
//            tServer.serve();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
