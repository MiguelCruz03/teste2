package pt.esgts.exam.functional;

/**
 * Works just like the {@link java.util.function.BiConsumer}, but can throw an {@link Exception}
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @author Bruno Jesus
 * @version 1.0
 * @see java.util.function.BiConsumer
 * @see java.util.function.Consumer
 * @see FunctionalInterface
 */
@FunctionalInterface
public interface BiConsumerThrows<T, U> {

    void accept(T t, U u) throws Exception;
}
