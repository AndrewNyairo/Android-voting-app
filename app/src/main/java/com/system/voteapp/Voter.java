package com.system.voteapp;

class Voter {
    public Voter(String ifVoted) {
        this.ifVoted = ifVoted;
    }

    public Voter(){

    }

    private String ifVoted;

    public String getIfVoted() {
        return ifVoted;
    }
}
