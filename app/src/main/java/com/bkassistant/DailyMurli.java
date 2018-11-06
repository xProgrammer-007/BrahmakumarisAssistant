package com.bkassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.*;
import android.widget.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DailyMurli extends AppCompatActivity implements View.OnClickListener {
    Dialog mBottomSheetDialog;
    WebView webView;

    public final String baseUrl = "http://www.babamurli.com/01.%20Daily%20Murli/";
    public MurliLanguage selectedLanguage;
    DatePicker datePicker;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TextView dateLabel;
    TextView languageLabel;
    String selectedDate;
    Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_murli);

        dateLabel = findViewById(R.id.dateLabel);
        languageLabel = findViewById(R.id.lanuageLabel);

        dialog = new Dialog(DailyMurli.this,R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.custom_progressbar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        datePicker = new DatePicker(this);
        myCalendar =  Calendar.getInstance();
        selectedLanguage = MurliLanguage.hindi;
        selectedDate  = new SimpleDateFormat("dd.MM.yy").format(new Date());

        dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));


        System.out.println(selectedDate);

        myCalendar.set(new Date().getYear(),new Date().getMonth(),new Date().getDay());


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
                String strDate = format.format(myCalendar.getTime());

                selectedDate = strDate;

                System.out.println(strDate);
                dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(myCalendar.getTime()));
                _getMurli();


            }

        };

        webView = findViewById(R.id.webViewMurli);




        webView.setWebViewClient(
            new WebViewClient() {



                 @Override
                 public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    System.out.println(url);
                     view.loadUrl(url);
                     return true;
                 }

                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    super.onReceivedHttpError(view, request, errorResponse);
                    System.out.println("PETER BHSOSDIKE");
                    Toast.makeText(DailyMurli.this, "Sorry , Document not found , It has not been uploaded . Try again later", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    System.out.println("PETER BHSOSDIKE");
                    Toast.makeText(DailyMurli.this, "Sorry , Document not found , It has not been uploaded . Try again later", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String pageTitle = view.getTitle();
                    String[] separated = pageTitle.split("-");
                    if(separated[0].equals("404")) {
                        System.out.println("detect page not found error 404");
                    }
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
            }
        );
        

        _getMurli();

       mBottomSheetDialog = new Dialog(DailyMurli.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.language_bottomsheet);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);


        int[] lanuageButtons = {R.id.englishLangBottomSheet,R.id.hindiLangBottomSheet,R.id.nepaliLangBottomSheet,R.id.bengaliLangBottomSheet,R.id.teluguLangBottomSheet,R.id.tamilLangBottomSheet};
        Drawable leftDrawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_check_box_black_24dp);
        ButtonGroup buttonGroup = new ButtonGroup(lanuageButtons,0,leftDrawable,mBottomSheetDialog);

          findViewById(R.id.languagePicker).setOnClickListener(this);
          findViewById(R.id.datePicker).setOnClickListener(this);
          findViewById(R.id.dateLabel).setOnClickListener(this);





    }

    private void _scrapMurli(WebView webView, String url) {
       GetWebPage getWebPage = new GetWebPage(webView, this, dialog);
           getWebPage.execute(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.languagePicker:
                    mBottomSheetDialog.show();
                break;
            case R.id.datePicker:
            case R.id.dateLabel:
                _showDatePicker();
                break;
                default:
        }
    }

    private void _showDatePicker() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog =
                new DatePickerDialog(this, date, mYear, mMonth, mDay);
        dialog.show();

    }

    void _getMurli(){
        if (!dialog.isShowing())
            dialog.show();
        final MurliLang[] langs = new MurliLang[5];
        langs[0] = new MurliLang("07.%20Bengali/01.%20Bengali%20Murli%20-%20Htm/","Bengali","bn", MurliLanguage.bengali);
        langs[1] = new MurliLang("02.%20English/01.%20Eng%20Murli%20-%20Htm/","E","en", MurliLanguage.english);
        langs[2] = new MurliLang("01.%20Hindi/01.%20Hindi%20Murli%20-%20Htm/","H","hi", MurliLanguage.hindi);
        langs[3] = new MurliLang("03.%20Tamil/01.%20Tamil%20Murli%20-%20Htm/","Tamil","ta", MurliLanguage.tamil);
        langs[4] = new MurliLang("04.%20Telugu/01.%20Telugu%20-%20Murli%20-%20Htm/","Telugu","te", MurliLanguage.telugu);

        for(int i = 0;i < langs.length;i++){
            if(langs[i].murliLanguage == selectedLanguage){
                final String url = this.baseUrl + langs[i].urlPart + selectedDate + '-' + langs[i].urlEndPart + ".htm";
                _scrapMurli(webView,url);
                System.out.println(url);
            }
        }

    }

    class MurliLang {
        final String urlPart;
        final String urlEndPart;
        final String language;
        final MurliLanguage murliLanguage;

        MurliLang(String urlPart, String urlEndPart, String language, MurliLanguage murliLanguage) {
            this.urlPart = urlPart;
            this.urlEndPart = urlEndPart;
            this.language = language;
            this.murliLanguage = murliLanguage;
        }
    }


    class ButtonGroup implements  View.OnClickListener{
        public final Dialog dialog;
        public final int[] ids;
        public Button[] buttons;
        private int activeIndex;
        public final Drawable drawableLeft;
        private Drawable transparent24  = getApplicationContext().getResources().getDrawable(R.drawable.transparent24);
        public int _getActiveIndex(){
            return activeIndex;
        }

        ButtonGroup(int[] ids, int activeIndex, Drawable drawableLeft, Dialog dialog) {
            this.ids = ids;
            this.activeIndex = activeIndex;
            this.drawableLeft = drawableLeft;
            this.dialog = dialog;
            buttons = new Button[ids.length];
            for (int i = 0; i<ids.length ; i++){
                buttons[i] =  ( (Button) dialog.findViewById(ids[i]) ) ;
                buttons[i].setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            for (int i =0;i< ids.length;i++){
                if(v.getId() == ids[i]){
                    activeIndex = i;
                    buttons[i].setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft,null,null,null);
                }else{
                    buttons[i].setCompoundDrawablesRelativeWithIntrinsicBounds(transparent24,null,null,null);
                }
                dialog.dismiss();
                switch(v.getId()){
                    case R.id.hindiLangBottomSheet:
                        selectedLanguage = MurliLanguage.hindi;
                        languageLabel.setText(getString(R.string.languageStrHindi));
                        break;
                    case R.id.nepaliLangBottomSheet:
                        selectedLanguage = MurliLanguage.nepali;
                        languageLabel.setText(getString(R.string.langStrNepali));
                        break;
                    case R.id.bengaliLangBottomSheet:
                        selectedLanguage = MurliLanguage.bengali;
                        languageLabel.setText(getString(R.string.langStrBengali));
                        break;
                    case R.id.englishLangBottomSheet:
                        selectedLanguage = MurliLanguage.english;
                        languageLabel.setText(getString(R.string.langStrEnglish));
                        break;
                    case R.id.tamilLangBottomSheet:
                        selectedLanguage = MurliLanguage.tamil;
                        languageLabel.setText(getString(R.string.langStrTamil));
                        break;
                    case R.id.teluguLangBottomSheet:
                        selectedLanguage = MurliLanguage.telugu;
                        languageLabel.setText(getString(R.string.langStrTelugu));
                        break;
                }

                _getMurli();
            }
        }
    }

    enum MurliLanguage{
        english,
        hindi,
        bengali,
        nepali,
        tamil,
        telugu
    }


}


class GetWebPage extends AsyncTask<String,Void,Document>{

     private final WebView webView;
     public  final DailyMurli dailyMurli;
     public final Dialog dialog;


    GetWebPage(WebView webView, DailyMurli dailyMurli, Dialog dialog) {
        this.webView = webView;
        this.dailyMurli = dailyMurli;
        this.dialog = dialog;
    }

    @Override
    protected Document doInBackground(String... strings) {
        Document doc = null;
        try {
            System.out.println(strings[0]);
            doc = Jsoup.connect(strings[0])
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101").get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (dialog.isShowing())
                dialog.dismiss();
        }

        return doc;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        if(document != null){
            System.out.println(document.select("blockquote"));
            webView.getSettings().setJavaScriptEnabled(true);

            String header =
                    "<html>" +
                            "<head>" +
                            "<meta http-equiv='Content-Type' content='text/html; charset=windows-1252'>" +
                            "</head>" +
                            "<body>";

            String footer = "" +
                    "</body>" +
                    "</html>";
            document.body().attr("style","background:transaprent;");
            String body = document.select("blockquote").outerHtml();

            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.loadDataWithBaseURL(
                    "",
                    header + body + footer,
                    "text/html",
                    "UTF-8",
                    ""
            );

        }else{
            System.out.println("PETER BHSOSDIKE");
            Toast.makeText(dailyMurli, "Sorry , Document not found , It has not been uploaded . Try again later", Toast.LENGTH_LONG).show();

        }
    }

}





