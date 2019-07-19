#!/bin/bash

// java -cp $CLASSPATH concepts.Test $WORKING_DIR/config/db.properties
java -Xmx1024m -Xms256m -Dlog4j.configuration=file:config/log4j.properties -cp "lib/*:target/*" gov.va.vinci.ef.ReaderUpdate "config/db.properties" "config/readers/BatchDatabaseCollectionReaderConfig.groovy" 