import cn.joey.mongo.utils.MongoUtils;
import com.mongodb.client.MongoIterable;
import entity.ZhaoBiaoExtraction;
import org.junit.Test;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/5/30
 * @description
 */
public class SimpleTest {
    @Test
    public void test() {
        MongoUtils.getMongoDatabase().getCollection("");
    }
}
