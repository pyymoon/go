package com.rpc;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceClient {
    public static void main(String[] args) {
        System.out.println("client service start-----");
        TTransport tTransport = null;
        try {
            tTransport = new TSocket("localhost",9090,300000);
            TProtocol tProtocol = new TBinaryProtocol(tTransport);
            Hello.Client client = new Hello.Client(tProtocol);
            tTransport.open();
            String result = client.helloString("hello");
            System.out.println(result);
        }catch (TTransportException e){
            e.printStackTrace();
        }catch (TException e){
            e.printStackTrace();
        }finally {
            if (null != tTransport){
                tTransport.close();
            }
        }
    }
}
