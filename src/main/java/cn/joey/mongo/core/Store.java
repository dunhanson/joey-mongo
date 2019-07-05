package cn.joey.mongo.core;

import cn.joey.mongo.utils.CommonUtils;
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
 * @date 2019/7/5
 * @description
 */
public class Store {
    private static volatile Store instance = null;
    private MongoClient mongoClient;
    private Properties properties;
    private String userName;
    private String password;
    private String database;
    private String host;

    /**
     * 获取实例
     * @return
     */
    public static Store getInstance() {
        if(instance == null) {
            synchronized (Store.class) {
                instance = new Store();
            }
        }
        return instance;
    }

    /**
     * 加载配置文件
     */
    public void loadConfig() {
        //properties检查
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
        userName = properties.getProperty("userName");
        password = properties.getProperty("password");
        database = properties.getProperty("database");
        host = properties.getProperty("host");
    }


    /**
     * MongoClientSettings
     * @return
     */
    public MongoClientSettings getMongoClientSettings() {
        //加载配置文件
        loadConfig();
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
        return MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyToClusterSettings(builder -> {
                    builder.hosts(serverAddressList);
                }).credential(MongoCredential.createCredential(userName, database, password.toCharArray()))
                .build();
    }

    /**
     * 获取Client
     * @return
     */
    public MongoDatabase getMongoDatabase() {
        return getMongoClient().getDatabase(database);
    }

    /**
     * 获取mongoClient
     * @return
     */
    public MongoClient getMongoClient() {
        if(mongoClient == null || isValid()) {
            //MongoClient
            mongoClient = MongoClients.create(getMongoClientSettings());
        }
        return mongoClient;
    }

    /**
     * 判断是否有效
     * @return
     */
    public boolean isValid() {
        try {
            mongoClient.getDatabase("").getCollection("test").find().first();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 关闭资源
     */
    public void close() {
        try {
            mongoClient.close();;
        } catch (Exception e) {

        }
    }
}
