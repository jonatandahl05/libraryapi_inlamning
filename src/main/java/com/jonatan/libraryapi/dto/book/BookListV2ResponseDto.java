package com.jonatan.libraryapi.dto.book;

import java.util.List;

public class BookListV2ResponseDto {

    private List<BookV2ResponseDto> data;
    private String version;

    public BookListV2ResponseDto() {
    }

    public BookListV2ResponseDto(List<BookV2ResponseDto> data, String version) {
        this.data = data;
        this.version = version;
    }

    public List<BookV2ResponseDto> getData() {
        return data;
    }

    public void setData(List<BookV2ResponseDto> data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    
    
}
