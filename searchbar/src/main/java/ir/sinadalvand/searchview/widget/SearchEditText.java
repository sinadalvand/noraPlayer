package ir.sinadalvand.searchview.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SearchEditText extends AppCompatEditText {

    private SearchBar mSearchLayout;

    public SearchEditText(@NonNull Context context) {
        super(context);
    }

    public SearchEditText(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayout(SearchBar layout) {
        mSearchLayout = layout;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mSearchLayout != null && mSearchLayout.isSearchbarOpen() && getText().toString().equals("")) {
                mSearchLayout.close();
                return true;
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

}
