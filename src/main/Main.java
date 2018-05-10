package main;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.model.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

public class Main extends Application {

    static String currentTableName = "none";
    final static MySQLAccess dao = new MySQLAccess("compshop", "root", "7456");

    enum Tables {
        clients,
        company,
        components,
        orders,
        paramcomponents,
        parameters
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final StackPane root = new StackPane();
        primaryStage.setTitle("compshop");
        primaryStage.setScene(new Scene(root, 500, 300));

        final VBox vBox = new VBox(5);
        final HBox hBox = new HBox(5);
        final Button addButton = new Button("Add");
        final Button deleteButton = new Button("delete");
        final TableView<Clients> tableViewClient = new TableView<>();
        tableViewClient.setEditable(true);
        final TableView<Components> tableViewCompany = new TableView<>();
        tableViewCompany.setEditable(true);
        final TableView<Company> tableViewComponents = new TableView<>();
        tableViewComponents.setEditable(true);
        final TableView<main.model.Parameters> tableViewOrders = new TableView<>();
        tableViewOrders.setEditable(true);
        final TableView<paramcomponents> tableViewParamcomponents = new TableView<>();
        tableViewParamcomponents.setEditable(true);
        final TableView<Orders> tableViewParameters = new TableView<>();
        tableViewParameters.setEditable(true);

        LinkedList<String> tables = dao.getTables();
        final ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll(tables);
        cb.getSelectionModel().selectFirst();

        cb.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                LinkedList<String> columns = dao.getColumns(cb.getItems().get((Integer) number2).toString());

                tableViewClient.getColumns().clear();
                tableViewCompany.getColumns().clear();
                tableViewComponents.getColumns().clear();
                tableViewOrders.getColumns().clear();
                tableViewParamcomponents.getColumns().clear();
                tableViewParameters.getColumns().clear();
                switch (cb.getItems().get((Integer) number2).toString().toLowerCase()) {
                    case "clients":
                        currentTableName = "clients";
                        ObservableList<Clients> dataClients = dao.getClientData(cb.getItems().get((Integer) number2).toString());
                        createClientsTable(columns, tableViewClient);
                        tableViewClient.setItems(dataClients);
                        vBox.getChildren().add(tableViewClient);
                        vBox.getChildren().removeAll(tableViewCompany, tableViewComponents, tableViewOrders, tableViewParamcomponents, tableViewParameters);
                        break;
                    case "components":
                        currentTableName = "components";
                        ObservableList<Components> dataComponents = dao.getComponentData(cb.getItems().get((Integer) number2).toString());
                        createComponentsTable(columns, tableViewCompany);
                        tableViewCompany.setItems(dataComponents);
                        vBox.getChildren().add(tableViewCompany);
                        vBox.getChildren().removeAll(tableViewClient, tableViewComponents, tableViewOrders, tableViewParamcomponents, tableViewParameters);
                        break;
                    case "company":
                        currentTableName = "company";
                        ObservableList<Company> dataCompanies = dao.getCompanyData(cb.getItems().get((Integer) number2).toString());
                        createCompanyTable(columns, tableViewComponents);
                        tableViewComponents.setItems(dataCompanies);
                        vBox.getChildren().add(tableViewComponents);
                        vBox.getChildren().removeAll(tableViewClient, tableViewCompany, tableViewOrders, tableViewParamcomponents, tableViewParameters);
                        break;
                    case "parameters":
                        currentTableName = "parameters";
                        ObservableList<main.model.Parameters> dataParameters = dao.getParameterData(cb.getItems().get((Integer) number2).toString());
                        createParametersTable(columns, tableViewOrders);
                        tableViewOrders.setItems(dataParameters);
                        vBox.getChildren().add(tableViewOrders);
                        vBox.getChildren().removeAll(tableViewClient, tableViewCompany, tableViewComponents, tableViewParamcomponents, tableViewParameters);
                        break;
                    case "paramcomponents":
                        currentTableName = "paramcomponents";
                        ObservableList<paramcomponents> dataParamcomponents = dao.getParamcomponentData(cb.getItems().get((Integer) number2).toString());
                        createParamcomponentsTable(columns, tableViewParamcomponents);
                        tableViewParamcomponents.setItems(dataParamcomponents);
                        vBox.getChildren().add(tableViewParamcomponents);
                        vBox.getChildren().removeAll(tableViewClient, tableViewCompany, tableViewComponents, tableViewOrders, tableViewParameters);
                        break;
                    case "orders":
                        currentTableName = "orders";
                        ObservableList<Orders> dataOrders = dao.getOrderData(cb.getItems().get((Integer) number2).toString());
                        createOrdersTable(columns, tableViewParameters);
                        tableViewParameters.setItems(dataOrders);
                        vBox.getChildren().add(tableViewParameters);
                        vBox.getChildren().removeAll(tableViewClient, tableViewCompany, tableViewOrders, tableViewParamcomponents, tableViewComponents);
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        addButton.setOnMouseClicked(event -> {
            if (currentTableName.equals("clients")) {
                final StackPane rootAdd = new StackPane();

                Stage secondStage = new Stage();
                secondStage.setTitle("add clients");
                secondStage.setScene(new Scene(rootAdd, 200, 300));

                VBox vBox1 = new VBox(20);

                vBox1.setAlignment(Pos.CENTER);
                TextField nameTextField = new TextField();
                nameTextField.setPadding(new Insets(5));
                nameTextField.setMaxWidth(180);
                nameTextField.setPromptText("clientName");

                TextField ageTextField = new TextField();
                ageTextField.setPadding(new Insets(5));
                ageTextField.setMaxWidth(180);
                ageTextField.setPromptText("age");

                TextField emailTextField = new TextField();
                emailTextField.setPromptText("Email");
                emailTextField.setMaxWidth(180);
                emailTextField.setPadding(new Insets(5));

                Button add = new Button("Add");
                add.setPrefWidth(180);
                add.setFont(new Font(18));
                add.setOnMouseClicked(event1 -> {
                    try {
                        tableViewClient.getColumns().clear();
                        dao.addClientOnPosition(new Clients(0, nameTextField.getText(), Integer.parseInt(ageTextField.getText()), emailTextField.getText()));
                        LinkedList<String> columns = dao.getColumns("clients");
                        ObservableList<Clients> dataClients = dao.getClientData("clients");
                        createClientsTable(columns, tableViewClient);
                        tableViewClient.setItems(dataClients);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Insert operation");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                });

                vBox1.getChildren().addAll(nameTextField, ageTextField, emailTextField, add);
                rootAdd.getChildren().add(vBox1);

                secondStage.showAndWait();
            }
        });

        deleteButton.setOnMouseClicked(event -> {
            if (currentTableName.equals("clients")) {
                tableViewClient.getFocusModel();
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete operation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want delete " + tableViewClient.getFocusModel().getFocusedItem().getClientName());
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get() == null) {
                    } else if (option.get() == ButtonType.OK) {
                        tableViewClient.getColumns().clear();
                        dao.deleteClientOnPosition(tableViewClient.getFocusModel().getFocusedItem().getClientID());
                        LinkedList<String> columns = dao.getColumns("clients");
                        ObservableList<Clients> dataClients = dao.getClientData("clients");
                        createClientsTable(columns, tableViewClient);
                        tableViewClient.setItems(dataClients);
                    } else if (option.get() == ButtonType.CANCEL) {
                    }

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete operation");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });

        hBox.getChildren().addAll(cb, addButton, deleteButton);
        vBox.getChildren().add(hBox);
        root.getChildren().add(vBox);
        primaryStage.show();
        if (cb.getValue() != null)
            dao.getColumns(cb.getValue().toString());
    }

    private static void createClientsTable(LinkedList<String> columns, TableView<Clients> tableView) {
        for (String column : columns) {
            switch (column) {
                case "clientID":
                    TableColumn<Clients, String> tableColumn = new TableColumn<>();
                    tableColumn.setText(column);
                    tableColumn.setCellValueFactory(new PropertyValueFactory<>("clientID"));
                    tableView.getColumns().add(tableColumn);
                    break;
                case "clientName":
                    TableColumn<Clients, String> tableColumnName = new TableColumn<>();
                    tableColumnName.setText(column);
                    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
                    tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
                    tableView.getColumns().add(tableColumnName);

                    tableColumnName.setOnEditCommit(event -> {
                        TablePosition<Clients, String> position = event.getTablePosition();
                        String newValue = event.getNewValue();
                        int row = position.getRow();
                        Clients clients = event.getTableView().getItems().get(row);
                        clients.setClientName(newValue);
                        try {
                            dao.updateClientOnPosition(clients);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText("Updated successfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    });
                    break;
                case "Email":
                    TableColumn<Clients, String> tableColumnEmail = new TableColumn<>();
                    tableColumnEmail.setText(column);
                    tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
                    tableColumnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
                    tableView.getColumns().add(tableColumnEmail);

                    tableColumnEmail.setOnEditCommit(event -> {
                        TablePosition<Clients, String> position = event.getTablePosition();
                        String newValue = event.getNewValue();
                        int row = position.getRow();
                        Clients clients = event.getTableView().getItems().get(row);
                        clients.setEmail(newValue);
                        try {
                            dao.updateClientOnPosition(clients);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText("Updated successfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    });
                    break;
                case "age":
                    TableColumn<Clients, Integer> tableColumnAge = new TableColumn<>();
                    tableColumnAge.setText(column);
                    tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
                    tableColumnAge.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
                        @Override
                        public String toString(Integer object) {
                            return object.toString();
                        }

                        @Override
                        public Integer fromString(String string) {
                            try {
                                return Integer.parseInt(string);
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Update operation");
                                alert.setHeaderText(null);
                                alert.setContentText("NumberFormatException: " + e.getMessage());
                                alert.showAndWait();
                                return Integer.parseInt(string);
                            }

                        }
                    }));
                    tableView.getColumns().add(tableColumnAge);

                    tableColumnAge.setOnEditCommit(event -> {
                        TablePosition<Clients, Integer> position = event.getTablePosition();
                        int newValue = event.getNewValue();
                        int row = position.getRow();
                        Clients clients = event.getTableView().getItems().get(row);
                        clients.setAge(newValue);
                        try {
                            dao.updateClientOnPosition(clients);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText("Updated successfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }

                    });
                    break;
            }
        }

    }

    private static void createComponentsTable(LinkedList<String> columns, TableView<Components> tableView) {
        for (String column : columns) {
            switch (column) {
                case "componentID":
                    TableColumn<Components, String> componentID = new TableColumn<>();
                    componentID.setText(column);
                    componentID.setCellValueFactory(new PropertyValueFactory<>("componentID"));
                    tableView.getColumns().add(componentID);
                    break;
                case "companyID":
                    TableColumn<Components, String> companyID = new TableColumn<>();
                    companyID.setText(column);
                    companyID.setCellValueFactory(new PropertyValueFactory<>("companyID"));
                    tableView.getColumns().add(companyID);
                    break;
                case "componentName":
                    TableColumn<Components, String> componentName = new TableColumn<>();
                    componentName.setText(column);
                    componentName.setCellValueFactory(new PropertyValueFactory<>("componentName"));
                    tableView.getColumns().add(componentName);
                    break;
                case "cost":
                    TableColumn<Components, String> cost = new TableColumn<>();
                    cost.setText(column);
                    cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
                    tableView.getColumns().add(cost);
                    break;
                case "type":
                    TableColumn<Components, String> type = new TableColumn<>();
                    type.setText(column);
                    type.setCellValueFactory(new PropertyValueFactory<>("type"));
                    tableView.getColumns().add(type);
                    break;
            }
        }
    }

    private static void createCompanyTable(LinkedList<String> columns, TableView<Company> tableView) {
        for (String column : columns) {
            switch (column) {
                case "companyID":
                    TableColumn<Company, String> companyID = new TableColumn<>();
                    companyID.setText(column);
                    companyID.setCellValueFactory(new PropertyValueFactory<>("companyID"));
                    tableView.getColumns().add(companyID);
                    break;
                case "companyName":
                    TableColumn<Company, String> companyName = new TableColumn<>();
                    companyName.setText(column);
                    companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
                    tableView.getColumns().add(companyName);
                    break;
            }
        }
    }

    private static void createParametersTable(LinkedList<String> columns, TableView<main.model.Parameters> tableView) {
        for (String column : columns) {
            switch (column) {
                case "paramID":
                    TableColumn<main.model.Parameters, String> paramID = new TableColumn<>();
                    paramID.setText(column);
                    paramID.setCellValueFactory(new PropertyValueFactory<>("paramID"));
                    tableView.getColumns().add(paramID);
                    break;
                case "parameterName":
                    TableColumn<main.model.Parameters, String> parameterName = new TableColumn<>();
                    parameterName.setText(column);
                    parameterName.setCellValueFactory(new PropertyValueFactory<>("parameterName"));
                    tableView.getColumns().add(parameterName);
                    break;
                case "ed":
                    TableColumn<main.model.Parameters, String> ed = new TableColumn<>();
                    ed.setText(column);
                    ed.setCellValueFactory(new PropertyValueFactory<>("ed"));
                    tableView.getColumns().add(ed);
                    break;
            }
        }
    }

    private static void createParamcomponentsTable(LinkedList<String> columns, TableView<paramcomponents> tableView) {
        for (String column : columns) {
            switch (column) {
                case "paramID":
                    TableColumn<paramcomponents, String> paramID = new TableColumn<>();
                    paramID.setText(column);
                    paramID.setCellValueFactory(new PropertyValueFactory<>("paramID"));
                    tableView.getColumns().add(paramID);
                    break;
                case "componentID":
                    TableColumn<paramcomponents, String> componentID = new TableColumn<>();
                    componentID.setText(column);
                    componentID.setCellValueFactory(new PropertyValueFactory<>("componentID"));
                    tableView.getColumns().add(componentID);
                    break;
                case "value":
                    TableColumn<paramcomponents, String> value = new TableColumn<>();
                    value.setText(column);
                    value.setCellValueFactory(new PropertyValueFactory<>("value"));
                    tableView.getColumns().add(value);
                    break;
            }
        }
    }

    private static void createOrdersTable(LinkedList<String> columns, TableView<Orders> tableView) {
        for (String column : columns) {
            switch (column) {
                case "orderID":
                    TableColumn<Orders, String> orderID = new TableColumn<>();
                    orderID.setText(column);
                    orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
                    tableView.getColumns().add(orderID);
                    break;
                case "clientID":
                    TableColumn<Orders, String> clientID = new TableColumn<>();
                    clientID.setText(column);
                    clientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
                    tableView.getColumns().add(clientID);
                    break;
                case "componentID":
                    TableColumn<Orders, String> componentID = new TableColumn<>();
                    componentID.setText(column);
                    componentID.setCellValueFactory(new PropertyValueFactory<>("componentID"));
                    tableView.getColumns().add(componentID);
                    break;
                case "orderDate":
                    TableColumn<Orders, String> illness = new TableColumn<>();
                    illness.setText(column);
                    illness.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
                    tableView.getColumns().add(illness);
                    break;
                case "completeDate":
                    TableColumn<Orders, String> completeDate = new TableColumn<>();
                    completeDate.setText(column);
                    completeDate.setCellValueFactory(new PropertyValueFactory<>("completeDate"));
                    tableView.getColumns().add(completeDate);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}