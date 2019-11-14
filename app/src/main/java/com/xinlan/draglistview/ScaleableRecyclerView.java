package com.xinlan.draglistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ScaleableRecyclerView extends RecyclerView {
    public ScaleableRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ScaleableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
//        ViewGroup.LayoutParams params = getLayoutParams();
//        params.height = getMeasuredHeight();
    }
}
