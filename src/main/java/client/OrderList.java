package client;


public class OrderList {
    private Integer courierId;
    private String nearestStation;
    private Integer limit;
    private Integer page;

    public OrderList(Integer courierId, String nearestStation, Integer limit, Integer page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }


}
