#!/bin/bash

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=${JBOSS_HOME}/bin/jboss-cli.sh
JBOSS_SCRIPT=${JBOSS_HOME}/customization/add-data-source.cli

function wait_for_server() {
  until `${JBOSS_CLI} -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
${JBOSS_HOME}/bin/standalone.sh &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Adding MySQL data source: " ${MYSQL_URI}
echo MYSQL_URI=${MYSQL_URI} > ${JBOSS_HOME}/env.properties
${JBOSS_CLI} --connect --file=${JBOSS_SCRIPT} --properties=${JBOSS_HOME}/env.properties

echo "=> Shutting down WildFly"
${JBOSS_CLI} -c ":shutdown"

# Fix for configuration rename issue
rm -rf ${JBOSS_HOME}/standalone/configuration/standalone_xml_history