package com.android.onlineshop_castanheirofreno.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoriesViewHolder> {

    private Context context;
    private List<CategoryEntity> categories;
    private RecyclerViewItemClickListener myListener;

    public CategoryAdapter(Context context, List<CategoryEntity> categories, RecyclerViewItemClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_list_categories, parent, false);
        CategoriesViewHolder holder = new CategoriesViewHolder(view);
        view.setOnClickListener(v -> myListener.onItemClick(view, holder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            myListener.onItemLongClick(view, holder.getAdapterPosition());
            return true;
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoryEntity categoryEntity = categories.get(position);
        String name = categoryEntity.getTag();
        System.out.println("");

        int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        holder.categoryImageView.setImageResource(resId);

        holder.categoryNameView.setText(categoryEntity.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoriesViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImageView;
        TextView categoryNameView;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImageView = itemView.findViewById(R.id.category_image);
            categoryNameView = itemView.findViewById(R.id.category_name);
        }
    }

    public void setData(final List<CategoryEntity> data) {
        if (categories == null) {
            categories = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return categories.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(((CategoryEntity) categories.get(oldItemPosition)).getIdCategory()==((CategoryEntity) data.get(newItemPosition)).getIdCategory())
                        return true;
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CategoryEntity newOrder = (CategoryEntity) data.get(newItemPosition);
                    CategoryEntity oldOrder = (CategoryEntity) categories.get(newItemPosition);
                    return newOrder.getIdCategory()== oldOrder.getIdCategory()
                            && newOrder.getName() == oldOrder.getName()
                            && newOrder.getTag() == oldOrder.getTag();
                }
            });
            categories = data;
            result.dispatchUpdatesTo(this);
        }
    }

}