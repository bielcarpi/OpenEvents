package com.grup8.OpenEvents.model;

public class AssistanceModel {

    private static final AssistanceModel singleton = new AssistanceModel();
    private AssistanceModel(){}

    public static AssistanceModel getInstance(){
        return singleton;
    }
}
