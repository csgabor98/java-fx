module javafx.nb1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires com.fasterxml.jackson.databind;
    opens mink to javafx.fxml;
    exports mink;

    opens mink.models;
    opens mink.controllers;
    opens mink.controllers.database;
    opens mink.controllers.rest;
}