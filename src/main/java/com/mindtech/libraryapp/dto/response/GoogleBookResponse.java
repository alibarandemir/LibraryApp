package com.mindtech.libraryapp.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class GoogleBookResponse {
    private List<GoogleBookItem> items;

    @Data
    public static class GoogleBookItem {
        private VolumeInfo volumeInfo;
    }

    @Data
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String publisher;
        private String publishedDate;
        private List<IndustryIdentifier> industryIdentifiers;
    }

    @Data
    public static class IndustryIdentifier {
        private String type;
        private String identifier;
    }
}
