package listeners

import gov.va.vinci.ef.listeners.BasicDatabaseListener

int batchSize = 1000

// Microsoft SQL connection
String url = "jdbc:sqlserver://<server_address>:1433;databasename=<database_name>;integratedSecurity=false"
String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
String dbUser = "<user>"
String dbPwd = "<password>"
String dbsName = "<database_name>"
String tableName = "<table_name>"

// MySQL connection
// String url = "jdbc:mysql://localhost:3306/<database_name>?autoReconnect=true&useSSL=false"
// String driver = "com.mysql.jdbc.Driver"
// String dbUser = "<user>"
// String dbPwd = "<password>"
// String dbsName = ""<database_name>"
// String tableName = "<table_name>"


incomingTypes = "gov.va.vinci.ef.types.Relation"

fieldList = [
        ["DocId", "0", "varchar(500)"],
        ["Term", "-1", "varchar(5000)"],
        ["Value", "-1", "varchar(5000)"],
        ["Value2", "-1", "varchar(5000)"],
        ["ICD9", "-1", "varchar(500)"],
        ["ICD10", "-1", "varchar(500)"],
        ["TStage", "-1", "varchar(500)"],
        ["NStage", "-1", "varchar(500)"],
        ["MStage", "-1", "varchar(500)"],
        ["GleasonScore", "-1", "varchar(500)"],
        ["AnatomicSite", "-1", "varchar(5000)"],
        ["HistologicType", "-1", "varchar(5000)"],
        ["HistologicGrade", "-1", "varchar(5000)"],
        ["MarginStatus", "-1", "varchar(5000)"],
        ["ValueString", "-1", "varchar(5000)"],
        ["InstanceID", "-1", "int"],
        ["Snippets", "-1", "varchar(8000)"],
        ["SpanStart", "-1", "int"],
        ["SpanEnd", "-1", "int"],
        ["RunDate", "-1", "date"]
]

boolean dropExisting = false;
listener = BasicDatabaseListener.createNewListener(
        driver,
        url,
        dbUser,
        dbPwd,
        dbsName,
        tableName,
        batchSize,
        fieldList,
        incomingTypes)

// Comment out the statement below if you want to add to the existing table
listener.createTable(dropExisting);
