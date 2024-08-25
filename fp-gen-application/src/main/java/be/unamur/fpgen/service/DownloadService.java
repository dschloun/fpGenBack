package be.unamur.fpgen.service;

import be.unamur.fpgen.message.download.InstantMessageDownload;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    public static final String TEXT_CSV_MIMETYPE = "text/csv";
    public static final String DOCUMENT_FILE_EXTENSION = ".csv";
    private static final String DATASET_FILENAME_PREFIX = "dataset_";
    public static final DateTimeFormatter DATE_DOWNLOAD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");



    private List<String[]> instantMessageElementsToStringArray(final List<InstantMessageDownload> instantMessages) {
        final List<String[]> records = new ArrayList<>(instantMessages.size());

        //CSV header
        records.add(new String[]{
                "type",
                "content"
        });

        instantMessages.forEach(im -> records.add(new String[]{
                im.getType(),
                im.getContent()
        }));

        return records;
    }
}
