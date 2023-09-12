FROM openjdk
WORKDIR /app
COPY . /app
RUN ChildGardenApplication.java
CMD ["java", "ChildGardenApplication"]