FROM openjdk

WORKDIR /app

COPY target/orcamento-1.jar /app/orcamento.jar

ENTRYPOINT ["java", "-jar", "orcamento.jar"]