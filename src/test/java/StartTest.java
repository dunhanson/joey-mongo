import cn.joey.mongo.utils.MongoUtils;
import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Collation;
import entity.ExtractedInformation;
import entity.ZhaoBiaoExtraction;
import entity.ZhongBiaoExtraction;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/5/17
 * @description
 */
public class StartTest {

    @Test
    public void test() {
        //MongoDatabase mongoDatabase = MongoUtils.getMongoDatabase();
        //System.out.println(mongoDatabase.getCollection("zhaobiao_extraction").find().first());
        testExtractedInformation();
    }


    /**
     * 测试
     */
    public void testExtractedInformation() {
        System.out.println(getExtractedInformationByDocumentId("13815762"));
    }

    public ExtractedInformation getExtractedInformationByDocumentId(String documentId) {
        ExtractedInformation extractedInformation = new ExtractedInformation();

        if(StringUtils.isBlank(documentId)) {
            return extractedInformation;
        }

        MongoDatabase mongoDatabase = MongoUtils.getMongoDatabase();

        //招标信息
        MongoCollection<ZhaoBiaoExtraction> zhaoBiaoCollection = mongoDatabase
                .getCollection("zhaobiao_extraction", ZhaoBiaoExtraction.class);
        ZhaoBiaoExtraction zhaoBiaoExtraction = zhaoBiaoCollection.find(
                eq("docId", documentId)
        ).first();


        if(zhaoBiaoExtraction != null){
            extractedInformation.setTenderee(zhaoBiaoExtraction.getTenderee());
        } else {
            //中标信息
            MongoCollection<ZhongBiaoExtraction> zhongBiaoCollection = mongoDatabase
                    .getCollection("zhongbiao_extraction", ZhongBiaoExtraction.class);
            ZhongBiaoExtraction zhongBiaoExtraction = zhongBiaoCollection.find(
                    eq("docId", documentId)
            ).first();
            if(zhongBiaoExtraction != null){
                extractedInformation.setTenderee(zhongBiaoExtraction.getTenderee());
                extractedInformation.setAgency(zhongBiaoExtraction.getAgency());
                extractedInformation.setWinTenderer(zhongBiaoExtraction.getWinTenderer());
            }
        }
        return extractedInformation;
    }

    public void start() {
        MongoUtils.init();

        //database
        MongoDatabase mongoDatabase = MongoUtils.getMongoDatabase();
        MongoCollection<ZhaoBiaoExtraction> collection = mongoDatabase.getCollection("zhaobiao_extraction", ZhaoBiaoExtraction.class);

        List<ZhaoBiaoExtraction> list = new ArrayList<>();
        collection.find().limit(5).into(list);
        list.forEach(obj->{
            System.out.println(obj);
        });
    }
}
