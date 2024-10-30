package com.dsw.students_grades.podam;


import uk.co.jemos.podam.common.AttributeStrategy;
import java.util.Date;
import java.util.List;
import java.lang.annotation.Annotation;
import java.util.Calendar;

public class CustomDateStrategy implements AttributeStrategy<Date> {
    
    @Override
    public Date getValue(Class<?> attrType, List<Annotation> annotations) {
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JANUARY, 1);
        return cal.getTime();
    }
}
