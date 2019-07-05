package cn.joey.mongo.utils;

import cn.joey.mongo.core.Store;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/5/17
 * @description
 */
public class MongoUtils {
    private static Properties properties;

    /**
     * 关闭资源
     */
    public static void close() {
        Store.getInstance().close();
    }

    /**
     * 获取MongoClient
     * @return
     */
    public static MongoClient getMongoClient() {
        return Store.getInstance().getMongoClient();
    }

    /**
     * 获取MongoDatabase
     * @return
     */
    public static MongoDatabase getMongoDatabase() {
        return Store.getInstance().getMongoDatabase();
    }

}
