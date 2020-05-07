package com.system.voteapp;

public class GetTotalVotes {
    public String getTotal_yes() {
        return total_yes;
    }

    public void setTotal_yes(String total_yes) {
        this.total_yes = total_yes;
    }

    public String getTotal_no() {
        return total_no;
    }

    public void setTotal_no(String total_no) {
        this.total_no = total_no;
    }

    private String total_yes;
    private String total_no;

    public GetTotalVotes(String total_yes, String total_no) {
        this.total_yes = total_yes;
        this.total_no = total_no;
    }

    public GetTotalVotes(){

    }
}

