package com.xinlan.draglistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mListView;


    public static class Bean {
        int type;
        String content;
    }

    private List<Bean> mData = new ArrayList<Bean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 19; i++) {
            Bean bean = new Bean();
            bean.content = String.valueOf(i);
            if (i % 10 == 0) {
                bean.type = 2;
            } else {
                bean.type = 1;
            }
            mData.add(bean);
        }


        mListView = findViewById(R.id.list_view);
        final Adapter adapter = new Adapter();
        mListView.setAdapter(adapter);

        GridLayoutManager layoutMgr = new GridLayoutManager(this, 3);
        mListView.setLayoutManager(layoutMgr);
        layoutMgr.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mData.get(position).type == 2)
                    return 3;
//                if(position %3 == 0)
//                    return 2;

                return 1;
            }
        });
        mListView.setHasFixedSize(true);
        mListView.setItemAnimator(new DefaultItemAnimator());


        ItemTouchHelper.SimpleCallback cb = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                // move item in `fromPos` to `toPos` in adapter.
                //adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                //int formPos = viewHolder.getAdapterPosition();
                //int toPos = target.getAdapterPosition();
                if (mData.get(toPos).type != 2) {
                    Collections.swap(mData, fromPos, toPos);
                    adapter.notifyItemMoved(fromPos, toPos);
                    return true;// true if moved, false otherwise
                } else {
                    return false;
                }
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
                final int fromPos = current.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                if (mData.get(toPos).type == 2) {
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(cb);
        touchHelper.attachToRecyclerView(mListView);
    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 2) {
                View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_title, parent, false);
                return new ViewHolder(rootView);
            } else {
                View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false);
                return new ViewHolder(rootView);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) == 2) {
                Bean itemData = mData.get(position);
                holder.textView.setText(itemData.content);
            } else {
                Bean itemData = mData.get(position);
                holder.textView.setText(itemData.content);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return mData.get(position).type;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
