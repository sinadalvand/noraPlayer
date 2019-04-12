package ir.sinadalvand.searchview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.lapism.searchview.R;
import ir.sinadalvand.searchview.Search;
import ir.sinadalvand.searchview.graphics.ResizeAnimation;

@SuppressLint("DrawAllocation")
public class SearchBar extends SearchLayout implements SearchbarStatus {

    private Search.OnBarClickListener mOnBarClickListener;

    private boolean SearchbarOpenStatus = false;

    private int lastWidth = 0;
    private boolean first_init = true;
    private boolean second_init = true;

    // ---------------------------------------------------------------------------------------------
    public SearchBar(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    // ---------------------------------------------------------------------------------------------

    RelativeLayout.LayoutParams params;
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        params = (RelativeLayout.LayoutParams) mCardView.getLayoutParams();
        if (!SearchbarOpenStatus && mCardView.getWidth() > 0) {

            if (first_init && lastWidth == 0) {
                lastWidth = mCardView.getWidth();
                first_init = false;
                invalidate();
            } else if (!first_init && second_init && lastWidth > 0) {
                params.width = params.height;
                mCardView.setLayoutParams(params);
                second_init = false;
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);



    }


    @Override
    protected void onTextChanged(CharSequence s) {
        if (mOnQueryTextListener != null) {
            mOnQueryTextListener.onQueryTextChange(s);
        }
    }

    @Override
    protected void addFocus() {
//        if (mOnMicClickListener == null) {
//            mImageViewMic.setVisibility(View.VISIBLE);
//        }
        showKeyboard();
    }

    @Override
    protected void removeFocus() {
//        if (mOnMicClickListener == null) {
//            mImageViewMic.setVisibility(View.GONE);
//        }
        hideKeyboard();
    }

    @Override
    protected boolean isView() {
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.search_bar;
    }

    @Override
    protected void open() {
        ResizeAnimation animation = new ResizeAnimation(mCardView, mCardView.getWidth(), mCardView.getHeight(), lastWidth, mCardView.getHeight());
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mSearchEditText.setVisibility(VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageViewLogo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.search_ic_clear_black_24dp));
                mSearchEditText.requestFocus();
                SearchbarOpenStatus = true;
                mOnBarClickListener.onBarClick(true);
                requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mCardView.startAnimation(animation);
    }

    @Override
    public void close() {
        ResizeAnimation animation = new ResizeAnimation(mCardView, mCardView.getWidth(), mCardView.getHeight(), mCardView.getHeight(), mCardView.getHeight());
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mSearchEditText.setVisibility(GONE);
                mSearchEditText.clearFocus();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageViewLogo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_search));
                SearchbarOpenStatus = false;
                mOnBarClickListener.onBarClick(false);
                requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mCardView.startAnimation(animation);


    }

    // ---------------------------------------------------------------------------------------------
    @Override
    void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchBar, defStyleAttr, defStyleRes);
        int layoutResId = getLayout();

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(layoutResId, this, true);

        super.init(context, attrs, defStyleAttr, defStyleRes);

        setLogo(a.getInt(R.styleable.SearchBar_search_logo, Search.Logo.GOOGLE));
        setShape(a.getInt(R.styleable.SearchBar_search_shape, Search.Shape.OVAL));
        setTheme(a.getInt(R.styleable.SearchBar_search_theme, Search.Theme.GOOGLE));
        setVersionMargins(a.getInt(R.styleable.SearchBar_search_version_margins, Search.VersionMargins.BAR));

        if (a.hasValue(R.styleable.SearchBar_search_elevation)) {
            setElevation(a.getDimensionPixelSize(R.styleable.SearchBar_search_elevation, 0));
        }


        mSearchEditText.setLayout(this);



        a.recycle();




    }

    // ---------------------------------------------------------------------------------------------
    // Listeners
    public void setOnBarClickListener(Search.OnBarClickListener listener) {
        mOnBarClickListener = listener;
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        if (v == mImageViewLogo) {
            if (!SearchbarOpenStatus) {
                open();
            } else {
                close();
            }

        } else if (v == mImageViewMic) {
            if (mOnMicClickListener != null) {
                mOnMicClickListener.onMicClick();
            }
        } else if (v == mImageViewMenu) {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onMenuClick();
            }
        }
    }


    @Override
    public boolean isSearchbarOpen() {
        return SearchbarOpenStatus;
    }
}
