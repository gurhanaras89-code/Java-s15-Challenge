package com.workintech.library.model;

import java.time.LocalDate;

public class MemberRecord {
    private long memberId;
    private String type;
    private LocalDate dateOfMembership;
    private int maxBookLimit;

    public MemberRecord(long memberId, String type, int maxBookLimit) {
        this.memberId = memberId;
        this.type = type;
        this.dateOfMembership = LocalDate.now();
        this.maxBookLimit = maxBookLimit;
    }

    public long getMemberId() { return memberId; }
    public String getType() { return type; }
    public LocalDate getDateOfMembership() { return dateOfMembership; }
    public int getMaxBookLimit() { return maxBookLimit; }
}