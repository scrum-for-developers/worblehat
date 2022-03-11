# Worblehat

[![Build Status](https://travis-ci.org/scrum-for-developers/worblehat.svg?branch=master)](https://travis-ci.org/scrum-for-developers/worblehat)

Worblehat is a Java Spring-Boot based Case Study for the [Applying Professional Scrum for Software Development](https://www.scrum.org/classes?uid=55) training
held by [Andreas Ebbert-Karroum](https://www.scrum.org/andreas-ebbert-karroum) from [codecentric AG](https://www.codecentric.de/).

## Requirements
* JDK 11+
* Docker / Podman

Maven comes bundled with the maven wrapper scripts, no need for manual installation before.

## Running the application

1. Compile and install the application in the local maven repository with `./mvnw install`
2. Start the database:
  * The easiest way is to fire up a docker container with `./worblehat-web/docker-db.sh`.
  * **On Apple M1 Chips:** We switched from MySQL to MariaDB because it's a drop in replacement and in version 10.5 compatible with the new ARM Chips. Just execute `./worblehat-web/podman-db-on-m1.sh` (In addition podman is used instead of docker).
3. Run the application.:
  * Either run `./mvnw -pl worblehat-web spring-boot:run` (will automatically compile & package the application before)
  * Or use your IDE to start the main class in worblehat-web: `de.codecentric.psd.Worblehat`
4. Access the application at <http://localhost:8080/worblehat/>

## Running tests

All tests are executed via JUnit, but can be conceptually divided in unit and integration tests. They are bound to different
maven lifecycle phases, are executed by differen maven plugins, and follow a different naming scheme.

### Unit Tests

1. Unit tests are run with `./mvnw test`
1. The [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin) includes
 [all these tests](https://maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html#includes) by default:
 ```
<includes>
    <include>**/Test*.java</include>
    <include>**/*Test.java</include>
    <include>**/*Tests.java</include>
    <include>**/*TestCase.java</include>
</includes>
```

### Acceptance Tests

1. Acceptance tests are run by activating the required profile `./mvnw -P runITs verify`.

   Note: The `verify` lifecycle is executed before `install`. Integration tests are only included, if the `runITs` profile is activated.
1. The [maven-failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin) includes
 [all these tests](https://maven.apache.org/surefire/maven-failsafe-plugin/integration-test-mojo.html#includes) by default:
 ```
<includes>
    <include>**/IT*.java</include>
    <include>**/*IT.java</include>
    <include>**/*ITCase.java</include>
</includes>
```

The acceptance tests spin docker containers for all required dependencies (Database & Browser) via [Testcontainers](https://www.testcontainers.org/).

## IDEs

### Intellij IDEA

In IDEA, you can use the `CodeWithMe` Plugin to work together on the same piece of code, even if you're remote.

You should be able to run the application, as well as the acceptance tests not only via maven, but also from within the IDE.
Right-Click the class `Worblehat` in `worblehat-web` or `AcceptanceTestsIT` in `worblehat-acceptancetests`and run them.

In order to run single acceptance tests, your best option is to use the cucumber plugin in IDEA, with which you can
start single Scenarios or Features from within the editor window.

### VisualStudio Code

With vs code, you can use the LiveShare feature to develop worblehat together. You can even share your port localhost:8080
so that everybody, who participates in the LiveShare session can access the application, even if it's only running on the hosts'
computer.

## Ideas

### Long build times

If your build is taking too long, here are some strategies:
* add a webhook from your git repository to jenkins, in order to trigger a build immedeately
  * Add `http://<teamserver-IP>/jenkins/git/notifyCommit?url=https://github.com/scrum-for-developers/<teamRepo>.git&delay=0` as webhook to your GitHub Repository
* try to reduce the amount of work in feature branches
* try to do steps in parallel
* try to reduce the amount of generated reports (maven-project-info-reports-plugin)

### Hot Reloading

Spring Boot is running in development mode, this means that it restarts the context when new
classes or resources are detected. Saving (and rebuilding) the class or html template should suffice
in order for the browser to pickup the changes on reload. If you use a live reload plugin
(like [LiveReload for Chrome](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei))
your browser will automatically refresh, and you don't even have to reload the page.

### Debugging

It is possible in IDEA and code to start the class `AcceptanceTestsIT` in debug mode, which enables you to
set breakpoints in the step definitions. This might help to understand, why things are not working as expected.

## Howto Release

### Changelog

The source for the generated Changelog can be found at `src/changes/changes.xml`. The XML file's syntax is [documented with the maven changes plugin](https://maven.apache.org/plugins/maven-changes-plugin/changes.html).
### Version Number

The version number should follow [semantic versioning](https://semver.org/):
1. Major version is currently `1` and should only change for breaking changes
1. Minor version should be set to the sprint number
1. Patch version is incremental, if you choose to deploy multiple times within a sprint or need to publish a bugfix release. Start with 0.

To release for example version 1.2.0 follow these steps:

1. Remove Snapshot version: `./mvnw versions:set -DremoveSnapshot`
    * Alternatively, you can use the interactive mode and just call `./mvnw versions:set`
1. Optional: Create a git tag and push the tag back to the team repository
    * `git tag v1.2.0`
    * `git push origin --tags` or `git push origin v1.2.0`
1. Let jenkins build the release and deploy it to the test and production environments
1. Bump the version for the next development iteration: `./mvnw versions:set -DnewVersion=1.3.0-SNAPSHOT`
