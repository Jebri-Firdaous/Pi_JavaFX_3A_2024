package tn.pidev.test;

import tn.pidev.utils.Parking.MyDataBase;

public class Main {
    public static void main(String[] args) {
        System.out.println(MyDataBase.getInstance());
        System.out.println(MyDataBase.getInstance());
    }
}