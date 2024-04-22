

package uz.abdulaziz.smartparking.core.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ExpiryDateTextWatcher implements TextWatcher {

    private final EditText editText;

    public ExpiryDateTextWatcher(EditText editText) {
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

        // Add a slash after the second digit
        StringBuilder formattedText = new StringBuilder();
        for (int i = 0; i < digitsOnly.length(); i++) {
            if (i == 2) {
                formattedText.append("/"); // Insert a slash after the second digit
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
