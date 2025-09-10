package ramarama;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;

@Command(name = "event")
public class EventOptions implements Runnable {
    // multi-word description before any /option
    @Parameters(arity = "1..*", paramLabel = "DESC")
    List<String> descParts;

    // multi-word values until the next /option
    @Option(names = {"/from", "--from"}, required = true, arity = "1..*")
    List<String> fromParts;

    @Option(names = {"/to", "--to"}, required = true, arity = "1..*")
    List<String> toParts;

    @Override public void run() {}

    String desc() { return String.join(" ", descParts == null ? List.of() : descParts); }
    String from() { return String.join(" ", fromParts == null ? List.of() : fromParts); }
    String to()   { return String.join(" ", toParts   == null ? List.of() : toParts); }
}
