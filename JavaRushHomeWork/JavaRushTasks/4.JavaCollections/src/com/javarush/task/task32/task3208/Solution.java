package com.javarush.task.task32.task3208;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
RMI-2
*/
public class Solution {
    public static Registry registry;
    public static final String CAT_BINDING_NAME = "server.cat";
    public static final String DOG_BINDING_NAME = "server.dog";

    //pretend we start rmi client as CLIENT_THREAD thread
    public static Thread CLIENT_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                for (String bindingName : registry.list()) {
                    Animal service = (Animal) registry.lookup(bindingName);
                    service.printName();
                    service.say();
                }
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    });

    //pretend we start rmi server as SERVER_THREAD thread
    public static Thread SERVER_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                final Cat cat = new Cat("Nastya");
                final Dog dog = new Dog("Ilya");
                registry = LocateRegistry.createRegistry(2099);
                Remote catRemote = UnicastRemoteObject.exportObject(cat, 2099);
                Remote dogRemote = UnicastRemoteObject.exportObject(dog, 2099);
                registry.bind(CAT_BINDING_NAME, catRemote);
                registry.bind(DOG_BINDING_NAME, dogRemote);
            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace();
            }
            //напишите тут ваш код
        }
    });

    public static void main(String[] args) throws InterruptedException {
        //start rmi server thread
        SERVER_THREAD.start();
        Thread.sleep(1000);
        //start client
        CLIENT_THREAD.start();
    }
}