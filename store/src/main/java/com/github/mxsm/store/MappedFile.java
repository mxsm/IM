package com.github.mxsm.store;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.store.utils.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.FileUtils;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since 1.0.0
 */
public class MappedFile {

    //mapped file wrote position
    private final AtomicInteger wrotePosition = new AtomicInteger(0);

    //has committed to file position
    private final AtomicInteger committedPosition = new AtomicInteger(0);

    //flushed to hard disk position
    private final AtomicInteger flushedPosition = new AtomicInteger(0);

    private int fileSize;

    //absolute path with file name
    private String fileName;

    private File file;

    private FileChannel fileChannel;

    private MappedByteBuffer mappedByteBuffer;

    private long fileFromOffset;

    private volatile long storeTimestamp = 0;

    public MappedFile(final String fileName, final int fileSize) throws IOException {
        init(fileName, fileSize);
    }

    private void init(final String fileName, final int fileSize) throws IOException {

        this.fileSize = fileSize;
        this.fileName = fileName;
        this.file = new File(fileName);
        this.fileFromOffset = Long.parseLong(this.file.getName());
        boolean ok = false;
        //enable dir exist
        FileUtil.ensureDirOK(this.file.getParent());

        try {
            this.fileChannel = new RandomAccessFile(file, "rw").getChannel();
            this.mappedByteBuffer = this.fileChannel.map(MapMode.READ_WRITE,0,fileSize);
            ok = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(!ok && this.fileChannel != null){
                this.fileChannel.close();
            }
        }
    }

    public AppendMessageResult appendMessage(RemotingCommand command, AppendMessageCallback callback){
        return callback.appendMessage(0, command);
    }

}
