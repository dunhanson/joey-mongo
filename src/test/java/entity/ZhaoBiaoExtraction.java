package entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/5/17
 * @description 招标要素提取
 */
@Getter
@Setter
@ToString
@BsonDiscriminator("zhaobiao_extraction")
public final class ZhaoBiaoExtraction {
    private ObjectId id;
    @BsonProperty("project_name")
    private String projectName;
    @BsonProperty("project_code")
    private String projectCode;
    @BsonProperty("page_time")
    private String pageTime;
    private String area;
    private String province;
    private String city;
    private String district;
    private String industry;
    @BsonProperty("info_type")
    private String infoType;
    @BsonProperty("document_id")
    private String documentId;
    @BsonProperty("document_title")
    private String documentTitle;
    private String tenderee;
    @BsonProperty("tenderee_addr")
    private String tendereeAddr;
    @BsonProperty("tenderee_phone")
    private String tendereePhone;
    @BsonProperty("tenderee_contact")
    private String tendereeContact;
    private String agency;
    @BsonProperty("neo_status")
    private String neoStatus;
    private String upgradeStatus;
    private String docId;
}
