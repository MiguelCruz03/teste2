package pt.esgts.exam.service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link AccountIdGenerator} that generates account ids randomly
 */
public class RandomAccountIdGenerator implements AccountIdGenerator {
    @Override
    public Integer get() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
}
