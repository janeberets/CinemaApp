package com.example.vkinfo.utils;

public abstract class BaseTask<R> implements CustomCallable<R> {
    @Override
    public void setUiForLoading() {

    }

    @Override
    public void setDataAfterLoading(R result) {

    }

    @Override
    public R call() throws Exception {
        return null;
    }
}

