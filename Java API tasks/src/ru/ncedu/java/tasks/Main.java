package ru.ncedu.java.tasks;

import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        String data = "Alexander;Kharichkin;DSI;07-09-1988;M;1000;9032606540";
        String current;
        StringTokenizer st = new StringTokenizer(data, ";");
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        BusinessCardImpl th = new BusinessCardImpl();
        if (!current.matches("[A-Za-z\\s\\-]+")) throw new InputMismatchException();
        th.FName = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[A-Za-z\\s\\-]+")) throw new InputMismatchException();
        th.LName = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[A-Za-z]+")) throw new InputMismatchException();
        th.Department = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) throw new InputMismatchException();
        th.birthdate = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[FM]{1}")) throw new InputMismatchException();
        th.Gender = current.charAt(0);
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[0-9]+")) throw new InputMismatchException();
        th.Salary = new Integer(current);
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[0-9]{10}")) throw new InputMismatchException();
        th.Phone = current;
        System.out.println(th.Salary);
        System.out.println(th.Phone);
        System.out.print(th.getPhoneNumber());

    }
}
