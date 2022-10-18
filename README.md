# CodeSV

### End of Life Feature as of February 1, 2023
_*Effective February 1, 2023, we will deprecate the CodeSV. We will continue to support the CodeSV until Jan 31st, 2023.*_


CA CodeSV is a new, lightweight way to define virtual services and save valuable time. 

At the core, CodeSV delivers a simple yet powerful Java library that provides an easy to use API so you can create and run virtual services as a part of jUnit testing. 

You can create and run HTTP virtual service definitions directly in your unit testing code.

Even better, the HTTP virtual services are completely transparent to an application under test. This means you don't have to make any other configuration tweaks.

[How to use CodeSV](https://github.com/CA-DevTest/CodeSV/wiki/Quick-Start-Guide)

[Share ideas and raise issues](https://communities.ca.com/community/ca-devtest-community/content?filterID=contentstatus%5Bpublished%5D~category%5Bsv-as-code%5D)


## Code Example
Here is a simple example of a jUnit test running an embedded virtual service using CodeSV.

```java
import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.*;
import static org.junit.Assert.*;

import com.ca.codesv.engine.junit4.VirtualServerRule;
import org.junit.*;

public class ExampleTest {

  @Rule
  public VirtualServerRule vs = new VirtualServerRule();

  @Test
  public void exampleTest() {
    // virtual service definition
    forGet("http://www.example.com/time").doReturn(
        okMessage()
            .withJsonBody("{\"timestamp\":1498838896}")
    );

    // application connects to http://www.example.com/test and retrieves JSON response
    int currentTimestamp = Application.retrieveCurrentTimestamp();

    // received timestamp check
    assertEquals(1498838896, currentTimestamp);
  }
}
```
