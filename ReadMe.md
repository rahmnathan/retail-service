Spring Boot service that aggregates product information from various sources and makes it available via Restful HTTP endpoints. 

The 'docker build' command has been bound to the maven package goal, so you must have Docker installed to build the
project as-is. This can be disabled by commenting out the following portion of the retail-service-standalone/pom.xml.
                
                <executions>
                    <execution>
                        <id>default</id>
                        <goals>
                            <goal>build</goal>
                            <goal>push</goal>
                        </goals>
                    </execution>
                </executions>
                
The docker-compose file will pull a MongoDB image and link it to the retail service image for easy deployment. There is
also a JMeter script in the retail-service-standalone/src/test/jmeter/ directory that allows for easy functional testing
of deployments.