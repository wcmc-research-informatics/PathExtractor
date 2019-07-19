package readers

import gov.va.vinci.leo.cr.BatchDatabaseCollectionReader;

// MySQL connection
// String driver = "com.mysql.jdbc.Driver"
// String url = "jdbc:mysql://localhost/<DB_NAME>"
// String username="<DB_USER>";
// String password="<DB_PASSWORD>";
// String query = "<SQL_QUERY>"

// Microsoft SQL connection
String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
String url = "jdbc:sqlserver://<SERVER_ADDRESS>:1433;databasename=<DB_NAME>;integratedSecurity=false"
String username="<DB_USER>";
String password = "<DB_PASSWORD>"
String query = "<SQL_QUERY>"

int startingIndex = 0;
int endingIndex = 10;
int batch_size = 10000;

reader = new BatchDatabaseCollectionReader(
        driver,
        url,
        username,
        password,
        query,
        "id","text",   /// Make sure that the names of the fields are low case.
        startingIndex , endingIndex
        , batch_size)
