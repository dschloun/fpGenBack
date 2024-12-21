package be.unamur.fpgen.service;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.message.download.ConversationMessageDownload;
import be.unamur.fpgen.message.download.DocumentContent;
import be.unamur.fpgen.message.download.InstantMessageDownload;
import be.unamur.fpgen.repository.DownloadRepository;
import com.opencsv.CSVWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DownloadService {

    private final DatasetService datasetService;
    private final DownloadRepository downloadRepository;

    public static final String TEXT_CSV_MIMETYPE = "text/csv";
    public static final String DOCUMENT_FILE_EXTENSION = ".csv";
    private static final String DATASET_FILENAME_PREFIX = "dataset_";
    public static final DateTimeFormatter DATE_DOWNLOAD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DownloadService(final DatasetService datasetService,
                           final DownloadRepository downloadRepository) {
        this.datasetService = datasetService;
        this.downloadRepository = downloadRepository;
    }

    @Transactional
    public DocumentContent downloadDataset(final UUID datasetId){
        // 1. Get dataset
        final Dataset dataset = datasetService.findById(datasetId);

        // 2. get data
        if (DatasetTypeEnum.INSTANT_MESSAGE.equals(dataset.getType())){
            return downloadInstantMessageDataset(prepareFileName(dataset), downloadRepository.findAllMessagesByDatasetId(datasetId.toString()));
        } else {
            return downloadConversationMessageDataset(prepareFileName(dataset), downloadRepository.findAllConversationsByDatasetId(datasetId.toString()));
        }
    }


    private DocumentContent downloadInstantMessageDataset(final String fileName, final List<InstantMessageDownload> instantMessageDownloadList){
        final byte[] bom = new byte[] { (byte) 239, (byte) 187, (byte) 191 };
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            outputStream.write(bom);
            final PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            final CSVWriter csvWriter = new CSVWriter(writer, ';', '"', '"', "\n");
            csvWriter.writeAll(instantMessageElementsToStringArray(instantMessageDownloadList));
            csvWriter.close();

            final ByteArrayResource contentStream = new ByteArrayResource(outputStream.toByteArray());

            return DocumentContent.newBuilder()
                    .withMimeType(TEXT_CSV_MIMETYPE)
                    .withLength(contentStream.contentLength())
                    .withFileName(fileName + DOCUMENT_FILE_EXTENSION)
                    .withContentStream(contentStream)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Error while writing BOM to output stream", e);
        }
    }

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

    private DocumentContent downloadConversationMessageDataset(final String fileName, final List<ConversationMessageDownload> conversationMessageDownloadList) {
        final byte[] bom = new byte[] { (byte) 239, (byte) 187, (byte) 191 };
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            outputStream.write(bom);
            final PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            final CSVWriter csvWriter = new CSVWriter(writer, ';', '"', '"', "\n");
            csvWriter.writeAll(conversationMessageElementsToStringArray(conversationMessageDownloadList));
            csvWriter.close();

            final ByteArrayResource contentStream = new ByteArrayResource(outputStream.toByteArray());

            return DocumentContent.newBuilder()
                    .withMimeType(TEXT_CSV_MIMETYPE)
                    .withLength(contentStream.contentLength())
                    .withFileName(fileName + DOCUMENT_FILE_EXTENSION)
                    .withContentStream(contentStream)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Error while writing BOM to output stream", e);
        }
    }

    private List<String[]> conversationMessageElementsToStringArray(final List<ConversationMessageDownload> conversationMessages) {
        final List<String[]> records = new ArrayList<>(conversationMessages.size());

        // CSV header
        records.add(new String[]{
                "conversationId",
                "type",
                "content"
        });

        // Group by conversationId
        Map<String, List<ConversationMessageDownload>> groupedMessages = conversationMessages.stream()
                .collect(Collectors.groupingBy(ConversationMessageDownload::getConversationId));

        // Process each conversation
        groupedMessages.forEach((conversationId, messages) -> {
            // Sort messages by orderNumber
            messages.sort(Comparator.comparing(ConversationMessageDownload::getOrderNumber));

            // Concatenate message content
            String concatenatedContent = messages.stream()
                    .map(ConversationMessageDownload::getContent)
                    .collect(Collectors.joining(" | "));

            // Extract type (assume all messages in a group have the same type)
            String type = messages.get(0).getType();

            // Add to records
            records.add(new String[]{
                    conversationId,
                    type,
                    concatenatedContent
            });
        });
            return records;
    }

    private String prepareFileName(final Dataset dataset){
        final String kind = DatasetTypeEnum.INSTANT_MESSAGE.equals(dataset.getType()) ? "IM " : "CM ";
        return kind + dataset.getName();
    }
}
