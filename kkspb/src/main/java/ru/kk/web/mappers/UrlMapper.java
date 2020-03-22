package ru.kk.web.mappers;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;
import ru.kk.domain.UrlTable;
import ru.kk.web.dto.request.ShortUrlRequestDto;
import ru.kk.web.dto.request.UrlRequestDto;
import ru.kk.web.dto.response.UrlResponseDto;

@Component
public class UrlMapper extends AbstractMapperRegister {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(UrlTable.class, UrlResponseDto.class)
                .field("shortUrl", "url")
                .byDefault()
                .register();

        mapperFactory.getConverterFactory().registerConverter(new CustomConverter<String, UrlResponseDto>() {
            @Override
            public UrlResponseDto convert(
                    String url, Type<? extends UrlResponseDto> type, MappingContext mappingContext) {

                return new UrlResponseDto(url);
            }
        });

        mapperFactory.getConverterFactory().registerConverter(new CustomConverter<UrlRequestDto, String>() {
            @Override
            public String convert(
                    UrlRequestDto requestDto, Type<? extends String> type, MappingContext mappingContext) {

                return requestDto.getUrl();
            }
        });

        mapperFactory.getConverterFactory().registerConverter(new CustomConverter<ShortUrlRequestDto, String>() {
            @Override
            public String convert(
                    ShortUrlRequestDto shortUrlRequestDto, Type<? extends String> type, MappingContext mappingContext) {

                return shortUrlRequestDto.getUrl();
            }
        });
    }

}
