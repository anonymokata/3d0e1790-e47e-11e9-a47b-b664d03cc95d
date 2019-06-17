package com.spenkana.wordsearch.membrane.result;


import static com.spenkana.wordsearch.membrane.result.Result.failureDueTo;
import static com.spenkana.wordsearch.membrane.result.Result.successWith;

/**
 * Wraps a valid HTTP code.
 * By default, any integer between 100 and 599 is accepted because this is
 * the true invariant definition. Therefore "I'm a teapot" is a legit code.
 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">
 * HTTP Status Code Definitions</a>
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/418">
 *     I'm a teapot</a>
 *
 */
public class HttpError extends SafeError<Integer> {
	public final int httpCode;
	public final String msg;


	private HttpError(int i, String msg) {
		httpCode = i;
		this.msg = msg;

	}

	/**
	 * Create an HttpError with the default message for a valid HTTP code.
	 * @param i the code
	 * @return a success Result containing the HttpError, or a failure Result
	 * with the default error message.
	 */
	public static Result<HttpError, SimpleError> httpError(int i) {
		return httpError(i, buildMsg(i));

	}

	/**
	 * Create an HttpError with the provided message for a valid code.
	 * @param i the code
	 * @param msg any string
	 * @return a success Result containing the HttpError, or a failure Result
	 * with the default error message.
	 */
	public static Result<HttpError, SimpleError> httpError(int i, String msg) {
		if (i < 100 || i > 599){
			return failureDueTo("Not an HTTP status code: " + i);
		}
    	return successWith(new HttpError(i, msg));
	}

	@Override
    public String message() {
        return msg;
    }

    @Override
    public Integer data() {
        return httpCode;
    }

	private static String buildMsg(int i) {
		return "HTTP Status Code returned: " + i;
	}
}
