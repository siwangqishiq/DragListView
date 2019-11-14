package com.xinlan.draglistview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView mListView;


    public static class Bean {
        int type;
        String content;
    }

    private List<Bean> mData = new ArrayList<Bean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        for (int i = 0; i < 100; i++) {
            Bean bean = new Bean();
            bean.content = String.valueOf(i);
            mData.add(bean);
        }


        mListView = findViewById(R.id.list_view);
        final Adapter adapter = new Adapter();
        mListView.setAdapter(adapter);

        GridLayoutManager layoutMgr = new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mListView.setLayoutManager(layoutMgr);
        mListView.setHasFixedSize(true);
        mListView.setItemAnimator(new DefaultItemAnimator());


        ItemTouchHelper.SimpleCallback cb = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();

                Collections.swap(mData, fromPos, toPos);
                adapter.notifyItemMoved(fromPos, toPos);
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(cb);
        touchHelper.attachToRecyclerView(mListView);




        RecyclerView lv2 = findViewById(R.id.list_view2);
        final Adapter adapter2 = new Adapter();
        lv2.setAdapter(adapter2);

        GridLayoutManager layoutMgr2 = new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lv2.setLayoutManager(layoutMgr2);
        lv2.setHasFixedSize(true);
    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(MainActivity2.this).inflate(R.layout.item, parent, false);
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Bean itemData = mData.get(position);
            holder.textView.setText(itemData.content);
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
