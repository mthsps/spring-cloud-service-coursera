# Building Cloud Services with the Java Spring Framework

Course from the Vanderbilt University on "how to build cloud services via the use of object-oriented design techniques, Java programming language features, Java Servlets, the Java Spring Framework, and cloud computing platforms, such as Amazon Web Services." 

## What I Have Learned?

I really enjoyed this course, even though it took many hours to fix all the configuration issues (dependencies, Java version, Gradle version, and so on) to actually make the assignments work. It builds every aspect from the ground up, showing how technologies have evolved to make programming tasks easier. For example, at the beginning it shows how we would build a simple RESTApi using just Java Servlets and HTTP, to later show how the Spring framework facilitates this by handling all the repetitive work (eg converting and parsing the data). It details the functions of annotations, such as @Controller, @RequestParam, @PathVariable and @ResponseBody.

The second part of the course is much more theoretical, but no less interesting. It shows how Spring can work together with cloud services (specifically with AWS). In addition, it addresses many issues that go through this, such as scalability and load balancing. I found it very informative to see the different approaches that can be used to work with Stateless or Stateful applications. Finally, it demonstrates how Spring can also be connected to NoSQL databases and how to deal with the problem of possible redundancies that can arise.

### Assignment 1 

>A popular use of cloud services is to manage media that is uploaded from mobile devices. This assignment will create a very basic application for uploading video to a cloud service and managing the video's metadata. Once you are able to build this basic type of infrastructure, you will have the core knowledge needed to create much more sophisticated cloud services.

### Assignment 2 

>This assignment will build on the ideas in the original video service to add OAuth 2.0 authentication of clients and the ability to "like" videos. To complete this assignment, you must allow users to authenticate using the OAuth 2.0 Password Grant flow. The code for the OAuth 2.0 integration is provided for you in the skeleton and tests. Once authenticated, users must be able to like/unlike videos, as well as search for videos by name and duration. In addition, video data must be stored in a Spring Data JPA repository.


## References

[Coursera - Building Cloud Services with the Java Spring Framework](https://www.coursera.org/learn/cloud-services-java-spring-framework/)
