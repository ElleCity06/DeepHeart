package com.deepheart.ellecity06.deepheart.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 限制EditText工具类
 */

public class EditTextLimitUtils {

    /**
     * 限制空格输入
     *
     * @param editText
     */
    public static void limitEmptyAndChar(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //监听输入空格和字数限制
                String inputText = charSequence.toString();

                if (inputText.contains(" ")) {
                    String[] str = charSequence.toString().split(" ");
                    String limitEmptyText = "";
                    for (String s : str) {
                        limitEmptyText += s;
                    }
                    editText.setText(limitEmptyText);
                    editText.setSelection(limitEmptyText.length());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * 限制身高体重的输入
     *
     * @param editText
     */
    public static void limitHeighWeightInput(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String inputText = charSequence.toString();

                // 监听输入空格和字数限制
                if (!TextUtils.isEmpty(inputText)) {
                    if (inputText.contains(" ")) {
                        String[] str = charSequence.toString().split(" ");
                        String limitEmptyText = "";
                        for (String s : str) {
                            limitEmptyText += s;
                        }
                        editText.setText(limitEmptyText);
                        editText.setSelection(limitEmptyText.length());
                    } else if (inputText.contains(".")) {
                        int indexOf = inputText.indexOf(".");
                        if (indexOf == 0) {
                            editText.setText("");
                        } else {
                            String[] split = inputText.split("\\.");
                            if (split.length == 2) {
                                if (split[1].length() > 1) {
                                    String s = split[0] + "." + split[1].substring(0, split[1].length() - 1);
                                    editText.setText(s);
                                    editText.setSelection(s.length());
                                }
                            }
                        }
                    } else {
                        int indexOf = inputText.indexOf("0");
                        if (indexOf == 0) {
                            editText.setText("");
                        } else {
                            if (inputText.length() > 3) {
                                String substring = inputText.substring(0, inputText.length() - 1);
                                editText.setText(substring);
                                editText.setSelection(substring.length());
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
