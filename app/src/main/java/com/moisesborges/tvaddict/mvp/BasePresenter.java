package com.moisesborges.tvaddict.mvp;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Moisés on 12/04/2017.
 */

public abstract class BasePresenter<T extends BaseView> {

    private T mView;
    private final RxJavaConfig mRxJavaConfig;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BasePresenter(@NonNull RxJavaConfig rxJavaConfig) {
        mRxJavaConfig = rxJavaConfig;
    }

    public T getView() {
        return mView;
    }

    public RxJavaConfig getRxJavaConfig() {
        return mRxJavaConfig;
    }

    public void bindView(@NonNull T view) {
        mView = view;
    }

    public void unbindView() {
        mCompositeDisposable.clear();
        mView = null;
    }

    public void addDisposable(@NonNull Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    public void checkView() {
        if (mView == null) {
            throw new ViewNotAttachedException("View not attached to " + getClass().getSimpleName() + " Presenter");
        }
    }

    @NonNull
    protected <T> SingleTransformer<T, T> applySchedulersToSingle() {
        return single -> single.subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler());
    }

    @NonNull
    protected CompletableTransformer applySchedulersToCompletable() {
        return completable -> completable.subscribeOn(getRxJavaConfig().ioScheduler()).observeOn(getRxJavaConfig().androidScheduler());
    }
}
