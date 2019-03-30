package com.siobhan.proxy.dto;

/**
 * Created by siobhan.zheng on 2019/3/28
 */
public class PageDTO {
    private Integer page;
    private Integer size;
    private String searchName;

    public PageDTO(Integer page, Integer size, String searchName) {
        this.page = page;
        this.size = size;
        this.searchName = searchName;
    }

    public String getSearchName() {

        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public PageDTO() {
    }

    public PageDTO(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
