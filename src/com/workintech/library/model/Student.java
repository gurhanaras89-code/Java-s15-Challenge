package com.workintech.library.model;

public class Student extends MemberRecord {
    private String studentNumber;

    public Student(long memberId, String studentNumber) {
        super(memberId, "Student", 5);
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() { return studentNumber; }
}