package com.github.mxsm;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.jupiter.api.Test;



/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        PooledByteBufAllocator bufAllocator = new PooledByteBufAllocator();
        ByteBuf buffer = bufAllocator.buffer(1024);
        System.out.println(buffer);
    }

    public static void main(String[] args) {
        PooledByteBufAllocator bufAllocator = new PooledByteBufAllocator();
        for(int i = 0; i < 100; ++i){
            ByteBuf buffer = bufAllocator.buffer(1024+i);
            System.out.println(buffer);
            buffer.readerIndex();
        }

    }
}
