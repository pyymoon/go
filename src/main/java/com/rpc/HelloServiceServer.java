package com.rpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceServer {



    public static void main(String[] args) {
        try {
            System.out.println("start service server-----");
            TProcessor tProcessor = new Hello.Processor<Hello.Iface>(new HelloServiceImpl());
            TServerSocket tServerSocket = new TServerSocket(9090);
            TServer.Args tArgs = new TServer.Args(tServerSocket);
            TServer tServer = new TSimpleServer(tArgs.processor(tProcessor));
            tServer.serve();
        }catch (TTransportException e){
            e.printStackTrace();
        }
    }
}
