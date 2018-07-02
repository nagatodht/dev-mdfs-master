package com.dht.nodeserver.Block;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Block {
	private String fileName;
    private int blockSize;
    
    public Block(String fileName, int blockSize) {
        this.fileName = fileName;
        this.blockSize = blockSize;
    }
    
    public Block() {}

    @Override
    public String getFileName() {
        return fileName;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }
}