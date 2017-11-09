FROM jboss/wildfly:10.1.0.Final

################## BEGIN INSTALLATION ######################
# Transfer required files
ADD customization /opt/jboss/wildfly/customization/

# Add management user to wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
##################### INSTALLATION END #####################

# Expose management port
EXPOSE 9990

# Add datasources and run wildfly
CMD /opt/jboss/wildfly/customization/bootstrap.sh