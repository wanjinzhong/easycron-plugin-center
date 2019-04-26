FROM java:8-jre
COPY easycron-plugin-center.jar /var/webapps/easycron-plugin-center.jar
ENV DB_URL localhost:3306
ENV DB_NAME EASY_CRON
ENV DB_USERNAME ???
ENV DB_PWD ???
ENV LOG_HOME /var/logs/easycron-plugin-center
WORKDIR /var/webapps/
CMD java -jar easycron-plugin-center.jar