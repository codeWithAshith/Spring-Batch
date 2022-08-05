package com.codeWithAshith.SpringBatch.reader;

import org.springframework.batch.item.*;

import java.util.List;

public class StatefulItemReader implements ItemStreamReader<String> {
    private final List<String> items;
    private int curIndex = -1;
    private boolean restart = false;

    public StatefulItemReader(List<String> items) {
        this.items = items;
        this.curIndex = 0;
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String item = null;
        if (curIndex < items.size()) {
            item = items.get(curIndex);
            curIndex++;
        }

        if (curIndex == 42 && !restart) {
            throw new RuntimeException("Force Restart");
        }

        return item;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("curIndex")) {
            curIndex = executionContext.getInt("curIndex");
            restart = true;
        } else {
            curIndex = 0;
            executionContext.putInt("curIndex", curIndex);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt("curIndex", curIndex);
    }

    @Override
    public void close() throws ItemStreamException {

    }
}
