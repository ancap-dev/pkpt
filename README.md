# pkpt
Simple messaging system based on Java Sockets

## Maven


```
<repositories>
    <repository>
        <id>pkpt-mvn-repo</id>
        <url>https://raw.github.com/ancap-dev/pkpt/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>

<dependency>
     <groupId>ru.ancap</groupId>
     <artifactId>pkpt</artifactId>
     <version>1.0</version>
     <scope>provided</scope>
</dependency>

```

## Usage

```
public class Example {

    @SneakyThrows
    public void server() {
        new Server(
                new ServerSocket(1051),
                "secret-word",
                (author, message) -> {
                    if (message.getCommand().nextArgument("How are you?")) {
                        author.say("Fine!");
                    }
                }
        ).run();
    }

    @SneakyThrows
    public void client() {
        new Client(
                new Socket(
                        "localhost",
                        1051
                ),
                "secret-word"
        ).ask(
                "How are you?",
                reply -> {
                    if (reply.nextArgument("Fine!")) {
                        System.out.println("Host is fine!");
                    }
                    if (reply.nextArgument("Things are bad")) {
                        System.out.println("Something happened with host...");
                    }
                }
        );
    }

}

```
