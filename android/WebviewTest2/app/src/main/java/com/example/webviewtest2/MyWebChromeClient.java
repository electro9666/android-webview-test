package com.example.webviewtest2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {
    private final String TAG = "MyWebChromeClient";

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        MyLog.i(TAG,"onProgressChanged(view:"+view.toString()+ ", newProgress:"+newProgress+")");
    }
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        MyLog.toastMakeTextShow(view.getContext(), "TAG", "window.open 협의가 필요합니다.");
        WebView newWebView = new WebView(view.getContext());
        WebSettings webSettings = newWebView.getSettings();
        WebSettings settings = newWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);

        //final Dialog dialog = new Dialog(view.getContext(),R.style.Theme_DialogFullScreen);
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(newWebView);
        dialog.show();


        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                // 왜 뒤로가기 버튼 1회에 두번씩 반응하나(true, false): keyUp, keyDown
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    MyLog.toastMakeTextShow(view.getContext(), "TAG", "KEYCODE_BACK");
                    MyLog.toastMakeTextShow(view.getContext(), "TAG", newWebView.canGoBack() + "");
                    MyLog.toastMakeTextShow(view.getContext(), "TAG", newWebView + "");
                    if(newWebView.canGoBack()){
                        newWebView.goBack();
                    }else{
                        MyLog.toastMakeTextShow(view.getContext(), "TAG", "Window.open 종료");
                        dialog.dismiss(); //
                    }
                    return true;
                }else{
                    return false;
                }
            }
        });
        newWebView.setWebViewClient(new MyWebViewClient(view.getContext()));
        newWebView.setWebChromeClient(new MyWebChromeClient() {
            @Override
            public void onCloseWindow(WebView window) {
                dialog.dismiss(); }
        });


        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(newWebView);
        resultMsg.sendToTarget();
        return true;
    }
    @Override
    public void onCloseWindow(WebView window) {
        MyLog.i(getClass().getName(), "onCloseWindow");
        window.setVisibility(View.GONE);
        window.destroy();
        //mWebViewSub=null;
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        MyLog.i(getClass().getName(), "onJsAlert() url:"+url+", message:"+message);
        //return super.onJsAlert(view, url, message, result);
        new AlertDialog.Builder(view.getContext())
                .setTitle("title")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();

                            }
                        })
                .setCancelable(false)
                .create()
                .show();

        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        MyLog.i(getClass().getName(), "onJsConfirm() url:"+url+", message"+message);
        //return super.onJsConfirm(view, url, message, result);

        new AlertDialog.Builder(view.getContext())
                .setTitle("")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();

                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                .create()
                .show();
        return true;
    }
}
