#!/bin/bash

java -Xmx1024m -Xms256m -Dlog4j.configuration=config/log4j.properties -cp "config/*:lib/*:target/*" gov.va.vinci.ef.Service
