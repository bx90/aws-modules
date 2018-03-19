package util.config;

import util.exception.ServerException;
/**
 * @author bsun
 */
public interface Configurable {
    void configure() throws ServerException;
}
