package com.dht.nodeserver.Block;

import com.syh.mdfs.nodeserver.Model.PersistentFileBlockMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

public class BlockManager {
    @Autowired
    private BlockRepository blockRepository;

    public List<Block> getAllBlocks(){
        return (List<Block>)blockRepository.findAll();
    }

    public void save(Block block){
        blockRepository.save(block);
    }

    public void delete(Block block){
        blockRepository.delete(block);
    }

}
