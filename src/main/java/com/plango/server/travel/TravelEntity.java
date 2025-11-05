//package com.plango.server.travel;
//
//
//import jakarta.persistence.*;
//
//import java.sql.Date;
//
//@Entity @Table(name="tavel")
//public class TravelEntity {
//    @Id @Column
//    private String travel_id;
//    @Column(nullable = false,length = 100)
//    private String travel_dest;
//    @Column(nullable = false)
//    private Date travel_start;
//    @Column(nullable = false)
//    private Date travel_end;
//    @Column(nullable = false, length = 20)
//    private String travel_theme1;
//    @Column(nullable = false, length = 20)
//    private String travel_theme2;
//    @Column(nullable = false, length = 20)
//    private String travel_theme3;
//
//    public TravelEntity(String travel_id, String travel_dest,
//                        Date travel_start, Date travel_end,
//                        String travel_theme1, String travel_theme2,
//                        String travel_theme3) {
//        this.travel_id = travel_id;
//        this.travel_dest = travel_dest;
//        this.travel_start = travel_start;
//        this.travel_end = travel_end;
//        this.travel_theme1 = travel_theme1;
//        this.travel_theme2 = travel_theme2;
//        this.travel_theme3 = travel_theme3;
//    }
//
//    public TravelEntity() { }
//
//    public String getTravel_id() {
//        return travel_id;
//    }
//
//    public String getTravel_dest() {
//        return travel_dest;
//    }
//
//    public Date getTravel_start() {
//        return travel_start;
//    }
//
//    public Date getTravel_end() {
//        return travel_end;
//    }
//
//    public String getTravel_theme1() {
//        return travel_theme1;
//    }
//
//    public String getTravel_theme2() {
//        return travel_theme2;
//    }
//
//    public String getTravel_theme3() {
//        return travel_theme3;
//    }
//}
