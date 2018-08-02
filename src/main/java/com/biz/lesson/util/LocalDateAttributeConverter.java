package com.biz.lesson.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.sql.Date;

/**
 * @author kevin zhao
 * @date 2018/7/26
 * 解决 java8 LocalDate 和 LocalDateTime 映射为 BLOB 的问题。
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
}
