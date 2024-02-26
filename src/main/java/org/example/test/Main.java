package org.example.test;

import org.example.utils.MyDataBase;

public class Main {
    public static void main(String[] args) {
        System.out.println(MyDataBase.getInstance());
        System.out.println(MyDataBase.getInstance());
    }
}