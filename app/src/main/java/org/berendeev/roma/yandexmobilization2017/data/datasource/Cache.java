package org.berendeev.roma.yandexmobilization2017.data.datasource;

import org.berendeev.roma.yandexmobilization2017.domain.Repository;

public interface Cache extends Repository {
    void invalidateCache();
}
