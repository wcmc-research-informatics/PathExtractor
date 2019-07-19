#!/bin/bash

java -Xmx1024m -Xms256m -Dlog4j.configuration=file:config/log4j.properties -cp "lib/*:target/*" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/BatchDatabaseCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/SimpleXmiListenerConfig.groovy"  -listenerConfigFile "config/listeners/DatabaseListenerConfig.groovy"