package com.stephen.myblog;

import java.io.*;

import static java.lang.Thread.sleep;

public class test{
    public static void main(String args[]) throws Exception{

        UnsafeClass Unsafe = new UnsafeClass();
        Unsafe.name = "hacked by ph0rse";

        FileOutputStream fos = new FileOutputStream("object");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        //writeObject()方法将Unsafe对象写入object文件
        os.writeObject(Unsafe);
        os.close();
        //从文件中反序列化obj对象
        FileInputStream fis = new FileInputStream("object");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //恢复对象
        UnsafeClass objectFromDisk = (UnsafeClass)ois.readObject();
        System.out.println(objectFromDisk.name);
        ois.close();
    }
}
