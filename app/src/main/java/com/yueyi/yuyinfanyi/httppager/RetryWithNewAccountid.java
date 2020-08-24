//package com.yueyi.yuyinfanyi.httppager;
//
//import android.util.Log;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.Scheduler;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//
//public class RetryWithNewAccountid implements Function<Observable<Throwable>, ObservableSource<?>> {
//    @Override
//    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Throwable throwable) throws Exception {
//                if(throwable.getMessage().equals("RetryWithNewAccountid")){
//                    return
//                }else {
//                    return Observable.error(throwable);
//                }
//            }
//        }).subscribeOn(Schedulers.io());
//    }
//}
