package com.github.mxsm.store;

import com.github.mxsm.store.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since 1.0.0
 */
public class MappedFileQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappedFile.class);

    private final String storePath;

    private final int mappedFileSize;

    private final CopyOnWriteArrayList<MappedFile> mappedFiles = new CopyOnWriteArrayList<MappedFile>();

    private long flushedWhere = 0;

    private long committedWhere = 0;

    private volatile long storeTimestamp = 0;

    private AllocateMappedFileService allocateMappedFileService;

    public MappedFileQueue(final String storePath, int mappedFileSize,
        AllocateMappedFileService allocateMappedFileService) {
        this.storePath = storePath;
        this.mappedFileSize = mappedFileSize;
        this.allocateMappedFileService = allocateMappedFileService;
    }

    public MappedFile getLastMappedFile(final long startOffset, boolean needCreate) {
        long createOffset = -1;
        MappedFile mappedFileLast = getLastMappedFile();

        if (mappedFileLast == null) {
            createOffset = startOffset - (startOffset % this.mappedFileSize);
        }

        if (mappedFileLast != null && mappedFileLast.isFull()) {
            createOffset = mappedFileLast.getFileFromOffset() + this.mappedFileSize;
        }

        if (createOffset != -1 && needCreate) {
            String nextFilePath = this.storePath + File.separator + FileUtil.offset2FileName(createOffset);
            String nextNextFilePath = this.storePath + File.separator
                + FileUtil.offset2FileName(createOffset + this.mappedFileSize);
            MappedFile mappedFile = null;
            if (this.allocateMappedFileService != null) {
                mappedFile = this.allocateMappedFileService.putRequestAndReturnMappedFile(nextFilePath,
                    nextNextFilePath, this.mappedFileSize);
            } else {
                try {
                    mappedFile = new MappedFile(nextFilePath, this.mappedFileSize);
                } catch (IOException e) {
                    LOGGER.error("create mappedFile exception", e);
                }
            }
            if (mappedFile != null) {
                this.mappedFiles.add(mappedFile);
            }
            return mappedFile;
        }

        return mappedFileLast;
    }

    public MappedFile getLastMappedFile(final long startOffset) {
        return getLastMappedFile(startOffset, true);
    }

    public MappedFile getLastMappedFile() {
        MappedFile mappedFileLast = null;

        while (!this.mappedFiles.isEmpty()) {
            try {
                mappedFileLast = this.mappedFiles.get(this.mappedFiles.size() - 1);
                break;
            } catch (IndexOutOfBoundsException e) {
                //continue;
            } catch (Exception e) {
                LOGGER.error("getLastMappedFile has exception.", e);
                break;
            }
        }
        return mappedFileLast;
    }

    public MappedFile getFirstMappedFile() {
        MappedFile mappedFileFirst = null;

        if (!this.mappedFiles.isEmpty()) {
            try {
                mappedFileFirst = this.mappedFiles.get(0);
            } catch (IndexOutOfBoundsException e) {
                //ignore
            } catch (Exception e) {
                LOGGER.error("getFirstMappedFile has exception.", e);
            }
        }

        return mappedFileFirst;
    }

    public boolean load() {
        File dir = new File(this.storePath);
        File[] files = dir.listFiles();
        if (files != null) {
            // ascending order
            Arrays.sort(files);
            for (File file : files) {

                if (file.length() != this.mappedFileSize) {
                    LOGGER.warn(file + "\t" + file.length()
                        + " length not matched message store config value, please check it manually");
                    return false;
                }
                try {
                    MappedFile mappedFile = new MappedFile(file.getPath(), mappedFileSize);
                    mappedFile.setWrotePosition(this.mappedFileSize);
                    mappedFile.setFlushedPosition(this.mappedFileSize);
                    mappedFile.setCommittedPosition(this.mappedFileSize);
                    this.mappedFiles.add(mappedFile);
                    LOGGER.info("load " + file.getPath() + " OK");
                } catch (IOException e) {
                    LOGGER.error("load file " + file + " error", e);
                    return false;
                }
            }
        }
        return true;
    }

}
