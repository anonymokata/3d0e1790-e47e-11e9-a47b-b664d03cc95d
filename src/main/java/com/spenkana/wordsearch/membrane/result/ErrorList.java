package com.spenkana.wordsearch.membrane.result;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mutable list of Error objects.
 * Any subclass instances of SafeError are accepted - does not require
 * uniformity.
 * @see SafeError
 */
public class ErrorList extends SafeError<List<SafeError>> {
    private final List<SafeError> errors = new ArrayList<SafeError>();

    public ErrorList(
        List<SafeError> errors) {
        this.errors.addAll(errors);
    }

    public void add(SafeError error){
       errors.add(error);
    }

    public String message() {
        return String.join(",",
            errors.stream().map(e->message()).collect(toList()));
    }

    public List<SafeError> data() {
        return errors;
    }

    @Override
    public int errorCount() {
        return errors.size();
    }
}
