package ramarama;

import java.util.Collections;
import java.util.List;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Represents parsed options for Event Tasks to allow for flexibility of order of parameters.
 */
@Command(name = "event")
public class EventOptions implements Runnable {
    // multi-word description before any /option
    @Parameters(arity = "1..*", paramLabel = "DESC")
    private List<String> descParts;

    // multi-word values until the next /option
    @Option(names = { "/from", "--from" }, required = true, arity = "1..*")
    private List<String> fromParts;

    @Option(names = { "/to", "--to" }, required = true, arity = "1..*")
    private List<String> toParts;

    @Override
    public void run() {
    }

    String desc() {
        return String.join(" ",
        descParts == null
                ? Collections.emptyList()
                : descParts);
    }

    String from() {
        return String.join(" ",
        fromParts == null
                ? Collections.emptyList()
                : fromParts);
    }

    String to() {
        return String.join(" ",
        toParts == null
                ? Collections.emptyList()
                : toParts);
    }
}
