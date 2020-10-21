package com.example.vkinfo.utils;

public interface iOnDataFetched {
    void showProgressBar();

    void hideProgressBar();

    void setDataInPageWithResult(Object result);
}