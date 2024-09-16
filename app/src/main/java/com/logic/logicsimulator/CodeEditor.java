package com.logic.logicsimulator;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class CodeEditor extends AppCompatEditText {
    public CodeEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Set up text change listener for syntax highlighting and auto-indentation
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Apply syntax highlighting
                applySyntaxHighlighting();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void applySyntaxHighlighting() {
        String text = getText().toString();

        // Create a SpannableStringBuilder to apply different colors
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        // Define colors for syntax highlighting
        int tagColor = Color.BLUE; // Color for XML tags
        int attributeColor = Color.RED; // Color for XML attributes
        int valueColor = Color.GREEN; // Color for attribute values

        // Find and color XML tags
        colorSyntax(builder, "<[^/!].*?>", tagColor);

        // Find and color XML attributes
        colorSyntax(builder, "\\b\\w+\\s*=", attributeColor);

        // Find and color attribute values
        colorSyntax(builder, "\"[^\"]*\"", valueColor);

        // Set the styled text to the editor
        setText(builder);
    }

    private void colorSyntax(SpannableStringBuilder builder, String pattern, int color) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(builder);

        while (m.find()) {
            builder.setSpan(new ForegroundColorSpan(color), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Implement auto-indentation
            int start = getSelectionStart();
            int end = getSelectionEnd();
            String text = getText().toString();

            // Find the indentation level of the previous line
            int previousLineIndentation = 0;
            int pos = start - 1;
            while (pos >= 0 && text.charAt(pos) != '\n') {
                if (text.charAt(pos) == ' ') {
                    previousLineIndentation++;
                } else {
                    break;
                }
                pos--;
            }

            // Insert the new line with the same indentation level
            String indent = "\n";
            for (int i = 0; i < previousLineIndentation; i++) {
                indent += " ";
            }
            getText().insert(start, indent);

            // Move the cursor to the appropriate position
            setSelection(start + previousLineIndentation + 1);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
