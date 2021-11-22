package com.example.learnthisbook;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ReadTextFile(View view) throws IOException {
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is = getAssets().open("test.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(string).append("\n");
            TextView bookView = ((TextView)findViewById(R.id.textView));
            bookView.setMovementMethod(LinkMovementMethod.getInstance());
            bookView.setText(stringBuilder, TextView.BufferType.SPANNABLE);
        }

        reader.close();
        //set spannable
        TextView bookView = ((TextView)findViewById(R.id.textView));
        Spannable spans = (Spannable) bookView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(stringBuilder.toString());
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = stringBuilder.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            final String mWord;
            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                Log.d("tapped on:", mWord);
                Toast.makeText(widget.getContext(), mWord, Toast.LENGTH_SHORT)
                        .show();
            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
    }

    public void ClickOnWord (View view) throws IOException
    {
        CharSequence sdfs = ((TextView)view).getText();
    }
}