package com.spenkana.wordsearch.membrane.result;

/**
 * Use this class when you need exception-specific info like stack traces.
 * <p>NOTE: SimpleError will accept an Exception and build an error message
 * containing the exception class and message - this should be sufficient for
 * most cases.</p>
 * <p>It can be a serious security breach to send stack traces and other sensitive
 * information over the wire: extract them and write to a secure log or message handler.</p>
 * @see SimpleError
 */
public class ExceptionalError extends SafeError<Exception>{
    private final Exception exception;

    public ExceptionalError(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String message() {
        return exception.getLocalizedMessage();
    }

    @Override
    public Exception data() {
        return exception;
    }
}
