package com.forum.sequence.service;

import com.forum.utils.propertis.PropertiesUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.stereotype.Component;

@Component("keyFactory")
public class ZooKeeperGeneratePrimaryKey implements IZookeeperKeyFactory, SharedCountListener {

    private CuratorFramework client;
    private static final String PATH = "/primaryKey/";

    private final static String FILE_NAME = "zookeeper.properties";
    private final static String PROP_NAME = "zookeeperList";

    public ZooKeeperGeneratePrimaryKey(){
        String integral = PropertiesUtil.getProperty(PROP_NAME, FILE_NAME);
        CuratorFramework client = CuratorFrameworkFactory
                .newClient(integral, new ExponentialBackoffRetry(1000, 3));
        client.start();
        try {
            client.blockUntilConnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.client = client;
    }

    @Override
    public void init(String typeName, long newValue) {
        String path = PATH + typeName + "/id";

        final DistributedAtomicLong count = new DistributedAtomicLong(client, path, new RetryNTimes(10, 10));
        try {
            count.forceSet(newValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(typeName);
    }

    @Override
    public int generateKey(String typeName) {
        int id=0;
        try {
            id = getPrimaryKey(typeName);
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    private int getPrimaryKey(String typeName) throws Exception{

        String path = PATH + typeName + "/id";

        final DistributedAtomicLong count = new DistributedAtomicLong(client, path, new RetryNTimes(10, 10));

        AtomicValue<Long> value = getValue(count);

        return Integer.parseInt(value.postValue().toString());

    }

    private AtomicValue<Long> getValue(DistributedAtomicLong count) throws Exception{

        AtomicValue<Long> value;
        do{
            value = count.increment();
        }while(!value.succeeded());

        return value;
    }

    @Override
    public void countHasChanged(SharedCountReader sharedCountReader, int i) throws Exception {

    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

    }
}
