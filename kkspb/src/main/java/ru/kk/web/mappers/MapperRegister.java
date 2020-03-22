package ru.kk.web.mappers;

import ma.glasnost.orika.MapperFactory;

/**
 * Маппер-регистратор.
 */
public interface MapperRegister {
    void register(MapperFactory mapperFactory);
}
