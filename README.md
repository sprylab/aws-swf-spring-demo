#aws-swf-spring-sample
A simple [AWS Simple Workflow Framework][] demo for Spring projects.

## Install & Run
Check out the source, configure your AWS credentials in  `/src/main/resources/config/appication.yaml` and run

	mvn clean install

This will build the project and execute a test that generates a few greeting messages using SWF (exceptions during shutdown can be ignored).

You should see something like:

```
Start greeting workflow

Hello Tom, how are you?
Hello Mark, how are you?

Workflow done.
```

Note: for demonstration purposes, this sample features a distribution model based on threads only. All workers run in the same VM. 

## Note on aspect weaving
The SWF build tools are making use of AspectJ for weaving a few annotations (eg. `@Asynchronous`). This sample uses Load-Time weaving, so when you start the application, you will need to specify the aspect weaver as a JVM argument (e.g.):
`-javaagent: ~/.m2/repository/org/aspectj/aspectjweaver/1.8.9/aspectjweaver-1.8.9.jar`

Aspect weaving for test execution is already configured.

 [AWS Simple Workflow Framework]: https://aws.amazon.com/de/swf/