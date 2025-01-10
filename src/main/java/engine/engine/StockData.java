package engine.engine;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StockData {
    public Response response;

    public static class Response {
        public Header header;
        public Body body;
    }

    public static class Header {
        public String resultCode;
        public String resultMsg;
    }

    public static class Body {
        public int numOfRows;
        public int pageNo;
        public int totalCount;
        public Items items;
    }

    public static class Items {
        @SerializedName("item")
        public List<Item> itemList;
    }

    public static class Item {
        public String basDt;
        public String srtnCd;
        public String isinCd;
        public String itmsNm;
        public String mrktCtg;
        public String clpr;
        public String vs;
        public String fltRt;
        public String mkp;
        public String hipr;
        public String lopr;
        public String trqu;
        public String trPrc;
        public String lstgStCnt;
        public String mrktTotAmt;
    }
}