package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    //static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb";
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Europe/Kiev";

    static final String DB_USER = "root";
    static final String DB_PASSWORD = "14041707";

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory(
                DB_CONNECTION, DB_USER, DB_PASSWORD
        );

        Connection conn = factory.getConnection();
        try {
            ClientDAOEx dao = new ClientDAOEx(conn, "clients");
            dao.delAll();

            Client c1 = new Client("Vasya", 11);
            Client c2 = new Client("Petya", 12);
            dao.add(c1);
            dao.add(c2);

            List<Client> list0 = dao.getAll(Client.class);
            listToScreen(list0);

            list0.get(1).setName("New Petya");
            list0.get(1).setAge(123);
            dao.update(list0.get(1), int.class);

            List<Client> list1 = dao.getAll(Client.class);
            listToScreen(list1);

            dao.delete(list1.get(0), int.class);


            List<Client> list2 = dao.getAll(Client.class);
            listToScreen(list2);
        } finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }

    public static <T> void listToScreen(List<T> list) {
        System.out.println("-----");
        for (T t : list)
            System.out.println(t);
    }
}
