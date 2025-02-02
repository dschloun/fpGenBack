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

/**
 * Service class for downloading datasets.
 */
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

    /**
     * Downloads a dataset.
     * @param datasetId the dataset id
     * @return the document content which is the download format
     */
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

    /**
     * Downloads instant message dataset.
     * @param fileName the file name
     * @param instantMessageDownloadList the instant message download list
     */
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

    /**
     * Instant message elements to string array.
     * @param instantMessages the instant messages
     * @return the list of string array
     */
    private List<String[]> instantMessageElementsToStringArray(final List<InstantMessageDownload> instantMessages) {
        final List<String[]> records = new ArrayList<>(instantMessages.size());

        //CSV header
        records.add(new String[]{
                "messageId",
                "topic",
                "content",
                "type",
                "malicious"
        });

        instantMessages.forEach(im -> records.add(new String[]{
                im.getMessageId(),
                im.getTopic(),
                im.getContent(),
                im.getType(),
                im.isMalicious() ? "true" : "false"
        }));

        return records;
    }

    /**
     * Downloads conversation message dataset.
     * @param fileName the file name
     * @param conversationMessageDownloadList the conversation message download list
     */
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

    /**
     * Conversation message elements to string array.
     * @param conversationMessages the conversation messages
     * @return the list of string array
     */
    private List<String[]> conversationMessageElementsToStringArray(final List<ConversationMessageDownload> conversationMessages) {
        final List<String[]> records = new ArrayList<>(conversationMessages.size());

        // CSV header
        records.add(new String[]{
                "conversationId",
                "interlocutor_1_id",
                "interlocutor_2_id",
                "topic",
                "content",
                "type",
                "malicious",
                "malicious_interlocutor_id"
        });

        // Group by conversationId
        Map<String, List<ConversationMessageDownload>> groupedMessages = conversationMessages.stream()
                .collect(Collectors.groupingBy(ConversationMessageDownload::getConversationId));

        // Process each conversation
        groupedMessages.forEach((conversationId, messages) -> {
            // Sort messages by orderNumber
            messages.sort(Comparator.comparing(ConversationMessageDownload::getOrderNumber));

            // id interlocultor (always 2)
            Integer senderId = messages.get(0).getSenderId();
            Integer receiverId = messages.get(0).getReceiverId();

            Integer interlocutor1Id = generateRandomId(1, 100, null);
            Integer interlocutor2Id = generateRandomId(1, 100, interlocutor1Id);

            Map<Integer, Integer> interlocutorMap = new HashMap<>();
            interlocutorMap.put(senderId, interlocutor1Id);
            interlocutorMap.put(receiverId, interlocutor2Id);

            // Extract sender and receiver ids
            StringBuilder content = new StringBuilder();
            for(ConversationMessageDownload message: messages){
                Integer interlocutor = interlocutorMap.get(message.getSenderId());
                content.append(String.format(" (%s) ", interlocutor));
                content.append(message.getContent());
            }

            // Extract type (assume all messages in a group have the same type)
            String type = messages.get(0).getType();
            boolean malicious = messages.get(0).isMalicious();
            String topic = messages.get(0).getTopic();

            // Add to records
            records.add(new String[]{
                    conversationId,
                    interlocutor1Id.toString(),
                    interlocutor2Id.toString(),
                    topic,
                    content.toString(),
                    type,
                    malicious ? "true" : "false",
                    type.equals("GENUINE") ? "none" : interlocutor1Id.toString()
            });
        });
            return records;
    }

    /**
     * Prepare file name.
     * @param dataset the dataset
     * @return the file name
     */
    private String prepareFileName(final Dataset dataset){
        final String kind = DatasetTypeEnum.INSTANT_MESSAGE.equals(dataset.getType()) ? "IM " : "CM ";
        return kind + dataset.getName();
    }

    /**
     * Generate random id.
     * @param min the min
     * @param max the max
     * @param exclude the exclude
     * @return the integer
     */
    private Integer generateRandomId(Integer min, Integer max, Integer exclude) {
        Random random = new Random();
        int result;

        do {
            result = random.nextInt(max - min + 1) + min;
        } while (Objects.nonNull(exclude) && result == exclude);

        return result;
    }
}
