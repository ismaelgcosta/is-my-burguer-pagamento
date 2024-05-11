FROM maven:3.9.5-eclipse-temurin-21-alpine as build
#
WORKDIR /is-my-burguer-pagamento
#
COPY ./ ./
RUN mvn clean
RUN mvn install

FROM eclipse-temurin:21-jdk-alpine as main
EXPOSE 8743
EXPOSE 5010

COPY --from=build /is-my-burguer-pagamento/api-main-build/src/main/resources/springboot.crt springboot.crt
RUN keytool -importcert -file springboot.crt -alias springboot -keystore $JDK_HOME/jre/lib/security/cacerts
COPY --from=build /is-my-burguer-pagamento/api-main-build/target/is-my-burguer-pagamento.jar is-my-burguer-pagamento.jar

ENTRYPOINT ["java","-jar","is-my-burguer-pagamento.jar","--spring.profiles.active=production","-Dserver.port=8743"]
#CMD ["sleep","infinity"] Only for testing