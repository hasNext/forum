package com.forum.sequence.service;

import com.forum.utils.sequence.KeyFactory;

public interface IZookeeperKeyFactory extends KeyFactory {

    void init(String typeName, long newValue);

}
