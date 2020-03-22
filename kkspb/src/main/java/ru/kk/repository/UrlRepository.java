package ru.kk.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kk.domain.UrlTable;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<UrlTable, Long> {

    Optional<UrlTable> findByShortUrl(@NotBlank String shortUrl);

    Optional<UrlTable> findByUrl(@NotBlank String url);

}
