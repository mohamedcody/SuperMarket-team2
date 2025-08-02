package com.team2.supermarket.dto;
import java.util.Collection;
public class PageResult {
    private Collection data;
    private Long total;

    public PageResult(Collection data, Long total) {
        this.data = data;
        this.total = total;
    }

    public Collection getData() {
        return data;
    }

    public void setData(Collection data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
