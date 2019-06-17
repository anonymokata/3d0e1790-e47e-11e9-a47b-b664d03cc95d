package com.spenkana.wordsearch.membrane.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * A monad to be used as a return type for functions and methods. It allows
 * for clean and informative failures and facilitates exception-free coding.
 *
 * <p>Generic type T is the type of the object to be returned: the output of the
 * function(/method). If nothing is to be returned (i.e., the function is
 * impure,
 * its only purpose being to produce a side effect), use "Void".
 * <p>Generic type E is the type of the error (if any), which must extend
 * SafeError.
 * <p>It would be nice to eliminate nulls, but in one case null will be
 * returned:
 * when output is requested from a successful Result with an output type of
 * Void. (An earlier version threw exceptions for invalid requests such as
 * the error object for a successful message. Nulls are better.)
 * <p>Unexpected (runtime) exceptions can be caught by enclosing all
 * untrusted calls in try/catch blocks. Essentially,
 * any impure function is untrusted. Any function that always returns a
 * Result instance is a total function.
 * But a signature that specifies a Result return type and no checked
 * exceptions does not guarantee totality. Only a try
 * block that encloses the entire function body along with a catch(Throwable)
 * could provide that guarantee. There are
 * more than a few people who will argue that you should never catch a
 * Throwable in application code. The classic example is an
 * out-of-memory error. Don't worry - even if you catch it and try to wrap it
 * in a Result, it will be thrown again.
 * </p>
 * <p>The advantage of catching all exceptions and wrapping them in Results
 * is the possibility of enrichment at each
 * level of the call hierarchy with locally unique information.
 * </p>
 * <p>
 *
 * @see SafeError
 * @see <a href="https://en.wikipedia.org/wiki/Monad_(functional_programming)">
 * Monad (functional programming)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Pure_function">Pure function</a>
 * @see
 * <a href="https://en.wikipedia.org/wiki/Partial_function">Partial function</a>
 * @see <a href="http://www.lighterra.com/papers/exceptionsharmful/">Exception
 * Handling Considered Harmful
 * </a>
 */
public class Result<T, E extends SafeError> implements Serializable {
	public final boolean succeeded;
	public final boolean failed;
	public final T output;
	public final E error;

	/**
	 * For serialization only.
	 */
	public Result() {
		succeeded = false;
		failed = true;
		output = null;
		error = (E) SimpleError.NOT_AN_ERROR;
	}

	/**
	 * For failure
	 *
	 * @param error
	 */
	private Result(E error) {
		this.error = error;
		succeeded = false;
		failed = true;
		output = null;
	}

	/**
	 * For success
	 *
	 * @param output
	 */
	private Result(T output) {
		this.output = output;
		succeeded = true;
		failed = false;
		error = (E) SimpleError.NOT_AN_ERROR;
	}

	/**
	 * For success of a pure partial function.
	 * A total function can never
	 * fail: it returns a value of the specified type for every possible input.
	 *
	 * @param output value of type T
	 * @param <T>    input type
	 * @param <U>    error type
	 * @return a success Result with the specified output
	 */
	public static <T, U extends SafeError> Result<T, U> successWith(T output) {
		return new Result<>(output);
	}

	/**
	 * For success of an impure function whose purpose is to cause a "side
	 * effect".
	 *
	 * @param <Void> specifies void return
	 * @param <E>    error type
	 * @return void
	 */
	public static <Void, E extends SafeError> Result<Void, E> success() {
		return new Result<>();
	}

	public static <T, E extends SafeError> Result<T, E> failureDueTo(E error) {
		return new Result(error);
	}

	public static <T> Result<T, SimpleError> failureDueTo(String msg) {
		return new Result(new SimpleError(msg));
	}

	public static <T> Result<T, ExceptionalError> failureDueTo(Exception e) {
		return new Result(new ExceptionalError(e));
	}

	public T getOutput() {
		return output;
	}

	public String getErrorMessage() {
		return error.message();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Result<?, ?> result = (Result<?, ?>) o;
		return succeeded == result.succeeded &&
				Objects.equals(output, result.output) &&
				Objects.equals(error, result.error);
	}

	@Override
	public int hashCode() {

		return Objects.hash(succeeded, output, error);
	}
}

