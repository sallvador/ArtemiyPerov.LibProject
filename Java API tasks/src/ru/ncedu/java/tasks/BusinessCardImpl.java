package ru.ncedu.java.tasks;

import java.util.*;

/**
 * Created by demon on 21.10.2016.
 */
public class BusinessCardImpl implements BusinessCard {

    /**
     * This method obtains (via Scanner) information from an input stream
     * that contains the following information about an Employee:<br/>
     * Name - String<br/>
     * Last name - String<br/>
     * Department - String <br/>
     * Birth date - String in format: "DD-MM-YYYY", where DD - two-digits birth date,
     * MM - two-digits month of birth, YYYY - year of birth<br/>
     * Gender : F or M - Character<br/>
     * Salary : number from 100 to 100000<br/>
     * Phone number : 10-digits number<br/>
     * All entries are separated with ";" sign<br/>
     * The format of input is the following:<br/>
     * Name;Last name;Department;Birth date;Gender;Salary;Phone number
     *
     * @param scanner Data source
     * @return Business Card
     * @throws InputMismatchException Thrown if input value
     *                                does not correspond to the data format given above (for example,
     *                                if phone number is like "AAA", or date format is incorrect, or salary is too high)
     * @throws NoSuchElementException Thrown if input stream hasn't enough values
     *                                to construct a BusinessCard
     */

    public String FName;
    public String LName;
    public String Department;
    public String birthdate;
    public char Gender;
    public int Salary;
    public String Phone;

    public BusinessCard getBusinessCard(Scanner scanner) {
        String data = scanner.nextLine();
        String current;
        StringTokenizer st = new StringTokenizer(data, ";");
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[A-Za-z\\s\\-]+")) throw new InputMismatchException();
        this.FName = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[A-Za-z\\s\\-]+")) throw new InputMismatchException();
        this.LName = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[A-Za-z]+")) throw new InputMismatchException();
        this.Department = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) throw new InputMismatchException();
        this.birthdate = current;
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[FM]{1}")) throw new InputMismatchException();
        this.Gender = current.charAt(0);
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[0-9]+")) throw new InputMismatchException();
        this.Salary = new Integer(current);
        if (!st.hasMoreTokens()) throw new NoSuchElementException();
        current = st.nextToken();
        if (!current.matches("[0-9]{10}")) throw new InputMismatchException();
        this.Phone = current;
        return this;
    }

    /**
     * @return Employee Name and Last name separated by space (" "), like "Chuck Norris"
     */
    @Override
    public String getEmployee() {
        return this.FName + " " + this.LName;
    }

    /**
     * @return Employee Department name, like "DSI"
     */
    @Override
    public String getDepartment()
    {
        return this.Department;
    }

    /**
     * @return Employee Salary, like 1000
     */
    @Override
    public int getSalary() {
        return this.Salary;
    }

    /**
     * @return Employee Age in years, like 69
     */
    @Override
    public int getAge() {
        int tyear = (this.birthdate.charAt(6) - '0') * 1000 + (this.birthdate.charAt(7) - '0') * 100 +
                (this.birthdate.charAt(8) - '0') * 10 + (this.birthdate.charAt(9) - '0');
        int tmonth = (this.birthdate.charAt(3) - '0') * 10 + (this.birthdate.charAt(4) - '0');
        int tday = (this.birthdate.charAt(0) - '0') * 10 + (this.birthdate.charAt(1) - '0');
        GregorianCalendar bdate = new GregorianCalendar(tyear, tmonth, tday);
        Date cdate = new java.util.Date();
        int cyear = cdate.getYear() + 1900;
        int cmonth = cdate.getMonth() + 1;
        int cday = cdate.getDate();
        GregorianCalendar cdateG = new GregorianCalendar(cyear, cmonth, cday);
        int years = calcYears(bdate, cdateG);
        return years;
    }

    /**
     * @return Employee Gender: either "Female" or "Male"
     */
    @Override
    public String getGender() {
        if (this.Gender == 'F')
                return "Female";
        else return "Male";
    }

    /**
     * @return Employee Phone Number in the following format: "+7 123-456-78-90"
     */
    @Override
    public String getPhoneNumber() {
        String supp = this.Phone;
        String inf = "+7 " + (Phone.charAt(0) - '0') + (Phone.charAt(1) - '0') + (Phone.charAt(2) - '0')
                + '-' + (Phone.charAt(3) - '0') + (Phone.charAt(4) - '0') + (Phone.charAt(5) - '0') + '-' +
                + (Phone.charAt(6) - '0') + (Phone.charAt(7) - '0') + '-' + (Phone.charAt(8) - '0') + (Phone.charAt(9) - '0');
        return inf;
    }

    private static int calcYears(GregorianCalendar birthDay, GregorianCalendar checkDay) {
        int years = checkDay.get(GregorianCalendar.YEAR) - birthDay.get(GregorianCalendar.YEAR);
        // корректируем, если текущий месяц в дате проверки меньше месяца даты рождения
        int checkMonth = checkDay.get(GregorianCalendar.MONTH);
        int birthMonth = birthDay.get(GregorianCalendar.MONTH);
        if ( checkMonth < birthMonth ) {
            years --;
        } else  if (checkMonth == birthMonth
                && checkDay.get(GregorianCalendar.DAY_OF_MONTH) < birthDay.get(GregorianCalendar.DAY_OF_MONTH)) {
            // отдельный случай - в случае равенства месяцев,
            // но меньшего дня месяца в дате проверки - корректируем
            years --;
        }
        return years;
    }
}
