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
 * @description
 */
@Getter
@Setter
@ToString
@BsonDiscriminator("zhongbiao_extraction")
public class ZhongBiaoExtraction {
    private ObjectId id;
    @BsonProperty("project_name")
    private String project_name;
    @BsonProperty("project_code")
    private String project_code;
    @BsonProperty("page_time")
    private String page_time;
    private String area;
    private String province;
    private String city;
    private String district;
    private String industry;
    @BsonProperty("info_type")
    private String info_type;
    @BsonProperty("document_id")
    private String document_id;
    @BsonProperty("document_title")
    private String document_title;
    private String tenderee;
    private String agency;
    @BsonProperty("agency_phone")
    private String agencyPhone;
    @BsonProperty("agency_contact")
    private String agencyContact;
    @BsonProperty("win_tenderer")
    private String winTenderer;
    @BsonProperty("win_bid_price")
    private String winBidPrice;
    @BsonProperty("win_tenderer_manager")
    private String winTendererManager;
    @BsonProperty("first_tenderer")
    private String firstTenderer;
    @BsonProperty("first_bid_price")
    private String firstBidPrice;
    @BsonProperty("first_tenderer_manager")
    private String firstTendererManager;
    @BsonProperty("is_effective")
    private Boolean effective;
    @BsonProperty("neo_status")
    private String neoStatus;
    private String upgradeStatus;

}
