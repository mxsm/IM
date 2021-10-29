package com.github.mxsm.store;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.store.utils.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since 1.0.0
 */
public class MappedFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappedFile.class);

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
        this.file = new File(this.fileName);
        this.fileFromOffset = Long.parseLong(this.file.getName());
        boolean ok = false;
        //enable dir exist
        FileUtil.ensureDirOK(this.file.getParent());

        try {
            this.fileChannel = new RandomAccessFile(file, "rw").getChannel();
            this.mappedByteBuffer = this.fileChannel.map(MapMode.READ_WRITE,0,this.fileSize);
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

        assert command != null;
        assert callback != null;

        int currentPosition = this.wrotePosition.get();

        if(currentPosition < this.fileSize){
            ByteBuffer byteBuffer = this.mappedByteBuffer.slice();
            byteBuffer.position(currentPosition);
            AppendMessageResult result = callback.appendMessage(0, command);
            this.wrotePosition.addAndGet(result.getWroteBytesSize());
            this.storeTimestamp = result.getStoreTimestamp();
            return result;
        }
        return new AppendMessageResult(AppendMessageStatus.UNKNOWN_ERROR);
    }

    public boolean appendMessage(final byte[] data, final int offset, final int length){
        int currentPos = this.wrotePosition.get();

        if ((currentPos + length) <= this.fileSize) {
            try {
                this.fileChannel.position(currentPos);
                this.fileChannel.write(ByteBuffer.wrap(data, offset, length));
            } catch (Throwable e) {
                LOGGER.error("Error occurred when append message to mappedFile.", e);
            }
            this.wrotePosition.addAndGet(length);
            return true;
        }

        return false;
    }

}
