package com.codeWithAshith.SpringBatch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

public class StateLessItemReader implements ItemReader<String> {

    private final Iterator<String> data;

    public StateLessItemReader(List<String> data) {
        this.data = data.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data.hasNext()) {
            return data.next();
        } else
            // return "Never Ending"
            return null;
    }
}
