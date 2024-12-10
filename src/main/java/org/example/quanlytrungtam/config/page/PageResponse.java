package org.example.quanlytrungtam.config.page;

import lombok.Data;
import org.springframework.data.domain.Slice;

import java.util.List;
@Data

public class PageResponse<T> {
    private List<T> content;
    private boolean hasNext;
    private boolean hasPrevious;
    private int numberOfElements;
    private int totalPages;

    public PageResponse(Slice<T> slice) {
        this.content = slice.getContent();
        this.hasNext = slice.hasNext();
        this.hasPrevious = slice.hasPrevious();
        this.numberOfElements = slice.getNumberOfElements();
    }
}

