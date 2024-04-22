package uz.abdulaziz.smartparking.core.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CreditCardTextWatcher implements TextWatcher {

    private final EditText editText;

    public CreditCardTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Not needed for this example
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Not needed for this example
    }

    @Override
    public void afterTextChanged(Editable s) {
        String originalText = s.toString();

        // Remove any non-digit characters
        String digitsOnly = originalText.replaceAll("\\D", "");

        // Insert a space after every four digits
        StringBuilder formattedText = new StringBuilder();
        for (int i = 0; i < digitsOnly.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedText.append(" "); // Insert a space
            }
            formattedText.append(digitsOnly.charAt(i));
        }

        // Set the formatted text back to the EditText
        editText.removeTextChangedListener(this); // Prevent infinite loop
        editText.setText(formattedText.toString());
        editText.setSelection(formattedText.length()); // Move cursor to the end
        editText.addTextChangedListener(this); // Reattach the listener
    }
}
