package com.example.androidnews;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnews.entity.ClassifyListEntity;

import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder>{
    private static final String TAG = "NavigationAdapter";
    private List<ClassifyListEntity> ClassifyList;
    private int indexArea = -1;
    static  public class ViewHolder extends RecyclerView.ViewHolder {
        View navigationView;
        TextView ClassifyName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navigationView=itemView;
            ClassifyName=itemView.findViewById(R.id.textView2);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.ClassifyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (indexArea != -1) {
                    TextView textView = (TextView) parent.getChildAt(indexArea).findViewById(R.id.textView2);
                    textView.setTextColor(view.getResources().getColor(R.color.colorText));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                }
                int position = holder.getAdapterPosition();
                indexArea = position;
                ClassifyListEntity  Classify = ClassifyList.get(position);
                TextView textView=view.findViewById(R.id.textView2);
                textView.setTextColor(view.getResources().getColor(R.color.colorAccent));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
                Toast.makeText(view.getContext(), "你点击了View"+  Classify.getClassifyName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassifyListEntity Classify = ClassifyList.get(position);
        holder.ClassifyName.setText(Classify.getClassifyName());
        holder.ClassifyName.setTag(Classify.getClassifyId());
       /* if(position==0){
            holder.ClassifyName.setTextColor(holder.itemView.getResources().getColor(R.color.colorAccent));
            holder.ClassifyName.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
        }*/
        //Log.d(TAG, "onBindViewHolder: ClassifyList.get(position)"+ClassifyList.get(position));
    }

    @Override
    public int getItemCount() {
        return ClassifyList.size();
    }

    public NavigationAdapter(List<ClassifyListEntity> fruitList) {
        ClassifyList = fruitList;
    }
}
