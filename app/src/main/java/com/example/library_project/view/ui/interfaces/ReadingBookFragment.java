//package com.example.library_project.view.ui.interfaces;
//
//import android.app.ProgressDialog;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.library_project.R;
//import com.github.barteksc.pdfviewer.PDFView;
//
//public class ReadingBookFragment extends Fragment {
//
//    WebView webview;
//    ProgressDialog pDialog;
//
//    public ReadingBookFragment() {
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_book_reading, container,false);
////        init(v);
////        listener();
//        PDFView pdfView = v.findViewById(R.id.pdfView);
//        pdfView.fromUri("https://library-management-prm391.herokuapp.com/rails/active_storage/blobs/redirect/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBJdz09IiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--220d0478b1593f41659b5223bb1ed7293871b6f6/aTypography.pdf");
//        return v;
//    }
//
//
////    private void init(View v) {
////        webview = (WebView) v.findViewById(R.id.webview);
////        webview.getSettings().setJavaScriptEnabled(true);
////        webview.getSettings().setSupportZoom(true);
////
////        pDialog = new ProgressDialog(getContext());
////        pDialog.setTitle("PDF");
////        pDialog.setMessage("Loading...");
////        pDialog.setIndeterminate(false);
////        pDialog.setCancelable(false);
//////        webview.loadUrl("ilename%2A%3DUTF-8%27%272%2520-Empathize_Userpdf.pdf&response-content-type=application%2Fpdf&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA6E6O5BDWN4RUB6ND%2F20211112%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Date=20211112T153246Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=27c7700487f8a922fa79f0530fd8cf4f5a3211109737d09ecf369b76313ae066");
//////        webview.loadUrl("https://www.ucrhealth.org/wp-content/uploads/2020/04/sample.pdf");
////        String pdf = "https://hkduc-bucket.s3.ap-southeast-1.amazonaws.com/ua7w28cc64zrgp37f3lozon09wbp?response-content-disposition=inline%3B%20filename%3D%22aTypography.pdf%22%3B%20filename%2A%3DUTF-8%27%27aTypography.pdf&response-content-type=application%2Fpdf&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA6E6O5BDWN4RUB6ND%2F20211112%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Date=20211112T160922Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=a1700a33f2e7e4ede0404afef8567637a12dbefdf9d5dcf6ed97e0d524363cea";
////        webview.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);
////    }
////
////    private void listener() {
////        webview.setWebViewClient(new WebViewClient() {
////            @Override
////            public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                super.onPageStarted(view, url, favicon);
////                pDialog.show();
////            }
////
////            @Override
////            public void onPageFinished(WebView view, String url) {
////                super.onPageFinished(view, url);
////                pDialog.dismiss();
////            }
////        });
////    }
//
//}
