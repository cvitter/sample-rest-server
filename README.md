# Sample Rest Server

This Java project contains a basic rest server example (based on the Spark micro framework: [spark](http://sparkjava.com/)) that I am using to run some CI/CD testing with [Jenkins](http://jenkins-ci.com) and CloudBees Enterprise Jenkins [cloudbees](https://www.cloudbees.com/products/cloudbees-jenkins-enterprise).

**Note**: There is nothing to see here. Move along.

## Setup

Install [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) or [OpenJDK](https://openjdk.java.net/install/) and setup $JAVA_HOME environment variable.

Install [Maven](https://maven.apache.org/download.cgi) and setup $MAVEN_HOME environment variable

Clone this repository: `git clone https://github.com/mohsenari/sample-rest-server.git`

Go to directory: `cd ./sample-rest-server/`

Install and run server: `mvn clean install && mvn exec:java -Dexec.mainClass=com.vitter.rest.Main`

Running unit tests: `mvn test`

## License

The content in this repository is Open Source material released under the Apache 2.0 License. Please see the [LICENSE](LICENSE) file for full license details.

## Questions, Feedback, Pull Requests Etc

If you have any questions, feedback, suggestions, etc. please submit them via issues or, even better, submit a Pull Request!
