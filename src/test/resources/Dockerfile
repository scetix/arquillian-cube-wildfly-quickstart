FROM jboss/wildfly:8.2.1.Final

################## BEGIN INSTALLATION ######################
# Transfer required files and bootstrap
ADD customization /opt/jboss/wildfly/customization/
RUN /opt/jboss/wildfly/customization/bootstrap.sh

# Add management user to wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
##################### INSTALLATION END #####################

# Expose management port
EXPOSE 9990

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]