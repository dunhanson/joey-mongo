package cn.joey.mongo.utils;

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
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static Properties properties;

    static {
        init();
    }

    /**
     * 初始化
     */
    public static void init() {
        if(properties == null) {
            String configFileName = CommonUtils.getConfigFileName();
            try (InputStream in = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(configFileName)){
                //加载配置
                properties = new Properties();
                properties.load(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //连接参数
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");
        String database = properties.getProperty("database");
        String host = properties.getProperty("host");

        //host转换
        List<ServerAddress> serverAddressList = new ArrayList<>();
        Arrays.asList(host.split(",")).forEach(obj->{
            String[] arr = obj.split(":");
            String ip = arr[0];
            int port = Integer.valueOf(arr[1]);
            serverAddressList.add(new ServerAddress(ip, port));
        });

        //CodecRegistry
        CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        //MongoClientSettings
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyToClusterSettings(builder -> {
                    builder.hosts(serverAddressList);
                }).credential(MongoCredential.createCredential(userName, database, password.toCharArray()))
                .build();

        //获取mongoClient
        mongoClient = MongoClients.create(settings);
        //获取mongoDatabase
        mongoDatabase = mongoClient.getDatabase(database);
    }

    /**
     * 关闭资源
     */
    public static void close() {
        if(mongoClient != null) {
            mongoClient.close();
        }
    }

    /**
     * 获取MongoClient
     * @return
     */
    public static MongoClient getMongoClient() {
        health();
        return mongoClient;
    }

    /**
     * 获取MongoDatabase
     * @return
     */
    public static MongoDatabase getMongoDatabase() {
        health();
        return mongoDatabase;
    }

    /**
     * 健康检查
     */
    public static void health(){
        if(mongoDatabase == null || mongoClient == null) {
            init();
            return;
        }
        try {
            mongoDatabase.getCollection("test").find().first();
        } catch (Exception e) {
            init();
        }
    }

}
