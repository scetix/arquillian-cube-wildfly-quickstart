#!/bin/sh

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

echo "=> Adding MySQL data source: " $MYSQL_URI
${JBOSS_CLI} -c << EOF
batch
# Add MySQL module
module add --name=com.mysql --resources=/opt/jboss/wildfly/customization/mysql-connector-java-5.1.44.jar --dependencies=javax.api,javax.transaction.api

# Add MySQL driver
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

# Add the datasource
data-source add --name=mysqlDS --driver-name=mysql --jndi-name=java:jboss/datasources/TestDS --connection-url=jdbc:mysql://$MYSQL_URI/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull --user-name=root --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true

# Execute the batch
run-batch
EOF

echo "=> Shutting down WildFly"
${JBOSS_CLI} -c ":shutdown"

# Fix for configuration rename issue
rm -rf ${JBOSS_HOME}/standalone/configuration/standalone_xml_history

# Restart wildfly
echo "=> Restarting WildFly"
${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0