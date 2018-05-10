package main;


//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.*;

import java.sql.*;
import java.util.LinkedList;

public class MySQLAccess {
    private String login;
    private String password;
    private String dataBaseName;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQLAccess(String dataBaseName, String login, String password) {
        this.dataBaseName = dataBaseName;
        this.login = login;
        this.password = password;

        connectToDataBase();
    }

    public void connectToDataBase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/"
                            + dataBaseName+ "?user=" + login + "&password="
                            + password + "&verifyServerCertificate=false"+
                            "&useSSL=false"+
                            "&requireSSL=false"+
                            "&useLegacyDatetimeCode=false"+
                            "&amp"+
                            "&serverTimezone=UTC");
            statement = connect.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("У вас не установлен драйвер");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка присоеденения к БД. Возможно, не запушен сервер или нет БД с таким именем");
            e.printStackTrace();
        }
    }

    public LinkedList<String> getTables() throws SQLException {
        LinkedList<String> tables = new LinkedList<>();
        DatabaseMetaData md = connect.getMetaData();
        resultSet = md.getTables(null, null, "%", null);
        while (resultSet != null && resultSet.next()) {
            if (resultSet.getString(3).equals(Main.Tables.clients.name()) ||
                    resultSet.getString(3).equals(Main.Tables.company.name()) ||
                    resultSet.getString(3).equals(Main.Tables.components.name()) ||
                    resultSet.getString(3).equals(Main.Tables.orders.name()) ||
                    resultSet.getString(3).equals(Main.Tables.paramcomponents.name()) ||
                    resultSet.getString(3).equals(Main.Tables.parameters.name()))
            tables.add(resultSet.getString(3));
        }
        return tables;
    }

    public LinkedList<String> getColumns(String tableName) throws SQLException {
        LinkedList<String> columns = new LinkedList<String>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Нумерация колонок начинается с 1
            for (int i = 1; i < columnCount + 1; i++) {
                columns.add(rsmd.getColumnName(i));
            }
            return columns;
        } catch (SQLSyntaxErrorException e) {
            return null;
        }
    }

    public ObservableList<Clients> getClientData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Clients> clients = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            clients.add(new Clients(rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4)));
        }
        return clients;
    }

    public ObservableList<Components> getComponentData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Components> components = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            components.add(new Components(rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getString(5)));
        }
        return components;
    }

    public ObservableList<Company> getCompanyData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Company> companies = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            companies.add(new Company(rs.getInt(1),
                    rs.getString(2)));
        }
        return companies;
    }

    public ObservableList<Parameters> getParameterData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Parameters> parameters = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            parameters.add(new Parameters(rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3)));
        }
        return parameters;
    }

    public ObservableList<paramcomponents> getParamcomponentData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<paramcomponents> paramcomponents = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            paramcomponents.add(new paramcomponents(rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3)));
        }
        return paramcomponents;
    }

    public ObservableList<Orders> getOrderData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Orders> orders = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            orders.add(new Orders(rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3)));
        }
        return orders;
    }

    public void updateClientOnPosition(Clients clients) throws SQLException {
        PreparedStatement prep = connect.prepareStatement("UPDATE Clients SET clientName = ?, age = ?, Email = ? WHERE clientID=?");
        prep.setString(1, clients.getClientName());
        prep.setString(2, clients.getEmail());
        prep.setInt(3, clients.getAge());
        prep.setInt(4, clients.getClientID());
        prep.executeUpdate();
    }

    public void addClientOnPosition(Clients clients) throws SQLException {
        PreparedStatement prep = connect.prepareStatement("INSERT INTO Clients (clientName, age, Email) VALUES (?, ?, ?)");
        prep.setString(1, clients.getClientName());
        prep.setInt(2, clients.getAge());
        prep.setString(3, clients.getEmail());
        prep.executeUpdate();
    }

    public void deleteClientOnPosition(int id) throws SQLException {
        PreparedStatement prep = connect.prepareStatement("DELETE FROM Clients WHERE clientID=?");
        prep.setInt(1, id);
        prep.executeUpdate();
    }
}
