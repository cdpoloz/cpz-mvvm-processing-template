package com.cpz.processingtemplate.logging;

import org.jetbrains.annotations.NotNull;

/**
 * Logging infrastructure utility ({@code logging} package) that provides standardized error
 * messages for application bootstrap and I/O operations.
 * <p>
 * This is a stateless utility and contains no business logic.
 * </p>
 *
 * @author CPZ
 */
public class LogMessage {

    /**
     * Builds a message indicating that a file could not be loaded.
     *
     * @param path path of the file that could not be loaded
     * @return formatted error message
     */
    @NotNull
    public static String fileLoadError(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not load file: ").append(path);
        return sb.toString();
    }

    /**
     * Builds a message indicating that a specific data item could not be retrieved.
     *
     * @param code identifier of the requested data item
     * @param exceptionMessage exception detail or failure description
     * @return formatted error message
     */
    @NotNull
    public static String dataQueryError(String code, String exceptionMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not download the selected data item: ").append(code).append(" - ").append(exceptionMessage);
        return sb.toString();
    }

    /**
     * Builds a message indicating that multiple data items could not be retrieved.
     *
     * @param exceptionMessage exception detail or failure description
     * @return formatted error message
     */
    @NotNull
    public static String dataQueryListError(String exceptionMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not download data items: ").append(exceptionMessage);
        return sb.toString();
    }

    /**
     * Builds a message indicating that a specific data item could not be updated.
     *
     * @param code identifier of the requested data item
     * @param exceptionMessage exception detail or failure description
     * @return formatted error message
     */
    @NotNull
    public static String dataUpdateError(String code, String exceptionMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not update the selected data item: ").append(code).append(" - ").append(exceptionMessage);
        return sb.toString();
    }

    /**
     * Builds a message indicating that setup could not be completed.
     *
     * @param exceptionMessage exception detail or failure description
     * @return formatted error message
     */
    @NotNull
    public static String setupCompletionError(String exceptionMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not complete setup: ").append(exceptionMessage);
        return sb.toString();
    }

    /**
     * Builds a message indicating that a specific image could not be loaded.
     *
     * @param fileName name of the image file that could not be loaded
     * @return formatted error message
     */
    @NotNull
    public static String imageLoadError(String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not load image: '").append(fileName).append("'");
        return sb.toString();
    }
}
